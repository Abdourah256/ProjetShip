package main.java.utils;

import main.java.model.ConsoleColors;

import java.awt.*;
import java.util.Objects;
import java.util.Scanner;

public class ConsolePagination {

    public static String genererUneBanniereApartirDe(String text) {
        StringBuilder banner = new StringBuilder();
        int width = text.length() + 4;
        banner.append(ConsoleColors.GREEN_BACKGROUND);
        banner.append("*".repeat(Math.max(0, width)));
        banner.append(ConsoleColors.RESET);
        banner.append("\n");
        banner.append(ConsoleColors.GREEN_BACKGROUND+"* ").append(text).append(" *"+ConsoleColors.RESET+"\n");
        banner.append(ConsoleColors.GREEN_BACKGROUND);
        banner.append("*".repeat(Math.max(0, width)));
        banner.append(ConsoleColors.RESET);

        return banner.toString();
    }

    public static boolean print(String text, Scanner scanner) {
        String[] lines = text.split("\n");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int pageSize = (int) (screenSize.height / 20);
        int lineCount = lines.length;
        int pageCount = (lineCount + pageSize - 1) / pageSize;
        int currentPage = 0;

        while (currentPage < pageCount) {
            int start = currentPage * pageSize;
            int end = Math.min((currentPage + 1) * pageSize, lineCount);
            System.out.println("Page " + (currentPage + 1) + "/" + pageCount + ":");
            for (int i = start; i < end; i++) {
                System.out.println(lines[i]);
            }
            System.out.println();
            System.out.print("Appuyer Entree pour continuer[entrer quitter pour quitter]... ");
            if (Objects.equals(scanner.nextLine(), "quitter"))
                return false;
            currentPage++;
        }
        return true;
    }
}
