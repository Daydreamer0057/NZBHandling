import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DeleteDuplicatesSeriesTotal {
	public DeleteDuplicatesSeriesTotal() {
		HashMap<String, Map> map240 = new HashMap<String, Map>();
		HashMap<String, Map> map360 = new HashMap<String, Map>();
		HashMap<String, Map> map480 = new HashMap<String, Map>();
		HashMap<String, Map> map576 = new HashMap<String, Map>();
		HashMap<String, Map> map720 = new HashMap<String, Map>();
		HashMap<String, Map> map1080 = new HashMap<String, Map>();
		HashMap<String, Map> map2160 = new HashMap<String, Map>();

		int compteur240 = 0;
		int compteur360 = 0;
		int compteur480 = 0;
		int compteur576 = 0;
		int compteur720 = 0;
		int compteur1080 = 0;
		int compteur2160 = 0;

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
					if ((fichier.getName().endsWith("mkv") || fichier.getName().endsWith("mp4")
							|| fichier.getName().endsWith("avi"))) {
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
							if ((fichierTemp.getName().endsWith("mkv") || fichierTemp.getName().endsWith("mp4")
									|| fichierTemp.getName().endsWith("avi"))) {
								listeFichier.add(fichierTemp);
							}
						}
					}
				}
				listeDirectory.remove(0);
			}

			for (File fichierTemp : listeFichier) {
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
					if (fichierTemp.getName().toLowerCase().contains("720p")) {
//						if(fichierTemp.getName().toLowerCase().contains("arrow")&&episode.toLowerCase().contains("s02e06")){
//							System.out.println("test");
//						}
						compteur720++;
						ArrayList<File> listFile = null;
						try {
							listFile = (ArrayList<File>) map720.get(series).get(episode);
						} catch (Exception ex) {

						}

						if (listFile == null) {
							listFile = new ArrayList<File>();
							listFile.add(fichierTemp);
							Map<String, ArrayList> listMap = map720.get(series);
							if(listMap==null){
								listMap = new HashMap<>();
							}
							listMap.put(episode, listFile);
							map720.put(series, listMap);
						} else {
							listFile.add(fichierTemp);
							Map<String, ArrayList> listMap = (Map<String, ArrayList>) map720.get(series);
							listMap.put(episode, listFile);
							map720.put(series, listMap);
						}
					}

					if (fichierTemp.getName().toLowerCase().contains("576p")) {
						compteur576++;
						ArrayList<File> listFile = null;
						try {
							listFile = (ArrayList<File>) map576.get(series).get(episode);
						} catch (Exception e) {
							
						}

						if (listFile == null) {
							listFile = new ArrayList<File>();
							listFile.add(fichierTemp);
							Map<String, ArrayList> listMap = map576.get(series);
							if(listMap==null){
								listMap = new HashMap<>();
							}
							listMap.put(episode, listFile);
							map576.put(series, listMap);
						} else {
							listFile.add(fichierTemp);
							Map<String, ArrayList> listMap = (Map<String, ArrayList>) map576.get(series);
							listMap.put(episode, listFile);
							map576.put(series, listMap);
						}
					}

					if (fichierTemp.getName().toLowerCase().contains("480p")) {
						compteur480++;
						ArrayList<File> listFile = null;
						try {
							listFile = (ArrayList<File>) map480.get(series).get(episode);
						} catch (Exception e) {
							
						}

						if (listFile == null) {
							listFile = new ArrayList<File>();
							listFile.add(fichierTemp);
							Map<String, ArrayList> listMap = new HashMap<>();
							if(listMap==null){
								listMap = new HashMap<>();
							}
							listMap.put(episode, listFile);
							map480.put(series, listMap);
						} else {
							listFile.add(fichierTemp);
							Map<String, ArrayList> listMap = (Map<String, ArrayList>) map480.get(series);
							listMap.put(episode, listFile);
							map480.put(series, listMap);
						}
					}

					if (fichierTemp.getName().toLowerCase().contains("360p")) {
						compteur360++;
						ArrayList<File> listFile = null;
						try {
							listFile = (ArrayList<File>) map360.get(series).get(episode);
						} catch (Exception e) {
							
						}

						if (listFile == null) {
							listFile = new ArrayList<File>();
							listFile.add(fichierTemp);
							Map<String, ArrayList> listMap = new HashMap<>();
							if(listMap==null){
								listMap = new HashMap<>();
							}
							listMap.put(episode, listFile);
							map360.put(series, listMap);
						} else {
							listFile.add(fichierTemp);
							Map<String, ArrayList> listMap = (Map<String, ArrayList>) map360.get(series);
							listMap.put(episode, listFile);
							map360.put(series, listMap);
						}
					}

					if (fichierTemp.getName().toLowerCase().contains("240p")) {
						compteur240++;
						ArrayList<File> listFile = null;
						try {
							listFile = (ArrayList<File>) map240.get(series).get(episode);
						} catch (Exception e) {
							
						}

						if (listFile == null) {
							listFile = new ArrayList<File>();
							listFile.add(fichierTemp);
							Map<String, ArrayList> listMap = new HashMap<>();
							if(listMap==null){
								listMap = new HashMap<>();
							}
							listMap.put(episode, listFile);
							map240.put(series, listMap);
						} else {
							listFile.add(fichierTemp);
							Map<String, ArrayList> listMap = (Map<String, ArrayList>) map240.get(series);
							listMap.put(episode, listFile);
							map240.put(series, listMap);
						}
					}

					if (fichierTemp.getName().toLowerCase().contains("1080p")) {
						compteur1080++;
						ArrayList<File> listFile = null;
						try {
							listFile = (ArrayList<File>) map1080.get(series).get(episode);
						} catch (Exception e) {
							
						}

						if (listFile == null) {
							listFile = new ArrayList<File>();
							listFile.add(fichierTemp);
							Map<String, ArrayList> listMap = new HashMap<>();
							if(listMap==null){
								listMap = new HashMap<>();
							}
							listMap.put(episode, listFile);
							map1080.put(series, listMap);
						} else {
							listFile.add(fichierTemp);
							Map<String, ArrayList> listMap = (Map<String, ArrayList>) map1080.get(series);
							listMap.put(episode, listFile);
							map1080.put(series, listMap);
						}
					}

					if (fichierTemp.getName().toLowerCase().contains("2160p")) {
						compteur2160++;
						ArrayList<File> listFile = null;
						try {
							listFile = (ArrayList<File>) map2160.get(series).get(episode);
						} catch (Exception e) {
							
						}

						if (listFile == null) {
							listFile = new ArrayList<File>();
							listFile.add(fichierTemp);
							Map<String, ArrayList> listMap = new HashMap<>();
							if(listMap==null){
								listMap = new HashMap<>();
							}
							listMap.put(episode, listFile);
							map2160.put(series, listMap);
						} else {
							listFile.add(fichierTemp);
							Map<String, ArrayList> listMap = (Map<String, ArrayList>) map2160.get(series);
							listMap.put(episode, listFile);
							map2160.put(series, listMap);
						}
					}
				}
			}


