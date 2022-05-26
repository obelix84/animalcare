package ru.animalcare.service.imp;

import lombok.Builder;
import lombok.RequiredArgsConstructor;;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.web.multipart.MultipartFile;
import ru.animalcare.domain.Animal;
import ru.animalcare.domain.Photo;
import ru.animalcare.repository.PhotoRepository;
import ru.animalcare.service.PhotoService;
import ru.animalcare.service.util.PhotoManager;

import java.io.IOException;
import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class PhotoServiceImp implements PhotoService {

    private final PhotoRepository repository;
    private final PhotoManager photoManager;

    //в случае падения IOException, все наши сохранения в БД откатятся.
    @Transactional(rollbackFor = {IOException.class})
    @Builder(builderMethodName = "builder")
    @Override
    public Photo upload(MultipartFile resource) throws IOException {
       // генерируем ключ, который будет уникальным для файла, когда он будет сохранен
        // (даже если будут сейвиться два файла с одинаковыми именами, путаницы не возникнет).
        String key = generateKey(resource.getName());
       // строим сущность для сохранения в БД.
        Photo createdPhoto = Photo.builder()
                .name(resource.getOriginalFilename())
                .size(resource.getSize())
                .keyPhoto(key)
                .comment(resource.getContentType())
                //.animalsId(animal.id)
                .build();
        // загоняем сущность с инфой в БД.
        createdPhoto = repository.create(createdPhoto);
        //сохраняем файл с хешированным именем.
        photoManager.upload(resource.getBytes(), key);

        //возвращаем созданую сущность photo, но со сгенерированным id в БД и датой создания.
        return createdPhoto;
    }


    private String generateKey(String name) {
        //Вычисляет дайджест MD5, и возвращает значение в виде 32-символьной шестнадцатеричной строки.
        return DigestUtils.md5Hex(name + LocalDateTime.now().toString());
    }

    //также откат изменения данных (удаления) при падении IOException.
    @Transactional(rollbackFor = {IOException.class})
    @Override
    public void delete(Long photoId) throws IOException {
        Photo photo = repository.findById(photoId);
        //удаляем информацию о файле из БД.
        repository.delete(photoId);
        //удаляем сам файл из нашего “хранилища”.
        photoManager.delete(photo.getKeyPhoto());
    }
    @Override
    public Resource download(String key) throws IOException {
        return photoManager.download(key);
    }

//    @Transactional(rollbackFor = {IOException.class})
//    @Builder(builderMethodName = "builder")
//    @Override
//    public Photo  addAnimal(MultipartFile resource, Animal addAnimal)throws IOException {
//        // генерируем ключ, который будет уникальным для файла, когда он будет сохранен
//        // (даже если будут сейвиться два файла с одинаковыми именами, путаницы не возникнет).
//        String key = generateKey(resource.getName());
//        // строим сущность для сохранения в БД.
//        Photo createdPhoto = Photo.builder()
//                .name(resource.getOriginalFilename())
//                .size(resource.getSize())
//                .keyPhoto(key)
//                .comment(resource.getContentType())
////                .animal(addAnimal)
//                .build();
//        // загоняем сущность с инфой в БД.
//        createdPhoto = repository.create(createdPhoto);
//        //сохраняем файл с хешированным именем.
//        photoManager.upload(resource.getBytes(), key);
//
//        //возвращаем созданую сущность photo, но со сгенерированным id в БД и датой создания.
//        return createdPhoto;
//
//    }

    // транзакция у нас для чтения
    @Transactional(readOnly = true)
    @Override
    public Photo findById(Long fileId) {
        return repository.findById(fileId);
    }
}
