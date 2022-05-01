package ru.animalcare.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexPageController {

    @GetMapping("/")
    public String indexPage() {
        return "main";
    }

    @GetMapping("/public")
    public String publicPage() {
        return "public";
    }

    @GetMapping("/authenticated")
    public String privatePage() {
        return "authenticated";
    }

}
