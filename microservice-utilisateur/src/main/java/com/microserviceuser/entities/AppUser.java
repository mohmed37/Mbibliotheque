package com.microserviceuser.entities;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Entity
@Table(name = "users")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AppUser implements Serializable {
    public AppUser(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long num;
    String prenom;
    String nom;
    @Column(unique = true)
    String username;
    String password;
    String matchingPassword;
    @Column(unique = true)
    String email;
    String phone;
    Date date;
    Boolean active;
    @ManyToMany(fetch = FetchType.EAGER)
    Collection<AppRole> roles =new ArrayList<>();

}
