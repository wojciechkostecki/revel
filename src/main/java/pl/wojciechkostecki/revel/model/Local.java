package pl.wojciechkostecki.revel.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.LocalTime;

@Entity
@Data
@EqualsAndHashCode(exclude = "menu")
public class Local {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    private String name;

    private LocalTime openingTime;

    private LocalTime closingTime;

    @OneToOne(cascade = CascadeType.ALL)
    @JsonManagedReference
    private Menu menu;
}
