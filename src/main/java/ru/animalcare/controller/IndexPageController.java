package ru.animalcare.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.animalcare.dto.UserDto;
import ru.animalcare.service.UserService;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class IndexPageController {

    private final UserService userService;

    @ModelAttribute(name = "userDto")
    public UserDto insertUserInMenu(Principal principal) {
        if (principal != null) {
            return userService.findUserByName(principal.getName());
        }
        return null;
    }

    @GetMapping("/")
    public String indexPage(Model model, Principal principal) {
        return "main";
    }

    @GetMapping("/public")
    public String publicPage() {
        return "public";
    }

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
