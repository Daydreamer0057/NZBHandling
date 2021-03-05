import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;

import org.apache.commons.io.FileUtils;

public class Delete {
	public Delete() {
		try {
			ArrayList<String> listExclude = new ArrayList<String>();
			listExclude.add("horizon zero dawn");
			listExclude.add("State of Decay 2 Juggernaut Edition");
			listExclude.add("Benchmark");
			listExclude.add("Hack And Slash");
			listExclude.add("Intellij");
			listExclude.add("Pinball FX3");
			listExclude.add("Pathfinder Kingmaker");
			listExclude.add("Solitaire");
			listExclude.add("Tower Defense");
			listExclude.add("Webstorm");
			listExclude.add("Wasteland 3");
			listExclude.add("GreedFall");
			listExclude.add("Control");
			listExclude.add("Metro Exodus");
			listExclude.add("State of Decay 2 Juggernaut Edition");
			listExclude.add("Ashen");
			listExclude.add("Star Renegades");

			HashSet<File> hashFile = new HashSet<File>();

			File base = new File("z://games/Action RPG");

			File[] fichiers = base.listFiles();

			HashSet<File> listeFichier = new HashSet<File>();
			ArrayList<File> listeDirectory = new ArrayList<File>();

			for (File fichier : fichiers) {
				File[] fichiers2 = fichier.listFiles();
				try {
					for (File fichierTemp : fichiers2) {
						if (fichierTemp.isDirectory() && !listExclude.contains(fichierTemp.getPath())) {
							listeDirectory.add(fichierTemp);
						}
					}
				} catch (NullPointerException e) {
					e.printStackTrace();
				}
			}

			while (listeDirectory.size() > 0) {
				// System.out.println("Size " + listeDirectory.size());
				File fichier = listeDirectory.get(0);

				File[] fichierListe = fichier.listFiles();

				try {
					// System.out.println(fichier.getPath() + " Nb fichiers " +
					// fichierListe.length);
					for (File fichierTemp : fichierListe) {
						// System.out.println("" + listExclude.contains(fichierTemp.getName()));
						// System.out.println(fichierTemp.getName());
						boolean testExclude = false;
						for (String lineTemp : listExclude) {
							if (fichierTemp.getPath().contains(lineTemp)) {
								testExclude = true;
							}
						}
						// System.out.println("" + testExclude);
						if (!testExclude && fichierTemp.isDirectory()) {
							listeDirectory.add(fichierTemp);
							hashFile.add(fichierTemp);
						}

					}
				} catch (NullPointerException e) {
					e.printStackTrace();
				}
				listeDirectory.remove(0);
			}
			for (File fichierFin : hashFile) {
				try {
					System.out.println(fichierFin.getPath());
					FileUtils.deleteDirectory(fichierFin);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Delete populate = new Delete();

	}

}
