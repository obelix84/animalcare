package ru.animalcare.domain;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

//@Entity
@Table(name = "photos")
@Data
//@NoArgsConstructor
@Builder(toBuilder = true)
public class Photo {
   // @Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    //@Column(name = "id", nullable = false)
    private Long id;

    //@Column(name = "name")
    private String name;

    //@Column(name = "size")
    private Long size;

    //@Column(name = "keyPhoto")
    private String keyPhoto;

    //@Column(name = "uploadDate")
    private LocalDate uploadDate;

    //@Column(name = "comment")
    private String comment;

    @ManyToOne
    @JoinColumn(
            name = "animals_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "FK_PHOTO_ANIMALS_ID_RELATION")
    )
    private Animal animalsId;


}
