package ru.animalcare.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "USERS")
@Data
public class User {

    @Id
    @Column(name = "id", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "username", unique = true)
    String username;

    @Column(name = "password", nullable = false)
    String password;

    @Column(name = "email", nullable = false)
    String email;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "enabled", nullable = false)
    boolean enabled;

    //cascade = {CascadeType.PERSIST, CascadeType.MERGE}
    //не надо использовать эти 2, либо только MERGE, либо ничего.
    @ManyToMany(cascade = {CascadeType.MERGE}, fetch = FetchType.LAZY)
    private Collection<Authority> authorities;

    //Пока так
    @Column(name = "photo_id")
    private Long photoId;
}
