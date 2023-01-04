package tn.flashcards.model.stats;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class StatsPile {
    protected String pileId;
    protected int noJeuxPile;
    protected int noJeuxCarte;
    protected LocalDateTime lastOpened;
    HashMap<Integer, LastStats> lastStats;
    ArrayList<FullStats> fullStats;

    // Const
    public StatsPile(String pileId) {
        this.pileId = pileId;
        this.noJeuxPile = 0;
        this.noJeuxCarte = 0;
        this.lastStats = new HashMap<Integer, LastStats>();
        this.fullStats = new ArrayList<FullStats>();
    }

    // NoJeuxPile
    public int getNoJeuxPile() {
        return this.noJeuxPile;
    }
    public void incrNoJeuxPile() {
        this.noJeuxPile ++;
    }

    // NoJeuxCarte
    public int getNoJeuxCarte() {
        return this.noJeuxCarte;
    }
    public void incrNoJeuxCarte() {
        this.noJeuxCarte ++;
    }

    // LastOpened
    public LocalDateTime getLastOpened() {
        return this.lastOpened;
    }
    public String getLastOpenedFormated() {
        return this.lastOpened.format(DateFormat.getDateTimeFormatter());
    }
    public void updateLastOpened() {
        this.lastOpened = LocalDateTime.now();
    }
    public void updateLastOpened(LocalDateTime lastOpened) {
        this.lastOpened = lastOpened;
    }

    // LastStat & FullStats
    public void addPlayed(Integer cardId, FullStats played) {
        this.lastStats.put(cardId, played.copyAsLastStats());
        this.fullStats.add(played);
    }

    // LastStats
    public LastStats getPlayed(Integer cardId) {
        return this.lastStats.get(cardId);
    }

    // FullStats
    public ArrayList<FullStats> getFullStats() {
        return this.fullStats;
    }
}