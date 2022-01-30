package entitiees;

import java.time.LocalDate;

public class Beneficiaire extends Personne {

    private String prenom;
    private LocalDate dateNaissance;
    private Don donRecu;

    public Beneficiaire(int identifiant) {
        super(identifiant);
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

    public String getPrenom() {
        return this.prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public LocalDate getDateNaissance() {
        return this.dateNaissance;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public Don getDonRecu() {
        return this.donRecu;
    }

    public void setDonRecu(Don donRecu) {
        this.donRecu = donRecu;
    }

}
