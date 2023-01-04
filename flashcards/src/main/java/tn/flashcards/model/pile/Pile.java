package tn.flashcards.model.pile;

import java.util.ArrayList;

public class Pile {

    private static int ID = 0 ;
    protected String name;
    protected String uniqueId;
    protected String creator;
    protected ArrayList<Card> cards;
    private int nextCardId;
    
    public Pile() {
        this.name = "Nom de la pile";
        this.uniqueId = Integer.toString(ID) ;
        this.creator = "";
        this.cards = new ArrayList<Card>();
        this.nextCardId = 0;
        this.incrID() ;
    }

    public void setName(String n) {
        this.name = n;
    }
    public String getName() {
        return this.name;
    }

    public void setCreator(String c) {
        this.creator = c;
    }
    public String getCreator() {
        return this.creator;
    }

    public String getUniqueId() {
        return this.uniqueId;
    }


    public Card createCard() {
        Card c = new Card(this.nextCardId);
        this.cards.add(c);
        this.nextCardId ++;
        return c ;
    }
    public Card getCard(int i) {
        return this.cards.get(i);
    }

    public void incrID()  {
        ID++ ;
    }
}
