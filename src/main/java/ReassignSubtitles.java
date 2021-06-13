import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.StringTokenizer;

public class ReassignSubtitles {

	public ReassignSubtitles() {
		try {
			File baseSub = new File("z://film/new");

			File[] fichiersSub = baseSub.listFiles();

			HashSet<File> listeFichierSub = new HashSet<File>();
			ArrayList<File> listeDirectorySub = new ArrayList<File>();
			// listeDirectory.add(new File("z://test/film"));

			for (File fichier : fichiersSub) {
				if (fichier.isDirectory()) {
					listeDirectorySub.add(fichier);
				} else {
					if (fichier.getName().endsWith("srt")) {
						listeFichierSub.add(fichier);
					}
				}
			}

			while (listeDirectorySub.size() > 0) {
				//System.out.println("Size "+listeDirectory.size());
				File fichier = listeDirectorySub.get(0);

				File[] fichierListe = fichier.listFiles();

				for (File fichierTemp : fichierListe) {
					if (fichierTemp.isDirectory()) {
						listeDirectorySub.add(fichierTemp);
					} else {
						if (fichierTemp.getName().endsWith("srt")) {
							listeFichierSub.add(fichierTemp);
						}
					}
				}
				listeDirectorySub.remove(0);
			}

			File baseSeries = new File("z://film/new");

			File[] fichiers = baseSeries.listFiles();

			HashSet<File> listeFichier = new HashSet<File>();
			ArrayList<File> listeDirectory = new ArrayList<File>();
			// listeDirectory.add(new File("z://test/film"));

			for (File fichier : fichiers) {
				if (fichier.isDirectory()) {
					listeDirectory.add(fichier);
				} else {
					if (fichier.getName().endsWith("mkv") || fichier.getName().endsWith("mp4")
							|| fichier.getName().endsWith("avi")) {
						listeFichier.add(fichier);
					}
				}
			}

			while (listeDirectory.size() > 0) {
				//System.out.println("Size "+listeDirectory.size());
				File fichier = listeDirectory.get(0);

				File[] fichierListe = fichier.listFiles();

				for (File fichierTemp : fichierListe) {
					if (fichierTemp.isDirectory()) {
						listeDirectory.add(fichierTemp);
					} else {
						if (fichierTemp.getName().endsWith("mkv") || fichierTemp.getName().endsWith("mp4")
								|| fichierTemp.getName().endsWith("avi")) {
							listeFichier.add(fichierTemp);
						}
					}
				}
				listeDirectory.remove(0);
			}

			int compteur = 0;
			int compteurMax = 0;
			for (File fichierFilm : listeFichier) {
				//if(fichierFilm.getName().toLowerCase().contains("ad astra")){
//					System.out.println("test");
//				}
				//System.out.println(compteur + " / " + listeFichier.size());
				compteur++;
				int index = fichierFilm.getName().indexOf('(') - 1;
				if (index >= 0) {
					System.out.println(fichierFilm.getName()+"    index "+index);
					String name = fichierFilm.getName().substring(0, index).trim();
					String year = "";
					try {
						year = fichierFilm.getName().substring(index + 2, index + 6);
					} catch (Exception ex) {
						ex.printStackTrace();
					}

					for (File fichierSub : listeFichierSub) {
						int indexSub = fichierSub.getName().indexOf('(') - 1;
						if (indexSub >= 0) {
							//System.out.println(fichierSub.getName()+"    index "+indexSub);
							String nameSub = "";
							String yearSub = "";
							try {
								nameSub = fichierSub.getName().substring(0, indexSub).trim();
								yearSub = fichierSub.getName().substring(indexSub + 2, indexSub + 6);
							} catch (Exception ex) {
								ex.printStackTrace();
								//System.out.println(fichierSub.getPath()+"    index "+index);
							}
							//System.out.println("year "+year+"    yearsub "+yearSub+"    namesub "+nameSub+"    name "+name);
							if(year.equals(yearSub)&&nameSub.equals(name)){
								System.out.println("modified");
								fichierSub.renameTo(new File(FilenameUtils.removeExtension(fichierFilm.getPath())+".srt"));
								break;
							}
						}
					}
				}
			}
			//System.out.println("compteur "+compteur);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void main(String[] args) {
		ReassignSubtitles populate = new ReassignSubtitles();

	}

}
