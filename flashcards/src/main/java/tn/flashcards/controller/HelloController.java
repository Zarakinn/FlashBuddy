package tn.flashcards.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}