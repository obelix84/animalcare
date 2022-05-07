package ru.animalcare.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.animalcare.domain.TypeOfAnimal;

@Repository
public interface TypeRepository extends PagingAndSortingRepository<TypeOfAnimal, Long> {
}

