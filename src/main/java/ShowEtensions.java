import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Properties;

import org.apache.log4j.Logger;

import entity.NZBEntity;

public class ShowEtensions {
	Properties appProps;
	String rootPath;
	NZBEntity nzbEntity = new NZBEntity();
	Connection conn;
	FileWriter fw;
	PrintWriter pw;

	final static Logger logger = Logger.getLogger(ShowEtensions.class);

	public ShowEtensions() {
		File base = new File("x://");

		File[] fichiers = base.listFiles();

		HashSet<File> listeFichier = new HashSet<File>();
		ArrayList<File> listeDirectory = new ArrayList<File>();

		for (File fichier : fichiers) {
			if (fichier.isDirectory()) {
				listeDirectory.add(fichier);
			} else {
				listeFichier.add(fichier);
			}
		}

		while (listeDirectory.size() > 0) {
			File fichier = listeDirectory.get(0);

			File[] fichierListe = fichier.listFiles();

			for (File fichierTemp : fichierListe) {
				if (fichierTemp.isDirectory()) {
					listeDirectory.add(fichierTemp);
				} else {
					listeFichier.add(fichierTemp);
				}
			}
			listeDirectory.remove(0);
		}

		HashSet<String> set = new HashSet<String>();

		for (File fichierTemp : listeFichier) {
			String extension = fichierTemp.getName().substring(fichierTemp.getName().lastIndexOf('.') + 1,
					fichierTemp.getName().length());
			set.add(extension);
		}
		for (String ext : set) {
			System.out.println(ext);
		}
	}

	public static void main(String[] args) {
		ShowEtensions populate = new ShowEtensions();

	}

}
