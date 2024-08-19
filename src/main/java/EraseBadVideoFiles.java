import com.xuggle.xuggler.IContainer;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;

public class EraseBadVideoFiles {

	public EraseBadVideoFiles() {
		try {
			File base = new File("z://temp/convert");
//			File base = new File("z://film/france");

			File[] fichiers = base.listFiles();

			HashSet<File> listeFichier = new HashSet<File>();
			ArrayList<File> listeDirectory = new ArrayList<File>();

			for (File fichier : fichiers) {
				if (fichier.isDirectory()) {
					if (!fichier.getPath().toLowerCase().contains("retry")) {
						listeDirectory.add(fichier);
					}
				} else if (fichier.isFile()) {
					if ((!fichier.getName().endsWith("srt") && ((fichier.getName().endsWith("mkv") || fichier.getName().endsWith("mp4")
							|| fichier.getName().endsWith("avi"))))) {
						listeFichier.add(fichier);
					}
				}
			}

			while (listeDirectory.size() > 0) {
				File fichier = listeDirectory.get(0);

				File[] fichierListe = fichier.listFiles();

				if (fichierListe != null) {
					for (File fichierTemp : fichierListe) {
						if (fichierTemp.isDirectory()) {
							if (!fichierTemp.getPath().toLowerCase().contains("retry")) {
								listeDirectory.add(fichierTemp);
							}
						} else if (fichierTemp.isFile()) {
							if ((!fichierTemp.getName().endsWith("srt") && ((fichierTemp.getName().endsWith("mkv") || fichierTemp.getName().endsWith("mp4")
									|| fichierTemp.getName().endsWith("avi"))))) {
								listeFichier.add(fichierTemp);
							}
						}
					}
				}
				listeDirectory.remove(0);
			}

			int compteur = 0;
			for(File fichierTemp : listeFichier){
				System.out.println("Compteur "+compteur);
				compteur++;

				//private static final String filename = "FILE_PATH";
				IContainer container = IContainer.make();
				int result = container.open(fichierTemp.getPath(), IContainer.Type.READ, null);
				long duration = container.getDuration();
//				long fileSize = container.getFileSize();
				if(container.getDuration()<(600000)){
					System.out.println("Error "+fichierTemp.getPath());
				}
			}

		} catch(Exception ex){
			ex.printStackTrace();
		}
	}

	public static void main(String[] args) {
		EraseBadVideoFiles del = new EraseBadVideoFiles();
	}

}
