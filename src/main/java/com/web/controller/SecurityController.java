package com.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SecurityController {
    @RequestMapping("/login/form")
    public String form(Model model){
        return "login";
    }

    @RequestMapping("/login/success")
    public String success(Model model){
        model.addAttribute("message","Success");
        return "forward:/login/form";
    }
    @RequestMapping("/login/error")
    public String error(Model model){

        return "forward:/login/form";
    }
}
