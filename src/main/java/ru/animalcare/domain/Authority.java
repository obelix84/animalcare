package ru.animalcare.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "AUTHORITIES")
@Data
public class Authority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    String authority;
}
