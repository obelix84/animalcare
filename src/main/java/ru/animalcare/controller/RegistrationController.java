package ru.animalcare.controller;


import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.animalcare.domain.Authority;
import ru.animalcare.domain.User;
import ru.animalcare.service.UserDetailsServiceImpl;
import ru.animalcare.validator.UserValidator;

import java.util.Arrays;

@Controller
public class RegistrationController {

    private static final Logger logger = LoggerFactory.getLogger(RegistrationController.class);
    private final UserDetailsServiceImpl userService;
    private final UserValidator userValidator;

    @Autowired
    public RegistrationController(@Qualifier("UserDetailsServiceImpl") UserDetailsServiceImpl userService, UserValidator userValidator) {
        this.userService = userService;
        this.userValidator = userValidator;
    }

    @GetMapping("/register")
    public String registration(Model model) {
        model.addAttribute("userForm", new User());

        return "register";
    }

    @PostMapping("/register")
    public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult) {
        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            logger.error(String.valueOf(bindingResult.getFieldError()));
            return "register";
        }
        Authority authority = new Authority();
        authority.setAuthority("USER");
        //authority.setId(1L);
        userForm.setAuthorities(Arrays.asList(authority));
        userService.save(userForm);
        userService.loadUserByUsername(userForm.getUsername());
        return "redirect:/";
    }
}