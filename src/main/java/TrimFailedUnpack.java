import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TrimFailedUnpack {
	static String path2;

	public TrimFailedUnpack(String chemin) {
		chemin = "v://d movies";
		// Dossier a supprimer
		File base = new File(chemin);

		File[] fichiers = base.listFiles();

		int compteurFinal = 0;
		for(File fichier : fichiers){
			System.out.println("compteur "+compteurFinal+" / "+fichiers.length);
			compteurFinal++;

			if(fichier.isDirectory()&&(fichier.getName().toLowerCase().contains("failed")||fichier.getName().toLowerCase().contains("unpack"))){
//				if(fichier.isFile()&&(fichier.getName().toLowerCase().contains("failed")||fichier.getName().toLowerCase().contains("unpack"))){
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
					System.out.println(chemin2 + "/" + name+"    "+fichier.getName());
					if(name.toLowerCase().contains("(Comedy)")){
						name = name.replace("(","");
						name = name.replace(")","");
					}
					fichier.renameTo(new File(chemin2 + "/" + name));
				}
			}
		}
//		RenameObfu r = new RenameObfu(chemin);

	}

	public static void main(String[] args) {
		TrimFailedUnpack epguides = new TrimFailedUnpack("Z://test/film a traiter");

	}

}
