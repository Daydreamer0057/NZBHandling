import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class DeleteNotWebBluDVDRemux {
	static String path2;
	static int compteurDelete = 0;

	static double tailleGagnee = 0.0;
	public DeleteNotWebBluDVDRemux() {
		while(true){
			File base = new File("v://download");
//			File base = new File("z://test/film");

			File[] fichiers = base.listFiles();

			int compteurBase = 0;
			for (File fichier : fichiers) {
				System.out.println("Compteur "+compteurBase+" / "+fichiers.length);
				compteurBase++;
				if (fichier.isDirectory()&&!(
						(fichier.getPath().toLowerCase().contains("web")&&fichier.getPath().toLowerCase().contains("2160"))||
								(fichier.getPath().toLowerCase().contains("web")&&fichier.getPath().toLowerCase().contains("1080"))||
								(fichier.getPath().toLowerCase().contains("web")&&fichier.getPath().toLowerCase().contains("720"))||
								(fichier.getPath().toLowerCase().contains("web")&&fichier.getPath().toLowerCase().contains("576"))||
								(fichier.getPath().toLowerCase().contains("web")&&fichier.getPath().toLowerCase().contains("480"))||
								(fichier.getPath().toLowerCase().contains("web")&&fichier.getPath().toLowerCase().contains("360"))||
								(fichier.getPath().toLowerCase().contains("blu")&&fichier.getPath().toLowerCase().contains("2160"))||
								(fichier.getPath().toLowerCase().contains("blu")&&fichier.getPath().toLowerCase().contains("1080"))||
								(fichier.getPath().toLowerCase().contains("blu")&&fichier.getPath().toLowerCase().contains("720"))||
								(fichier.getPath().toLowerCase().contains("blu")&&fichier.getPath().toLowerCase().contains("576"))||
								(fichier.getPath().toLowerCase().contains("blu")&&fichier.getPath().toLowerCase().contains("480"))||
								(fichier.getPath().toLowerCase().contains("blu")&&fichier.getPath().toLowerCase().contains("360"))||
								(fichier.getPath().toLowerCase().contains("remux")&&fichier.getPath().toLowerCase().contains("2160"))||
								(fichier.getPath().toLowerCase().contains("remux")&&fichier.getPath().toLowerCase().contains("1080"))||
								(fichier.getPath().toLowerCase().contains("remux")&&fichier.getPath().toLowerCase().contains("720"))||
								(fichier.getPath().toLowerCase().contains("remux")&&fichier.getPath().toLowerCase().contains("576"))||
								(fichier.getPath().toLowerCase().contains("remux")&&fichier.getPath().toLowerCase().contains("480"))||
								(fichier.getPath().toLowerCase().contains("remux")&&fichier.getPath().toLowerCase().contains("360"))||
								(fichier.getPath().toLowerCase().contains("dvd"))
				)) {
					System.out.println("Delete " + fichier.getPath());
					try {
						FileUtils.deleteDirectory(fichier);
					} catch (IOException ex) {
						ex.printStackTrace();
					}
				}
			}
			try {
				System.out.println("Wait");
				for(int i=1;i<60;i++) {
					Thread.sleep(60000);
					System.out.println(60-i);
				}
			} catch (Exception ex){
				ex.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		DeleteNotWebBluDVDRemux epguides = new DeleteNotWebBluDVDRemux();

	}

}
