import java.io.File;

public class ReplaceConvertFiles {

	public ReplaceConvertFiles() {
		long ms = System.currentTimeMillis();
		System.out.println("debut " + ms);
		try {
			File base = new File("z:/test/done");
			//File base = new File("d://te/film");

			File[] fichiers = base.listFiles();
//
//			HashSet<File> listeFichier = new HashSet<File>();
//			ArrayList<File> listeDirectory = new ArrayList<File>();
//
//			for (File fichier : fichiers) {
//				if (fichier.isDirectory()) {
//					listeDirectory.add(fichier);
//				} else if (fichier.isFile()) {
//					if ((fichier.getName().endsWith("mkv") || fichier.getName().endsWith("mp4")
//							|| fichier.getName().endsWith("avi"))) {
//						listeFichier.add(fichier);
//					}
//				}
//			}
//
//			while (listeDirectory.size() > 0) {
//				File fichier = listeDirectory.get(0);
//
//				File[] fichierListe = fichier.listFiles();
//
//				if (fichierListe != null) {
//					for (File fichierTemp : fichierListe) {
//						if (fichierTemp.isDirectory()) {
//							listeDirectory.add(fichierTemp);
//						} else if (fichierTemp.isFile()) {
//							if ((fichierTemp.getName().endsWith("mkv") || fichierTemp.getName().endsWith("mp4")
//									|| fichierTemp.getName().endsWith("avi"))) {
//								listeFichier.add(fichierTemp);
//							}
//						}
//					}
//				}
//				listeDirectory.remove(0);
//			}

			for(File fichierTemp : fichiers){
				if(fichierTemp.getPath().contains("_")) {
					fichierTemp.renameTo(new File(fichierTemp.getPath().replaceAll("_", "'")));
				}
			}
		}

		catch (

				Exception ex) {
			ex.printStackTrace();
		}
	}

	public static boolean ReplaceConvertFiles(String chemin){
		long ms = System.currentTimeMillis();
		System.out.println("debut " + ms);
		try {
			File base = new File(chemin);
			//File base = new File("d://te/film");

			File[] fichiers = base.listFiles();

			for(File fichierTemp : fichiers){
				if(fichierTemp.getPath().contains("_")) {
					fichierTemp.renameTo(new File(fichierTemp.getPath().replaceAll("_", "'")));
				}
			}
		}

		catch (

				Exception ex) {
			ex.printStackTrace();
		}
		return true;
	}

	public static void main(String[] args) {
		ReplaceConvertFiles del = new ReplaceConvertFiles();
	}

}
