package org.dgashuk.web_game.model;


import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Data
@Builder
public class Location implements Serializable {
    private String title;
    private List<String> exits;
    @Builder.Default
    private List<String> quest = new ArrayList<>();
    @Builder.Default
    private List<String> npc = new ArrayList<>();
    @Builder.Default
    private List<Item> items = new ArrayList<>();
    private Predicate<User> isAvailable;

    public boolean isLocationAvailable(User user){
        if(isAvailable == null){
            return true;
        }
        return isAvailable.test(user);
    }

}
