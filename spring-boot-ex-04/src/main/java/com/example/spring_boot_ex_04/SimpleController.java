package com.example.spring_boot_ex_04;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SimpleController {
    
    @Value("${spring.application.name}")
    String appName;

    @GetMapping("/")
    public String homePage(Model model) {
        System.out.println("SimpleController çalışıyor!");
        System.out.println("AppName: " + appName);
        model.addAttribute("appName", appName);
        return "home";
    }
}