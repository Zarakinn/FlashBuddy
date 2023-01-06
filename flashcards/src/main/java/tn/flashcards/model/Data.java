package tn.flashcards.model;

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
import tn.flashcards.model.stats.StatsPile;

import lombok.Setter;

@Getter @Setter
public class Data extends SujetObserve {
    // Singleton
    private static Data INSTANCE;
    protected HashMap<String, StatsPile> statsPile;
    protected ObservableList<Pile> piles;

    public enum Mode {APPRENTISSAGE_SELECTION, PARTIE_EN_COURS, EDITION_SELECTION, STATS, PARAM, EDIT_PILE}
    private Mode mode;

    private Pile currentPile;
    private Pile currentTrainingPile ;
    private Card currentTrainingCard ;
    private Settings settings ;

    private Data() {
        this.mode = Mode.APPRENTISSAGE_SELECTION;
        this.statsPile = new HashMap<String, StatsPile>();
        this.piles = FXCollections.observableArrayList();
        this.settings = new Settings();
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
    public void addPile(Pile p) {
        //TODO - Faire des vérifications, pile n'est pas déja dedans
        this.piles.add(p) ;
        StatsPile pile = new StatsPile(p.getUniqueId());
        this.statsPile.put(p.getUniqueId(), pile) ;
    }

    /*
    METHODES COMPLEXES POUR MODIFIER LE MODELE
     */

    public Pile createPile() {
        Pile p = new Pile() ;
        this.addPile(p) ;
        this.notifierObservateur() ;
        return p;
    }

    public Pile clonePile(Pile p)
    {
        Pile clone = new Pile();
        clone.setName(p.getName());
        clone.setCreator(p.getCreator());
        clone.setDesc(p.getDesc());
        clone.setTags(p.getTags());
        for (Card card: p.getCards())
        {
            clone.addCard(cloneCard(card));
        }
        return clone;
    }

    public Card cloneCard(Card c)
    {
        Card clone = new Card(c.getId());
        QuestionReponse q = c.getQuestion();
        QuestionReponse r = c.getReponse();
        clone.setQuestion(new QuestionReponse(q.getType(),q.getContent()));
        clone.setReponse(new QuestionReponse(r.getType(),r.getContent()));
        return clone;
    }

    public Card createDefaultCard(String pileId)
    {
        return createCard(pileId,QRType.TEXT,"",QRType.TEXT,"");
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
        // Suppression de la pile
        pile.getCards().remove(card);

        // Suppression des stats
        StatsPile sp = this.getStatsPile().get(pile.getUniqueId());
        sp.getLastStats().remove(card.getId());
        int i = 0;
        while (i < sp.getFullStats().size()) {
            if (sp.getFullStats().get(i).getCardId() == card.getId()) {
                sp.getFullStats().remove(i);
            } else {
                i ++;
            }
        }

        // Obs
        notifierObservateur();
    }

    public void setMode(Mode mode) {
        this.mode = mode;
        if (mode == Mode.STATS) {

        }
        this.notifierObservateur();
    }

    public void scoreCard(int score) {
        FullStats fs = new FullStats(score, this.currentTrainingCard.getId()) ;
        this.getStatsPile().get(this.currentTrainingPile.getUniqueId()).addPlayed(this.currentTrainingCard.getId(), fs);
    }
}
