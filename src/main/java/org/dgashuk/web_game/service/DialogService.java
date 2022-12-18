package org.dgashuk.web_game.service;

import org.dgashuk.web_game.model.*;
import org.dgashuk.web_game.utils.GameData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

public class DialogService {
    private static final Logger log = LoggerFactory.getLogger(DialogService.class);
    private final GameData gameData;

    public DialogService(GameData gameData) {
        this.gameData = gameData;
    }

    public Npc getCurrentNpc(String currentNpsName) {
        Map<String, Npc> nps = gameData.getNps();
        return nps.get(currentNpsName);
    }

    public Dialog getNpcDialog(Npc npc, String dialogId) {
        if (dialogId == null) {
            Integer id = npc.getDialogId();
            return gameData.getDialogs().get(id);
        }

        try {
            Integer id = Integer.parseInt(dialogId);
            return gameData.getDialogs().get(id);
        } catch (NumberFormatException e) {
            throw new RuntimeException("DialogId is not a number!!", e);
        }
    }

    public void processingQuestDialog(User user, Npc currentNpc, String questId) {
        if (questId == null) {
            return;
        }

        Quest newQuest = null;
        try {
            Integer id = Integer.parseInt(questId);
            newQuest = gameData.getQuests().get(id);
        } catch (NumberFormatException e) {
            throw new RuntimeException("DialogId is not a number!!", e);
        }

        if (!user.getQuestList().contains(newQuest)) {
            user.addQuest(newQuest);
            log.debug("if questId is not null and user have`t got it, add quest " + newQuest + "to user");
        }

        takeItemFromCurrentNpc(user, currentNpc);
    }

    private void takeItemFromCurrentNpc(User user, Npc currentNpc) {
        if (currentNpc.getItemId() == null) {
            log.debug("Npc has`t got items...");
            return;
        }

        Integer itemId = currentNpc.getItemId();
        Map<Integer, Item> items = gameData.getItems();
        Item currentItem = items.get(itemId);

        List<Item> userItems = user.getItems();
        if(!userItems.contains(currentItem)){
            userItems.add(currentItem);
        }

        log.debug("if NPS has some item and item to user 'items-list': " + items.get(itemId));
    }

}
