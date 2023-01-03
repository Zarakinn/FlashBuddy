package tn.flashcards.Utils;

import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import tn.flashcards.model.Stack;


import com.google.gson.Gson;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class FileHandler {


    public static Stack LoadStack(Window window, String url)
    {
        FileChooser fc = new FileChooser();
        fc.setTitle("Selectionner une pile");
        File file = fc.showOpenDialog(window);
        if (file != null) {
            try {
                Gson gson = new Gson();
                Stack stack = gson.fromJson(new FileReader(file),Stack.class);
                return stack;
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR,"Fichier non valid, veuillez sélectionner un JSON créé par cette application.");
                alert.show();
            }
        }
        return null;
    }

    public static void SaveStack(Stack stack)
    {
        SaveStackAs(stack, String.valueOf(stack.hashCode()) + ".json");
    }

    public static void SaveStackAs(Stack stack, String name)
    {
        Gson gson = new Gson();
        String json = gson.toJson(stack);
        try {
            //TODO - trouver un vrai nom
            FileWriter fw = new FileWriter(name);
            fw.write(json);
            fw.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR,"Il y a eu une erreur dans l'enregistrement de la pile.");
            alert.show();
        }
    }

}
