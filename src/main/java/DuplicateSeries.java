import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DuplicateSeries {

	public DuplicateSeries() {
		File base = new File("z://Temp/Main/MKV Done");

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

		int compteur = 0;
		HashSet<File> listeFileDelete = new HashSet<File>();
		HashSet<File> listeFileAll = new HashSet<File>();

		for (File fichier1 : listeFichier) {
			compteur++;
			System.out.println(compteur + " / " + listeFichier.size());
			String fichierName = fichier1.getName();
			String[] fichierTemp = fichierName.split(" - ");
			String fichierPart1 = fichierTemp[0];

			Pattern pattern = Pattern.compile("S[0-9]*E[0-9]*");
			Matcher matcher = pattern.matcher(fichier1.getName());
			String episode = "";
			try {
				while (matcher.find()) {
					episode = matcher.group(0);
				}
			} catch (Exception e) {

			}

			File fichierSelected = fichier1;
			long taille = fichier1.length();
			for (File fichier2 : listeFichier) {
				String[] fichierTemp3 = fichier2.getName().split(" - ");
				String fichierPart2 = fichierTemp3[0];

				// System.out.println(fichierPart1 + " " + fichierPart2);
				if (fichierPart1.equalsIgnoreCase(fichierPart2)) {
					Pattern pattern2 = Pattern.compile("S[0-9]*E[0-9]*");
					Matcher matcher2 = pattern2.matcher(fichier2.getName());
					String episode2 = "";
					try {
						while (matcher2.find()) {
							episode2 = matcher2.group(0);
						}
					} catch (Exception e) {

					}

					if (episode.equalsIgnoreCase(episode2)) {
						if (fichier2.length() > taille) {
							fichierSelected = fichier2;
							taille = fichier2.length();
						}
					}
				}
			}
			for (File fichierTemp2 : listeFichier) {
				String[] fichierTemp4 = fichierSelected.getName().split(" - ");
				String fichierPart3 = fichierTemp4[0];
				if (fichierPart1.equalsIgnoreCase(fichierPart3)) {
					listeFileAll.add(fichierTemp2);
				}
			}
		}
		listeFileAll.forEach(res -> {
			try {
				res.renameTo(new File(res.getName() + ".old"));
			} catch (Exception e) {
				e.printStackTrace();
			}
			// System.out.println(res.getName());
		});
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DuplicateSeries du = new DuplicateSeries();
	}

}
