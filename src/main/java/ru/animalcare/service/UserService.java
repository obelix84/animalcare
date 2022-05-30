package ru.animalcare.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.animalcare.domain.User;
import ru.animalcare.dto.UserDto;
import ru.animalcare.repository.UserRepository;

import java.util.NoSuchElementException;

@Service
public class UserService {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.modelMapper = new ModelMapper();
        this.userRepository = userRepository;
    }

    public UserDto findUserByName(String name){
        User user = userRepository.findByUsername(name)
                .orElseThrow( ()-> new NoSuchElementException("User with name " + name + " isn\'t exist"));
        System.out.println(user);
        System.out.println("user");
        return this.modelMapper.map(user, UserDto.class);
    }
}
