package ru.animalcare.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.animalcare.domain.Animal;

import java.util.ArrayList;
import java.util.List;

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
    private List<String> pathPhoto = new ArrayList<>();
    private String type;
    private Long userId;
    private String firstName;
    private String lastName;
    private String phone;
    private Boolean active;

    public AnimalDto(Animal animal) {
        if (animal.getId() != null) {
            this.id = animal.getId();
        }
        this.name = animal.getName();
        this.gender = animal.getAnimalGender().getName();
        this.age = animal.getAge();
        this.condition = animal.getCondition();
        this.description = animal.getDescription();
        for (int i = 0; i < animal.getAnimalPhotoList().size(); i++) {
            if (animal.getAnimalPhotoList().get(i).getSize() > 0) {
                this.pathPhoto.add(animal.getAnimalPhotoList()
                        .get(i)
                        .getName()
                        .replace(".jpg", ""));
            }
        }
        this.type = animal.getAnimalType().getName();
        this.active = animal.getActive();
        this.firstName = animal.getUser().getFirstName();
        this.lastName = animal.getUser().getLastName();
        this.phone = animal.getUser().getContactNumber();
    }
}
