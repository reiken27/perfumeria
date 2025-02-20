package com.tienda.perfumeria.configs;

import java.util.Date;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.tienda.perfumeria.entities.Role;
import com.tienda.perfumeria.entities.User;
import com.tienda.perfumeria.repositories.RoleRepository;
import com.tienda.perfumeria.repositories.UserRepository;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initializeData(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            String adminRoleName = "ROLE_ADMIN";
            Role adminRole = roleRepository.findByName(adminRoleName)
                    .orElseGet(() -> {
                        Role newRole = new Role(adminRoleName);
                        roleRepository.save(newRole);
                        System.out.println("Rol ROLE_ADMIN creado.");
                        return newRole;
                    });

            String adminEmail = "admin@admin.com";
            if (userRepository.findByEmail(adminEmail).isEmpty()) {
                User adminUser = new User(
                        "Admin",
                        "eladmin",
                        adminEmail,
                        passwordEncoder.encode("admin"),
                        new Date(),
                        "1234567890",
                        Set.of(adminRole)
                );
                userRepository.save(adminUser);
                System.out.println("Usuario admin creado con email: " + adminEmail);
            }
        };
    }
}
