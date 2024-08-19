import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
			
			HashSet<File> listeFichier = new HashSet<File>();
			ArrayList<File> listeDirectory = new ArrayList<File>();

			listeDirectory.add(new File("z://Books"));
			listeDirectory.add(new File("z://Documentaires"));
			listeDirectory.add(new File("z://Film"));
			listeDirectory.add(new File("z://Series"));

			listeDirectory.add(new File("z://temp"));

			for (File fichier : listeFichier) {
				if (fichier.isDirectory()) {
					listeDirectory.add(fichier);
				} else {
//						BasicFileAttributes attrs >= Files.readAttributes(fichier.toPath(), BasicFileAttributes.class);
//						FileTime createTime = attrs.creationTime();
//
//						FileTime modifiedTime = attrs.lastModifiedTime();

						// FileTime accessTime = attrs.lastAccessTime();

//						pw.println(fichier.getPath() + "      Created " + returnDate(new Date(createTime.toMillis()))
//								+ "    Modif " + returnDate(new Date(modifiedTime.toMillis())));
						pw.println(fichier.getPath()+"/"+fichier.getName());
					}

			}

			while (listeDirectory.size() > 0) {
				File fichier = listeDirectory.get(0);

				File[] fichierListe = fichier.listFiles();

				if (fichierListe != null) {
					for (File fichierTemp : fichierListe) {
						if (fichierTemp.isDirectory()) {
							listeDirectory.add(fichierTemp);
						} else {
//								BasicFileAttributes attrs = Files.readAttributes(fichierTemp.toPath(),
//										BasicFileAttributes.class);
//								FileTime createTime = attrs.creationTime();
//
//								FileTime modifiedTime = attrs.lastModifiedTime();

								// FileTime accessTime = attrs.lastAccessTime();

//								pw.println(fichierTemp.getPath() + "      Created "
//										+ returnDate(new Date(createTime.toMillis())) + "    Modif "
//										+ returnDate(new Date(modifiedTime.toMillis())));
								pw.println(fichier.getPath()+"/"+fichier.getName());
							}
					}
				}
				listeDirectory.remove(0);
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
