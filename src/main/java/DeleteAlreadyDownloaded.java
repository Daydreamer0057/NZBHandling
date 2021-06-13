import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

public class DeleteAlreadyDownloaded {
	FileWriter fw;
	PrintWriter pw;

	public DeleteAlreadyDownloaded() {
		try {
			File base =  new File("");
			File[] fichiers = base.listFiles();

			for(File fichier : fichiers){
				if(fichier.getName().endsWith(".srt")){
					int index = fichier.getPath().indexOf(".srt");
					String line = fichier.getPath().substring(0,index+4);
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
		DeleteAlreadyDownloaded populate = new DeleteAlreadyDownloaded();

	}

}
