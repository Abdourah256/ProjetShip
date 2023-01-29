package model;

import enums.ActionsNavirePossibleSurUneCarteEnum;
import exceptions.*;

import java.io.*;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

public class ModelPlateauDeJeu implements Serializable {
    public static final String GAME_SAVED_BIN = "_gameSaved.bin";
    private ModelCarte modelCarte;
    private ModelCarteRobot modelCarteRobot;

    
    public ModelPlateauDeJeu() throws ExceptionCompartimentHorsDeLaCarteHorizontalement, ExceptionCompartimentHorsDeLaCarteVerticalement, ExceptionPositionDejaOccupe {
        modelCarte = new ModelCarte();
        creerAleatoirementTousLesNaviresSurLaCarte(modelCarte);
        this.modelCarteRobot = new ModelCarteRobot();
        creerAleatoirementTousLesNaviresSurLaCarte(modelCarteRobot);
    }

    private ModelCarte getModelCarte() {
        return modelCarte;
    }

    private void setModelCarte(ModelCarte modelCarte) {
        this.modelCarte = modelCarte;
    }

    private ModelCarteRobot getModelCarteRobot() {
        return modelCarteRobot;
    }

    private void setModelCarteRobot(ModelCarteRobot modelCarteRobot) {
        this.modelCarteRobot = modelCarteRobot;
    }

    private void sauvegarderPlateau() throws IOException {

        // create a FileOutputStream to write the object to a file
        // LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss")),
        FileOutputStream fos = new FileOutputStream(String.format("%s%s%s",modelCarte.getName(),modelCarteRobot.getName(), GAME_SAVED_BIN));

        // create an ObjectOutputStream to write the object
        ObjectOutputStream oos = new ObjectOutputStream(fos);

        // write the object to the file
        oos.writeObject(this);

        // close the streams
        oos.close();
        fos.close();

    }
    public void chargerPlateau(String sauvegarde) throws IOException, ClassNotFoundException, ExceptionCompartimentHorsDeLaCarteHorizontalement, ExceptionCompartimentHorsDeLaCarteVerticalement, ExceptionPositionDejaOccupe {

        FileInputStream fis = new FileInputStream(sauvegarde);
        ObjectInputStream ois = new ObjectInputStream(fis);
        ModelPlateauDeJeu plateauDeJeu = (ModelPlateauDeJeu) ois.readObject();
        modelCarte = plateauDeJeu.getModelCarte();
        modelCarteRobot = plateauDeJeu.getModelCarteRobot();
        ois.close();
        fis.close();
    }
    private void afficherLesCartes() {
        System.out.println("\nCarte Joueur:\n" + modelCarte);
        System.out.println("\nCarte Ordinateur:\n" + modelCarteRobot);
    }

