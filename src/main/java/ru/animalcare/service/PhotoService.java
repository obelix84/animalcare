package ru.animalcare.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
import ru.animalcare.domain.Animal;
import ru.animalcare.domain.Photo;

import java.io.IOException;

public interface PhotoService {
    Photo upload(MultipartFile resource) throws IOException;

    void delete(Long photoId) throws IOException;

    Photo findById(Long id);

    Resource download(String key) throws IOException;

//    Photo addAnimal(MultipartFile attachment, Animal addAnimal) throws IOException;
}
