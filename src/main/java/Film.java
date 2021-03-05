
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;

public class Film {
	static String path2;

	public Film() {
		// Dossier a supprimer
		 File base = new File("z://test/film france");
		//File base = new File("Z://test/film2");
//		File base = new File("z://documentaires/Deadliest Catch");
		// File base = new File("f://Graver/Theatre/Convert");

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

		int compteur = 0;
		File fichier = new File("z://temp/convert");
		//File fichier = new File("f://convert");
		File[] files = fichier.listFiles();

		for (File fichierTemp : files) {
			String nomFichier = "";
			if ((fichierTemp.getName().indexOf("_")>fichierTemp.getName().length()-6)&&(
					fichierTemp.getName().indexOf("_0") != -1 || fichierTemp.getName().indexOf("_1") != -1
					|| fichierTemp.getName().indexOf("_2") != -1 || fichierTemp.getName().indexOf("_3") != -1
					|| fichierTemp.getName().indexOf("_4") != -1 || fichierTemp.getName().indexOf("_5") != -1
					|| fichierTemp.getName().indexOf("_6") != -1 || fichierTemp.getName().indexOf("_7") != -1
					|| fichierTemp.getName().indexOf("_8") != -1 || fichierTemp.getName().indexOf("_9") != -1)) {
				nomFichier = fichierTemp.getName().substring(0, fichierTemp.getName().length() - 6);
				path2 = nomFichier.replaceAll("_", "'");
			} else {
				// System.out.println("Compteur " + compteur + " / " + files.length);
				compteur++;
				final String path = fichierTemp.getName();
				path2 = path.replaceAll("_", "'");
				path2 = path2.substring(0, path2.length() - 4);
			}

			listeFichier.forEach(res -> {
				String nameRes = res.getName();
				if(nameRes.contains("Naples")&&path2.contains("Naples")){
					System.out.println("");
				}
				if (nameRes.indexOf("_0") != -1 || nameRes.indexOf("_1") != -1 || nameRes.indexOf("_2") != -1
						|| nameRes.indexOf("_3") != -1 || nameRes.indexOf("_4") != -1 || nameRes.indexOf("_5") != -1
						|| nameRes.indexOf("_6") != -1 || nameRes.indexOf("_7") != -1 || nameRes.indexOf("_8") != -1
						|| nameRes.indexOf("_9") != -1) {
					nameRes = nameRes.substring(0, nameRes.length() - 6);
				} else {
					nameRes = nameRes.substring(0, nameRes.length() - 4);
				}
				if (nameRes.equalsIgnoreCase(path2)) {
					res.delete();
					System.out.println(res.getPath() + "    " + listeFichier.size());
				}
			});
		}
	}

	public static void main(String[] args) {
		Film epguides = new Film();

	}

}
