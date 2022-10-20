
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;

public class MoveSeriesSimple {

	public MoveSeriesSimple() {
		File base = new File("z://test/stockage");

		File[] fichiers = base.listFiles();

		HashSet<File> listeFichier = new HashSet<File>();
		ArrayList<File> listeDirectory = new ArrayList<File>();

		for (File fichier : fichiers) {
			if (fichier.isDirectory()) {
				if (fichier.getPath().indexOf("anime") == -1 || fichier.getPath().indexOf("Anime") == -1) {
					listeDirectory.add(fichier);
				}
			} else {
				if (fichier.getPath().indexOf("anime") == -1 || fichier.getPath().indexOf("Anime") == -1) {
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
				} else {
					listeFichier.add(fichierTemp);
				}
			}
			listeDirectory.remove(0);
		}

		for(File fichierTemp : listeFichier) {
			try {
				try {
					String nameLowerCase = fichierTemp.getName();
					int pos = nameLowerCase.indexOf(" - ");
					nameLowerCase = nameLowerCase.substring(0,pos);

//							System.out.println(chemin + "/" + fichierTemp.getName());

				Path p = Paths.get("z://series/"+ nameLowerCase);

				String path = p.toString();
				String chemin = path.replaceAll("\\\\", "/");
				System.out.println(chemin + "/" + nameLowerCase);
				boolean test = fichierTemp.renameTo(new File(chemin + "/" + fichierTemp.getName()));
				if(!test){
					test = fichierTemp.renameTo(new File("z:/test/test/"));
				}
//						System.out.println(chemin + "/" + fichierTemp.getName());
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}




		File base2 = new File("z://test/stockage");

		File[] fichiers2 = base.listFiles();

		HashSet<File> listeFichier2 = new HashSet<File>();
		ArrayList<File> listeDirectory2 = new ArrayList<File>();

		for (File fichier : fichiers) {
			if (fichier.isDirectory()) {
				if (fichier.getPath().indexOf("anime") == -1 || fichier.getPath().indexOf("Anime") == -1) {
					listeDirectory2.add(fichier);
				}
			} else {
				if (fichier.getPath().indexOf("anime") == -1 || fichier.getPath().indexOf("Anime") == -1) {
					listeFichier2.add(fichier);
				}
			}
		}

		while (listeDirectory2.size() > 0) {
			File fichier = listeDirectory2.get(0);

			File[] fichierListe = fichier.listFiles();

			for (File fichierTemp : fichierListe) {
				if (fichierTemp.isDirectory()) {
					listeDirectory2.add(fichierTemp);
				} else {
					listeFichier2.add(fichierTemp);
				}
			}
			listeDirectory2.remove(0);
		}

		for(File fichierTemp : listeFichier) {
			try {
				try {
					String nameLowerCase = fichierTemp.getName();
					int pos = nameLowerCase.indexOf(" - ");
					nameLowerCase = nameLowerCase.substring(0,pos);

//							System.out.println(chemin + "/" + fichierTemp.getName());

					Path p = Paths.get("e://humour/"+ nameLowerCase);

					String path = p.toString();
					String chemin = path.replaceAll("\\\\", "/");
					System.out.println(chemin + "/" + nameLowerCase);
					boolean test = fichierTemp.renameTo(new File(chemin + "/" + fichierTemp.getName()));
					if(!test){
						test = fichierTemp.renameTo(new File("z:/test/test/"));
					}
//						System.out.println(chemin + "/" + fichierTemp.getName());
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MoveSeriesSimple du = new MoveSeriesSimple();
	}

}
