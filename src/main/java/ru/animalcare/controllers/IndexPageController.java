package ru.animalcare.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.animalcare.models.Animal;
import ru.animalcare.services.AnimalService;

import java.util.List;

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

    @GetMapping("/authenticated")
    public String privatePage() {
        return "authenticated";
    }

    @GetMapping("/all_animals")
    public String allAnimals(Model model) {
        List<Animal> animals = animalService.findAll();
        model.addAttribute("animals", animals);
        return "all_animals";
    }

}
