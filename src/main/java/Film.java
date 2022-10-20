
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;

public class Film {
	static String path2;
	static int compteurDelete = 0;
	public Film() {
		// Dossier a supprimer
		File base = new File("z://test/test");
//		File base = new File("z://documentaires/How The Universe Works");
//		File base = new File("Z://Series/We Own This City");
//		File base = new File("e://humour/convert");
//		File base = new File("e://theatre/convert");
		// File base = new File("f://Graver/Theatre/Convert");

		File[] fichiers = base.listFiles();

		HashSet<File> listeFichier = new HashSet<File>();
		ArrayList<File> listeDirectory = new ArrayList<File>();

		for (File fichier : fichiers) {
			if (fichier.isDirectory()) {
				listeDirectory.add(fichier);
			} else {
				if(!fichier.getName().endsWith(".srt")&&(fichier.getName().endsWith(".mp4")||fichier.getName().endsWith(".mkv")||fichier.getName().endsWith(".avi"))) {
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
					if(!fichierTemp.getName().endsWith(".srt")&&(fichierTemp.getName().endsWith(".mp4")||fichierTemp.getName().endsWith(".mkv")||fichierTemp.getName().endsWith(".avi"))) {
						listeFichier.add(fichierTemp);
					}
				}
			}
			listeDirectory.remove(0);
		}

		int compteur = 0;
		HashSet<File> fileBase = new HashSet<File>();
		HashSet<File> files = new HashSet<File>();
		fileBase.add(new File("z://temp/convert"));
//		fileBase.add(new File("z://series/Star Trek Strange New Worlds"));
//		fileBase.add(new File("e://humour/h265"));
//		fileBase.add(new File("z://test/temp"));

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
				path2 = path.substring(0, path.length() - 8);
			}


			listeFichier.forEach(res -> {
				try {
					compteurDelete++;
					String nameRes = res.getName();
					if (!nameRes.equalsIgnoreCase(".classpath")) {
						if (FilenameUtils.removeExtension(nameRes).endsWith("_0") || FilenameUtils.removeExtension(nameRes).endsWith("_1") || FilenameUtils.removeExtension(nameRes).endsWith("_2") || FilenameUtils.removeExtension(nameRes).endsWith("_3") || FilenameUtils.removeExtension(nameRes).endsWith("_4") || FilenameUtils.removeExtension(nameRes).endsWith("_5") || FilenameUtils.removeExtension(nameRes).endsWith("_6") || FilenameUtils.removeExtension(nameRes).endsWith("_7") || FilenameUtils.removeExtension(nameRes).endsWith("_8") || FilenameUtils.removeExtension(nameRes).endsWith("_9")) {
							if (nameRes.length() > 5) {
								nameRes = nameRes.substring(0, nameRes.length() - 6);
							} else {
								nameRes = nameRes.substring(0, nameRes.length() - 4);
							}
						}
						nameRes = FilenameUtils.removeExtension(nameRes);
//						System.out.println(path2+"    "+nameRes);
						if (nameRes.toLowerCase().contains(path2.toLowerCase())) {
							res.renameTo(new File("z:/test/stockage/"+res.getName()));
//							res.delete();
							System.out.println(res.getPath() + "    " + listeFichier.size());
						}
					}
				}catch(Exception ex) {
					ex.printStackTrace();
				}
			});
		}
	}

	public static void main(String[] args) {
		Film epguides = new Film();

	}

}
