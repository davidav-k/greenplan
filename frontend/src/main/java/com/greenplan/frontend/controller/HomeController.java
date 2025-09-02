package com.greenplan.frontend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "GreenPlan - Главная");
        model.addAttribute("message", "Добро пожаловать в GreenPlan!");
        return "home";
    }
    
    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("title", "GreenPlan - О проекте");
        model.addAttribute("message", "GreenPlan - система планирования экологических проектов");
        return "about";
    }

    @GetMapping("/refresh")
    public String refreshTemplates() {
        // Принудительно перезагружает шаблоны
        return "redirect:/";
    }
}
