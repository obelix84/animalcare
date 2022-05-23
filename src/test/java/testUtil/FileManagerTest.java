package testUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.animalcare.domain.Animal;
import ru.animalcare.domain.Photo;
import ru.animalcare.service.util.PhotoManager;


import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnit4.class)
public class FileManagerTest {

    private static MultipartFile multipartFile;

    private static PhotoManager manager;

    private static Photo photo;

    //testFile будет заменой нашего хранилища.
    @BeforeClass
    public static void prepareTestData() throws IOException {
        photo = Photo.builder()
                .id(1L)
                .name("test.txt")
                .keyPhoto("test.txt")
                .size(48L)
                .uploadDate(LocalDate.now())
                .comment("test")
//                .animalsId(new Animal())
                .build();
        multipartFile = new MockMultipartFile("test", "test.txt", "txt",
                new FileInputStream("src/test/resources/test.txt"));
        manager = new PhotoManager();
    }

    @Test
    public void uploadTest() throws IOException {
        //с помощью тестовой рефлексии меняем нашу константу в сервисе для задания пути сохранения файла.
        // ReflectionTestUtils  у меня почему-то не работает, не меняет путь
        // ReflectionTestUtils.setField(manager, "DIRECTORY_PATH", "src/test/resources/testFile/");

        //вызываем проверяемый метод
        manager.upload(multipartFile.getBytes(), "test.txt");

        // проверяем правильность исполнения сохранения
        Path checkFile = Paths.get("src/main/resources/img/test.txt");

        assertThat(Files.exists(checkFile)).isTrue();
        assertThat(Files.isRegularFile(checkFile)).isTrue();
        assertThat(Files.size(checkFile)).isEqualTo(multipartFile.getSize());
        // удаляем сохраненный файл
        Files.delete(checkFile);

    }

    @Test
    public void deleteTest() throws IOException {
        //задаем путь и создаем файл
        Path checkFile = Paths.get("src/main/resources/img/test.txt");
        Files.createFile(checkFile);
        //проверяем его существование.
        assertThat(Files.exists(checkFile)).isTrue();
        assertThat(Files.isRegularFile(checkFile)).isTrue();
        //   ReflectionTestUtils.setField(manager, "DIRECTORY_PATH", "src/test/resources/testFile/");

        //используем проверяемый метод
        manager.delete(photo.getKeyPhoto());

        //проверяем, что объекта уже нет.
        assertThat(Files.notExists(checkFile)).isTrue();
    }

    @Test
    public void downloadTest() throws IOException {
        // ReflectionTestUtils.setField(manager, "DIRECTORY_PATH", "src/test/resources/");

        Resource resource = manager.download(photo.getKeyPhoto());
        //проверяем результат исполнения.
        assertThat(resource.isFile()).isTrue();
        assertThat(resource.getFilename()).isEqualTo(photo.getName());
        assertThat(resource.exists()).isTrue();
    }

}
