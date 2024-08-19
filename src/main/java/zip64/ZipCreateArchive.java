package zip64;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;

public class ZipCreateArchive {

	public ZipCreateArchive() {
		try {
			File base = new File("z://test/test");
			//File base = new File("d://te/film");

			File[] fichiers = base.listFiles();

			HashSet<File> listeFichier = new HashSet<File>();
			ArrayList<File> listeDirectory = new ArrayList<File>();

			for (File fichier : fichiers) {
				if (fichier.isDirectory()) {
					listeDirectory.add(fichier);
				} else if (fichier.isFile()) {
					if ((fichier.getName().endsWith("mkv") || fichier.getName().endsWith("mp4")
							|| fichier.getName().endsWith("avi"))) {
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
							listeDirectory.add(fichierTemp);
						} else if (fichierTemp.isFile()) {
							if ((fichierTemp.getName().endsWith("mkv") || fichierTemp.getName().endsWith("mp4")
									|| fichierTemp.getName().endsWith("avi"))) {
								listeFichier.add(fichierTemp);
							}
						}
					}
				}
				listeDirectory.remove(0);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

/*	public void compress(){
	Collection<File> filesToArchive = ...
			try (ArchiveOutputStream o = ... create the stream for your format ...) {
				for (File f : filesToArchive) {
					// maybe skip directories for formats like AR that don't store directories
					ArchiveEntry entry = o.createArchiveEntry(f, entryName(f));
					// potentially add more flags to entry
					o.putArchiveEntry(entry);
					if (f.isFile()) {
						try (InputStream i = Files.newInputStream(f.toPath())) {
							IOUtils.copy(i, o);
						}
					}
					o.closeArchiveEntry();
				}
			}*/


			public static void main (String[]args){
				ZipCreateArchive del = new ZipCreateArchive();
			}

}
