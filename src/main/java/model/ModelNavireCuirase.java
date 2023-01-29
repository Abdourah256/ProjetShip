package main.java.model;

import main.java.enums.MotifCompartimentNavireEnum;
import main.java.enums.NavirePuissanceEnum;
import main.java.enums.NavireTypeEnum;
import main.java.enums.OrientationNavireEnum;

public class ModelNavireCuirase extends ModelNavire {
    public final static int TAILLE = 7;

    public ModelNavireCuirase(OrientationNavireEnum orientationNavire, PositionSurCarte positionSurCarte) {
        super(NavireTypeEnum.CUIRASE, NavirePuissanceEnum.CUIRASE.getPuissanceNavire(), orientationNavire, TAILLE, positionSurCarte, MotifCompartimentNavireEnum.CUIRASE);
    }
}
