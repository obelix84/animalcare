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

    private List<String> typeName;

    public TypeOfAnimalDto(List<TypeOfAnimal> typeOfAnimal) {
        this.typeName = new ArrayList<>();
        for (int i = 0; i < typeOfAnimal.size(); i++) {
            typeName.add(typeOfAnimal.get(i).getName());
        }
    }

}
