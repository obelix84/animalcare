package ru.animalcare.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.animalcare.domain.Animal;
import ru.animalcare.domain.TypeOfAnimal;
import ru.animalcare.service.AnimalService;
import ru.animalcare.service.TypeService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class AnimalController {
    private final AnimalService animalService;
    private final TypeService typeService;

    @GetMapping("/all")
    public List<Animal> getAllAnimals() {
        return animalService.findAll();
    }

    @GetMapping("/animal_id")
    public Animal getAnimalById(@RequestParam Long id) {
        return animalService.findAnimalById(id);
    }

    @PostMapping
    public String save(@RequestParam(value = "animal", required = false) Animal animal,
                       @RequestParam(value = "typeOfAnimal", required = false) TypeOfAnimal typeOfAnimal,
                       BindingResult result) {
        if (result.hasErrors()) {
            return "/animals-add";
        }
        animalService.save(animal);
        return "redirect:/main";
    }

  //  @PreAuthorize("hasAuthority({'ROLE_ADMIN', 'ROLE_MANAGER'})")
    @GetMapping("/animals-add")
    public String saveForm(Model model) {
        List<TypeOfAnimal> typeOfAnimals = new ArrayList<>();
        typeService.findAll().forEach(typeOfAnimals ::add);
        model.addAttribute("animals", new Animal());
        model.addAttribute("typeOfAnimal", typeOfAnimals);

        return "/animals-add";
    }
}
