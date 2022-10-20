
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.StringTokenizer;

public class DeleteWebBluDVD {
	static String path2;
	static String year;
	static String nameSeries;
	static int resolutionSeries = 0;
	static int compteurDelete = 0;

	public DeleteWebBluDVD() {
		File base = new File("z://test/film a traiter");

		File[] fichiers = base.listFiles();

		HashSet<File> listeFichier = new HashSet<File>();
		ArrayList<File> listeDirectory = new ArrayList<File>();
		ArrayList<File> listeDirectoryTri = new ArrayList<File>();


		for (File fichier : fichiers) {
			System.out.println("Files "+listeFichier.size()+"    Directory "+listeDirectory.size()+"    tri "+listeDirectoryTri.size());
			if (fichier.isDirectory()) {
					listeDirectory.add(fichier);
				if(!fichier.getPath().toLowerCase().contains("web")&&!fichier.getPath().toLowerCase().contains("blu")&&!fichier.getPath().toLowerCase().contains("dvd")) {
					listeDirectoryTri.add(fichier);
				}
			} else {
				if(!fichier.getPath().toLowerCase().contains("web")&&!fichier.getPath().toLowerCase().contains("blu")&&!fichier.getPath().toLowerCase().contains("dvd")) {
					listeFichier.add(fichier);
				}
			}
		}

		while (listeDirectory.size() > 0) {
			System.out.println("Files "+listeFichier.size()+"    Directory "+listeDirectory.size()+"    tri "+listeDirectoryTri.size());
			File fichier = listeDirectory.get(0);
			File[] fichierListe = fichier.listFiles();

			for (File fichierTemp : fichierListe) {
				if (fichierTemp.isDirectory()) {
					listeDirectory.add(fichierTemp);
					if(!fichierTemp.getPath().toLowerCase().contains("web")&&!fichierTemp.getPath().toLowerCase().contains("blu")&&!fichierTemp.getPath().toLowerCase().contains("dvd")) {
						listeDirectoryTri.add(fichierTemp);
					}
				} else {
					if(!fichierTemp.getPath().toLowerCase().contains("web")&&!fichierTemp.getPath().toLowerCase().contains("blu")&&!fichierTemp.getPath().toLowerCase().contains("dvd")) {
						listeFichier.add(fichierTemp);
					}
				}
			}
			listeDirectory.remove(0);
		}

//		for(File fichierTemp : listeDirectoryTri){
//			System.out.println(fichierTemp.getPath());
//
//		}

		int compteur = 0;
		for(File fichierTemp : listeFichier){
			compteur++;
			System.out.println(fichierTemp.getPath()+"    "+compteur+" / "+listeFichier.size());
			try {
				fichierTemp.delete();
			} catch(Exception ex){
				ex.printStackTrace();
			}
		}

		int compteurDir = 0;
		for(File fichierTemp : listeDirectoryTri){
			compteurDir++;
			System.out.println(fichierTemp.getPath()+"    "+compteurDir+" / "+listeDirectoryTri.size());
			try {
				FileUtils.deleteDirectory(fichierTemp);
			} catch(Exception ex){
				ex.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		DeleteWebBluDVD epguides = new DeleteWebBluDVD();

	}

}
