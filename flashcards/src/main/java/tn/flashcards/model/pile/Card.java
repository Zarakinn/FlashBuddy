package tn.flashcards.model.pile;

public class Card {
    int id;
    QuestionReponse question;
    QuestionReponse reponse;

    public Card(int id) {
        this.id = id;
        this.question = new QuestionReponse(QRType.TEXT, "");
        this.reponse = new QuestionReponse(QRType.TEXT, "");
    }

    public int getId() {
        return this.id;
    }

    public Card copyWithId(int id) {
        Card c = new Card(this.id);
        c.setQuestion(this.question);
        c.setReponse(this.reponse);
        return c;
    }

    public QuestionReponse getQuestion() {
        return this.question;
    }
    public void setQuestion(QuestionReponse qr) {
        this.question = qr;
    }

    public QuestionReponse getReponse() {
        return this.reponse;
    }
    public void setReponse(QuestionReponse qr) {
        this.reponse = qr;
    }
}
