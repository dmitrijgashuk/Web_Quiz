package org.dgashuk.web_game;

import org.dgashuk.web_game.model.*;
import org.dgashuk.web_game.service.DialogService;
import org.dgashuk.web_game.service.LocationService;
import org.dgashuk.web_game.service.UserService;
import org.dgashuk.web_game.storage.Repository;
import org.dgashuk.web_game.utils.GameData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.Map;

@WebListener
public class ContextListener implements ServletContextListener {

    private static final Logger log = LoggerFactory.getLogger(ContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        GameData mainGameData = new GameData();
        Map<String, Location> gameMap = mainGameData.getLocations();
        Map<String, Npc> npsRepository = mainGameData.getNps();
        Map<Integer, Dialog> dialogMap = mainGameData.getDialogs();
        Map<Integer, Quest> quests = mainGameData.getQuests();
        Map<Integer, Item> items = mainGameData.getItems();

        ServletContext servletContext = sce.getServletContext();
        log.debug("Servlet context init, setAttribute:");

        servletContext.setAttribute("gameMap", gameMap);
        servletContext.setAttribute("npsRepository", npsRepository);
        servletContext.setAttribute("quests", quests);
        servletContext.setAttribute("dialogMap", dialogMap);
        servletContext.setAttribute("items", items);

        log.debug("gameMap : " + gameMap);
        log.debug("npsRepository : " + npsRepository);
        log.debug("quests : " + quests);
        log.debug("dialogMap : " + dialogMap);
        log.debug("items : " + items);

        Repository<Integer,User> userInMemoryRepository = new Repository<>();
        UserService userService = new UserService(mainGameData, userInMemoryRepository);
        servletContext.setAttribute("userService", userService);

        LocationService locationService = new LocationService(mainGameData);
        servletContext.setAttribute("locationService",locationService);

        DialogService dialogService = new DialogService(mainGameData);
        servletContext.setAttribute("dialogService",dialogService);
    }

}
