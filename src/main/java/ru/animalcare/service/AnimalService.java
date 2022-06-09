package ru.animalcare.service;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.animalcare.domain.*;
import ru.animalcare.domain.paging.Paged;
import ru.animalcare.domain.paging.Paging;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Sort;
import ru.animalcare.dto.AnimalDto;
import ru.animalcare.dto.AnimalRegistrationDto;
import ru.animalcare.dto.UserDto;
import ru.animalcare.repository.AnimalRepository;
import ru.animalcare.repository.UserRepository;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static ru.animalcare.common.Settings.ANIMAL_PHOTO_DEFAULT;


@Service
@RequiredArgsConstructor
public class AnimalService {

    private final AnimalRepository animalRepository;
    private final AnimalTypeService animalTypeService;
    private final AnimalGenderService animalGenderService;
    private final AnimalPhotoService animalPhotoService;
    private final UserRepository userRepository;
    private final UserService userService;


    public Long countActiveAnimalsByUserId(Long id) {
        return animalRepository.countAnimalsByUserIdAndActiveTrue(id);
    }

    public Long countInActiveAnimalsByUserId(Long id) {
        return animalRepository.countAnimalsByUserIdAndActiveFalse(id);
    }

    public List<AnimalDto> findAllAnimals() {
        return StreamSupport.stream(animalRepository.findAll().spliterator(), false)
                .map(AnimalDto::new)
                .collect(Collectors.toList());
    }


    public Long countModeration() {
        Long count = 0L;
        Iterable<Animal> animalList = animalRepository.findAll();
        for (Animal animal : animalList) {
            if (!animal.getActive()) {
                count++;
            }
        }
        return count;
    }


    public List<AnimalDto> findPagedAnimalsByUserIdAndActive(boolean active, long userId, int startElement, int maxElementsCount) {
        if (active) {
            return animalRepository.findAnimalsByUserIdAndActiveIsTrue(userId, PageRequest.of(startElement, maxElementsCount)).stream().map(AnimalDto::new)
                    .collect(Collectors.toList());
        }
        return animalRepository.findAnimalsByUserIdAndActiveIsFalse(userId, PageRequest.of(startElement, maxElementsCount)).stream().map(AnimalDto::new)
                .collect(Collectors.toList());
    }


    public List<AnimalDto> findAnimalsById(Long id) {
        return animalRepository.findAnimalsByUserId(id)
                .stream()
                .map(AnimalDto::new)
                .collect(Collectors.toList());
    }

    public List<AnimalDto> findAllInactiveAnimals() {
        return StreamSupport.stream(animalRepository.findAll().spliterator(), false)
                .filter(animal -> !animal.getActive())
                .map(AnimalDto::new)
                .collect(Collectors.toList());
    }

