package ru.animalcare.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.animalcare.domain.Animal;
import ru.animalcare.domain.TypeOfAnimal;
import ru.animalcare.dto.AnimalDto;
import ru.animalcare.dto.TypeOfAnimalDto;
import ru.animalcare.repository.AnimalRepository;
import ru.animalcare.repository.TypeRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.NoSuchElementException;


@Service
@RequiredArgsConstructor
public class AnimalService {
    private final AnimalRepository animalRepository;
    private final TypeRepository typeRepository;

    public List<Animal> findAll() {
        return animalRepository.findAll();
    }

    public Animal findAnimalById(Long id) {
        return animalRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Animal for ID: " + id + " not found"));
    }

    public void save(Animal animal) {
        try {
            animalRepository.save(animal);
        } catch (NoSuchElementException e) {
            throw new EntityNotFoundException("Animal entity no found by id: " + animal.getId());
        }
    }

    public boolean deleteById(Long id) {
        try {
            animalRepository.deleteById(id);
            return true;
        } catch (NoSuchElementException e) {
            throw new EntityNotFoundException("Animal entity no found by id: " + id);
        }
    }

    public boolean saveOrUpdate(Long animalId) {
        Animal animal = findAnimalById(animalId);
        if (animal == null) {
            return false;
        }
        save(animal);
        return true;
    }


    public void add(AnimalDto animalDto) {
        Animal animal = new Animal();
        animal.setName(animalDto.getName());
        animal.setGender(animalDto.getGender());
        animal.setAge(animalDto.getAge());
        animal.setCondition(animalDto.getCondition());
        animal.setDescription(animalDto.getDescription());
        TypeOfAnimal type = typeRepository.findByName(
                animalDto.getTypeOfAnimal())
                .orElseThrow(() -> new NoSuchElementException("ERROR type name"));


    }
}
