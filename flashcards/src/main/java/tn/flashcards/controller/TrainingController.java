package tn.flashcards.controller;


import javafx.event.ActionEvent;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.*;
import javafx.util.converter.IntegerStringConverter;
import tn.flashcards.components.ActionButtonTableCell;
import tn.flashcards.model.Data;
import tn.flashcards.model.pile.Card;
import tn.flashcards.model.pile.Pile;
import tn.flashcards.model.stats.StatsPile;
import tn.flashcards.view.QRView;
import tn.flashcards.view.QRViewFactory;

import java.net.URL;
import java.util.ResourceBundle;

import static atlantafx.base.theme.Styles.*;
import static tn.flashcards.components.ActionButtonTableCell.GRADIENT_BTN;

public class TrainingController implements Initializable, Observateur {


    @FXML
    private Pane pileList;
    @FXML
    private BorderPane trainingView;
    @FXML
    private Label pileName;
    @FXML
    private HBox cardView;
    @FXML
    private Button showAnsButton;
    @FXML
    private HBox scoreButtons;

    @FXML
    TableColumn<Pile, String> cName, cCreateur, cTags;
    @FXML
    TableColumn<Pile, Integer> cNb;
    @FXML
    TableColumn<Pile, String> cDate;
    @FXML
    TableColumn<Pile, Button> cJouer;
    @FXML
    TableView<Pile> table;


    public TrainingController() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        createViewTable();
    }

    @Override
    public void reagir() {
        if (Data.getInstance().getMode() == Data.Mode.APPRENTISSAGE_SELECTION) {
            table.refresh();
        }
    }

    private void nextCardView() {
        Data.getInstance().getSettings().getAlgoChoix().execute();
        Card c = Data.getInstance().getCurrentTrainingCard();

        this.cardView.getChildren().removeAll(this.cardView.getChildren());


        if (c != null) {
            QRView q = QRViewFactory.createQRView(c.getQuestion());
            QRView r = QRViewFactory.createQRView(c.getReponse());

            q.setPrefWidth(this.cardView.getWidth() / 2 - 20);
            r.setPrefWidth(this.cardView.getWidth() / 2 - 20);
            q.getStyleClass().add("view");
            r.getStyleClass().add("view");

            this.cardView.getChildren().add(q);
            this.cardView.getChildren().add(r);

            this.cardView.getChildren().get(1).setVisible(false);
        }
    }

    @FXML
    public void startTraining() {
        this.pileList.setVisible(false);
        this.trainingView.setVisible(true);

        Pile p = Data.getInstance().getCurrentPile();
        this.pileName.setText(p.getName());

        this.nextCardView();

    }

    @FXML
    public void closeTraining() {
        this.pileList.setVisible(true);
        this.trainingView.setVisible(false);
        Data.getInstance().setCurrentPile(null);
    }

    @FXML
    public void affReponse() {
        this.scoreButtons.setVisible(true);
        this.showAnsButton.setVisible(false);
        this.cardView.getChildren().get(1).setVisible(true);
    }

    @FXML
    public void scoreCard(ActionEvent e) {

        String button = ((Button) e.getSource()).getText();
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

    public void createViewTable() {
        cName.setCellFactory(TextFieldTableCell.forTableColumn());
        cName.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getName()));

        cCreateur.setCellFactory(TextFieldTableCell.forTableColumn());
        cCreateur.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getCreator()));

        cTags.setCellFactory(TextFieldTableCell.forTableColumn());
        cTags.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getTags()));

        cNb.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        cNb.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getCards().size()));

        cDate.setCellFactory(TextFieldTableCell.forTableColumn());
        cDate.setCellValueFactory(data -> {
            StatsPile pile = Data.getInstance().getStatsPile().get(data.getValue().getUniqueId());
            if (pile == null) {
                return new ReadOnlyObjectWrapper<>("—");
            }
            return new ReadOnlyStringWrapper(pile.getLastOpenedFormated());
        });


        cJouer.setCellFactory(ActionButtonTableCell.forTableColumn(
                "Lancer le test",
                new String[]{SUCCESS, GRADIENT_BTN},
                null,
                (Pile p) -> {
                    Data.getInstance().setCurrentPile(p);
                    startTraining();
                    return p;
                }));

        table.setItems(Data.getInstance().getPiles());
        table.setEditable(false);
    }
}