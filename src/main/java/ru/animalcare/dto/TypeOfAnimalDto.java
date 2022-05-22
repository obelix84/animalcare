package ru.animalcare.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.animalcare.domain.TypeOfAnimal;
import ru.animalcare.repository.TypeRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
@NoArgsConstructor
public class TypeOfAnimalDto {

    private List<String> animalTypes;

    public TypeOfAnimalDto(List<TypeOfAnimal> typeOfAnimals) {
        this.animalTypes = new ArrayList<>();
        for (int i = 0; i < typeOfAnimals.size(); i++) {
            animalTypes.add(typeOfAnimals.get(i).getName());
        }

    }

}
