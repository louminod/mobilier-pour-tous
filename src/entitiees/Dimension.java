package entitiees;

public class Dimension {

	private float hauteur;
	private float largeur;
	private float longueur;

	public Dimension(float hauteur, float largeur, float longueur) {
		this.hauteur = hauteur;
		this.largeur = largeur;
		this.longueur = longueur;
	}

	public float getHauteur() {
		return this.hauteur;
	}

	public float getLargeur() {
		return this.largeur;
	}

	public float getLongueur() {
		return this.longueur;
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
}