    private void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
    private ModelNavire creerAleatoirementUnNavireCroiseurSurLaCarte(ModelCarte modelCarte) throws ExceptionCompartimentHorsDeLaCarteVerticalement, ExceptionCompartimentHorsDeLaCarteHorizontalement {
        var orientationEtPositionNavire = modelCarte.trouverUneOrientationEtUnePositionPourUneNavireDeTaille(ModelNavireCroiseur.TAILLE);
        return orientationEtPositionNavire != null ? new ModelNavireCroiseur(orientationEtPositionNavire.orientationNavireEnum(), orientationEtPositionNavire.positionSurCarte()):null;
    }
    private ModelNavire creerAleatoirementUnNavireCuiraseSurLaCarte(ModelCarte modelCarte) throws ExceptionCompartimentHorsDeLaCarteVerticalement, ExceptionCompartimentHorsDeLaCarteHorizontalement {
        var orientationEtPositionNavire = modelCarte.trouverUneOrientationEtUnePositionPourUneNavireDeTaille(ModelNavireCuirase.TAILLE);
        return orientationEtPositionNavire != null ? new ModelNavireCuirase(orientationEtPositionNavire.orientationNavireEnum(), orientationEtPositionNavire.positionSurCarte()):null;
    }
    private ModelNavire creerAleatoirementUnNavireDestroyerSurLaCarte(ModelCarte modelCarte) throws ExceptionCompartimentHorsDeLaCarteVerticalement, ExceptionCompartimentHorsDeLaCarteHorizontalement {
        var orientationEtPositionNavire = modelCarte.trouverUneOrientationEtUnePositionPourUneNavireDeTaille(ModelNavireDestroyer.TAILLE);
        return orientationEtPositionNavire != null ? new ModelNavireDestroyer(orientationEtPositionNavire.orientationNavireEnum(), orientationEtPositionNavire.positionSurCarte()):null;
    }
    private ModelNavire creerAleatoirementUnNavireSousMarinSurLaCarte(ModelCarte modelCarte) throws ExceptionCompartimentHorsDeLaCarteVerticalement, ExceptionCompartimentHorsDeLaCarteHorizontalement {
        var orientationEtPositionNavire = modelCarte.trouverUneOrientationEtUnePositionPourUneNavireDeTaille(ModelNavireSousMarin.TAILLE);
        return orientationEtPositionNavire != null ? new ModelNavireSousMarin(orientationEtPositionNavire.orientationNavireEnum(), orientationEtPositionNavire.positionSurCarte()):null;
    }
    private void creerAleatoirementTousLesNaviresSurLaCarte(ModelCarte modelCarte) throws ExceptionPositionDejaOccupe, ExceptionCompartimentHorsDeLaCarteVerticalement, ExceptionCompartimentHorsDeLaCarteHorizontalement {
        creerUnNavireDeTypeCuiraseSurLaCarte(modelCarte);
        creerUnNavireDeTypeCroiseurSurLaCarte(modelCarte);
        creerUnNavireDeTypeCroiseurSurLaCarte(modelCarte);
        creerUnNavireDeTypeDestroyerSurLaCarte(modelCarte);
        creerUnNavireDeTypeDestroyerSurLaCarte(modelCarte);
        creerUnNavireDeTypeDestroyerSurLaCarte(modelCarte);
        creerUnNavireDeTypeSousMarinSurLaCarte(modelCarte);
        creerUnNavireDeTypeSousMarinSurLaCarte(modelCarte);
        creerUnNavireDeTypeSousMarinSurLaCarte(modelCarte);
        creerUnNavireDeTypeSousMarinSurLaCarte(modelCarte);
    }

    private void creerUnNavireDeTypeSousMarinSurLaCarte(ModelCarte modelCarte) throws ExceptionPositionDejaOccupe, ExceptionCompartimentHorsDeLaCarteVerticalement, ExceptionCompartimentHorsDeLaCarteHorizontalement {
        try {
            modelCarte.ajouterUnNavireALaPosition(Objects.requireNonNull(creerAleatoirementUnNavireSousMarinSurLaCarte(modelCarte)));
        }catch (Throwable throwable){
            creerUnNavireDeTypeSousMarinSurLaCarte(modelCarte);
        }
    }

    private void creerUnNavireDeTypeDestroyerSurLaCarte(ModelCarte modelCarte) throws ExceptionPositionDejaOccupe, ExceptionCompartimentHorsDeLaCarteVerticalement, ExceptionCompartimentHorsDeLaCarteHorizontalement {
        try{
            modelCarte.ajouterUnNavireALaPosition(Objects.requireNonNull(creerAleatoirementUnNavireDestroyerSurLaCarte(modelCarte)));

        }catch (Throwable throwable){
            creerUnNavireDeTypeDestroyerSurLaCarte(modelCarte);
        }
    }

    private void creerUnNavireDeTypeCuiraseSurLaCarte(ModelCarte modelCarte) throws ExceptionPositionDejaOccupe, ExceptionCompartimentHorsDeLaCarteVerticalement, ExceptionCompartimentHorsDeLaCarteHorizontalement {
        try{
            modelCarte.ajouterUnNavireALaPosition(Objects.requireNonNull(creerAleatoirementUnNavireCuiraseSurLaCarte(modelCarte)));
        }catch (Throwable throwable){
            creerUnNavireDeTypeCuiraseSurLaCarte(modelCarte);
        }
    }

    private void creerUnNavireDeTypeCroiseurSurLaCarte(ModelCarte modelCarte) throws ExceptionPositionDejaOccupe, ExceptionCompartimentHorsDeLaCarteVerticalement, ExceptionCompartimentHorsDeLaCarteHorizontalement {
        try {
            modelCarte.ajouterUnNavireALaPosition(Objects.requireNonNull(creerAleatoirementUnNavireCroiseurSurLaCarte(modelCarte)));
        }catch (Throwable throwable){
            creerUnNavireDeTypeCroiseurSurLaCarte(modelCarte);
        }
    }

