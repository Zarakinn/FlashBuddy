package tn.flashcards.model;

import org.junit.Test;
import tn.flashcards.model.pile.Card;
import tn.flashcards.model.pile.QRType;

import static org.junit.Assert.assertEquals;

public class TestCard {

    public String createPile() {
        Data.getInstance().createPile() ;
        return Data.getInstance().getPiles().get(Data.getInstance().getPiles().size()-1).getUniqueId() ;
    }

    @Test
    public void testCreateCard() {
        Data d = Data.getInstance() ;
        String id = createPile() ;
        d.createCard(id, QRType.TEXT, "my_text", QRType.IMAGE, "my_url") ;

        assertEquals(1, d.getAPile(id).getCards().size()) ;

        Card c = d.getAPile(id).getCards().get(0);

        assertEquals(QRType.TEXT, c.getQuestion().getType());
        assertEquals("my_text", c.getQuestion().getContent());
        assertEquals(QRType.IMAGE, c.getReponse().getType());
        assertEquals("my_url", c.getReponse().getContent());
        assertEquals(0, c.getId());

        d.createCard(id, QRType.TEXT, "my_text", QRType.IMAGE, "my_url") ;
        d.createCard(id, QRType.TEXT, "my_text", QRType.IMAGE, "my_url") ;
        d.createCard(id, QRType.TEXT, "my_text", QRType.IMAGE, "my_url") ;

        assertEquals(4, d.getAPile(id).getCards().size());
        assertEquals(3, d.getAPile(id).getCards().get(3).getId());
    }

    @Test
    public void testCreateDefaultCard() {
        Data d = Data.getInstance() ;
        String id = createPile() ;
        d.createDefaultCard(id) ;

        assertEquals(1, d.getAPile(id).getCards().size()) ;

        Card c = d.getAPile(id).getCards().get(0);

        assertEquals(QRType.TEXT, c.getQuestion().getType());
        assertEquals("", c.getQuestion().getContent());
        assertEquals(QRType.TEXT, c.getReponse().getType());
        assertEquals("", c.getReponse().getContent());
        assertEquals(0, c.getId());
    }

    @Test
    public void testCloneCard() {
        Data d = Data.getInstance() ;
        String id = createPile() ;
        d.createCard(id, QRType.IMAGE, "efzrthrz", QRType.TEXT, "azertyuikjhg") ;
        Card c = d.getAPile(id).getCards().get(0);
        Card c2 = d.cloneCard(c) ;

        assertEquals(c.getQuestion().getType(), c2.getQuestion().getType());
        assertEquals(c.getQuestion().getContent(), c2.getQuestion().getContent());
        assertEquals(c.getReponse().getType(), c2.getReponse().getType());
        assertEquals(c.getReponse().getContent(), c2.getReponse().getContent());
        assertEquals(c.getId(), c2.getId());

    }

    @Test
    public void testDeleteCard() {
        Data d = Data.getInstance() ;
        String id = createPile() ;
        d.createCard(id, QRType.TEXT, "grsgr", QRType.IMAGE, "aaaa") ;
        d.createCard(id, QRType.TEXT, "esrt", QRType.IMAGE, "zzzz") ;
        d.createCard(id, QRType.TEXT, "cccc", QRType.IMAGE, "eeeel") ;
        d.createCard(id, QRType.TEXT, "ssss", QRType.IMAGE, "rrrr") ;

        int nbCards = d.getAPile(id).getCards().size() ;
        d.deleteCard(d.getAPile(id), d.getAPile(id).getCards().get(2));
        assertEquals(nbCards - 1, d.getAPile(id).getCards().size());
        assertEquals(0, d.getAPile(id).getCards().get(0).getId());
        assertEquals(1, d.getAPile(id).getCards().get(1).getId());
        assertEquals(3, d.getAPile(id).getCards().get(2).getId());
    }
}
