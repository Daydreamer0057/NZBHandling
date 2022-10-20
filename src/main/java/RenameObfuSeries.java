import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;

public class RenameObfuSeries {

	public RenameObfuSeries() {
		String pathPrincipal = "z://test/film a traiter";
		File base = new File(pathPrincipal);
//		File base = new File("F://The Mandalorian");

		File[] fichiers = base.listFiles();

		HashSet<File> listeFichier = new HashSet<File>();
		ArrayList<File> listeDirectory = new ArrayList<File>();
		// listeDirectory.add(new File("z://test/film"));

		for (File fichier : fichiers) {
			System.out.println("Files "+listeFichier.size()+"    Directory "+listeDirectory.size());
			if (fichier.isDirectory()) {
				listeDirectory.add(fichier);
			} else {
				if (fichier.getName().endsWith(".mkv") || fichier.getName().endsWith(".mp4")
						|| fichier.getName().endsWith(".avi")&&fichier.listFiles().length<=1) {
					listeFichier.add(fichier);
				}
			}
		}

		while (listeDirectory.size() > 0) {
			System.out.println("Files "+listeFichier.size()+"    Directory "+listeDirectory.size());
			File fichier = listeDirectory.get(0);

			File[] fichierListe = fichier.listFiles();

			for (File fichierTemp : fichierListe) {
				if (fichierTemp.isDirectory()) {
					listeDirectory.add(fichierTemp);
				} else {
					if (fichierTemp.getName().endsWith(".mkv") || fichierTemp.getName().endsWith(".mp4")
							|| fichierTemp.getName().endsWith(".avi")&&fichierTemp.listFiles().length<=1) {
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

			if(fichierObfu.isFile()&&!path2.toLowerCase().equals(pathPrincipal.toLowerCase())){
				try {
//					System.out.println("1 "+fichierObfu.getPath()+"    "+pathPrincipal.length()+"    "+ path2.length());
//					System.out.println("path2 "+path2);
//					String path3 = path2.substring(pathPrincipal.length(), path2.length());

//					System.out.println("2 "+path3);
					String path4 = path2.substring(pathPrincipal.length(),path2.length());
					fichierObfu.renameTo(new File(path2 + "/" + path4 + "." + FilenameUtils.getExtension(fichierObfu.getName())));

				} catch(StringIndexOutOfBoundsException ex){
					ex.printStackTrace();
				}
			}

		}
	}

	public static void main(String[] args) {
		RenameObfuSeries t = new RenameObfuSeries();
	}

}