    private PositionSurCarte getPositionSurCarteDepuisUtilisateur(Scanner scanner, boolean choixNavire) throws QuitApplication {
        System.out.printf("Veuillez selectionner un %s (ligne colonne, ex a 1, i 3 ...) %s: ",choixNavire? "navire":"point à attaquer", choixNavire? "'quitter' pour quitter":"");

        var indexElement = scanner.nextLine();
        if (choixNavire && indexElement.equals("quitter")){
            throw new QuitApplication();
        }
        int x,y;
        var indexElements = indexElement.split(" ");
        if (indexElements.length < 2) {
            System.out.printf("chaine %s entrée incorrecte.", indexElement);
            return null;
        }
        x = indexElements[0].charAt(0) - 'a';
        try {
            y = Integer.parseInt(indexElements[1]);
        }catch (NumberFormatException numberFormatException){
            System.out.println(indexElements[1]+" n'est pas un nombre valide.");
            return null;
        }
        if (x < 0 || x >= ModelCarte.TAILLE_CARTE_PAR_DEFAUT) {
            System.out.printf("Le caractère %c n'est pas reconnu comme une ligne valide.", indexElements[0].charAt(0));
            return null;
        }
        if (y < 0 || y >= ModelCarte.TAILLE_CARTE_PAR_DEFAUT) {
            System.out.printf("La valeur %s n'est pas reconnu comme une colonne valide.", indexElements[1]);
            return null;
        }
        return new PositionSurCarte(x,y);
    }
    public static String[] extendArray(String[] originalArray, int extendBy) {
        String[] extendedArray = new String[originalArray.length + extendBy];
        System.arraycopy(originalArray, 0, extendedArray, 0, originalArray.length);
        return extendedArray;
    }

