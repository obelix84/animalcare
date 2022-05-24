package ru.animalcare.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.animalcare.service.AnimalService;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class IndexPageController {

    private final AnimalService animalService;

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

    @RequestMapping("/login")
    public String loginPage() {
        return "login";
    }
}
