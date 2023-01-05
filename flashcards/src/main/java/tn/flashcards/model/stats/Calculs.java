package tn.flashcards.model.stats;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

import tn.flashcards.model.pile.Card;

import tn.flashcards.model.Data;
import tn.flashcards.model.pile.Pile;

public class Calculs {
    /**
     * Il prend une pile et renvoie un tableau de 6 entiers : le nombre de cartes
     * avec chaque difficulté
     * (indice 0-4) et les cartes non jouées (indice 5).
     * 
     * @param p la pile pour laquelle vous voulez obtenir le camembert
     * @return Un tableau d'entiers.
     */
    public static int[] camembert(Pile p) {
        int[] res = { 0, 0, 0, 0, 0, 0 };

        HashMap<Integer, LastStats> ls = Data.getInstance().getStatsPile().get(p.getUniqueId()).getLastStats();
        ArrayList<Card> cards = p.getCards();

        LastStats ls_thisCard;
        for (Card c : cards) {
            ls_thisCard = ls.get(c.getId());
            if (ls_thisCard == null || ls_thisCard.getDifficulty() > 4 || ls_thisCard.getDifficulty() < 0) {
                res[5]++;
            } else {
                res[ls_thisCard.getDifficulty()]++;
            }
        }
        
        return res;
    }

    /**
     * IRenvoie un tableau de 6 entiers : le nombre de cartes
     * avec chaque difficulté
     * (indice 0-4) et les cartes non jouées (indice 5).
     * 
     * @param p la pile pour laquelle vous voulez obtenir le camembert
     * @return Un tableau d'entiers.
     */
    public static int[] camembert() {
        int[] res = { 0, 0, 0, 0, 0, 0 };
        int[] loc;

        for (Pile p : Data.getInstance().getPiles()) {
            loc = camembert(p);
            for (int i = 0; i < 6; i++) {
                res[i] = res[i] + loc[i];
            }
        }

        return res;
    }

    /**
     * Etant donné une pile de cartes, renvoie un tableau de 10 entiers,
     * représentant le nombre de parties
     * pour chacun des dix derniers jours.
     * 
     * @param p un objet Pile
     */
    public static int[] tenLastDays(Pile p) {
        int[] res = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };

        LocalDateTime d = LocalDateTime.now();
        StatsPile sp = Data.getInstance().getStatsPile().get(p.getUniqueId());

        if (sp.getFullStats().size() == 0) {
            return res;
        }

        int j = 0;
        FullStats fs = sp.getFullStats().get(j);
        for (int i = 9; i >= 0; i--) {
            while (j < sp.getFullStats().size() && isSameDay(d, fs.getPlayDate())) {
                
                res[i] ++;
                
                j++;
            }
            d = d.minusDays(1);
        }

        return res;
    }

    /**
     * Renvoie vrai si les deux dates sont le même jour
     * 
     * @param d1 La première date à comparer.
     * @param d2 La date à comparer.
     * @return Une valeur booléenne.
     */
    private static boolean isSameDay(LocalDateTime d1, LocalDateTime d2) {

        String s1 = d1.format(DateFormat.getDayFormater());
        String s2 = d2.format(DateFormat.getDayFormater());

        return (s1 == s2);
    }
}