    public void demarerLeJeu(Scanner scanner
    ) throws IOException, ExceptionCompartimentHorsDeLaCarteHorizontalement, ExceptionCompartimentHorsDeLaCarteVerticalement, ExceptionPositionDejaOccupe {

        afficherLesCartes();

        ActionsNavirePossibleSurUneCarteEnum actionChoisisParLutilisateur;

        boolean actionRobotPrecedenteAttaquee = false;

        while (true){
            if (!modelCarteRobot.aEncoreUnNavireCapableDattaquer() || !modelCarte.aEncoreUnNavireCapableDattaquer()) {
                if (!modelCarteRobot.aEncoreUnNavireCapableDattaquer()) {
                    System.out.println(ConsoleColors.GREEN_BACKGROUND + "Felicitation Vous avez remporté cette manche" + ConsoleColors.RESET);
                }
                if (!modelCarte.aEncoreUnNavireCapableDattaquer()) {
                    System.out.println(ConsoleColors.RED_BACKGROUND + "Oushh Vous avez perdu cette manche... peut être une autre fois." + ConsoleColors.RESET);
                }
                return;
            }
            PositionSurCarte positionSurCarteEntreeParLutilisateur = null;
            try {
                positionSurCarteEntreeParLutilisateur = getPositionSurCarteDepuisUtilisateur(scanner, true);
            } catch (QuitApplication e) {

                System.out.println("\nQuitter vers le menu principal?");
                if(new ModelMenuString("Oui","Non").run(scanner) == 1)
                    continue;
                System.out.println("\nSauvegarder la partie en cour?");
                if(new ModelMenuString("Oui","Non").run(scanner) == 0) {
                    sauvegarderPlateau();
                    System.out.println(ConsoleColors.GREEN_BACKGROUND+"Partie sauvegardée avec succès."+ConsoleColors.RESET);
                }
                break;
            }
            if (positionSurCarteEntreeParLutilisateur == null)
                continue;
            ModelNavire modelNavireTrouve = modelCarte.recupererNavireAlaPosition(positionSurCarteEntreeParLutilisateur);
            if (modelNavireTrouve == null){
                System.out.printf("Aucun navire trouvé à la position %c %d%n",positionSurCarteEntreeParLutilisateur.getX()+'a',positionSurCarteEntreeParLutilisateur.getY());
                continue;
            }else {

                System.out.printf("Navire trouvé à la position %c %d: %s%n",positionSurCarteEntreeParLutilisateur.getX()+'a',positionSurCarteEntreeParLutilisateur.getY(),modelNavireTrouve.getTypeNavire());
            }
            ActionsNavirePossibleSurUneCarteEnum[] actionsNavirePossibleSurUneCarteEnums = modelCarte.getToutesLesActionsPossiblesDuNavire(modelNavireTrouve);
            if (actionsNavirePossibleSurUneCarteEnums.length <= 0) {
                System.out.println("le navire choisis ne peut effectuer aucune action, veuillez reessayer");
                continue;
            }
            String [] actionsNavirePossibleSurUneCarteEnumsString = Arrays.stream(actionsNavirePossibleSurUneCarteEnums).map(Object::toString).toArray(String[]::new);
            actionsNavirePossibleSurUneCarteEnumsString = extendArray(actionsNavirePossibleSurUneCarteEnumsString,1);
            actionsNavirePossibleSurUneCarteEnumsString[actionsNavirePossibleSurUneCarteEnumsString.length-1] = "Annuler";
            var choixEntierEffectueParLutilisateur = new ModelMenuString(actionsNavirePossibleSurUneCarteEnumsString).run(scanner);
            if (choixEntierEffectueParLutilisateur>=actionsNavirePossibleSurUneCarteEnums.length || choixEntierEffectueParLutilisateur<0)
                continue;
            actionChoisisParLutilisateur = actionsNavirePossibleSurUneCarteEnums[choixEntierEffectueParLutilisateur];

            // scanner.close();

            executerActionPrise(modelCarte, modelCarteRobot, actionChoisisParLutilisateur, scanner, modelNavireTrouve);
            clearScreen();
            afficherLesCartes();
            System.out.println("--------------------------------"+" Appuyer sur entrée pour laisser l'ordinateur jouer "+"--------------------------------");
            scanner.nextLine();
            boolean actionRobotTermineAvecSuccess = false;


            while(!actionRobotTermineAvecSuccess) {

                System.out.println("--------------------------------"+" L'ordinateur effectue un choix de navire"+"--------------------------------");

                var navireChoisiParRobot = modelCarteRobot.choisirAleatoirementUnNavire();
                System.out.printf("L'ordinateur a choisi le navire de type %s à la position %c%d.%n",navireChoisiParRobot.getTypeNavire(),navireChoisiParRobot.getPremierCompartimentNavire().getX()+'a',navireChoisiParRobot.getPremierCompartimentNavire().getY()
                );

                ActionsNavirePossibleSurUneCarteEnum[] actionsNavirePossibleSurUneCarteEnumsPourLeRobot = modelCarteRobot.getToutesLesActionsPossiblesDuNavire(navireChoisiParRobot);
                if (actionsNavirePossibleSurUneCarteEnumsPourLeRobot.length <=0) {
                    System.out.println("le navire choisis ne peut effectuer aucune action, L'ordianateur reefectue un choix");
                    continue;
                }
                int actionAleatoireParRobot = 0;
                if (actionRobotPrecedenteAttaquee) {
                    actionAleatoireParRobot = new Random().nextInt(actionsNavirePossibleSurUneCarteEnumsPourLeRobot.length);
                    if (actionAleatoireParRobot ==0){
                        if (actionsNavirePossibleSurUneCarteEnumsPourLeRobot.length>0){
                            actionAleatoireParRobot += new Random().nextInt(actionsNavirePossibleSurUneCarteEnumsPourLeRobot.length-1);
                        }
                    }
                }
                actionRobotPrecedenteAttaquee = actionAleatoireParRobot==0;
                var actionChoisisParLeRobot = actionsNavirePossibleSurUneCarteEnumsPourLeRobot[actionAleatoireParRobot];
                System.out.printf("L'ordinateur a choisit l'action %s%n", actionChoisisParLeRobot);
                actionRobotTermineAvecSuccess = executerActionPrise(modelCarteRobot, modelCarte, actionChoisisParLeRobot, null, navireChoisiParRobot);

            }
            System.out.println("--------------------------------"+" Action effectué par l'ordinateur, A votre tour "+"--------------------------------");
            clearScreen();
            afficherLesCartes();
        }
    }

