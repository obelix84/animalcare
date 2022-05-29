package ru.animalcare.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.animalcare.domain.Animal;

import static ru.animalcare.common.Settings.PATH_TO_ANIMAL_PHOTO_DIRECTORY_THYMELEAF;

@Data
@NoArgsConstructor
public class AnimalDto {
    private Long id;
    private String name;
    private String gender;
    private int age;
    private String condition;
    private String description;
    private String pathPhoto;
    private String type;

    public AnimalDto(Animal animal) {
        if(animal.getId() != null){
            this.id = animal.getId();
        }
        this.name = animal.getName();
        this.gender = animal.getAnimalGender().getName();
        this.age = animal.getAge();
        this.condition = animal.getCondition();
        this.description = animal.getDescription();
        this.pathPhoto = PATH_TO_ANIMAL_PHOTO_DIRECTORY_THYMELEAF + animal.getAnimalPhotoList().get(0).getName();
        this.type = animal.getAnimalType().getName();
    }
}
