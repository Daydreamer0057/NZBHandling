import java.io.File;
import java.util.*;

public class DeleteDuplicatesFilmTotalFinal {
	public DeleteDuplicatesFilmTotalFinal() {
		HashMap<String, Map> mapnameFilm = new HashMap<>();

		long ms = System.currentTimeMillis();
		System.out.println("debut " + ms);
		try {
			HashSet<File> listeFichier = FileDirParcours.getParcours("z://film/new", new String[]{".mkv",".mp4","avi"});
			HashSet<File> listeFichier2 = FileDirParcours.getParcours("z://film/treated", new String[]{".mkv",".mp4","avi"});

			listeFichier.addAll(listeFichier2);

			for (File fichierTemp : listeFichier) {
				String year = "";
				String nameFilm = "";

				try {
					if (fichierTemp.getName().contains("(")) {
						nameFilm = fichierTemp.getName().substring(0, fichierTemp.getName().indexOf("(") - 1);
						year = fichierTemp.getName().substring(fichierTemp.getName().indexOf("(") + 1, fichierTemp.getName().indexOf(")"));
					}
				} catch (Exception ex){

				}

				if (!year.isEmpty()) {
						ArrayList<File> listFile = null;
						try {
							listFile = (ArrayList<File>) mapnameFilm.get(nameFilm).get(year);
						} catch (Exception ex) {

						}

						if (listFile == null) {
							listFile = new ArrayList<File>();
							listFile.add(fichierTemp);
							Map<String, ArrayList> listMap = mapnameFilm.get(nameFilm);
							if(listMap==null){
								listMap = new HashMap<>();
							}
							listMap.put(year, listFile);
							mapnameFilm.put(nameFilm, listMap);
						} else {
							listFile.add(fichierTemp);
							Map<String, ArrayList> listMap = (Map<String, ArrayList>) mapnameFilm.get(nameFilm);
							listMap.put(year, listFile);
							mapnameFilm.put(nameFilm, listMap);
						}
					}
			}


			if (!mapnameFilm.isEmpty()) {
				Set<String> setnameFilm = mapnameFilm.keySet();
				for (String linenameFilm : setnameFilm) {
					Set<String> setyear = (Set<String>) mapnameFilm.get(linenameFilm).keySet();
					for (String lineyear : setyear) {
						ArrayList<File> listFile2 = (ArrayList<File>) mapnameFilm.get(linenameFilm).get(lineyear);
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
											retour = Long.compare(lhs.length(), rhs.length());
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
											retour = Long.compare(lhs.length(), rhs.length());
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
											retour = Long.compare(lhs.length(), rhs.length());
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
											retour = Long.compare(lhs.length(), rhs.length());
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
											retour = Long.compare(lhs.length(), rhs.length());
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
											retour = Long.compare(lhs.length(), rhs.length());
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
											retour = Long.compare(lhs.length(), rhs.length());
									}
									if(retour==0) retour = -1;
									return retour;
								}
						});

							for(int i=1;i<keepFile.size();i++){
								File fichierTemp = keepFile.get(i);

									System.out.println(fichierTemp.getPath());
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
		DeleteDuplicatesFilmTotalFinal del = new DeleteDuplicatesFilmTotalFinal();
	}
}
