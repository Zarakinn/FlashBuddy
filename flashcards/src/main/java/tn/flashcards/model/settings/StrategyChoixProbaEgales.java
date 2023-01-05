package tn.flashcards.model.settings;

import tn.flashcards.model.Data;
import tn.flashcards.model.pile.Card;

import java.util.ArrayList;
import java.util.Random;

public class StrategyChoixProbaEgales implements StrategyChoix {
    @Override
    public void execute() {
        ArrayList<Card> cards = Data.getInstance().getCurrentTrainingPile().getCards() ;

        if (cards.size() > 0) {
            var index = new Random().nextInt(cards.size());
            Data.getInstance().setCurrentTrainingCard(cards.get(index));
        }
        else {
            Data.getInstance().setCurrentTrainingCard(null);
        }

    }
}
