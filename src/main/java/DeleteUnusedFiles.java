import java.io.File;
import java.util.HashSet;

public class DeleteUnusedFiles {
	static String path2;

	public DeleteUnusedFiles() {
		String chemin = "Z://test";
//		String chemin = "w://download";
		// Dossier a supprimer

		HashSet<File> listeFichier = FileDirParcours.getParcours(chemin,new String[]{"iso","m2ts"});
		long taille = 0L;
		for(File fichierTemp : listeFichier){
			if(!fichierTemp.getPath().toLowerCase().contains("games")) {
				System.out.println(fichierTemp.getPath());
				taille += fichierTemp.length();
			fichierTemp.delete();
			}
		}

		System.out.println("Taille gagnee "+(taille/1000000000L));

	}

	public static void main(String[] args) {
		DeleteUnusedFiles epguides = new DeleteUnusedFiles();

	}

}
