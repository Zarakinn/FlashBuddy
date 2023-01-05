package tn.flashcards.Utils;

import com.google.gson.GsonBuilder;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import tn.flashcards.model.pile.Pile;


import com.google.gson.Gson;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class FileHandler {


    public static Pile LoadStack(Window window) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Selectionner une pile");
        File file = fc.showOpenDialog(window);
        if (file != null) {
            try {
                Gson gson = new Gson();
                Pile stack = gson.fromJson(new FileReader(file), Pile.class);
                return stack;
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Fichier non valid, veuillez sélectionner un JSON créé par cette application.");
                alert.show();
            }
        }
        return null;
    }

    public static void SaveStack(Pile stack) {
        SaveStackAs(stack, String.valueOf(stack.getName()) + ".json");
    }

    public static void SaveStackAs(Pile stack, String name) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(stack);
        try {
            FileWriter fw = new FileWriter(name);
            fw.write(json);
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Il y a eu une erreur dans l'enregistrement de la pile.");
            alert.show();
        }
    }

}
