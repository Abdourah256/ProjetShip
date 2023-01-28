package model;

import java.util.Scanner;

public class ModelMenuString {
    private final String[] modelMenuItems;

    public ModelMenuString(String ...items) {
        this.modelMenuItems = new String[items.length];
        for (int i = 0; i < items.length; i++)
            this.modelMenuItems[i] = String.format("%d -> %s", i+1, items[i]);
    }
    public int run(Scanner scanner){
        afficherTextPourSaisie();
        String choix_texte = "";
        int choix_nombre = 0;
        while (true){
            choix_texte = scanner.nextLine();

            try {
                choix_nombre = Integer.parseInt(choix_texte);
                if (choix_nombre < 1 || choix_nombre > modelMenuItems.length)
                    System.out.println("Désolé le nombre choisi ne fait pas partir de l'intervalle proposée.");
                else
                    return choix_nombre - 1;
            }catch (RuntimeException runtimeException){
                System.out.println("Veuillez entrer un choix correct.");
            }

            afficherTextPourSaisie();
        }


    }

    private void afficherTextPourSaisie() {
        System.out.println(this);
        System.out.printf("Veuillez Faire un choix entre [1-%d]: ", modelMenuItems.length);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (var modelMenuItem :
                modelMenuItems)
            stringBuilder.append(modelMenuItem).append('\n');
        return stringBuilder.toString();
    }
}
