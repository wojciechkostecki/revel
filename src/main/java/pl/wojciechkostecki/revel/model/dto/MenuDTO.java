package pl.wojciechkostecki.revel.model.dto;

import lombok.Data;
import pl.wojciechkostecki.revel.model.Local;
import pl.wojciechkostecki.revel.model.MenuItem;

import java.util.HashSet;
import java.util.Set;

@Data
public class MenuDTO {
    private Long id;

    private String name;

    private Local local;

    private Set<MenuItem> menuItems = new HashSet<>();
}