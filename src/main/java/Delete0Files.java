import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;

public class Delete0Files {

	public Delete0Files() {
		File base = new File("f:///Download/Video/Star Trek Voyager");

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

		System.out.println("Effacement");

		listeFichier.forEach(fichier -> {
			if (fichier.length() == 0) {
				fichier.delete();
			}
		});

		System.out.println("Fini");
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Delete0Files del = new Delete0Files();
	}

}
