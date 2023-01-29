package model;

import enums.ActionsNavirePossibleSurUneCarteEnum;
import enums.MotifCompartimentNavireEnum;
import enums.NavirePuissanceEnum;
import enums.OrientationNavireEnum;
import exceptions.*;

import java.io.Serializable;
import java.util.*;

public class ModelCarte implements Serializable {
    public static final List<ActionsNavirePossibleSurUneCarteEnum> LIST_ACTIONS_NAVIRE_DEPLACEMENT_SUD_EST = Arrays.asList(ActionsNavirePossibleSurUneCarteEnum.SUD, ActionsNavirePossibleSurUneCarteEnum.EST);
    public static final List<ActionsNavirePossibleSurUneCarteEnum> LIST_ACTIONS_NAVIRE_DEPLACEMENT_NORD_OUEST = Arrays.asList(ActionsNavirePossibleSurUneCarteEnum.NORD, ActionsNavirePossibleSurUneCarteEnum.OUEST);
    public static final List<ActionsNavirePossibleSurUneCarteEnum> LIST_ACTIONS_NAVIRE_DEPLACEMENT_VERTICAL = Arrays.asList(ActionsNavirePossibleSurUneCarteEnum.NORD, ActionsNavirePossibleSurUneCarteEnum.SUD);
    public static final List<ActionsNavirePossibleSurUneCarteEnum> LIST_ACTIONS_NAVIRE_DEPLACEMENT_HORIZONTAL = Arrays.asList(ActionsNavirePossibleSurUneCarteEnum.OUEST, ActionsNavirePossibleSurUneCarteEnum.EST);

    private static final PositionSurCarte[] POINT_DATTAQUE_SELON_PUISSANCE_DATTAQUE = new PositionSurCarte[]{new PositionSurCarte(0,0), new PositionSurCarte(1,0), new PositionSurCarte(0,1), new PositionSurCarte(-1,0),  new PositionSurCarte(0,-1), new PositionSurCarte(1,-1), new PositionSurCarte(1,1), new PositionSurCarte(-1,1), new PositionSurCarte(-1,-1)};
    public final String MOTIF_VIDE = "  ";
    public static final int TAILLE_CARTE_PAR_DEFAUT = 15;
    private final int taille;

    public boolean estCachee() {
        return cachee;
    }

    public void setCachee(boolean cachee) {
        this.cachee = cachee;
    }

    private boolean cachee=false;

    public String getName() {
        return name;
    }

    private final String name;

    private final ArrayList<ModelNavire> modelNavires;
    public Object[] getModelNaviresAsArray(){
        return modelNavires.toArray();
    }
    private final CompartimentSurCarte[][] compartimentNavires;
    public ModelCarte() {
        this.taille = TAILLE_CARTE_PAR_DEFAUT;
        this.name =  String.format("CARTE-%s", UUID.randomUUID());
        this.compartimentNavires = new CompartimentSurCarte[taille][taille];
        this.modelNavires = new ArrayList<>();
        for (int i = 0; i < taille; i++)
            for (int j = 0; j < taille; j++)
                compartimentNavires[i][j] = null;
    }
    private boolean estOccupe(PositionSurCarte positionSurCarte){
        return verifierSiUnCompartimentPeutEtrePlaceSurLaCarte(positionSurCarte) && compartimentNavires[positionSurCarte.getX()][positionSurCarte.getY()] != null;
    }
    OrientationEtPositionNavire allouerUnEspaceAleatoireNavire(int tailleNavire) throws ExceptionCompartimentHorsDeLaCarteVerticalement, ExceptionCompartimentHorsDeLaCarteHorizontalement {
        final int marge = taille - tailleNavire;
        int startX = new Random().nextInt(marge);
        int startY = new Random().nextInt(marge);
        return verifierSiUnNavireDuneTaillePeutEtrePlacerA(tailleNavire, startX, startY);
    }

    private boolean verifierSiUnCompartimentPeutEtrePlaceSurLaCarte(PositionSurCarte positionSurCarte){
        return (positionSurCarte != null && positionSurCarte.getX()<taille && positionSurCarte.getX()>=0 && positionSurCarte.getY()<taille && positionSurCarte.getY()>=0);
    }

