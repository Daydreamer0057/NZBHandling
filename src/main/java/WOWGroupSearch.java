
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;

import org.apache.commons.io.FileUtils;

public class WOWGroupSearch {
	static String path2;

	public WOWGroupSearch() {
		File base = new File("f://WOW TSM Groups");

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

		while (listeDirectory.size() > 0) {
			File fichier = listeDirectory.get(0);

			File[] fichierListe = fichier.listFiles();

			for (File fichierTemp : fichierListe) {
				if (fichierTemp.isDirectory()) {
					listeDirectory.add(fichierTemp);
				} else {
					listeFichier.add(fichierTemp);
				}
				// System.out.println(listeDirectory.size() + " Files " + listeFichier.size());
			}
			listeDirectory.remove(0);
		}

		for (File fichierTemp : listeFichier) {
			try {
				FileReader fr = new FileReader(fichierTemp);
				BufferedReader br = new BufferedReader(fr);

				String line = "";
				String lineTemp = "";

				while (lineTemp != null) {
					lineTemp = br.readLine();
					if (lineTemp != null) {
						line += lineTemp;
					}
				}
				br.close();
				fr.close();
				if (line.toLowerCase().contains("best") && line.toLowerCase().contains("seller")) {
					FileUtils.copyFile(fichierTemp, new File("f://WOW TSM Groups/found/" + fichierTemp.getName()));
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}

		}
	}

	public static void main(String[] args) {
		WOWGroupSearch epguides = new WOWGroupSearch();

	}

}
