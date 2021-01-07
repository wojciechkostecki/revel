package pl.wojciechkostecki.revel.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class MenuItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "menu_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Menu menu;

    @Enumerated(EnumType.STRING)
    private Category category;

    private String description;

    private double price;
}
