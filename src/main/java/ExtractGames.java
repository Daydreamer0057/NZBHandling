
import org.apache.commons.io.FilenameUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class ExtractGames {

	public ExtractGames() {
		File base = new File("Y:/Users/bmonnet/AppData/Local/qBittorrent/Backup/BT_backup");

		File[] fichiers = base.listFiles();

		HashSet<File> listeFichier = new HashSet<File>();

		for (File fichier : fichiers) {
			if (fichier.getName().toLowerCase().endsWith("fastresume")) {
				listeFichier.add(fichier);
			}
		}

		System.out.println(listeFichier.size());
		int compteur = 0;
		for(File fichierTemp : listeFichier){
			System.out.println(compteur+" / "+listeFichier.size());
			compteur++;

			try {
				boolean testGames = false;
				FileReader fr = new FileReader(fichierTemp);
				BufferedReader br = new BufferedReader(fr);

				String line = "";
				while(line!=null){
					line = br.readLine();
					if(line!=null&&line.toLowerCase().contains("film qbit")){
						testGames = true;
					}
				}

				br.close();
				fr.close();

				if(!testGames){
					fichierTemp.delete();
//					System.out.println(fichierTemp.getName());
//					System.out.println(new File(FilenameUtils.removeExtension(fichierTemp.getName())+".torrent").exists());
					if(new File(FilenameUtils.removeExtension(fichierTemp.getName())+".torrent").exists()) {
//						System.out.println(FilenameUtils.removeExtension(fichierTemp.getName())+".torrent");
						new File(FilenameUtils.removeExtension(fichierTemp.getName())+".torrent").delete();
					}
				}
			} catch(Exception ex){
				ex.printStackTrace();
			}
		}

	}

	public static void main(String[] args) {
		ExtractGames epguides = new ExtractGames();

	}

}
