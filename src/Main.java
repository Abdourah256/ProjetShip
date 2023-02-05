//import org.kwhat.jnativehook.NativeKeyListener;

import exceptions.*;
//import jline.console.ConsoleReader;
import model.ModelCarte;
import model.ModelMenuString;
import model.ModelPlateauDeJeu;
import utils.AideJeu;
import utils.ChargerJeu;
import utils.ConsolePagination;

import java.io.IOException;
import java.util.*;

import static java.lang.Thread.sleep;

public class Main  {

    ModelCarte modelCartesJoueur;
    ModelCarte modelCartesRobot;
    Main(ModelCarte modelCartesJoueur, ModelCarte modelCartesRobot){
        this.modelCartesJoueur = modelCartesJoueur;
        this.modelCartesRobot = modelCartesRobot;
    }
    public static void main(String[] args) throws ExceptionPositionDejaOccupe, ExceptionCompartimentHorsDeLaCarteVerticalement, ExceptionCompartimentHorsDeLaCarteHorizontalement, InterruptedException, ExceptionCompartimentPresentEnAvant, ExceptionAucunNavireTrouverACettePosition, ExceptionCompartimentPresentEnArriere, IOException, ClassNotFoundException {

        System.out.println(ConsolePagination.genererUneBanniereApartirDe("LA BATAILLE DES NAVIRES"));
        System.out.println();
        Scanner scanner = new Scanner(System.in);


        while(true) {
            ModelMenuString choixUtilisateur = new ModelMenuString("Jouer une partie", "Charger une partie", "Aide", "Quitter");
            int choixEffectuer = choixUtilisateur.run(scanner);
            switch (choixEffectuer){
                case 0 -> new ModelPlateauDeJeu().demarerLeJeu(scanner);
                case 1 -> ChargerJeu.chargerJeu(scanner);
                case 2 -> AideJeu.afficherAideAuJeu(scanner);
                case 3 -> {
                    System.out.println("Merci et à très bientôt.");
                    return;
                }
            }
        }
    }

}







