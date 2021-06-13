import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RenameObfu {

	public RenameObfu() {
		String pathPrincipal = "z://test/main";
		File base = new File(pathPrincipal);
//		File base = new File("F://The Mandalorian");

		File[] fichiers = base.listFiles();

		HashSet<File> listeFichier = new HashSet<File>();
		ArrayList<File> listeDirectory = new ArrayList<File>();
		// listeDirectory.add(new File("z://test/film"));

		for (File fichier : fichiers) {
			if (fichier.isDirectory()) {
				listeDirectory.add(fichier);
			} else {
				if (fichier.getName().endsWith(".mkv") || fichier.getName().endsWith(".mp4")
						|| fichier.getName().endsWith(".avi")) {
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
					if (fichierTemp.getName().endsWith(".mkv") || fichierTemp.getName().endsWith(".mp4")
							|| fichierTemp.getName().endsWith(".avi")) {
						listeFichier.add(fichierTemp);
					}
				}
			}
			listeDirectory.remove(0);
		}

		for(File fichierObfu : listeFichier) {
			Path p2 = Paths.get(fichierObfu.getPath());
			Path folder2 = p2.getParent();
			String path2 = folder2.toString();
			path2 = path2.replaceAll("\\\\", "/");

			if(fichierObfu.isFile()&&!path2.toLowerCase().equals(pathPrincipal)){
				String path3 = path2.substring(pathPrincipal.length(),path2.length());
				fichierObfu.renameTo(new File(path2+"/"+path3+"."+ FilenameUtils.getExtension(fichierObfu.getName())));
			}

		}
	}

	public static void main(String[] args) {
		RenameObfu t = new RenameObfu();
	}

}
