package tn.flashcards.model.stats;

import tn.flashcards.Utils.DateFormat;

import java.time.LocalDateTime;

public class LastStats {
    LocalDateTime playDate;
    int difficulty;

    public LastStats(int difficulty) {
        this.playDate = LocalDateTime.now();
        if (difficulty >= 0 && difficulty < 5) {
            this.difficulty = difficulty;
        } else {
            this.difficulty = 2;
        }
    }

    public LastStats(LocalDateTime playDate, int difficulty) {
        this.playDate = playDate;
        if (difficulty >= 0 && difficulty < 5) {
            this.difficulty = difficulty;
        } else {
            this.difficulty = 2;
        }
    }

    public int getDifficulty() {
        return this.difficulty;
    }

    public LocalDateTime getPlayDate() {
        return this.playDate;
    }

    public String getPlayDateFormated() {
        return this.playDate.format(DateFormat.getDateTimeFormatter());
    }
}
