package ru.animalcare.domain;

import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Collection;


@Entity
@Table(name = "USERS")
@Data
public class User {

    @Id
    @Column(name = "id", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "email", unique = true)
    @Email
    String email;

    @NotNull
    @Column(name = "password", nullable = false)
    private String password;

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    @NotNull
    @Column(name = "contact_number", nullable = false)
    private String contactNumber;

    @NotNull
    @Column(name = "first_name")
    private String firstName;

    @NotNull
    @Column(name = "last_name")
    private String lastName;

    @Column(name = "enabled", nullable = false)
    boolean enabled;

    @ManyToMany(cascade = {CascadeType.MERGE}, fetch = FetchType.LAZY)
    private Collection<Authority> authorities;

    //Пока так
    @Column(name = "photo_id")
    private Long photoId;

}