// ====================================2160
			if (map2160.size() > 0) {
				Set<String> setSeries = map2160.keySet();
				for (String lineSeries : setSeries) {
					Set<String> setEpisode = (Set<String>) map2160.get(lineSeries).keySet();
					for (String lineEpisode : setEpisode) {
						ArrayList<File> listFile2 = (ArrayList<File>) map2160.get(lineSeries).get(lineEpisode);
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
									// -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
									return lhs.length() > rhs.length() ? -1 : (lhs.length() < rhs.length()) ? 1 : 0;
								}
							});

							File fichierKeep = keepFile.get(0);
							keepFile.remove(0);

							for (File fichierTemp : keepFile) {
									System.out.println("test");
//									//fichierTemp.delete();;
								fichierTemp.delete();
							}
						}

						if (map1080.get(lineSeries)!=null && map1080.get(lineSeries)!=null && map1080.get(lineSeries).containsKey(lineEpisode)) {
							ArrayList<File> listDelete = (ArrayList<File>) map1080.get(lineSeries).get(lineEpisode);
							map1080.get(lineSeries).remove(lineEpisode);
							for (File fichierTemp : listDelete) {
								try {
									System.out.println(fichierTemp.getPath() + "    " + fichierTemp.length() + "    " + listFile2.size());
									fichierTemp.delete();
								} catch (Exception ex) {

								}
							}
						}

						if (map720.get(lineSeries)!=null && map720.get(lineSeries).containsKey(lineEpisode)) {
							ArrayList<File> listDelete = (ArrayList<File>) map720.get(lineSeries).get(lineEpisode);
							map720.get(lineSeries).remove(lineEpisode);
							for (File fichierTemp : listDelete) {
								try {
									System.out.println(fichierTemp.getPath() + "    " + fichierTemp.length() + "    " + listFile2.size());
									//fichierTemp.delete();;
									fichierTemp.delete();
								} catch (Exception ex) {

								}
							}
						}

						if (map576.get(lineSeries)!=null && map576.get(lineSeries).containsKey(lineEpisode)){
							ArrayList<File> listDelete = (ArrayList<File>) map576.get(lineSeries).get(lineEpisode);
							map576.get(lineSeries).remove(lineEpisode);
							for (File fichierTemp : listDelete) {
								try {
									System.out.println(fichierTemp.getPath() + "    " + fichierTemp.length() + "    " + listFile2.size());
									//fichierTemp.delete();;
									fichierTemp.delete();
								} catch (Exception ex) {

								}
							}
						}

						if (map480.get(lineSeries)!=null && map480.get(lineSeries).containsKey(lineEpisode)) {
							ArrayList<File> listDelete = (ArrayList<File>) map480.get(lineSeries).get(lineEpisode);
							map480.get(lineSeries).remove(lineEpisode);
							for (File fichierTemp : listDelete) {
								try {
									System.out.println(fichierTemp.getPath() + "    " + fichierTemp.length() + "    " + listFile2.size());
									//fichierTemp.delete();;
									fichierTemp.delete();
								} catch (Exception ex) {

								}
							}
						}

						if (map360.get(lineSeries)!=null && map360.get(lineSeries).containsKey(lineEpisode)) {
							ArrayList<File> listDelete = (ArrayList<File>) map360.get(lineSeries).get(lineEpisode);
							map360.get(lineSeries).remove(lineEpisode);
							for (File fichierTemp : listDelete) {
								try {
									System.out.println(fichierTemp.getPath() + "    " + fichierTemp.length() + "    " + listFile2.size());
									//fichierTemp.delete();;
									fichierTemp.delete();
								} catch (Exception ex) {

								}
							}
						}

						if (map240.get(lineSeries)!=null && map240.get(lineSeries).containsKey(lineEpisode)) {
							ArrayList<File> listDelete = (ArrayList<File>) map240.get(lineSeries).get(lineEpisode);
							map240.get(lineSeries).remove(lineEpisode);
							for (File fichierTemp : listDelete) {
								try {
									System.out.println(fichierTemp.getPath() + "    " + fichierTemp.length() + "    " + listFile2.size());
									//fichierTemp.delete();;
									fichierTemp.delete();
								} catch (Exception ex) {

								}
							}
						}

					}
				}
			}
			
