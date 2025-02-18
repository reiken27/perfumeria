package services;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import com.tienda.perfumeria.services.JwtService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

class JwtServiceTest {

    private JwtService jwtService;
    private UserDetails userDetails;
    private final String secretKey = Base64.getEncoder()
            .encodeToString(Keys.secretKeyFor(SignatureAlgorithm.HS256).getEncoded());
    
    private final long expirationTime = 3600000L; // 1 hora en milisegundos

    @BeforeEach
    void setUp() {
        jwtService = new JwtService();
        ReflectionTestUtils.setField(jwtService, "secretKey", secretKey);
        ReflectionTestUtils.setField(jwtService, "jwtExpiration", expirationTime);
        
        userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("testuser");
    }

    @Test
    void testGenerateToken() {
        String token = jwtService.generateToken(userDetails);
        assertNotNull(token);
    }

    @Test
    void testExtractUsername() {
        String token = jwtService.generateToken(userDetails);
        String extractedUsername = jwtService.extractUsername(token);
        assertEquals("testuser", extractedUsername);
    }

    @Test
    void testIsTokenValid() {
        String token = jwtService.generateToken(userDetails);
        assertTrue(jwtService.isTokenValid(token, userDetails));
    }

    @Test
void testIsTokenExpired() {
    // Crear un token expirado correctamente
    Date expiredDate = new Date(System.currentTimeMillis() - 10000); // Expiró hace 10 seg
    String expiredToken = Jwts.builder()
        .setSubject("testuser")
        .setIssuedAt(new Date(System.currentTimeMillis() - 20000)) // Hace 20 seg
        .setExpiration(expiredDate)
        .signWith(getSignInKey(secretKey), SignatureAlgorithm.HS256)
        .compact();
    
    // Capturar la excepción y validar que el token no es válido
    try {
        boolean isValid = jwtService.isTokenValid(expiredToken, userDetails);
        assertFalse(isValid, "El token expirado debería ser inválido.");
    } catch (io.jsonwebtoken.ExpiredJwtException e) {
        assertTrue(true, "El token ha expirado correctamente.");
    }
}


    private Key getSignInKey(String base64Key) {
        byte[] keyBytes = Base64.getDecoder().decode(base64Key);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}