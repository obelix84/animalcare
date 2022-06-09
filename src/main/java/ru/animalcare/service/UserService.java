package ru.animalcare.service;

import com.sun.xml.bind.v2.runtime.output.SAXOutput;
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

    public User findUserById(Long id){
        User user = userRepository.findById(id)
                .orElseThrow( ()-> new NoSuchElementException("User with id " + id + " isn\'t exist"));
        return user;
    }

}
