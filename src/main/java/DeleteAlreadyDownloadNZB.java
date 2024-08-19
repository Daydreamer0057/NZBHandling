import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.StringTokenizer;

public class DeleteAlreadyDownloadNZB {
	static String path2;
	static int compteurDelete = 0;

	static double tailleGagnee = 0.0;
	public DeleteAlreadyDownloadNZB() {

		File base = new File("z://film/new/treated");

		File[] fichiers = base.listFiles();

		HashSet<File> listeFichierATraiter = new HashSet<File>();
		ArrayList<File> listeDirectory = new ArrayList<File>();

		listeDirectory.add(new File("z:/test/test"));

		for (File fichier : fichiers) {
			if (fichier.isDirectory()) {
				listeDirectory.add(fichier);
			} else {
				if (!fichier.getName().endsWith(".srt") && (fichier.getName().endsWith(".mp4") || fichier.getName().endsWith(".mkv") || fichier.getName().endsWith(".avi"))) {
					listeFichierATraiter.add(fichier);
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
					if (!fichierTemp.getName().endsWith(".srt") && (fichierTemp.getName().endsWith(".mp4") || fichierTemp.getName().endsWith(".mkv") || fichierTemp.getName().endsWith(".avi"))) {
						listeFichierATraiter.add(fichierTemp);
					}
				}
			}
			listeDirectory.remove(0);
		}

		File baseDownload = new File("y://nzb/download");

		File[] fichiersDownload = baseDownload.listFiles();

		for (File fichierDownload : fichiersDownload) {
			for (File fichierTreated : listeFichierATraiter) {
				int resolutionTreated = 0;
				int resolutionDownload = 0;

				if (fichierTreated.getName().toLowerCase().contains("2160")) {
					resolutionTreated = 2160;
				}
				if (fichierTreated.getName().toLowerCase().contains("1080")) {
					resolutionTreated = 1080;
				}
				if (fichierTreated.getName().toLowerCase().contains("720")) {
					resolutionTreated = 720;
				}
				if (fichierTreated.getName().toLowerCase().contains("576")) {
					resolutionTreated = 576;
				}
				if (fichierTreated.getName().toLowerCase().contains("480")) {
					resolutionTreated = 480;
				}
				if (fichierTreated.getName().toLowerCase().contains("360")) {
					resolutionTreated = 360;
				}
				if (fichierTreated.getName().toLowerCase().contains("240")) {
					resolutionTreated = 240;
				}
				if (fichierTreated.getName().toLowerCase().contains("dvd")) {
					resolutionTreated = 480;
				}

				if (fichierDownload.getName().toLowerCase().contains("2160")) {
					resolutionDownload = 2160;
				}
				if (fichierDownload.getName().toLowerCase().contains("1080")) {
					resolutionDownload = 1080;
				}
				if (fichierDownload.getName().toLowerCase().contains("720")) {
					resolutionDownload = 720;
				}
				if (fichierDownload.getName().toLowerCase().contains("576")) {
					resolutionDownload = 576;
				}
				if (fichierDownload.getName().toLowerCase().contains("480")) {
					resolutionDownload = 480;
				}
				if (fichierDownload.getName().toLowerCase().contains("360")) {
					resolutionDownload = 360;
				}
				if (fichierDownload.getName().toLowerCase().contains("240")) {
					resolutionDownload = 240;
				}
				if (fichierDownload.getName().toLowerCase().contains("dvd")) {
					resolutionDownload = 480;
				}

				String nameFile = fichierTreated.getName();

				String year = fichierTreated.getName().substring(nameFile.indexOf("(") + 1, nameFile.indexOf(")"));

				if (fichierDownload.getName().contains(year)) {

					try {
						if (nameFile.indexOf("(") != -1) {
							nameFile = nameFile.substring(0, nameFile.indexOf("("));
							nameFile = nameFile.trim();
							nameFile = nameFile.replaceAll("[-]", " ");
							nameFile = nameFile.replaceAll("[.]", " ");
						}
					} catch (Exception ex) {
						ex.printStackTrace();
						System.out.println("namefile " + nameFile);
					}

					if (nameFile.length() > 4) {
						StringTokenizer stk = new StringTokenizer(nameFile, " ");
						int nbToken = stk.countTokens();
						boolean[] testCount = new boolean[nbToken];

						for (int i = 0; i < stk.countTokens(); i++) {
							testCount[i] = false;
						}

						int compteur = 0;
						while (stk.hasMoreTokens()) {
							if (fichierDownload.getName().toLowerCase().contains(stk.nextToken().toLowerCase())) {
								testCount[compteur] = true;
								compteur++;
							}
						}

						boolean testFinal = true;
						for (int i = 0; i < nbToken; i++) {
							if (!testCount[i]) {
								testFinal = false;
								break;
							}
						}

						if (testFinal && (resolutionDownload <= resolutionTreated)) {
								if(fichierDownload.getName().toLowerCase().substring(0,fichierDownload.getName().indexOf(".")).equalsIgnoreCase(fichierTreated.getName().toLowerCase().substring(0,fichierTreated.getName().indexOf(" ")))) {
									System.out.println(fichierDownload.getName() + "    " + fichierTreated.getName() + "    " + resolutionDownload + "    " + resolutionTreated);
									fichierDownload.delete();
								}
						}
					}
				}
			}
		}
	}


	public long calculTaille(File file) {
		long taille = 0;
		HashSet<File> listeFichierDownload = new HashSet<File>();
		ArrayList<File> listeDirectoryDownload = new ArrayList<File>();
		listeDirectoryDownload.add(file);

		while (listeDirectoryDownload.size() > 0) {
			System.out.println(listeFichierDownload.size() + "    " + listeDirectoryDownload.size());
			File[] fichierListeDownload = listeDirectoryDownload.get(0).listFiles();

			if (fichierListeDownload != null) {
				for (File fichierTempDownload : fichierListeDownload) {
					if (fichierTempDownload.isDirectory()) {
						listeDirectoryDownload.add(fichierTempDownload);
					} else {
						taille += fichierTempDownload.length();
					}
				}
			}
			listeDirectoryDownload.remove(0);
		}
		return taille;
	}

	public static void main(String[] args) {
		DeleteAlreadyDownloadNZB epguides = new DeleteAlreadyDownloadNZB();

	}

}
