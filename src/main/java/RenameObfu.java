import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;

public class RenameObfu {

	public RenameObfu() {
		String pathPrincipal = "z:/test/film a traiter";
//		String pathPrincipal = "w:/download 2";
//		String pathPrincipal = chemin;
		File base = new File(pathPrincipal);
//		File base = new File("F://The Mandalorian");

		HashSet<File> listeFichier = FileDirParcours.getParcours(pathPrincipal,new String[]{"mp4","avi","mkv"});
		try {
//				PrintWriter pw = new PrintWriter("c://temp/log_renameobfu.txt");

			for (File fichierObfu : listeFichier) {
				Path p2 = Paths.get(fichierObfu.getPath());
				Path folder2 = p2.getParent();
				String path2 = folder2.toString();
				path2 = path2.replaceAll("\\\\", "/");

				String pathRename = path2.replace(pathPrincipal,"");
				pathRename = pathRename.substring(1,pathRename.length());

				File fichierRename = new File(path2 + "/" + pathRename + "." + FilenameUtils.getExtension(fichierObfu.getName()));
				fichierObfu.renameTo(fichierRename);

			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void main(String[] args) {
		RenameObfu t = new RenameObfu();
	}

}
