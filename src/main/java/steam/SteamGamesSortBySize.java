package steam;

import Utilities.FileDirParcours;

import java.io.File;
import java.io.PrintWriter;
import java.util.*;
import java.util.stream.Collectors;

public class SteamGamesSortBySize {

	public SteamGamesSortBySize() {
		try {
			PrintWriter pw = new PrintWriter("c:/temp/liste_games.txt");
			HashMap<File,Long> map = new HashMap<File, Long>();

			File base = new File("z:/test/ultracc/games install");

			File[] fichiers = base.listFiles();

			for (File fichierTemp : fichiers) {
				File baseGame = fichierTemp;

				File[] fichiersGame = baseGame.listFiles();

				for (File fichierGameTemp : fichiersGame) {
					System.out.println(fichierGameTemp.getName());
					map.put(fichierGameTemp, calculTaille(fichierGameTemp));
				}
			}

			Map<File, Long> sortedMap = map.entrySet()
					.stream()
					.sorted(Map.Entry.<File, Long>comparingByValue(Comparator.reverseOrder()))
					.collect(Collectors.toMap(
							entry -> entry.getKey(), // Lambda for Key mapper
							entry -> entry.getValue(), // Value mapper
							(e1, e2) -> e1, // Merge function (not used here)
							LinkedHashMap<File,Long>::new // Collect into LinkedHashMap to maintain order
					));

			// Print the sorted map
			sortedMap.forEach((file, size) -> pw.println(file.getName()));
			pw.flush();
			pw.close();
		} catch (Exception ex){
			ex.printStackTrace();
		}
	}

	public long calculTaille(File file) {
		long taille = 0;
		HashSet<File> listeFichierDownload = new HashSet<File>();
		ArrayList<File> listeDirectoryDownload = new ArrayList<File>();
		listeDirectoryDownload.add(file);

		while (listeDirectoryDownload.size() > 0) {
//			System.out.println(listeFichierDownload.size() + "    " + listeDirectoryDownload.size());
			File[] fichierListeDownload = listeDirectoryDownload.get(0).listFiles();

			if (fichierListeDownload != null) {
				for (File fichierTempDownload : fichierListeDownload) {
					if (fichierTempDownload.isDirectory()) {
						listeDirectoryDownload.add(fichierTempDownload);
					} else {
						taille += fichierTempDownload.length();
					}
				}
			}
			listeDirectoryDownload.remove(0);
		}
		return taille;
	}

	public static void main(String[] args) {
		SteamGamesSortBySize epguides = new SteamGamesSortBySize();

	}

}
