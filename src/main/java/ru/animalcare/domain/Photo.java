package ru.animalcare.domain;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "photos")
@Data
@NoArgsConstructor
@Builder(toBuilder = true)
public class Photo {
    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "size")
    private Long size;

    @Column(name = "keyPhoto")
    private String keyPhoto;

    @Column(name = "uploadDate")
    private LocalDate uploadDate;

    @Column(name = "comment")
    private String comment;

//    @ManyToOne
//    @JoinColumn(
//            name = "animals_id",
//            nullable = false,
//            foreignKey = @ForeignKey(name = "FK_PHOTO_ANIMALS_ID_RELATION")
//    )
//    private Animal animalsId;

    public Photo(@Value("id") Long id,
                 @Value("name") String name,
                 @Value("size") Long size,
                 @Value("keyPhoto") String keyPhoto,
                 @Value("uploadDate") LocalDate uploadDate,
                 @Value("comment") String comment) {
        this.id = id;
        this.name = name;
        this.size = size;
        this.keyPhoto = keyPhoto;
        this.uploadDate = uploadDate;
        this.comment = comment;
    }

}
