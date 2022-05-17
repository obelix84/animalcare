package ru.animalcare.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
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

    @PostMapping("/animals-add")
    public String save(@RequestParam(value = "animals", required = false) Animal animal,
                       @RequestParam(value = "type", required = false) TypeOfAnimal type,
                       BindingResult result) {
        if (result.hasErrors()) {
            return "/animals-add";
        }
        animalService.save(animal);
        return "redirect:/main";
    }

  //  @PreAuthorize("hasAuthority({'ROLE_ADMIN', 'ROLE_MANAGER'})")

    @GetMapping("/animals-add")
    public String displayingTheAnimals(Model model) {
        List<TypeOfAnimal> type = new ArrayList<>();
        typeService.findAll().forEach(type::add);
        model.addAttribute("animals",new Animal() );
        model.addAttribute("type", type);

        return "/animals-add";
    }
}
