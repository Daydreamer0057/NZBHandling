
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;

public class MoveSeries {

	public MoveSeries() {
		boolean testSerie = false;
		File file = new File("z://test/main");

		File base = new File("z:///series");

		File[] fichiers = base.listFiles();

		HashSet<File> listeFichier = new HashSet<File>();
		ArrayList<File> listeDirectory = new ArrayList<File>();

		for (File fichier : fichiers) {
			if (fichier.isDirectory()) {
				listeDirectory.add(fichier);
			} else {
				listeFichier.add(fichier);
			}
		}

		while (listeDirectory.size() > 0) {
			File fichier = listeDirectory.get(0);

			File[] fichierListe = fichier.listFiles();

			for (File fichierTemp : fichierListe) {
				if (fichierTemp.isDirectory()) {
					listeDirectory.add(fichierTemp);
				} else {
					listeFichier.add(fichierTemp);
				}
			}
			listeDirectory.remove(0);
		}

		File[] listNzb = file.listFiles();

		HashSet<File> listFile = new HashSet<File>();
		ArrayList<File> listDirectory = new ArrayList<File>();

		for (File fichier : listNzb) {
			if (fichier.isDirectory()) {
				listDirectory.add(fichier);
			} else {
				listFile.add(fichier);
			}
		}

		while (listDirectory.size() > 0) {
			File fichier = listDirectory.get(0);

			File[] fichierListe = fichier.listFiles();

			for (File fichierTemp : fichierListe) {
				if (fichierTemp.isDirectory()) {
					listDirectory.add(fichierTemp);
				} else {
					listFile.add(fichierTemp);
				}
			}
			listDirectory.remove(0);
		}

		HashSet<File> listFinal = new HashSet<File>();
		for (File fichierTemp : listFile) {
			listFinal.add(fichierTemp);
		}

		int compteur = 0;
		for (File fichierNzb : listFile) {
			System.out.println("fichierNzb " + compteur + " / " + listFile.size());
			compteur++;
			String episode = episode(fichierNzb);
			String name = name(fichierNzb);
			for (File fichierSeries : listeFichier) {
				String episodeSeries = episode(fichierSeries);
				String nameSeries = name(fichierSeries);
				if (!episode.equals("") && !name.equals("") && !episodeSeries.equals("") && !nameSeries.equals("")
						&& episode.equalsIgnoreCase(episodeSeries) && (name.equalsIgnoreCase(nameSeries))) {
					listFinal.remove(fichierNzb);
				}
			}
		}

		compteur = 0;
		for (File fichierTemp : listFinal) {
			System.out.println("Final " + compteur + " / " + listFinal.size());
			compteur++;
			try {
				FileUtils.copyFile(fichierTemp, new File("f://NZBFile MKV/Final/" + fichierTemp.getName()));
			} catch (IOException ioex) {
				ioex.printStackTrace();
			}
		}

	}

	public String episode(File fichier) {
		Pattern pattern = Pattern.compile("S[0-9]*E[0-9]*");
		Matcher matcher = pattern.matcher(fichier.getName());
		String episode = "";
		try {
			while (matcher.find()) {				episode = matcher.group(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		episode = episode.trim();

		return episode;
	}

	public String name(File fichier) {
		String name = "";

		StringTokenizer stk = new StringTokenizer(fichier.getName(), "-");

		if (stk.hasMoreTokens()) {
			name = stk.nextToken();
		}

		if (name.indexOf("(") != -1) {
			name = name.substring(0, name.indexOf("(") - 1);
		}

		name = name.trim();

		return name;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MoveSeries du = new MoveSeries();
	}

}