// ====================================1080
			if (map1080.size() > 0) {
				Set<String> setSeries = map1080.keySet();
				for (String lineSeries : setSeries) {
					Set<String> setEpisode = (Set<String>) map1080.get(lineSeries).keySet();
					for (String lineEpisode : setEpisode) {
						ArrayList<File> listFile2 = (ArrayList<File>) map1080.get(lineSeries).get(lineEpisode);
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
									// -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
									return lhs.length() > rhs.length() ? -1 : (lhs.length() < rhs.length()) ? 1 : 0;
								}
							});

							File fichierKeep = keepFile.get(0);
							keepFile.remove(0);

							for (File fichierTemp : keepFile) {
								System.out.println("test");
//									//fichierTemp.delete();;
								fichierTemp.delete();
							}
						}

						if (map720.get(lineSeries)!=null && map720.get(lineSeries).containsKey(lineEpisode)) {
							ArrayList<File> listDelete = (ArrayList<File>) map720.get(lineSeries).get(lineEpisode);
							map720.get(lineSeries).remove(lineEpisode);
							for (File fichierTemp : listDelete) {
								try {
									System.out.println(fichierTemp.getPath() + "    " + fichierTemp.length() + "    " + listFile2.size());
									//fichierTemp.delete();;
									fichierTemp.delete();
								} catch (Exception ex) {

								}
							}
						}

						if (map576.get(lineSeries)!=null && map576.get(lineSeries).containsKey(lineEpisode)) {
							ArrayList<File> listDelete = (ArrayList<File>) map576.get(lineSeries).get(lineEpisode);
							map576.get(lineSeries).remove(lineEpisode);
							for (File fichierTemp : listDelete) {
								try {
									System.out.println(fichierTemp.getPath() + "    " + fichierTemp.length() + "    " + listFile2.size());
									//fichierTemp.delete();;
									fichierTemp.delete();
								} catch (Exception ex) {

								}
							}
						}

						if (map480.get(lineSeries)!=null && map480.get(lineSeries).containsKey(lineEpisode)) {
							ArrayList<File> listDelete = (ArrayList<File>) map480.get(lineSeries).get(lineEpisode);
							map480.get(lineSeries).remove(lineEpisode);
							for (File fichierTemp : listDelete) {
								try {
									System.out.println(fichierTemp.getPath() + "    " + fichierTemp.length() + "    " + listFile2.size());
									//fichierTemp.delete();;
									fichierTemp.delete();
								} catch (Exception ex) {

								}
							}
						}

						if (map360.get(lineSeries)!=null && map360.get(lineSeries).containsKey(lineEpisode)) {
							ArrayList<File> listDelete = (ArrayList<File>) map360.get(lineSeries).get(lineEpisode);
							map360.get(lineSeries).remove(lineEpisode);
							for (File fichierTemp : listDelete) {
								try {
									System.out.println(fichierTemp.getPath() + "    " + fichierTemp.length() + "    " + listFile2.size());
									//fichierTemp.delete();;
									fichierTemp.delete();
								} catch (Exception ex) {

								}
							}
						}

						if (map240.get(lineSeries)!=null && map240.get(lineSeries).containsKey(lineEpisode)) {
							ArrayList<File> listDelete = (ArrayList<File>) map240.get(lineSeries).get(lineEpisode);
							map240.get(lineSeries).remove(lineEpisode);
							for (File fichierTemp : listDelete) {
								try {
									System.out.println(fichierTemp.getPath() + "    " + fichierTemp.length() + "    " + listFile2.size());
									//fichierTemp.delete();;
									fichierTemp.delete();
								} catch (Exception ex) {

								}
							}
						}

					}
				}
			}

