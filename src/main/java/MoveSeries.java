
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;

public class MoveSeries {

	public MoveSeries() {
		File base = new File("z://test/stockage");
//		File base = new File("x://convert");

		File[] fichiers = base.listFiles();

		HashSet<File> listeFichier = new HashSet<File>();
		ArrayList<File> listeDirectory = new ArrayList<File>();
		//listeDirectory.add(new File("d://series"));
		//listeDirectory.add(new File("d://temp 2/main"));

		for (File fichier : fichiers) {
			if (fichier.isDirectory()) {
				if (fichier.getPath().indexOf("anime") == -1 || fichier.getPath().indexOf("Anime") == -1) {
					listeDirectory.add(fichier);
				}
			} else {
				if (fichier.getPath().indexOf("anime") == -1 || fichier.getPath().indexOf("Anime") == -1) {
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
					listeFichier.add(fichierTemp);
				}
			}
			listeDirectory.remove(0);
		}

		File[] listeSeries = new File("z://series").listFiles();

		for (File fichierTemp : listeFichier) {
			for (File fichierSeries : listeSeries) {
				if (fichierTemp.getName().indexOf(" - ") != -1) {
					String name = fichierTemp.getName().substring(0, fichierTemp.getName().indexOf(" - ")).trim();
					name = name.replace("_", "'");

					Pattern p1 = Pattern.compile("([0-9][0-9][0-9][0-9])");
					Matcher m = p1.matcher(fichierTemp.getName().toLowerCase());

					if (m.find()) {
						name = name.replaceAll(m.group(0),"");
					}
					String nameLowerCase = name.toLowerCase();
					String nameSeriesLowerCase = fichierSeries.getName().toLowerCase();
					if (nameLowerCase.indexOf(nameSeriesLowerCase) != -1) {
						Path p = Paths.get(fichierSeries.getPath());

						String path = p.toString();
						String chemin = path.replaceAll("\\\\", "/");
						System.out.println(chemin + "/" + fichierTemp.getName());
						fichierTemp.renameTo(new File(chemin + "/" + fichierTemp.getName()));
					}
				}
			}
		}
	}

		public static void main(String[] args) {
		// TODO Auto-generated method stub
		MoveSeries du = new MoveSeries();
	}

}
