package ru.animalcare.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import ru.animalcare.domain.Animal;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class AnimalRegistrationDto {
    private String name;
    private String gender;
    private int age;
    private String condition;
    private String description;
    private String type;
    private List<MultipartFile> multipartFile;

    public AnimalRegistrationDto(Animal animal) {
        this.name = animal.getName();
        this.gender = animal.getAnimalGender().getName();
        this.age = animal.getAge();
        this.condition = animal.getCondition();
        this.description = animal.getDescription();
        this.type = animal.getAnimalType().getName();
        this.multipartFile = new ArrayList<>();
    }
}
