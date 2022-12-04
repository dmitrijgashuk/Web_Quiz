package org.dgashuk.web_game.model;

import lombok.Builder;
import lombok.Data;

import java.util.function.Predicate;

@Builder
@Data
public class Quest {
    private Integer id;
    private String questText;
    private Predicate<Object> isFinished;

    public boolean checkFinishQuest(Object object){
        return isFinished.test(object);
    }

}
