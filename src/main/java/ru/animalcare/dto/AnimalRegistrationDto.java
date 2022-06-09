package ru.animalcare.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
public class AnimalRegistrationDto {
    private String name;
    private String gender;
    private int age;
    private String condition;
    private String description;
    private String type;
    private MultipartFile multipartFile;
}
