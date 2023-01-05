package tn.flashcards.model.stats;

import java.util.ArrayList;
import java.util.HashMap;

import tn.flashcards.model.pile.Card;

import tn.flashcards.model.Data;
import tn.flashcards.model.pile.Pile;

public class Calculs {
    /**
     * Il prend une pile et renvoie un tableau de 6 entiers : le nombre de cartes avec chaque difficulté 
     * (indice 0-4) et les cartes non jouées (indice 5).
     * 
     * @param p la pile pour laquelle vous voulez obtenir le camembert
     * @return Un tableau d'entiers.
     */
    public static int[] camembert(Pile p) {
        int[] res = {0, 0, 0, 0, 0, 0};

        HashMap<Integer, LastStats> ls = Data.getInstance().getStatsPile().get(p.getUniqueId()).getLastStats();
        ArrayList<Card> cards = p.getCards();

        LastStats ls_thisCard;
        for (Card c : cards) {
            ls_thisCard = ls.get(c.getId());
            if (ls_thisCard == null || ls_thisCard.getDifficulty() > 4 || ls_thisCard.getDifficulty() < 0) {
                res[6] ++;
            } else {
                res[ls_thisCard.getDifficulty()] ++;
            }
        }

        return res;
    }
}
