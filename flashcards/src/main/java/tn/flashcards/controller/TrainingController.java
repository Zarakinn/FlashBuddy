package tn.flashcards.controller;


import atlantafx.base.controls.RingProgressIndicator;
import javafx.concurrent.Task;
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
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;
import tn.flashcards.components.ActionButtonTableCell;
import tn.flashcards.model.Data;
import tn.flashcards.model.pile.Card;
import tn.flashcards.model.pile.Pile;
import tn.flashcards.model.stats.StatsPile;
import tn.flashcards.VisualFactory.QRViewFactory;

import java.net.URL;
import java.util.ResourceBundle;

import static atlantafx.base.theme.Styles.*;
import static tn.flashcards.components.ActionButtonTableCell.GRADIENT_BTN;

public class TrainingController implements Initializable, Observateur {

    @FXML
    private VBox errorMsg;
    @FXML
    private BorderPane trainingView;
    @FXML
    private Label pileName;
    @FXML
    private Pane question, reponse, pileList, aucune_carte_pan;
    @FXML
    private Button showAnsButton;
    @FXML
    private HBox scoreButtons, cardView;
    @FXML
    private RingProgressIndicator timerRing;

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

    private Task task;

    public enum ModeTraining {SELECTION, NO_CARTE, JOUER}

    private ModeTraining mode = ModeTraining.SELECTION;

    public TrainingController() {

    }

    public void setMode(ModeTraining mode) {
        this.mode = mode;
        pileList.setVisible(false);
        aucune_carte_pan.setVisible(false);
        trainingView.setVisible(false);

        switch (mode) {
            case SELECTION -> {
                pileList.setVisible(true);

            }
            case NO_CARTE -> {
                aucune_carte_pan.setVisible(true);

            }
            case JOUER -> {
                trainingView.setVisible(true);
                startTraining();
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        createViewTable();
        this.task = new Task() {
            @Override
            protected Void call() throws Exception {
                return null;
            }
        };
        setMode(ModeTraining.SELECTION);
    }

    @Override
    public void reagir() {
        if (Data.getInstance().getMode() == Data.Mode.APPRENTISSAGE_SELECTION) {
            table.refresh();
        }
    }

    private void nextCardView() {
        this.question.getChildren().removeAll(this.question.getChildren());
        this.reponse.getChildren().removeAll(this.reponse.getChildren());

        if (Data.getInstance().getCurrentPile() == null
                || Data.getInstance().getCurrentPile().getCards().size() == 0) {
            setMode(ModeTraining.NO_CARTE);
            return;
        }
        Data.getInstance().getSettings().getAlgoChoix().execute();
        Card c = Data.getInstance().getCurrentTrainingCard();
        
        this.question.getChildren().add(QRViewFactory.createQRView(c.getQuestion()));
        this.reponse.getChildren().add(QRViewFactory.createQRView(c.getReponse()));
        this.setQuestionView();
    }

    private void setQuestionView() {
        this.scoreButtons.setVisible(false);
        this.showAnsButton.setVisible(true);
        this.reponse.setVisible(false);

        int timer = Data.getInstance().getSettings().getTimerAffichage();

        if (timer > 0) {
            this.timerRing.setVisible(true);
            this.timerRing.setStringConverter(new StringConverter<Double>() {
                @Override
                public String toString(Double aDouble) {
                    Double res = aDouble * timer;
                    return Integer.toString(res.intValue()) + "s";
                }

                @Override
                public Double fromString(String s) {
                    String number = s.substring(0, s.length() - 2);
                    return Double.parseDouble(number);
                }
            });

            this.timerRing.setProgress(0);
            this.task = new Task() {

                @Override
                protected Void call() throws Exception {
                    int steps = timer * 20;
                    for (int i = 0; i <= steps; i++) {

                        if (isCancelled()) {
                            break;
                        }

                        Thread.sleep(timer * 1000L / steps);
                        updateProgress(i, steps);
                        updateMessage(i + "s");
                    }
                    return null;
                }
            };

            this.task.setOnSucceeded(e -> {
                this.setReponseView();
            });

            this.timerRing.progressProperty().bind(this.task.progressProperty());

            new Thread(this.task).start();
        }
    }

    private void setReponseView() {
        this.scoreButtons.setVisible(true);
        this.showAnsButton.setVisible(false);
        this.reponse.setVisible(true);
        this.timerRing.setVisible(false);
        this.timerRing.progressProperty().unbind();
        this.task.cancel(true);
    }

    @FXML
    public void startTraining() {
        Pile p = Data.getInstance().getCurrentTrainingPile();
        if (p.getCards().size() == 0) {
            setMode(ModeTraining.NO_CARTE);
        }

        // Incrémente le nombre de fois qu'on a ouvert cette pile
        Data.getInstance().getStatsPile().get(p.getUniqueId()).incrNoJeuxPile();
        Data.getInstance().getStatsPile().get(p.getUniqueId()).updateLastOpened();

        this.question.setPrefWidth(this.cardView.getWidth() / 2);
        this.reponse.setPrefWidth(this.cardView.getWidth() / 2);

        this.nextCardView();
    }

    @FXML
    public void closeTraining() {
        Data.getInstance().setCurrentTrainingPile(null);
        setMode(ModeTraining.SELECTION);
        //FileHandler.saveStats();
    }

    @FXML
    public void affReponse() {
        setReponseView();
    }

    @FXML
    public void scoreCard(ActionEvent e) {

        String button = ((Button) e.getSource()).getId();
        int score = switch (button) {
            case "1" -> 1;
            case "2" -> 2;
            case "3" -> 3;
            case "4" -> 4;
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
                    Data.getInstance().setCurrentTrainingPile(p);
                    setMode(ModeTraining.JOUER);
                    return p;
                }));

        table.setItems(Data.getInstance().getPiles());
        table.setEditable(false);
    }
}