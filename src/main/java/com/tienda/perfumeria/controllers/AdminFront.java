package com.tienda.perfumeria.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/admin")
@Controller
public class AdminFront {

    @RequestMapping("/addItem")
    public String admin() {
        return "admin-additem";
    }
}
