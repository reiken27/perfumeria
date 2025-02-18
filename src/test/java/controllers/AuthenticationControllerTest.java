package controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;

import com.tienda.perfumeria.controllers.AuthenticationController;
import com.tienda.perfumeria.dtos.LoginUserDto;
import com.tienda.perfumeria.dtos.RegisterUserDto;
import com.tienda.perfumeria.entities.User;
import com.tienda.perfumeria.exceptions.InvalidProductException;
import com.tienda.perfumeria.responses.LoginResponse;
import com.tienda.perfumeria.services.AuthenticationService;
import com.tienda.perfumeria.services.JwtService;

import jakarta.servlet.http.HttpServletResponse;

@ExtendWith(MockitoExtension.class)
class AuthenticationControllerTest {

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationService authenticationService;

    @Mock
    private HttpServletResponse response;

    @InjectMocks
    private AuthenticationController authenticationController;

    private RegisterUserDto registerUserDto;
    private LoginUserDto loginUserDto;
    private User mockUser;

    @BeforeEach
    void setUp() {
        registerUserDto = new RegisterUserDto();
        loginUserDto = new LoginUserDto().setEmail("test@example.com").setPassword("password123");
        mockUser = new User();
    }

    @Test
    void testRegister_Success() {
        when(authenticationService.signup(registerUserDto)).thenReturn(mockUser);
        ResponseEntity<User> response = authenticationController.register(registerUserDto);
        assertNotNull(response);
        assertEquals(mockUser, response.getBody());
    }

    @Test
    void testRegister_InvalidData() {
        when(authenticationService.signup(registerUserDto)).thenThrow(new IllegalArgumentException("Invalid data"));
        assertThrows(InvalidProductException.class, () -> authenticationController.register(registerUserDto));
    }

    @Test
    void testAuthenticate_Success() {
        when(authenticationService.authenticate(any(LoginUserDto.class))).thenReturn(mockUser);
        when(jwtService.generateToken(mockUser)).thenReturn("fake-jwt-token");
        when(jwtService.getExpirationTime()).thenReturn(3600L);
        
        ResponseEntity<LoginResponse> responseEntity = authenticationController.authenticate("test@example.com", "password123", response);
        assertNotNull(responseEntity);
        assertEquals("fake-jwt-token", responseEntity.getBody().getToken());
        assertEquals(3600L, responseEntity.getBody().getExpiresIn());
    }

    @Test
    void testAuthenticate_BadCredentials() {
        when(authenticationService.authenticate(any(LoginUserDto.class))).thenThrow(new BadCredentialsException("Credenciales incorrectas"));
        assertThrows(BadCredentialsException.class, () -> authenticationController.authenticate("test@example.com", "wrongpassword", response));
    }

    @Test
    void testAuthenticate_DisabledAccount() {
        when(authenticationService.authenticate(any(LoginUserDto.class))).thenThrow(new DisabledException("Cuenta deshabilitada"));
        assertThrows(DisabledException.class, () -> authenticationController.authenticate("test@example.com", "password123", response));
    }

    @Test
    void testLogout_Success() {
        ResponseEntity<Void> responseEntity = authenticationController.logout(response);
        assertNotNull(responseEntity);
        assertEquals(204, responseEntity.getStatusCodeValue());
    }
}
