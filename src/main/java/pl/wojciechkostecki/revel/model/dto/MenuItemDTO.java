package pl.wojciechkostecki.revel.model.dto;

import lombok.Data;
import pl.wojciechkostecki.revel.model.Category;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class MenuItemDTO {

    private Long id;

    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotNull(message = "Menu ID is mandatory")
    private Long menuId;

    private Category category;

    private String description;

    private double price;
}
