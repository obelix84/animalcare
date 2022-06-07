package ru.animalcare.domain;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Data
@Setter
@Getter
@NoArgsConstructor
public class SearchAnimal {

    private String gender;
    private int age;
    private String type;

    public SearchAnimal(String gender, int age, String type) {
        this.gender = gender;
        this.age = age;
        this.type = type;

    }
}
