package ru.animalcare.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;
import ru.animalcare.dto.AnimalDto;
import ru.animalcare.dto.AnimalRegistrationDto;
import ru.animalcare.service.AnimalGenderService;
import ru.animalcare.service.AnimalPhotoService;
import ru.animalcare.service.AnimalService;
import ru.animalcare.service.AnimalTypeService;


import javax.validation.Valid;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

//    @GetMapping
//    public String showAllAnimals(Model model) {
//        List<AnimalDto> animals = animalService.findAll();
//        model.addAttribute("animals", animals);
//        return "all_animals";
//    }

    @GetMapping
    public String showAllAnimalsPage(@RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
                                 @RequestParam(value = "size", required = false, defaultValue = "3") int size, Model model) {
        model.addAttribute("animals", animalService.getPage(pageNumber,size));
        return "all_animal";
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

//        return "animals_add";
//        return "add_animals";

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

    @GetMapping("/download/{imageName}")
    public ResponseEntity downloadFileFromLocal(@PathVariable String imageName) {
        Path path = Paths.get(PATH_TO_ANIMAL_PHOTO_DIRECTORY + imageName + ".jpg");
        Resource resource = null;
        try {
            resource = new UrlResource(path.toUri());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

   //  return showAllAnimals(model);
    //    return showAllAnimalsPage(1,3, model);


    @GetMapping("/get/{imageName}")
    public ResponseEntity<Resource> getFileFromLocal(@PathVariable String imageName) {
        Path path = Paths.get(PATH_TO_ANIMAL_PHOTO_DIRECTORY + imageName + ".jpg");
        Resource resource = null;
        try {
            resource = new UrlResource(path.toUri());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok()
                .contentLength(path.toFile().length())
                //вот тут надо чего сделать
                .contentType(MediaType.IMAGE_JPEG)
                .body(resource);
    }


}
