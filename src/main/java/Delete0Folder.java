import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;

public class Delete0Folder {

	public Delete0Folder() {
		File base = new File("z://");

		File[] fichiers = base.listFiles();

		ArrayList<File> listeDirectory = new ArrayList<File>();

		for (File fichier : fichiers) {
			if (fichier.isDirectory()) {
				listeDirectory.add(fichier);
			}
		}

		int compteur = 0;
		while (listeDirectory.size() > 0) {
			System.out.println(compteur+" / "+ listeDirectory.size());
			File fichier = listeDirectory.get(0);

			File[] fichierListe = fichier.listFiles();

			if(fichierListe!=null) {
				for (File fichierTemp : fichierListe) {
					if (fichierTemp.isDirectory()) {
						listeDirectory.add(fichierTemp);
						compteur++;
					}
				}
			}
			listeDirectory.remove(0);
		}


			System.out.println("Effacement");

			listeDirectory.forEach(folder -> {
				File[] liste = folder.listFiles();

				if (liste.length == 0) {
					System.out.println(folder.getPath());
//					folder.delete();
				}
			});

			System.out.println("Fini");
		}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Delete0Folder del = new Delete0Folder();
	}

}
