package tn.flashcards.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;


import tn.flashcards.model.pile.Card;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;
import tn.flashcards.model.pile.Card;
import tn.flashcards.model.pile.Pile;
import tn.flashcards.model.pile.QRType;
import tn.flashcards.model.pile.QuestionReponse;
import tn.flashcards.model.settings.AlgoAffichage;
import tn.flashcards.model.settings.Settings;
import tn.flashcards.model.settings.StrategyChoix;
import tn.flashcards.model.settings.Theme;
import tn.flashcards.model.stats.StatsPile;

import lombok.Getter;
import lombok.Setter;

@Getter
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
        this.settings = new Settings();
        this.addPile(new Pile("user1", "pile1", "moi", new ArrayList<Card>(), 0, "tag1"));
        this.addPile(new Pile("user1", "pile1", "moi", new ArrayList<Card>(), 0, "tag2"));
        this.addPile(new Pile("user1", "pile1", "moi", new ArrayList<Card>(), 0, "tag3"));


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
        StatsPile pile = new StatsPile(p.getUniqueId());
        //pile.setLastOpened(LocalDateTime.now());
        this.statsPile.put(p.getUniqueId(), pile) ;
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

    public void deletePile(Pile pile){
        // TODO
    }
}
