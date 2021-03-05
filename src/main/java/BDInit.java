import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;

public class BDInit {

	public static void main(String[] args) {
		File base = new File("z://comics/bd francaise");

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
			}
			listeDirectory.remove(0);
		}

		for (File fichierTemp : listeFichier) {

			Path p2 = Paths.get(fichierTemp.getPath());
			Path folder2 = p2.getParent();
			String path2 = folder2.toString();
			path2 = path2.replaceAll("\\\\", "/");
			path2 = path2.replace("z:", "e:");

			File dir = new File(path2);
			if (!dir.exists())
				dir.mkdirs();
			try {
				String chemin = fichierTemp.getPath();
				chemin = chemin.replace("z:", "e:");
				PrintWriter pw = new PrintWriter(chemin);
				pw.flush();
				pw.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

}
