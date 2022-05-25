package ru.animalcare.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.animalcare.domain.User;
import ru.animalcare.repository.UserRepository;
import ru.animalcare.service.AnimalService;

import java.security.Principal;
import java.util.NoSuchElementException;

@Controller
@RequiredArgsConstructor
public class IndexPageController {

    private final UserRepository userRepository;

    @GetMapping("/")
    public String indexPage(Model model, Principal principal) {
        if (principal != null) {
            //TODO переделать на UserService
            User user = userRepository.findByUsername(principal.getName()).orElseThrow(NoSuchElementException::new);
            String fio = user.getFirstName() + " " + user.getLastName();
            model.addAttribute("fio", fio);
        }
        return "main";
    }

    @GetMapping("/account")
    public String showAccount(Model model, Principal principal) {
        if (principal != null) {
            model.addAttribute("fio", principal.getName());
        }
        return "account";
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