    private OrientationEtPositionNavire verifierSiUnNavireDuneTaillePeutEtrePlacerA(int tailleNavire, int startX, int startY) throws ExceptionCompartimentHorsDeLaCarteVerticalement, ExceptionCompartimentHorsDeLaCarteHorizontalement {
        PositionSurCarte debutPositionSurCarte =  new PositionSurCarte(startX,startY);
        PositionSurCarte finPositionSurCarteHorizontal = new PositionSurCarte((startX + tailleNavire), startY);
        PositionSurCarte finPositionSurCarteVertical = new PositionSurCarte( startX, startY + tailleNavire);
        boolean verifierSiUnCompartimentPeutEtrePlaceSurLaCarteHorizontalement = verifierSiUnCompartimentPeutEtrePlaceSurLaCarte(finPositionSurCarteHorizontal);

        boolean verifierSiUnCompartimentPeutEtrePlaceSurLaCarteVerticalement = verifierSiUnCompartimentPeutEtrePlaceSurLaCarte(finPositionSurCarteVertical);

        boolean debutNavireOccupe = estOccupe(debutPositionSurCarte);
        boolean finHorizontalOccupe = estOccupe(finPositionSurCarteHorizontal);
        boolean finVerticalOccupe = estOccupe(finPositionSurCarteVertical);

        if (debutNavireOccupe || finHorizontalOccupe && finVerticalOccupe || !verifierSiUnCompartimentPeutEtrePlaceSurLaCarteHorizontalement &&!verifierSiUnCompartimentPeutEtrePlaceSurLaCarteVerticalement)
            return null;


        var orientationNavire =  (verifierSiUnCompartimentPeutEtrePlaceSurLaCarteHorizontalement && verifierSiUnCompartimentPeutEtrePlaceSurLaCarteVerticalement)?  (new Random().nextBoolean())? OrientationNavireEnum.HORIZONTAL: OrientationNavireEnum.VERTICAL: verifierSiUnCompartimentPeutEtrePlaceSurLaCarteHorizontalement ? OrientationNavireEnum.HORIZONTAL: OrientationNavireEnum.VERTICAL;
        if (verifierAllocationDeTousLesCompartimentDunNavire(tailleNavire, debutPositionSurCarte, orientationNavire))
            return null;

        return new OrientationEtPositionNavire(orientationNavire, debutPositionSurCarte);
    }

    private boolean verifierAllocationDeTousLesCompartimentDunNavire(int tailleNavire, PositionSurCarte debutPositionSurCarte, OrientationNavireEnum orientationNavire) throws ExceptionCompartimentHorsDeLaCarteHorizontalement, ExceptionCompartimentHorsDeLaCarteVerticalement {
        PositionSurCarte tmpDebutPositionSurCarte = new PositionSurCarte(debutPositionSurCarte);
        for (int i = 0; i < tailleNavire; i++) {
            int xToVerify = tmpDebutPositionSurCarte.getX();
            int yToVerify = tmpDebutPositionSurCarte.getY();
            if (xToVerify<0 || xToVerify>= taille)
                throw new ExceptionCompartimentHorsDeLaCarteHorizontalement();
            if (yToVerify<0 || yToVerify>= taille)
                throw new ExceptionCompartimentHorsDeLaCarteVerticalement();
            if(estOccupe(new PositionSurCarte(xToVerify,yToVerify))) {
                return true;
            }
            if (orientationNavire.estHorizontal())
                tmpDebutPositionSurCarte.avancerHorizontalement();
            if (orientationNavire.estHorizontal())
                tmpDebutPositionSurCarte.avancerVerticalement();
        }
        return false;
    }

    OrientationEtPositionNavire verifierSiUnNavireDuneTaillePeutEtrePlacerAvecLindexLineaire(int tailleNavire, int index) throws ExceptionCompartimentHorsDeLaCarteVerticalement, ExceptionCompartimentHorsDeLaCarteHorizontalement {
        return verifierSiUnNavireDuneTaillePeutEtrePlacerA(tailleNavire, index/taille,index%taille);
    }
    OrientationEtPositionNavire allocationAleatoireSur10EssaiDUnNavire(int tailleNavire) throws ExceptionCompartimentHorsDeLaCarteVerticalement, ExceptionCompartimentHorsDeLaCarteHorizontalement {
        OrientationEtPositionNavire orientationEtPositionNavire = null;
        for (int i = 0; i < 10 && orientationEtPositionNavire == null; i++) {
            orientationEtPositionNavire = allouerUnEspaceAleatoireNavire(tailleNavire);
        }
        return orientationEtPositionNavire;
    }

