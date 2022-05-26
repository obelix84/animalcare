package ru.animalcare.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "ANIMAL_GENDER")
@Data
@NoArgsConstructor
public class AnimalGender {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "animalGender")
//    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<Animal> animalList;

}
