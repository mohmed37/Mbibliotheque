package com.microserviceuser.entities;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Entity

@Table(name = "users")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long num;

    @NotNull
    @Column(name = "prenom")
    String prenom;

    @NotNull
    @Column(name = "nom")
    String nom;

    @NotNull
    @Column(name = "userName")
    String userName;


    @Column(name = "password")
    String password;

    @NotNull
    @Column(name = "matchingPassword")
    String matchingPassword;


    @NotNull

    @Column(name = "email")
    String email;

    @NotNull
    @Size(max = 10,min = 10,message = "Le numéro doit contenir 10 chiffres")
    @Column(name = "phone")
    String phone;

    @Column(name = "date")
    Date date;



}
