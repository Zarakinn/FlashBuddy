module tn.flashcards {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;
    requires atlantafx.base;
    requires com.google.gson;

    opens tn.flashcards to javafx.fxml;
    exports tn.flashcards;
    exports tn.flashcards.controller;
    opens tn.flashcards.controller to javafx.fxml;
}