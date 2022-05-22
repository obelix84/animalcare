package ru.animalcare.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "type_of_animal")
@Data
@NoArgsConstructor
public class TypeOfAnimal {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;

    public TypeOfAnimal (@Value("id") Long id,
                         @Value("name") String name){
        this.id = id;
        this.name = name;

    }

    @OneToMany(mappedBy = "typeOfAnimal")
//    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<Animal> animalList;
}
