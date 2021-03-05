import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;

public class RenameNZB {

	public RenameNZB() {
		File base = new File("c://temp/nzb/nzb");

		File[] fichiers = base.listFiles();

		for(File fichier : fichiers) {
			if(fichier.getName().contains(".queued")) {
				fichier.renameTo(new File(fichier.getPath().substring(0,(fichier.getPath().length()-7))));
			}
		}
	}

	public static void main(String[] args) {
		RenameNZB t = new RenameNZB();
	}

}
