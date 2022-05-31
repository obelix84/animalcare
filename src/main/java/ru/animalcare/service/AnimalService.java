package ru.animalcare.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import ru.animalcare.domain.Animal;
import ru.animalcare.domain.AnimalGender;
import ru.animalcare.domain.AnimalType;
import ru.animalcare.domain.paging.Paged;
import ru.animalcare.domain.paging.Paging;
import ru.animalcare.dto.AnimalDto;
import ru.animalcare.repository.AnimalRepository;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class AnimalService {

    private final AnimalRepository animalRepository;
    private final AnimalTypeService animalTypeService;
    private final AnimalGenderService animalGenderService;

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

    public boolean deleteById(Long id) {
        try {
            animalRepository.deleteById(id);
            return true;
        } catch (NoSuchElementException e) {
            throw new EntityNotFoundException("Animal entity no found by id: " + id);
        }
    }

//    public boolean saveOrUpdate(Long animalId) {
//        Animal animal = findAnimalById(animalId);
//        if (animal == null) {
//            return false;
//        }
//        save(animal);
//        return true;
//    }

    public void addNewAnimal(AnimalDto animalDto) {
        Animal animal = new Animal();
        animal.setName(animalDto.getName());

        AnimalGender animalGender = animalGenderService.findAnimalGenderByName(animalDto.getGender())
                .orElseThrow(() -> new RuntimeException(String.format("Animal gender '%s' not found\n", animalDto.getGender())));
        animal.setAnimalGender(animalGender);

        animal.setAge(animalDto.getAge());
        animal.setCondition(animalDto.getCondition());
        animal.setDescription(animalDto.getDescription());

        AnimalType animalType = animalTypeService.findTypeAnimalByName(animalDto.getType())
                .orElseThrow(() -> new RuntimeException(String.format("Animal type '%s' not found\n", animalDto.getType())));
        animal.setAnimalType(animalType);

        animalRepository.save(animal);
    }

    public Paged<AnimalDto> getPage(int pageNumber, int size) {

        PageRequest request = PageRequest.of(pageNumber - 1, size, Sort.by(Sort.Direction.ASC, "id"));

        Page<AnimalDto> animalPage = new PageImpl<>(animalRepository.findAll(request)
                .stream()
                .map(AnimalDto::new)
                .collect(Collectors.toList())
                , request
                , findAll().size());
        return new Paged<>(animalPage, Paging.of(animalPage.getTotalPages(), pageNumber, size));
    }
}
