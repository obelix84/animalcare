package ru.animalcare.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.animalcare.domain.AnimalGender;

@Data
@NoArgsConstructor
public class AnimalGenderDto {
    private Long id;
    private String name;

    public AnimalGenderDto(AnimalGender animalGender) {
        this.id = animalGender.getId();
        this.name = animalGender.getName();
    }
}
