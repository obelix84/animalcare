package ru.animalcare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.animalcare.domain.AnimalGender;

import java.util.Optional;

@Repository
public interface AnimalGenderRepository extends JpaRepository<AnimalGender, Long> {
    Optional<AnimalGender> findByName(String name);
}
