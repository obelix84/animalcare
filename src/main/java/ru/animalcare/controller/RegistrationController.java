package ru.animalcare.controller;

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
import ru.animalcare.service.imp.EmailServiceImpl;
import ru.animalcare.validator.UserValidator;

import java.util.List;

@Controller
public class RegistrationController {

    private static final Logger logger = LoggerFactory.getLogger(RegistrationController.class);
    private final UserDetailsServiceImpl userService;
    private final UserValidator userValidator;
    private final EmailServiceImpl emailService;


    @Autowired
    public RegistrationController(@Qualifier("UserDetailsServiceImpl") UserDetailsServiceImpl userService, UserValidator userValidator,
                                  EmailServiceImpl emailService) {
        this.userService = userService;
        this.userValidator = userValidator;
        this.emailService = emailService;
    }

    @GetMapping("/register")
    public String registration(Model model) {
        model.addAttribute("userForm", new User());

        return "registration";
    }

    @PostMapping("/register")
    public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult) {
        userValidator.validate(userForm, bindingResult);
        if (bindingResult.hasErrors()) {
            logger.error(String.valueOf(bindingResult.getFieldError()));
            return "/registration";
        }
        Authority authority = new Authority();
        authority.setAuthority("USER");
        authority.setId(1L);
        userForm.setAuthorities(List.of(authority));
        userForm.setEnabled(true);
        emailService.sendEmail("<receiver email>","<OTP>");
        if (userService.save(userForm)) {
            userService.loadUserByUsername(userForm.getEmail());
            return "redirect:/register/confirm";
        } else
            return "registration";
    }

    @GetMapping("/register/confirm")
    public String confirm(Model model){
        model.addAttribute("OTP", "");
        return "confirmEmail";
    }

    @PostMapping("/register/confirm")
    public String confirm(@ModelAttribute("OTP") String otp, BindingResult bindingResult){
        return "redirect:/profile";
    }
}