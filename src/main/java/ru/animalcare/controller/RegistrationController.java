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
import ru.animalcare.service.imp.OTPService;
import ru.animalcare.validator.UserValidator;

import javax.naming.Binding;
import java.util.List;

@Controller
public class RegistrationController {

    private static final Logger logger = LoggerFactory.getLogger(RegistrationController.class);

    private final UserDetailsServiceImpl userService;
    private final UserValidator userValidator;

    private final EmailServiceImpl emailService;
    private final OTPService otpService;

    @Autowired
    public RegistrationController(@Qualifier("UserDetailsServiceImpl") UserDetailsServiceImpl userService, UserValidator userValidator, EmailServiceImpl emailService, OTPService otpService) {
        this.userService = userService;
        this.userValidator = userValidator;
        this.emailService = emailService;
        this.otpService = otpService;
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
            return "registration";
        }
        //Генерируем OTP(one time password)
        //String opt = String.valueOf(otpService.generateOTP(userForm.getUsername()));
        //отправляем email with OTP
       // emailService.sendEmail(userForm.getEmail(),opt);

        //нужно отправить все, что снизу в confirmEmail.
        Authority authority = new Authority();
        authority.setAuthority("USER");
        authority.setId(1L);
        userForm.setAuthorities(List.of(authority));
        userForm.setEnabled(true);
        userService.save(userForm);
        userService.loadUserByUsername(userForm.getUsername());
        return "redirect:/authenticated";
    }
//
//    @GetMapping("/confirmEmail")
//    public String confirmEmail(Model model){
//        model.addAttribute("OTP", new String());
//        return "confirmEmail";
//    }
//    @PostMapping("/confirmEmail")
//    public String confirmEmail(@ModelAttribute("OPT") String opt, BindingResult bindingResult){
//        //нужно проверить здесь код и после этого только создать аккаунт
//        return "redirect:/authenticated";
//    }
}