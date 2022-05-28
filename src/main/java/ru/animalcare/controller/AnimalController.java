package ru.animalcare.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;
import ru.animalcare.domain.Animal;
import ru.animalcare.dto.AnimalDto;
import ru.animalcare.dto.AnimalPhotoDto;
import ru.animalcare.service.AnimalGenderService;
import ru.animalcare.service.AnimalPhotoService;
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
    private final AnimalPhotoService animalPhotoService;

    @GetMapping
    public String showAllAnimals(Model model) {
        List<AnimalDto> animals = animalService.findAll();
        model.addAttribute("animals", animals);
        return "all_animals";
    }

    @GetMapping("/{id}")
    public String showAnimalById(Model model, @PathVariable Long id) {
        AnimalDto animalDto = new AnimalDto(animalService.findAnimalById(id));
        model.addAttribute("current_animal", animalDto);
        return "profile_animal";
    }

    //  @PreAuthorize("hasAuthority({'ROLE_ADMIN', 'ROLE_MANAGER'})")
    @GetMapping("/add")
    public String showFormAddAnimal(Model model) {
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

//    @GetMapping("/photo_add/{id}")
//    public String showFormAddAnimalPhoto(@PathVariable Long id, Model model) {
//        AnimalDto animalDto = new AnimalDto(animalService.findAnimalById(id));
//        model.addAttribute("current_animal", animalDto);
//        model.addAttribute("photo", new AnimalPhotoDto());
//        return "photo_add";
//    }

    @GetMapping("/photo_add")
    public String showFormAddAnimalPhoto(Model model) {
//        AnimalDto animalDto = new AnimalDto(animalService.findAnimalById(id));
//        model.addAttribute("current_animal", animalDto);
        model.addAttribute("photo", new AnimalPhotoDto());
        return "photo_add";
    }

    @PostMapping("/photo_add")
    public String uploadAnimalPhotoToServer(@RequestParam("file") MultipartFile multipartFile) {
        AnimalPhotoDto animalPhotoDto = animalPhotoService.uploadAnimalPhotoToServer(multipartFile);
        return "all_animals";
    }

}
