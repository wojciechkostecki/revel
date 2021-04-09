package pl.wojciechkostecki.revel.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.wojciechkostecki.revel.model.Menu;
import pl.wojciechkostecki.revel.model.dto.MenuDTO;

@Mapper(componentModel = "spring")
public interface MenuMapper extends EntityMapper<MenuDTO, Menu> {

    @Mapping(source = "local.id", target = "localId")
    MenuDTO toDto(Menu menu);
}
