package ru.animalcare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.animalcare.domain.Animal;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long> {
}
