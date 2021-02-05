package pl.wojciechkostecki.revel.model.dto;

import lombok.Data;
import pl.wojciechkostecki.revel.model.Menu;

import java.time.LocalTime;

@Data
public class LocalDTO {

    private String name;

    private LocalTime openingTime;

    private LocalTime closingTime;

    private Menu menu;
}
