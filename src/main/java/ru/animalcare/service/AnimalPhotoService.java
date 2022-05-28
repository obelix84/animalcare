package ru.animalcare.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.animalcare.domain.AnimalPhoto;
import ru.animalcare.dto.AnimalPhotoDto;
import ru.animalcare.repository.AnimalPhotoRepository;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static ru.animalcare.common.Settings.PATH_TO_ANIMAL_PHOTO_DIRECTORY;

@Service
@RequiredArgsConstructor
public class AnimalPhotoService {
    private final AnimalPhotoRepository animalPhotoRepository;

    public List<AnimalPhotoDto> findAllAnimalPhotos() {
        return animalPhotoRepository.findAll()
                .stream()
                .map(AnimalPhotoDto::new)
                .collect(Collectors.toList());
    }

    public AnimalPhoto findAnimalPhotoById(Long id) {
        return animalPhotoRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Animal photo for ID: " + id + " not found"));
    }

    public AnimalPhoto findAnimalPhotoByName(String name) {
        return animalPhotoRepository.findByName(name)
                .orElseThrow(() -> new NoSuchElementException("Animal photo for ID: " + name + " not found"));
    }

    @Transactional
    public AnimalPhotoDto uploadAnimalPhotoToServer(MultipartFile multipartFile) {
        AnimalPhoto animalPhoto = new AnimalPhoto();
        animalPhoto.setName(generateKey(multipartFile.getName()) + "_" + multipartFile.getOriginalFilename());
        animalPhoto.setSize(multipartFile.getSize());
        animalPhoto.setUploadDate(LocalDate.now());
        animalPhoto.setComment(multipartFile.getContentType());

        animalPhotoRepository.save(animalPhoto);
        animalPhoto = findAnimalPhotoByName(animalPhoto.getName());

        try {
            saveAnimalPhotoToServer(multipartFile.getBytes(), animalPhoto.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new AnimalPhotoDto(animalPhoto);
    }

    private String generateKey(String name) {
        return DigestUtils.md5Hex(name + LocalDateTime.now());
    }

    private void saveAnimalPhotoToServer(byte[] fileBytes, String name) throws IOException {
        try(FileOutputStream fileOutputStream = new FileOutputStream(PATH_TO_ANIMAL_PHOTO_DIRECTORY + name)) {
            fileOutputStream.write(fileBytes);
        }
    }

}