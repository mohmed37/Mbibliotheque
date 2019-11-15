package com.clientiu.bean;

import lombok.*;
import lombok.experimental.FieldDefaults;

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
    Date dateDeb;
    Date dateFin;

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
        sb.append('}');
        return sb.toString();
    }
}
