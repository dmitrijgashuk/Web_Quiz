package org.dgashuk.web_game.model;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Builder
@Data
public class User implements Serializable {
    private Integer id;
    private String name;
    private String currentLocation;
    @Builder.Default
    private List<Quest> questList = new ArrayList<>();
    @Builder.Default
    private Integer gameCount = 0;
    @Builder.Default
    private List<Item> items = new ArrayList<>();

    public void addQuest(Quest quest) {
        questList.add(quest);
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", currentLocation='" + currentLocation + '\'' +
                ", questList=" + questList +
                ", items=" + items +
                '}';
    }

    public void incrementGameCounter() {
        gameCount++;
    }

    public void reset() {
        currentLocation = "Enter";
        questList = new ArrayList<>();
        items = new ArrayList<>();
    }
}
