package tn.flashcards.model.settings;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class Settings {
    protected StrategyChoix algoChoix;
    protected AlgoAffichage algoAffichage;
    protected Theme theme;
}
