import warcraft.AuctionHouseData;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;

public class SteamLookBySize {

	public SteamLookBySize() {
		try {
			PrintWriter pw = new PrintWriter("e:/temp/steamlook.txt");

			ArrayList<File> listFiles = new ArrayList<>();

			File base = new File("z:/test/ultracc/games install");
			File[] files = base.listFiles();

			for (File fileTemp : files) {
				if (fileTemp.isDirectory()) {
					File baseTemp = fileTemp;
					File[] filesTemp = base.listFiles();

					for (File fichierTemp : filesTemp) {
						if(fichierTemp.isDirectory()) {
							listFiles.add(fichierTemp);
						}
					}
				}
			}

			listFiles.sort(new Comparator<File>() {
				@Override
				public int compare(File f1, File f2) {
					int retour = 0;
					if (calculTaille(f1) < calculTaille(f2)) retour = -1;
					if (calculTaille(f1) == calculTaille(f2)) retour = 0;
					if (calculTaille(f1) > calculTaille(f2)) retour = 1;
					return retour;
				}
			});

			for(File fichierTemp : listFiles) {
				pw.println(fichierTemp.getName());
			}

			pw.flush();
			pw.close();

		} catch (Exception ex) {
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
		SteamLookBySize populate = new SteamLookBySize();

	}

}
