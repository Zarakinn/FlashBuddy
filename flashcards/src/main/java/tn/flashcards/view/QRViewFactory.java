package tn.flashcards.view;

import tn.flashcards.model.pile.QRType;
import tn.flashcards.model.pile.QuestionReponse;

public class QRViewFactory {


    public static QRView createQRView(QuestionReponse qr) {
        if (qr.getType() == QRType.TEXT) {
            return new QRTextView(qr.getContent()) ;
        }
        else if (qr.getType() == QRType.IMAGE) {
            return new QRImageView(qr.getContent(), 200, 200) ;
        }

        return null ;
    }
}
