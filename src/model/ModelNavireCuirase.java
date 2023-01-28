package model;

import enums.MotifCompartimentNavireEnum;
import enums.NavirePuissanceEnum;
import enums.NavireTypeEnum;
import enums.OrientationNavireEnum;

public class ModelNavireCuirase extends ModelNavire {
    public final static int TAILLE = 7;

    public ModelNavireCuirase(OrientationNavireEnum orientationNavire, PositionSurCarte positionSurCarte) {
        super(NavireTypeEnum.CUIRASE, NavirePuissanceEnum.CUIRASE.getPuissanceNavire(), orientationNavire, TAILLE, positionSurCarte, MotifCompartimentNavireEnum.CUIRASE);
    }
}
