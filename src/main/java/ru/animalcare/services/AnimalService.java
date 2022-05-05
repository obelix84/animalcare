package ru.animalcare.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.animalcare.models.Animal;
import ru.animalcare.repositories.AnimalRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnimalService {
    private final AnimalRepository animalRepository;

    public List<Animal> findAll(){
        return animalRepository.findAll();
    }
}
