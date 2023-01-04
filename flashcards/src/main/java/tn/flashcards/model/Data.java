package tn.flashcards.model;

import java.util.ArrayList;
import java.util.HashMap;


import tn.flashcards.model.pile.Card;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tn.flashcards.model.pile.Card;
import tn.flashcards.model.pile.Pile;
import tn.flashcards.model.pile.QRType;
import tn.flashcards.model.pile.QuestionReponse;
import tn.flashcards.model.settings.Settings;
import tn.flashcards.model.stats.StatsPile;

import lombok.Getter;
import lombok.Setter;

public class Data extends SujetObserve {
    // Singleton
    private static Data INSTANCE;
    protected HashMap<String, StatsPile> statsPile;
    protected ObservableList<Pile> piles;

    @Getter @Setter
    private Pile currentPile ;

    @Getter
    private Settings settings ;

    private Data() {
        this.statsPile = new HashMap<String, StatsPile>();
        this.piles = FXCollections.observableArrayList();

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

    public ObservableList<Pile> getPiles() {
        return this.piles;
    }


    //Recuperer une pile specifique
    public Pile getAPile(String id) {
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
    METHODES COMPLEXES POUR MODIFIER LE MODELE
     */

    public Pile createPile(String name, String creator) {
        Pile p = new Pile() ;
        p.setName(name);
        p.setCreator(creator);
        this.addPile(p) ;
        this.notifierObservateur() ;
        return p;
    }

    public Card createDefaultCard(String pileId)
    {
        return createCard(pileId,QRType.TEXT,"question",QRType.TEXT,"réponse");
    }

    public Card createCard(String pileId, QRType qType, String question, QRType rType, String reponse) {
        Pile p = this.getAPile(pileId) ;
        Card c = new Card(p.getNextCardId()) ;
        c.setQuestion(new QuestionReponse(qType, question));
        c.setReponse(new QuestionReponse(rType, reponse));
        p.addCard(c);
        this.notifierObservateur();
        return c;
    }

}
