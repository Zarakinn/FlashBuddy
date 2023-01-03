package tn.flashcards;

import atlantafx.base.theme.NordDark;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tn.flashcards.controller.HelloController;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Objects;

public class Launcher extends Application {
    @Override
    public void start(Stage stage) throws IOException, URISyntaxException {
        // Création modèle
        // TODO

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(Launcher.class.getResource("fxml/hello-view.fxml"));

        // Création des controlleurs
        var HelloController = new HelloController();

        // model.ajouterObservateur(HelloController);
        // ...

        fxmlLoader.setControllerFactory(ic -> {
            if (ic.equals(HelloController.class)) return HelloController;
            // ...
            return null;
        });

        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 1000, 700);
        scene.getStylesheets().add(Objects.requireNonNull(Launcher.class.getResource("css/style.css")).toURI().toString());
        stage.setScene(scene);
        stage.show();

        Application.setUserAgentStylesheet(new NordDark().getUserAgentStylesheet());
        //Application.setUserAgentStylesheet(new PrimerDark().getUserAgentStylesheet());
    }

    public static void main(String[] args) {
        launch();
    }
}