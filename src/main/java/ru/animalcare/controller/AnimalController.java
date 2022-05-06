package ru.animalcare.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.animalcare.domain.Animal;
import ru.animalcare.service.AnimalService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AnimalController {
    private final AnimalService animalService;

    @GetMapping("/all")
    public List<Animal> getAllAnimals(){
        return animalService.findAll();
    }

    @GetMapping("/animal_id")
    public Animal getAnimalById(@RequestParam Long id){
        return animalService.findAnimalById(id);
    }
}
