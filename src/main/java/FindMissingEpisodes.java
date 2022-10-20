import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;

public class FindMissingEpisodes {

	public FindMissingEpisodes() {
		String pathPrincipal = "z://series";
		File base = new File(pathPrincipal);

		File[] fichiers = base.listFiles();

		HashSet<File> listeFichier = new HashSet<File>();
		ArrayList<File> listeDirectory = new ArrayList<File>();

		for (File fichier : fichiers) {
			System.out.println("Files " + listeFichier.size() + "    Directory " + listeDirectory.size());
			if (fichier.isDirectory()) {
				listeDirectory.add(fichier);
			} else {
				if (fichier.getName().endsWith(".mkv") || fichier.getName().endsWith(".mp4")
						|| fichier.getName().endsWith(".avi")) {
					listeFichier.add(fichier);
				}
			}
		}

		while (listeDirectory.size() > 0) {
			System.out.println("Files " + listeFichier.size() + "    Directory " + listeDirectory.size());

			File fichier = listeDirectory.get(0);

			File[] fichierListe = fichier.listFiles();

			if (fichierListe != null) {
				for (File fichierTemp : fichierListe) {
					if (fichierTemp.isDirectory()) {
						listeDirectory.add(fichierTemp);
					} else {
						if (fichierTemp.getName().endsWith(".mkv") || fichierTemp.getName().endsWith(".mp4")
								|| fichierTemp.getName().endsWith(".avi")) {
							listeFichier.add(fichierTemp);
						}
					}
				}
			}
			listeDirectory.remove(0);
		}

		for(File fichierTemp : listeFichier){
			for(int season = 1;season>10;season++){

			}
		}

			}

	public static void main(String[] args) {
		FindMissingEpisodes t = new FindMissingEpisodes();
	}

}
