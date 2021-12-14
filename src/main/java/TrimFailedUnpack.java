import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;

public class TrimFailedUnpack {
	static String path2;

	public TrimFailedUnpack() {
		// Dossier a supprimer
		File base = new File("Z://test/film");

		File[] fichiers = base.listFiles();

		for(File fichier : fichiers){
			if(fichier.isDirectory()&&(fichier.getName().toLowerCase().contains("failed")||fichier.getName().toLowerCase().contains("unpack"))){
				Path p2 = Paths.get(fichier.getPath());
				Path folder2 = p2.getParent();

				String path2 = folder2.toString();
				String chemin2 = path2.replaceAll("\\\\", "/");

				String name = fichier.getName().substring(8,fichier.getName().length());

				int compteur = 0;
				if (new File(chemin2 + "/" + name).exists()) {
					boolean test = false;
					boolean testFile = false;
					while(!test){
						testFile = fichier.renameTo(new File(chemin2 + "/" + name + "_" + compteur));
						if(testFile){
							test = true;
						} else {
							compteur++;
						}
					}
				} else {
					fichier.renameTo(new File(chemin2 + "/" + name));
				}
			}
		}

	}

	public static void main(String[] args) {
		TrimFailedUnpack epguides = new TrimFailedUnpack();

	}

}
