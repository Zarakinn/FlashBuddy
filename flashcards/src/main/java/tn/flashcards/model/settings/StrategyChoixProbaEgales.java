package tn.flashcards.model.settings;

import tn.flashcards.model.Data;
import tn.flashcards.model.pile.Card;

import java.util.ArrayList;
import java.util.Random;

public class StrategyChoixProbaEgales implements StrategyChoix {
    @Override
    public Card execute() {
        ArrayList<Card> cards = Data.getInstance().getCurrentPile().getCards() ;
        var index = new Random().nextInt(cards.size());
        return cards.get(index);
    }
}
