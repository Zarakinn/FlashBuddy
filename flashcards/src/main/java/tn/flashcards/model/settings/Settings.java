package tn.flashcards.model.settings;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class Settings {
    protected StrategyChoix algoChoix;
    protected int timerAffichage ;
    protected Theme theme;

    public Settings() {
        this.algoChoix = new StrategyChoixProbaEgales();
        this.timerAffichage = -1 ;
        this.theme = Theme.FONCE;
    }
}
