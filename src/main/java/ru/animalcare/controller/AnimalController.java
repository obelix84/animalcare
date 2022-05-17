package ru.animalcare.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.animalcare.domain.Animal;
import ru.animalcare.domain.Photo;
import ru.animalcare.domain.TypeOfAnimal;
import ru.animalcare.service.AnimalService;
import ru.animalcare.service.PhotoService;
import ru.animalcare.service.TypeService;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class AnimalController {
    private final AnimalService animalService;
    private final TypeService typeService;
   // private final PhotoService service;

    @GetMapping("/all")
    public List<Animal> getAllAnimals() {
        return animalService.findAll();
    }

    @GetMapping("/animal_id")
    public Animal getAnimalById(@RequestParam Long id) {
        return animalService.findAnimalById(id);
    }



    @PostMapping("/animals-add")
    public String save(//@ModelAttribute Photo photo, Model model,
                       @RequestParam(value = "animals", required = false) Animal animal,
                       @RequestParam(value = "type", required = false) TypeOfAnimal type,
                       BindingResult result) {
        if (result.hasErrors()) {
            return "/animals-add";
        }

        animalService.save(animal);
       // model.addAttribute("photo",photo);
        return "redirect:/main";
    }

  //  @PreAuthorize("hasAuthority({'ROLE_ADMIN', 'ROLE_MANAGER'})")

    @GetMapping("/animals-add")
    public String displayingTheAnimals(Model model) {
        List<TypeOfAnimal> types = new ArrayList<>();
        typeService.findAll().forEach(types::add);

        model.addAttribute("animals",new Animal());
        model.addAttribute("types", types);

        return "animals-add";
    }
}
