package pl.wojciechkostecki.revel.model.dto;

import lombok.Data;
import pl.wojciechkostecki.revel.model.Category;

@Data
public class MenuItemDTO {
    private Long id;

    private String name;

    private Long menuId;

    private Category category;

    private String description;

    private double price;
}
