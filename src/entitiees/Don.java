package entitiees;

import java.time.LocalDate;

import ressources.Statut;
import ressources.TypeMateriel;

public class Don {

	private int reference;
	private LocalDate dateReception;
	private LocalDate dateVente;
	private String descriptionComplementaire;
	private float montant;

	private TypeMateriel typeMateriel;
	private Personne donateur;
	private Beneficiaire beneficiaire;
	private Adherent gestionnaire;
	private Statut statut;
	private Destination lieuStockage;
	private Dimension dimension;

	public Don(int reference) {
		this.reference = reference;
		this.montant = 0;
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
		return String.format("Référence: %d | Type de matériel: %s | Description: %s | montant: %f", this.reference,
				this.typeMateriel, this.descriptionComplementaire, this.montant);
	}

	public LocalDate getDateReception() {
		return this.dateReception;
	}

	public void setDateReception(LocalDate dateReception) {
		this.dateReception = dateReception;
	}

	public String getDescriptionComplementaire() {
		return this.descriptionComplementaire;
	}

	public void setDescriptionComplementaire(String descriptionComplementaire) {
		this.descriptionComplementaire = descriptionComplementaire;
	}

	public float getMontant() {
		return this.montant;
	}

	public void setMontant(float montant) {
		this.montant = montant;
	}

	public TypeMateriel getTypeMateriel() {
		return this.typeMateriel;
	}

	public void setTypeMateriel(TypeMateriel typeMateriel) {
		this.typeMateriel = typeMateriel;
	}

	public Personne getDonateur() {
		return this.donateur;
	}

	public void setDonateur(Personne donateur) {
		this.donateur = donateur;
	}

	public Beneficiaire getBeneficiaire() {
		return this.beneficiaire;
	}

	public void setBeneficiaire(Beneficiaire beneficiaire) {
		this.beneficiaire = beneficiaire;
	}

	public Adherent getGestionnaire() {
		return this.gestionnaire;
	}

	public void setGestionnaire(Adherent gestionnaire) {
		this.gestionnaire = gestionnaire;
	}

	public Statut getStatut() {
		return this.statut;
	}

	public void setStatut(Statut statut) {
		this.statut = statut;
	}

	public Destination getLieuStockage() {
		return this.lieuStockage;
	}

	public void setLieuStockage(Destination lieuStockage) {
		this.lieuStockage = lieuStockage;
	}

	public Dimension getDimension() {
		return this.dimension;
	}

	public void setDimension(Dimension dimension) {
		this.dimension = dimension;
	}

	public int getReference() {
		return this.reference;
	}

	public LocalDate getDateVente() {
		return dateVente;
	}

	public void setDateVente(LocalDate dateVente) {
		this.dateVente = dateVente;
	}
}
