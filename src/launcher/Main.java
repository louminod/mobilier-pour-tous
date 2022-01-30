package launcher;

import java.util.Scanner;

import entitiees.Archive;
import procedures.*;
import tools.Activites;
import tools.Menu;

public class Main {
    public static void main(String[] args) {

        Menu menu = new Menu();
        Scanner scanner = new Scanner(System.in);
        Activites activites = new Activites();

        Procedure procedurePersonnes = new ProcedurePersonnes(menu, scanner);
        Procedure procedureDons = new ProcedureDons(menu, scanner, (ProcedurePersonnes) procedurePersonnes, activites);
        Procedure procedureDestinations = new ProcedureDestination(menu, scanner, activites);
        Procedure procedureRecherche = new ProcedureRecherche(menu, scanner, activites, (ProcedureDons) procedureDons);
        Procedure procedureStatistiques = new ProcedureStatistiques(menu, scanner, activites, (ProcedureDons) procedureDons, (ProcedurePersonnes) procedurePersonnes);
        ProcedureAssociation procedureAssociation = new ProcedureAssociation(menu, scanner, (ProcedurePersonnes) procedurePersonnes, activites);

        boolean quitter = false;

        while (!quitter) {

            menu.afficherMenuAccueil();
            System.out.print("Votre choix : ");

            switch (scanner.next()) {
                case "1":
                    procedurePersonnes.lancerProcedure();
                    break;
                case "2":
                    procedureDons.lancerProcedure();
                    break;
                case "3":
                    procedureRecherche.lancerProcedure();
                    break;
                case "4":
                    procedureStatistiques.lancerProcedure();
                    break;
                case "5":
                    procedureDestinations.lancerProcedure();
                    break;
                case "6":
                    procedureAssociation.lancerProcedure();
                    break;
                case "7":
                    quitter = true;
                    System.out.println("\n -- Au revoir ! -- ");
                    break;
                default:
                    System.out.println("/!\\ Le choix selectionné ne fait pas parti de ceux proposés /!\\");
                    break;
            }
            ((ProcedureStatistiques) procedureStatistiques).updateLstDons();
        }
        scanner.close();
    }
}
