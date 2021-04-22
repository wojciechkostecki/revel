package pl.wojciechkostecki.revel.model.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class UserDTO {

    @NotBlank(message = "Login is mandatory")
    @Size(min = 4, message = "Login requires a minimum of 4 characters")
    private String login;

    @NotBlank(message = "Password is mandatory")
    @Size(min = 4, message = "Password requires a minimum of 4 characters")
    private String password;
}