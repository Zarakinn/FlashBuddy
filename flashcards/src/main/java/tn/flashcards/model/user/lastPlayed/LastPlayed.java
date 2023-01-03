package tn.flashcards.model.user.lastPlayed;

import java.util.HashMap;

public class LastPlayed {
    protected String pileId;
    protected HashMap<Integer, Played> playedHM;

    public LastPlayed(String pileId) {
        this.pileId = pileId;
        this.playedHM = new HashMap<Integer, Played>();
    }

    public String getPileId() {
        return this.pileId;
    }

    public void addPlayed(Integer cardId, Played played) {
        this.playedHM.put(cardId, played);
    }

    public Played getPlayed(Integer cardId) {
        return this.playedHM.get(cardId);
    }
}
