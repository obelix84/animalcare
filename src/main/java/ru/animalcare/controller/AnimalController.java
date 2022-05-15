package ru.animalcare.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.animalcare.domain.Animal;
import ru.animalcare.service.AnimalService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/animals")
public class AnimalController {
    private final AnimalService animalService;

    @GetMapping
    public String allAnimals(Model model) {
        List<Animal> animals = animalService.findAll();
        model.addAttribute("animals", animals);
        return "all_animals";
    }

    @GetMapping("/{id}")
    public String profileAnimal(Model model, @PathVariable Long id){
        Animal currentAnimal = animalService.findAnimalById(id);
        model.addAttribute("current_animal", currentAnimal);
        return "profile_animal";
    }

}
