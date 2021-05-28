package pl.wojciechkostecki.revel.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.wojciechkostecki.revel.model.MenuItem;
import pl.wojciechkostecki.revel.model.dto.MenuItemDTO;

@Mapper(componentModel = "spring")
public interface MenuItemMapper extends EntityMapper<MenuItemDTO, MenuItem> {

    @Mapping(source = "menu.id", target = "menuId")
    MenuItemDTO toDto(MenuItem menuItem);
}
