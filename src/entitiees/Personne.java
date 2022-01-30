package entitiees;

public abstract class Personne {

	private int identifiant;
	private String nom;
	private String coordonnees;
	private String telephone;

	public Personne(int identifiant) {
		this.identifiant = identifiant;
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
		return String.format("[%d | %s | %s | %s]", this.identifiant, this.nom, this.coordonnees, this.telephone);
	}

	public int getIdentifiant() {
		return this.identifiant;
	}

	public String getNom() {
		return this.nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getCoordonnees() {
		return this.coordonnees;
	}

	public void setCoordonnees(String coordonnees) {
		this.coordonnees = coordonnees;
	}

	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

}
