package ru.animalcare.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.animalcare.domain.Animal;
import ru.animalcare.dto.AnimalDto;
import ru.animalcare.dto.UserDto;
import ru.animalcare.service.AnimalService;
import ru.animalcare.service.UserService;

import java.security.Principal;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

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

    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN', 'ROLE_MANAGER')")
    @GetMapping
    public String showProfile(Model model, @ModelAttribute("userDto") UserDto user) {
        if (user != null) {
            model.addAttribute("countActive", animalService.countActiveAnimalsByUserId(user.getId()));
            model.addAttribute("countInActive", animalService.countInActiveAnimalsByUserId(user.getId()));
        }
        return "user_profile";
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN', 'ROLE_MANAGER')")
    @GetMapping("/ads")
    public String showAds(Model model, @ModelAttribute("userDto") UserDto user, @RequestParam(name = "active", required = false) Boolean active,
                          @RequestParam(name = "p", required = false) Integer page,
                          @RequestParam(name = "l", required = false) Integer pageSize) {
        if (pageSize == null) {
            //ToDo: вынести в конфиг
            pageSize = 4;
        }
        if (page == null) {
            page = 0;
        }
        if (active == null) {
            active = true;
        }
        if (user != null) {
            model.addAttribute("active", active);
            long count = 0;
            if (active) {
                count = animalService.countActiveAnimalsByUserId(user.getId());
            } else {
                count = animalService.countInActiveAnimalsByUserId(user.getId());
            }
            long mod = count % pageSize;
            long countOfPages = (count - mod) / pageSize;
            if (mod != 0) {
                countOfPages++;
            }
            List<Long> range = LongStream.range(0, countOfPages).boxed().toList();
            model.addAttribute("currentPage", page);
            model.addAttribute("animals", animalService.findPagedAnimalsByUserIdAndActive(active ,
                    user.getId(), page, pageSize));
            model.addAttribute("links", range);
            model.addAttribute("limit", pageSize);
            model.addAttribute("max", countOfPages + 1);
        }
        return "user_ads";
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN', 'ROLE_MANAGER')")
    @GetMapping("/ads/arc/{id}")
    public String archiveAd(Model model, @PathVariable long id, @ModelAttribute("userDto") UserDto user) {
        this.animalService.archiveAd(id);
        return this.showAds(model, user, null, null, null);
    }

}
