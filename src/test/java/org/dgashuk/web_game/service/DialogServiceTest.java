package org.dgashuk.web_game.service;

import org.dgashuk.web_game.model.Dialog;
import org.dgashuk.web_game.model.Npc;
import org.dgashuk.web_game.utils.GameData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DialogServiceTest {

    @Mock
    GameData data;
    @Mock
    Npc npc;

    private DialogService dialogService;

    @BeforeEach
    void init(){
        dialogService = new DialogService(data);
    }

    @Test
    void test_getCurrentNpc() {
        Npc testNpc = Npc.builder()
                .npcName("Naruto")
                .dialogId(0)
                .build();

        Map<String, Npc> testNpcMap = new HashMap<>();
        testNpcMap.put(testNpc.getNpcName(),testNpc);

        Mockito.doReturn(testNpcMap).when(data).getNps();
        Npc naruto = dialogService.getCurrentNpc("Naruto");

        assertEquals(testNpc,naruto);
    }

    @Test
    void test_getNpcDialog_throw_NumberFormatException() {
        assertThrows(RuntimeException.class, () -> {
            dialogService.getNpcDialog(npc,null);
        });
    }

    @Test
    void test_getNpcDialog_When_Dialog_Not_Null(){
        Dialog testDialog = Dialog.builder()
                .id(1)
                .nextDialogId(0)
                .text("Hi friends!")
                .build();

        Map<Integer, Dialog> testNpcMap = new HashMap<>();
        testNpcMap.put(testDialog.getId(),testDialog);

        Mockito.doReturn(testNpcMap).when(data).getDialogs();

        Dialog actual = dialogService.getNpcDialog(npc, "1");
        assertEquals(testDialog,actual);

    }

    @Test
    void test_getNpcDialog_When_Dialog_is_Null(){
        Dialog testDialog = Dialog.builder()
                .id(1)
                .nextDialogId(0)
                .text("Hi friends!")
                .build();

        Map<Integer, Dialog> testNpcMap = new HashMap<>();
        testNpcMap.put(testDialog.getId(),testDialog);

        Mockito.doReturn(testNpcMap).when(data).getDialogs();

        Mockito.doReturn(1).when(npc).getDialogId();

        Dialog actual = dialogService.getNpcDialog(npc, null);
        assertEquals(testDialog,actual);
    }

    @Test
    void processingQuestDialog() { // todo
    }
}