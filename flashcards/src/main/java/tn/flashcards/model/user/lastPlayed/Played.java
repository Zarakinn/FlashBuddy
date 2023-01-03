package tn.flashcards.model.user.lastPlayed;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Played {
    LocalDateTime playDate;
    int difficulty;

    public Played(int difficulty) {
        this.difficulty = difficulty;
        playDate = LocalDateTime.now();
    }

    public int getDifficulty() {
        return this.difficulty;
    }

    public LocalDateTime getPlayDate() {
        return this.playDate;
    }

    public String getFormatedPlayDate() {
        return this.playDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }
}
