package org.dgashuk.web_game.service;

import org.dgashuk.web_game.dto.LocationData;
import org.dgashuk.web_game.model.Item;
import org.dgashuk.web_game.model.Location;
import org.dgashuk.web_game.model.Quest;
import org.dgashuk.web_game.model.User;
import org.dgashuk.web_game.utils.GameData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class LocationService {
    private static final Logger log = LoggerFactory.getLogger(LocationService.class);

    private final GameData game;

    public LocationService(GameData game) {
        this.game = game;
    }

    public void putLocationDataToRequest(User user, HttpServletRequest request) {
        Location location = getLocationByName(user.getCurrentLocation());

        List<Item> itemsOnTheLocation = availableItemsOnTheLocation(user,location);

        LocationData locationData = LocationData.builder()
                .user(user)
                .availableLocation(getAvailableLocation(user))
                .availableQuests(getAvailableQuests(user))
                .npc(location.getNpc())
                .items(itemsOnTheLocation)
                .finishQuests(getFinishedQuests(user))
                .build();

        log.debug("Use locationService to add data: ");
        request.setAttribute("npc", locationData.getNpc());
        log.debug("Get 'npc' from locationService and set it to attribute request: " + locationData.getNpc());
        request.setAttribute("items", locationData.getItems());
        log.debug("Get 'items' from locationService and set it to attribute request: " + locationData.getItems());
        request.setAttribute("finishQuests", locationData.getFinishQuests());
        log.debug("Get 'finishQuests' from locationService and set it to attribute request: " + locationData.getFinishQuests());
        request.setAttribute("availableQuests", locationData.getAvailableQuests());
        log.debug("Get 'availableQuests' from locationService and set it to attribute request: " + locationData.getAvailableQuests());
        request.setAttribute("availableLocation", locationData.getAvailableLocation());
        log.debug("Get 'availableLocation' from locationService and set it to attribute request: " + locationData.getAvailableLocation());

    }

    public Item pickUpItem(String itemId, User user) {
        Integer id = null;
        try {
            id = Integer.parseInt(itemId);
        } catch (NumberFormatException e) {
            throw new RuntimeException("String - " + itemId + " can`t parser to int ", e);
        }
        Map<Integer, Item> items = game.getItems();
        Item item = items.get(id);
        user.getItems().add(item);
        Location location = getLocationByName(user.getCurrentLocation());
        log.debug("User pick up " + item.getItemName() + "in location: " + location.getTitle());
        return item;
    }

    public List<Location> getAvailableLocation(User user) { // todo rewrite like stream
        List<Location> availableLocationList = new ArrayList<>();

        String currentLocation = user.getCurrentLocation();
        Location location = getLocationByName(currentLocation);

        List<String> exits = location.getExits();
        for (String exit : exits) {
            Location existLocation = getLocationByName(exit);
            if (existLocation.isLocationAvailable(user)) {
                availableLocationList.add(existLocation);
            }
        }
        return availableLocationList;
    }

    public Location getLocationByName(String locationName) {
        Map<String, Location> locations = game.getLocations();
        return locations.get(locationName);
    }

    public List<Quest> getFinishedQuests(User user) {// todo rewrite look like stream
        List<Quest> finishQuests = new ArrayList<>();
        List<Quest> questList = user.getQuestList();
        for (Quest quest : questList) {
            if (quest.checkFinishQuest(user)) {
                finishQuests.add(quest);
            }
        }
        return finishQuests;
    }

    public List<Quest> getAvailableQuests(User user) {
        List<Quest> availableList = new ArrayList<>();
        List<Quest> questList = user.getQuestList();
        for (Quest quest : questList) {
            if (!quest.checkFinishQuest(user)) {
                availableList.add(quest);
            }
        }
        return availableList;
    }

    private List<Item> availableItemsOnTheLocation(User user, Location location){
        List<Item> items = new ArrayList<>(location.getItems());
        items.removeIf(nextItem -> user.getItems().contains(nextItem));
        return items;
    }

}
