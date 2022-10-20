
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DeleteSeriesBeforeConvert {
	static String path2;
	static String year;
	static String nameSeries;
	static int resolutionSeries = 0;
	static int compteurDelete = 0;

	public DeleteSeriesBeforeConvert() {
		// Dossier a supprimer
		File base = new File("z://test/done");
		//File base = new File("Z://film/new");
		//File base = new File("e://theatre/convert");
		// File base = new File("f://Graver/Theatre/Convert");

		File[] fichiers = base.listFiles();

		HashSet<File> listeFichier = new HashSet<File>();
		ArrayList<File> listeDirectory = new ArrayList<File>();

		for (File fichier : fichiers) {
			if (fichier.isDirectory()) {
				listeDirectory.add(fichier);
			} else {
				if(!fichier.getName().endsWith(".srt")&&(fichier.getName().endsWith(".mp4")||fichier.getName().endsWith(".mkv")||fichier.getName().endsWith(".avi"))) {
					listeFichier.add(fichier);
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
					if(!fichierTemp.getName().endsWith(".srt")&&(fichierTemp.getName().endsWith(".mp4")||fichierTemp.getName().endsWith(".mkv")||fichierTemp.getName().endsWith(".avi"))) {
						listeFichier.add(fichierTemp);
					}
				}
			}
			listeDirectory.remove(0);
		}

		int compteur = 0;
		HashSet<File> fileBase = new HashSet<File>();
		HashSet<File> files = new HashSet<File>();
//		fileBase.add(new File("e://convert"));
		fileBase.add(new File("z://series"));
//		fileBase.add(new File("z://test/temp"));

		ArrayList<File> listeDirectory2 = new ArrayList<File>();

		for (File filesTemp : fileBase) {
			if (filesTemp.isDirectory()) {
				listeDirectory2.add(filesTemp);
			} else {
				files.add(filesTemp);
			}
		}

		while (listeDirectory2.size() > 0) {
			File fichier2 = listeDirectory2.get(0);
			File[] fichierListe2 = fichier2.listFiles();

			for (File fichierTemp : fichierListe2) {
				if (fichierTemp.isDirectory()) {
					listeDirectory2.add(fichierTemp);
				} else {
					files.add(fichierTemp);
				}
			}
			listeDirectory2.remove(0);
		}


		for (File fichierTemp : files) {
			String nomFichier = "";
			final String path = "";
			if ((fichierTemp.getName().indexOf("_")>fichierTemp.getName().length()-6)&&(
					fichierTemp.getName().indexOf("_0") != -1 || fichierTemp.getName().indexOf("_1") != -1
							|| fichierTemp.getName().indexOf("_2") != -1 || fichierTemp.getName().indexOf("_3") != -1
							|| fichierTemp.getName().indexOf("_4") != -1 || fichierTemp.getName().indexOf("_5") != -1
							|| fichierTemp.getName().indexOf("_6") != -1 || fichierTemp.getName().indexOf("_7") != -1
							|| fichierTemp.getName().indexOf("_8") != -1 || fichierTemp.getName().indexOf("_9") != -1)) {
				path2 = fichierTemp.getName().substring(0, fichierTemp.getName().length() - 6);
			} else {
				// System.out.println("Compteur " + compteur + " / " + files.length);
				compteur++;
				path2 = fichierTemp.getName();
				//path2 = path.replaceAll("_", "'");
				path2 = FilenameUtils.removeExtension(path2);
			}
			if(path2!=""){
				StringTokenizer stk = new StringTokenizer(path2,"-");
				if(stk.hasMoreTokens()) {
					try {
						nameSeries = stk.nextToken().trim();
					} catch (Exception ex){

					}
				}
				if(stk.hasMoreTokens()) {
					try {
						String line = stk.nextToken();
						year = line.trim();
					} catch (Exception ex){

					}
				}
			}

			if(fichierTemp.getName().toLowerCase().contains("2160p")){
				resolutionSeries = 2160;
			}
			if(fichierTemp.getName().toLowerCase().contains("1080p")){
				resolutionSeries = 1080;
			}
			if(fichierTemp.getName().toLowerCase().contains("720p")){
				resolutionSeries = 720;
			}
			if(fichierTemp.getName().toLowerCase().contains("576p")){
				resolutionSeries = 576;
			}
			if(fichierTemp.getName().toLowerCase().contains("480p")){
				resolutionSeries = 480;
			}


			listeFichier.forEach(res -> {
				try {
//					compteurDelete++;
					String nameRes = res.getName();
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
						StringTokenizer stk = new StringTokenizer(nameRes,"-");
						String nameFinal = "";
						String yearFinal = "";
						if(stk.hasMoreTokens()) {
							nameFinal = stk.nextToken().trim();
						}
						if(stk.hasMoreTokens()) {
							yearFinal = stk.nextToken().trim();
						}
						int sizeFinal = 0;
						if(nameRes.toLowerCase().contains("2160p")){
							sizeFinal = 2160;
						}
						if(nameRes.toLowerCase().contains("1080p")){
							sizeFinal = 1080;
						}
						if(nameRes.toLowerCase().contains("720p")){
							sizeFinal = 720;
						}
						if(nameRes.toLowerCase().contains("576p")){
							sizeFinal = 576;
						}
						if(nameRes.toLowerCase().contains("480p")){
							sizeFinal = 480;
						}



//						if (nameFinal.equalsIgnoreCase(nameSeries)&&(year.equalsIgnoreCase(yearFinal))){
//							System.out.println("nameFinal "+nameFinal+"    nameSeries "+nameSeries+"    yearFinal "+yearFinal+"    year "+year+"    sizeFinal "+sizeFinal+"    sizeSeries "+resolutionSeries);
//						}
						if (nameFinal.equalsIgnoreCase(nameSeries)&&(year.equalsIgnoreCase(yearFinal))&&(resolutionSeries>=sizeFinal)) {
							res.delete();
//							System.out.println(nameFinal+"    "+nameSeries+"    delete "+compteurDelete++);
							System.out.println("nameFinal "+nameFinal+"    nameSeries "+nameSeries+"    yearFinal "+yearFinal+"    year "+year+"    resolutionnSeries "+resolutionSeries+"    sizeFinal "+sizeFinal+"    delete "+compteurDelete++);

						}
					}
				}catch(Exception ex) {
					ex.printStackTrace();
				}
			});
		}
	}

	public static void main(String[] args) {
		DeleteSeriesBeforeConvert epguides = new DeleteSeriesBeforeConvert();

	}

}
