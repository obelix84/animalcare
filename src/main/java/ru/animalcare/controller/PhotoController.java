package ru.animalcare.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.animalcare.domain.Animal;
import ru.animalcare.domain.Photo;
import ru.animalcare.domain.TypeOfAnimal;
import ru.animalcare.service.PhotoService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/photo")
@RequiredArgsConstructor
public class PhotoController {

    private final PhotoService service;

    @PostMapping
    public ResponseEntity<Photo> upload(@RequestParam MultipartFile attachment) {
        try {
            return new ResponseEntity<Photo>(service.upload(attachment), HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<Resource> download(@PathVariable("id") Long id) throws IOException {
        try {
            //вытягиваем из БД прилежащую сущность Photo
            Photo foundFile = service.findById(id);
            //по ключу из сущности скачиваем файл
            Resource resource = service.download(foundFile.getKeyPhoto());
            //отправляем назад файл, при этом добавив в хедер имя файла
            // (опять же, полученное из сущности с информацией о файле).
            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=" + foundFile.getName())
                    .body(resource);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        try {
            service.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}