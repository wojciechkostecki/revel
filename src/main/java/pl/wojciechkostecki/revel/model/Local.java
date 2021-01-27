package pl.wojciechkostecki.revel.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalTime;

@Entity
@Data
public class Local {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    private String name;

    private LocalTime openingTime;

    private LocalTime closingTime;

    @OneToOne(cascade = CascadeType.ALL)
    private Menu menu;
}
