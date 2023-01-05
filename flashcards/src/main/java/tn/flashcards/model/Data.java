package tn.flashcards.model;

import java.util.ArrayList;
import java.util.HashMap;


import tn.flashcards.model.pile.Card;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;
import tn.flashcards.model.pile.Pile;
import tn.flashcards.model.pile.QRType;
import tn.flashcards.model.pile.QuestionReponse;
import tn.flashcards.model.settings.Settings;
import tn.flashcards.model.stats.FullStats;
import tn.flashcards.model.settings.StrategyChoix;
import tn.flashcards.model.settings.Theme;
import tn.flashcards.model.stats.StatsPile;

import lombok.Setter;

@Getter @Setter
public class Data extends SujetObserve {
    // Singleton
    private static Data INSTANCE;
    protected HashMap<String, StatsPile> statsPile;
    protected ObservableList<Pile> piles;

    public enum Mode {APPRENTISSAGE_SELECTION, PARTIE_EN_COURS, EDITION, STATS, PARAM, EDIT_PILE}
    private Mode mode;

    private Pile currentTrainingPile;
    private Card currentTrainingCard ;
    private Settings settings ;

    private Data() {
        this.mode = Mode.APPRENTISSAGE_SELECTION;
        this.statsPile = new HashMap<String, StatsPile>();
        this.piles = FXCollections.observableArrayList();
        this.settings = new Settings();
        this.addPile(new Pile("user1", "_0", "moi", new ArrayList<Card>(), 0, "tag1"));
        this.addPile(new Pile("user1", "_1", "moi", new ArrayList<Card>(), 0, "tag2"));
        this.addPile(new Pile("user1", "_2", "moi", new ArrayList<Card>(), 0, "tag3"));


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

    public void deleteCard(Pile pile, Card card)
    {
        pile.getCards().remove(card);
        notifierObservateur();
    }

    public void setMode(Mode mode) {
        this.mode = mode;
        this.notifierObservateur();
    }

    public void deletePile(Pile pile){
        // TODO
    }

    public void scoreCard(int score) {
        FullStats fs = new FullStats(score, this.currentTrainingCard.getId()) ;
        this.getStatsPile().get(this.currentTrainingPile.getUniqueId()).addPlayed(this.currentTrainingCard.getId(), fs);
    }
}
