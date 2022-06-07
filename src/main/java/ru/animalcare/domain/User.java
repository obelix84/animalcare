package ru.animalcare.domain;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import ru.animalcare.validator.ValidPassword;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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
    private Long id;

    @NotNull
    @Column(name = "email", unique = true)
    @Email
    String email;

    @NotNull
    @Column(name = "password", nullable = false)
    @ValidPassword
    private String password;

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    @NotNull
    @Column(name = "contact_number", nullable = false)
//    @Length(max = 12,min = 12, message = "phone number should be in this format: +71234567890")
    private String contactNumber;

    @NotNull
    @Column(name = "first_name")
    private String firstName;

    @NotNull
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


//    //  couldn't find the getter of contact number
//    public String getContactNumber() {
//        return contactNumber;
//    }
//    public void setContactNumber(String contactNumber){
//        this.contactNumber = contactNumber;
//    }
}
