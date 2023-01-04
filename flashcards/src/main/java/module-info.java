module tn.flashcards {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;
    requires atlantafx.base;
    requires com.google.gson;

    requires org.kordamp.ikonli.core;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.feather;
    requires org.kordamp.ikonli.material2;

    opens tn.flashcards to javafx.fxml;
    exports tn.flashcards;
    exports tn.flashcards.controller;
    opens tn.flashcards.controller to javafx.fxml;
    exports tn.flashcards.components;
    opens tn.flashcards.components to javafx.fxml;
}