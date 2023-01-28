package enums;

public enum NavireTypeEnum {
    CUIRASE("Cuirase"),
    CROISEUR("Croiseur"),
    DESTROYER("Destroyer"),
    SOUSMARIN("SousMarin");
    private final String navireType;

    NavireTypeEnum(String navireType) {
        this.navireType = navireType;
    }

    public String getNavireType() {
        return navireType;
    }
}
