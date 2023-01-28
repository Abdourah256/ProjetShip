package model;

public class ModelNavireDestroyer extends ModelNavire {
    public final static int TAILLE = 3;

    public ModelNavireDestroyer(OrientationNavireEnum orientationNavire, PositionSurCarte positionSurCarte) {
        super(NavireTypeEnum.DESTROYER, NavirePuissanceEnum.DESTROYER.getPuissanceNavire(), orientationNavire, TAILLE, positionSurCarte, MotifCompartimentNavireEnum.DESTROYER);
    }
}
