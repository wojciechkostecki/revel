package pl.wojciechkostecki.revel.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class MenuItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Category category;

    private String name;

    private String description;

    private double price;

}
