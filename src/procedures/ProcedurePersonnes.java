package procedures;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import entitiees.Adherent;
import entitiees.Beneficiaire;
import entitiees.Personne;
import ressources.Fonction;
import tools.Menu;

public class ProcedurePersonnes implements Procedure {

    private final String PATH_ADHERENTS = "src/files/Adherents.txt";
    private final String PATH_BENEFICIAIRES = "src/files/Beneficiaires.txt";

    private List<Personne> lstPersonnes;
    private Scanner scanner;
    private Menu menu;

    public ProcedurePersonnes(Menu menu, Scanner scanner) {
        this.scanner = scanner;
        this.menu = menu;
        this.chargerFichiers();
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

    public List<Personne> getLstPersonnes() {
        return this.lstPersonnes;
    }

    @Override
    public void lancerProcedure() {
        boolean fini = false;

        while (!fini) {
            this.menu.afficherMenuPersonnes();

            switch (this.scanner.next()) {
                case "1": // Recherche sur les bénéficiaires
                    this.rechercheSurBeneficiaire();
                    fini = true;
                    break;
                case "2": // Modification/Suppression des personnes
                    this.modificationSuppressionPersonne();
                    fini = true;
                    break;
                case "3": // Recherche sur les personnes autres que bénéficiaires
                    this.rechercheAutre();
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

    private void rechercheSurBeneficiaire() {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Vous souhaitez effectuer une recherche par :\n");
        stringBuilder.append("  a. Par téléphone\n");
        stringBuilder.append("  b. Par nom\n\n");

        Beneficiaire beneficiaire = null;

        System.out.println(stringBuilder.toString());
        String choix = this.scanner.next();

        if (choix.equals("a")) {

            System.out.print("numéro : ");

            String numero = this.scanner.next();

            for (Personne pers : this.lstPersonnes) {
                if (pers.getClass() == Beneficiaire.class) {
                    Beneficiaire benef = (Beneficiaire) pers;

                    if (benef.getTelephone().equals(numero)) {
                        beneficiaire = benef;
                    }
                }

            }
        } else if (choix.equals("b")) {

            System.out.print("nom : ");

            String nom = this.scanner.next();

            for (Personne pers : this.lstPersonnes) {
                if (pers.getClass() == Beneficiaire.class) {
                    Beneficiaire benef = (Beneficiaire) pers;

                    if (benef.getNom().toString().equals(nom.toString())) {
                        beneficiaire = benef;
                    }
                }

            }
        } else {
            System.out.println("Le choix sélectionné ne fait pas partit de ceux proposés");
        }

        if (beneficiaire != null) {
            System.out.println(beneficiaire);
        } else {
            System.out.println("Le bénéficiaire n'a pas été trouvé");
        }
    }

    private void modificationSuppressionPersonne() {

        int index = 0;
        for (Personne pers : this.lstPersonnes) {
            String presentation = pers.toString().substring(5);
            System.out.println(String.format("%d) %s", index, presentation));
            index++;
        }

        System.out.print("Qui voulez vous ? -> ");

        int numero = this.scanner.nextInt();

        Personne personne = this.lstPersonnes.get(numero);

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format("\nQue faire avec %s ?\n", personne));
        stringBuilder.append("  1. Modifier\n");
        stringBuilder.append("  2. Supprimer\n");
        stringBuilder.append("  3. Rien\n\n");
        System.out.println(stringBuilder.toString());

        int choix = 0;

        try {
            choix = this.scanner.nextInt();
        } catch (Exception exception) {
            System.err.println("Le choix doit étre un entier");
        }

        if (choix == 1) {
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("Que voulez vous modifier ?\n");
            stringBuilder2.append("1) nom\n");
            stringBuilder2.append("2) coordonnées\n");
            stringBuilder2.append("3) téléphone\n");
            stringBuilder2.append("4) retour\n");

            System.out.println(stringBuilder2.toString());

            try {
                choix = this.scanner.nextInt();
            } catch (Exception exception) {
                System.err.println("Le choix doit étre un entier");
            }

            switch (choix) {
                case 1:
                    System.out.print(String.format("[%s] -> ", personne.getNom()));
                    personne.setNom(this.scanner.next());
                    break;
                case 2:
                    System.out.print(String.format("[%s] -> ", personne.getCoordonnees()));
                    personne.setCoordonnees(this.scanner.next());
                    break;
                case 3:
                    System.out.print(String.format("[%s] -> ", personne.getTelephone()));
                    personne.setTelephone(this.scanner.next());
                    break;
                default:
                    break;
            }

        } else if (choix == 2) {
            this.lstPersonnes.remove(numero);
            System.out.println(String.format("-> M/Mme %s supprimé !", personne.getNom()));
        }

        if (choix != 3) {
            try {
                this.modifierFichiers();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    private void rechercheAutre() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Vous souhaitez effectuer une recherche par :\n");
        stringBuilder.append("  a. Par téléphone\n");
        stringBuilder.append("  b. Par nom\n\n");

        System.out.println(stringBuilder.toString());

        List<Personne> lstAutres = new ArrayList<>();

        for (Personne personne : this.lstPersonnes) {
            if (personne.getClass() != Beneficiaire.class) {
                lstAutres.add(personne);
            }
        }

        boolean personneTrouvee = false;

        switch (this.scanner.next()) {
            case "a":
                System.out.print("Téléphone à chercher : ");
                String telephone = this.scanner.next();
                for (Personne personne : lstAutres) {
                    if (personne.getTelephone().equals(telephone)) {
                        System.out.println(personne);
                        personneTrouvee = true;
                    }
                }
                break;
            case "b":
                System.out.print("Nom à chercher : ");
                String nom = this.scanner.next();
                for (Personne personne : lstAutres) {
                    if (personne.getNom().toLowerCase().equals(nom.toLowerCase())) {
                        System.out.println(personne);
                        personneTrouvee = true;
                    }
                }
                break;
            default:
                break;
        }

        if (!personneTrouvee) {
            System.out.println("La personne n'a pas été trouvée");
        }
    }

    public Personne chercherPersonne(String nom) {
        Personne personne = null;

        for (Personne p : lstPersonnes) {
            if (p.getNom().toLowerCase().equals(nom.toLowerCase())) {
                personne = p;
            }
        }

        return personne;
    }

    private void chargerFichiers() {
        this.lstPersonnes = new ArrayList<>();

        File fileAdherent = new File(this.PATH_ADHERENTS);

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(fileAdherent));

            String line;

            while ((line = bufferedReader.readLine()) != null) {
                String tabDataLine[] = line.split(";");

                if (!tabDataLine[0].equals("")) {

                    Adherent adherent = new Adherent(Integer.valueOf(tabDataLine[0]));
                    adherent.setNom(tabDataLine[1]);
                    adherent.setCoordonnees(tabDataLine[2]);
                    adherent.setTelephone(tabDataLine[3]);
                    adherent.setPrenom(tabDataLine[4]);

                    for (Fonction fonction : Fonction.values()) {
                        if (tabDataLine[5].toUpperCase().equals(fonction.toString())) {
                            adherent.setFonction(fonction);
                        }
                    }
                    this.lstPersonnes.add(adherent);
                }
            }

            bufferedReader.close();

        } catch (Exception exception) {
            exception.printStackTrace();
        }

        File fileBeneficiaires = new File(this.PATH_BENEFICIAIRES);

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(fileBeneficiaires));

            String line;

            while ((line = bufferedReader.readLine()) != null) {
                String tabDataLine[] = line.split(";");

                if (!tabDataLine[0].equals("")) {

                    Beneficiaire beneficiaire = new Beneficiaire(Integer.valueOf(tabDataLine[0]));
                    beneficiaire.setNom(tabDataLine[1]);
                    beneficiaire.setCoordonnees(tabDataLine[2]);
                    beneficiaire.setTelephone(tabDataLine[3]);
                    beneficiaire.setPrenom(tabDataLine[4]);

                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    beneficiaire.setDateNaissance(LocalDate.parse(tabDataLine[5], formatter));

                    this.lstPersonnes.add(beneficiaire);
                }
            }

            bufferedReader.close();

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void modifierFichiers() throws Exception {
        File fileAdherents = new File(this.PATH_ADHERENTS);
        File fileBeneficiares = new File(this.PATH_BENEFICIAIRES);

        PrintWriter pwAdherents = new PrintWriter(new FileWriter(fileAdherents));
        PrintWriter pwBeneficiaires = new PrintWriter(new FileWriter(fileBeneficiares));

        for (Personne personne : this.lstPersonnes) {
            if (personne.getClass() == Adherent.class) {
                Adherent adherent = (Adherent) personne;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(String.format("%d;", adherent.getIdentifiant()));
                stringBuilder.append(String.format("%s;", adherent.getNom()));
                stringBuilder.append(String.format("%s;", adherent.getCoordonnees()));
                stringBuilder.append(String.format("%s;", adherent.getTelephone()));
                stringBuilder.append(String.format("%s;", adherent.getPrenom()));
                stringBuilder.append(String.format("%s;\n", adherent.getFonction().toString().toLowerCase()));
                pwAdherents.write(stringBuilder.toString());
            } else if (personne.getClass() == Beneficiaire.class) {
                Beneficiaire beneficiaire = (Beneficiaire) personne;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(String.format("%d;", beneficiaire.getIdentifiant()));
                stringBuilder.append(String.format("%s;", beneficiaire.getNom()));
                stringBuilder.append(String.format("%s;", beneficiaire.getCoordonnees()));
                stringBuilder.append(String.format("%s;", beneficiaire.getTelephone()));
                stringBuilder.append(String.format("%s;", beneficiaire.getPrenom()));

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                stringBuilder.append(String.format("%s;\n", beneficiaire.getDateNaissance().format(formatter)));
                pwBeneficiaires.write(stringBuilder.toString());
            }
        }

        pwAdherents.close();
        pwBeneficiaires.close();
    }
}