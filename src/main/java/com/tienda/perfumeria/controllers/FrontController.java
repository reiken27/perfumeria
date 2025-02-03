package com.tienda.perfumeria.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/")
@Controller
public class FrontController {

    @GetMapping("")
    public String index() {
        return "index";
    }
	@GetMapping("login")
	public String showLoginForm() {
		return "login";
	}
    
	@GetMapping("signup")
	public String showSignupForm() {
		return "signup"; // Renderiza signup.html
	}

}
