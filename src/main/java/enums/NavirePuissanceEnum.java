package main.java.enums;

public enum NavirePuissanceEnum {
    CUIRASE(9),
    CROISEUR(4),
    DESTROYER(1),
    SOUSMARIN(1);
    private final int puissanceNavire;

    NavirePuissanceEnum(int puissanceNavire) {
        this.puissanceNavire = puissanceNavire;
    }

    public int getPuissanceNavire() {
        return puissanceNavire;
    }
}
