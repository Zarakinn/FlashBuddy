package tn.flashcards.model.settings;

import java.util.ArrayList;
import java.lang.Math;

import tn.flashcards.model.Data;
import tn.flashcards.model.pile.Card;
import tn.flashcards.model.stats.LastStats;

public class StrategyChoixProba implements StrategyChoix {
    private static double probas[] = { 0.0, 0.5, 1.0, 1.5, 2.0 };

    public static void setProbas(int i, double v) {
        probas[i] = v;
    }

    public static double getProbas(int i) {
        return probas[i];
    }

    @Override
    public void execute() {
        boolean cardChosen = false ;

        ArrayList<Card> cards = Data.getInstance().getCurrentTrainingPile().getCards();
        String currentPileId = Data.getInstance().getCurrentTrainingPile().getUniqueId();
        double s = 0;
        ArrayList<Double> difficulties = new ArrayList<Double>();

        Card c;
        for (int i = 0; i < cards.size(); i++) {
            c = cards.get(i);
            LastStats ls = Data.getInstance().getStatsPile().get(currentPileId).getLastStats().get(c.getId());
            int difficulty;
            if (ls == null) {
                difficulty = 2;
            } else {
                difficulty = ls.getDifficulty();
            }
            difficulties.add(probas[difficulty]);
            s = s + probas[difficulty];
        }

        if (s < 0.1 /* est nul */) {
            /* Les cartes ne sont pas jouables (toutes évidentes) */
            StrategyChoix sc = new StrategyChoixProbaEgales();
            sc.execute();
        } else {
            /* Les cartes sont jouables */
            double p = Math.random() * s;
    
            double localSum = 0;
            for (int i = 0; i < difficulties.size(); i++) {
                localSum = localSum + difficulties.get(i);
                if (localSum > p) {
                    Data.getInstance().setCurrentTrainingCard(cards.get(i));
                    cardChosen = true ;
                    break ;
                }
            }
    
            if (!cardChosen) {
                Data.getInstance().setCurrentTrainingCard(null);
            }
        }

    }
}
