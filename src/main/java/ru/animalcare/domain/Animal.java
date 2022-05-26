package ru.animalcare.domain;

import lombok.*;

import javax.persistence.*;


@Entity
@Table(name = "animals")
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
//    @JoinColumn(name = "user_id", referencedColumnName = "id")
//    private User user;

    @ManyToOne
    @JoinColumn(name = "animal_type_id")
    private AnimalType animalType;

}
