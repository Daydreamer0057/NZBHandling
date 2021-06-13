import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;

public class KeepMovieSubtitle {
	FileWriter fw;
	PrintWriter pw;

	public KeepMovieSubtitle() {
		try {
			File base =  new File("z://test/temp");
			File[] fichiers = base.listFiles();

			int compteur = 0;
			for(File fichier : fichiers){
				//System.out.println(compteur + " / "+fichiers.length);

				if(fichier.getName().endsWith(".mp4") || fichier.getName().endsWith(".mkv") || fichier.getName().endsWith(".avi")){
					String name = FilenameUtils.removeExtension(fichier.getPath())+".srt";
					File fichierTemp = new File(name);
					if(!fichierTemp.exists()){
						compteur++;
						fichier.delete();
						System.out.println(fichier.getName());
					}
				}
			}
			System.out.println("Deleted "+compteur);
		}catch(Exception ex){
			ex.printStackTrace();
		}

	}


	public static void main(String[] args) {
		KeepMovieSubtitle populate = new KeepMovieSubtitle();

	}

}
