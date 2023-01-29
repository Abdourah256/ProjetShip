package main.java.enums;

public enum ActionsNavirePossibleSurUneCarteEnum {
    OUEST("Deplacement Ouest"),
    EST("Deplacement Est"),
    NORD("Deplacement Nord"),
    SUD("Deplacement Sud"),
    ATTAQUER("Attaquer"),
    SAUVEGARDER_ET_QUITTER("Sauvegarder et quitter"),
    QUITTER("Quitter"),
    ;
    private final String action;

    ActionsNavirePossibleSurUneCarteEnum(String action) {
        this.action = action;
    }

    public String toString() {
        return action;
    }
}
