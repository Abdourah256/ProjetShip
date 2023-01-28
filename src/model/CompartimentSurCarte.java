package model;


public class CompartimentSurCarte extends PositionSurCarte {

    public ModelNavire getModelNavire() {
        return modelNavire;
    }

    private final ModelNavire modelNavire;
    private boolean etat = true;
    private final MotifCompartimentNavireEnum motifCompartimentNavireEnum;

    @Override
    public String toString() {
        return motifCompartimentNavireEnum.getMotifCompartimentNavire();
    }

    CompartimentSurCarte(int x, int y, ModelNavire modelNavire){
        super(x,y);
        this.modelNavire = modelNavire;
        this.motifCompartimentNavireEnum = modelNavire.getMotifCompartimentNavire();
    }

    public boolean estEnBonEtat() {
        return etat;
    }
    public boolean estDetruit() {
        return !estEnBonEtat();
    }
    public void encaisserAttaque(){
        etat = false;
    }
    public boolean appartientAUnSousMarin(){
        return getModelNavire().estUnSousMarin();
    }
}