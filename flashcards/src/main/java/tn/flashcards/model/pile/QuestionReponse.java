package tn.flashcards.model.pile;

public class QuestionReponse {


    private QRType type ;
    private String content ;

    public QuestionReponse(QRType t, String c) {
        this.type = t ;
        this.content = c ;
    }

    public QRType getType() {
        return type;
    }

    public void setType(QRType type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
