
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;

public class DeleteRarFiles {
	static String path2;

	public DeleteRarFiles() {
		// Dossier a supprimer
		File base = new File("z://test/film");
//		File base = new File("F://The Mandalorian");

		File[] fichiers = base.listFiles();

		HashSet<File> listeFichier = new HashSet<File>();
		HashSet<File> setDirectory = new HashSet<File>();
		ArrayList<File> listeDirectory = new ArrayList<File>();

		for (File fichier : fichiers) {
			if (fichier.isDirectory()) {
				listeDirectory.add(fichier);
				setDirectory.add(fichier);
			} else {
				if (!(fichier.getName().endsWith("mkv") || fichier.getName().endsWith("mp4")
						|| fichier.getName().endsWith("avi") || fichier.getName().endsWith("srt")|| fichier.getName().endsWith("sample")|| fichier.getName().endsWith("cbz")|| fichier.getName().endsWith("cbr"))) {
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
					setDirectory.add(fichierTemp);
				} else {
					if (!(fichierTemp.getName().endsWith("mkv") || fichierTemp.getName().endsWith("mp4")
							|| fichierTemp.getName().endsWith("avi") || fichierTemp.getName().endsWith("srt")|| fichierTemp.getName().endsWith("sample")|| fichierTemp.getName().endsWith("cbz")|| fichierTemp.getName().endsWith("cbr"))) {
						listeFichier.add(fichierTemp);
					}
				}
			}
			listeDirectory.remove(0);
		}

		try {
			int compteur = 0;
			// PrintWriter pw = new PrintWriter("c://temp/log_delete.txt");
			for (File fichierTemp : listeFichier) {
				System.out.println(compteur + " / " + listeFichier.size() + "    " + fichierTemp.getPath());
				compteur++;
				// pw.println(fichierTemp.getPath());
				fichierTemp.delete();
			}
			// pw.flush();
			// pw.close();

			for(File fichierTemp : setDirectory){
				File[] listDirectory = fichierTemp.listFiles();
				if(listDirectory.length==0){
					FileUtils.deleteDirectory(fichierTemp);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public static void main(String[] args) {
		DeleteRarFiles epguides = new DeleteRarFiles();

	}

}
