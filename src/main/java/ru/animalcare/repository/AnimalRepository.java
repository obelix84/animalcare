package ru.animalcare.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.animalcare.domain.Animal;

import java.util.List;

@Repository
public interface AnimalRepository extends PagingAndSortingRepository<Animal, Long> {
    List<Animal> findAnimalsByUserId(Long id);
    List<Animal> findAnimalsByUserIdAndActiveIsTrue(Long id, Pageable pageable);
    List<Animal> findAnimalsByUserIdAndActiveIsFalse(Long id, Pageable pageable);
    Long countAnimalsByUserIdAndActiveTrue(Long id);
    Long countAnimalsByUserIdAndActiveFalse(Long id);
}
