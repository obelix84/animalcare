package ru.animalcare.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.animalcare.domain.Animal;
import ru.animalcare.service.AnimalService;

import java.util.List;

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

    @GetMapping("/all_animals")
    public String allAnimals(Model model) {
        List<Animal> animals = animalService.findAll();
        model.addAttribute("animals", animals);
        return "all_animals";
    }

    // todo: доработать
//    @GetMapping("/profile_animal")
//    public String profileAnimal(Model model, @RequestParam Long id){
//        Animal currentAnimal = animalService.findAnimalById(id);
//        model.addAttribute("current_animal", currentAnimal);
//        return "redirect: profile_animal";
//    }

//    @GetMapping("/profile_animal")
//    public String profileAnimal(){
//        return "redirect:/profile_animal";
//    }


    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }


}
