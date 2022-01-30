package tools;

import java.util.Scanner;

public class Menu {

    public Menu() {

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

    public void afficherMenuAccueil() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Que désirez vous faire :\n");
        stringBuilder.append("  1. Gérer les personnes\n");
        stringBuilder.append("  2. Gérer les dons\n");
        stringBuilder.append("  3. Faire une recherche\n");
        stringBuilder.append("  4. Consulter les statistiques\n");
        stringBuilder.append("  5. Gérer les destinations\n");
        stringBuilder.append("  6. Gérer les associations\n");
        stringBuilder.append("  7. Quitter le logiciel\n");

        System.out.println("\n############################\n");
        System.out.println("#### MOBILIER POUR TOUS ####\n");
        System.out.println(stringBuilder.toString());
    }

    public void afficherMenuPersonnes() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Que désirez vous faire :\n");
        stringBuilder.append("  1. Recherche sur les bénéficiaires\n");
        stringBuilder.append("  2. Modification/Suppression des personnes\n");
        stringBuilder.append("  3. Rechercher une personne non bénéficiaire\n");
        stringBuilder.append("  4. Retour au menu principal\n");

        System.out.println("\n###############################\n");
        System.out.println("---- GESTION DES PERSONNES ----\n");
        System.out.println(stringBuilder.toString());
    }

    public void afficherMenuDons() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Que désirez vous faire :\n");
        stringBuilder.append("  1. Création d'un don\n");
        stringBuilder.append("  2. Administration des proposotitions de dons\n");
        stringBuilder.append("  3. Stockage d'un don\n");
        stringBuilder.append("  4. Transfert du don au beneficiaire\n");
        stringBuilder.append("  5. Retour au menu principal\n");

        System.out.println("\n############################\n");
        System.out.println("---- GESTION DES DONS ----\n");
        System.out.println(stringBuilder.toString());
    }

    public void afficherMenuRecherches() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Que désirez vous faire :\n");
        stringBuilder.append("  1. Rechercher les dons refusés\n");
        stringBuilder.append("  2. Rechercher les dons en traitement (accepté ou stocké)\n");
        stringBuilder.append("  3. Rechercher les dons vendus\n");
        stringBuilder.append("  4. Rechercher les dons donnés\n");
        stringBuilder.append("  5. Rechercher les dons stockés par entrepét\n");
        stringBuilder.append("  6. Rechercher les dons par dépét-vente\n");

        System.out.println("\n#########################\n");
        System.out.println("---- RECHERCHES ----\n");
        System.out.println(stringBuilder.toString());
    }

    public void afficherMenuDestination() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Que désirez vous faire :\n");
        stringBuilder.append("  1. Créer destination\n");
        stringBuilder.append("  2. Supprimer destination\n");
        stringBuilder.append("  3. Lister destinations\n");
        stringBuilder.append("  4. Retour au menu principal\n");

        System.out.println("\n#########################\n");
        System.out.println("---- DESTINATION ----\n");
        System.out.println(stringBuilder.toString());
    }

    public void afficherMenuAssociation() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Que désirez vous faire :\n");
        stringBuilder.append("  1. Ajouter personne à une association\n");
        stringBuilder.append("  2. Retour au menu principal\n");

        System.out.println("\n#########################\n");
        System.out.println("---- ASSOCIATION ----\n");
        System.out.println(stringBuilder.toString());
    }
}
