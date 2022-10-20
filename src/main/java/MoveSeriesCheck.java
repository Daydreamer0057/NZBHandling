
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;

public class MoveSeriesCheck {

	public MoveSeriesCheck() {
		File base = new File("z://test/film a traiter");
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
					String nameLowerCase = fichierTemp.getName().toLowerCase();
					int pos = nameLowerCase.indexOf(" - ");
					nameLowerCase = nameLowerCase.substring(0,pos);
					String nameSeriesLowerCase = fichierSeries.getName().toLowerCase();
					
					if (nameLowerCase.indexOf(nameSeriesLowerCase) != -1) {
						Path p = Paths.get(fichierSeries.getPath());

						String path = p.toString();
						String chemin = path.replaceAll("\\\\", "/");
						System.out.println(chemin + "/" + fichierTemp.getName());
						fichierTemp.renameTo(new File("z://test/test/" + fichierTemp.getName()));
					}
				}
			}
		}

		listeSeries = new File("e://humour").listFiles();

		for (File fichierTemp : listeFichier) {
			for (File fichierSeries : listeSeries) {
				if(fichierSeries.isDirectory()){
				if (fichierTemp.getName().indexOf(" - ") != -1) {
					String nameLowerCase = fichierTemp.getName().toLowerCase();
					int pos = nameLowerCase.indexOf(" - ");
					nameLowerCase = nameLowerCase.substring(0, pos);
					String nameSeriesLowerCase = fichierSeries.getName().toLowerCase();

					if (nameLowerCase.indexOf(nameSeriesLowerCase) != -1) {
						Path p = Paths.get(fichierSeries.getPath());

						String path = p.toString();
						String chemin = path.replaceAll("\\\\", "/");
						System.out.println(chemin + "/" + fichierTemp.getName());
						fichierTemp.renameTo(new File("z://test/test/" + fichierTemp.getName()));
					}
				}
				}
			}
		}
	}

		public static void main(String[] args) {
		// TODO Auto-generated method stub
		MoveSeriesCheck du = new MoveSeriesCheck();
	}

}