    private boolean executerActionPrise(ModelCarte attaquant, ModelCarte attaque, ActionsNavirePossibleSurUneCarteEnum actionChoisisParLutilisateur, Scanner scanner, ModelNavire modelNavireTrouve) {
        PositionSurCarte positionSurCarteEntreeParLutilisateur;
        switch (actionChoisisParLutilisateur) {
            case NORD -> {
                try {
                    attaquant.effectuerUnDeplacementAvecLeNavire(modelNavireTrouve, ActionsNavirePossibleSurUneCarteEnum.NORD);
                    return true;
                } catch (ExceptionCompartimentPresentEnAvant | ExceptionPositionDejaOccupe e) {
                    System.out.println(ConsoleColors.RED + "Impossible de se deplacer au Nord, case occupée" + ConsoleColors.RESET);
                } catch (ExceptionAucunNavireTrouverACettePosition e) {
                    System.out.println(ConsoleColors.RED + "Choix de Navire Incorrect" + ConsoleColors.RESET);
                } catch (Throwable e) {
                    System.out.println(ConsoleColors.RED + "Impossible de se deplacer au Nord, le premier compartiment navire sera hors de la carte" + ConsoleColors.RESET);
                }
            }
            case SUD -> {
                try {
                    attaquant.effectuerUnDeplacementAvecLeNavire(modelNavireTrouve, ActionsNavirePossibleSurUneCarteEnum.SUD);
                    return true;
                } catch (ExceptionCompartimentPresentEnArriere | ExceptionPositionDejaOccupe e) {
                    System.out.println(ConsoleColors.RED + "Impossible de se deplacer au Sud, case occupé" + ConsoleColors.RESET);
                } catch (ExceptionAucunNavireTrouverACettePosition e) {
                    System.out.println(ConsoleColors.RED + "Choix de Navire Incorrect" + ConsoleColors.RESET);
                } catch (Throwable e) {
                    System.out.println(ConsoleColors.RED + "Impossible de se deplacer au Sud, le denier compartiment navire sera hors de la carte" + ConsoleColors.RESET);
                }
            }
            case OUEST -> {
                try {
                    attaquant.effectuerUnDeplacementAvecLeNavire(modelNavireTrouve, ActionsNavirePossibleSurUneCarteEnum.OUEST);
                    return true;
                } catch (ExceptionCompartimentPresentEnAvant | ExceptionPositionDejaOccupe e) {
                    System.out.println(ConsoleColors.RED + "Impossible de se deplacer à l'Ouest, case occupée" + ConsoleColors.RESET);
                } catch (ExceptionAucunNavireTrouverACettePosition e) {
                    System.out.println(ConsoleColors.RED + "Choix de Navire Incorrect" + ConsoleColors.RESET);
                } catch (Throwable e) {
                    System.out.println(ConsoleColors.RED + "Impossible de se deplacer à l'Ouest, le premier compartiment navire sera hors de la carte" + ConsoleColors.RESET);
                }
            }
            case EST -> {
                try {
                    attaquant.effectuerUnDeplacementAvecLeNavire(modelNavireTrouve, ActionsNavirePossibleSurUneCarteEnum.EST);
                    return true;
                } catch (ExceptionCompartimentPresentEnArriere | ExceptionPositionDejaOccupe e) {
                    System.out.println(ConsoleColors.RED + "Impossible de se deplacer à l'Est, case occupé" + ConsoleColors.RESET);
                } catch (ExceptionAucunNavireTrouverACettePosition e) {
                    System.out.println(ConsoleColors.RED + "Choix de Navire Incorrect" + ConsoleColors.RESET);
                } catch (Throwable e) {
                    System.out.println(ConsoleColors.RED + "Impossible de se deplacer à l'Est, le dernier compartiment navire sera hors de la carte" + ConsoleColors.RESET);
                }
            }
            case ATTAQUER -> {

                try {
                    if (scanner != null)
                        positionSurCarteEntreeParLutilisateur = getPositionSurCarteDepuisUtilisateur(scanner, false);
                    else {
                        int xRobot;
                        int yRobot;
                        if (attaquant.isCiblePrecedentAtteinte()) {
                            xRobot = new Random().nextInt(ModelCarte.TAILLE_CARTE_PAR_DEFAUT);
                            yRobot = new Random().nextInt(ModelCarte.TAILLE_CARTE_PAR_DEFAUT);
                            attaquant.setCiblePrecedentAtteinte(false);
                        }else{
                            var naviresAttaque = attaque.getModelNaviresAsArray();
                            var navireAattaqueChoisi = (ModelNavire)naviresAttaque[new Random().nextInt(naviresAttaque.length)];
                            xRobot = navireAattaqueChoisi.getTopLeftX();
                            yRobot = navireAattaqueChoisi.getTopLeftY();
                            attaquant.setCiblePrecedentAtteinte(true);
                        }
                        positionSurCarteEntreeParLutilisateur = new PositionSurCarte(xRobot, yRobot);

                    }
                    attaque.encaisserUneAttatque(modelNavireTrouve, positionSurCarteEntreeParLutilisateur);
                    return true;
                } catch (ExceptionAttaqueSurUnePositionHorsDeLaCarte e) {
                    System.out.println(ConsoleColors.RED + "Impossible d'attaquer cette position elle est hors de la carte." + ConsoleColors.RESET);
                } catch (QuitApplication e) {
                    throw new RuntimeException(e);
                }

            }
        }
        return false;
    }

}
