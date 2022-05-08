package ru.animalcare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.animalcare.domain.User;

import java.util.Optional;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);
}
