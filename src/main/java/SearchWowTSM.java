import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;

import org.apache.commons.io.FileUtils;

public class SearchWowTSM {

	public SearchWowTSM() {
		File base = new File("z://tradeskillmaster");

		File[] fichiers = base.listFiles();

		HashSet<File> listeFichier = new HashSet<File>();
		ArrayList<File> listeDirectory = new ArrayList<File>();
		// listeDirectory.add(new File("f://series"));
		// listeDirectory.add(new File("f://temp 2/main"));

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

		System.out.println(("Fin parcours"));
		int compteur = 0;
		for (File fichier : listeFichier) {
			System.out.println(compteur + "/" + listeFichier.size());
			compteur++;
			try {
				FileReader fr = new FileReader(fichier);
				BufferedReader br = new BufferedReader(fr);

				String line = "";
				while (line != null) {
					line = br.readLine();
					if (line != null && line.contains(("<li class=\"active\">"))) {
						if (line.toLowerCase().contains("8.2")
								&& (line.toLowerCase().contains("bs") || line.toLowerCase().contains("blacksmith"))) {
							FileUtils.copyFile(fichier,
									new File("z://tradeskillmaster/result/" + fichier.getName() + ".html"));
							br.close();
							fr.close();
							break;
						}
					}
				}

				br.close();
				fr.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}

		}
	}

	public static void main(String[] args) {
		SearchWowTSM t = new SearchWowTSM();
	}

}
