package ru.animalcare.dto;

import lombok.*;
import ru.animalcare.domain.Authority;
import java.util.Collection;


@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class UserDto {
    private Long id;
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    boolean enabled;
    private Collection<Authority> authorities;
    private Long photoId;
}
