package procedures;

import entitiees.Association;
import entitiees.Destination;
import entitiees.Personne;
import ressources.TypeActivite;
import tools.Activites;
import tools.Menu;

import java.util.List;
import java.util.Scanner;

public class ProcedureAssociation implements Procedure {

    private ProcedurePersonnes procedurePersonnes;
    private Activites activites;
    private Scanner scanner;
    private Menu menu;

    public ProcedureAssociation(Menu menu, Scanner scanner, ProcedurePersonnes procedurePersonnes, Activites activites) {
        this.scanner = scanner;
        this.menu = menu;
        this.procedurePersonnes = procedurePersonnes;
        this.activites = activites;
    }

    @Override
    public void lancerProcedure() {
        boolean fini = false;

        while (!fini) {
            this.menu.afficherMenuAssociation();
            switch (this.scanner.next()) {
                case "1": // Ajout d'un membre à une association
                    this.ajoutMembreAssociation();
                    fini = true;
                    break;
                case "2": // Retour menu
                    fini = true;
                    break;
                default:
                    System.out.println("/!\\ Le choix selectionné ne fait pas partit de ceux proposés /!\\");
                    break;
            }
        }
    }

    private void ajoutMembreAssociation() {
        List<Association> lstAssociations = this.activites.getLstAssociations();
        if(lstAssociations.size() > 0) {

            System.out.println("\nQuelle personne traiter ?");

            int i = 0;
            for (Personne personne : this.procedurePersonnes.getLstPersonnes()) {
                System.out.println(String.format("  %d. %s", i, personne.toString()));
                i++;
            }

            Personne personne = this.procedurePersonnes.getLstPersonnes().get(this.scanner.nextInt());

            System.out.println("\nDans quelle association ajouter cette personne ?");

            i = 0;
            for(Association association : lstAssociations){
                System.out.printf(" %d. %s\n", i, association.getNom());
            }

            Association association = lstAssociations.get(this.scanner.nextInt());

            association.addPersonne(personne);

            System.out.println("La personne à bien été ajoutée");
        } else {
            System.out.println("Il n'existe aucune association.");
        }
    }
}
