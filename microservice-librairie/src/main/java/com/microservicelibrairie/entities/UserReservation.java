package com.microservicelibrairie.entities;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;


@Entity
@Table(name = "userReservation")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@NoArgsConstructor
@AllArgsConstructor

public class UserReservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    Long idClient;
    Integer nbLivre=0;


    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("UserReservation{");
        sb.append("id=").append(id);
        sb.append(", idClient=").append(idClient);
        sb.append('}');
        return sb.toString();
    }


}
