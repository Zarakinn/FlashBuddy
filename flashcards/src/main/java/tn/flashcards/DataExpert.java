package tn.flashcards;

import tn.flashcards.model.Data;
import tn.flashcards.model.pile.Card;
import tn.flashcards.model.pile.Pile;
import tn.flashcards.model.pile.QRType;
import tn.flashcards.model.pile.QuestionReponse;

public class DataExpert {

    private Data data ;

    public DataExpert() {
        this.data = Data.getInstance() ;
    }

    public void addPile(String name, String creator) {
        Pile p = new Pile() ;
        p.setName(name);
        p.setCreator(creator);
        this.data.addPile(p) ;
        this.data.notifierObservateur() ;
    }

    public void addCard(String pileId, QRType qType, String question, QRType rType, String reponse) {
        Card c = this.data.getAPile(pileId).createCard() ;
        c.setQuestion(new QuestionReponse(qType, question));
        c.setReponse(new QuestionReponse(rType, reponse));
        this.data.notifierObservateur();
    }


}
