package tn.flashcards.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import tn.flashcards.model.Data;
import tn.flashcards.model.pile.Card;
import tn.flashcards.model.pile.Pile;
import tn.flashcards.view.QRView;
import tn.flashcards.view.QRViewFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class TrainingController implements Initializable, Observateur {


    @FXML
    private Pane pileList ;
    @FXML
    private BorderPane trainingView ;
    @FXML
    private Label pileName ;
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
        Data.getInstance().getSettings().getAlgoChoix().execute();
        Card c = Data.getInstance().getCurrentTrainingCard() ;
        //Card c = new Card(0) ;
        //c.setReponse(new QuestionReponse(QRType.TEXT, "question"));
        //c.setQuestion(new QuestionReponse(QRType.TEXT, "reponse"));

        this.cardView.getChildren().removeAll(this.cardView.getChildren()) ;


        if (c != null) {
            QRView q = QRViewFactory.createQRView(c.getQuestion());
            QRView r = QRViewFactory.createQRView(c.getReponse());

            q.setPrefWidth(this.cardView.getWidth() / 2 - 20);
            r.setPrefWidth(this.cardView.getWidth() / 2 - 20);
            q.getStyleClass().add("view") ;
            r.getStyleClass().add("view") ;

            this.cardView.getChildren().add(q);
            this.cardView.getChildren().add(r);

            this.cardView.getChildren().get(1).setVisible(false);
        }
    }

    @FXML
    public void startTraining() {
        this.pileList.setVisible(false);
        this.trainingView.setVisible(true);

        Pile p = Data.getInstance().getAPile("0") ;
        Data.getInstance().setCurrentTrainingPile(p);
        this.pileName.setText(p.getName());

        this.nextCardView();

    }

    @FXML
    public void closeTraining() {
        this.pileList.setVisible(true);
        this.trainingView.setVisible(false);
        Data.getInstance().setCurrentTrainingPile(null);
    }

    @FXML
    public void affReponse() {
        this.scoreButtons.setVisible(true);
        this.showAnsButton.setVisible(false);
        this.cardView.getChildren().get(1).setVisible(true);
    }

    @FXML
    public void scoreCard(ActionEvent e) {

        String button = ((Button) e.getSource()).getText() ;
        int score = switch (button) {
            case "1" -> 1;
            case "2" -> 2;
            case "3" -> 3;
            case "4" -> 4;
            case "5" -> 5;
            default -> 0;
        };

        Data.getInstance().scoreCard(score);

        this.nextCardView();

        this.scoreButtons.setVisible(false);
        this.showAnsButton.setVisible(true);
    }
}