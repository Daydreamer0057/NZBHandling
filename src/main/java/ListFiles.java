import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;

public class ListFiles {
	FileWriter fw;
	PrintWriter pw;

	public ListFiles() {
		long ms = System.currentTimeMillis();
		System.out.println("debut " + ms);
		try {
			fw = new FileWriter("e://log/" + ms + "_log_populate.txt");
			pw = new PrintWriter(fw);

//			File base = new File("z://series");
			
			HashSet<File> listeFichier = FileDirParcours.getParcours("z://onedrive", new String[]{"*"});
			while(Thread.activeCount()>2){
				System.out.println(Thread.activeCount());
				Thread.sleep(1000);
			}
			for(File fichierTemp : listeFichier){
				pw.println(fichierTemp.getPath());
				pw.flush();
			}

			HashSet<File> listeFichier2 = FileDirParcours.getParcours("z://Books", new String[]{"*"});
			for(File fichierTemp : listeFichier2){
				pw.println(fichierTemp.getPath());
				pw.flush();
			}
			while(Thread.activeCount()>2){
				System.out.println(Thread.activeCount());
				Thread.sleep(1000);
			}

			HashSet<File> listeFichier3 = FileDirParcours.getParcours("z://Documentaires", new String[]{"*"});
			for(File fichierTemp : listeFichier3){
				pw.println(fichierTemp.getPath());
				pw.flush();
			}
			while(Thread.activeCount()>2){
				System.out.println(Thread.activeCount());
				Thread.sleep(1000);
			}

			HashSet<File> listeFichier4 = FileDirParcours.getParcours("z://Film", new String[]{"*"});
			for(File fichierTemp : listeFichier4){
				pw.println(fichierTemp.getPath());
				pw.flush();
			}
			while(Thread.activeCount()>2){
				System.out.println(Thread.activeCount());
				Thread.sleep(1000);
			}

			HashSet<File> listeFichier5 = FileDirParcours.getParcours("z://Series", new String[]{"*"});
			for(File fichierTemp : listeFichier5){
				pw.println(fichierTemp.getPath());
				pw.flush();
			}
			while(Thread.activeCount()>2){
				System.out.println(Thread.activeCount());
				Thread.sleep(1000);
			}

			HashSet<File> listeFichier6 = FileDirParcours.getParcours("z://Temp", new String[]{"*"});
			for(File fichierTemp : listeFichier6){
				pw.println(fichierTemp.getPath());
				pw.flush();
			}
			while(Thread.activeCount()>2){
				System.out.println(Thread.activeCount());
				Thread.sleep(1000);
			}

			pw.flush();
			fw.flush();
			pw.close();
			fw.close();
			System.out.println("fin " + ((System.currentTimeMillis() - ms) / 60000));
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
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
		ListFiles populate = new ListFiles();

	}

}
