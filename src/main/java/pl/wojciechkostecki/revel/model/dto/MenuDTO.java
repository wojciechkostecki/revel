package pl.wojciechkostecki.revel.model.dto;

import lombok.Data;
import pl.wojciechkostecki.revel.model.Local;
import pl.wojciechkostecki.revel.model.MenuItem;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Data
public class MenuDTO {

    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotNull(message = "Local ID is mandatory")
    private Long localId;

    private Set<MenuItem> menuItems = new HashSet<>();
}
