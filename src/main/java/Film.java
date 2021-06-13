
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;

public class Film {
	static String path2;

	public Film() {
		// Dossier a supprimer
		File base = new File("z://film/new/convert");
		//File base = new File("Z://film/new");
		//File base = new File("e://theatre/convert");
		// File base = new File("f://Graver/Theatre/Convert");

		File[] fichiers = base.listFiles();

		HashSet<File> listeFichier = new HashSet<File>();
		ArrayList<File> listeDirectory = new ArrayList<File>();

		for (File fichier : fichiers) {
			if (fichier.isDirectory()) {
				listeDirectory.add(fichier);
			} else {
				if(!fichier.getName().endsWith(".srt")) {
					listeFichier.add(fichier);
				}
			}
		}

		while (listeDirectory.size() > 0) {
			File fichier = listeDirectory.get(0);
			File[] fichierListe = fichier.listFiles();

			for (File fichierTemp : fichierListe) {
				if (fichierTemp.isDirectory()) {
					listeDirectory.add(fichierTemp);
				} else {
					if(!fichier.getName().endsWith(".srt")) {
						listeFichier.add(fichierTemp);
					}
				}
			}
			listeDirectory.remove(0);
		}

		int compteur = 0;
		HashSet<File> fileBase = new HashSet<File>();
		HashSet<File> files = new HashSet<File>();
		fileBase.add(new File("e://convert"));
		fileBase.add(new File("z://temp/convert"));
		ArrayList<File> listeDirectory2 = new ArrayList<File>();

		for (File filesTemp : fileBase) {
			if (filesTemp.isDirectory()) {
				listeDirectory2.add(filesTemp);
			} else {
				files.add(filesTemp);
			}
		}

		while (listeDirectory2.size() > 0) {
			File fichier2 = listeDirectory2.get(0);
			File[] fichierListe2 = fichier2.listFiles();

			for (File fichierTemp : fichierListe2) {
				if (fichierTemp.isDirectory()) {
					listeDirectory2.add(fichierTemp);
				} else {
					files.add(fichierTemp);
				}
			}
			listeDirectory2.remove(0);
		}


		for (File fichierTemp : files) {
			String nomFichier = "";
			if ((fichierTemp.getName().indexOf("_")>fichierTemp.getName().length()-6)&&(
					fichierTemp.getName().indexOf("_0") != -1 || fichierTemp.getName().indexOf("_1") != -1
							|| fichierTemp.getName().indexOf("_2") != -1 || fichierTemp.getName().indexOf("_3") != -1
							|| fichierTemp.getName().indexOf("_4") != -1 || fichierTemp.getName().indexOf("_5") != -1
							|| fichierTemp.getName().indexOf("_6") != -1 || fichierTemp.getName().indexOf("_7") != -1
							|| fichierTemp.getName().indexOf("_8") != -1 || fichierTemp.getName().indexOf("_9") != -1)) {
				nomFichier = fichierTemp.getName().substring(0, fichierTemp.getName().length() - 6);
				//path2 = nomFichier.replaceAll("_", "'");
			} else {
				// System.out.println("Compteur " + compteur + " / " + files.length);
				compteur++;
				final String path = fichierTemp.getName();
				//path2 = path.replaceAll("_", "'");
				path2 = path.substring(0, path.length() - 4);
			}

			listeFichier.forEach(res -> {
				String nameRes = res.getName();
				if(!nameRes.equalsIgnoreCase(".classpath")) {
					if (FilenameUtils.removeExtension(nameRes).endsWith("_0") || FilenameUtils.removeExtension(nameRes).endsWith("_1") ||FilenameUtils.removeExtension(nameRes).endsWith("_2") ||FilenameUtils.removeExtension(nameRes).endsWith("_3") ||FilenameUtils.removeExtension(nameRes).endsWith("_4") ||FilenameUtils.removeExtension(nameRes).endsWith("_5") ||FilenameUtils.removeExtension(nameRes).endsWith("_6") ||FilenameUtils.removeExtension(nameRes).endsWith("_7") ||FilenameUtils.removeExtension(nameRes).endsWith("_8") ||FilenameUtils.removeExtension(nameRes).endsWith("_9")) {
						nameRes = nameRes.substring(0, nameRes.length() - 6);
					} else {
						nameRes = nameRes.substring(0, nameRes.length() - 4);
					}
					if (nameRes.equalsIgnoreCase(path2)) {
						res.delete();
						System.out.println(res.getPath() + "    " + listeFichier.size());
					}
				}
			});
		}
	}

	public static void main(String[] args) {
		Film epguides = new Film();

	}

}
