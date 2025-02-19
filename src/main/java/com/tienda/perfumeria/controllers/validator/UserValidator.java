package com.tienda.perfumeria.controllers.validator;

import java.util.regex.Pattern;

import com.tienda.perfumeria.dtos.LoginUserDto;
import com.tienda.perfumeria.dtos.RegisterUserDto;

public class UserValidator {

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d@$!%*?&]{8,}$"); 

    private static final Pattern NAME_PATTERN =
            Pattern.compile("^[A-Za-zÁÉÍÓÚáéíóúÑñ ]+$"); 
    private static final Pattern MOBILE_PATTERN =
            Pattern.compile("^\\+?\\d{10,15}$"); 

    public static void validateRegisterUser(RegisterUserDto user) {
        if (user.getEmail() == null || !EMAIL_PATTERN.matcher(user.getEmail()).matches()) {
            throw new IllegalArgumentException("El email no es válido.");
        }
        if (user.getPassword() == null || !PASSWORD_PATTERN.matcher(user.getPassword()).matches()) {
            throw new IllegalArgumentException("La contraseña debe tener al menos 8 caracteres, incluir una letra y un número.");
        }
        if (user.getName() == null || !NAME_PATTERN.matcher(user.getName()).matches()) {
            throw new IllegalArgumentException("El nombre solo puede contener letras.");
        }
        if (user.getLastName() == null || !NAME_PATTERN.matcher(user.getLastName()).matches()) {
            throw new IllegalArgumentException("El apellido solo puede contener letras.");
        }
        if (user.getMobileNum() == null || !MOBILE_PATTERN.matcher(user.getMobileNum()).matches()) {
            throw new IllegalArgumentException("El número de teléfono no es válido.");
        }
        if (user.getBirthDate() == null) {
            throw new IllegalArgumentException("La fecha de nacimiento no puede estar vacía.");
        }
    }

    public static void validateLoginUser(LoginUserDto user) {
        if (user.getEmail() == null || !EMAIL_PATTERN.matcher(user.getEmail()).matches()) {
            throw new IllegalArgumentException("El email no es válido.");
        }
        if (user.getPassword() == null || user.getPassword().length() < 8) {
            throw new IllegalArgumentException("La contraseña debe tener al menos 8 caracteres.");
        }
    }
}