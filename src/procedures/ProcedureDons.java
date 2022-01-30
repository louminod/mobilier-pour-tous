package procedures;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import entitiees.*;
import ressources.Statut;
import ressources.TypeMateriel;
import tools.Activites;
import tools.Menu;

public class ProcedureDons implements Procedure {

    private ProcedurePersonnes procedurePersonnes;

    private List<Don> lstDonsEnAttente;
    private Activites activites;
    private Scanner scanner;
    private Menu menu;

    public ProcedureDons(Menu menu, Scanner scanner, ProcedurePersonnes procedurePersonnes,
                         Activites activites) {
        this.scanner = scanner;
        this.menu = menu;
        this.procedurePersonnes = procedurePersonnes;
        this.lstDonsEnAttente = new ArrayList<>();
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

    public List<Don> getLstDonsEnAttente() {
        return lstDonsEnAttente;
    }

    @Override
    public void lancerProcedure() {
        boolean fini = false;

        while (!fini) {
            this.menu.afficherMenuDons();
            switch (this.scanner.next()) {
                case "1": // Création d'un don
                    this.creationDon();
                    fini = true;
                    break;
                case "2": // Administration des propositions de dons
                    this.administrerPropositionDon();
                    fini = true;
                    break;
                case "3": // Stockage d'un don
                    this.gererStockageDon();
                    fini = true;
                    break;
                case "4": // Transfert du don au bénéficiaire
                    this.transfererDonABeneficiaire();
                    fini = true;
                    break;
                case "5": // Retour menu principal
                    fini = true;
                    break;
                default:
                    System.out.println("/!\\ Le choix selectionné (%s) ne fait pas partit de ceux proposés /!\\");
                    break;
            }
        }
    }

    private void creationDon() {
        Don nouveauDon = new Don(this.getLastDonReference());

        nouveauDon.setGestionnaire(this.adherentActuel());
        nouveauDon.setDateReception(LocalDate.now());

        System.out.println("Type de matériel :");

        for (int i = 0; i < TypeMateriel.values().length; i++) {
            System.out.println(String.format("  %d %s", i, TypeMateriel.values()[i]));
        }

        nouveauDon.setTypeMateriel(TypeMateriel.values()[this.scanner.nextInt()]);

        System.out.print("Nom du donateur : ");
        nouveauDon.setDonateur(this.procedurePersonnes.chercherPersonne(this.scanner.next()));

        System.out.println("Objet volumineux ? (oui/non) : ");

        switch (this.scanner.next().toLowerCase()) {
            case "oui":
                System.out.print("Hauteur :");
                float hauteur = this.scanner.nextFloat();
                System.out.print("Largeur :");
                float largeur = this.scanner.nextFloat();
                System.out.print("Longueur :");
                float longueur = this.scanner.nextFloat();
                Dimension dimension = new Dimension(hauteur, largeur, longueur);
                nouveauDon.setDimension(dimension);
                break;
            default:
                break;
        }

        System.out.println("Description complémentaires :");
        nouveauDon.setDescriptionComplementaire(this.scanner.next());

        this.lstDonsEnAttente.add(nouveauDon);
    }

    private void gererStockageDon() {

        if (this.activites.getLstDestinations().size() > 0) {

            System.out.println("Quel don traiter ?");

            for (int i = 0; i < this.lstDonsEnAttente.size(); i++) {
                if (this.lstDonsEnAttente.get(i).getStatut() == Statut.ACCEPTE) {
                    System.out.println(String.format("  %d. %s", i, this.lstDonsEnAttente.get(i)));
                }
            }

            Don donATraiter = this.lstDonsEnAttente.get(scanner.nextInt());
            donATraiter.setGestionnaire(this.adherentActuel());

            System.out.println("Où stocker le don ?");

            for (int i = 0; i < this.activites.getLstDestinations().size(); i++) {
                System.out.println(String.format("  %d. %s", i, this.activites.getLstDestinations().get(i)));
            }

            Destination destination = this.activites.getLstDestinations().get(this.scanner.nextInt());

            destination.stockerDon(donATraiter);
            destination.setDateDepot(LocalDate.now());
            donATraiter.setLieuStockage(destination);

            donATraiter.setStatut(Statut.STOCKE);
            this.lstDonsEnAttente.remove(donATraiter);
        } else {
            System.out.println("Aucune destination de stockage n'existe !");
        }
    }

    private void administrerPropositionDon() {
        if (this.lstDonsEnAttente.size() > 0) {
            Don donATraiter = this.choisirDonATraiter();
            donATraiter.setGestionnaire(this.adherentActuel());

            System.out.print("\n\nAccepter le don ? (oui/non) : ");
            String reponse = this.scanner.next();
            switch (reponse) {
                case "oui":
                    donATraiter.setStatut(Statut.ACCEPTE);
                    break;
                case "non":
                    donATraiter.setStatut(Statut.REFUSE);
                    this.activites.getArchive().archiveDon(donATraiter);
                    break;
            }
        } else {
            System.out.println("Il n'existe aucune proposition de don");
        }
    }

    private void transfererDonABeneficiaire() {
        if (this.lstDonsEnAttente.size() > 0) {
        System.out.print("nom du bénéficiare : ");
        Beneficiaire beneficiaire = (Beneficiaire) procedurePersonnes.chercherPersonne(this.scanner.next());
        Adherent gestionnaire = this.adherentActuel();

        Don donATraiter = this.choisirDonATraiter();

        if (donATraiter != null) {

            donATraiter.setBeneficiaire(beneficiaire);
            donATraiter.setGestionnaire(gestionnaire);

            System.out.print("Montant de la vente : ");
            donATraiter.setMontant(this.scanner.nextInt());

            if (donATraiter.getMontant() > 0) {
                donATraiter.setStatut(Statut.VENDU);
            } else {
                donATraiter.setStatut(Statut.DONNE);
            }
            switch (donATraiter.getLieuStockage().getTypeActivite()) {
                case ASSOCIATION:
                    // Aucune action particulière
                    break;
                case DEPOT_VENTE:
                    DepotVente depotVente = (DepotVente) donATraiter.getLieuStockage();
                    depotVente.setDateVente(LocalDate.now());
                    depotVente.setSolde(depotVente.getSolde() + donATraiter.getMontant());
                    break;
                case GARDE_MEUBLE:
                    // Aucune action particulière
                    break;
                case TRANSPORTEUR:
                    // Aucune action particulière
                    break;
            }

            donATraiter.setDateVente(LocalDate.now());

            this.activites.getArchive().archiveDon(donATraiter);
            this.lstDonsEnAttente.remove(donATraiter);
        } else {
            System.err.println("Aucun don n'est enregistré !");
        }
        } else {
            System.out.println("Il n'existe aucun don");
        }
    }

    private Don choisirDonATraiter() {

        System.out.println("Quel don traiter ?");

        for (int i = 0; i < this.lstDonsEnAttente.size(); i++) {
            System.out.println(String.format("  %d. %s", i, this.lstDonsEnAttente.get(i)));
        }

        return this.lstDonsEnAttente.get(this.scanner.nextInt());
    }

    private Adherent adherentActuel() {
        System.out.print("Votre nom : ");
        return (Adherent) procedurePersonnes.chercherPersonne(this.scanner.next());
    }

    private int getLastDonReference() {
        int result = 0;

        for (Don don : lstDonsEnAttente) {
            if (don.getReference() > result) {
                result = don.getReference();
            }
        }

        if (result > 0) {
            result++;
        }

        return result;
    }
}
