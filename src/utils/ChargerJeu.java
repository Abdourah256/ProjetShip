package utils;

import exceptions.ExceptionCompartimentHorsDeLaCarteHorizontalement;
import exceptions.ExceptionCompartimentHorsDeLaCarteVerticalement;
import exceptions.ExceptionPositionDejaOccupe;
import model.ModelMenuString;
import model.ModelPlateauDeJeu;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

public class ChargerJeu {
    public static void chargerJeu(Scanner scanner) throws IOException, ExceptionCompartimentHorsDeLaCarteHorizontalement, ExceptionCompartimentHorsDeLaCarteVerticalement, ExceptionPositionDejaOccupe, ClassNotFoundException {
        File dir = new File(".");
        File[] files = dir.listFiles();
        assert files != null;
        int counter = 0;
        var sauvegardes = new ArrayList<String>();
        var choix_de_sauvegardes_a_charger = new ArrayList<String>();
        for (File file : files) {
            // .format(DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss"))
            if (file.getName().endsWith(ModelPlateauDeJeu.GAME_SAVED_BIN)) {
                counter ++;
                sauvegardes.add(file.getName());
                choix_de_sauvegardes_a_charger.add(String.format("Sauvegarde %d - Date de sauvegarde: %s", counter, new Date(file.lastModified())));
            }
        }
        if (counter == 0){
            System.out.println("Aucune sauvegarde n'a été effectuée.");
        }else{
            choix_de_sauvegardes_a_charger.add("annuler");
            var choixUtilisateur = new ModelMenuString(Arrays.stream(choix_de_sauvegardes_a_charger.toArray()).map(Object::toString).toArray(String[]::new)).run(scanner);
            if (choixUtilisateur<sauvegardes.size() && choixUtilisateur>=0){
                ModelPlateauDeJeu modelPlateauDeJeuCharge = new ModelPlateauDeJeu();
                modelPlateauDeJeuCharge.chargerPlateau(sauvegardes.get(choixUtilisateur));
                modelPlateauDeJeuCharge.demarerLeJeu(scanner);
            }
        }
    }
}
