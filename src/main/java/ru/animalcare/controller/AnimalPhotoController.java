package ru.animalcare.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.animalcare.dto.AnimalPhotoDto;
import ru.animalcare.service.AnimalPhotoService;

@Controller
@RequiredArgsConstructor
public class AnimalPhotoController {
    private final AnimalPhotoService animalPhotoService;

//    @GetMapping("/upload")
//    public String showFormAddAnimalPhoto(Model model) {
//        model.addAttribute("photo", new AnimalPhotoDto());
//        return "photo_add";
//    }
//
//    @PostMapping("/upload")
//    public String uploadAnimalPhotoToServer(@RequestParam("file") MultipartFile multipartFile) {
//        AnimalPhotoDto animalPhotoDto = animalPhotoService.uploadAnimalPhotoToServer(multipartFile);
////        model.addAttribute()
//        return "all_animals";
//    }

}
