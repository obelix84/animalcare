package ru.animalcare.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.animalcare.dto.AnimalDto;
import ru.animalcare.dto.AnimalRegistrationDto;
import ru.animalcare.service.AnimalGenderService;
import ru.animalcare.service.AnimalPhotoService;
import ru.animalcare.service.AnimalService;
import ru.animalcare.service.AnimalTypeService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static ru.animalcare.common.Settings.PATH_TO_ANIMAL_PHOTO_DIRECTORY;

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

    @GetMapping("/add")
    public String showFormAddAnimalPhoto(Model model) {
        model.addAttribute("animalTypes", animalTypeService.findAllAnimalTypes());
        model.addAttribute("animalGenders", animalGenderService.findAllAnimalGenders());
        model.addAttribute("animalRegistration", new AnimalRegistrationDto());
        return "animal_add";
    }

    @PostMapping("/add")
    public String uploadAnimalPhotoToServer(@ModelAttribute AnimalRegistrationDto animalRegistrationDto) {
        animalService.addNewAnimal(animalRegistrationDto);
        return "redirect:/";
    }

    @GetMapping("/image/{imageName}")
    @ResponseBody
    public byte[] getImage(@PathVariable(value = "imageName") String imageName) throws IOException {
        File serverFile = new File(PATH_TO_ANIMAL_PHOTO_DIRECTORY + imageName + ".jpg");
        return Files.readAllBytes(serverFile.toPath());
    }

}
