import org.apache.commons.io.FileExistsException;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.*;

public class DeleteDuplicatesSeriesTotalFinal {
	public DeleteDuplicatesSeriesTotalFinal() {
		HashMap<String, Map> mapSeries = new HashMap<String, Map>();

		String pathNew = "qsdqgqsfwhfd";

		long ms = System.currentTimeMillis();
		System.out.println("debut " + ms);
		try {
			File base = new File("z://series");
			//File base = new File("d://film/new/Film 20200226");

			File[] fichiers = base.listFiles();

			HashSet<File> listeFichier = new HashSet<File>();
			ArrayList<File> listeDirectory = new ArrayList<File>();

			for (File fichier : fichiers) {
				if (fichier.isDirectory()) {
					listeDirectory.add(fichier);
				} else if (fichier.isFile()) {
					if ((fichier.getName().toLowerCase().endsWith("mkv") || fichier.getName().toLowerCase().endsWith("mp4")
							|| fichier.getName().toLowerCase().endsWith("avi")
							|| fichier.getName().toLowerCase().endsWith("m4v"))) {
						listeFichier.add(fichier);
					}
				}
			}

			while (listeDirectory.size() > 0) {
				File fichier = listeDirectory.get(0);

				File[] fichierListe = fichier.listFiles();

				if (fichierListe != null) {
					for (File fichierTemp : fichierListe) {
						if (fichierTemp.isDirectory()) {
							listeDirectory.add(fichierTemp);
						} else if (fichierTemp.isFile()) {
							if ((fichierTemp.getName().toLowerCase().endsWith("mkv") || fichierTemp.getName().toLowerCase().endsWith("mp4")
									|| fichierTemp.getName().toLowerCase().endsWith("avi")
									|| fichierTemp.getName().toLowerCase().endsWith("m4v"))) {
								listeFichier.add(fichierTemp);
							}
						}
					}
				}
				listeDirectory.remove(0);
			}

			int compteurTest = 0;
			for (File fichierTemp : listeFichier) {
//				if(fichierTemp.getName().toLowerCase().contains("succession")&&fichierTemp.getName().toLowerCase().contains("s01e08")){
//					System.out.println();
//				}
				String episode = "";
				String series = "";
				StringTokenizer stk = new StringTokenizer(fichierTemp.getName(), "-");
				if (stk.hasMoreTokens()) {
					series = stk.nextToken().toLowerCase().trim();
				}
				if (stk.hasMoreTokens()) {
					episode += stk.nextToken().trim();
				}
				episode = episode.toLowerCase();
				if (!episode.equals("") && episode != null) {
						ArrayList<File> listFile = null;
						try {
							listFile = (ArrayList<File>) mapSeries.get(series).get(episode);
						} catch (Exception ex) {

						}

						if (listFile == null) {
							listFile = new ArrayList<File>();
							listFile.add(fichierTemp);
							Map<String, ArrayList> listMap = mapSeries.get(series);
							if(listMap==null){
								listMap = new HashMap<>();
							}
							listMap.put(episode, listFile);
							mapSeries.put(series, listMap);
						} else {
							listFile.add(fichierTemp);
							Map<String, ArrayList> listMap = (Map<String, ArrayList>) mapSeries.get(series);
							listMap.put(episode, listFile);
							mapSeries.put(series, listMap);
						}
					}
			}


			if (mapSeries.size() > 0) {
				Set<String> setSeries = mapSeries.keySet();
				for (String lineSeries : setSeries) {
					Set<String> setEpisode = (Set<String>) mapSeries.get(lineSeries).keySet();
					for (String lineEpisode : setEpisode) {
						ArrayList<File> listFile2 = (ArrayList<File>) mapSeries.get(lineSeries).get(lineEpisode);
						if (listFile2.size() > 1) {
							File fichier = listFile2.get(0);
							long taille = 0;
							long tailleTemp = 0;
							boolean testFile = false;
							ArrayList<File> keepFile = new ArrayList<>();

							for (File fichierTemp : listFile2) {
								keepFile.add(fichierTemp);
							}

							Collections.sort(keepFile, new Comparator<File>() {
								@Override
								public int compare(File lhs, File rhs) {
									int retour = 0;
									if (lhs.getName().toLowerCase().contains("2160p")) {
										if (rhs.getName().toLowerCase().contains("2160p"))
											retour = lhs.length() > rhs.length() ? 1 : (lhs.length() < rhs.length()) ? -1 : 0;
										if (rhs.getName().toLowerCase().contains("1080p")) retour = -1;
										if (rhs.getName().toLowerCase().contains("720p")) retour = -1;
										if (rhs.getName().toLowerCase().contains("576p")) retour = -1;
										if (rhs.getName().toLowerCase().contains("480p")) retour = -1;
										if (rhs.getName().toLowerCase().contains("360p")) retour = -1;
										if (rhs.getName().toLowerCase().contains("240p")) retour = -1;
									}

									if (lhs.getName().toLowerCase().contains("1080p")) {
										if (rhs.getName().toLowerCase().contains("2160p"))
											retour = 1;
										if (rhs.getName().toLowerCase().contains("1080p"))
											retour = lhs.length() > rhs.length() ? 1 : (lhs.length() < rhs.length()) ? -1 : 0;
										if (rhs.getName().toLowerCase().contains("720p")) retour = -1;
										if (rhs.getName().toLowerCase().contains("576p")) retour = -1;
										if (rhs.getName().toLowerCase().contains("480p")) retour = -1;
										if (rhs.getName().toLowerCase().contains("360p")) retour = -1;
										if (rhs.getName().toLowerCase().contains("240p")) retour = -1;
									}
									if (lhs.getName().toLowerCase().contains("720p")) {
										if (rhs.getName().toLowerCase().contains("2160p")) retour = 1;
										if (rhs.getName().toLowerCase().contains("1080p")) retour = 1;
										if (rhs.getName().toLowerCase().contains("720p"))
											retour = lhs.length() > rhs.length() ? 1 : (lhs.length() < rhs.length()) ? -1 : 0;
										if (rhs.getName().toLowerCase().contains("576p")) retour = -1;
										if (rhs.getName().toLowerCase().contains("480p")) retour = -1;
										if (rhs.getName().toLowerCase().contains("360p")) retour = -1;
										if (rhs.getName().toLowerCase().contains("240p")) retour = -1;
									}

									if (lhs.getName().toLowerCase().contains("576p")) {
										if (rhs.getName().toLowerCase().contains("2160p")) retour = 1;
										if (rhs.getName().toLowerCase().contains("1080p")) retour = 1;
										if (rhs.getName().toLowerCase().contains("720p")) retour = 1;
										if (rhs.getName().toLowerCase().contains("576p"))
											retour = lhs.length() > rhs.length() ? 1 : (lhs.length() < rhs.length()) ? -1 : 0;
										if (rhs.getName().toLowerCase().contains("480p")) retour = -1;
										if (rhs.getName().toLowerCase().contains("360p")) retour = -1;
										if (rhs.getName().toLowerCase().contains("240p")) retour = -1;
									}

									if (lhs.getName().toLowerCase().contains("480p")) {
										if (rhs.getName().toLowerCase().contains("2160p")) retour = 1;
										if (rhs.getName().toLowerCase().contains("1080p")) retour = 1;
										if (rhs.getName().toLowerCase().contains("720p")) retour = 1;
										if (rhs.getName().toLowerCase().contains("576p")) retour = 1;
										if (rhs.getName().toLowerCase().contains("480p"))
											retour = lhs.length() > rhs.length() ? 1 : (lhs.length() < rhs.length()) ? -1 : 0;
										if (rhs.getName().toLowerCase().contains("360p")) retour = -1;
										if (rhs.getName().toLowerCase().contains("240p")) retour = -1;
									}

									if (lhs.getName().toLowerCase().contains("360p")) {
										if (rhs.getName().toLowerCase().contains("2160p")) retour = 1;
										if (rhs.getName().toLowerCase().contains("1080p")) retour = 1;
										if (rhs.getName().toLowerCase().contains("720p")) retour = 1;
										if (rhs.getName().toLowerCase().contains("576p")) retour = 1;
										if (rhs.getName().toLowerCase().contains("480p")) retour = 1;
										if (rhs.getName().toLowerCase().contains("360p"))
											retour = lhs.length() > rhs.length() ? 1 : (lhs.length() < rhs.length()) ? -1 : 0;
										if (rhs.getName().toLowerCase().contains("240p")) retour = -1;
									}

									if (lhs.getName().toLowerCase().contains("240p")) {
										if (rhs.getName().toLowerCase().contains("2160p")) retour = 1;
										if (rhs.getName().toLowerCase().contains("1080p")) retour = 1;
										if (rhs.getName().toLowerCase().contains("720p")) retour = 1;
										if (rhs.getName().toLowerCase().contains("576p")) retour = 1;
										if (rhs.getName().toLowerCase().contains("480p")) retour = 1;
										if (rhs.getName().toLowerCase().contains("360p")) retour = 1;
										if (rhs.getName().toLowerCase().contains("240p"))
											retour = lhs.length() > rhs.length() ? 1 : (lhs.length() < rhs.length()) ? -1 : 0;
									}
									if(retour==0) retour = -1;
									return retour;
								}
						});

							File fichierKeep = keepFile.get(0);
							keepFile.remove(0);

							for (File fichierTemp : keepFile) {
									System.out.println(fichierTemp.getName());
//									fichierTemp.delete();
								try {
									FileUtils.moveFileToDirectory(fichierTemp, new File("z://test/error"), false);
								} catch(FileExistsException fex){
									fex.printStackTrace();
								}
							}
						}
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void main(String[] args) {
		DeleteDuplicatesSeriesTotalFinal del = new DeleteDuplicatesSeriesTotalFinal();
	}
}
