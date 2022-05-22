package ru.animalcare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.animalcare.domain.TypeOfAnimal;

import java.util.Optional;

@Repository
public interface TypeRepository extends JpaRepository<TypeOfAnimal, Long> {

    Optional<TypeOfAnimal> findByName(String typename);
}

