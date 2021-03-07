import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;

public class CopyTrim {

	public CopyTrim() {
		File base = new File("z://test/main");

		File[] fichiers = base.listFiles();

		HashSet<File> listeFichier = new HashSet<File>();
		ArrayList<File> listeDirectory = new ArrayList<File>();
		// listeDirectory.add(new File("f://series"));
		// listeDirectory.add(new File("e://temp 2/main"));

		// for (File fichier : fichiers) {
		// if (fichier.isDirectory()) {
		// listeDirectory.add(fichier);
		// } else {
		// listeFichier.add(fichier);
		// }
		// }
		//
		// while (listeDirectory.size() > 0) {
		// File fichier = listeDirectory.get(0);
		//
		// File[] fichierListe = fichier.listFiles();
		//
		// for (File fichierTemp : fichierListe) {
		// if (fichierTemp.isDirectory()) {
		// listeDirectory.add(fichierTemp);
		// } else {
		// listeFichier.add(fichierTemp);
		// }
		// }
		// listeDirectory.remove(0);
		// }

		for (File fichier : fichiers) {
			if (fichier.getName().startsWith("Copy(")) {
				String line = fichier.getName();
				line = line.substring(11, line.length());

				String extension = fichier.getName().substring(fichier.getName().length() - 4,
						fichier.getName().length());

				// String fileName = fichierTemp.getPath();
				Path p = Paths.get(fichier.getPath());
				Path folder = p.getParent();

				String path = folder.toString();
				String chemin = path.replaceAll("\\\\", "/");

				File out = new File(chemin + "/" + line + extension);
				int compteurFichier = 0;
				while (out.exists()) {
					out = new File(chemin + "/" + line + "_" + compteurFichier + extension);
					compteurFichier++;
				}
				// System.out.println(" " + fichierTemp.getName() + " " + out.getName());
				fichier.renameTo(out);

				System.out.println(line);

			}
		}
	}

	public static void main(String[] args) {
		CopyTrim t = new CopyTrim();
	}

}
