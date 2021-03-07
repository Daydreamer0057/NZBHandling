package entity;

import java.io.File;

public class Duplicate {
	String name;
	long taille;
	File fichier;

	public Duplicate(String name, long taille, File fichier) {
		this.name = name;
		this.taille = taille;
		this.fichier = fichier;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getTaille() {
		return taille;
	}

	public void setTaille(long taille) {
		this.taille = taille;
	}

	public File getFichier() {
		return fichier;
	}

	public void setFichier(File fichier) {
		this.fichier = fichier;
	}

}
