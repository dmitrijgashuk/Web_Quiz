package org.dgashuk.web_game.model;

import lombok.Builder;
import lombok.Data;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Builder
@Data
public class Dialog {
    private Integer id;
    private String text;
    @Builder.Default
    private List<Dialog> answerList = new ArrayList<>();
    private Integer nextDialogId;
    private Integer questId;

    public void addAnswer(Dialog ... answers){
        Collections.addAll(answerList, answers);
    }

}
