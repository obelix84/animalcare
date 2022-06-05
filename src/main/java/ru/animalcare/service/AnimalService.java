package ru.animalcare.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.animalcare.domain.*;
import ru.animalcare.dto.AnimalDto;
import ru.animalcare.dto.AnimalRegistrationDto;
import ru.animalcare.dto.UserDto;
import ru.animalcare.repository.AnimalRepository;

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
    private final UserService userService;

    public Long countActiveAnimalsByUserId(Long id){
        return animalRepository.countAnimalsByUserIdAndActiveTrue(id);
    }

    public Long countInActiveAnimalsByUserId(Long id){
        return animalRepository.countAnimalsByUserIdAndActiveFalse(id);
    }

    public List<AnimalDto> findAllAnimals(){
        return animalRepository.findAll()
                .stream()
                .map(AnimalDto::new)
                .collect(Collectors.toList());
    }

    public List<AnimalDto> findAnimalsById(Long id){
        return animalRepository.findAnimalsByUserId(id)
                .stream()
                .map(AnimalDto::new)
                .collect(Collectors.toList());
    }

    public List<AnimalDto> findAll(){
        return animalRepository.findAll()
                .stream()
                .map(AnimalDto::new)
                .collect(Collectors.toList());
    }

    public Animal findAnimalById(Long id) {
        return animalRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Animal for ID: " + id + " not found"));
    }

    @Transactional
    public AnimalDto addNewAnimal(AnimalRegistrationDto animalRegistrationDto, String username) {
        Animal animal = new Animal();
        animal.setName(animalRegistrationDto.getName());

        AnimalGender animalGender = animalGenderService.findAnimalGenderByName(animalRegistrationDto.getGender())
                .orElseThrow(() -> new RuntimeException(String.format("Animal gender '%s' not found\n", animalRegistrationDto.getGender())));
        animal.setAnimalGender(animalGender);

        animal.setAge(animalRegistrationDto.getAge());
        animal.setCondition(animalRegistrationDto.getCondition());
        animal.setDescription(animalRegistrationDto.getDescription());
        animal.setActive(true);

//  todo подумать, нужно ли возвращать UserDto из метода findUserByName, чтобы несколько раз поиск не проводить, если нужна сущность User
        UserDto userDto = userService.findUserByName(username);
        User user = userService.findUserById(userDto.getId());
        if(user != null){
            animal.setUser(user);
        }

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

        return new AnimalDto(animalRepository.save(animal));
    }

    @Transactional
    public AnimalDto updateAnimal(Long id, AnimalRegistrationDto animalRegistrationDto) {
        Animal animal = findAnimalById(id);
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
        }

        return new AnimalDto(animalRepository.save(animal));
    }

}
