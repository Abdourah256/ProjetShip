package main.java.model;

import main.java.enums.MotifCompartimentNavireEnum;
import main.java.enums.NavirePuissanceEnum;
import main.java.enums.NavireTypeEnum;
import main.java.enums.OrientationNavireEnum;

public class ModelNavireCroiseur extends ModelNavire {
    public final static int TAILLE = 5;

    public ModelNavireCroiseur(OrientationNavireEnum orientationNavire, PositionSurCarte positionSurCarte) {
        super(NavireTypeEnum.CROISEUR, NavirePuissanceEnum.CROISEUR.getPuissanceNavire(), orientationNavire, TAILLE, positionSurCarte, MotifCompartimentNavireEnum.CROISEUR);
    }
}
