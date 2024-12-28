import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DeleteSeriesBeforeConvert {
	static String path2;
	static String year = "";
	static String nameSeries = "";
	static int resolutionFilmNew = 0;
	static int compteurDelete = 0;

	public DeleteSeriesBeforeConvert() {
		ArrayList<String> listPriority = new ArrayList<>();
		listPriority.add("poiyhoiyh");

		HashSet<File> listeFichierATraiter = FileDirParcours.getParcours("z://test/test", new String[]{".mp4",".mkv",".avi"});

		int compteur = 0;
		HashSet<File> filesFilmNew = FileDirParcours.getParcours("z://series", new String[]{".mp4",".mkv",".avi"});
		filesFilmNew.addAll(FileDirParcours.getParcours("e://series", new String[]{".mp4",".mkv",".avi"}));

		System.out.println("Debut files");
		for (File fichierFilmNew : filesFilmNew) {
//			if(fichierFilmNew.getName().toLowerCase().contains("village")){
//				System.out.println("test");
//			}
			Pattern pSeries = Pattern.compile(".*( - [0-9]+p - ).*");
			Matcher mSeries = pSeries.matcher(fichierFilmNew.getName().toLowerCase());

			if (mSeries.matches()) {
				nameSeries = "";
				year = "";
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
					} catch (Exception ex) {
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
					StringTokenizer stk = new StringTokenizer(path2, "-");
					while (stk.hasMoreTokens()) {
						try {
							String lineTemp = stk.nextToken().trim();
							Pattern p = Pattern.compile(".*([sS][0-9]+[xXeE][0-9]+).*");
							Matcher m = p.matcher(lineTemp.toLowerCase());

							if (m.matches()) {
								year = m.group(1);
								break;
							} else {
								nameSeries += " " + lineTemp;
							}
						} catch (Exception ex) {

						}
					}
					nameSeries = nameSeries.trim();
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
								nameRes = FilenameUtils.removeExtension(nameRes);
								//						System.out.println(path2+"    "+nameRes);
								//							StringTokenizer stk = new StringTokenizer(nameRes, "(");

								String nameFinal = "";
								String yearFinal = "";

								try {
									StringTokenizer stk = new StringTokenizer(nameRes, "-");
									while (stk.hasMoreTokens()) {
										try {
											String lineTemp = stk.nextToken().trim();
											Pattern p = Pattern.compile(".*([sS][0-9]+[xXeE][0-9]+).*");
											Matcher m = p.matcher(lineTemp.toLowerCase());

											if (m.matches()) {
												yearFinal = m.group(1);
												break;
											} else {
												nameFinal += " " + lineTemp;
											}
										} catch (Exception ex) {

										}
									}
									nameFinal = nameFinal.trim();

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
								if (nameFinal.toLowerCase().contains(nameSeries.toLowerCase()) && (year.toLowerCase().contains(yearFinal.toLowerCase()))) {
									//System.out.println(nameFinal + "    " + nameSeries + "    delete " + compteurDelete+++"    nameres "+resolutionSeries+"    final "+resolutionTestTest);

									if ((resolutionTestTest > resolutionFilmNew)) {
										System.out.println("Fichier Film New   " + fichierFilmNew.getPath() + "    " + fichierTestTest.getName());
										try {
										fichierFilmNew.delete();
										} catch (Exception ex) {

										}
									} else {
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
		}
		java.awt.Toolkit.getDefaultToolkit().beep();	}

	public static void main(String[] args) {
		DeleteSeriesBeforeConvert epguides = new DeleteSeriesBeforeConvert();

	}

}