    public OrientationEtPositionNavire trouverUneOrientationEtUnePositionPourUneNavireDeTaille(int taillenavire) throws ExceptionCompartimentHorsDeLaCarteVerticalement, ExceptionCompartimentHorsDeLaCarteHorizontalement {
        OrientationEtPositionNavire orientationEtPositionNavire = null;

        final int marge = taille - taillenavire - 1;
        int tailleMiniCarteDeRecherche = marge * marge;
        final int[] miniCarteDeRecherche = new int[tailleMiniCarteDeRecherche];
        for (int i = 0; i < marge; i++)
            for (int j = 0; j < marge; j++)
                miniCarteDeRecherche[i] = j+i*taille;

        for (int i = 0; i < tailleMiniCarteDeRecherche; i++) {

            orientationEtPositionNavire = allouerUnEspaceAleatoireNavire(ModelNavireCroiseur.TAILLE);
            if (orientationEtPositionNavire != null)
                break;

            orientationEtPositionNavire = verifierSiUnNavireDuneTaillePeutEtrePlacerAvecLindexLineaire(taillenavire,miniCarteDeRecherche[i]);
            if (orientationEtPositionNavire != null)
                break;


            orientationEtPositionNavire = verifierSiUnNavireDuneTaillePeutEtrePlacerAvecLindexLineaire(taillenavire,miniCarteDeRecherche[tailleMiniCarteDeRecherche-i-1]);
            if (orientationEtPositionNavire != null)
                break;

        }
        return orientationEtPositionNavire;
    }


    public record OrientationEtPositionNavire(OrientationNavireEnum orientationNavireEnum, PositionSurCarte positionSurCarte) {
    }

    public boolean ajouterUnNavireALaPosition(ModelNavire modelNavire) throws ExceptionPositionDejaOccupe, ExceptionCompartimentHorsDeLaCarteVerticalement, ExceptionCompartimentHorsDeLaCarteHorizontalement {
        if (estOccupe(modelNavire.getPremierCompartimentNavire()) || estOccupe(modelNavire.getDernierCompartimentNavire()))
            return false;
        //int depart = modelNavire.estHorizontal()? modelNavire.getPremierCompartimentNavire().getX(): modelNavire.getPremierCompartimentNavire().getY();

        if (verifierAllocationDeTousLesCompartimentDunNavire(modelNavire.getTaille(), modelNavire.getPremierCompartimentNavire(), modelNavire.getOrientationNavire()))
            throw new ExceptionPositionDejaOccupe();
        for (var compartimentNavire: modelNavire.getCompartimentNavires()
             ) {
            if (compartimentNavires[compartimentNavire.getX()][compartimentNavire.getY()] != null)
                throw new ExceptionPositionDejaOccupe();
            compartimentNavires[compartimentNavire.getX()][compartimentNavire.getY()] = compartimentNavire;
        }
        if (modelNavire.estHorizontal())
            for (int i = 0; i < modelNavire.getTaille(); i++)
        if (!modelNavires.contains(modelNavire))
            modelNavires.add(modelNavire);
        return true;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getDelimiteurDeBord(true));
        StringBuilder indexesEnEntierBuilder = null;
        stringBuilder.append('\n');

