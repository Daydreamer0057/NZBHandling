import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;

public class DeleteFilesQbit {
	static String path2;

	public DeleteFilesQbit() {
		// Dossier a supprimer
		File base = new File("Z://test/main qbit");
//		File base = new File("F://The Mandalorian");

		File[] fichiers = base.listFiles();

		HashSet<File> listeFichier = new HashSet<File>();
		HashSet<File> setDirectory = new HashSet<File>();
		ArrayList<File> listeDirectory = new ArrayList<File>();

		int compteurBegin = 0;
		for (File fichier : fichiers) {
			compteurBegin++;
			System.out.println(compteurBegin+" / "+fichiers.length);
			if (fichier.isDirectory()) {
				listeDirectory.add(fichier);
				setDirectory.add(fichier);
			} else {
				if (!(
//						|| fichier.getName().endsWith("cbz")|| fichier.getName().endsWith("cbr")
						 fichier.getName().endsWith(".!qB")
//						|| fichier.getName().endsWith("rar")
//						|| fichier.getName().toLowerCase().endsWith("par2")
				)) {

					listeFichier.add(fichier);
				}
			}
		}

		while (listeDirectory.size() > 0) {
			System.out.println("Files "+listeFichier.size()+"    Directory "+listeDirectory.size());
			File fichier = listeDirectory.get(0);

			File[] fichierListe = fichier.listFiles();

			if(fichierListe!=null) {
				for (File fichierTemp : fichierListe) {
					if (fichierTemp.isDirectory()) {
						listeDirectory.add(fichierTemp);
						setDirectory.add(fichierTemp);
					} else {
						if (!(
							fichierTemp.getName().endsWith(".!qB")
//							|| fichierTemp.getName().endsWith("rar")
//							|| fichierTemp.getName().toLowerCase().endsWith("par2")
						)) {
//							System.out.println(fichierTemp.getName());
							listeFichier.add(fichierTemp);
						}
					}
				}
			}
			listeDirectory.remove(0);
		}

		try {
			int compteur = 1;
			double taille = 0;
			double moyenne = 0;
			// PrintWriter pw = new PrintWriter("c://temp/log_delete.txt");
			for (File fichierTemp : listeFichier) {
				taille += fichierTemp.length();
				moyenne = new Double(taille/compteur/1024/1024);
				System.out.println(compteur + " / " + listeFichier.size() + "    " + fichierTemp.getPath()+"    moyenne "+moyenne+"    taille "+(new Double(taille/1024/1024/1024)));
				compteur++;
				// pw.println(fichierTemp.getPath());
				new Thread(() -> {
					fichierTemp.delete();
				}).start();
			}
			// pw.flush();
			// pw.close();

			/*for(File fichierTemp : setDirectory){
				File[] listDirectory = fichierTemp.listFiles();
				if(listDirectory.length==0){
					FileUtils.deleteDirectory(fichierTemp);
				}
			}*/
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public static void main(String[] args) {
		DeleteFilesQbit epguides = new DeleteFilesQbit();

	}

}
