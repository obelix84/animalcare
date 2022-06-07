package ru.animalcare.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.animalcare.domain.Animal;
import ru.animalcare.dto.AnimalDto;
import ru.animalcare.dto.AnimalRegistrationDto;
import ru.animalcare.dto.UserDto;
import ru.animalcare.service.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;



import static ru.animalcare.common.Settings.PATH_TO_ANIMAL_PHOTO_DIRECTORY;

@Controller
@RequiredArgsConstructor
@RequestMapping("/animals")
public class AnimalController {
    private final AnimalService animalService;
    private final AnimalTypeService animalTypeService;
    private final AnimalGenderService animalGenderService;
    private final UserService userService;
    private final ModelMapper modelMapper;



    @ModelAttribute(name = "userDto")
    public UserDto getUserDto(Principal principal) {
        if (principal != null) {
            UserDto userDto = userService.findUserByName(principal.getName());
            return userService.findUserByName(principal.getName());
        }
        return null;
    }

    @GetMapping
    public String showAllAnimalsPage(@RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
                                 @RequestParam(value = "size", required = false, defaultValue = "3") int size, Model model) {
        model.addAttribute("animals", animalService.getPage(pageNumber,size));
        return "all_animal";
    }

    @GetMapping(value ="type/{type}")
    public String showAnimalsTypesPage( Model model, @PathVariable String type) {
        model.addAttribute("animals", animalService.findAllAnimalsTypes(type) );
        return "animals_type";
    }

    @GetMapping("/{id}")
    public String showAnimalById(Model model, @PathVariable Long id) {
        AnimalDto animalDto = new AnimalDto(animalService.findAnimalById(id));
        model.addAttribute("currentAnimal", animalDto);
        return "profile_animal";
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN', 'ROLE_MANAGER')")
    @GetMapping("/add")
    public String showFormAddAnimalPhoto(Model model) {
        model.addAttribute("animalTypes", animalTypeService.findAllAnimalTypes());
        model.addAttribute("animalGenders", animalGenderService.findAllAnimalGenders());
        model.addAttribute("animalRegistration", new AnimalRegistrationDto());
        return "animal_add";

    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN', 'ROLE_MANAGER')")
    @PostMapping("/add")
    public String uploadAnimalPhotoToServer(@ModelAttribute AnimalRegistrationDto animalRegistrationDto,
                                            @ModelAttribute UserDto userDto) {
        animalService.addNewAnimal(animalRegistrationDto, userDto);
        return "redirect:/";
    }

    public String addNewAnimal(@ModelAttribute AnimalRegistrationDto animalRegistrationDto, @ModelAttribute UserDto userDto) {
        animalService.addNewAnimal(animalRegistrationDto, userDto);
        return "redirect:/";
    }


    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN', 'ROLE_MANAGER')")
    @GetMapping("/{id}/update")
    public String showUpdateFormAnimal(Model model, @PathVariable Long id) {
        //model.addAttribute("animal", animalService.findAnimalsById(id)) ;
        AnimalDto animalDto = new AnimalDto(animalService.findAnimalById(id));
        model.addAttribute("currentAnimal", animalDto);
        model.addAttribute("animalTypes", animalTypeService.findAllAnimalTypes());
        model.addAttribute("animalGenders", animalGenderService.findAllAnimalGenders());
        model.addAttribute("animalRegistration", new AnimalRegistrationDto());
        return "animal_update";
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN', 'ROLE_MANAGER')")
    @PostMapping("/{id}/update")
    public String updateAnimal(@PathVariable Long id, @ModelAttribute AnimalRegistrationDto animalRegistrationDto) {
        animalService.updateAnimal(id, animalRegistrationDto);
        return "redirect:/";
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN', 'ROLE_MANAGER')")
    @GetMapping("/change_active/{id}")
    public String changeActiveAnimal(@PathVariable Long id) {
        animalService.changeActiveAnimal(id);
        return "redirect:/";
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN', 'ROLE_MANAGER')")
    @GetMapping("/{id}/delete")
    public String deleteAnimal(@PathVariable Long id, @ModelAttribute UserDto userDto) {
        //нельзя удалять чужие объявления
        //нужна проверка
        animalService.deleteAnimalById(id);
        return "redirect:/profile/ads?active=false";
    }

    @GetMapping("/get/{imageName}")
    @ResponseBody
    public byte[] getImage(@PathVariable(value = "imageName") String imageName) throws IOException {
        File serverFile = new File(PATH_TO_ANIMAL_PHOTO_DIRECTORY + imageName + ".jpg");
        return Files.readAllBytes(serverFile.toPath());
    }

    @GetMapping("/download/{imageName}")
    public ResponseEntity downloadFileFromLocal(@PathVariable String imageName) {
        Path path = Paths.get(PATH_TO_ANIMAL_PHOTO_DIRECTORY + imageName + ".jpg");
        Resource resource = null;
        try {
            resource = new UrlResource(path.toUri());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }


    @GetMapping("/image/{imageName}")
    public ResponseEntity<Resource> getFileFromLocal(@PathVariable String imageName) {
        Path path = Paths.get(PATH_TO_ANIMAL_PHOTO_DIRECTORY + imageName + ".jpg");
        Resource resource = null;
        try {
            resource = new UrlResource(path.toUri());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok()
                .contentLength(path.toFile().length())
                //вот тут надо чего сделать
                .contentType(MediaType.IMAGE_JPEG)
                .body(resource);
    }


}
