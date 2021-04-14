import org.apache.log4j.Logger;

import java.io.File;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DeleteDuplicatesFilmTotal {

	/** The Constant logger. */
	private static final Logger logger = Logger.getLogger(DeleteDuplicatesFilmTotal.class);

	public DeleteDuplicatesFilmTotal() {
		HashMap<String, ArrayList> map240 = new HashMap<String, ArrayList>();
		HashMap<String, ArrayList> map360 = new HashMap<String, ArrayList>();
		HashMap<String, ArrayList> map480 = new HashMap<String, ArrayList>();
		HashMap<String, ArrayList> map576 = new HashMap<String, ArrayList>();
		HashMap<String, ArrayList> map720 = new HashMap<String, ArrayList>();
		HashMap<String, ArrayList> map1080 = new HashMap<String, ArrayList>();
		HashMap<String, ArrayList> map2160 = new HashMap<String, ArrayList>();

		String pathNew ="20210318";

		long ms = System.currentTimeMillis();
		System.out.println("debut " + ms);
		try {
//			File base = new File("z://film/new");
			File base = new File("z://film/new");

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

			HashMap<String, ArrayList> map = new HashMap<String, ArrayList>();

			for (File fichierTemp : listeFichier) {
				String episode = "";
				StringTokenizer stk = new StringTokenizer(fichierTemp.getName(), "-");
				if (stk.hasMoreTokens()) {
					episode = stk.nextToken();
				}

				if (!episode.equals("") && episode != null) {
					if (fichierTemp.getName().contains("720p")) {
						ArrayList<File> listFile = map720.get(episode);
						if (listFile == null) {
							listFile = new ArrayList<File>();
							listFile.add(fichierTemp);
							map720.put(episode, listFile);
						} else {
							listFile.add(fichierTemp);
							map720.put(episode, listFile);
						}
					}

					if (fichierTemp.getName().contains("576p")) {
						ArrayList<File> listFile = map576.get(episode);
						if (listFile == null) {
							listFile = new ArrayList<File>();
							listFile.add(fichierTemp);
							map576.put(episode, listFile);
						} else {
							listFile.add(fichierTemp);
							map576.put(episode, listFile);
						}
					}

					if (fichierTemp.getName().contains("480p")) {
						ArrayList<File> listFile = map480.get(episode);
						if (listFile == null) {
							listFile = new ArrayList<File>();
							listFile.add(fichierTemp);
							map480.put(episode, listFile);
						} else {
							listFile.add(fichierTemp);
							map480.put(episode, listFile);
						}
					}

					if (fichierTemp.getName().contains("360p")) {
						ArrayList<File> listFile = map360.get(episode);
						if (listFile == null) {
							listFile = new ArrayList<File>();
							listFile.add(fichierTemp);
							map360.put(episode, listFile);
						} else {
							listFile.add(fichierTemp);
							map360.put(episode, listFile);
						}
					}

					if (fichierTemp.getName().contains("240p")) {
						ArrayList<File> listFile = map240.get(episode);
						if (listFile == null) {
							listFile = new ArrayList<File>();
							listFile.add(fichierTemp);
							map240.put(episode, listFile);
						} else {
							listFile.add(fichierTemp);
							map240.put(episode, listFile);
						}
					}

					if (fichierTemp.getName().contains("1080p")) {
						ArrayList<File> listFile = map1080.get(episode);
						if (listFile == null) {
							listFile = new ArrayList<File>();
							listFile.add(fichierTemp);
							map1080.put(episode, listFile);
						} else {
							listFile.add(fichierTemp);
							map1080.put(episode, listFile);
						}
					}

					if (fichierTemp.getName().contains("2160p")) {
						ArrayList<File> listFile = map2160.get(episode);
						if (listFile == null) {
							listFile = new ArrayList<File>();
							listFile.add(fichierTemp);
							map2160.put(episode, listFile);
						} else {
							listFile.add(fichierTemp);
							map2160.put(episode, listFile);
						}
					}
				}
			}

			// ====================================2160
			if (map2160.size() > 0) {
				Set<String> set = map2160.keySet();
				for (String lineTemp : set) {
					ArrayList<File> listFile2 = map2160.get(lineTemp);
					if (listFile2.size() > 0) {
						File fichier = listFile2.get(0);
						long taille = 0;
						long tailleTemp = 0;
						for (File fichierTemp : listFile2) {
							if (fichierTemp.length() > taille) {
								fichier = fichierTemp;
								taille = tailleTemp;
							}
						}

						if(fichier.getPath().contains(pathNew)){
							taille = 0;
							tailleTemp = 0;
							for (File fichierTemp : listFile2) {
								if (fichierTemp.length() > taille&&!fichierTemp.getPath().contains(pathNew)) {
									fichier = fichierTemp;
									taille = tailleTemp;
								}
							}
						}

						for (File fichierTemp : listFile2) {
							if (fichierTemp != fichier) {
								System.out.println(fichierTemp.getPath() + "    " + fichierTemp.length()+"    "+listFile2.size());
								fichierTemp.delete();
							}
						}
						if (map1080.containsKey(lineTemp)) {
							ArrayList<File> listDelete = map1080.get(lineTemp);
							map1080.remove(lineTemp);
							for (File fichierTemp : listDelete) {
								try {
									System.out.println(fichierTemp.getPath() + "    " + fichierTemp.length()+"    "+listFile2.size());
									fichierTemp.delete();
								} catch (Exception ex) {

								}
							}
						}

						if (map720.containsKey(lineTemp)) {
							ArrayList<File> listDelete = map720.get(lineTemp);
							map720.remove(lineTemp);
							for (File fichierTemp : listDelete) {
								try {
									System.out.println(fichierTemp.getPath() + "    " + fichierTemp.length()+"    "+listFile2.size());
									fichierTemp.delete();
								} catch (Exception ex) {

								}
							}
						}

						if (map576.containsKey(lineTemp)) {
							ArrayList<File> listDelete = map576.get(lineTemp);
							map576.remove(lineTemp);
							for (File fichierTemp : listDelete) {
								try {
									System.out.println(fichierTemp.getPath() + "    " + fichierTemp.length()+"    "+listFile2.size());
									fichierTemp.delete();
								} catch (Exception ex) {

								}
							}
						}

						if (map480.containsKey(lineTemp)) {
							ArrayList<File> listDelete = map480.get(lineTemp);
							map480.remove(lineTemp);
							for (File fichierTemp : listDelete) {
								try {
									System.out.println(fichierTemp.getPath() + "    " + fichierTemp.length()+"    "+listFile2.size());
									fichierTemp.delete();
								} catch (Exception ex) {

								}
							}
						}

						if (map360.containsKey(lineTemp)) {
							ArrayList<File> listDelete = map360.get(lineTemp);
							map360.remove(lineTemp);
							for (File fichierTemp : listDelete) {
								try {
									System.out.println(fichierTemp.getPath() + "    " + fichierTemp.length()+"    "+listFile2.size());
									fichierTemp.delete();
								} catch (Exception ex) {

								}
							}
						}

						if (map240.containsKey(lineTemp)) {
							ArrayList<File> listDelete = map240.get(lineTemp);
							map240.remove(lineTemp);
							for (File fichierTemp : listDelete) {
								try {
									System.out.println(fichierTemp.getPath() + "    " + fichierTemp.length()+"    "+listFile2.size());
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
				Set<String> set = map1080.keySet();
				for (String lineTemp : set) {
					ArrayList<File> listFile2 = map1080.get(lineTemp);
					if (listFile2.size() > 0) {
						File fichier = listFile2.get(0);
						long taille = 0;
						long tailleTemp = 0;
						for (File fichierTemp : listFile2) {
							if (fichierTemp.length() > taille) {
								fichier = fichierTemp;
								taille = tailleTemp;
							}
						}

						if(fichier.getPath().contains(pathNew)){
							taille = 0;
							tailleTemp = 0;
							for (File fichierTemp : listFile2) {
								if (fichierTemp.length() > taille&&!fichierTemp.getPath().contains(pathNew)) {
									fichier = fichierTemp;
									taille = tailleTemp;
								}
							}
						}

						for (File fichierTemp : listFile2) {
							if (fichierTemp != fichier) {
								System.out.println(fichierTemp.getPath() + "    " + fichierTemp.length()+"    "+listFile2.size());
								fichierTemp.delete();
							}
						}
						if (map720.containsKey(lineTemp)) {
							ArrayList<File> listDelete = map720.get(lineTemp);
							map720.remove(lineTemp);
							for (File fichierTemp : listDelete) {
								try {
									System.out.println(fichierTemp.getPath() + "    " + fichierTemp.length()+"    "+listFile2.size());
									fichierTemp.delete();
								} catch (Exception ex) {

								}
							}
						}

						if (map576.containsKey(lineTemp)) {
							ArrayList<File> listDelete = map576.get(lineTemp);
							map576.remove(lineTemp);
							for (File fichierTemp : listDelete) {
								try {
									System.out.println(fichierTemp.getPath() + "    " + fichierTemp.length()+"    "+listFile2.size());
									fichierTemp.delete();
								} catch (Exception ex) {

								}
							}
						}

						if (map480.containsKey(lineTemp)) {
							ArrayList<File> listDelete = map480.get(lineTemp);
							map480.remove(lineTemp);
							for (File fichierTemp : listDelete) {
								try {
									System.out.println(fichierTemp.getPath() + "    " + fichierTemp.length()+"    "+listFile2.size());
									fichierTemp.delete();
								} catch (Exception ex) {

								}
							}
						}

						if (map360.containsKey(lineTemp)) {
							ArrayList<File> listDelete = map360.get(lineTemp);
							map360.remove(lineTemp);
							for (File fichierTemp : listDelete) {
								try {
									System.out.println(fichierTemp.getPath() + "    " + fichierTemp.length()+"    "+listFile2.size());
									fichierTemp.delete();
								} catch (Exception ex) {

								}
							}
						}

						if (map240.containsKey(lineTemp)) {
							ArrayList<File> listDelete = map240.get(lineTemp);
							map240.remove(lineTemp);
							for (File fichierTemp : listDelete) {
								try {
									System.out.println(fichierTemp.getPath() + "    " + fichierTemp.length()+"    "+listFile2.size());
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
				Set<String> set = map720.keySet();
				for (String lineTemp : set) {
					ArrayList<File> listFile2 = map720.get(lineTemp);
					if (listFile2.size() > 0) {
						File fichier = listFile2.get(0);
						long taille = 0;
						long tailleTemp = 0;
						for (File fichierTemp : listFile2) {
							if (fichierTemp.length() > taille) {
								fichier = fichierTemp;
								taille = tailleTemp;
							}
						}

						if(fichier.getPath().contains(pathNew)){
							taille = 0;
							tailleTemp = 0;
							for (File fichierTemp : listFile2) {
								if (fichierTemp.length() > taille&&!fichierTemp.getPath().contains(pathNew)) {
									fichier = fichierTemp;
									taille = tailleTemp;
								}
							}
						}

						for (File fichierTemp : listFile2) {
							if (fichierTemp != fichier) {
								System.out.println(fichierTemp.getPath() + "    " + fichierTemp.length()+"    "+listFile2.size());
								fichierTemp.delete();
							}
						}

						if (map576.containsKey(lineTemp)) {
							ArrayList<File> listDelete = map576.get(lineTemp);
							map576.remove(lineTemp);
							for (File fichierTemp : listDelete) {
								try {
									System.out.println(fichierTemp.getPath() + "    " + fichierTemp.length()+"    "+listFile2.size());
									fichierTemp.delete();
								} catch (Exception ex) {

								}
							}
						}

						if (map480.containsKey(lineTemp)) {
							ArrayList<File> listDelete = map480.get(lineTemp);
							map480.remove(lineTemp);
							for (File fichierTemp : listDelete) {
								try {
									System.out.println(fichierTemp.getPath() + "    " + fichierTemp.length()+"    "+listFile2.size());
									fichierTemp.delete();
								} catch (Exception ex) {

								}
							}
						}

						if (map360.containsKey(lineTemp)) {
							ArrayList<File> listDelete = map360.get(lineTemp);
							map360.remove(lineTemp);
							for (File fichierTemp : listDelete) {
								try {
									System.out.println(fichierTemp.getPath() + "    " + fichierTemp.length()+"    "+listFile2.size());
									fichierTemp.delete();
								} catch (Exception ex) {

								}
							}
						}

						if (map240.containsKey(lineTemp)) {
							ArrayList<File> listDelete = map240.get(lineTemp);
							map240.remove(lineTemp);
							for (File fichierTemp : listDelete) {
								try {
									System.out.println(fichierTemp.getPath() + "    " + fichierTemp.length()+"    "+listFile2.size());
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
				Set<String> set = map576.keySet();
				for (String lineTemp : set) {
					ArrayList<File> listFile2 = map576.get(lineTemp);
					if (listFile2.size() > 0) {
						File fichier = listFile2.get(0);
						long taille = 0;
						long tailleTemp = 0;
						for (File fichierTemp : listFile2) {
							if (fichierTemp.length() > taille) {
								fichier = fichierTemp;
								taille = tailleTemp;
							}
						}

						if(fichier.getPath().contains(pathNew)){
							taille = 0;
							tailleTemp = 0;
							for (File fichierTemp : listFile2) {
								if (fichierTemp.length() > taille&&!fichierTemp.getPath().contains(pathNew)) {
									fichier = fichierTemp;
									taille = tailleTemp;
								}
							}
						}

						for (File fichierTemp : listFile2) {
							if (fichierTemp != fichier) {
								System.out.println(fichierTemp.getPath() + "    " + fichierTemp.length()+"    "+listFile2.size());
								fichierTemp.delete();
							}
						}

						if (map480.containsKey(lineTemp)) {
							ArrayList<File> listDelete = map480.get(lineTemp);
							map480.remove(lineTemp);
							for (File fichierTemp : listDelete) {
								try {
									System.out.println(fichierTemp.getPath() + "    " + fichierTemp.length()+"    "+listFile2.size());
									fichierTemp.delete();
								} catch (Exception ex) {

								}
							}
						}

						if (map360.containsKey(lineTemp)) {
							ArrayList<File> listDelete = map360.get(lineTemp);
							map360.remove(lineTemp);
							for (File fichierTemp : listDelete) {
								try {
									System.out.println(fichierTemp.getPath() + "    " + fichierTemp.length()+"    "+listFile2.size());
									fichierTemp.delete();
								} catch (Exception ex) {

								}
							}
						}

						if (map240.containsKey(lineTemp)) {
							ArrayList<File> listDelete = map240.get(lineTemp);
							map240.remove(lineTemp);
							for (File fichierTemp : listDelete) {
								try {
									System.out.println(fichierTemp.getPath() + "    " + fichierTemp.length()+"    "+listFile2.size());
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
				Set<String> set = map480.keySet();
				for (String lineTemp : set) {
					ArrayList<File> listFile2 = map480.get(lineTemp);
					if (listFile2.size() > 0) {
						File fichier = listFile2.get(0);
						long taille = 0;
						long tailleTemp = 0;
						for (File fichierTemp : listFile2) {
							if (fichierTemp.length() > taille) {
								fichier = fichierTemp;
								taille = tailleTemp;
							}
						}

						if(fichier.getPath().contains(pathNew)){
							taille = 0;
							tailleTemp = 0;
							for (File fichierTemp : listFile2) {
								if (fichierTemp.length() > taille&&!fichierTemp.getPath().contains(pathNew)) {
									fichier = fichierTemp;
									taille = tailleTemp;
								}
							}
						}

						for (File fichierTemp : listFile2) {
							if (fichierTemp != fichier) {
								System.out.println(fichierTemp.getPath() + "    " + fichierTemp.length()+"    "+listFile2.size());
								fichierTemp.delete();
							}
						}

						if (map360.containsKey(lineTemp)) {
							ArrayList<File> listDelete = map360.get(lineTemp);
							map360.remove(lineTemp);
							for (File fichierTemp : listDelete) {
								try {
									System.out.println(fichierTemp.getPath() + "    " + fichierTemp.length()+"    "+listFile2.size());
									fichierTemp.delete();
								} catch (Exception ex) {

								}
							}
						}

						if (map240.containsKey(lineTemp)) {
							ArrayList<File> listDelete = map240.get(lineTemp);
							map240.remove(lineTemp);
							for (File fichierTemp : listDelete) {
								try {
									System.out.println(fichierTemp.getPath() + "    " + fichierTemp.length()+"    "+listFile2.size());
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
				Set<String> set = map360.keySet();
				for (String lineTemp : set) {
					ArrayList<File> listFile2 = map360.get(lineTemp);
					if (listFile2.size() > 0) {
						File fichier = listFile2.get(0);
						long taille = 0;
						long tailleTemp = 0;
						for (File fichierTemp : listFile2) {
							if (fichierTemp.length() > taille) {
								fichier = fichierTemp;
								taille = tailleTemp;
							}
						}

						if(fichier.getPath().contains(pathNew)){
							taille = 0;
							tailleTemp = 0;
							for (File fichierTemp : listFile2) {
								if (fichierTemp.length() > taille&&!fichierTemp.getPath().contains(pathNew)) {
									fichier = fichierTemp;
									taille = tailleTemp;
								}
							}
						}

						for (File fichierTemp : listFile2) {
							if (fichierTemp != fichier) {
								System.out.println(fichierTemp.getPath() + "    " + fichierTemp.length()+"    "+listFile2.size());
								fichierTemp.delete();
							}
						}

						if (map240.containsKey(lineTemp)) {
							ArrayList<File> listDelete = map240.get(lineTemp);
							map240.remove(lineTemp);
							for (File fichierTemp : listDelete) {
								try {
									System.out.println(fichierTemp.getPath() + "    " + fichierTemp.length()+"    "+listFile2.size());
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
				Set<String> set = map240.keySet();
				for (String lineTemp : set) {
					ArrayList<File> listFile2 = map240.get(lineTemp);
					if (listFile2.size() > 0) {
						File fichier = listFile2.get(0);
						long taille = 0;
						long tailleTemp = 0;
						for (File fichierTemp : listFile2) {
							if (fichierTemp.length() > taille) {
								fichier = fichierTemp;
								taille = tailleTemp;
							}
						}

						if(fichier.getPath().contains(pathNew)){
							taille = 0;
							tailleTemp = 0;
							for (File fichierTemp : listFile2) {
								if (fichierTemp.length() > taille&&!fichierTemp.getPath().contains(pathNew)) {
									fichier = fichierTemp;
									taille = tailleTemp;
								}
							}
						}

						for (File fichierTemp : listFile2) {
							if (fichierTemp != fichier) {
								System.out.println(fichierTemp.getPath() + "    " + fichierTemp.length()+"    "+listFile2.size());
								fichierTemp.delete();
							}
						}

					}
				}
			}

		} catch (

		Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void main(String[] args) {
		DeleteDuplicatesFilmTotal del = new DeleteDuplicatesFilmTotal();
	}
}
