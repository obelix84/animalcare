package ru.animalcare.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import ru.animalcare.domain.Animal;
import ru.animalcare.domain.Photo;

import ru.animalcare.dto.AnimalDto;
import ru.animalcare.service.AnimalService;
import ru.animalcare.service.PhotoService;

import javax.imageio.ImageIO;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;


@Controller
@RequestMapping()
@RequiredArgsConstructor
public class PhotoController {

    private final PhotoService service;
    private final AnimalService animalService;

    @PostMapping("/upload")
    public ResponseEntity<Photo> upload( @RequestParam("file") MultipartFile attachment) {
        try {
            return new ResponseEntity<Photo>(service.upload(attachment), HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/upload")
    public String addNewPhoto(Model model) {
//        List<Animal> animals = animalService.findAll();
//        model.addAttribute("animals", animals);
        model.addAttribute("photo", new Photo());
        return "photo";
    }

//    @GetMapping("/upload")
//    public ResponseEntity index() throws IOException {
//        BufferedImage bufferedImage = ImageIO.read(
//                Photo.class.getResourceAsStream("/resources/img/"));
//
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        ImageIO.write(bufferedImage, "jpg", baos);
//
//        return ResponseEntity
//                .ok()
//                .contentType(MediaType.IMAGE_PNG)
//                .body(baos.toByteArray());
//    }



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