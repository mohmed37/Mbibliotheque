package com.microservicelibrairie.entities;

        import lombok.*;
        import lombok.experimental.FieldDefaults;
        import org.springframework.format.annotation.DateTimeFormat;

        import javax.persistence.*;
        import java.io.Serializable;
        import java.util.Date;

@Entity
@Table(name = "LivreReserve")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class LivreReserve implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    Long idClient;
    Boolean prolongation=false;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date dateDeb;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date dateFin;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "livre_id")
    Librairie librairie;
}
