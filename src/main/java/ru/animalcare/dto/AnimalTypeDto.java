package ru.animalcare.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.animalcare.domain.AnimalType;

@Data
@NoArgsConstructor
public class AnimalTypeDto {
    private Long id;
    private String name;

    public AnimalTypeDto(AnimalType animalType) {
        this.id = animalType.getId();
        this.name = animalType.getName();
    }
}
