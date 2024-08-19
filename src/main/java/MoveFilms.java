import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class MoveFilms {

	public MoveFilms() {
//		File base = new File("z://temp/convert");
//		File base = new File("z://temp/convert");

//		boolean test01 = ReplaceConvertFiles("z:/temp/convert");
//		boolean test02 = ReplaceConvertFiles("z:/film");

//		System.exit(0);

		File base = new File("z://temp/convert");

		File[] fichiers = base.listFiles();

		HashSet<File> listeFichier = new HashSet<File>();
		ArrayList<File> listeDirectory = new ArrayList<File>();
		//listeDirectory.add(new File("d://series"));
		//listeDirectory.add(new File("d://temp 2/main"));

		for (File fichier : fichiers) {
			if (fichier.isDirectory()) {
					listeDirectory.add(fichier);
			} else {
					listeFichier.add(fichier);
			}
		}

		while (listeDirectory.size() > 0) {
			File fichier = listeDirectory.get(0);

			File[] fichierListe = fichier.listFiles();

			for (File fichierTemp : fichierListe) {
				if (fichierTemp.isDirectory()) {
					listeDirectory.add(fichierTemp);
				} else {
					listeFichier.add(fichierTemp);
				}
			}
			listeDirectory.remove(0);
		}

		HashMap<String,Integer> listResolution = new HashMap<>();
		for(File fichierTemp : listeFichier) {
			try {
				try {
					String nameLowerCase = fichierTemp.getName();
//					int pos = nameLowerCase.indexOf(" - ");
//					nameLowerCase = nameLowerCase.substring(0,pos);

					String folder2 = ""+nameLowerCase.toUpperCase().charAt(0);

					String resolution = "All";

					if(nameLowerCase.toLowerCase().contains("2160p")){
						resolution = "2160";
					}
					if(nameLowerCase.toLowerCase().contains("1080p")){
						resolution = "1080";
					}
					if(nameLowerCase.toLowerCase().contains("720p")){
						resolution = "720";
					}
					if(nameLowerCase.toLowerCase().contains("576p")){
						resolution = "576";
					}
					if(nameLowerCase.toLowerCase().contains("480p")){
						resolution = "480";
					}
					if(nameLowerCase.toLowerCase().contains("360p")){
						resolution = "360";
					}
					if(nameLowerCase.toLowerCase().contains("240p")){
						resolution = "240";
					}

					Integer index = 0;
					try {
						 index = listResolution.get(resolution);
						 if(index!=null) {
							 listResolution.put(resolution, index + 1);
						 } else {
							 index = 0;
							 listResolution.put(resolution, index + 1);
						 }
					} catch(Exception ex){
						ex.printStackTrace();
					}

//					String chemin = "z://film/New/"+resolution+"/"+ folder2;
					String chemin = "z://film/Treated/"+resolution+"/"+ folder2;

					System.out.println("Chemin "+ chemin);
					FileUtils.forceMkdir(new File(chemin));
//					Files.createDirectories(Paths.get("z://film/new/"+nameLowerCase.toUpperCase().charAt(0)+"/"+nameLowerCase));
//							System.out.println(chemin + "/" + fichierTemp.getName());

//				Path p = Paths.get("z://film/new/"+ nameLowerCase.toUpperCase().charAt(0)+"/"+nameLowerCase);
//
//				String path = p.toString();
//				String chemin = path.replaceAll("\\\\", "/");
				System.out.println(chemin + "/" + nameLowerCase);
				String chemin3 = chemin + "/" + fichierTemp.getName();
				fichierTemp.renameTo(new File(chemin3));
//						System.out.println(chemin + "/" + fichierTemp.getName());
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		Set<String> cles = listResolution.keySet();
		for(String nombre : cles){
			System.out.println(nombre + "    "+listResolution.get(nombre));
		}


//		File[] listeSeries = new File("z://series").listFiles();
//
//		for (File fichierTemp : listeFichier) {
//			for (File fichierSeries : listeSeries) {
//				if (fichierTemp.getName().indexOf(" - ") != -1) {
//					String nameLowerCase = fichierTemp.getName().toLowerCase();
//					int pos = nameLowerCase.indexOf(" - ");
//					nameLowerCase = nameLowerCase.substring(0,pos);
//					String nameSeriesLowerCase = fichierSeries.getName().toLowerCase();
//
//					if (nameLowerCase.indexOf(nameSeriesLowerCase) != -1) {
//						Path p = Paths.get(fichierSeries.getPath());
//
//						String path = p.toString();
//						String chemin = path.replaceAll("\\\\", "/");
//						System.out.println(chemin + "/" + fichierTemp.getName());
//						fichierTemp.renameTo(new File(chemin + "/" + fichierTemp.getName()));
//					}
//				}
//			}
//		}

//		File[] listeSeries = new File("e://humour").listFiles();
//
//		for (File fichierTemp : listeFichier) {
//			for (File fichierSeries : listeSeries) {
//				if(fichierSeries.isDirectory()){
//					if (fichierTemp.getName().indexOf(" - ") != -1) {
//						String nameLowerCase = fichierTemp.getName().toLowerCase();
//						int pos = nameLowerCase.indexOf(" - ");
//						nameLowerCase = nameLowerCase.substring(0, pos);
//						String nameSeriesLowerCase = fichierSeries.getName().toLowerCase();
//
//						if (nameLowerCase.indexOf(nameSeriesLowerCase) != -1) {
//							Path p = Paths.get(fichierSeries.getPath());
//
//							String path = p.toString();
//							String chemin = path.replaceAll("\\\\", "/");
//							System.out.println(chemin + "/" + fichierTemp.getName());
//							fichierTemp.renameTo(new File(chemin + "/" + fichierTemp.getName()));
//						}
//					}
//				}
//			}
//		}
	}

	public static boolean ReplaceConvertFiles(String chemin){
		long ms = System.currentTimeMillis();
		System.out.println("debut " + ms);
		try {
			File base = new File(chemin);
			//File base = new File("d://te/film");

			File[] fichiers = base.listFiles();
			ArrayList<File> listeDirectory = new ArrayList<>();
			ArrayList<File> listeFichier = new ArrayList<>();

			for (File fichier : fichiers) {
				if (fichier.isDirectory()) {
					listeDirectory.add(fichier);
				} else {
					listeFichier.add(fichier);
				}
			}

			while (listeDirectory.size() > 0) {
				System.out.println("Taille "+listeDirectory.size()+"    Fichier "+listeFichier.size());
				File fichier = listeDirectory.get(0);

				File[] fichierListe = fichier.listFiles();

				for (File fichierTemp : fichierListe) {
					if (fichierTemp.isDirectory()) {
						listeDirectory.add(fichierTemp);
					} else {
						listeFichier.add(fichierTemp);
					}
				}
				listeDirectory.remove(0);
			}

			for(File fichierTemp : listeFichier){
				if(fichierTemp.getPath().contains("_")) {
					fichierTemp.renameTo(new File(fichierTemp.getPath().replaceAll("_", "'")));
				}
			}
		}

		catch (

				Exception ex) {
			ex.printStackTrace();
		}
		return true;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MoveFilms du = new MoveFilms();
	}

}
