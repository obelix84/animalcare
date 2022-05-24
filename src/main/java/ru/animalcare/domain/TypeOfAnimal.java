package ru.animalcare.domain;

import lombok.Data;
import org.hibernate.annotations.Cascade;
import org.springframework.beans.factory.annotation.Value;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "TYPE_OF_ANIMAL")
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

    @OneToMany(mappedBy = "typeOfAnimal")
//    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<Animal> animalList;
}
