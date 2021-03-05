
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Steam {
	Properties appProps;
	String rootPath;

	public Steam() {
		try {

			File base = new File("f://steam");

			File[] fichiers = base.listFiles();

			HashSet<File> listeFichier = new HashSet<File>();
			ArrayList<File> listeDirectory = new ArrayList<File>();

			for (File fichierTemp : fichiers) {
				if (fichierTemp.isDirectory()) {
					listeDirectory.add(fichierTemp);
				} else {
					if (fichierTemp.getName().contains("html") || fichierTemp.getName().contains("htm")) {
						listeFichier.add(fichierTemp);
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
						if (fichierTemp.getName().contains("html")
								|| fichierTemp.getName().contains("htm")) {
							listeFichier.add(fichierTemp);
						}
					}
				}
				listeDirectory.remove(0);
			}

			int compteur = 0;

			for (File fichier : listeFichier) {
				boolean foundEpisode = false;
				boolean found30Minutes = false;
				boolean testNetflix = true;
				boolean testYear = false;
				FileReader fr = new FileReader(fichier);
				BufferedReader br = new BufferedReader(fr);

				String line = "";

				while (line != null) {
					line = br.readLine();
					// if (line != null && (line.contains("aired from") && (line.contains("2019"))))
					// {
					// foundEpisode = true;
					// }
					// if (line != null && (line.contains("2018")) && !line.contains("<em>")
					// && !line.contains("Text copyright")) {
					// if (line.contains("Start date") || line.contains("End date")) {
					// testYear = true;
					// }
					// }
					GregorianCalendar gc = new GregorianCalendar();
					gc.setTime(new Date(System.currentTimeMillis()));
					if (line != null && (line.contains(gc.get(Calendar.DAY_OF_MONTH-2)+" "+gc.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH ).substring(0,3)+", "+gc.get(Calendar.YEAR)))) {
						testNetflix = false;
					}
				}
				br.close();
				fr.close();

				System.out.println("" + compteur + " / " + listeFichier.size());
				compteur++;
				System.out.println(testNetflix + "    " + testYear);
				// if (!testNetflix || !testYear || found30Minutes) {
				if (testNetflix) {
					fichier.delete();
				}
			}
		} catch (IOException ioex) {
			System.out.println(ioex);
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			Steam ep = new Steam();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}