//import org.kwhat.jnativehook.NativeKeyListener;

import enums.ActionsNavirePossibleSurUneCarteEnum;
import exceptions.*;
import model.*;

import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

import static java.lang.Thread.sleep;

public class Main implements Runnable {
    public static final String ACTION_AVANCER = "avancer";
    public static final String ACTION_RECULER = "reculer";
    ModelCarte modelCartesJoueur;
    ModelCarte modelCartesRobot;
    Main(ModelCarte modelCartesJoueur, ModelCarte modelCartesRobot){
        this.modelCartesJoueur = modelCartesJoueur;
        this.modelCartesRobot = modelCartesRobot;
    }
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
    public static ModelNavire creerAleatoirementUnNavireCroiseurSurLaCarte(ModelCarte modelCarte) throws ExceptionCompartimentHorsDeLaCarteVerticalement, ExceptionCompartimentHorsDeLaCarteHorizontalement {
        var orientationEtPositionNavire = modelCarte.trouverUneOrientationEtUnePositionPourUneNavireDeTaille(ModelNavireCroiseur.TAILLE);
        return orientationEtPositionNavire != null ? new ModelNavireCroiseur(orientationEtPositionNavire.orientationNavireEnum(), orientationEtPositionNavire.positionSurCarte()):null;
    }
    public static ModelNavire creerAleatoirementUnNavireCuiraseSurLaCarte(ModelCarte modelCarte) throws ExceptionCompartimentHorsDeLaCarteVerticalement, ExceptionCompartimentHorsDeLaCarteHorizontalement {
        var orientationEtPositionNavire = modelCarte.trouverUneOrientationEtUnePositionPourUneNavireDeTaille(ModelNavireCuirase.TAILLE);
        return orientationEtPositionNavire != null ? new ModelNavireCuirase(orientationEtPositionNavire.orientationNavireEnum(), orientationEtPositionNavire.positionSurCarte()):null;
    }
    public static ModelNavire creerAleatoirementUnNavireDestroyerSurLaCarte(ModelCarte modelCarte) throws ExceptionCompartimentHorsDeLaCarteVerticalement, ExceptionCompartimentHorsDeLaCarteHorizontalement {
        var orientationEtPositionNavire = modelCarte.trouverUneOrientationEtUnePositionPourUneNavireDeTaille(ModelNavireDestroyer.TAILLE);
        return orientationEtPositionNavire != null ? new ModelNavireDestroyer(orientationEtPositionNavire.orientationNavireEnum(), orientationEtPositionNavire.positionSurCarte()):null;
    }
    public static ModelNavire creerAleatoirementUnNavireSousMarinSurLaCarte(ModelCarte modelCarte) throws ExceptionCompartimentHorsDeLaCarteVerticalement, ExceptionCompartimentHorsDeLaCarteHorizontalement {
        var orientationEtPositionNavire = modelCarte.trouverUneOrientationEtUnePositionPourUneNavireDeTaille(ModelNavireSousMarin.TAILLE);
        return orientationEtPositionNavire != null ? new ModelNavireSousMarin(orientationEtPositionNavire.orientationNavireEnum(), orientationEtPositionNavire.positionSurCarte()):null;
    }
    private static void creerAleatoirementTousLesNaviresSurLaCarte(ModelCarte modelCarte) throws ExceptionPositionDejaOccupe, ExceptionCompartimentHorsDeLaCarteVerticalement, ExceptionCompartimentHorsDeLaCarteHorizontalement {
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

    private static void creerUnNavireDeTypeSousMarinSurLaCarte(ModelCarte modelCarte) throws ExceptionPositionDejaOccupe, ExceptionCompartimentHorsDeLaCarteVerticalement, ExceptionCompartimentHorsDeLaCarteHorizontalement {
        try {
            modelCarte.ajouterUnNavireALaPosition(Objects.requireNonNull(creerAleatoirementUnNavireSousMarinSurLaCarte(modelCarte)));
        }catch (Throwable throwable){
            creerUnNavireDeTypeSousMarinSurLaCarte(modelCarte);
        }
    }

    private static void creerUnNavireDeTypeDestroyerSurLaCarte(ModelCarte modelCarte) throws ExceptionPositionDejaOccupe, ExceptionCompartimentHorsDeLaCarteVerticalement, ExceptionCompartimentHorsDeLaCarteHorizontalement {
        try{
            modelCarte.ajouterUnNavireALaPosition(Objects.requireNonNull(creerAleatoirementUnNavireDestroyerSurLaCarte(modelCarte)));

        }catch (Throwable throwable){
            creerUnNavireDeTypeDestroyerSurLaCarte(modelCarte);
        }
    }

    private static void creerUnNavireDeTypeCuiraseSurLaCarte(ModelCarte modelCarte) throws ExceptionPositionDejaOccupe, ExceptionCompartimentHorsDeLaCarteVerticalement, ExceptionCompartimentHorsDeLaCarteHorizontalement {
        try{
            modelCarte.ajouterUnNavireALaPosition(Objects.requireNonNull(creerAleatoirementUnNavireCuiraseSurLaCarte(modelCarte)));
        }catch (Throwable throwable){
            creerUnNavireDeTypeCuiraseSurLaCarte(modelCarte);
        }
    }

    private static void creerUnNavireDeTypeCroiseurSurLaCarte(ModelCarte modelCarte) throws ExceptionPositionDejaOccupe, ExceptionCompartimentHorsDeLaCarteVerticalement, ExceptionCompartimentHorsDeLaCarteHorizontalement {
        try {
            modelCarte.ajouterUnNavireALaPosition(Objects.requireNonNull(creerAleatoirementUnNavireCroiseurSurLaCarte(modelCarte)));
        }catch (Throwable throwable){
            creerUnNavireDeTypeCroiseurSurLaCarte(modelCarte);
        }
    }

    private static PositionSurCarte getPositionSurCarteDepuisUtilisateur(Scanner scanner, boolean choixNavire) throws QuitApplication {
        System.out.printf("Veuillez selectionner un %s (ligne colonne, ex a 1, i 3 ...) %s ",choixNavire? "navire":"point à attaquer", choixNavire? "'quitter' pour quitter":"");

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

    public static void main(String[] args) throws ExceptionPositionDejaOccupe, ExceptionCompartimentHorsDeLaCarteVerticalement, ExceptionCompartimentHorsDeLaCarteHorizontalement, InterruptedException, ExceptionCompartimentPresentEnAvant, ExceptionAucunNavireTrouverACettePosition, ExceptionCompartimentPresentEnArriere {
        /*int[][] arr = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};

// Determine the center of the array
        int centerX = arr.length / 2;
        int centerY = arr[0].length / 2;

// Iterate over the array starting from the center
        for (int i = -centerX; i <= centerX; i++) {
            for (int j = -centerY; j <= centerY; j++) {
                System.out.print(arr[centerX + i][centerY + j] + " ");
            }
            System.out.println();
        }*/

        ModelCarte modelCarteJoueur = new ModelCarte();
        creerAleatoirementTousLesNaviresSurLaCarte(modelCarteJoueur);

        ModelCarteRobot modelCarteRobot = new ModelCarteRobot();
        creerAleatoirementTousLesNaviresSurLaCarte(modelCarteRobot);

        afficherLesCartes(modelCarteJoueur, modelCarteRobot);

        DeroulementPrincipalDuJeu(modelCarteJoueur, modelCarteRobot);

    }

    private static void DeroulementPrincipalDuJeu(ModelCarte modelCarteJoueur, ModelCarteRobot modelCarteRobot) {
        ActionsNavirePossibleSurUneCarteEnum actionChoisisParLutilisateur;

        while (true){
            Scanner scanner = new Scanner(System.in);

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
            actionChoisisParLutilisateur = actionsNavirePossibleSurUneCarteEnums[new ModelMenuStringActionSurNavire(actionsNavirePossibleSurUneCarteEnums).run(scanner)];

            // scanner.close();

            executerActionPrise(modelCarteJoueur, modelCarteRobot, actionChoisisParLutilisateur, scanner, modelNavireTrouve);
            clearScreen();
            afficherLesCartes(modelCarteJoueur, modelCarteRobot);
            System.out.println("--------------------------------"+" Appuyer sur entrée pour laisser l'ordianteur jouer "+"--------------------------------");
            scanner.nextLine();
            boolean actionRobotTermineAvecSuccess = false;


            while(!actionRobotTermineAvecSuccess) {

                System.out.println("--------------------------------"+" L'ordinateur effectue un choix de navire"+"--------------------------------");

                var navireChoisiParRobot = modelCarteRobot.choisirAleatoirementUnNavire();
                System.out.println("L'ordinateur a choisi un navire.");

                ActionsNavirePossibleSurUneCarteEnum[] actionsNavirePossibleSurUneCarteEnumsPourLeRobot = modelCarteJoueur.getToutesLesActionPossibleDuNavire(navireChoisiParRobot);
                var actionChoisisParLeRobot = actionsNavirePossibleSurUneCarteEnumsPourLeRobot[new Random().nextInt(actionsNavirePossibleSurUneCarteEnumsPourLeRobot.length)];

                actionRobotTermineAvecSuccess = executerActionPrise(modelCarteRobot, modelCarteJoueur, actionChoisisParLeRobot, null, navireChoisiParRobot);

            }
            System.out.println("--------------------------------"+" Action effectué par le Robot, A votre tour "+"--------------------------------");
            clearScreen();
            afficherLesCartes(modelCarteJoueur, modelCarteRobot);
        }
    }

    private static boolean executerActionPrise(ModelCarte attaquant, ModelCarte attaque, ActionsNavirePossibleSurUneCarteEnum actionChoisisParLutilisateur, Scanner scanner, ModelNavire modelNavireTrouve) {
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

    private static void afficherLesCartes(ModelCarte modelCarteJoueur, ModelCarte modelCarteRobot) {
        System.out.println("\nCarte Joueur:\n" + modelCarteJoueur);
        System.out.println("\nCarte Robot:\n" + modelCarteRobot);
    }

    @Override
    public void run() {
        for (int i = 0; i < ModelCarte.TAILLE_CARTE_PAR_DEFAUT/2; i++) {

            try {
                sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            /*try {

                //modelCartesRobot.faireAvancerLeNavireSeTrouvantALaPosition(((ModelNavire)modelCartesRobot.getModelNaviresAsArray()[0]).getPremierCompartimentNavire());
            }catch (ExceptionCompartimentPresentEnAvant e1) {
                try {
                    //modelCartesRobot.faireReculerLeNavireSeTrouvantALaPosition(((ModelNavire)modelCartesRobot.getModelNaviresAsArray()[0]).getPremierCompartimentNavire());
                } catch (Throwable e2) {
                    throw new RuntimeException(e2);
                }
                //throw new RuntimeException(e);
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }*/
        }

    }
}
