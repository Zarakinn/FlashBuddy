package tn.flashcards.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import tn.flashcards.model.Data;
import tn.flashcards.model.pile.Card;
import tn.flashcards.model.pile.QRType;
import tn.flashcards.model.pile.QuestionReponse;
import tn.flashcards.view.QRViewFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class TrainingController implements Initializable, Observateur {


    @FXML
    private Pane pileList ;
    @FXML
    private Button startTraining ;
    @FXML
    private BorderPane trainingView ;
    @FXML
    private Button closeTraining ;
    @FXML
    private HBox cardView ;
    @FXML
    private Button showAnsButton ;
    @FXML
    private HBox scoreButtons ;


    public TrainingController()
    {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    @Override
    public void reagir() {

    }

    private void nextCardView() {
        //Card c = Data.getInstance().getSettings().getAlgoChoix().execute();
        Card c = new Card(0) ;
        c.setReponse(new QuestionReponse(QRType.TEXT, "question"));
        c.setQuestion(new QuestionReponse(QRType.TEXT, "reponse"));

        this.cardView.getChildren().removeAll(this.cardView.getChildren()) ;

        this.cardView.getChildren().add(QRViewFactory.createQRView(c.getQuestion())) ;
        this.cardView.getChildren().add(QRViewFactory.createQRView(c.getReponse())) ;

        this.cardView.getChildren().get(1).setVisible(false);
    }

    @FXML
    public void startTraining() {
        this.pileList.setVisible(false);
        this.trainingView.setVisible(true);

        if (this.cardView.getChildren().size() < 2) {
            this.nextCardView();
        }
    }

    @FXML
    public void closeTraining() {
        this.pileList.setVisible(true);
        this.trainingView.setVisible(false);
    }

    @FXML
    public void affReponse() {
        this.scoreButtons.setVisible(true);
        this.showAnsButton.setVisible(false);

        try {
            this.cardView.getChildren().get(1).setVisible(true);
        } catch (Exception ignored) {}
    }

    @FXML
    public void nextCard() {
        this.nextCardView();

        this.scoreButtons.setVisible(false);
        this.showAnsButton.setVisible(true);
    }
}