package tn.flashcards.model;

import org.junit.Test;
import tn.flashcards.model.pile.Card;
import tn.flashcards.model.pile.Pile;
import tn.flashcards.model.pile.QRType;
import tn.flashcards.model.pile.QuestionReponse;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

public class TestPile {

    public Pile myPile() {
        Pile p = new Pile() ;
        p.setName("My Pile");
        p.setCreator("Me");

        for (int i = 0 ; i < 5 ; i++) {
            Card c = new Card(p.getNextCardId()) ;
            c.setReponse(new QuestionReponse(QRType.TEXT, ""));
            c.setQuestion(new QuestionReponse(QRType.TEXT, ""));
            p.addCard(c);
        }

        p.setDesc("My description");
        p.setTags("My tag");
        return p ;
    }

    @Test
    public void testCreatePile() {
        Data d = Data.getInstance() ;
        int nbPiles = d.getPiles().size() ;
        d.createPile() ;
        assertEquals(nbPiles + 1, d.getPiles().size()) ;

        assertEquals(nbPiles + 1, d.getStatsPile().size());

        Pile p = d.getPiles().get(d.getPiles().size() - 1) ;
        assertEquals("Pile sans nom", p.getName());
        assertEquals(d.getSettings().getAuteur(), p.getCreator());
        assertEquals("", p.getDesc());
        assertEquals(0, p.getCards().size());
        assertEquals("", p.getTags());
        assertEquals(0, p.getNextCardId());
    }

    @Test
    public void testAddABuiltPile() {
        Pile p = myPile() ;

        Data d = Data.getInstance() ;
        int nbPiles = d.getPiles().size() ;
        d.addPile(p);
        assertEquals(nbPiles + 1, d.getPiles().size());

        Pile p2 = d.getPiles().get(d.getPiles().size() - 1) ;
        assertEquals(p, p2) ;
    }

    @Test
    public void testClonePile() {
        Pile p = myPile() ;
        Pile p2 = Data.getInstance().clonePile(p) ;

        assertEquals(p2.getName(), p.getName());
        assertEquals(p2.getCreator(), p.getCreator());
        assertEquals(p2.getTags(), p.getTags());
        assertEquals(p2.getDesc(), p.getDesc());
        assertEquals(p2.getCards().size(), p.getCards().size());
        assertEquals(p2.getNextCardId(), p.getNextCardId());
        assertNotSame(p2.getUniqueId(), p.getUniqueId());
    }
}
