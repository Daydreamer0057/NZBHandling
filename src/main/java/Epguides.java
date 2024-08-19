import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Properties;

public class Epguides {
	Properties appProps;
	String rootPath;

	public Epguides() {
		int compteurFinal = 0;
		try {

			File base = new File("e://Epguides");

			File[] fichiers = base.listFiles();

			HashSet<File> listeFichier = new HashSet<File>();
			ArrayList<File> listeDirectory = new ArrayList<File>();

			for (File fichierTemp : fichiers) {
				if (fichierTemp.isDirectory()) {
					listeDirectory.add(fichierTemp);
				} else {
					if (fichierTemp.getPath().toLowerCase().contains("html")) {
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
						if (fichierTemp.getPath().toLowerCase().contains("html")
						) {
							listeFichier.add(fichierTemp);
						}
					}
				}
				listeDirectory.remove(0);
			}

			int compteur = 0;

			for (File fichier : listeFichier) {
				boolean foundEpisode = false;
				boolean found2022 = false;
				boolean testNetflix = false;
				boolean testYear = false;
				boolean testSeason = false;
				FileReader fr = new FileReader(fichier);
				BufferedReader br = new BufferedReader(fr);

				String line = "";

				while (line != null) {
					line = br.readLine();
					if (line != null && ((line.toLowerCase().contains("crime"))||(line.toLowerCase().contains("reality"))||(line.toLowerCase().contains("comedy"))||(line.toLowerCase().contains("game")))) {
//					if (line != null && (line.toLowerCase().contains("comedy"))) {
						foundEpisode = true;

					}
//					if (line != null && ((line.toLowerCase().contains("30 min")||line.toLowerCase().contains("45 min")))) {
					if (line != null && ((line.toLowerCase().contains("30 min")))) {
						testYear = true;
					}
					if(line != null && !line.toLowerCase().contains ("updated")) {
						line = br.readLine();
					}
					if (line != null && ((line.toLowerCase().contains("start date")||line.toLowerCase().contains("end date"))) && ((line.toLowerCase().contains("2000")) || (line.toLowerCase().contains("2001")) || (line.toLowerCase().contains("2002")) || (line.toLowerCase().contains("2003")) || (line.toLowerCase().contains("2004"))
							|| (line.toLowerCase().contains("2005")) || (line.toLowerCase().contains("2006")) || (line.toLowerCase().contains("2007")) || (line.toLowerCase().contains("2008")) || (line.toLowerCase().contains("2009"))
							|| (line.toLowerCase().contains("2010")) || (line.toLowerCase().contains("2011")) || (line.toLowerCase().contains("2012")) || (line.toLowerCase().contains("2013") || (line.toLowerCase().contains("2014"))
							|| (line.toLowerCase().contains("2015")) || (line.toLowerCase().contains("2016")) || (line.toLowerCase().contains("2017")) || (line.toLowerCase().contains("2018")) || (line.toLowerCase().contains("2019"))
							|| (line.toLowerCase().contains("2020")) || (line.toLowerCase().contains("2021")) || (line.toLowerCase().contains("2022")) || (line.toLowerCase().contains("2023")|| (line.toLowerCase().contains("2024")))))) {
						found2022 = true;
					}
					if(line != null && line.toLowerCase().contains("season 2")){
						testSeason = true;
					}
				}
				br.close();
				fr.close();

				System.out.println("" + compteur + " / " + listeFichier.size());
				compteur++;
//				System.out.println(testNetflix + "    " + testYear);
				// if (!testNetflix || !testYear || found30Minutes) {
				if (testYear||foundEpisode||!found2022) {
//					System.out.println("Serie "+fichier.getPath()+"    "+line);
					compteurFinal++;
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
			Epguides ep = new Epguides();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}