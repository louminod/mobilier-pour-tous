package entitiees;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import ressources.Statut;
import ressources.TypeActivite;

public abstract class Destination extends Personne {

	private Map<Integer, Don> mapDons;

	private LocalDate dateDepot;
	private LocalDate dateEnlevement;

	private TypeActivite typeActivite;

	public Destination(int identifiant) {
		super(identifiant);
		this.mapDons = new HashMap<>();
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
		return String.format("Destination: %s | Identifiant: %d", super.getNom(), super.getIdentifiant());
	}

	public LocalDate getDateDepot() {
		return this.dateDepot;
	}

	public void setDateDepot(LocalDate dateDepot) {
		this.dateDepot = dateDepot;
	}

	public LocalDate getDateEnlevement() {
		return this.dateEnlevement;
	}

	public void setDateEnlevement(LocalDate dateEnlevement) {
		this.dateEnlevement = dateEnlevement;
	}

	public TypeActivite getTypeActivite() {
		return this.typeActivite;
	}

	public void setTypeActivite(TypeActivite typeActivite) {
		this.typeActivite = typeActivite;
	}

	public Map<Integer, Don> getMapDons() {
		return this.mapDons;
	}

	public void stockerDon(Don don) {
		if (don.getStatut() == Statut.ACCEPTE) {
			this.mapDons.put(don.getReference(), don);
			don.setStatut(Statut.STOCKE);
		}
	}

}
