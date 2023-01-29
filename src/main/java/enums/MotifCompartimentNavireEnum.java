package main.java.enums;

import main.java.model.ConsoleColors;

public enum MotifCompartimentNavireEnum {
    DETRUIT(ConsoleColors.RED +"╳╳"+ConsoleColors.RESET),
    CACHE(ConsoleColors.WHITE_BOLD+"██"+ConsoleColors.RESET),
    CUIRASE(ConsoleColors.GREEN+"**"+ConsoleColors.RESET),
    CROISEUR(ConsoleColors.GREEN+"oo"+ConsoleColors.RESET),
    DESTROYER(ConsoleColors.GREEN+"++"+ConsoleColors.RESET),
    SOUSMARIN(ConsoleColors.GREEN+"ss"+ConsoleColors.RESET);
    private final String motifCompartimentNavire;

    MotifCompartimentNavireEnum(String motifCompartimentNavire) {
        this.motifCompartimentNavire = motifCompartimentNavire;
    }

    public String getMotifCompartimentNavire() {
        return motifCompartimentNavire;
    }
}
