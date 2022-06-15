package ru.animalcare.domain;

import lombok.Data;
import org.hibernate.annotations.Cascade;
import org.springframework.beans.factory.annotation.Value;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "ANIMAL_TYPE")
@Data
public class AnimalType {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    public AnimalType(@Value("id") Long id,
                      @Value("name") String name) {
        this.id = id;
        this.name = name;

    }

    public AnimalType() {

    }

    @OneToMany(mappedBy = "animalType")
//    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<Animal> animalList;
}
