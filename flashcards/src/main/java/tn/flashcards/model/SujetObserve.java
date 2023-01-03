package tn.flashcards.model;

import tn.flashcards.controller.Observateur;

import java.util.ArrayList;

public class SujetObserve {

    private final ArrayList<Observateur> observateurs = new ArrayList<>();
    public void ajouterObservateur(Observateur observateur) {
        observateurs.add(observateur);
    }
    public void notifierObservateur() {
        observateurs.forEach(Observateur::reagir);
    }
}