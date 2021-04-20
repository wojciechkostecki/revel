package pl.wojciechkostecki.revel.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    private String login;

    private String password;
}
