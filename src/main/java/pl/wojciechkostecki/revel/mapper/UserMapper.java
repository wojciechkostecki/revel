package pl.wojciechkostecki.revel.mapper;

import org.mapstruct.Mapper;
import org.springframework.security.core.userdetails.UserDetails;
import pl.wojciechkostecki.revel.model.User;
import pl.wojciechkostecki.revel.model.dto.UserDTO;

@Mapper(componentModel = "spring")
public interface UserMapper extends EntityMapper<UserDTO, User>{

    default UserDTO toDto(UserDetails user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(user.getUsername());

        return userDTO;
    }
}
