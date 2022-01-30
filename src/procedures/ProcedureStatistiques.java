package procedures;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import entitiees.Adherent;
import entitiees.Beneficiaire;
import entitiees.Destination;
import entitiees.Don;
import entitiees.Personne;
import ressources.Statut;
import ressources.TypeActivite;
import ressources.TypeMateriel;
import tools.Activites;
import tools.Menu;

public class ProcedureStatistiques implements Procedure {

    private Activites activites;
    private ProcedureDons procedureDons;
    private ProcedurePersonnes procedurePersonnes;

    private StringBuilder stringBuilderStatistiques;
    private List<Don> lstTotaleDons;
    private Scanner scanner;
    private Menu menu;

    public ProcedureStatistiques(Menu menu, Scanner scanner, Activites activites,
                                 ProcedureDons procedureDons, ProcedurePersonnes procedurePersonnes) {
        this.scanner = scanner;
        this.menu = menu;
        this.activites = activites;
        this.procedureDons = procedureDons;
        this.procedurePersonnes = procedurePersonnes;

        this.lstTotaleDons = new ArrayList<>();

        this.updateLstDons();

        this.stringBuilderStatistiques = new StringBuilder();
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
        System.out.println("\n-- STATISTIQUES --");
        this.afficherStatistiqueNbPropositionDonsRecues();
        this.afficherStatistiqueNbDonnateursEtBeneficiaires();
        this.afficherStatistiqueNbPropositionsDonsAcceptees();
        this.afficherStatistiqueRatioRecuesAccepteesVolumineux();
        this.afficherStatistiqueVentilationVentesParDestination();
        this.afficherStatistiquePrincipaleCategorieArticles();
        this.afficherStatistiqueMoyenneDeTempsReceptionRetrait();
        this.afficherStatistiqueMoyennePrixDepotVente();
        this.afficherStatistiqueMoyenneAgesBeneficiaires();
        this.sauvegarderStatistique();
    }

    public void updateLstDons() {
        for (Don don : this.procedureDons.getLstDonsEnAttente()) {
            if (!this.lstTotaleDons.contains(don)) {
                lstTotaleDons.add(don);
            }
        }

        for (Don don : this.activites.getArchive().getLstDonsArchives()) {
            if (!this.lstTotaleDons.contains(don)) {
                lstTotaleDons.add(don);
            }
        }

        for (Destination destination : this.activites.getLstDestinations()) {
            for (Don don : destination.getMapDons().values()) {
                if (!this.lstTotaleDons.contains(don)) {
                    lstTotaleDons.add(don);
                }
            }
        }
    }

    private void afficherStatistiqueNbPropositionDonsRecues() {
        String result = String.format("|-> Nombre de proposition de dons reçues : %d\n", this.lstTotaleDons.size());
        this.stringBuilderStatistiques.append(result);
        System.out.println(result);
    }

    private void afficherStatistiqueNbDonnateursEtBeneficiaires() {
        int nbDonnateurs = 0;
        int nbBeneficiaires = 0;

        for (Personne personne : this.procedurePersonnes.getLstPersonnes()) {
            if (personne.getClass() == Adherent.class) {
                nbDonnateurs++;
            }
            if (personne.getClass() == Beneficiaire.class) {
                nbBeneficiaires++;
            }
        }

        StringBuilder result = new StringBuilder();
        result.append(String.format("|-> Nombre total de |donnateurs : %d\n", nbDonnateurs));
        result.append(String.format("                    |bénneficiares : %d\n", nbBeneficiaires));
        this.stringBuilderStatistiques.append(result.toString());
        System.out.println(result.toString());
    }

    private void afficherStatistiqueNbPropositionsDonsAcceptees() {
        int nb = 0;

        for (Don don : lstTotaleDons) {
            if (don.getStatut() != Statut.REFUSE) {
                nb++;
            }
        }

        String result = String.format("|-> Nombre de proposition de dons acceptées : %d\n", nb);
        this.stringBuilderStatistiques.append(result);
        System.out.println(result);
    }

    private void afficherStatistiqueRatioRecuesAccepteesVolumineux() {
        int nbRefusees = 0;

        for (Don don : lstTotaleDons) {
            if (don.getStatut() == Statut.REFUSE && don.getDimension() != null) {
                nbRefusees++;
            }
        }

        int nbAcceptes = 0;

        for (Don don : lstTotaleDons) {
            if (don.getStatut() == Statut.ACCEPTE && don.getDimension() != null) {
                nbAcceptes++;
            }
        }

        float ratio = nbAcceptes > 0 ? (nbAcceptes + nbRefusees) / nbAcceptes : 0;

        String result = String.format("|-> Ratio don volumineux reçus/acceptés: %f\n", ratio);
        this.stringBuilderStatistiques.append(result);
        System.out.println(result);
    }

