package tn.flashcards.model;

import org.junit.Test;
import tn.flashcards.model.pile.Card;
import tn.flashcards.model.pile.Pile;
import tn.flashcards.model.stats.StatsPile;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class TestStats {

    private Data d = Data.getInstance() ;

    @Test
    public void testPileStatsExist() {
        int nbPileStats = d.getStatsPile().size() ;
        Pile p = d.createPile() ;
        assertEquals(nbPileStats + 1, d.getStatsPile().size()) ;
        assertNotNull(d.getStatsPile().get(p.getUniqueId()));

        Pile p2 = d.createPile() ;
        assertEquals(nbPileStats + 2, d.getStatsPile().size()) ;
        assertNotNull(d.getStatsPile().get(p2.getUniqueId()));

        Pile p3 = d.createPile() ;
        assertEquals(nbPileStats + 3, d.getStatsPile().size()) ;
        assertNotNull(d.getStatsPile().get(p3.getUniqueId()));
    }


    @Test
    public void testPileStatsRemoved() {
        Pile p1 = d.createPile() ;
        Pile p2 = d.createPile() ;
        Pile p3 = d.createPile() ;

        d.deletePile(p2) ;
        assertNotNull(d.getStatsPile().get(p1.getUniqueId()));
        assertNull(d.getStatsPile().get(p2.getUniqueId()));
        assertNotNull(d.getStatsPile().get(p3.getUniqueId()));

        d.deletePile(p1) ;
        assertNull(d.getStatsPile().get(p1.getUniqueId()));
        assertNull(d.getStatsPile().get(p2.getUniqueId()));
        assertNotNull(d.getStatsPile().get(p3.getUniqueId()));

        d.deletePile(p3) ;
        assertNull(d.getStatsPile().get(p1.getUniqueId()));
        assertNull(d.getStatsPile().get(p2.getUniqueId()));
        assertNull(d.getStatsPile().get(p3.getUniqueId()));
    }

    @Test
    public void testScoreSaved() {
        Pile p = d.createPile();
        StatsPile sp = d.getStatsPile().get(p.getUniqueId()) ;
        Card c = d.createDefaultCard(p.getUniqueId()) ;
        d.setCurrentTrainingPile(p);
        d.setCurrentTrainingCard(c);

        int n = sp.getNoJeuxCarte() ;
        int nLast = sp.getLastStats().size() ;
        int nFull = sp.getFullStats().size() ;

        d.scoreCard(1);

        assertEquals(n+1, sp.getNoJeuxCarte());

        assertEquals(nLast+1, sp.getLastStats().size());
        assertNotNull(sp.getLastStats().get(c.getId()));
        assertEquals(1, sp.getLastStats().get(c.getId()).getDifficulty());

        assertEquals(nFull+1, sp.getFullStats().size());
        assertEquals(sp.getFullStats().get(0).getDifficulty(), 1);
        assertEquals(sp.getFullStats().get(0).getCardId(), c.getId());

        LocalDateTime time = sp.getLastStats().get(c.getId()).getPlayDate() ;

        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e) {
            System.out.println("InterruptedException");
        }
        d.scoreCard(2);

        assertEquals(n+2, sp.getNoJeuxCarte());

        assertEquals(nLast+1, sp.getLastStats().size());
        assertNotNull(sp.getLastStats().get(c.getId()));
        assertEquals(2, sp.getLastStats().get(c.getId()).getDifficulty());

        assertEquals(nFull+2, sp.getFullStats().size());
        assertEquals(sp.getFullStats().get(0).getDifficulty(), 1);
        assertEquals(sp.getFullStats().get(0).getCardId(), c.getId());
        assertEquals(sp.getFullStats().get(1).getDifficulty(), 2);
        assertEquals(sp.getFullStats().get(1).getCardId(), c.getId());

        assertTrue(time.compareTo(sp.getLastStats().get(c.getId()).getPlayDate()) < 0);

    }

    @Test
    public void testScoreSaved2() {
        Pile p = d.createPile();
        StatsPile sp = d.getStatsPile().get(p.getUniqueId()) ;
        Card c1 = d.createDefaultCard(p.getUniqueId()) ;
        Card c2 = d.createDefaultCard(p.getUniqueId()) ;
        Card c3 = d.createDefaultCard(p.getUniqueId()) ;
        d.setCurrentTrainingPile(p);
        d.setCurrentTrainingCard(c1);
        d.scoreCard(1);
        d.setCurrentTrainingCard(c3);
        d.scoreCard(3);
        d.scoreCard(4);

        assertEquals(3, sp.getNoJeuxCarte());

        assertEquals(2, sp.getLastStats().size());

        assertNotNull(sp.getLastStats().get(c1.getId()));
        assertNull(sp.getLastStats().get(c2.getId()));
        assertNotNull(sp.getLastStats().get(c3.getId()));

        assertEquals(1, sp.getLastStats().get(c1.getId()).getDifficulty());
        assertEquals(4, sp.getLastStats().get(c3.getId()).getDifficulty());

        assertEquals(3, sp.getFullStats().size());

        assertEquals(1, sp.getFullStats().get(0).getDifficulty());
        assertEquals(c1.getId(), sp.getFullStats().get(0).getCardId());

        assertEquals(3, sp.getFullStats().get(1).getDifficulty());
        assertEquals(c3.getId(), sp.getFullStats().get(1).getCardId());

        assertEquals(4, sp.getFullStats().get(2).getDifficulty());
        assertEquals(c3.getId(), sp.getFullStats().get(2).getCardId());

    }

    @Test
    public void testCardStatsRemoved() {
        Pile p = d.createPile();
        StatsPile sp = d.getStatsPile().get(p.getUniqueId()) ;
        Card c1 = d.createDefaultCard(p.getUniqueId()) ;
        d.setCurrentTrainingPile(p);
        d.setCurrentTrainingCard(c1);
        d.scoreCard(1);

        assertNotNull(sp.getLastStats().get(c1.getId()));
        assertEquals(1, sp.getFullStats().size());

        d.deleteCard(p, c1);

        assertNull(sp.getLastStats().get(c1.getId())) ;
        assertEquals(0, sp.getFullStats().size());
    }





}
