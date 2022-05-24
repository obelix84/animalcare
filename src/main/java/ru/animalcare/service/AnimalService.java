package ru.animalcare.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.animalcare.domain.Animal;
import ru.animalcare.domain.TypeOfAnimal;
import ru.animalcare.dto.AnimalDto;
import ru.animalcare.repository.AnimalRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class AnimalService {
    private final AnimalRepository animalRepository;
    private final TypeService typeService;

    public List<AnimalDto> findAll(){
        return animalRepository.findAll()
                .stream()
                .map(AnimalDto::new)
                .collect(Collectors.toList());
    }

    public Animal findAnimalById(Long id){
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

    public void addNewAnimal(AnimalDto animalDto){
        Animal animal = new Animal();
        animal.setName(animalDto.getName());
        animal.setGender(animalDto.getGender());
        animal.setAge(animalDto.getAge());
        animal.setCondition(animalDto.getCondition());
        animal.setDescription(animalDto.getDescription());

        TypeOfAnimal typeOfAnimal = typeService.findTypeAnimalByName(animalDto.getTypeOfAnimal())
                .orElseThrow(() -> new RuntimeException(String.format("Animal type '%s' not found\n", animalDto.getTypeOfAnimal())));
        animal.setTypeOfAnimal(typeOfAnimal);

        animalRepository.save(animal);
    }
}
