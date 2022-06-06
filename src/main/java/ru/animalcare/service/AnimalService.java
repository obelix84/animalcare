package ru.animalcare.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.animalcare.domain.Animal;
import ru.animalcare.domain.AnimalGender;
import ru.animalcare.domain.AnimalPhoto;
import ru.animalcare.domain.AnimalType;
import ru.animalcare.domain.paging.Paged;
import ru.animalcare.domain.paging.Paging;
import ru.animalcare.dto.AnimalDto;
import ru.animalcare.dto.AnimalRegistrationDto;
import ru.animalcare.repository.AnimalRepository;
import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static ru.animalcare.common.Settings.ANIMAL_PHOTO_DEFAULT;


@Service
@RequiredArgsConstructor
public class AnimalService {

    private final AnimalRepository animalRepository;
    private final AnimalTypeService animalTypeService;
    private final AnimalGenderService animalGenderService;
    private final AnimalPhotoService animalPhotoService;

    public Long countActiveAnimalsByUserId(Long id){
        return animalRepository.countAnimalsByUserIdAndActiveTrue(id);
    }

    public Long countInActiveAnimalsByUserId(Long id){
        return animalRepository.countAnimalsByUserIdAndActiveFalse(id);
    }

    public List<AnimalDto> findAllAnimals(){
        return StreamSupport.stream(animalRepository.findAll().spliterator(), false)
                .map(AnimalDto::new)
                .collect(Collectors.toList());
    }

    public List<AnimalDto> findPagedAnimalsByUserIdAndActive(boolean active, long userId, int startElement, int maxElementsCount) {
        if (active) {
            return animalRepository.findAnimalsByUserIdAndActiveIsTrue(userId, PageRequest.of(startElement, maxElementsCount)).stream().map(AnimalDto::new)
                    .collect(Collectors.toList());
        }
        return animalRepository.findAnimalsByUserIdAndActiveIsFalse(userId, PageRequest.of(startElement, maxElementsCount)).stream().map(AnimalDto::new)
                .collect(Collectors.toList());
    }


    public Animal findAnimalById(Long id) {
        return animalRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Animal for ID: " + id + " not found"));
    }

    public boolean deleteById(Long id) {
        try {
            animalRepository.deleteById(id);
            return true;
        } catch (NoSuchElementException e) {
            throw new EntityNotFoundException("Animal entity no found by id: " + id);
        }
    }

    public long getCount() {
        return animalRepository.count();
    }


    @Transactional
    public AnimalDto addNewAnimal(AnimalRegistrationDto animalRegistrationDto) {
        Animal animal = new Animal();
        animal.setName(animalRegistrationDto.getName());

        AnimalGender animalGender = animalGenderService.findAnimalGenderByName(animalRegistrationDto.getGender())
                .orElseThrow(() -> new RuntimeException(String.format("Animal gender '%s' not found\n", animalRegistrationDto.getGender())));
        animal.setAnimalGender(animalGender);

        animal.setAge(animalRegistrationDto.getAge());
        animal.setCondition(animalRegistrationDto.getCondition());
        animal.setDescription(animalRegistrationDto.getDescription());
        animal.setActive(true);
//        todo переделать на поиск юзера по id
        animal.setUser(null);

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

    public Paged<AnimalDto> getPage(int pageNumber, int size) {

        PageRequest request = PageRequest.of(pageNumber - 1, size, Sort.by(Sort.Direction.ASC, "id"));

        Page<AnimalDto> animalPage = new PageImpl<>(animalRepository.findAll(request)
                .stream()
                .map(AnimalDto::new)
                .collect(Collectors.toList())
                , request
                , findAllAnimals().size());
        return new Paged<>(animalPage, Paging.of(animalPage.getTotalPages(), pageNumber, size));
    }
}
