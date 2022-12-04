package org.dgashuk.web_game.storage;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

class RepositoryTest {

    private Map<Integer, String> map;

    private Repository<Integer, String> repository;

    @BeforeEach
    void init() {
        map = new ConcurrentHashMap<>();
        map.put(1, "stub1");
        map.put(2, "stub2");
        repository = new Repository<>(map);
    }

    @Test
    void test_get_ValueById() {
        String expected = "stub1";
        String actual = repository.get(1);
        assertEquals(expected, actual);
    }

    @Test
    void test_get_WhenKeyIsNotExist() {
        String actual = repository.get(3);
        assertEquals(null, actual);
    }

    @Test
    void test_save_newValue() {
        String newValue = "stub3";
        repository.save(3, newValue);
        assertEquals(newValue, repository.get(3), newValue);
    }

    @Test
    void test_isExist() {
        String testString = "stub1";
        boolean exist = repository.isExist(testString);
        assertTrue(exist);

    }

    @Test
    void test_isEmpty() {
        boolean empty = repository.isEmpty();
        assertFalse(empty);
    }

}