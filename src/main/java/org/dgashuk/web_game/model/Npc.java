package org.dgashuk.web_game.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
public class Npc {
    private String npcName;
    private Integer dialogId;
    @Setter
    private Integer itemId;
    @Builder.Default
    private List<Quest> quests = new ArrayList<>();

}
