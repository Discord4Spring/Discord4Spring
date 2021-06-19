package net.hypixel.floppybot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;

@Data
@AllArgsConstructor
public class PlayerNamesListDTO {
    private ArrayList<NameDTO> names;

    public String getCurrentName() {
        return names.get(names.size()-1).getName();
    }
}
