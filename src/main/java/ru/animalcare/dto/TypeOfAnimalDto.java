package ru.animalcare.dto;

import lombok.Data;
import ru.animalcare.domain.TypeOfAnimal;

import java.util.ArrayList;
import java.util.List;

@Data
public class TypeOfAnimalDto {
    private List<String> animalTypes;

    public TypeOfAnimalDto(List<TypeOfAnimal> typeOfAnimals) {
        this.animalTypes = new ArrayList<>();
        for (int i = 0; i < typeOfAnimals.size(); i++) {
            animalTypes.add(typeOfAnimals.get(i).getName());
        }
    }
}
