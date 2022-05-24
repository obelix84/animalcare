package ru.animalcare.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;
import ru.animalcare.domain.Animal;
import ru.animalcare.dto.AnimalDto;
import ru.animalcare.service.AnimalGenderService;
import ru.animalcare.service.AnimalService;
import ru.animalcare.service.AnimalTypeService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/animals")
public class AnimalController {
    private final AnimalService animalService;
    private final AnimalTypeService animalTypeService;
    private final AnimalGenderService animalGenderService;

    @GetMapping
    public String showAllAnimals(Model model) {
        List<AnimalDto> animals = animalService.findAll();
        model.addAttribute("animals", animals);
        return "all_animals";
    }

    @GetMapping("/{id}")
    public String showAnimalById(Model model, @PathVariable Long id) {
        Animal currentAnimal = animalService.findAnimalById(id);
        model.addAttribute("current_animal", currentAnimal);
        return "profile_animal";
    }

    //  @PreAuthorize("hasAuthority({'ROLE_ADMIN', 'ROLE_MANAGER'})")
    @GetMapping("/add")
    public String showAnimalAddForm(Model model) {
        model.addAttribute("animal", new AnimalDto());
        model.addAttribute("animalTypes", animalTypeService.findAllAnimalTypes());
        model.addAttribute("animalGenders", animalGenderService.findAllAnimalGenders());
        return "animals_add";
    }

    @PostMapping("/add")
    public String addNewAnimal(@Valid AnimalDto animalDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "main";
        }
        animalService.addNewAnimal(animalDto);

        return showAllAnimals(model);
    }

}
