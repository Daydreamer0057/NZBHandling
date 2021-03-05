
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Properties;

public class Epguides {
	Properties appProps;
	String rootPath;

	public Epguides() {
		try {

			File base = new File("f://epguides");

			File[] fichiers = base.listFiles();

			HashSet<File> listeFichier = new HashSet<File>();
			ArrayList<File> listeDirectory = new ArrayList<File>();

			for (File fichierTemp : fichiers) {
				if (fichierTemp.isDirectory()) {
					listeDirectory.add(fichierTemp);
				} else {
					if (fichierTemp.getName().contains("index.html") || fichierTemp.getName().contains("index.htm")) {
						listeFichier.add(fichierTemp);
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
						if (fichierTemp.getName().contains("index.html")
								|| fichierTemp.getName().contains("index.htm")) {
							listeFichier.add(fichierTemp);
						}
					}
				}
				listeDirectory.remove(0);
			}

			int compteur = 0;

			for (File fichier : listeFichier) {
				boolean foundEpisode = false;
				boolean found30Minutes = false;
				boolean testNetflix = true;
				boolean testYear = false;
				FileReader fr = new FileReader(fichier);
				BufferedReader br = new BufferedReader(fr);

				String line = "";

				while (line != null) {
					line = br.readLine();
					// if (line != null && (line.contains("aired from") && (line.contains("2019"))))
					// {
					// foundEpisode = true;
					// }
					// if (line != null && (line.contains("2018")) && !line.contains("<em>")
					// && !line.contains("Text copyright")) {
					// if (line.contains("Start date") || line.contains("End date")) {
					// testYear = true;
					// }
					// }
					if (line != null && (line.contains("30 min"))) {
						testNetflix = false;
					}
					if (line != null && line.contains("Science Fiction")) {
						found30Minutes = true;
						// foundEpisode = true;
					}
					if (line != null &&line.contains("Season 2")) {
						testYear = true;
					}
				}
				br.close();
				fr.close();

				System.out.println("" + compteur + " / " + listeFichier.size());
				compteur++;
				System.out.println(testNetflix + "    " + testYear);
				// if (!testNetflix || !testYear || found30Minutes) {
				if (!testNetflix || !found30Minutes || !testYear) {
					fichier.delete();
				}
			}
		} catch (IOException ioex) {
			System.out.println(ioex);
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			Epguides ep = new Epguides();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}