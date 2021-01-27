package pl.wojciechkostecki.revel.mapper;

import org.mapstruct.Mapper;
import pl.wojciechkostecki.revel.model.Local;
import pl.wojciechkostecki.revel.model.dto.LocalDTO;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LocalMapper extends EntityMapper<LocalDTO,Local> {

    //LocalDTO toLocalDTO(Local local);

    //List<LocalDTO> toLocalsDTOs(List<Local> locals);

    //Local toLocal(LocalDTO localDTO);
}
