package tn.flashcards.model;

import java.util.ArrayList;
import java.util.HashMap;

import tn.flashcards.model.pile.Pile;
import tn.flashcards.model.stats.StatsPile;

public class Data {
    // Singleton
    private static Data INSTANCE;
    protected HashMap<String, StatsPile> statsPile;
    protected ArrayList<Pile> piles;

    private Data() {
        this.statsPile = new HashMap<String, StatsPile>();
        this.piles = new ArrayList<Pile>();
        // TODO : vérifier si peut être importer depuis un fichier
    }

    // Singleton
    public static Data getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Data();
        }
        return INSTANCE;
    }

    // Create Pile
    public void createPile() {
        Pile p = new Pile();
        this.piles.add(p);
        this.statsPile.put(p.getUniqueId(), new StatsPile(p.getUniqueId()));
    }

    public ArrayList<Pile> getPiles() {
        return this.piles;
    }
}
