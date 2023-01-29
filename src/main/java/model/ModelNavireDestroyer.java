package main.java.model;

import main.java.enums.MotifCompartimentNavireEnum;
import main.java.enums.NavirePuissanceEnum;
import main.java.enums.NavireTypeEnum;
import main.java.enums.OrientationNavireEnum;

public class ModelNavireDestroyer extends ModelNavire {
    public final static int TAILLE = 3;

    public ModelNavireDestroyer(OrientationNavireEnum orientationNavire, PositionSurCarte positionSurCarte) {
        super(NavireTypeEnum.DESTROYER, NavirePuissanceEnum.DESTROYER.getPuissanceNavire(), orientationNavire, TAILLE, positionSurCarte, MotifCompartimentNavireEnum.DESTROYER);
    }
}
