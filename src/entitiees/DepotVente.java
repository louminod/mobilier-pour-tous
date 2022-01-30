package entitiees;

import java.time.LocalDate;

public class DepotVente extends Destination {

	private LocalDate dateVente;
	private float solde;

	public DepotVente(int identifiant) {
		super(identifiant);
		this.solde = 1000;
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
		return String.format("Dépot-Vente: %s | Identifiant: %d | solde: %f", super.getNom(), super.getIdentifiant(), this.getSolde());
	}

	public LocalDate getDateVente() {
		return this.dateVente;
	}

	public void setDateVente(LocalDate dateVente) {
		this.dateVente = dateVente;
	}

	public float getSolde() {
		return this.solde;
	}

	public void setSolde(float solde) {
		this.solde = solde;
	}
}
