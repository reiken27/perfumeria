package com.tienda.perfumeria.dtos;

import java.util.Date;

public class RegisterUserDto {

    private String email;
    private String name;
    private String lastName;
    private String password;
    private Date birthDate;
    private String mobileNum;
    
        @Override
        public String toString() {
            return "RegisterUserDto{"
                    + "email='" + email + '\''
                    + ", password='" + password + '\''
                    + ", Name='" + name + '\''
                    + "', LastName='" + lastName + '\''
                    + ", birthDate=" + birthDate + '\''
                    + ", mobileNum=" + mobileNum
                    + '}';
        }
    
        public String getEmail() {
            return email;
        }
    
        public RegisterUserDto setEmail(String email) {
            this.email = email;
            return this;
        }
    
        public String getPassword() {
            return password;
        }
    
        public RegisterUserDto setPassword(String password) {
            this.password = password;
            return this;
        }
    
        public String getFullName() {
            return name + ' ' + lastName;
        }
    
        public String getName() {
            return name;
        }
    
        public RegisterUserDto setName(String name) {
            this.name = name;
            return this;
        }
    
        public String getLastName() {
            return lastName;
        }
    
        public RegisterUserDto setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }
    
        public String getMobileNum() {
            return mobileNum;
        }
    
        public RegisterUserDto setMobileNum(String mobileNum) {
            this.mobileNum = mobileNum;
            return this;
        }
    
        public Date getBirthDate() {
            return birthDate;
        }
    
        public RegisterUserDto setBirthDate(Date birthDate) {
            this.birthDate = birthDate;
            return this;
        }
    }