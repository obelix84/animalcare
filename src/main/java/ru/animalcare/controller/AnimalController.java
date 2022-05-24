package ru.animalcare.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;
import ru.animalcare.domain.Animal;
import ru.animalcare.dto.AnimalDto;
import ru.animalcare.dto.TypeOfAnimalDto;
import ru.animalcare.service.AnimalService;
import ru.animalcare.service.TypeService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/animals")
public class AnimalController {
    private final AnimalService animalService;
    private final TypeService typeService;

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
        TypeOfAnimalDto typeOfAnimalDto = new TypeOfAnimalDto(typeService.findAll());
        model.addAttribute("animal", new AnimalDto());
        model.addAttribute("types", typeOfAnimalDto.getAnimalTypes());
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
