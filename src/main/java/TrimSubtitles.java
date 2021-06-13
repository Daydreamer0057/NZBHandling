import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

public class TrimSubtitles {
	FileWriter fw;
	PrintWriter pw;

	public TrimSubtitles() {
		try {
			File base =  new File("z://test/temp");
			File[] fichiers = base.listFiles();

			for(File fichier : fichiers){
				if(fichier.getName().endsWith(".srt")){
					//int index = fichier.getPath().indexOf(".srt");
					//String line = fichier.getPath().substring(0,index+4);
					String line = fichier.getPath().replaceAll("_","'");
					int compteur = 0;
//					while (new File(line).exists()) {
//						line = line.substring(0,line.length()-4);
//						line = line + compteur + ".srt";
//						compteur++;
//					}
					fichier.renameTo(new File(line));
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}

	}

	public String returnDate(Date date) {
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(date);
		SimpleDateFormat fmt = new SimpleDateFormat("dd-MMM-yyyy");
		fmt.setCalendar(gc);
		return fmt.format(gc.getTime());
	}

	public static void main(String[] args) {
		TrimSubtitles populate = new TrimSubtitles();

	}

}
