package org.dgashuk.web_game.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Item {
    private Integer id;
    private String itemName;
    @Override
    public String toString() {
        return itemName;
    }

}