// ====================================720
			if (map720.size() > 0) {
				Set<String> setSeries = map720.keySet();
				for (String lineSeries : setSeries) {
					Set<String> setEpisode = (Set<String>) map720.get(lineSeries).keySet();
					for (String lineEpisode : setEpisode) {
						ArrayList<File> listFile2 = (ArrayList<File>) map720.get(lineSeries).get(lineEpisode);
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
									// -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
									return lhs.length() > rhs.length() ? -1 : (lhs.length() < rhs.length()) ? 1 : 0;
								}
							});

							File fichierKeep = keepFile.get(0);
							keepFile.remove(0);

							for (File fichierTemp : keepFile) {
								System.out.println("test");
//									//fichierTemp.delete();;
								fichierTemp.delete();
							}
						}

						if (map576.get(lineSeries)!=null && map576.get(lineSeries).containsKey(lineEpisode)) {
							ArrayList<File> listDelete = (ArrayList<File>) map576.get(lineSeries).get(lineEpisode);
							map576.get(lineSeries).remove(lineEpisode);
							for (File fichierTemp : listDelete) {
								try {
									System.out.println(fichierTemp.getPath() + "    " + fichierTemp.length() + "    " + listFile2.size());
									//fichierTemp.delete();;
									fichierTemp.delete();
								} catch (Exception ex) {

								}
							}
						}

						if (map480.get(lineSeries)!=null && map480.get(lineSeries).containsKey(lineEpisode)) {
							ArrayList<File> listDelete = (ArrayList<File>) map480.get(lineSeries).get(lineEpisode);
							map480.get(lineSeries).remove(lineEpisode);
							for (File fichierTemp : listDelete) {
								try {
									System.out.println(fichierTemp.getPath() + "    " + fichierTemp.length() + "    " + listFile2.size());
									//fichierTemp.delete();;
									fichierTemp.delete();
								} catch (Exception ex) {

								}
							}
						}

						if (map360.get(lineSeries)!=null && map360.get(lineSeries).containsKey(lineEpisode)) {
							ArrayList<File> listDelete = (ArrayList<File>) map360.get(lineSeries).get(lineEpisode);
							map360.get(lineSeries).remove(lineEpisode);
							for (File fichierTemp : listDelete) {
								try {
									System.out.println(fichierTemp.getPath() + "    " + fichierTemp.length() + "    " + listFile2.size());
									//fichierTemp.delete();;
									fichierTemp.delete();
								} catch (Exception ex) {

								}
							}
						}

						if (map240.get(lineSeries)!=null && map240.get(lineSeries).containsKey(lineEpisode)) {
							ArrayList<File> listDelete = (ArrayList<File>) map240.get(lineSeries).get(lineEpisode);
							map240.get(lineSeries).remove(lineEpisode);
							for (File fichierTemp : listDelete) {
								try {
									System.out.println(fichierTemp.getPath() + "    " + fichierTemp.length() + "    " + listFile2.size());
									//fichierTemp.delete();;
									fichierTemp.delete();
								} catch (Exception ex) {

								}
							}
						}

					}
				}
			}

