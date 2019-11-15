package com.microservicelibrairie.entities;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "librairie")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class Librairie {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String genre;
    String titre;
    String auteur;
    String resume;
    Integer nExemplaire;
    Boolean status;
    Date dateDeb;
    Date dateFin;

}
