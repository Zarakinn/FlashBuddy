package tn.flashcards.model.stats;

import tn.flashcards.Utils.DateFormat;

import java.time.LocalDateTime;

public class LastStats {
    LocalDateTime playDate;
    int difficulty;

    public LastStats(int difficulty) {
        this.playDate = LocalDateTime.now();
        this.difficulty = difficulty;
    }

    public LastStats(LocalDateTime playDate, int difficulty) {
        this.playDate = playDate;
        this.difficulty = difficulty;
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
