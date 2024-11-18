import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;

public class DeleteRarFiles {
	static String path2;

	public DeleteRarFiles() {
		double taille = 0;
		String chemin = "Z://test/main a traiter";
//		String chemin = "w://download";
		// Dossier a supprimer
		File base = new File(chemin);
//		File base = new File("F://The Mandalorian");


		File[] fichiers = base.listFiles();

		HashSet<File> listeFichier = new HashSet<File>();
		HashSet<File> setDirectory = new HashSet<File>();
		ArrayList<File> listeDirectory = new ArrayList<File>();

		int compteurBegin = 0;
		for (File fichier : fichiers) {
			System.out.println("ListeDirectory Size "+listeDirectory.size());
			if (fichier.isDirectory()) {
				listeDirectory.add(fichier);
				setDirectory.add(fichier);
			} else {
				if (!(fichier.getName().toLowerCase().endsWith("mkv") || fichier.getName().toLowerCase().endsWith("mp4")
						|| fichier.getName().toLowerCase().endsWith("avi")
//						|| fichier.getName().toLowerCase().endsWith("cbz")|| fichier.getName().toLowerCase().endsWith("cbr")
						|| fichier.getName().toLowerCase().endsWith(".!qB")
//						|| fichier.getName().toLowerCase().endsWith("iso")
//						|| fichier.getName().toLowerCase().toLowerCase().endsWith("par2")
				)) {
					System.out.println("Files Deleted " + compteurBegin + " / " + listeFichier.size() + "   Dir Size " + listeDirectory.size() + "    " + fichier.getName());
						taille += fichier.length();
						fichier.delete();
					compteurBegin++;

				}
			}
		}

		while (listeDirectory.size() > 0) {
//			System.out.println("ListeDirectory Size "+listeDirectory.size());
//			System.out.println("Files "+listeFichier.size()+"    Directory "+listeDirectory.size());
			File fichier = listeDirectory.get(0);

			File[] fichierListe = fichier.listFiles();

			if(fichierListe!=null) {
				for (File fichierTemp : fichierListe) {
					if (fichierTemp.isDirectory()) {
						listeDirectory.add(fichierTemp);
						setDirectory.add(fichierTemp);
					} else {
						if (!(fichierTemp.getName().endsWith("mkv") || fichierTemp.getName().endsWith("mp4")
								|| fichierTemp.getName().endsWith("avi")
//								|| fichierTemp.getName().endsWith("cbz") || fichierTemp.getName().endsWith("cbr")
								|| fichierTemp.getName().endsWith(".!qB")
//							|| fichierTemp.getName().endsWith("iso")
//							|| fichierTemp.getName().toLowerCase().endsWith("par2")
						)) {
//							System.out.println(fichierTemp.getName());
//							listeFichier.add(fichierTemp);
							System.out.println("Files Deleted " + compteurBegin + " / " + listeFichier.size() + "   Dir Size " + listeDirectory.size() + "    " + fichierTemp.getName());
							taille += fichier.length();
							while(Thread.activeCount()>50){
								try {
									Thread.sleep(1000);
								} catch (InterruptedException ex){
									ex.printStackTrace();
								}
							}
							new Thread(() -> {
								fichierTemp.delete();
							}).start();
							compteurBegin++;
						}
					}
				}
			}
			listeDirectory.remove(0);
		}

		TrimFailedUnpack t = new TrimFailedUnpack(chemin);

		System.out.println("Taille gagnee "+taille+"    "+(taille/1000000000));

	}

	public static void main(String[] args) {
		DeleteRarFiles epguides = new DeleteRarFiles();

	}

}
