package tn.flashcards.components;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import tn.flashcards.model.Data;
import tn.flashcards.model.pile.Card;
import tn.flashcards.model.pile.Pile;
import tn.flashcards.model.pile.QRType;
import tn.flashcards.view.QRImageView;
import tn.flashcards.view.QRView;
import tn.flashcards.view.QRViewFactory;

import java.io.File;
import java.util.Arrays;

public class EditCell extends ListCell<Card> {

    HBox view;
    ComboBox<QRType> QuestionType; // TODO - à remplacer par l'énum type de carte

    ComboBox<QRType> AnswerType;

    Button editQuestion, editAnswer, deleteCard;

    Pile pile;

    public EditCell(Pile pile)
    {
        this.pile = pile;
    }

    public void createComboBox(Card c)
    {
        QuestionType = new ComboBox<QRType>();
        ObservableList<QRType> oS = FXCollections.observableList(Arrays.asList(QRType.TEXT,QRType.IMAGE));
        QuestionType.setItems(oS);
        QuestionType.setValue(c.getQuestion().getType());

        QuestionType.setOnAction(
                event -> {
                    switch (QuestionType.getValue())
                    {
                        case TEXT:
                            c.getQuestion().setType(QRType.TEXT);
                            break;
                        case IMAGE:
                            c.getQuestion().setType(QRType.IMAGE);
                            break;
                        default:
                            break;
                    }
                }
        );

        AnswerType = new ComboBox<QRType>();
        AnswerType.setItems(oS);
        AnswerType.setValue(c.getReponse().getType());
        AnswerType.setOnAction(event -> {
            switch (AnswerType.getValue())
            {
                case TEXT:
                    c.getReponse().setType(QRType.TEXT);
                    break;
                case IMAGE:
                    c.getReponse().setType(QRType.IMAGE);
                    break;
                default:
                    break;
            }

        });

    }

    public void createButton(Card c)
    {
        editQuestion = new Button("Edit");

        editQuestion.setOnAction(event -> {
            switch (QuestionType.getValue())
            {
                case TEXT:
                    GridPane form = new GridPane();

                    // Add form elements
                    TextField nameField = new TextField();
                    Button submitButton = new Button("Submit");
                    Label responseLabel = new Label();

                    form.add(new Label("Name:"), 0, 0);
                    form.add(nameField, 1, 0);
                    form.add(submitButton, 1, 1);
                    form.add(responseLabel, 1, 2);

                    Stage formStage = new Stage();
                    formStage.setTitle("Edit Form");

                    // Set event handler for submit button
                    submitButton.setOnAction( submited-> {
                        String value = nameField.getText();
                        c.getQuestion().setContent(value);
                        formStage.close();
                    });

                    Scene formScene = new Scene(form, 300, 200);
                    formStage.setScene(formScene);
                    formStage.show();
                    break;
                case IMAGE:
                    FileChooser fc = new FileChooser();
                    Window w = this.getScene().getWindow();
                    File file = fc.showOpenDialog(w);
                    if (file!=null)
                    {
                        c.getQuestion().setContent(file.toURI().toString());
                    }
                    break;
                default:
                    break;
            }
        });

        editAnswer = new Button("Edit");
        editAnswer.setOnAction(event -> {
            switch (AnswerType.getValue())
            {
                case TEXT:
                    GridPane form = new GridPane();

                    // Add form elements
                    TextField nameField = new TextField();
                    Button submitButton = new Button("Submit");
                    Label responseLabel = new Label();

                    form.add(new Label("Name:"), 0, 0);
                    form.add(nameField, 1, 0);
                    form.add(submitButton, 1, 1);
                    form.add(responseLabel, 1, 2);

                    Stage formStage = new Stage();
                    formStage.setTitle("Edit Form");

                    // Set event handler for submit button
                    submitButton.setOnAction( submited-> {
                        String value = nameField.getText();
                        c.getReponse().setContent(value);
                        formStage.close();
                    });

                    Scene formScene = new Scene(form, 300, 200);
                    formStage.setScene(formScene);
                    formStage.show();
                    break;
                case IMAGE:
                    FileChooser fc = new FileChooser();
                    Window w = this.getScene().getWindow();
                    File file = fc.showOpenDialog(w);
                    if (file!=null)
                    {
                        c.getReponse().setContent(file.toURI().toString());
                    }
                    break;
                default:
                    break;
            }
        });

        deleteCard = new Button("Delete");
        deleteCard.setOnAction(event -> {
            Data.getInstance().deleteCard(pile,c);
        });

    }

    @Override
    protected void updateItem(Card c, boolean empty)
    {
        super.updateItem(c,empty);
        if (c==null || empty)
        {
            setGraphic(null);
        }
        else
        {
            createComboBox(c);
            createButton(c);
            view = new HBox();
            view.getChildren().addAll(QuestionType,
                    QRViewFactory.createQRView(c.getQuestion()),
                    editQuestion,
                    AnswerType,
                    QRViewFactory.createQRView(c.getReponse()),
                    editAnswer,
                    deleteCard);
            view.setSpacing(10);
            setGraphic(view);
        }
    }
}
