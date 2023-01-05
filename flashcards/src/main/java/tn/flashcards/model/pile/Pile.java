package tn.flashcards.model.pile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Random;

@Getter
@Setter
@AllArgsConstructor
public class Pile {

    private static String genUniqueId() {
        int length = 32;
        char chars[] = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '_', '-'};

        // --> 64^32 possibilités

        String res = "";
        for (int i = 0; i < length; i++) {
            res = res + chars[new Random().nextInt(chars.length)];
        }
        return res;
    }

    protected String name;
    protected String desc;
    protected String uniqueId;
    protected String creator;
    protected ArrayList<Card> cards;
    private int nextCardId;
    private String tags;

    public Pile() {
        this.name = "Nom de la pile";
        this.desc = "Description par défaut";
        this.uniqueId = Pile.genUniqueId();
        this.creator = "Créateur";
        this.cards = new ArrayList<>();
        this.nextCardId = 0;
        this.tags = "tag";
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
