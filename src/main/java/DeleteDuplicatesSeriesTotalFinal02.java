import java.io.File;
import java.util.*;

public class DeleteDuplicatesSeriesTotalFinal02 {
	public DeleteDuplicatesSeriesTotalFinal02() {
		HashMap<String, Map> mapnameFilm = new HashMap<String, Map>();

		String pathNew = "treated";

		long ms = System.currentTimeMillis();
		System.out.println("debut " + ms);
		try {
			File base = new File("e://series");
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
						if(!fichier.getPath().toLowerCase().contains("fallout")&&!fichier.getPath().toLowerCase().contains("halo")&&!fichier.getPath().toLowerCase().contains("doctor")&&!fichier.getPath().toLowerCase().contains("body")) {
							listeFichier.add(fichier);
						}
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
								if(!fichier.getPath().toLowerCase().contains("fallout")&&!fichier.getPath().toLowerCase().contains("halo")&&!fichier.getPath().toLowerCase().contains("doctor")&&!fichier.getPath().toLowerCase().contains("body")) {
									listeFichier.add(fichierTemp);
								}
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
				String nameSeries = "";

				StringTokenizer stk = new StringTokenizer(fichierTemp.getName(), "-");
				if (stk.hasMoreTokens()) {
					try {
						nameSeries = stk.nextToken().trim();
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
				if (stk.hasMoreTokens()) {
					try {
						String line = stk.nextToken();
						episode = line;
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}

				if (!episode.equals("") && episode != null && fichierTemp.getName().contains("- subs")) {
						ArrayList<File> listFile = null;
						try {
							listFile = (ArrayList<File>) mapnameFilm.get(nameSeries).get(episode);
						} catch (Exception ex) {

						}

						if (listFile == null) {
							listFile = new ArrayList<File>();
							listFile.add(fichierTemp);
							Map<String, ArrayList> listMap = mapnameFilm.get(nameSeries);
							if(listMap==null){
								listMap = new HashMap<>();
							}
							listMap.put(episode, listFile);
							mapnameFilm.put(nameSeries, listMap);
						} else {
							listFile.add(fichierTemp);
							Map<String, ArrayList> listMap = (Map<String, ArrayList>) mapnameFilm.get(nameSeries);
							listMap.put(episode, listFile);
							mapnameFilm.put(nameSeries, listMap);
						}
					}
			}


			if (mapnameFilm.size() > 0) {
				Set<String> setnameFilm = mapnameFilm.keySet();
				for (String linenameFilm : setnameFilm) {
					Set<String> setyear = (Set<String>) mapnameFilm.get(linenameFilm).keySet();
					for (String lineyear : setyear) {
						ArrayList<File> listFile2 = (ArrayList<File>) mapnameFilm.get(linenameFilm).get(lineyear);
						if (listFile2.size() > 1) {
//							if(listFile2.size()>1){
//								System.out.println("test");
//							}
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

//							boolean testKeep = false;
//							ArrayList<File> listeFichierKeep = new ArrayList<>();

//							Iterator keepFileIT = keepFile.iterator();
//							while(keepFileIT.hasNext()){
//								File fichierKeep = (File)keepFileIT.next();
//								if(fichierKeep.getPath().toLowerCase().contains(pathNew)) {
//									keepFileIT.remove();
//									testKeep = true;
//								}
//							}

//							if(!testKeep){
//								Iterator keepFileIT2 = keepFile.iterator();
//								if(keepFileIT2.hasNext()){
//									File fichierKeep2 = (File)keepFileIT2.next();
//									keepFileIT2.remove();
//								}
//
//							}
							for(int i=1;i<keepFile.size();i++){
								File fichierTemp = keepFile.get(i);

									System.out.println(fichierTemp.getPath());
									fichierTemp.delete();
//								try {
//									FileUtils.moveFileToDirectory(fichierTemp, new File("z://test/error"), false);
//								} catch(FileExistsException fex){
//									fex.printStackTrace();
//								}
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
		DeleteDuplicatesSeriesTotalFinal02 del = new DeleteDuplicatesSeriesTotalFinal02();
	}
}
