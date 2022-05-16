package ru.animalcare.service.util;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class PhotoManager {
    private static final String DIRECTORY_PATH = "src/main/resources/img";

    public void upload(byte[] resource, String keyName) throws IOException {
        Path path = Paths.get(DIRECTORY_PATH, keyName);
        Path file = Files.createFile(path);
        FileOutputStream stream = null;
        try {
            stream = new FileOutputStream(file.toString());
            stream.write(resource);
        } finally {
            stream.close();
        }
    }

    //Возвращаем файл в виде объекта Resource, а искать будем по клю
    public Resource download(String key) throws IOException {
        Path path = Paths.get(DIRECTORY_PATH + key);
        //создаем Resource по пути  + ключ.
        Resource resource = new UrlResource(path.toUri());
        //Проверяем, что файл по заданному пути не пуст и читаем. Если всё ОК,
        // возвращаем его, а если нет, прокидываем IOException наверх.
        if (resource.exists() || resource.isReadable()) {
            return resource;
        } else {
            throw new IOException();
        }
    }

    public void delete(String key) throws IOException {
        Path path = Paths.get(DIRECTORY_PATH + key);
        Files.delete(path);
    }
}
