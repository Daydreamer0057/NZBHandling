
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

public class MoveSeries {

	public MoveSeries() {
		File base = new File("z://test/main a traiter");
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

		for(File fichierTemp : listeFichier) {
			try {
				try {
					String nameLowerCase = fichierTemp.getName();
					int pos = nameLowerCase.indexOf(" - ");
					nameLowerCase = nameLowerCase.substring(0,pos);

					Files.createDirectories(Paths.get("z://series/"+nameLowerCase));
//							System.out.println(chemin + "/" + fichierTemp.getName());

				Path p = Paths.get("z://series/"+ nameLowerCase);

				String path = p.toString();
				String chemin = path.replaceAll("\\\\", "/");
				System.out.println(chemin + "/" + nameLowerCase);
				fichierTemp.renameTo(new File(chemin + "/" + fichierTemp.getName()));
//						System.out.println(chemin + "/" + fichierTemp.getName());
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}


//		File[] listeSeries = new File("z://series").listFiles();
//
//		for (File fichierTemp : listeFichier) {
//			for (File fichierSeries : listeSeries) {
//				if (fichierTemp.getName().indexOf(" - ") != -1) {
//					String nameLowerCase = fichierTemp.getName().toLowerCase();
//					int pos = nameLowerCase.indexOf(" - ");
//					nameLowerCase = nameLowerCase.substring(0,pos);
//					String nameSeriesLowerCase = fichierSeries.getName().toLowerCase();
//
//					if (nameLowerCase.indexOf(nameSeriesLowerCase) != -1) {
//						Path p = Paths.get(fichierSeries.getPath());
//
//						String path = p.toString();
//						String chemin = path.replaceAll("\\\\", "/");
//						System.out.println(chemin + "/" + fichierTemp.getName());
//						fichierTemp.renameTo(new File(chemin + "/" + fichierTemp.getName()));
//					}
//				}
//			}
//		}

//		File[] listeSeries = new File("e://humour").listFiles();
//
//		for (File fichierTemp : listeFichier) {
//			for (File fichierSeries : listeSeries) {
//				if(fichierSeries.isDirectory()){
//					if (fichierTemp.getName().indexOf(" - ") != -1) {
//						String nameLowerCase = fichierTemp.getName().toLowerCase();
//						int pos = nameLowerCase.indexOf(" - ");
//						nameLowerCase = nameLowerCase.substring(0, pos);
//						String nameSeriesLowerCase = fichierSeries.getName().toLowerCase();
//
//						if (nameLowerCase.indexOf(nameSeriesLowerCase) != -1) {
//							Path p = Paths.get(fichierSeries.getPath());
//
//							String path = p.toString();
//							String chemin = path.replaceAll("\\\\", "/");
//							System.out.println(chemin + "/" + fichierTemp.getName());
//							fichierTemp.renameTo(new File(chemin + "/" + fichierTemp.getName()));
//						}
//					}
//				}
//			}
//		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MoveSeries du = new MoveSeries();
	}

}
