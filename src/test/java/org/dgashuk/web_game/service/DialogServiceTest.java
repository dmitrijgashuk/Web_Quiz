package org.dgashuk.web_game.service;

import org.dgashuk.web_game.model.Npc;
import org.dgashuk.web_game.utils.GameData;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;

class DialogServiceTest {

    @Mock
    GameData data;
    @Mock
    Npc npc;

    @Test
    void getCurrentNpc() {
    }

    @Test
    void test_getNpcDialog_throw_NumberFormatException() {
        DialogService dialogService = new DialogService(data);
        assertThrows(RuntimeException.class, () -> {
            dialogService.getNpcDialog(npc,null);
        });
    }

    @Test
    void processingQuestDialog() {
    }
}