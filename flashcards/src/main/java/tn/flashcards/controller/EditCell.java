package tn.flashcards.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import tn.flashcards.model.pile.Card;
import tn.flashcards.model.pile.QRType;

import java.util.Arrays;

public class EditCell extends ListCell<Card> {

    HBox view;
    ComboBox<QRType> QuestionType; // TODO - à remplacer par l'énum type de carte

    ComboBox<QRType> AnswerType;

    Label l,l2;

    Button editQuestion, editAnswer;

    public EditCell()
    {

    }

    public void createComboBox(Card c)
    {
        QuestionType = new ComboBox<QRType>();
        ObservableList<QRType> oS = FXCollections.observableList(Arrays.asList(QRType.TEXT,QRType.IMAGE));
        QuestionType.setItems(oS);
        QuestionType.getSelectionModel().selectFirst();

        QuestionType.setOnAction(
                event -> {
                    switch (QuestionType.getValue())
                    {
                        case TEXT:
                            System.out.println("La question de " + String.valueOf(c.getId()) + " devient un Text");
                            break;
                        case IMAGE:
                            System.out.println("La question de " + String.valueOf(c.getId()) + " devient une Image");
                            break;
                        default:
                            break;
                    }
                }
        );

        AnswerType = new ComboBox<QRType>();
        AnswerType.setItems(oS);
        AnswerType.getSelectionModel().selectFirst();
        AnswerType.setOnAction(event -> {
            switch (QuestionType.getValue())
            {
                case TEXT:
                    System.out.println("La réponse de " + String.valueOf(c.getId()) + " devient un Text");
                    c.getQuestion().setType(QRType.TEXT);
                    break;
                case IMAGE:
                    c.getQuestion().setType(QRType.IMAGE);
                    System.out.println("La réponse de " + String.valueOf(c.getId()) + " devient une Image");
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
                    System.out.println("Créer file chooser, modifie c sachant que c'est une image");
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
                    System.out.println("Créer file chooser, modifie c sachant que c'est une image");
                    break;
                default:
                    break;
            }
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
            l = new Label(c.getQuestion().getContent());
            l2 = new Label(c.getReponse().getContent());
            view = new HBox();
            view.getChildren().addAll(QuestionType,l, editQuestion, AnswerType, l2, editAnswer);
            view.setSpacing(10);
            setGraphic(view);
        }
    }
}
