package ru.animalcare.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.animalcare.dto.AnimalDto;
import ru.animalcare.dto.UserDto;
import ru.animalcare.service.AnimalService;
import ru.animalcare.service.UserService;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/profile")
public class UserProfileController {

    private final UserService userService;
    private final AnimalService animalService;

    @ModelAttribute(name = "userDto")
    public UserDto insertUserInMenu(Principal principal) {
        if (principal != null) {
            return userService.findUserByName(principal.getName());
        }
        return null;
    }

    @ModelAttribute(name = "animals")
    public List<AnimalDto> insertAnimals(@ModelAttribute("userDto") UserDto user) {
        if (user != null) {
            return animalService.findAnimalsById(user.getId());
        }
        return null;
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN', 'ROLE_MANAGER')")
    @GetMapping
    public String showProfile(Model model, @ModelAttribute("userDto") UserDto user) {
        if (user != null) {
            model.addAttribute("countActive", animalService.countActiveAnimalsByUserId(user.getId()));
            model.addAttribute("countInActive", animalService.countInActiveAnimalsByUserId(user.getId()));
        }
        return "user_profile";
    }

}
