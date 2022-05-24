package ru.animalcare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.animalcare.domain.AnimalType;

import java.util.Optional;

@Repository
public interface AnimalTypeRepository extends JpaRepository<AnimalType, Long> {
    Optional<AnimalType> findByName(String name);
}