        indexesEnEntierBuilder = new StringBuilder("   │");
        for (int i = 0; i < taille; i++) {
            indexesEnEntierBuilder.append(String.format("%s%d│",i<10?" ":"",i));
        }
        indexesEnEntierBuilder.append("\n");
        stringBuilder.append(indexesEnEntierBuilder);
        stringBuilder.append("┌").append(getLigneDelimiteur().substring(1));
        for (int i = 0; i < compartimentNavires.length; i++) {
            CompartimentSurCarte[] ligneDeCompartimentNavire = compartimentNavires[i];
            StringBuilder tmpBuilder = new StringBuilder();
            for (var colonneCompartimentNavire : ligneDeCompartimentNavire)
                tmpBuilder.append(String.format("%s│", colonneCompartimentNavire == null ?  MOTIF_VIDE: (colonneCompartimentNavire.estDetruit())? (cachee)? MotifCompartimentNavireEnum.CACHE.getMotifCompartimentNavire():MotifCompartimentNavireEnum.DETRUIT.getMotifCompartimentNavire(): (cachee)? MOTIF_VIDE:colonneCompartimentNavire.toString()));
            stringBuilder.append(String.format("│ %c│%s\n", 'a'+i, tmpBuilder));
            if (i<compartimentNavires.length-1)
                stringBuilder.append(getLigneDelimiteur());
        }
        stringBuilder.append(getDelimiteurDeBord(false));
        return stringBuilder.toString();
    }

    private String getDelimiteurDeBord(boolean haut) {
        return String.format("%s%s\b%c",haut?"   ┌":"└",String.format("%s",haut?"——┬":"——┴").repeat(Math.max(0, taille+(haut?0:1))),haut?'┐':'┘');
    }

    private StringBuilder getLigneDelimiteur() {
        StringBuilder ligneDelimiteur = new StringBuilder();
        ligneDelimiteur.append('├');
        ligneDelimiteur.append("━━┼".repeat(Math.max(0, taille+1)));
        //ligneDelimiteur.append("-".repeat(Math.max(0, taille-1)));
        ligneDelimiteur.append("\b┤\n");
        return ligneDelimiteur;
    }
    public ModelNavire recupererNavireAlaPosition(PositionSurCarte positionSurCarte){
        if (!estOccupe(positionSurCarte))
                return null;

        return compartimentNavires[positionSurCarte.getX()][positionSurCarte.getY()].getModelNavire();
    }
    private void faireAvancerLeNavireSeTrouvantALaPosition(ModelNavire navireAcettePosition) throws Throwable {

        for (var compartimentALaPosition: navireAcettePosition.getCompartimentNavires())
            compartimentNavires[compartimentALaPosition.getX()][compartimentALaPosition.getY()] = null;
        navireAcettePosition.avancer();
        ajouterUnNavireALaPosition(navireAcettePosition);
    }
    private void faireReculerLeNavireSeTrouvantALaPosition(ModelNavire navireAcettePosition) throws Throwable {

        for (var compartimentALaPosition: navireAcettePosition.getCompartimentNavires())
            compartimentNavires[compartimentALaPosition.getX()][compartimentALaPosition.getY()] = null;
        navireAcettePosition.reculer();
        ajouterUnNavireALaPosition(navireAcettePosition);
    }
    public boolean verifierSiUnePositionNavireExiste(PositionSurCarte positionSurCarte){
        return (positionSurCarte.getX()>=0&& positionSurCarte.getX()<taille&& positionSurCarte.getY()>=0&& positionSurCarte.getY()<taille);
    }
    public void effectuerUnDeplacementAvecLeNavire(ModelNavire modelNavire, ActionsNavirePossibleSurUneCarteEnum actionNavirePossibleSurUneCarteEnum) throws Throwable {
        var actionPossibleDuNavire = getToutesLesActionPossibleDuNavire(modelNavire);
        if (!Arrays.asList(actionPossibleDuNavire).contains(actionNavirePossibleSurUneCarteEnum))
            throw new ExceptionImpossibleDEffectuerUneActionAvecUnNavire();

        // Changement d'orientation si le mouvement demnadé sur un sous marin n'est pas possible avec son orientation actuelle

        if (modelNavire.estUnSousMarin()&&((LIST_ACTIONS_NAVIRE_DEPLACEMENT_VERTICAL.contains(actionNavirePossibleSurUneCarteEnum) && modelNavire.estHorizontal()) || (LIST_ACTIONS_NAVIRE_DEPLACEMENT_HORIZONTAL.contains(actionNavirePossibleSurUneCarteEnum) && modelNavire.estVertical())))
                modelNavire.changerDOrientation();

        if(LIST_ACTIONS_NAVIRE_DEPLACEMENT_SUD_EST.contains(actionNavirePossibleSurUneCarteEnum)){
            faireAvancerLeNavireSeTrouvantALaPosition(modelNavire);
        } else if (LIST_ACTIONS_NAVIRE_DEPLACEMENT_NORD_OUEST.contains(actionNavirePossibleSurUneCarteEnum)) {
            faireReculerLeNavireSeTrouvantALaPosition(modelNavire);
        }
    }
    public ActionsNavirePossibleSurUneCarteEnum[] getToutesLesActionPossibleDuNavire(ModelNavire modelNavire){
        if (modelNavire ==null)
            return null;
        int nombreDActions = modelNavire.peutAttaquer()? 1:0;
        boolean reculerSuivantLOrientationNavire = false;
        boolean avancerSuivantLOrientationNavire = false;
        boolean reculerSuivantLOrientationDifferenteDuNavire = false;
        boolean avancerSuivantLOrientationDifferenteDuNavire = false;
        if (modelNavire.peutSeDeplacer()){
            reculerSuivantLOrientationNavire = peutReculerSuivantLOrientationNavire(modelNavire);
            if (reculerSuivantLOrientationNavire)
                nombreDActions++;
            avancerSuivantLOrientationNavire = peutAvancerSuivantLOrientationNavire(modelNavire);
            if (avancerSuivantLOrientationNavire)
                nombreDActions++;

            if (modelNavire.estUnSousMarin()) {
                modelNavire.changerDOrientation();
                reculerSuivantLOrientationDifferenteDuNavire = peutReculerSuivantLOrientationNavire(modelNavire);
                if (reculerSuivantLOrientationDifferenteDuNavire)
                    nombreDActions++;
                avancerSuivantLOrientationDifferenteDuNavire = peutAvancerSuivantLOrientationNavire(modelNavire);
                if (avancerSuivantLOrientationDifferenteDuNavire)
                    nombreDActions++;
            }
        }
        ActionsNavirePossibleSurUneCarteEnum[] actionsNavirePossibleSurUneCarteEnums = new ActionsNavirePossibleSurUneCarteEnum[nombreDActions];
        int counter = 0;
        if (modelNavire.peutAttaquer()){
            actionsNavirePossibleSurUneCarteEnums[counter++] = ActionsNavirePossibleSurUneCarteEnum.ATTAQUER;
        }
        if (modelNavire.estUnSousMarin()){

            modelNavire.changerDOrientation();
            if (reculerSuivantLOrientationNavire)
                actionsNavirePossibleSurUneCarteEnums[counter++] = modelNavire.estHorizontal()?ActionsNavirePossibleSurUneCarteEnum.OUEST : ActionsNavirePossibleSurUneCarteEnum.NORD;
            if (avancerSuivantLOrientationNavire)
                actionsNavirePossibleSurUneCarteEnums[counter++] = modelNavire.estHorizontal()?ActionsNavirePossibleSurUneCarteEnum.EST : ActionsNavirePossibleSurUneCarteEnum.SUD;

            modelNavire.changerDOrientation();
            if (reculerSuivantLOrientationDifferenteDuNavire)
                actionsNavirePossibleSurUneCarteEnums[counter++] =  modelNavire.estHorizontal()?ActionsNavirePossibleSurUneCarteEnum.OUEST : ActionsNavirePossibleSurUneCarteEnum.NORD;
            if (avancerSuivantLOrientationDifferenteDuNavire)
                actionsNavirePossibleSurUneCarteEnums[counter] = modelNavire.estHorizontal()?ActionsNavirePossibleSurUneCarteEnum.EST : ActionsNavirePossibleSurUneCarteEnum.SUD;
        }else{
            if (reculerSuivantLOrientationNavire)
                actionsNavirePossibleSurUneCarteEnums[counter++] = modelNavire.estHorizontal()?ActionsNavirePossibleSurUneCarteEnum.OUEST : ActionsNavirePossibleSurUneCarteEnum.NORD;
            if (avancerSuivantLOrientationNavire)
                actionsNavirePossibleSurUneCarteEnums[counter] = modelNavire.estHorizontal()?ActionsNavirePossibleSurUneCarteEnum.EST : ActionsNavirePossibleSurUneCarteEnum.SUD;
        }

        return actionsNavirePossibleSurUneCarteEnums;
    }

    private boolean peutReculerSuivantLOrientationNavire(ModelNavire modelNavire) {
        if (modelNavire == null || !modelNavire.peutSeDeplacer())
            return false;
        boolean navireTrouveAcettePosition = recupererNavireAlaPosition(modelNavire.getPremierCompartimentNavire()) != null;
        boolean positionAvantNavireSuivantOrientationNavireOccupee;
        var tmpPositionPremierCompartimentNavire = new PositionSurCarte(modelNavire.getPremierCompartimentNavire());
        if (modelNavire.estHorizontal())
            tmpPositionPremierCompartimentNavire.reculerHorizontalement();
        else if (modelNavire.estVertical()) {
            tmpPositionPremierCompartimentNavire.reculerVerticalement();
        }
        positionAvantNavireSuivantOrientationNavireOccupee = (recupererNavireAlaPosition(tmpPositionPremierCompartimentNavire) == null);
        boolean positionAvantNavireSuivantOrientationNavireAppartientALaCarte = verifierSiUnePositionNavireExiste(tmpPositionPremierCompartimentNavire);
        return navireTrouveAcettePosition && positionAvantNavireSuivantOrientationNavireOccupee && positionAvantNavireSuivantOrientationNavireAppartientALaCarte;
    }
    private boolean peutAvancerSuivantLOrientationNavire(ModelNavire modelNavire) {
        if (modelNavire == null || !modelNavire.peutSeDeplacer())
            return false;
        boolean navireTrouveAcettePosition = recupererNavireAlaPosition(modelNavire.getPremierCompartimentNavire()) != null;
        boolean positionApresNavireSuivantOrientationNavireOccupee;
        var tmpPositionDernierCompartimentNavire = new PositionSurCarte(modelNavire.getDernierCompartimentNavire());
        if (modelNavire.estHorizontal())
            tmpPositionDernierCompartimentNavire.avancerHorizontalement();
        else if (modelNavire.estVertical()) {
            tmpPositionDernierCompartimentNavire.avancerVerticalement();
        }
        positionApresNavireSuivantOrientationNavireOccupee = (recupererNavireAlaPosition(tmpPositionDernierCompartimentNavire) == null);
        boolean positionAvantNavireSuivantOrientationNavireAppartientALaCarte = verifierSiUnePositionNavireExiste(tmpPositionDernierCompartimentNavire);
        return navireTrouveAcettePosition && positionApresNavireSuivantOrientationNavireOccupee && positionAvantNavireSuivantOrientationNavireAppartientALaCarte;
    }
    public void encaisserUneAttatque(ModelNavire modelNavire, PositionSurCarte positionSurCarte) throws ExceptionAttaqueSurUnePositionHorsDeLaCarte {
        if (!verifierSiUnCompartimentPeutEtrePlaceSurLaCarte(positionSurCarte))
            throw new ExceptionAttaqueSurUnePositionHorsDeLaCarte();

        var puissanceNavire = modelNavire.getPuissance();

        assert puissanceNavire > 0;
        assert puissanceNavire <= NavirePuissanceEnum.CUIRASE.getPuissanceNavire();

        var positionsAttaquees = new PositionSurCarte[puissanceNavire];
        for (int i = 0; i < puissanceNavire; i++) {
            PositionSurCarte postionSurCarteTemporaire = POINT_DATTAQUE_SELON_PUISSANCE_DATTAQUE[i];
            positionsAttaquees[i] = new PositionSurCarte(postionSurCarteTemporaire.getX()+positionSurCarte.getX(),postionSurCarteTemporaire.getY()+positionSurCarte.getY());
        }
        for (var positionAttaque :
                positionsAttaquees) {
            System.out.print("Attaque sur le point: "+positionAttaque);
            if (!verifierSiUnCompartimentPeutEtrePlaceSurLaCarte(positionAttaque)) {
                System.out.println(ConsoleColors.RED +" Hors carte."+ConsoleColors.RESET);
                continue;
            }
            if (estOccupe(positionAttaque)){
                CompartimentSurCarte compartimentSurCarteACettePosition = compartimentNavires[positionAttaque.getX()][positionAttaque.getY()];
                if(compartimentSurCarteACettePosition.appartientAUnSousMarin()) {
                    if (modelNavire.estUnSousMarin()) {
                        System.out.println(ConsoleColors.GREEN + " Navire touché."+ConsoleColors.RESET);
                        compartimentSurCarteACettePosition.encaisserAttaque();
                        continue;
                    }
                }
                else {
                    System.out.println(ConsoleColors.GREEN +" Navire touché."+ConsoleColors.RESET);
                    compartimentSurCarteACettePosition.encaisserAttaque();
                    continue;
                }
            }
            System.out.println(ConsoleColors.YELLOW+" Raté."+ConsoleColors.RESET);
        }
        // switch ()
    }
}
