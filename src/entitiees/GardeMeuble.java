package entitiees;

public class GardeMeuble extends Destination {

	public GardeMeuble(int identifiant) {
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
		return String.format("Garde meubles: %s | Identifiant: %d", super.getNom(), super.getIdentifiant());
	}
}
