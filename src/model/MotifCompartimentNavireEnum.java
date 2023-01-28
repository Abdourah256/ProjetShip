package model;

public enum MotifCompartimentNavireEnum {
    DETRUIT("╳╳"),
    CACHE("██"),
    CUIRASE("**"),
    CROISEUR("oo"),
    DESTROYER("++"),
    SOUSMARIN("ss");
    private final String motifCompartimentNavire;

    MotifCompartimentNavireEnum(String motifCompartimentNavire) {
        this.motifCompartimentNavire = motifCompartimentNavire;
    }

    public String getMotifCompartimentNavire() {
        return motifCompartimentNavire;
    }
}
