package org.dgashuk.web_game.utils;


import org.dgashuk.web_game.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class GameData {
    private static final Logger log = LoggerFactory.getLogger(GameData.class);

    private final Map<String, Location> locations = new HashMap<>();
    private final Map<String, Npc> nps = new HashMap<>();
    private final Map<Integer, Quest> quests = new HashMap<>();
    private final Map<Integer, Dialog> dialogs = new HashMap<>();
    private final Map<Integer, Item> items = new HashMap<>();

    public GameData() {
        npsInit();
        locationMapInit();
        initItem();
        questInit();
        initDialog();

    }

    private void locationMapInit() {
        Location enter = Location.builder()
                .title("Enter")
                .exits(List.of("Green Garden", "Dark Cave"))
                .build();
        locations.put("Enter", enter);

        Location bigTree = Location.builder()
                .title("Big Tree")
                .exits(List.of("Dark Cave", "Lonely Lake"))
                .build();
        locations.put("Big Tree", bigTree);

        Location darkCave = Location.builder()
                .title("Dark Cave")
                .exits(List.of("Enter", "Big Tree"))
                .build();
        locations.put("Dark Cave", darkCave);

        Location lonelyLake = Location.builder()
                .title("Lonely Lake")
                .exits(List.of("Big Tree", "Green Garden", "Old Forest", "Field"))
                .build();
        locations.put("Lonely Lake", lonelyLake);

        Location oldForest = Location.builder()
                .title("Old Forest")
                .exits(List.of("Lonely Lake"))
                .build();
        locations.put("Old Forest", oldForest);

        Location greenGarden = Location.builder()
                .title("Green Garden")
                .exits(List.of("Enter", "Lonely Lake", "Field"))
                .build();
        locations.put("Green Garden", greenGarden);

        Location field = Location.builder()
                .title("Field")
                .exits(List.of("Lonely Lake", "Green Garden", "Dock", "Gorge"))
                .build();
        locations.put("Field", field);

        Location gorge = Location.builder()
                .title("Gorge")
                .exits(List.of("Field", "Wasteland"))
                .build();
        locations.put("Gorge", gorge);

        Location wasteland = Location.builder()
                .title("Wasteland")
                .exits(List.of("Gorge"))
                .npc(List.of("Naruto"))
                .build();
        locations.put("Wasteland", wasteland);

        Location dock = Location.builder()
                .title("Dock")
                .exits(List.of("Field", "Desert"))
                .npc(List.of("Hinata"))
                .build();
        locations.put("Dock", dock);

        Location desert = Location.builder()
                .title("Desert")
                .exits(List.of("Dock", "Temple"))
                .isAvailable((User user) -> user.getItems().contains(items.get(1)))
                .build();
        locations.put("Desert", desert);

        Location temple = Location.builder()
                .title("Temple")
                .exits(List.of("Desert", "Exit"))
                .build();
        locations.put("Temple", temple);

        Location exit = Location.builder()
                .title("Exit")
                .exits(List.of("Temple"))
                .isAvailable((User user) -> user.getItems().contains(items.get(2)))
                .build();
        locations.put("Exit", exit);

        log.debug("Collection [locations] initialized - " + locations);
    }

    private void questInit() {
        Quest mainQuest = Quest.builder()
                .id(1)
                .questText("Найдите выход из WinterLand, для этого найдите Temple (Храм)")
                .isFinished((Object object) -> {
                    User user = (User) object;
                    return user.getCurrentLocation()
                            .equals("Exit");
                })
                .build();
        quests.put(mainQuest.getId(), mainQuest);

        Quest boatQuest = Quest.builder()
                .id(2)
                .questText("Найдите весла, для лодки, что бы переплыть через озеро")
                .isFinished((Object obj) -> {
                    User user = (User) obj;
                    return user.getItems().contains(items.get(1));
                })
                .build();
        quests.put(boatQuest.getId(), boatQuest);

        Quest keyQuest = Quest.builder()
                .id(3)
                .questText("Найди ключь чтобы открыть дверь в закрытую комнату (Exit)")
                .isFinished((Object obj) -> {
                    User user = (User) obj;
                    return user.getItems().contains(items.get(2));
                })
                .build();
        quests.put(keyQuest.getId(), keyQuest);

        log.debug("Collection  [quests] initialized - " + quests);

    }

    private void initDialog() {
        Dialog narutoGreetings = Dialog.builder()
                .id(0)
                .text("Привет меня Зовут Наруто, чем я могу тебе помочь?")
                .build();
        dialogs.put(narutoGreetings.getId(), narutoGreetings);

        Dialog nothingAnswer = Dialog.builder()
                .id(1)
                .text("Просто проходил мимо.")
                .build();
        dialogs.put(nothingAnswer.getId(), nothingAnswer);

        Dialog giveMeAnswer = Dialog.builder()
                .id(2)
                .text("У тебя есть что-то для одинокого путника?")
                .build();
        dialogs.put(giveMeAnswer.getId(), giveMeAnswer);

        Dialog takeSomeThingAnswer = Dialog.builder()
                .id(3)
                .text("Вот держи, я давно жду кому бы отдать это старое весло!" +
                        ", и помни для дверей в Храме нужен ключ!")
                .build();
        dialogs.put(takeSomeThingAnswer.getId(), takeSomeThingAnswer);

        Dialog nothingToGiveAnswer = Dialog.builder()
                .id(4)
                .text("Ничего у меня нет, иди куда шол!") // todo шол?
                .build();
        dialogs.put(nothingToGiveAnswer.getId(), nothingToGiveAnswer);

        Dialog thanksAndGive = Dialog.builder()
                .id(5)
                .text("Спасибо, я возьму это!")
                .questId(2)
                .build();

        Dialog nothingToGive = Dialog.builder()
                .id(6)
                .text("Нет мне это уже не нужно!")
                .build();

        narutoGreetings.addAnswer(nothingAnswer, giveMeAnswer);
        giveMeAnswer.setNextDialogId(takeSomeThingAnswer.getId());
        takeSomeThingAnswer.addAnswer(thanksAndGive);

        Dialog hinataGreetings = Dialog.builder()
                .id(7)
                .text("Привет путник, меня зовут Хината, хочеш попасть на другой берег озера?")
                .build();
        hinataGreetings.addAnswer(nothingAnswer);
        dialogs.put(hinataGreetings.getId(), hinataGreetings);

        Dialog helpAnswerHinata = Dialog.builder()
                .id(8)
                .text("Да хочу, ты мне поможеш?")
                .build();
        hinataGreetings.addAnswer(helpAnswerHinata);
        dialogs.put(helpAnswerHinata.getId(), helpAnswerHinata);

        Dialog getHinataQuest = Dialog.builder()
                .id(9)
                .text("У меня есть старая надувная лодка, но нет весел. Вот если бы ты нашол весла я бы тебе " +
                        "помогла. Помню у Наруто были.")
                .build();
        dialogs.put(getHinataQuest.getId(), getHinataQuest);

        helpAnswerHinata.setNextDialogId(getHinataQuest.getId());

        Dialog goToNaruto = Dialog.builder()
                .id(10)
                .text("Спасибо, пойду его искать")
                .questId(2)
                .build();
        dialogs.put(goToNaruto.getId(), goToNaruto);

        getHinataQuest.addAnswer(goToNaruto);

        log.debug("Collection  [dialogs] initialized - " + dialogs);

    }


    private void npsInit() {
        Npc naruto = Npc.builder()
                .npcName("Naruto")
                .dialogId(0)
                .build();
        nps.put("Naruto", naruto);

        Npc hinata = Npc.builder()
                .npcName("Hinata")
                .dialogId(7)
                .build();
        nps.put("Hinata", hinata);

        log.debug("Collection  [nps] initialized - " + nps);

    }

    private void initItem() {
        Item someItem = new Item(1, "Вёсла для Резиновой лодки");
        items.put(someItem.getId(), someItem);
        Npc naruto = nps.get("Naruto");
        naruto.setItemId(1);

        Item mainKay = new Item(2, "Большой ржавый ключь от дверного замка");
        items.put((mainKay.getId()), mainKay);
        Location big_tree = locations.get("Big Tree");
        big_tree.getItems().add(mainKay);

        log.debug("Collection  [items] initialized - " + items);

    }

    public Map<String, Location> getLocations() {
        return Map.copyOf(locations);
    }

    public Map<String, Npc> getNps() {
        return Map.copyOf(nps);
    }

    public Map<Integer, Quest> getQuests() {
        return Map.copyOf(quests);
    }

    public Map<Integer, Dialog> getDialogs() {
        return Map.copyOf(dialogs);
    }

    public Map<Integer, Item> getItems() {
        return Map.copyOf(items);
    }
}
