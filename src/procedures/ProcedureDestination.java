package procedures;

import java.util.List;
import java.util.Scanner;

import entitiees.Association;
import entitiees.DepotVente;
import entitiees.Destination;
import entitiees.GardeMeuble;
import entitiees.Transporteur;
import ressources.TypeActivite;
import tools.Activites;
import tools.Menu;

public class ProcedureDestination implements Procedure {

    private Activites activites;
    private Menu menu;
    private Scanner scanner;

    public ProcedureDestination(Menu menu, Scanner scanner, Activites activites) {
        this.menu = menu;
        this.scanner = scanner;
        this.activites = activites;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public void lancerProcedure() {
        boolean fini = false;

        while (!fini) {
            this.menu.afficherMenuDestination();

            switch (this.scanner.next()) {
                case "1": // Créer destination
                    this.creerDestination();
                    fini = true;
                    break;
                case "2": // Supprimer destination
                    this.supprimerDestination();
                    fini = true;
                    break;
                case "3": // Lister destination
                    this.listerDestination();
                    fini = true;
                    break;
                case "4": // Retour au menu principal
                    fini = true;
                    break;
                default:
                    System.out.println("/!\\ Le choix selectionné ne fait pas partit de ceux proposés /!\\");
                    break;
            }
        }
    }

    private void creerDestination() {
        System.out.println("Quel est le type de destination ?");

        for (int i = 0; i < TypeActivite.values().length; i++) {
            System.out.println(String.format("  %d. %s", i, TypeActivite.values()[i]));
        }

        TypeActivite typeActivite = TypeActivite.values()[this.scanner.nextInt()];

        Destination nouvelleDestination = null;

        int identifiant = this.getNouvelIdentifiant();

        System.out.print("Nom :");
        String nom = scanner.next();
        System.out.print("Coordonnées :");
        String coordonnees = scanner.next();
        System.out.print("Téléphone :");
        String telephone = scanner.next();

        switch (typeActivite) {
            case ASSOCIATION:
                Association association = new Association(identifiant);
                association.setNom(nom);
                association.setCoordonnees(coordonnees);
                association.setTelephone(telephone);
                nouvelleDestination = association;
                break;
            case DEPOT_VENTE:
                DepotVente depotVente = new DepotVente(identifiant);
                depotVente.setNom(nom);
                depotVente.setCoordonnees(coordonnees);
                depotVente.setTelephone(telephone);
                nouvelleDestination = depotVente;
                break;
            case GARDE_MEUBLE:
                GardeMeuble gardeMeuble = new GardeMeuble(identifiant);
                gardeMeuble.setNom(nom);
                gardeMeuble.setCoordonnees(coordonnees);
                gardeMeuble.setTelephone(telephone);
                nouvelleDestination = gardeMeuble;
                break;
            case TRANSPORTEUR:
                Transporteur transporteur = new Transporteur(identifiant);
                transporteur.setNom(nom);
                transporteur.setCoordonnees(coordonnees);
                transporteur.setTelephone(telephone);
                nouvelleDestination = transporteur;
                break;
            default:
                break;
        }

        if (nouvelleDestination != null) {
            nouvelleDestination.setTypeActivite(typeActivite);
            this.activites.addDestination(nouvelleDestination);
        }
    }

    private void listerDestination() {
        if (this.activites.getLstDestinations() != null && this.activites.getLstDestinations().size() > 0) {
            Destination destination = this.selectionnerDestination();
            System.out.println(String.format("\n-- %s --\n* Idenfifiant: %d\n* Coordonnées: %s\n* Téléphone: %s",
                    destination.getNom(), destination.getIdentifiant(), destination.getCoordonnees(),
                    destination.getTelephone()));
        } else {
            System.err.println("Aucune destination n'existe");
        }
    }

    private void supprimerDestination() {
        if (this.activites.getLstDestinations() != null && this.activites.getLstDestinations().size() > 0) {
            Destination destination = this.selectionnerDestination();
            System.out.println(String.format("Voulez vous vraiment supprimer %s (oui/non)?", destination.getNom()));

            String reponse = this.scanner.next();

            if (reponse.toLowerCase().equals("oui")) {
                this.activites.getLstDestinations().remove(destination);
            }
        } else {
            System.err.println("Aucune destination n'existe");
        }
    }

    private Destination selectionnerDestination() {
        List<Destination> lstDestinations = this.activites.getLstDestinations();

        int i = 0;
        for (Destination destination : lstDestinations) {
            System.out.println(String.format("  %d. %s", i, destination.toString()));
            i++;
        }

        System.out.println("Choix de la destination :");
        return lstDestinations.get(this.scanner.nextInt());
    }

    private int getNouvelIdentifiant() {
        int nouvelIdentifiant = 0;

        for (Destination destination : this.activites.getLstDestinations()) {
            if (destination.getIdentifiant() > nouvelIdentifiant) {
                nouvelIdentifiant = destination.getIdentifiant();
            }
        }

        return nouvelIdentifiant + 1;
    }
}
