import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Properties;

public class RenameBySubject {
	Properties appProps;
	String rootPath;
	Connection conn;

	public RenameBySubject() {
		File base = new File("z://temp/watchlist");

		File[] fichiers = base.listFiles();

		HashSet<File> listeFichier = new HashSet<File>();
		ArrayList<File> listeDirectory = new ArrayList<File>();

		for(File fichier : fichiers) {
			if(fichier.isDirectory()) {
				listeDirectory.add(fichier);
			} else {
				listeFichier.add(fichier);
			}
		}

		while(listeDirectory.size()>0) {
			File fichier = listeDirectory.get(0);

			File[] fichierListe = fichier.listFiles();

			for(File fichierTemp : fichierListe) {
				if(fichierTemp.isDirectory()) {
					listeDirectory.add(fichierTemp);
				}else { 
					listeFichier.add(fichierTemp);
				}
			}
			listeDirectory.remove(0);
		}

		for(File fichierTemp: listeFichier) {
			//System.out.println(fichierTemp.getName());
			if(fichierTemp.isFile()&&(fichierTemp.getName().endsWith(".mkv")||fichierTemp.getName().endsWith(".mp4")||fichierTemp.getName().endsWith(".avi"))) {

				Path p2 = Paths.get(fichierTemp.getPath());
				Path folder2 = p2.getParent();
				String path2 = folder2.toString();
				path2 = path2.replaceAll("\\\\", "/");
				
				String directory = path2.substring(path2.lastIndexOf("/")+1, path2.length());
				
				fichierTemp.renameTo(new File("z://temp/watchlist"+"/"+directory));
			
			}
		}

	}

	public static void main(String[] args) {
		RenameBySubject populate = new RenameBySubject();

	}

}
