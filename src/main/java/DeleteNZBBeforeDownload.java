
import org.apache.commons.io.FilenameUtils;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.StringTokenizer;

public class DeleteNZBBeforeDownload {
	static String path2;
	static String year;
	static String nameSeries;
	static int resolutionSeries = 0;
	static int compteurDelete = 0;

	public DeleteNZBBeforeDownload() {
		// Dossier a supprimer
		File base = new File("v://nzb/a trier");
		//File base = new File("Z://film/new");
		//File base = new File("e://theatre/convert");
		// File base = new File("f://Graver/Theatre/Convert");

		File baseFilm = new File("z://film/new");
		//File base = new File("Z://film/new");
		//File base = new File("e://theatre/convert");
		// File base = new File("f://Graver/Theatre/Convert");

		File[] fichiersFilm = baseFilm.listFiles();

		HashSet<String> listeFichierFilm = new HashSet<String>();
		ArrayList<File> listeDirectoryFilm = new ArrayList<File>();

		for (File fichier : fichiersFilm) {
			if (fichier.isDirectory()) {
				listeDirectoryFilm.add(fichier);
			} else {
				if((fichier.getName().endsWith(".mp4")||fichier.getName().endsWith(".mkv")||fichier.getName().endsWith(".avi"))) {
					try {
						String name = fichier.getName().substring(0, fichier.getName().indexOf("(")-1).trim();
						listeFichierFilm.add(name);
					} catch(Exception ex){
						ex.printStackTrace();
					}
				}
			}
		}

		while (listeDirectoryFilm.size() > 0) {
			System.out.println("Fichiers "+listeFichierFilm.size()+"    Directories "+listeDirectoryFilm.size());
			File fichier = listeDirectoryFilm.get(0);
			File[] fichierListe = fichier.listFiles();

			for (File fichierTemp : fichierListe) {
				if (fichierTemp.isDirectory()) {
					listeDirectoryFilm.add(fichierTemp);
				} else {
					if((fichierTemp.getName().endsWith(".mp4")||fichierTemp.getName().endsWith(".mkv")||fichierTemp.getName().endsWith(".avi"))) {
						try {
							if(fichierTemp.getName().indexOf("(")!=-1) {
								String name = fichierTemp.getName().substring(0, fichierTemp.getName().indexOf("(") - 1).trim();
								listeFichierFilm.add(name);
							}
						} catch(Exception ex){
							ex.printStackTrace();
						}
					}
				}
			}
			listeDirectoryFilm.remove(0);
		}

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

		HashSet<File> listeDelete = new HashSet<File>();

		int compteur = 0;
		for(File fichier : listeFichier){
			System.out.println(compteur+" / "+listeFichier.size());
			compteur++;
			String name = fichier.getName();
			name = name.replaceAll("[.]"," ");
			for(String line : listeFichierFilm){
				if(line.length()>=4&&name.indexOf(line)!=-1){
					listeDelete.add(fichier);
					break;
				}
			}
		}

		for(File fichier : listeDelete){
			fichier.delete();
		}
	}

	public static void main(String[] args) {
		DeleteNZBBeforeDownload epguides = new DeleteNZBBeforeDownload();

	}

}
