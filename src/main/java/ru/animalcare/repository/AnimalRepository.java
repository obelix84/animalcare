package ru.animalcare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.animalcare.domain.Animal;

import java.util.List;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long> {
    List<Animal> findAnimalsByUserId(Long id);
    Long countAnimalsByUserIdAndActiveTrue(Long id);
    Long countAnimalsByUserIdAndActiveFalse(Long id);
}
