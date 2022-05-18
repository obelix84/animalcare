package ru.animalcare.domain;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import javax.persistence.*;

@Entity
@Table(name = "typeOfAnimal")
@Data
public class TypeOfAnimal {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;

    public TypeOfAnimal (@Value("id") Long id,
                         @Value("name") String name) {
        this.id = id;
        this.name = name;

    }

    public TypeOfAnimal() {

    }
}