    public List<AnimalDto> findAllActiveAnimals() {
        return StreamSupport.stream(animalRepository.findAll().spliterator(), false)
                .filter(Animal::getActive)
                .map(AnimalDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public Paged<AnimalDto> getPage(int pageNumber, int size) {

        PageRequest request = PageRequest.of(pageNumber - 1, size, Sort.by(Sort.Direction.ASC, "id"));

        Page<AnimalDto> animalPage = new PageImpl<>(animalRepository.findAll(request)
                .stream()
                .filter(Animal::getActive)
                .map(AnimalDto::new)
                .collect(Collectors.toList())
                , request
                , findAllActiveAnimals().size());
        return new Paged<>(animalPage, Paging.of(animalPage.getTotalPages(), pageNumber, size));
    }

    public Animal findAnimalById(Long id) {
        return animalRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Animal for ID: " + id + " not found"));
    }

    public boolean deleteById(Long id) {
        try {
            animalRepository.deleteById(id);
            return true;
        } catch (NoSuchElementException e) {
            throw new EntityNotFoundException("Animal entity no found by id: " + id);
        }
    }

    public long getCount() {
        return animalRepository.count();
    }

    public void changeActiveAnimal(Long id) {
        Animal animal = findAnimalById(id);
        animal.setActive(true);
        animalRepository.save(animal);
    }

    @Transactional
    public AnimalDto addNewAnimal(AnimalRegistrationDto animalRegistrationDto, UserDto userDto) {
        Animal animal = new Animal();
        animal.setName(animalRegistrationDto.getName());

        AnimalGender animalGender = animalGenderService.findAnimalGenderByName(animalRegistrationDto.getGender())
                .orElseThrow(() -> new RuntimeException(String.format("Animal gender '%s' not found\n", animalRegistrationDto.getGender())));
        animal.setAnimalGender(animalGender);

        animal.setAge(animalRegistrationDto.getAge());
        animal.setCondition(animalRegistrationDto.getCondition());
        animal.setDescription(animalRegistrationDto.getDescription());
        animal.setActive(true);
        animal.setUser(userRepository.findById(userDto.getId()).orElseThrow(
                () -> new RuntimeException(String.format("User with id '%d' is not exist\n", userDto.getId()))
        ));
//=======
//
////  todo подумать, нужно ли возвращать UserDto из метода findUserByName, чтобы несколько раз поиск не проводить, если нужна сущность User
//        UserDto userDto = userService.findUserByEmail(username);
//        User user = userService.findUserById(userDto.getId());
//        if (user != null) {
//            animal.setUser(user);
//        }
//>>>>>>> origin/cat_dog

        AnimalType animalType = animalTypeService.findTypeAnimalByName(animalRegistrationDto.getType())
                .orElseThrow(() -> new RuntimeException(String.format("Animal type '%s' not found\n", animalRegistrationDto.getType())));
        animal.setAnimalType(animalType);

        if (animalRegistrationDto.getMultipartFile().getSize() != 0) {
            AnimalPhoto animalPhoto = animalPhotoService.uploadAnimalPhotoToServer(animalRegistrationDto.getMultipartFile());
            animal.setAnimalPhotoList(new ArrayList<>(Arrays.asList(animalPhoto)));
        } else {
            AnimalPhoto animalPhoto = animalPhotoService.findAnimalPhotoByName(ANIMAL_PHOTO_DEFAULT);
            animal.setAnimalPhotoList(new ArrayList<>(Arrays.asList(animalPhoto)));
        }

        return new AnimalDto(animalRepository.save(animal));
    }


    public void archiveAd(long id) {
        Animal animal = animalRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("Animal with id " + id + " isnt exist!"));
        animal.setActive(false);
        animalRepository.save(animal);
    }

    @Transactional
    public AnimalDto updateAnimal(Long id, AnimalRegistrationDto animalRegistrationDto) {
        Animal animal = findAnimalById(id);
        animal.setName(animalRegistrationDto.getName());

        AnimalGender animalGender = animalGenderService.findAnimalGenderByName(animalRegistrationDto.getGender())
                .orElseThrow(() -> new RuntimeException(String.format("Animal gender '%s' not found\n", animalRegistrationDto.getGender())));
        animal.setAnimalGender(animalGender);

        animal.setAge(animalRegistrationDto.getAge());
        animal.setCondition(animalRegistrationDto.getCondition());
        animal.setDescription(animalRegistrationDto.getDescription());

        AnimalType animalType = animalTypeService.findTypeAnimalByName(animalRegistrationDto.getType())
                .orElseThrow(() -> new RuntimeException(String.format("Animal type '%s' not found\n", animalRegistrationDto.getType())));
        animal.setAnimalType(animalType);

        if (animalRegistrationDto.getMultipartFile().getSize() != 0) {
            AnimalPhoto animalPhoto = animalPhotoService.uploadAnimalPhotoToServer(animalRegistrationDto.getMultipartFile());
            animal.setAnimalPhotoList(new ArrayList<>(Arrays.asList(animalPhoto)));
        }

        return new AnimalDto(animalRepository.save(animal));
    }

    public void deleteAnimalById(Long id) {
        animalRepository.deleteById(id);
    }

    public List<AnimalDto> findAllAnimalsTypes(String type) {
        return StreamSupport.stream(animalRepository.findAll().spliterator(), false)
                .filter(Animal::getActive)
                .filter(Animal -> Animal.getAnimalType().getName().equals(type))
                .map(AnimalDto::new)
                .collect(Collectors.toList());
    }


    public List<AnimalDto> getAnimalBySearch(SearchAnimal animal) {


        return StreamSupport.stream(animalRepository.findAll().spliterator(), false)
                .filter(Animal::getActive)
                .filter(Animal -> Animal.getAnimalGender().getName().equals(animal.getGender()))
                .filter(Animal -> Animal.getAnimalType().getName().equals(animal.getType()))
                .filter(Animal -> Animal.getAge() == animal.getAge())
                .map(AnimalDto::new)
                .collect(Collectors.toList());

    }
}
