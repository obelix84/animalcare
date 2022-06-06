package ru.animalcare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.animalcare.domain.AnimalPhoto;

import java.util.Optional;

@Repository
public interface AnimalPhotoRepository extends JpaRepository<AnimalPhoto, Long> {
    Optional<AnimalPhoto> findByName(String name);
}
