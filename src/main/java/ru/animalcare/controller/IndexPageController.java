package ru.animalcare.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

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

    //@PreAuthorize("hasAuthority('ROLE_USER')")
    @GetMapping("/authenticated")
    public String privatePage(Model model, Principal principal) {
        model.addAttribute("user", principal.getName());
        return "authenticated";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

}
