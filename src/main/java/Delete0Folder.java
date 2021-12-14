import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;

public class Delete0Folder {

	public Delete0Folder() {
		File base = new File("y://");

		File[] fichiers = base.listFiles();

		ArrayList<File> listeDirectory = new ArrayList<File>();

		for (File fichier : fichiers) {
			if (fichier.isDirectory()) {
				listeDirectory.add(fichier);
			}
		}

		while (listeDirectory.size() > 0) {
			File fichier = listeDirectory.get(0);

			File[] fichierListe = fichier.listFiles();

			if(fichierListe!=null) {
				for (File fichierTemp : fichierListe) {
					if (fichierTemp.isDirectory()) {
						listeDirectory.add(fichierTemp);
					}
				}
			}
			listeDirectory.remove(0);
		}


			System.out.println("Effacement");

			listeDirectory.forEach(folder -> {
				File[] list = folder.listFiles();

				if (list.length == 0) {
					folder.delete();
				}
			});

			System.out.println("Fini");
		}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Delete0Folder del = new Delete0Folder();
	}

}
