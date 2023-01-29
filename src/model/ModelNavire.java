package model;

import enums.MotifCompartimentNavireEnum;
import enums.NavireTypeEnum;
import enums.OrientationNavireEnum;
import exceptions.ExceptionMouvementHorizontalException;
import exceptions.ExceptionMouvementVerticalException;
import exceptions.ExceptionNavireEndommagerDeplacementImpossible;

import java.io.Serializable;
import java.util.Arrays;
import java.util.UUID;

public class ModelNavire implements Cloneable, Serializable {
    private final String name;
    private final NavireTypeEnum navireTypeEnum;
    private final int puissance;

    public int getTaille() {
        return taille;
    }

    private final int taille;

    private final MotifCompartimentNavireEnum motifCompartimentNavireEnum;

    protected OrientationNavireEnum getOrientationNavire() {

        return orientationNavire;
    }

    protected void setOrientationNavire(OrientationNavireEnum orientationNavire) {
        this.orientationNavire = orientationNavire;
    }

    private OrientationNavireEnum orientationNavire;

    public CompartimentSurCarte[] getCompartimentNavires() {
        return compartimentNavires;
    }

    private final CompartimentSurCarte[] compartimentNavires;

    public ModelNavire(ModelNavire modelNavire) {
        this(modelNavire.navireTypeEnum, modelNavire.puissance, modelNavire.getOrientationNavire(),modelNavire.taille, modelNavire.getPremierCompartimentNavire(), modelNavire.motifCompartimentNavireEnum);
    }
    protected ModelNavire(NavireTypeEnum navireTypeEnum, int puissance, OrientationNavireEnum orientationNavire, int taille, PositionSurCarte positionSurCarte, MotifCompartimentNavireEnum motifCompartimentNavireEnum) {
        this.name = String.format("%s-%s", navireTypeEnum.toString(), UUID.randomUUID());
        this.navireTypeEnum = navireTypeEnum;
        this.puissance = puissance;
        this.orientationNavire = orientationNavire;
        this.compartimentNavires = new CompartimentSurCarte[taille];
        this.taille = taille;
        this.motifCompartimentNavireEnum = motifCompartimentNavireEnum;
        setNavirePosition(positionSurCarte);
    }

    public void setNavirePosition(PositionSurCarte positionSurCarte) {
        for (int i = 0; i < taille; i++) {
            this.compartimentNavires[i] = new CompartimentSurCarte((positionSurCarte.getX()+(orientationNavire == OrientationNavireEnum.HORIZONTAL? 0:i)), positionSurCarte.getY()+(orientationNavire == OrientationNavireEnum.VERTICAL? 0:i), this);
        }
    }
    public void avancer() throws ExceptionNavireEndommagerDeplacementImpossible {
        if (!peutSeDeplacer())
            throw new ExceptionNavireEndommagerDeplacementImpossible();
        if (estVertical()) {
            try {
                incrementX();
            } catch (ExceptionMouvementVerticalException e) {
                throw new RuntimeException(e);
            }
        } else if (estHorizontal()) {
            try {
                incrementY();
            } catch (ExceptionMouvementHorizontalException e) {
                throw new RuntimeException(e);
            }
        }

    }
    public void reculer() throws ExceptionNavireEndommagerDeplacementImpossible {
        if (!peutSeDeplacer())
            throw new ExceptionNavireEndommagerDeplacementImpossible();
        if (estVertical()) {
            try {
                decrementX();
            } catch (ExceptionMouvementVerticalException e) {
                throw new RuntimeException(e);
            }
        } else if (estHorizontal()) {
            try {
                decrementY();
            } catch (ExceptionMouvementHorizontalException e) {
                throw new RuntimeException(e);
            }
        }

    }
    public void incrementX() throws ExceptionMouvementVerticalException {
        if (orientationNavire != OrientationNavireEnum.VERTICAL)
            throw new ExceptionMouvementVerticalException();
        getPremierCompartimentNavire().avancerVerticalement();
        setNavirePosition(compartimentNavires[0]);
    }
    public void decrementX() throws ExceptionMouvementVerticalException {
        if (orientationNavire != OrientationNavireEnum.VERTICAL)
            throw new ExceptionMouvementVerticalException();
        getPremierCompartimentNavire().reculerVerticalement();
        setNavirePosition(compartimentNavires[0]);
    }
    private void incrementY() throws ExceptionMouvementHorizontalException {
        if (orientationNavire != OrientationNavireEnum.HORIZONTAL)
            throw new ExceptionMouvementHorizontalException();
        getPremierCompartimentNavire().avancerHorizontalement();
        setNavirePosition(compartimentNavires[0]);
    }
    private void decrementY() throws ExceptionMouvementHorizontalException {
        if (orientationNavire != OrientationNavireEnum.HORIZONTAL)
            throw new ExceptionMouvementHorizontalException();
        getPremierCompartimentNavire().reculerHorizontalement();
        setNavirePosition(compartimentNavires[0]);
    }

    private MotifCompartimentNavireEnum getMotifCompartimentNavireEnum() {
        return motifCompartimentNavireEnum;
    }
    public CompartimentSurCarte getPremierCompartimentNavire(){
        return compartimentNavires[0];
    }
    public CompartimentSurCarte getDernierCompartimentNavire(){
        return compartimentNavires[compartimentNavires.length-1];
    }
    public boolean estHorizontal(){
        return orientationNavire.estHorizontal();
    }
    public boolean estVertical(){
        return orientationNavire.estVertical();
    }

    public void changerDOrientation(){
    }

    @Override
    public String toString() {
        return "ModelNavire{" +
                "name='" + name + '\'' +
                ", navireTypeEnum=" + navireTypeEnum +
                ", puissance=" + puissance +
                ", taille=" + taille +
                ", motifCompartimentNavireEnum=" + motifCompartimentNavireEnum +
                ", orientationNavire=" + orientationNavire +
                ", compartimentNavires=" + Arrays.toString(compartimentNavires) +
                '}';
    }

    public String getName() {
        return name;
    }

    public MotifCompartimentNavireEnum getMotifCompartimentNavire() {
        return motifCompartimentNavireEnum;
    }
    public boolean estUnSousMarin(){
        return navireTypeEnum == NavireTypeEnum.SOUSMARIN;
    }

    @Override
    public ModelNavire clone() {
        try {
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return (ModelNavire) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    public boolean peutAttaquer() {
        for (var compartimentNavire:compartimentNavires
             ) if (compartimentNavire.estEnBonEtat())
                 return true;
        return false;
    }
    public boolean peutSeDeplacer() {
        for (var compartimentNavire:compartimentNavires
             ) if (!compartimentNavire.estEnBonEtat())
                 return false;
        return true;
    }

    public NavireTypeEnum getTypeNavire() {
        return navireTypeEnum;
    }

    public int getPuissance() {
        return puissance;
    }
}
