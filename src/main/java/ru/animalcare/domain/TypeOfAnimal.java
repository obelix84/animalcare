package ru.animalcare.domain;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Entity
@Table(name = "typeOfAnimal")
@Data
@Component
@Scope("session")
public class TypeOfAnimal {
    @Id
    @GeneratedValue
    @Column(name = "typeId")
    private long id;
    @Column(name = "typeName")
    private String typeName;

    public TypeOfAnimal (@Value("typeId") long id,
                         @Value("typeName") String typeName) {
        this.id = id;
        this.typeName = typeName;

    }

    public TypeOfAnimal() {

    }
}
