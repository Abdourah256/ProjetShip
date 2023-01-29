package model;

import enums.ActionsNavirePossibleSurUneCarteEnum;
import exceptions.*;

import java.io.*;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

public class ModelPlateauDeJeu implements Serializable {
    private ModelCarte modelCarte;
    private ModelCarteRobot modelCarteRobot;
    
    public ModelPlateauDeJeu() {
        this.modelCarte = new ModelCarte();
        this.modelCarteRobot = new ModelCarteRobot();
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
        FileOutputStream fos = new FileOutputStream(String.format("%s%s.bin",modelCarte.getName(),modelCarteRobot.getName()));

        // create an ObjectOutputStream to write the object
        ObjectOutputStream oos = new ObjectOutputStream(fos);

        // write the object to the file
        oos.writeObject(this);

        // close the streams
        oos.close();
        fos.close();

    }
    private void chargerPlateau() throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(String.format("%s%s.bin",modelCarte.getName(),modelCarteRobot.getName()));
        ObjectInputStream ois = new ObjectInputStream(fis);
        ModelCarte obj = (ModelCarte) ois.readObject();
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

    public void demarerLeJeu(Scanner scanner
    ) throws IOException, ExceptionCompartimentHorsDeLaCarteHorizontalement, ExceptionCompartimentHorsDeLaCarteVerticalement, ExceptionPositionDejaOccupe {

        ModelCarte modelCarteJoueur = new ModelCarte();
        creerAleatoirementTousLesNaviresSurLaCarte(modelCarteJoueur);

        ModelCarteRobot modelCarteRobot = new ModelCarteRobot();
        creerAleatoirementTousLesNaviresSurLaCarte(modelCarteRobot);

        afficherLesCartes();

        ActionsNavirePossibleSurUneCarteEnum actionChoisisParLutilisateur;

        while (true){

            PositionSurCarte positionSurCarteEntreeParLutilisateur = null;
            try {
                positionSurCarteEntreeParLutilisateur = getPositionSurCarteDepuisUtilisateur(scanner, true);
            } catch (QuitApplication e) {

                System.out.println("Quitter vers le menu principal?");
                if(new ModelMenuString("Oui","Non").run(scanner) == 1)
                    continue;
                System.out.println("Sauvegarder la partie en cour?");
                if(new ModelMenuString("Oui","Non").run(scanner) == 0){

                }
                break;
            }
            if (positionSurCarteEntreeParLutilisateur == null)
                continue;
            ModelNavire modelNavireTrouve = modelCarteJoueur.recupererNavireAlaPosition(positionSurCarteEntreeParLutilisateur);
            if (modelNavireTrouve == null){
                System.out.printf("Aucun navire trouvé à la position %c %d%n",positionSurCarteEntreeParLutilisateur.getX()+'a',positionSurCarteEntreeParLutilisateur.getY());
                continue;
            }else {

                System.out.printf("Navire trouvé à la position %c %d: %s%n",positionSurCarteEntreeParLutilisateur.getX()+'a',positionSurCarteEntreeParLutilisateur.getY(),modelNavireTrouve.getTypeNavire());
            }
            ActionsNavirePossibleSurUneCarteEnum[] actionsNavirePossibleSurUneCarteEnums = modelCarteJoueur.getToutesLesActionPossibleDuNavire(modelNavireTrouve);
            if (actionsNavirePossibleSurUneCarteEnums.length <= 0) {
                System.out.println("le navire choisis ne peut effectuer aucune action, veuillez reessayer");
                continue;
            }
            actionChoisisParLutilisateur = actionsNavirePossibleSurUneCarteEnums[new ModelMenuStringActionSurNavire(actionsNavirePossibleSurUneCarteEnums).run(scanner)];

            // scanner.close();

            executerActionPrise(modelCarteJoueur, modelCarteRobot, actionChoisisParLutilisateur, scanner, modelNavireTrouve);
            clearScreen();
            afficherLesCartes();
            System.out.println("--------------------------------"+" Appuyer sur entrée pour laisser l'ordinateur jouer "+"--------------------------------");
            scanner.nextLine();
            boolean actionRobotTermineAvecSuccess = false;


            while(!actionRobotTermineAvecSuccess) {

                System.out.println("--------------------------------"+" L'ordinateur effectue un choix de navire"+"--------------------------------");

                var navireChoisiParRobot = modelCarteRobot.choisirAleatoirementUnNavire();
                System.out.println("L'ordinateur a choisi un navire.");

                ActionsNavirePossibleSurUneCarteEnum[] actionsNavirePossibleSurUneCarteEnumsPourLeRobot = modelCarteJoueur.getToutesLesActionPossibleDuNavire(navireChoisiParRobot);
                if (actionsNavirePossibleSurUneCarteEnumsPourLeRobot.length <=0) {
                    System.out.println("le navire choisis ne peut effectuer aucune action, veuillez reessayer");
                    continue;
                }
                var actionChoisisParLeRobot = actionsNavirePossibleSurUneCarteEnumsPourLeRobot[new Random().nextInt(actionsNavirePossibleSurUneCarteEnumsPourLeRobot.length)];

                actionRobotTermineAvecSuccess = executerActionPrise(modelCarteRobot, modelCarteJoueur, actionChoisisParLeRobot, null, navireChoisiParRobot);

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
                        var xRobot = new Random().nextInt(ModelCarte.TAILLE_CARTE_PAR_DEFAUT);
                        var yRobot = new Random().nextInt(ModelCarte.TAILLE_CARTE_PAR_DEFAUT);
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
