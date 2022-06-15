package ru.animalcare.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.animalcare.domain.AnimalPhoto;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class AnimalPhotoDto {
    private Long id;
    private String name;
    private Long size;
//    private String keyPhoto;
    private LocalDate uploadDate;
    private String comment;

    public AnimalPhotoDto(AnimalPhoto animalPhoto) {
        this.id = animalPhoto.getId();
        this.name = animalPhoto.getName();
        this.size = animalPhoto.getSize();
//        this.keyPhoto = animalPhoto.getKeyPhoto();
        this.uploadDate = animalPhoto.getUploadDate();
        this.comment = animalPhoto.getComment();
    }
}
