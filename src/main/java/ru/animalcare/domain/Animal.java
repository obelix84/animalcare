package ru.animalcare.domain;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;


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

    @Column(name = "gender")
    private String gender;

    @Column(name = "age")
    private int age;

    @Column(name = "condition")
    private String condition;

    @Column(name = "description")
    private String description;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToMany(mappedBy = "animal")
    //@Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<TypeOfAnimal> typeOfAnimal;

//    @OneToMany
//    @JoinColumn(
//            name = "type_id",
//            nullable = false,
//            foreignKey = @ForeignKey(name = "fk_typeOfAnimal")
//    )
//    List<TypeOfAnimal> typeOfAnimals;

}
