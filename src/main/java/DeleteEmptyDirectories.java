import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;

public class DeleteEmptyDirectories {

	public DeleteEmptyDirectories() {
		try {
			File base = new File("e://games");
			File[] listLevel1 = base.listFiles();

			for(File fichierTemp : listLevel1){
				File base2 = fichierTemp;
				File[] listLevel2 = base2.listFiles();

				for(File fichierLvl2 : listLevel2){
					File base3 = fichierLvl2;
					File[] listLevel3 = base3.listFiles();
					if(listLevel3!=null) {
						for (File fichierFinal : listLevel3) {
							if (fichierFinal.isDirectory()) {
//							calculTaille(fichierFinal) == 0L
								System.out.println(fichierFinal.getPath());
								try {
									FileUtils.deleteDirectory(fichierFinal);
								} catch (Exception ex) {

								}
							}
						}
					}
				}
			}
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
		DeleteEmptyDirectories populate = new DeleteEmptyDirectories();

	}

}
