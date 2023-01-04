package tn.flashcards.model;

import java.util.ArrayList;
import java.util.HashMap;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tn.flashcards.model.pile.Card;
import tn.flashcards.model.pile.Pile;
import tn.flashcards.model.stats.StatsPile;

public class Data extends SujetObserve {
    // Singleton
    private static Data INSTANCE;
    protected HashMap<String, StatsPile> statsPile;
    protected ArrayList<Pile> piles;

    private Data() {
        this.statsPile = new HashMap<String, StatsPile>();
        this.piles = new ArrayList<Pile>();

        piles.add(new Pile("user1", "pile1", "moi", new ArrayList<Card>(), 0, "tag1"));
        piles.add(new Pile("user1", "pile1", "moi", new ArrayList<Card>(), 0, "tag2"));
        piles.add(new Pile("user1", "pile1", "moi", new ArrayList<Card>(), 0, "tag3"));


        // TODO : vérifier si peut être importer depuis un fichier
    }

    // Singleton
    public static Data getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Data();
        }
        return INSTANCE;
    }

    public ArrayList<Pile> getPiles() {
        return this.piles;
    }

    //Recuperer unr pile specifique
    public Pile getAPile(String id) {
        for (Pile p:this.piles) {
            if (p.getUniqueId().equals(id)) {
                return p ;
            }
        }

        return null ;
    }

    // Ajouter une pile
    public void addPile(Pile p) {
        this.piles.add(p) ;
        this.statsPile.put(p.getUniqueId(), new StatsPile(p.getUniqueId())) ;
    }
}
