package ru.animalcare.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.animalcare.domain.AnimalGender;
import ru.animalcare.dto.AnimalGenderDto;
import ru.animalcare.repository.AnimalGenderRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnimalGenderService {
    private final AnimalGenderRepository animalGenderRepository;

    public List<AnimalGenderDto> findAllAnimalGenders() {
        return animalGenderRepository.findAll()
                .stream()
                .map(AnimalGenderDto::new)
                .collect(Collectors.toList());
    }

    public Optional<AnimalGender> findAnimalGenderByName(String name) {
        return animalGenderRepository.findByName(name);
    }
}
