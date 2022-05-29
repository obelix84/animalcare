package ru.animalcare.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.animalcare.domain.Animal;
import ru.animalcare.domain.AnimalGender;
import ru.animalcare.domain.AnimalPhoto;
import ru.animalcare.domain.AnimalType;
import ru.animalcare.dto.AnimalDto;
import ru.animalcare.dto.AnimalPhotoDto;
import ru.animalcare.dto.AnimalRegistrationDto;
import ru.animalcare.repository.AnimalRepository;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static ru.animalcare.common.Settings.ANIMAL_PHOTO_DEFAULT;


@Service
@RequiredArgsConstructor
public class AnimalService {
    private final AnimalRepository animalRepository;
    private final AnimalTypeService animalTypeService;
    private final AnimalGenderService animalGenderService;
    private final AnimalPhotoService animalPhotoService;

    public List<AnimalDto> findAll() {
        return animalRepository.findAll()
                .stream()
                .map(AnimalDto::new)
                .collect(Collectors.toList());
    }

    public Animal findAnimalById(Long id) {
        return animalRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Animal for ID: " + id + " not found"));
    }

//    public boolean deleteById(Long id) {
//        try {
//            animalRepository.deleteById(id);
//            return true;
//        } catch (NoSuchElementException e) {
//            throw new EntityNotFoundException("Animal entity no found by id: " + id);
//        }
//    }

//    public void addNewAnimal(AnimalDto animalDto){
//        Animal animal = new Animal();
//        animal.setName(animalDto.getName());
//
//        AnimalGender animalGender = animalGenderService.findAnimalGenderByName(animalDto.getGender())
//                .orElseThrow(() -> new RuntimeException(String.format("Animal gender '%s' not found\n", animalDto.getGender())));
//        animal.setAnimalGender(animalGender);
//
//        animal.setAge(animalDto.getAge());
//        animal.setCondition(animalDto.getCondition());
//        animal.setDescription(animalDto.getDescription());
//
//        AnimalType animalType = animalTypeService.findTypeAnimalByName(animalDto.getType())
//                .orElseThrow(() -> new RuntimeException(String.format("Animal type '%s' not found\n", animalDto.getType())));
//        animal.setAnimalType(animalType);
//
//        animalRepository.save(animal);
//    }

    public void addNewAnimal(AnimalRegistrationDto animalRegistrationDto) {
        Animal animal = new Animal();
        animal.setName(animalRegistrationDto.getName());

        AnimalGender animalGender = animalGenderService.findAnimalGenderByName(animalRegistrationDto.getGender())
                .orElseThrow(() -> new RuntimeException(String.format("Animal gender '%s' not found\n", animalRegistrationDto.getGender())));
        animal.setAnimalGender(animalGender);

        animal.setAge(animalRegistrationDto.getAge());
        animal.setCondition(animalRegistrationDto.getCondition());
        animal.setDescription(animalRegistrationDto.getDescription());

        AnimalType animalType = animalTypeService.findTypeAnimalByName(animalRegistrationDto.getType())
                .orElseThrow(() -> new RuntimeException(String.format("Animal type '%s' not found\n", animalRegistrationDto.getType())));
        animal.setAnimalType(animalType);

        if (animalRegistrationDto.getMultipartFile().getSize() != 0) {
            AnimalPhoto animalPhoto = animalPhotoService.uploadAnimalPhotoToServer(animalRegistrationDto.getMultipartFile());
            animal.setAnimalPhotoList(new ArrayList<>(Arrays.asList(animalPhoto)));
        } else {
            AnimalPhoto animalPhoto = animalPhotoService.findAnimalPhotoByName(ANIMAL_PHOTO_DEFAULT);
            animal.setAnimalPhotoList(new ArrayList<>(Arrays.asList(animalPhoto)));
        }

        animalRepository.save(animal);
    }

}
