package tn.flashcards.model.pile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
public class Pile {

    private static int ID = 0;
    protected String name;
    protected String uniqueId;
    protected String creator;
    protected ArrayList<Card> cards;
    private int nextCardId;
    private String tags;

    public Pile() {
        this.name = "Nom de la pile";
        this.uniqueId = Integer.toString(ID);
        this.creator = "";
        this.cards = new ArrayList<Card>();
        this.nextCardId = 0;
        this.incrID();
    }

    public void incrID() {
        ID++;
    }

    public Card createCard() {
        this.cards.add(new Card(this.nextCardId));
        this.nextCardId++;
        return this.cards.get(this.cards.size() - 1);
    }

    public Card getCard(int i) {
        return this.cards.get(i);
    }

    public void addCard(Card c) {
        this.cards.add(c);
        this.nextCardId++;
    }
}
