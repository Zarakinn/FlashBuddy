package tn.flashcards.model.pile;

public class Card {
    int id;
    QuestionReponse question;
    QuestionReponse reponse;

    public Card(int id) {
        this.id = id;
        this.question = null;
        this.reponse = null;
    }

    public int getId() {
        return this.id;
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

    public Card copyWithId(int nextCardId) {
        // TODO
        return null;
    }
}
