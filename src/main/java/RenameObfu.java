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
		File base = new File("z://test/main");
//		File base = new File("F://The Mandalorian");

		File[] fichiers = base.listFiles();

		HashSet<File> listeFichier = new HashSet<File>();
		ArrayList<File> listeDirectory = new ArrayList<File>();
		// listeDirectory.add(new File("z://test/film"));

		for (File fichier : fichiers) {
			if (fichier.isDirectory()) {
				listeDirectory.add(fichier);
			} else {
				if (fichier.getName().endsWith("mkv") || fichier.getName().endsWith("mp4")
						|| fichier.getName().endsWith("avi") || fichier.getName().endsWith("srt")) {
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
					if (fichierTemp.getName().endsWith("mkv") || fichierTemp.getName().endsWith("mp4")
							|| fichierTemp.getName().endsWith("avi")) {
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

			Pattern pattern = Pattern.compile("[sS][0-9]+[eE][0-9]+");
			Matcher matcher = pattern.matcher(fichierObfu.getName());
			boolean testMaj = matcher.find();

			if(!matcher.find()){
				fichierObfu.renameTo(new File(path2+"/"+path2.substring(path2.lastIndexOf("/")+1,path2.length())+ FilenameUtils.getExtension(fichierObfu.getName())));
			}

		}
	}

	public static void main(String[] args) {
		RenameObfu t = new RenameObfu();
	}

}
