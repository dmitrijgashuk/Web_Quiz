package org.dgashuk.web_game.service;

import org.dgashuk.web_game.model.Item;
import org.dgashuk.web_game.model.Location;
import org.dgashuk.web_game.model.Quest;
import org.dgashuk.web_game.model.User;
import org.dgashuk.web_game.utils.GameData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class LocationServiceTest {

    @Mock
    User user;

    @Mock
    HttpServletRequest request;

    GameData gameData;

    LocationService locationService;

    @BeforeEach
    void init() {
        gameData = new GameData();
        locationService = new LocationService(gameData);
    }


    @Test
    void test_getLocationByName() {
        Location expectedLocation = Location.builder()
                .title("Enter")
                .exits(List.of("Green Garden", "Dark Cave"))
                .build();

        Location actualLocation = locationService.getLocationByName("Enter");
        assertEquals(expectedLocation,actualLocation);
    }

    @Test
    void test_getFinishedQuests() {
        List<Quest> questList = new ArrayList<>();

        Quest mainQuest = Quest.builder()
                .id(1)
                .questText("Найдите выход из WinterLand, для этого найдите Temple (Храм)")
                .isFinished((Object object) -> {
                    User user = (User) object;
                    return user.getCurrentLocation()
                            .equals("Exit");
                })
                .build();

        Map<Integer, Item> items = gameData.getItems();

        Item mainKay = new Item(2, "Большой ржавый ключь от дверного замка");

        Location exit = Location.builder()
                .title("Exit")
                .exits(List.of("Temple"))
                .isAvailable((User user) -> user.getItems().contains(items.get(2)))
                .build();

        questList.add(mainQuest);

        Mockito.doReturn(questList).when(user).getQuestList();
        Mockito.doReturn("Exit").when(user).getCurrentLocation();

        List<Quest> finishedQuests = locationService.getFinishedQuests(user);

        assertEquals(questList, finishedQuests);

    }

    @Test
    void test_getCorrectFinishQuests(){
        Map<Integer, Quest> quests = gameData.getQuests();
        Quest quest = quests.get(1);

        doReturn("Exit").when(user).getCurrentLocation();

        List<Quest> allQuestUserList = new ArrayList<>(){{
           add(quests.get(1));
           add(quests.get(2));
        }};

        doReturn(allQuestUserList).when(user).getQuestList();

        List<Quest> finishedQuests = locationService.getFinishedQuests(user);
        assertEquals(1,finishedQuests.size());
    }

    @Test
    void getAvailableQuests() {
        List<Quest> questList = new ArrayList<>();

        Quest mainQuest = Quest.builder()
                .id(1)
                .questText("Найдите выход из WinterLand, для этого найдите Temple (Храм)")
                .isFinished((Object object) -> {
                    User user = (User) object;
                    return user.getCurrentLocation()
                            .equals("Exit");
                })
                .build();

        Map<Integer, Item> items = gameData.getItems();

        Item mainKay = new Item(2, "Большой ржавый ключь от дверного замка");

        Location exit = Location.builder()
                .title("Exit")
                .exits(List.of("Temple"))
                .isAvailable((User user) -> user.getItems().contains(items.get(2)))
                .build();

        questList.add(mainQuest);

        Mockito.doReturn(questList).when(user).getQuestList();
        Mockito.doReturn("Enter").when(user).getCurrentLocation();

        List<Quest> finishedQuests = locationService.getAvailableQuests(user);
        assertEquals(questList, finishedQuests);
    }

}