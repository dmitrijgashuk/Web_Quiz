package org.dgashuk.web_game.service;

import org.dgashuk.web_game.model.Quest;
import org.dgashuk.web_game.model.User;
import org.dgashuk.web_game.service.exceptions.UserServiceException;
import org.dgashuk.web_game.storage.Repository;
import org.dgashuk.web_game.utils.GameData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class UserService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private Repository<Integer, User> users;
    private GameData game;
    private AtomicInteger userCount = new AtomicInteger();

    public UserService(GameData game, Repository<Integer, User> userRepository) {
        this.users = userRepository;
        this.game = game;
    }

    public User createNewUser(String userName) { // todo
        if (userName.isBlank()) {
            String errorMessage = "UserName in method 'createNewUser' parameter is empty or null";
            log.error("errorMessage");
            throw new UserServiceException(errorMessage);
        }

        Quest startQuest = game.getQuests().get(1);
        String startGameLocation = "Enter";

        User user = User.builder()
                .id(userCount.incrementAndGet())
                .name(userName)
                .currentLocation(startGameLocation)
                .build();

        user.addQuest(startQuest);
        log.debug("Create a new 'user' - " + userName);
        return user;
    }

    public User get(String userName) {
        if (userName.isBlank()) {
            throw new UserServiceException("UserName parameter is empty or null");
        }

        Iterator<Map.Entry<Integer, User>> iterator = users.iterator(); // todo rewrite on stream style
        while (iterator.hasNext()) {
            Map.Entry<Integer, User> next = iterator.next();
            User value = next.getValue();
            if (value.getName().equals(userName)) {
                return value;
            }
        }
        log.debug("Repository can not find user that has name: " + userName);
        return null;
    }

    public User get(int id) {
        return users.get(id);
    }

    public int save(User user) {
        if (user == null) {
            throw new UserServiceException("We can not save 'null' user");
        }

        if (user.getId() == null) {
            int id = userCount.incrementAndGet();
            user.setId(id);
            users.save(user.getId(), user);
            log.debug("Set 'id'-" + id + " to 'user-'" + user.getName() + " and save it to repository");
            return user.getId();
        }

        users.save(user.getId(), user);
        log.debug("save 'user-'" + user.getName() + " with 'id'-" + user.getId() + " to repository");
        return user.getId();
    }

    public void updateUser(User user){
        users.save(user.getId(),user);
    }

}
