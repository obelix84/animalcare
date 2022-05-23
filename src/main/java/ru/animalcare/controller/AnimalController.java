package ru.animalcare.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;
import ru.animalcare.domain.Animal;
import ru.animalcare.dto.AnimalDto;
import ru.animalcare.dto.TypeOfAnimalDto;
import ru.animalcare.service.AnimalService;
import ru.animalcare.service.TypeService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/animals")
public class AnimalController {
    private final AnimalService animalService;
    private final TypeService typeService;

    @GetMapping
    public String showAllAnimals(Model model) {
        List<AnimalDto> animals = animalService.findAllAnimalDto();
        model.addAttribute("animals", animals);
        return "all_animals";
    }

    @GetMapping("/{id}")
    public String showAnimalById(Model model, @PathVariable Long id) {
        Animal currentAnimal = animalService.findAnimalById(id);
        model.addAttribute("current_animal", currentAnimal);
        return "profile_animal";
    }


//    @PostMapping("/add")
//    public String save(@RequestParam(value = "animals", required = false) Animal animal,
//                       @RequestParam(value = "type", required = false) TypeOfAnimal type,
//                       BindingResult result) {
//        if (result.hasErrors()) {
//            return "animals-add";
//        }
//        animalService.save(animal);
//        return "redirect:/main";
//    }

    //  @PreAuthorize("hasAuthority({'ROLE_ADMIN', 'ROLE_MANAGER'})")

//    @GetMapping("/animals-add")
//    public String displayingTheAnimals(Model model) {
//        List<TypeOfAnimal> type = new ArrayList<>();
//        typeService.findAll().forEach(type::add);
//        model.addAttribute("animal",new Animal() );
//        model.addAttribute("types", type);
//        return "animals-add";
//    }

    @GetMapping("/add")
    public String addNewAnimals(Model model) {

        TypeOfAnimalDto typeOfAnimalDto = new TypeOfAnimalDto(typeService.findAll());
        model.addAttribute("animal", new AnimalDto());
        model.addAttribute("types", typeOfAnimalDto.getAnimalTypes());
        return "animals-add";
    }

    @PostMapping("/add")
    public String add(@Valid AnimalDto animalDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "index";
        }
        animalService.addNewAnimal(animalDto);
        return showAllAnimals(model);
    }


//    @PostMapping("/add")
//    public String add (@ModelAttribute("animal") AnimalDto animalDto, BindingResult result){
////            @RequestParam(value = "animal", required = false) AnimalDto animalDto,
////                       @RequestParam(value = "type", required = false) TypeOfAnimal type,
////                       BindingResult result) {
//        if (result.hasErrors()==false) {
//            return "animals-add";
//        }
//        animalService.addNewAnimal(animalDto);
//        return "all_animals";
//    }
}
