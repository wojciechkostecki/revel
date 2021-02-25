package pl.wojciechkostecki.revel.mapper;

import org.mapstruct.Mapper;
import pl.wojciechkostecki.revel.model.Local;
import pl.wojciechkostecki.revel.model.dto.LocalDTO;

@Mapper(componentModel = "spring")
public interface LocalMapper extends EntityMapper<LocalDTO,Local> {
}
