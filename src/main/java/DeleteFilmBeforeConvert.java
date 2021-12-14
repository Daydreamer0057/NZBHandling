
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.StringTokenizer;

public class DeleteFilmBeforeConvert {
	static String path2;
	static String year;
	static String nameSeries;
	static int compteurDelete = 0;

	public DeleteFilmBeforeConvert() {
		// Dossier a supprimer
		File base = new File("z://test/film");
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
		fileBase.add(new File("z://film/new"));
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
				StringTokenizer stk = new StringTokenizer(path2,"(");
				if(stk.hasMoreTokens()) {
					try {
						nameSeries = stk.nextToken().trim();
					} catch (Exception ex){

					}
				}
				if(stk.hasMoreTokens()) {
					try {
						String line = stk.nextToken();
						year = line.substring(0, 4).trim();
					} catch (Exception ex){

					}
				}
			}


			listeFichier.forEach(res -> {
				try {
					compteurDelete++;
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
						StringTokenizer stk = new StringTokenizer(nameRes,"(");
						String nameFinal = "";
						String yearFinal = "";
						if(stk.hasMoreTokens()) {
							nameFinal = stk.nextToken().trim();
						}
						if(stk.hasMoreTokens()) {
							yearFinal = stk.nextToken().substring(0,4).trim();
						}
						int sizePath = 0;
						if(nameRes.toLowerCase().contains("2160p")){
							sizePath = 2160;
						}
						if(nameRes.toLowerCase().contains("1080p")){
							sizePath = 1080;
						}
						if(nameRes.toLowerCase().contains("720p")){
							sizePath = 720;
						}
						if(nameRes.toLowerCase().contains("576p")){
							sizePath = 576;
						}
						if(nameRes.toLowerCase().contains("480p")){
							sizePath = 480;
						}

						int sizeRes = 0;
						if(nameFinal.toLowerCase().contains("2160p")){
							sizeRes = 2160;
						}
						if(nameFinal.toLowerCase().contains("1080p")){
							sizeRes = 1080;
						}
						if(nameFinal.toLowerCase().contains("720p")){
							sizeRes = 720;
						}
						if(nameFinal.toLowerCase().contains("576p")){
							sizeRes = 576;
						}
						if(nameFinal.toLowerCase().contains("480p")){
							sizeRes = 480;
						}
						if (nameFinal.equalsIgnoreCase(nameSeries)&&(year.equalsIgnoreCase(yearFinal))&&(sizeRes<sizePath)) {
//							res.delete();
							System.out.println(res.getPath() + "    " + listeFichier.size());
						}
					}
				}catch(Exception ex) {
					ex.printStackTrace();
				}
			});
		}
	}

	public static void main(String[] args) {
		DeleteFilmBeforeConvert epguides = new DeleteFilmBeforeConvert();

	}

}
