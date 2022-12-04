package org.dgashuk.web_game.dto;

import lombok.Builder;
import lombok.Data;
import org.dgashuk.web_game.model.Item;
import org.dgashuk.web_game.model.Location;
import org.dgashuk.web_game.model.Quest;
import org.dgashuk.web_game.model.User;

import java.util.List;


@Data
@Builder
public class LocationData {
    User user;
    List<String> npc;
    List<Item> items;
    List<Quest> finishQuests;
    List<Quest> availableQuests;
    List<Location> availableLocation;

}
