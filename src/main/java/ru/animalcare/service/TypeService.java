package ru.animalcare.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.animalcare.domain.TypeOfAnimal;
import ru.animalcare.repository.TypeRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class TypeService {
    private final TypeRepository typeRepository;

    public List<TypeOfAnimal> findAll() {
        return typeRepository.findAll();
    }
    public Optional<TypeOfAnimal> findTypeAnimalByName(String name) {
        return typeRepository.findByName(name);
    }

    public TypeOfAnimal findById(long id) {
        try {
            return typeRepository.findById(id).orElseThrow();
        } catch (NoSuchElementException e) {
            throw new EntityNotFoundException("type animal no found by id: " + id);
        }
    }

    public boolean deleteById(Long id) {
        try {
            typeRepository.deleteById(id);
            return true;
        } catch (NoSuchElementException e) {
            throw new EntityNotFoundException("type animal no found by id: " + id);
        }
    }

    public void saveOrUpdate(TypeOfAnimal typeOfAnimal) {

        try {
            typeRepository.save(typeOfAnimal);
        } catch (NoSuchElementException e) {
            throw new EntityNotFoundException("tape animal no found by id: " + typeOfAnimal.getId());
        }
    }

    // удалить
    public boolean saveOrUpdate(Long typeId) {
        TypeOfAnimal typeOfAnimal = findById(typeId);
        if (typeOfAnimal == null) {
            return false;
        }
        saveOrUpdate(typeOfAnimal);
        return true;
    }
}
