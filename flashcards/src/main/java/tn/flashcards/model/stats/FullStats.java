package tn.flashcards.model.stats;

import java.time.LocalDateTime;

public class FullStats extends LastStats {
    protected int cardId;

    public FullStats(int difficulty, int cardId) {
        super(difficulty);
        this.cardId = cardId;
    }

    public FullStats(int difficulty, LocalDateTime playDate, int cardId) {
        super(playDate, difficulty);
        this.cardId = cardId;
    }

    public LastStats copyAsLastStats() {
        return new LastStats(playDate, difficulty);
    }
    
    public int getCardId() {
        return this.cardId;
    }
}
