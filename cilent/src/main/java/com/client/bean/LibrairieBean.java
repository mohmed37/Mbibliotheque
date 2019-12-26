package com.client.bean;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)

public class LibrairieBean {
    Long id;
    String genre;
    String titre;
    String auteur;
    String resume;
    Integer nExemplaire;
    Boolean status;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date dateDeb;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date dateFin;
    String photo;
    Long location;
    Boolean prolongation;


    public String getDateCreatedString(Date date) {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        return df.format(date);
    }


    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("LibrairieBean{");
        sb.append("id=").append(id);
        sb.append(", genre='").append(genre).append('\'');
        sb.append(", titre='").append(titre).append('\'');
        sb.append(", auteur='").append(auteur).append('\'');
        sb.append(", resume='").append(resume).append('\'');
        sb.append(", nExemplaire=").append(nExemplaire);
        sb.append(", status=").append(status);
        sb.append(", dateDeb=").append(dateDeb);
        sb.append(", dateFin=").append(dateFin);
        sb.append(", photo='").append(photo).append('\'');
        sb.append(", idUserLocation=").append(location);
        sb.append('}');
        return sb.toString();
    }
}
