package ru.animalcare.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.animalcare.models.Animal;
import ru.animalcare.services.AnimalService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AnimalController {
    private final AnimalService animalService;

    @GetMapping("/all")
    public List<Animal> getAllAnimals(){
        return animalService.findAll();
    }
}
