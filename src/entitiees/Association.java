package entitiees;

import java.util.HashMap;
import java.util.Map;

public class Association extends Destination {

	private Map<Integer, Personne> mapPersonnes;

	public Association(int identifiant) {
		super(identifiant);
		this.mapPersonnes = new HashMap<>();
	}

	public Map<Integer, Personne> getMapPersonnes() {
		return this.mapPersonnes;
	}

	public void addPersonne(Personne adherent) {
		this.mapPersonnes.put(adherent.getIdentifiant(), adherent);
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
		return String.format("Association: %s | Identifiant: %d", super.getNom(), super.getIdentifiant());
	}
}
