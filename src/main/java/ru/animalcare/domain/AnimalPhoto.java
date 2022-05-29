package ru.animalcare.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "ANIMAL_PHOTOS")
@Data
@NoArgsConstructor
public class AnimalPhoto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "size")
    private Long size;

    @Column(name = "upload_date")
    private LocalDate uploadDate;

    @Column(name = "comment")
    private String comment;

}
