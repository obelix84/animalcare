package ru.animalcare.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.animalcare.domain.User;
import ru.animalcare.dto.UserDto;
import ru.animalcare.repository.UserRepository;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UserService {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    public UserDto findUserByEmail(String email){
        User user = userRepository.findByEmail(email)
                .orElseThrow( ()-> new NoSuchElementException("Account with email " + email + " does not exist"));
        return this.modelMapper.map(user, UserDto.class);
    }
}