// ====================================576
			if (map576.size() > 0) {
				Set<String> setSeries = map576.keySet();
				for (String lineSeries : setSeries) {
					Set<String> setEpisode = (Set<String>) map576.get(lineSeries).keySet();
					for (String lineEpisode : setEpisode) {
						ArrayList<File> listFile2 = (ArrayList<File>) map576.get(lineSeries).get(lineEpisode);
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
									// -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
									return lhs.length() > rhs.length() ? -1 : (lhs.length() < rhs.length()) ? 1 : 0;
								}
							});

							File fichierKeep = keepFile.get(0);
							keepFile.remove(0);

							for (File fichierTemp : keepFile) {
//									//fichierTemp.delete();;
								fichierTemp.delete();
							}
						}

						if (map480.get(lineSeries)!=null && map480.get(lineSeries).containsKey(lineEpisode)) {
							ArrayList<File> listDelete = (ArrayList<File>) map480.get(lineSeries).get(lineEpisode);
							map480.get(lineSeries).remove(lineEpisode);
							for (File fichierTemp : listDelete) {
								try {
									System.out.println(fichierTemp.getPath() + "    " + fichierTemp.length() + "    " + listFile2.size());
									//fichierTemp.delete();;
									fichierTemp.delete();
								} catch (Exception ex) {

								}
							}
						}

						if (map360.get(lineSeries)!=null && map360.get(lineSeries).containsKey(lineEpisode)) {
							ArrayList<File> listDelete = (ArrayList<File>) map360.get(lineSeries).get(lineEpisode);
							map360.get(lineSeries).remove(lineEpisode);
							for (File fichierTemp : listDelete) {
								try {
									System.out.println(fichierTemp.getPath() + "    " + fichierTemp.length() + "    " + listFile2.size());
									//fichierTemp.delete();;
									fichierTemp.delete();
								} catch (Exception ex) {

								}
							}
						}

						if (map240.get(lineSeries)!=null && map240.get(lineSeries).containsKey(lineEpisode)) {
							ArrayList<File> listDelete = (ArrayList<File>) map240.get(lineSeries).get(lineEpisode);
							map240.get(lineSeries).remove(lineEpisode);
							for (File fichierTemp : listDelete) {
								try {
									System.out.println(fichierTemp.getPath() + "    " + fichierTemp.length() + "    " + listFile2.size());
									//fichierTemp.delete();;
									fichierTemp.delete();
								} catch (Exception ex) {

								}
							}
						}

					}
				}
			}

