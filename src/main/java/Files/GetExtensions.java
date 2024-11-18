package Files;

import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;

public class GetExtensions {

	public GetExtensions() {
		File base = new File("e://games");

		File[] fichiers = base.listFiles();

		HashSet<File> listeFichier = new HashSet<File>();
		ArrayList<File> listeDirectory = new ArrayList<File>();
		HashSet<String> listeExtension = new HashSet<>();

		listeDirectory.add(new File("z://test/ultracc/games"));

		for (File fichier : fichiers) {
			if (fichier.isDirectory()) {
				listeDirectory.add(fichier);
			} else {
				listeFichier.add(fichier);
			}
		}

		while (listeDirectory.size() > 0) {
			System.out.println(listeDirectory.size());
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

		for(File fichierTemp : listeFichier){
            listeExtension.add(FilenameUtils.getExtension(fichierTemp.getName()));
		}

		for(String lineTemp : listeExtension){
			System.out.println(lineTemp);
		}
	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		GetExtensions du = new GetExtensions();
	}

}
