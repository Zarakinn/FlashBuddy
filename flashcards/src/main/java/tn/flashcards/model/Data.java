package tn.flashcards.model;

import java.util.ArrayList;

import tn.flashcards.model.pile.Pile;
import tn.flashcards.model.user.User;

public class Data {
    // Singleton
    private static Data INSTANCE;
    protected User user;
    protected ArrayList<Pile> pile;

    private Data() {
        this.user = new User();
        this.pile = new ArrayList<Pile>();
        // TODO : vérifier si peut être importer depuis un fichier
    }

    public static Data getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Data();
        }
        return INSTANCE;
    }

}