// ====================================480
			if (map480.size() > 0) {
				Set<String> setSeries = map480.keySet();
				for (String lineSeries : setSeries) {
					Set<String> setEpisode = (Set<String>) map480.get(lineSeries).keySet();
					for (String lineEpisode : setEpisode) {
						ArrayList<File> listFile2 = (ArrayList<File>) map480.get(lineSeries).get(lineEpisode);
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
									// -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
									return lhs.length() > rhs.length() ? -1 : (lhs.length() < rhs.length()) ? 1 : 0;
								}
							});

							File fichierKeep = keepFile.get(0);
							keepFile.remove(0);

							for (File fichierTemp : keepFile) {
								System.out.println("test");
//									//fichierTemp.delete();;
								fichierTemp.delete();
							}
						}

						if (map360.get(lineSeries)!=null && map360.get(lineSeries).containsKey(lineEpisode)) {
							ArrayList<File> listDelete = (ArrayList<File>) map360.get(lineSeries).get(lineEpisode);
							map360.get(lineSeries).remove(lineEpisode);
							for (File fichierTemp : listDelete) {
								try {
									System.out.println(fichierTemp.getPath() + "    " + fichierTemp.length() + "    " + listFile2.size());
									//fichierTemp.delete();;
									fichierTemp.delete();
								} catch (Exception ex) {

								}
							}
						}

						if (map240.get(lineSeries)!=null && map240.get(lineSeries).containsKey(lineEpisode)) {
							ArrayList<File> listDelete = (ArrayList<File>) map240.get(lineSeries).get(lineEpisode);
							map240.get(lineSeries).remove(lineEpisode);
							for (File fichierTemp : listDelete) {
								try {
									System.out.println(fichierTemp.getPath() + "    " + fichierTemp.length() + "    " + listFile2.size());
									//fichierTemp.delete();;
									fichierTemp.delete();
								} catch (Exception ex) {

								}
							}
						}

					}
				}
			}

// ====================================360
			if (map360.size() > 0) {
				Set<String> setSeries = map360.keySet();
				for (String lineSeries : setSeries) {
					Set<String> setEpisode = (Set<String>) map360.get(lineSeries).keySet();
					for (String lineEpisode : setEpisode) {
						ArrayList<File> listFile2 = (ArrayList<File>) map360.get(lineSeries).get(lineEpisode);
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
									// -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
									return lhs.length() > rhs.length() ? -1 : (lhs.length() < rhs.length()) ? 1 : 0;
								}
							});

							File fichierKeep = keepFile.get(0);
							keepFile.remove(0);

							for (File fichierTemp : keepFile) {
								System.out.println("test");
//									//fichierTemp.delete();;
								fichierTemp.delete();
							}
						}

						if (map240.get(lineSeries)!=null && map240.get(lineSeries).containsKey(lineEpisode)) {
							ArrayList<File> listDelete = (ArrayList<File>) map240.get(lineSeries).get(lineEpisode);
							map240.get(lineSeries).remove(lineEpisode);
							for (File fichierTemp : listDelete) {
								try {
									System.out.println(fichierTemp.getPath() + "    " + fichierTemp.length() + "    " + listFile2.size());
									//fichierTemp.delete();;
									fichierTemp.delete();
								} catch (Exception ex) {

								}
							}
						}

					}
				}
			}
			
// ====================================240
			if (map240.size() > 0) {
				Set<String> setSeries = map240.keySet();
				for (String lineSeries : setSeries) {
					Set<String> setEpisode = (Set<String>) map240.get(lineSeries).keySet();
					for (String lineEpisode : setEpisode) {
						ArrayList<File> listFile2 = (ArrayList<File>) map240.get(lineSeries).get(lineEpisode);
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
									// -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
									return lhs.length() > rhs.length() ? -1 : (lhs.length() < rhs.length()) ? 1 : 0;
								}
							});

							File fichierKeep = keepFile.get(0);
							keepFile.remove(0);

							for (File fichierTemp : keepFile) {
								System.out.println("test");
//									//fichierTemp.delete();;
								fichierTemp.delete();
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
		DeleteDuplicatesSeriesTotal del = new DeleteDuplicatesSeriesTotal();
	}
}
