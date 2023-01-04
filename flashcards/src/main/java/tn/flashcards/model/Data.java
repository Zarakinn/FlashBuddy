package tn.flashcards.model;

import java.util.ArrayList;
import java.util.HashMap;

import tn.flashcards.model.pile.Card;
import tn.flashcards.model.pile.Pile;
import tn.flashcards.model.pile.QRType;
import tn.flashcards.model.pile.QuestionReponse;
import tn.flashcards.model.stats.StatsPile;

public class Data extends SujetObserve {
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

    public ArrayList<Pile> getPiles() {
        return this.piles;
    }

    //Recuperer une pile specifique
    private Pile getAPile(String id) {
        for (Pile p:this.piles) {
            if (p.getUniqueId().equals(id)) {
                return p ;
            }
        }

        return null ;
    }

    // Ajouter une pile
    private void addPile(Pile p) {
        this.piles.add(p) ;
        this.statsPile.put(p.getUniqueId(), new StatsPile(p.getUniqueId())) ;
    }

    /*
    METHODES COMPLEXES
     */

    public void createPile(String name, String creator) {
        Pile p = new Pile() ;
        p.setName(name);
        p.setCreator(creator);
        this.addPile(p) ;
        this.notifierObservateur() ;
    }

    public void createCard(String pileId, QRType qType, String question, QRType rType, String reponse) {
        Card c = this.getAPile(pileId).createCard() ;
        c.setQuestion(new QuestionReponse(qType, question));
        c.setReponse(new QuestionReponse(rType, reponse));
        this.notifierObservateur();
    }
}
