import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;

public class FilmFrance {
	static String path2;
	static int compteurDelete = 0;

	static double tailleGagnee = 0.0;
	public FilmFrance() {
//		File basic = new File("z:/test/sabnzbd");
//		File[] listFiles = basic.listFiles();
//
//		for(File fichierTemp : listFiles){
//			fichierTemp.renameTo(new File(fichierTemp.getPath().replaceAll("convert","")));
//		}
//
//		String eraseName = "z:/test/films france";
		String eraseName = "z:/film/france/convert";
//		String eraseName = "z:/test/stockage";
//		String eraseName = "e:/humour/h265";
//		String baseName = "z:/temp/main";
		String baseName = "z:/temp/convert2";
//		String baseName = "z:/temp/convert av1";
//		String baseName = "e:/humour/convert";

		boolean test01 = ReplaceConvertFiles.ReplaceConvertFiles(baseName);
		boolean test02 = ReplaceConvertFiles.ReplaceConvertFiles(eraseName);
//		boolean test02 = ReplaceConvertFiles.ReplaceConvertFiles("e:/humour/h265");
//		boolean test02 = ReplaceConvertFiles.ReplaceConvertFiles("z://test/Convert Videoproc");

		// Dossier a supprimer
		File base = new File(eraseName);
//		File base = new File("e://humour/convert");
//		File base = new File("z://film/new/treated");<

		File[] fichiers = base.listFiles();

		HashSet<File> listeFichier = new HashSet<File>();
		ArrayList<File> listeDirectory = new ArrayList<File>();

		for (File fichier : fichiers) {
			if (fichier.isDirectory()) {
				listeDirectory.add(fichier);
			} else {
				if (!fichier.getName().endsWith(".srt") && (fichier.getName().endsWith(".mp4") || fichier.getName().endsWith(".mkv") || fichier.getName().endsWith(".avi"))) {
					listeFichier.add(fichier);
				}
			}
		}

		while (listeDirectory.size() > 0) {
			System.out.println(listeFichier.size()+"    "+listeDirectory.size());
			File fichier = listeDirectory.get(0);
			File[] fichierListe = fichier.listFiles();

			for (File fichierTemp : fichierListe) {
				if (fichierTemp.isDirectory()) {
					listeDirectory.add(fichierTemp);
				} else {
					if (!fichierTemp.getName().endsWith(".srt") && (fichierTemp.getName().endsWith(".mp4") || fichierTemp.getName().endsWith(".mkv") || fichierTemp.getName().endsWith(".avi"))) {
						listeFichier.add(fichierTemp);
					}
				}
			}
			listeDirectory.remove(0);
		}

		int compteur = 0;
		HashSet<File> fileBase = new HashSet<File>();
		HashSet<File> files = new HashSet<File>();
		fileBase.add(new File(baseName));
//		fileBase.add(new File("e://humour/h265"));

		ArrayList<File> listeDirectory2 = new ArrayList<File>();

		for (File filesTemp : fileBase) {
			if (filesTemp.isDirectory()) {
				listeDirectory2.add(filesTemp);
			} else {
				files.add(filesTemp);
			}
		}

		while (listeDirectory2.size() > 0) {
			System.out.println(files.size()+"    "+listeDirectory2.size());
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


		for (File fichierConvert : files) {
			String nomFichier = "";
			// System.out.println("Compteur " + compteur + " / " + files.length);
			compteur++;
			int pos = fichierConvert.getName().indexOf("-");
			final String path = fichierConvert.getName();
			//path2 = path.replaceAll("_", "'");

			if (path.length() > 8) {
				path2 = path.substring(0, path.length() - 8);

				listeFichier.forEach(fichierTestTest -> {
					try {

						compteurDelete++;
						String nameRes = fichierTestTest.getName();
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
							if (nameRes.toLowerCase().contains(path2.toLowerCase()) && fichierConvert.length() > 0) {
								if (fichierTestTest.length() < fichierConvert.length()) {
									try {
										if (fichierTestTest.getName().toLowerCase().contains("2160p") || fichierConvert.getName().toLowerCase().contains("2160p")) {
											System.out.println("Delete 2160p    " + fichierTestTest.getPath() + "    " + fichierConvert.getPath() + "    " + fichierTestTest.length() + "    " + fichierConvert.length());
											fichierConvert.delete();
											fichierTestTest.renameTo(new File(baseName + "/" + fichierTestTest.getName()));
										} else {
											System.out.println("Delete temp/convert    " + fichierTestTest.getPath() + "    " + fichierConvert.getPath() + "    " + fichierTestTest.length() + "    " + fichierConvert.length());
											fichierConvert.delete();

											fichierTestTest.renameTo(new File(baseName + "/" + fichierTestTest.getName()));
										}
									} catch (Exception ex) {
										ex.printStackTrace();
										System.out.println("Error in copy to directory line 120");
									}
								} else {
									tailleGagnee += fichierTestTest.length() - fichierConvert.length();
									fichierTestTest.delete();
									System.out.println("Delete test/test    " + fichierTestTest.getPath() + "    " + fichierTestTest.length());
//							}
								}
							}
						}
					} catch (Exception ex) {
						ex.printStackTrace();
						System.out.println("Error in copy to directory line 120");
					}
				});

			}
		}
		System.out.println("Taille Gagnee "+(tailleGagnee/1000000000));
		}


	public static void main(String[] args) {
		FilmFrance epguides = new FilmFrance();

	}

}
