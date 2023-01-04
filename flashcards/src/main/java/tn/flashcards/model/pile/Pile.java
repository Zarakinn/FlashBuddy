package tn.flashcards.model.pile;

import java.util.ArrayList;

public class Pile {
    protected String name;
    protected String uniqueId;
    protected String creator;
    protected ArrayList<Card> cards;
    private int nextCardId;
    
    public Pile() {
        this.name = "Titre de la pile";
        this.uniqueId = "a";
        this.creator = "";
        this.cards = new ArrayList<Card>();
        this.nextCardId = 0;
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

    public void createCard() {
        this.cards.add(new Card(this.nextCardId));
        this.nextCardId ++;
    }
    public void addCard(Card c) {
        this.cards.add(c.copyWithId(this.nextCardId));
        this.nextCardId ++;
    }
    public Card getCard(int i) {
        return this.cards.get(i);
    }
}
