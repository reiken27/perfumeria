package com.tienda.perfumeria.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tienda.perfumeria.dtos.LoginUserDto;
import com.tienda.perfumeria.dtos.RegisterUserDto;
import com.tienda.perfumeria.entities.User;
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
        User registeredUser = authenticationService.signup(registerUserDto);

        return ResponseEntity.ok(registeredUser);
    }

    @GetMapping("/signup")
    public String showSignupForm() {
        return "signup"; // Renderiza signup.html
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(
            @RequestParam String email,
            @RequestParam String password,
            HttpServletResponse response) { // Agregar HttpServletResponse para manejar cookies

        LoginUserDto loginUserDto = new LoginUserDto().setEmail(email).setPassword(password);
        User authenticatedUser = authenticationService.authenticate(loginUserDto);

        // Generar token JWT
        String jwtToken = jwtService.generateToken(authenticatedUser);

        // Crear la cookie JWT
        Cookie cookie = new Cookie("jwt", jwtToken);
        cookie.setHttpOnly(true);
        cookie.setSecure(false); // Cambia a true si usas HTTPS
        cookie.setMaxAge((int) jwtService.getExpirationTime());
        cookie.setPath("/");

        // Agregar la cookie a la respuesta
        response.addCookie(cookie);

        // Enviar la respuesta con el token y el tiempo de expiración
        LoginResponse loginResponse = new LoginResponse()
                .setToken(jwtToken)
                .setExpiresIn(jwtService.getExpirationTime());

        return ResponseEntity.ok(loginResponse);
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }
}
