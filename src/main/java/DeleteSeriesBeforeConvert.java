import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.StringTokenizer;

public class DeleteSeriesBeforeConvert {
	static String path2;
	static String year;
	static String nameSeries;
	static int resolutionFilmNew = 0;
	static int compteurDelete = 0;

	public DeleteSeriesBeforeConvert() {
		ArrayList<String> listPriority = new ArrayList<>();
		listPriority.add("poiyhoiyh");

		HashSet<File> listeFichierATraiter = FileDirParcours.getParcours("z://test/test", new String[]{".mp4",".mkv",".avi"});

		int compteur = 0;
		HashSet<File> filesFilmNew = FileDirParcours.getParcours("z://series", new String[]{".mp4",".mkv",".avi"});

		System.out.println("Debut files");
		for (File fichierFilmNew : filesFilmNew) {
			boolean testFiles = false;
			String nomFichier = "";
			final String path = "";
			if ((fichierFilmNew.getName().indexOf("_") > fichierFilmNew.getName().length() - 6) && (
					fichierFilmNew.getName().indexOf("_0") != -1 || fichierFilmNew.getName().indexOf("_1") != -1
							|| fichierFilmNew.getName().indexOf("_2") != -1 || fichierFilmNew.getName().indexOf("_3") != -1
							|| fichierFilmNew.getName().indexOf("_4") != -1 || fichierFilmNew.getName().indexOf("_5") != -1
							|| fichierFilmNew.getName().indexOf("_6") != -1 || fichierFilmNew.getName().indexOf("_7") != -1
							|| fichierFilmNew.getName().indexOf("_8") != -1 || fichierFilmNew.getName().indexOf("_9") != -1)) {
				try {
					path2 = fichierFilmNew.getName().substring(0, fichierFilmNew.getName().length() - 6);
				} catch (Exception ex){
					ex.printStackTrace();
				}
			} else {
				// System.out.println("Compteur " + compteur + " / " + files.length);
				compteur++;
				path2 = fichierFilmNew.getName();
				//path2 = path.replaceAll("_", "'");
				path2 = FilenameUtils.removeExtension(path2);
			}
			if (path2 != "") {
				StringTokenizer stk = new StringTokenizer(path2, " - ");
				if (stk.hasMoreTokens()) {
					try {
						nameSeries = stk.nextToken().trim();
					} catch (Exception ex) {
						testFiles = true;
					}
				}
				if (stk.hasMoreTokens()) {
					try {
						year = stk.nextToken();
					} catch (Exception ex) {
						testFiles = true;
					}
				}
			}

			if (!testFiles) {

				if (fichierFilmNew.getName().toLowerCase().contains("2160p")) {
					resolutionFilmNew = 2160;
				}
				if (fichierFilmNew.getName().toLowerCase().contains("1080p")) {
					resolutionFilmNew = 1080;
				}
				if (fichierFilmNew.getName().toLowerCase().contains("720p")) {
					resolutionFilmNew = 720;
				}
				if (fichierFilmNew.getName().toLowerCase().contains("576p")) {
					resolutionFilmNew = 576;
				}
				if (fichierFilmNew.getName().toLowerCase().contains("480p")) {
					resolutionFilmNew = 480;
				}


				listeFichierATraiter.forEach(fichierTestTest -> {
					try {
						String nameRes = fichierTestTest.getName();
						//						System.out.println(nameRes);
						if (!nameRes.equalsIgnoreCase(".classpath")) {
							if (FilenameUtils.removeExtension(nameRes).endsWith("_0") || FilenameUtils.removeExtension(nameRes).endsWith("_1") || FilenameUtils.removeExtension(nameRes).endsWith("_2") || FilenameUtils.removeExtension(nameRes).endsWith("_3") || FilenameUtils.removeExtension(nameRes).endsWith("_4") || FilenameUtils.removeExtension(nameRes).endsWith("_5") || FilenameUtils.removeExtension(nameRes).endsWith("_6") || FilenameUtils.removeExtension(nameRes).endsWith("_7") || FilenameUtils.removeExtension(nameRes).endsWith("_8") || FilenameUtils.removeExtension(nameRes).endsWith("_9")) {
								if (nameRes.length() > 5) {
									nameRes = nameRes.substring(0, nameRes.length() - 6);
								} else {
									nameRes = nameRes.substring(0, nameRes.length() - 4);
								}
							}
							nameRes = FilenameUtils.removeExtension(nameRes);
							//						System.out.println(path2+"    "+nameRes);
							//							StringTokenizer stk = new StringTokenizer(nameRes, "(");

							String nameFinal = "";
							String yearFinal = "";

							try {
								StringTokenizer stk = new StringTokenizer(nameRes, " - ");
								if (stk.hasMoreTokens()) {
									try {
										nameFinal = stk.nextToken().trim();
									} catch (Exception ex) {

									}
								}
								if (stk.hasMoreTokens()) {
									try {
										yearFinal = stk.nextToken();
									} catch (Exception ex) {

									}
								}
							} catch (Exception ex) {
								ex.printStackTrace();
								System.out.println(nameRes + "     " + nameRes.indexOf("("));
							}

							int resolutionTestTest = 0;
							if (nameRes.toLowerCase().contains("2160p")) {
								resolutionTestTest = 2160;
							}
							if (nameRes.toLowerCase().contains("1080p")) {
								resolutionTestTest = 1080;
							}
							if (nameRes.toLowerCase().contains("720p")) {
								resolutionTestTest = 720;
							}
							if (nameRes.toLowerCase().contains("576p")) {
								resolutionTestTest = 576;
							}
							if (nameRes.toLowerCase().contains("480p")) {
								resolutionTestTest = 480;
							}


							//						System.out.println("nameFinal "+nameFinal+"    nameSeries "+nameSeries+"    yearFinal "+yearFinal+"    year "+year+"    delete "+compteurDelete++);
							//						if (nameFinal.equalsIgnoreCase(nameSeries)&&(year.equalsIgnoreCase(yearFinal))){
							//							System.out.println("nameFinal "+nameFinal+"    nameSeries "+nameSeries+"    yearFinal "+yearFinal+"    year "+year+"    resolutionTestTest "+resolutionTestTest+"    sizeSeries "+resolutionSeries);
							//						}
							//							if (nameFinal.equalsIgnoreCase(nameSeries) && (year.equalsIgnoreCase(yearFinal)) && (resolutionSeries <= resolutionTestTest)) {
							if (nameFinal.equalsIgnoreCase(nameSeries) && (year.equalsIgnoreCase(yearFinal))) {
								//System.out.println(nameFinal + "    " + nameSeries + "    delete " + compteurDelete+++"    nameres "+resolutionSeries+"    final "+resolutionTestTest);

								if ((resolutionTestTest > resolutionFilmNew)) {
									System.out.println("Fichier Film New   " + fichierFilmNew.getPath() + "    " + fichierTestTest.getName());
									try {
										fichierFilmNew.delete();
									} catch (Exception ex) {

									}
								}
								if ((resolutionTestTest == resolutionFilmNew)) {
									if (fichierFilmNew.getPath().toLowerCase().contains("treated")) {
										System.out.println("Fichier Test   " + fichierTestTest.getPath() + "    " + fichierFilmNew.getName());
										try {
											fichierTestTest.delete();
										} catch (Exception ex) {

										}
									} else {
										System.out.println("Fichier New  " + fichierTestTest.getPath() + "    " + fichierFilmNew.getName());
										try {
											fichierFilmNew.delete();
										} catch (Exception ex) {

										}
									}
								}
								if ((resolutionTestTest < resolutionFilmNew)) {
									System.out.println("Fichier Test    " + fichierTestTest.getPath() + "    " + fichierFilmNew.getName());
									try {
										fichierTestTest.delete();
									} catch (Exception ex) {

									}
								}
							}
						}
					} catch (Exception ex) {
						ex.printStackTrace();
						System.exit(0);
					}
				});
			}
		}
		java.awt.Toolkit.getDefaultToolkit().beep();	}

	public static void main(String[] args) {
		DeleteSeriesBeforeConvert epguides = new DeleteSeriesBeforeConvert();

	}

}
