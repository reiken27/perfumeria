package com.tienda.perfumeria.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tienda.perfumeria.dtos.LoginUserDto;
import com.tienda.perfumeria.dtos.RegisterUserDto;
import com.tienda.perfumeria.entities.User;
import com.tienda.perfumeria.exceptions.InvalidProductException;
import com.tienda.perfumeria.responses.LoginResponse;
import com.tienda.perfumeria.services.AuthenticationService;
import com.tienda.perfumeria.services.JwtService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@RequestMapping("/auth")
@Controller
public class AuthenticationController {

    private final JwtService jwtService;
    private final AuthenticationService authenticationService;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<User> register(@RequestBody RegisterUserDto registerUserDto) {
        try {
            User registeredUser = authenticationService.signup(registerUserDto);
            return ResponseEntity.ok(registeredUser);
        } catch (IllegalArgumentException e) {
            throw new InvalidProductException("Datos de registro inválidos: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Error al registrar usuario", e);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(
            @RequestParam String email,
            @RequestParam String password,
            HttpServletResponse response) {
        try {
            LoginUserDto loginUserDto = new LoginUserDto().setEmail(email).setPassword(password);
            User authenticatedUser = authenticationService.authenticate(loginUserDto);

            // Generar token JWT
            String jwtToken = jwtService.generateToken(authenticatedUser);
            int maxAge = (int) (jwtService.getExpirationTime());
            String cookieValue = "jwt=" + jwtToken + "; Path=/; HttpOnly; SameSite=Strict; Max-Age=" + maxAge;
            response.addHeader("Set-Cookie", cookieValue);

            // Enviar la respuesta con el token y el tiempo de expiración
            LoginResponse loginResponse = new LoginResponse().setToken(jwtToken).setExpiresIn(jwtService.getExpirationTime());

            return ResponseEntity.ok(loginResponse);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Credenciales incorrectas. Verifique su usuario y contraseña.");
        } catch (AccountStatusException e) {
            throw new DisabledException("Cuenta bloqueada o deshabilitada.");
        } catch (Exception e) {
            throw new RuntimeException("Error durante la autenticación", e);
        }
    }

    @GetMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) {
        try {
            Cookie cookie = new Cookie("jwt", "");
            cookie.setMaxAge(0);
            cookie.setPath("/");
            response.addCookie(cookie);

            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            throw new RuntimeException("Error durante el cierre de sesión", e);
        }
    }
}

