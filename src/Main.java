//import org.kwhat.jnativehook.NativeKeyListener;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import model.*;
import sun.misc.Signal;
import sun.misc.SignalHandler;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Objects;
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

    private static PositionSurCarte getPositionSurCarteDepuisUtilisateur(Scanner scanner, boolean choixNavire){
        System.out.printf("Veuillez selectionner un %s (ligne colonne, ex a 1, i 3 ...): ",choixNavire? "navire":"point à attaquer");
        var indexElement = scanner.nextLine();
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

    public static void main(String[] args) throws ExceptionPositionDejaOccupe, ExceptionCompartimentHorsDeLaCarteVerticalement, ExceptionCompartimentHorsDeLaCarteHorizontalement, InterruptedException, ExceptionCompartimentPresentEnAvant, AucunNavireTrouverACettePosition, ExceptionCompartimentPresentEnArriere {
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
        var listeDeNavireJoueur = modelCarteJoueur.getModelNaviresAsArray();
        ModelCarte modelCarteRobot = new ModelCarteRobot();
        creerAleatoirementTousLesNaviresSurLaCarte(modelCarteRobot);
        var listeDeNavireRobot = modelCarteJoueur.getModelNaviresAsArray();
        Main main_object = new Main(modelCarteJoueur, modelCarteRobot);
        // pass the runnable reference to Thread
        // Thread t = new Thread(main_object, "main_object");
        afficherLesCartes(modelCarteJoueur, modelCarteRobot);
        //modelCarteRobot.faireAvancerLeNavireALaPosition(((ModelNavire)modelCarteRobot.getModelNaviresAsArray()[0]).getPremierCompartimentNavire());


        boolean choixEnCour = true;
        ActionsNavirePossibleSurUneCarteEnum input = null;


        while (choixEnCour){
            Scanner scanner = new Scanner(System.in);
            var positionSurCarteEntreeParLutilisateur = getPositionSurCarteDepuisUtilisateur(scanner, true);
            if (positionSurCarteEntreeParLutilisateur == null)continue;
            ModelNavire modelNavireTrouve = modelCarteJoueur.recupererNavireAlaPosition(positionSurCarteEntreeParLutilisateur);
            if (modelNavireTrouve == null){
                System.out.printf("Aucun navire trouvé à la position %c %d%n",positionSurCarteEntreeParLutilisateur.getX()+'a',positionSurCarteEntreeParLutilisateur.getY());
                continue;
            }else {

                System.out.printf("Navire trouvé à la position %c %d: %s%n",positionSurCarteEntreeParLutilisateur.getX()+'a',positionSurCarteEntreeParLutilisateur.getY(),modelNavireTrouve.getTypeNavire());
            }
            ActionsNavirePossibleSurUneCarteEnum[] actionsNavirePossibleSurUneCarteEnums = modelCarteJoueur.getToutesLesActionPossibleDuNavire(modelNavireTrouve);
            input = actionsNavirePossibleSurUneCarteEnums[new ModelMenuStringActionSurNavire(actionsNavirePossibleSurUneCarteEnums).run(scanner)];

            // scanner.close();

            switch (input) {
                case NORD -> {
                    try {
                        modelCarteJoueur.effectuerUnDeplacementAvecLeNavire(modelNavireTrouve, ActionsNavirePossibleSurUneCarteEnum.NORD);

                    } catch (ExceptionCompartimentPresentEnAvant | ExceptionPositionDejaOccupe e) {
                        System.out.println(ConsoleColors.RED + "Impossible de se deplacer au Nord, case occupée" + ConsoleColors.RESET);
                    } catch (AucunNavireTrouverACettePosition e) {
                        System.out.println(ConsoleColors.RED + "Choix de Navire Incorrect" + ConsoleColors.RESET);
                    } catch (Throwable e) {
                        System.out.println(ConsoleColors.RED + "Impossible de se deplacer au Nord, le premier compartiment navire sera hors de la carte" + ConsoleColors.RESET);
                    }
                }
                case SUD -> {
                    try {
                        modelCarteJoueur.effectuerUnDeplacementAvecLeNavire(modelNavireTrouve, ActionsNavirePossibleSurUneCarteEnum.SUD);

                    } catch (ExceptionCompartimentPresentEnArriere | ExceptionPositionDejaOccupe e) {
                        System.out.println(ConsoleColors.RED + "Impossible de se deplacer au Sud, case occupé" + ConsoleColors.RESET);
                    } catch (AucunNavireTrouverACettePosition e) {
                        System.out.println(ConsoleColors.RED + "Choix de Navire Incorrect" + ConsoleColors.RESET);
                    } catch (Throwable e) {
                        System.out.println(ConsoleColors.RED + "Impossible de se deplacer au Sud, le denier compartiment navire sera hors de la carte" + ConsoleColors.RESET);
                    }
                }
                case OUEST -> {
                    try {
                        modelCarteJoueur.effectuerUnDeplacementAvecLeNavire(modelNavireTrouve, ActionsNavirePossibleSurUneCarteEnum.OUEST);

                    } catch (ExceptionCompartimentPresentEnAvant | ExceptionPositionDejaOccupe e) {
                        System.out.println(ConsoleColors.RED + "Impossible de se deplacer à l'Ouest, case occupée" + ConsoleColors.RESET);
                    } catch (AucunNavireTrouverACettePosition e) {
                        System.out.println(ConsoleColors.RED + "Choix de Navire Incorrect" + ConsoleColors.RESET);
                    } catch (Throwable e) {
                        System.out.println(ConsoleColors.RED + "Impossible de se deplacer à l'Ouest, le premier compartiment navire sera hors de la carte" + ConsoleColors.RESET);
                    }
                }
                case EST -> {
                    try {
                        modelCarteJoueur.effectuerUnDeplacementAvecLeNavire(modelNavireTrouve, ActionsNavirePossibleSurUneCarteEnum.EST);

                    } catch (ExceptionCompartimentPresentEnArriere | ExceptionPositionDejaOccupe e) {
                        System.out.println(ConsoleColors.RED + "Impossible de se deplacer à l'Est, case occupé" + ConsoleColors.RESET);
                    } catch (AucunNavireTrouverACettePosition e) {
                        System.out.println(ConsoleColors.RED + "Choix de Navire Incorrect" + ConsoleColors.RESET);
                    } catch (Throwable e) {
                        System.out.println(ConsoleColors.RED + "Impossible de se deplacer à l'Est, le dernier compartiment navire sera hors de la carte" + ConsoleColors.RESET);
                    }
                }
                case ATTAQUER -> {

                    try {
                        positionSurCarteEntreeParLutilisateur = getPositionSurCarteDepuisUtilisateur(scanner, false);
                        modelCarteRobot.encaisserUneAttatque(modelNavireTrouve,positionSurCarteEntreeParLutilisateur);
                    } catch (ExceptionAttaqueSurUnePositionHorsDeLaCarte e) {
                        System.out.println(ConsoleColors.RED + "Impossible d'attaquer cette position elle est hors de la carte." + ConsoleColors.RESET);
                    }

                }
            }
            clearScreen();
            afficherLesCartes(modelCarteJoueur, modelCarteRobot);
        }

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


class SnakeGame extends KeyAdapter {
    private static final int MAX_X = 20;
    private static final int MAX_Y = 20;
    private static int xPos;
    private static int yPos;
    private static int foodX;
    private static int foodY;
    private static int score;
    private static boolean gameOver;

    public static void main(String[] args) {
        Signal.handle(new Signal("INT"), new SignalHandler() {
            // Signal handler method
            public void handle(Signal signal) {
                System.out.println("Got signal" + signal);
            }
        });
        initGame();

        while (!gameOver) {
            displayGame();
            processInput();
            updateGame();
            checkCollision();
        }

        System.out.println("Game Over! Your score is: " + score);
    }

    private static void initGame() {
        xPos = MAX_X / 2;
        yPos = MAX_Y / 2;
        foodX = (int) (Math.random() * MAX_X);
        foodY = (int) (Math.random() * MAX_Y);
        score = 0;
        gameOver = false;
    }

    private static void displayGame() {
        for (int i = 0; i < MAX_X; i++) {
            for (int j = 0; j < MAX_Y; j++) {
                if (i == yPos && j == xPos) {
                    System.out.print("O");
                } else if (i == foodY && j == foodX) {
                    System.out.print("X");
                } else {
                    System.out.print("-");
                }
            }
            System.out.println();
        }
        System.out.println("Score: " + score);
    }

    private static void processInput(){
        InputStream sysInBackup = System.in; // backup System.in to restore it later
        ByteArrayInputStream in = new ByteArrayInputStream("a".getBytes());
        System.setIn(in);

        // do your thing

        // optionally, reset System.in to its original
        System.setIn(sysInBackup);
        Scanner scanner = new Scanner(System.in);
        String input;

        input = scanner.nextLine();

        switch (input) {
            case "q" -> yPos--;
            case "z" -> xPos--;
            case "s" -> yPos++;
            case "d" -> xPos++;
        }
    }
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_W) {
            yPos--;
        } else if (key == KeyEvent.VK_A) {
            xPos--;
        } else if (key == KeyEvent.VK_S) {
            yPos++;
        } else if (key == KeyEvent.VK_D) {
            xPos++;
        }
    }
    private static void updateGame() {
        if (xPos == foodX && yPos == foodY) {
            score++;
            foodX = (int) (Math.random() * MAX_X);
            foodY = (int) (Math.random() * MAX_Y);
        }
    }

    private static void checkCollision() {
        if (xPos < 0 || xPos >= MAX_X || yPos < 0 || yPos >= MAX_Y) {
            gameOver = true;
        }
    }
}

class GlobalKeyListenerExample implements NativeKeyListener {
    public void nativeKeyPressed(NativeKeyEvent e) {
        System.out.println("Key Pressed: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
    }

    public void nativeKeyReleased(NativeKeyEvent e) {
        System.out.println("Key Released: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
    }

    public void nativeKeyTyped(NativeKeyEvent e) {
        System.out.println("Key Typed: " + e.getKeyText(e.getKeyCode()));
    }

    public static void main(String[] args) {
        try {
            GlobalScreen.registerNativeHook();

            GlobalKeyListenerExample listener = new GlobalKeyListenerExample();
            GlobalScreen.addNativeKeyListener(listener);// getInstance().addNativeKeyListener(listener);

        } catch (NativeHookException ex) {
            System.err.println(ex.getMessage());
        }
    }
}