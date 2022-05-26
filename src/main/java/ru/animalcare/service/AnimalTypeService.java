package ru.animalcare.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.animalcare.domain.AnimalType;
import ru.animalcare.dto.AnimalTypeDto;
import ru.animalcare.repository.AnimalTypeRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnimalTypeService {
    private final AnimalTypeRepository animalTypeRepository;

    public List<AnimalTypeDto> findAllAnimalTypes() {
        return animalTypeRepository.findAll()
                .stream()
                .map(AnimalTypeDto::new)
                .collect(Collectors.toList());

    }

    public AnimalType findById(Long id) {
        try {
            return animalTypeRepository.findById(id).orElseThrow();
        } catch (NoSuchElementException e) {
            throw new EntityNotFoundException("type animal no found by id: " + id);
        }
    }

    public Optional<AnimalType> findTypeAnimalByName(String name) {
        return animalTypeRepository.findByName(name);
    }

    public boolean deleteById(Long id) {
        try {
            animalTypeRepository.deleteById(id);
            return true;
        } catch (NoSuchElementException e) {
            throw new EntityNotFoundException("type animal no found by id: " + id);
        }
    }

    public void saveOrUpdate(AnimalType typeOfAnimal) {

        try {
            animalTypeRepository.save(typeOfAnimal);
        } catch (NoSuchElementException e) {
            throw new EntityNotFoundException("tape animal no found by id: " + typeOfAnimal.getId());
        }
    }

    // удалить
    public boolean saveOrUpdate(Long typeId) {
        AnimalType typeOfAnimal = findById(typeId);
        if (typeOfAnimal == null) {
            return false;
        }
        saveOrUpdate(typeOfAnimal);
        return true;
    }
}
