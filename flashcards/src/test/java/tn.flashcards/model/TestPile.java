package tn.flashcards.model;

import org.junit.Test;
import tn.flashcards.model.pile.Card;
import tn.flashcards.model.pile.Pile;
import tn.flashcards.model.pile.QRType;
import tn.flashcards.model.pile.QuestionReponse;

import java.util.Random;

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
    public void testDeletePile() {
        Data d = Data.getInstance() ;

        int n = d.getPiles().size() ;
        Pile p = d.createPile() ;
        d.deletePile(p);

        assertEquals(n, d.getPiles().size());

        var nb = new Random().nextInt(10);

        for (int i = 0 ;  i < nb ; i++) {
            d.createPile() ;
        }

        int nbPiles = d.getPiles().size() ;

        var nb2 = new Random().nextInt(nb);

        for (int j = 0 ; j < nb2 ; j++) {
            d.deletePile(d.getPiles().get(new Random().nextInt(d.getPiles().size())));
        }

        assertEquals(n + nb - nb2, d.getPiles().size());
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
