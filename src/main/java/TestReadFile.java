import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;

public class TestReadFile {

	public TestReadFile() {
		File base = new File("f:///test");

		File[] fichiers = base.listFiles();

		HashSet<File> listeFichier = new HashSet<File>();
		ArrayList<File> listeDirectory = new ArrayList<File>();

		for (File fichier : fichiers) {
			if (fichier.isDirectory()) {
				listeDirectory.add(fichier);
			} else {
				if (fichier.getName().endsWith("html"))
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
			}
			listeDirectory.remove(0);
		}

		int compteur = 0;
		int total = 0;
		int change = 0;
		for (File fichierTemp : listeFichier) {
			System.out.println("Fichier " + compteur + " / " + listeFichier.size());
			compteur++;
			boolean nameTest = false;
			boolean titleTest = false;
			try {
				// String lineFichier = FileUtils.readFileToString(fichierTemp);

				FileReader fr = new FileReader(fichierTemp);
				BufferedReader br = new BufferedReader(fr);

				String line = "";

				String mediaName = "";
				String fileName = "";
				String lineFichier = "";
				while (line != null) {
					line = br.readLine();
					if (line != null) {
						lineFichier += line + "\\n";
					}
				}
				br.close();
				fr.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TestReadFile test = new TestReadFile();
	}

}
