package ru.animalcare.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.animalcare.domain.User;
import ru.animalcare.dto.UserDto;
import ru.animalcare.repository.UserRepository;
import ru.animalcare.service.UserService;

import java.security.Principal;
import java.util.NoSuchElementException;

@Controller
@RequiredArgsConstructor
public class IndexPageController {

    private final UserRepository userRepository;
    private final UserService userService;

    @GetMapping("/")
    public String indexPage(Model model, Principal principal) {
        if (principal != null) {
            UserDto userDto = userService.findUserByName(principal.getName());
            System.out.println("-------------------");
            System.out.println(userDto);
            String fio = userDto.getFirstName() + " " + userDto.getLastName();

            model.addAttribute("fio", fio);
        }
        return "main";
    }

    @GetMapping("/profile")
    public String showAccount(Model model, Principal principal) {
        if (principal != null) {
            model.addAttribute("fio", principal.getName());
        }
        return "user_profile";
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
