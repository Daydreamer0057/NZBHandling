import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

public class MoveDownloaded {

	public MoveDownloaded() {
		File base = new File("w://download");

		File[] fichiers = base.listFiles();

		HashSet<File> listeFichier = new HashSet<File>();
		ArrayList<File> listeDirectory = new ArrayList<File>();
		//listeDirectory.add(new File("d://series"));
		//listeDirectory.add(new File("d://temp 2/main"));

		for (File fichier : fichiers) {
			if (fichier.isDirectory()) {
					listeDirectory.add(fichier);
			} else {
				if(fichier.getName().toLowerCase().contains("avi")||fichier.getName().toLowerCase().contains("mkv")||fichier.getName().toLowerCase().contains("mp4")) {
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
					if(fichierTemp.getName().toLowerCase().contains("avi")||fichierTemp.getName().toLowerCase().contains("mkv")||fichierTemp.getName().toLowerCase().contains("mp4")) {
						listeFichier.add(fichierTemp);
					}
				}
			}
			listeDirectory.remove(0);
		}

		try {
			for (File fichier : listeFichier) {
				String cheminFinal = fichier.getPath().replace("w:/download", "z:/test/film");
				if (fichier.getParentFile().getName().toLowerCase().contains("admin")) {
					FileUtils.moveDirectory(fichier.getParentFile().getParentFile(), new File(cheminFinal));
				} else {
					FileUtils.moveDirectory(fichier.getParentFile(), new File(cheminFinal));
				}
			}
		} catch(IOException ex){
			ex.printStackTrace();
		}
	}



	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MoveDownloaded du = new MoveDownloaded();
	}

}
