package org.dgashuk.web_game.service;

import org.dgashuk.web_game.model.Quest;
import org.dgashuk.web_game.model.User;
import org.dgashuk.web_game.service.exceptions.UserServiceException;
import org.dgashuk.web_game.storage.Repository;
import org.dgashuk.web_game.utils.GameData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    GameData data;

    @Mock
    Repository<Integer, User> repository;

    private UserService userService;

    @BeforeEach
    void init(){
        data = new GameData();
        userService = new UserService(data,repository);
    }

    @Test
    void test_createNewUser_throw_when_userName_isBlank() {
        assertThrows(UserServiceException.class,() ->{
           userService.createNewUser("");
        });
    }

    @Test
    void test_createNewUser(){
        String testUserName = "TestName";

        User testUser = User.builder()
                .id(1)
                .name(testUserName)
                .currentLocation("Enter")
                .build();
        Quest quest = data.getQuests().get(1);
        testUser.addQuest(quest);

        User actualUser = userService.createNewUser(testUserName);
        assertEquals(testUser,actualUser);
    }

    @Test
    void test_get_By_id() {
        String testUserName = "TestName";

        User testUser = User.builder()
                .id(1)
                .name(testUserName)
                .currentLocation("Enter")
                .build();
        Quest quest = data.getQuests().get(1);
        testUser.addQuest(quest);

        Mockito.doReturn(testUser).when(repository).get(1);

        User actual = userService.get(1);
        assertEquals(testUser,actual);
    }

    @Test
    void test_save_throw_UserServiceException() {
        assertThrows(UserServiceException.class,() -> {
            userService.save(null);
        });
    }
}