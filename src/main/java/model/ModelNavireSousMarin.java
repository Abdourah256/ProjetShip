package main.java.model;

import main.java.enums.MotifCompartimentNavireEnum;
import main.java.enums.NavirePuissanceEnum;
import main.java.enums.NavireTypeEnum;
import main.java.enums.OrientationNavireEnum;

public class ModelNavireSousMarin extends ModelNavire {
    public final static int TAILLE = 1;
    public ModelNavireSousMarin(OrientationNavireEnum orientationNavire, PositionSurCarte positionSurCarte) {
        super(NavireTypeEnum.SOUSMARIN, NavirePuissanceEnum.SOUSMARIN.getPuissanceNavire(), orientationNavire, TAILLE, positionSurCarte, MotifCompartimentNavireEnum.SOUSMARIN);
    }
    public void changerDOrientation(){
        if (estHorizontal())
            setOrientationNavire(OrientationNavireEnum.VERTICAL);
        else if (estVertical())
            setOrientationNavire(OrientationNavireEnum.HORIZONTAL);
    }
}
