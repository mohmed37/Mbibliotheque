package com.microservicelibrairie.entities;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

@Entity
@Table(name = "librairie")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class Librairie implements Serializable {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String genre;
    @Column(name = "titre",length = 30)
    String titre;
    String auteur;
    String resume;
    Integer nExemplaire;
    String photo;


}
