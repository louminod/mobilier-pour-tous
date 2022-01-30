package procedures;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import entitiees.DepotVente;
import entitiees.Destination;
import entitiees.Don;
import ressources.Statut;
import ressources.TypeActivite;
import tools.Activites;
import tools.Menu;

public class ProcedureRecherche implements Procedure {
    private Activites activites;
    private ProcedureDons procedureDons;
    private Scanner scanner;
    private Menu menu;

    public ProcedureRecherche(Menu menu, Scanner scanner, Activites activites,
                              ProcedureDons procedureDons) {
        this.scanner = scanner;
        this.menu = menu;
        this.activites = activites;
        this.procedureDons = procedureDons;
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
            this.menu.afficherMenuRecherches();

            switch (this.scanner.next()) {
                case "1": // Rechercher les dons refusés
                    this.rechercherDonRefuse();
                    fini = true;
                    break;
                case "2": // Rechercher les dons en traitement (accepté ou stocké)
                    this.rechercherDonEnTraitement();
                    fini = true;
                    break;
                case "3": // Rechercher les dons vendus
                    this.rechercherDonVendu();
                    fini = true;
                    break;
                case "4": // Rechercher les dons donnés
                    this.rechercherDonDonne();
                    fini = true;
                    break;
                case "5": // Rechercher les dons stockés par entrepôt
                    this.rechercherDonStockeParEntrepot();
                    fini = true;
                    break;
                case "6": // Rechercher les dons par dépôt-vente
                    this.rechercherDonParDepotVente();
                    fini = true;
                    break;
                case "7": // Retour au menu principal
                    fini = true;
                    break;
                default:
                    System.out.println("/!\\ Le choix selectionné ne fait pas partit de ceux proposés /!\\");
                    break;
            }
        }
    }

    private void rechercherDonRefuse() {
        List<Don> lstDonEnTraitement = new ArrayList<>();

        for (Don don : this.activites.getArchive().getLstDonsArchives()) {
            if (don.getStatut() == Statut.REFUSE) {
                System.out.println(don.toString());
                lstDonEnTraitement.add(don);
            }
        }

        this.sauvegarderRecherche(lstDonEnTraitement);
    }

    private void rechercherDonEnTraitement() {
        List<Don> lstDonEnTraitement = new ArrayList<>();

        for (Don don : this.procedureDons.getLstDonsEnAttente()) {
            if (don.getStatut() == Statut.ACCEPTE) {
                lstDonEnTraitement.add(don);
            }
        }

        for (Destination destination : this.activites.getLstDestinations()) {
            for (int identifiant : destination.getMapDons().keySet()) {
                Don don = destination.getMapDons().get(identifiant);
                if (!lstDonEnTraitement.contains(don) && don.getStatut() == Statut.STOCKE) {
                    lstDonEnTraitement.add(don);
                }
            }
        }

        for (Don don : lstDonEnTraitement) {
            System.out.println(don.toString());
        }

        this.sauvegarderRecherche(lstDonEnTraitement);
    }

    private void rechercherDonVendu() {
        List<Don> lstDonEnTraitement = new ArrayList<>();
        for (Don don : this.activites.getArchive().getLstDonsArchives()) {
            if (don.getStatut() == Statut.VENDU) {
                System.out.println(don.toString());
                lstDonEnTraitement.add(don);
            }
        }
        this.sauvegarderRecherche(lstDonEnTraitement);
    }

    private void rechercherDonDonne() {
        List<Don> lstDonEnTraitement = new ArrayList<>();
        for (Don don : this.activites.getArchive().getLstDonsArchives()) {
            if (don.getStatut() == Statut.DONNE) {
                System.out.println(don.toString());
                lstDonEnTraitement.add(don);
            }
        }
        this.sauvegarderRecherche(lstDonEnTraitement);
    }

    private void rechercherDonStockeParEntrepot() {
        System.out.println("Dans quelle entrepot(destination) chercher ? ");

        int i = 0;
        for (Destination destination : this.activites.getLstDestinations()) {
            System.out.println(String.format(" %d. %s", i, destination.getNom()));
        }

        Destination destination = this.activites.getLstDestinations().get(this.scanner.nextInt());

        List<Don> lstDonEnTraitement = new ArrayList<>();

        for (int identifiant : destination.getMapDons().keySet()) {
            Don don = destination.getMapDons().get(identifiant);

            if (don.getStatut() == Statut.STOCKE) {
                System.out.println(don.toString());
                if (!lstDonEnTraitement.contains(don)) {
                    lstDonEnTraitement.add(don);
                }
            }
        }

        this.sauvegarderRecherche(lstDonEnTraitement);
    }

    private void rechercherDonParDepotVente() {
        System.out.println("Dans quel dépôt-vente chercher ? ");

        int i = 0;
        for (Destination destination : this.activites.getLstDestinations()) {
            if (destination.getTypeActivite() == TypeActivite.DEPOT_VENTE) {
                System.out.println(String.format(" %d. %s", i, destination.getNom()));
            }
        }

        Destination depotVente = (DepotVente) this.activites.getLstDestinations().get(this.scanner.nextInt());

        List<Don> lstDonEnTraitement = new ArrayList<>();

        for (int identifiant : depotVente.getMapDons().keySet()) {
            Don don = depotVente.getMapDons().get(identifiant);
            System.out.println(don.toString());
            lstDonEnTraitement.add(don);
        }

        this.sauvegarderRecherche(lstDonEnTraitement);
    }

    private void sauvegarderRecherche(List<Don> lstResultatsRecherche) {
        if(lstResultatsRecherche.size() > 0) {
            BufferedWriter writer = null;
            try {
                LocalDate date = LocalDate.now();
                File file = new File(System.getProperty("user.home") + "/exportRecherche_" + date + ".txt");

                writer = new BufferedWriter(new FileWriter(file));

                for (Don don : lstResultatsRecherche) {
                    writer.write(don.toString());
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    writer.close();
                    System.out.println("Recherche sauvegardée dans : " + System.getProperty("user.home"));
                } catch (Exception e) {
                }
            }
        }
    }
}
