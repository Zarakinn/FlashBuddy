package tn.flashcards.model.user;

import java.util.HashMap;

import tn.flashcards.model.user.lastPlayed.LastPlayed;
import tn.flashcards.model.user.lastPlayed.Played;

public class User {
    protected String name;
    protected HashMap<String, LastPlayed> lastPlayedHM;

    public User() {
        // TODO : vérifier si peut être importer depuis un fichier
        this.name = "Utilisateur";
        this.lastPlayedHM = new HashMap<String, LastPlayed>();
    }

    public void setName(String n) {
        this.name = n;
    }

    public String getName() {
        return this.name;
    }

    public void addPlayed(String pileId, Integer cardId, Played played) {
        this.lastPlayedHM.get(pileId).addPlayed(cardId, played);
    }

    public LastPlayed getLastPlayed(String pileId) {
        return this.lastPlayedHM.get(pileId);
    }

    public Played getPlayed(String pileId, Integer cardId) {
        return this.lastPlayedHM.get(pileId).getPlayed(cardId);
    }

}
