package pl.wojciechkostecki.revel.model.dto;

import lombok.Data;
import pl.wojciechkostecki.revel.model.Menu;

import javax.validation.constraints.NotBlank;
import java.time.LocalTime;

@Data
public class LocalDTO {

    private Long id;

    @NotBlank(message = "Name is mandatory")
    private String name;

    private LocalTime openingTime;

    private LocalTime closingTime;

    private Menu menu;
}
