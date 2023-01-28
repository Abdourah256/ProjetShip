package model;

public class ModelNavireCroiseur extends ModelNavire {
    public final static int TAILLE = 5;

    public ModelNavireCroiseur(OrientationNavireEnum orientationNavire, PositionSurCarte positionSurCarte) {
        super(NavireTypeEnum.CROISEUR, NavirePuissanceEnum.CROISEUR.getPuissanceNavire(), orientationNavire, TAILLE, positionSurCarte, MotifCompartimentNavireEnum.CROISEUR);
    }
}
