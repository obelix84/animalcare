package ru.animalcare.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;


@Entity
@Table(name = "ANIMALS")
@Data
@NoArgsConstructor
public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "animal_gender_id")
    private AnimalGender animalGender;

    @Column(name = "age")
    private int age;

    @Column(name = "condition")
    private String condition;

    @Column(name = "description")
    private String description;

//    @OneToOne
//    @JoinColumn(name = "animal_photo_id", referencedColumnName = "id")
//    private AnimalPhoto animalPhoto;

//    @Column(name = "path_photo")
//    private String pathPhoto;

    @ManyToOne
    @JoinColumn(name = "animal_type_id")
    private AnimalType animalType;

    @ManyToMany
    @JoinTable(name = "ANIMALS_ANIMAL_PHOTOS",
            joinColumns = @JoinColumn(name = "animal_id"),
            inverseJoinColumns = @JoinColumn(name = "animal_photo_id"))
    private List<AnimalPhoto> animalPhotoList;

}
