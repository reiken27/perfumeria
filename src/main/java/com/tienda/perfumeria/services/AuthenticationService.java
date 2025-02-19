package com.tienda.perfumeria.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tienda.perfumeria.controllers.validator.UserValidator;
import com.tienda.perfumeria.dtos.LoginUserDto;
import com.tienda.perfumeria.dtos.RegisterUserDto;
import com.tienda.perfumeria.entities.User;
import com.tienda.perfumeria.repositories.UserRepository;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserRepository userRepository, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User signup(RegisterUserDto input) {
        UserValidator.validateRegisterUser(input);
        var user = new User();
        var name = input.getName();
        var email = input.getEmail();
        var password = input.getPassword();
        var lastName = input.getLastName();
        var birthDate = input.getBirthDate();
        var mobileNum = input.getMobileNum();

        user.setName(name);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setLastName(lastName);
        user.setBirthDate(birthDate);
        user.setMobileNum(mobileNum);
        user.setUsername(email);

        return userRepository.save((User) user);
    }

    public User authenticate(LoginUserDto input) {
        UserValidator.validateLoginUser(input);
        User user = userRepository.findByEmail(input.getEmail())
            .orElseThrow(() -> new RuntimeException("User not found"));
    
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(input.getEmail(), input.getPassword()));
    
        return user;
    }
    

    public List<User> allUsers() {
        List<User> users = new ArrayList<>();

        userRepository.findAll().forEach(users::add);

        return users;
    }
}
