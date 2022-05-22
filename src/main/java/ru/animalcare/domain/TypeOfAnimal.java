package ru.animalcare.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import javax.persistence.*;

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

    @ManyToOne
    @JoinColumn(name = "type_of_animal_id")
    private Animal animal;

    public TypeOfAnimal (@Value("id") Long id,
                         @Value("name") String name) {
        this.id = id;
        this.name = name;

    }

}