    private void afficherStatistiqueVentilationVentesParDestination() {
        StringBuilder result = new StringBuilder();
        result.append("|-> Ventilation ventes par destinations possibles:\n");

        for (TypeActivite typeActivite : TypeActivite.values()) {
            int nbVentes = 0;
            for (Don don : lstTotaleDons) {
                if (don.getLieuStockage() != null) {
                    if (don.getLieuStockage().getTypeActivite() == typeActivite && don.getStatut() == Statut.VENDU) {
                        nbVentes++;
                    }
                }
            }
            result.append(String.format("     |%s: %d\n", typeActivite.toString(), nbVentes));
        }

        this.stringBuilderStatistiques.append(result.toString());
        System.out.println(result.toString());
    }

    // TODO gerer problème
    private void afficherStatistiquePrincipaleCategorieArticles() {
        Map<TypeMateriel, Integer> mapNbArticlesParCategorie = new HashMap<>();

        for (TypeMateriel typeMateriel : TypeMateriel.values()) {
            int nb = 0;
            for (Don don : lstTotaleDons) {
                if (don.getStatut() == Statut.STOCKE && don.getTypeMateriel() == typeMateriel) {
                    nb++;
                }
            }
            mapNbArticlesParCategorie.put(typeMateriel, nb);
        }

        StringBuilder result = new StringBuilder();
        result.append("|-> Nombres d'articles par catégories en stock:\n");

        for (TypeMateriel typeMateriel : mapNbArticlesParCategorie.keySet()) {
            result.append(String.format("     |%s: %d\n", typeMateriel, mapNbArticlesParCategorie.get(typeMateriel)));
        }

        this.stringBuilderStatistiques.append(result.toString());
        System.out.println(result.toString());
    }

    private void afficherStatistiqueMoyenneDeTempsReceptionRetrait() {
        float moyenne = 0;
        int nbDestinations = 0;
        int sommeMoyennesDestinations = 0;

        for (Destination destination : this.activites.getLstDestinations()) {
            int sommeDureesDestination = 0;
            int nbDurees = 0;

            for (Integer reference : destination.getMapDons().keySet()) {
                Don don = destination.getMapDons().get(reference);
                // TODO gestion des dates nulles
                Period period = Period.between(don.getDateReception(), don.getDateVente());
                sommeDureesDestination += Integer.valueOf(period.getDays());
                nbDurees += 1;
            }

            if (nbDurees > 0) {
                sommeMoyennesDestinations += sommeDureesDestination / nbDurees;
                nbDestinations += 1;
            }
        }

        if (nbDestinations > 0) {
            moyenne = sommeMoyennesDestinations / nbDestinations;
        }

        String result = String.format("|-> Moyenne de jours entre la réception et le retrait de dons: %f\n", moyenne);
        this.stringBuilderStatistiques.append(result);
        System.out.println(result);
    }

    private void afficherStatistiqueMoyennePrixDepotVente() {
        float moyenne = 0;
        int nbDepotsVentes = 0;
        int sommeMoyennesDepotsVentes = 0;

        for (Destination destination : this.activites.getLstDestinations()) {
            if (destination.getTypeActivite() == TypeActivite.DEPOT_VENTE) {
                int sommeMontantDepotVente = 0;
                int nbDons = 0;

                for (Integer reference : destination.getMapDons().keySet()) {
                    sommeMontantDepotVente += destination.getMapDons().get(reference).getMontant();
                    nbDons += 1;
                }

                if (nbDons > 0) {
                    sommeMoyennesDepotsVentes += sommeMontantDepotVente / nbDons;
                    nbDepotsVentes += 1;
                }
            }
        }

        if (nbDepotsVentes > 0) {
            moyenne = sommeMoyennesDepotsVentes / nbDepotsVentes;
        }

        String result = String.format("|-> Moyenne du montant des produits des dépots-ventes: %f €\n", moyenne);
        this.stringBuilderStatistiques.append(result);
        System.out.println(result);
    }

    private void afficherStatistiqueMoyenneAgesBeneficiaires() {
        int nbBeneficiaires = 0;
        int sommeAges = 0;
        int moyenneAge = 0;

        for (Personne personne : this.procedurePersonnes.getLstPersonnes()) {
            if (personne.getClass() == Beneficiaire.class) {
                nbBeneficiaires++;
                sommeAges += this.calculerAge(((Beneficiaire) personne).getDateNaissance());
            }
        }

        if (nbBeneficiaires > 0) {
            moyenneAge = sommeAges / nbBeneficiaires;
        }

        String result = String.format("|-> Moyenne d'âge des bénéficiaires: %d ans\n", moyenneAge);
        this.stringBuilderStatistiques.append(result);
        System.out.println(result);
    }

    private int calculerAge(LocalDate dateNaissance) {
        return Period.between(dateNaissance, LocalDate.now()).getYears();
    }

    private void sauvegarderStatistique() {
        BufferedWriter writer = null;
        try {
            LocalDate date = LocalDate.now();
            File file = new File(System.getProperty("user.home") + "/exportStatistiques_" + date + ".txt");

            writer = new BufferedWriter(new FileWriter(file));

            writer.write(this.stringBuilderStatistiques.toString());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                writer.close();
                System.out.println("Statistiques sauvegardées dans : " + System.getProperty("user.home"));
            } catch (Exception e) {
            }
        }
    }
}
