package ru.animalcare.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.animalcare.models.Animal;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long> {
}
