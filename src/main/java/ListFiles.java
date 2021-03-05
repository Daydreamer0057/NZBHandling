import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.file.AccessDeniedException;
import java.nio.file.FileSystemException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
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
			fw = new FileWriter("f://log/" + ms + "_log_populate.txt");
			pw = new PrintWriter(fw);

//			File base = new File("z://series");
			
			File base = new File("z://test/film2");

			File[] fichiers = base.listFiles();

			HashSet<File> listeFichier = new HashSet<File>();
			ArrayList<File> listeDirectory = new ArrayList<File>();

//			listeDirectory.add(new File("z://Film"));
			listeDirectory.add(new File("z://Series"));
			listeDirectory.add(new File("z://Games"));
			listeDirectory.add(new File("z://Onedrive"));

			for (File fichier : fichiers) {
				if (fichier.isDirectory()) {
					listeDirectory.add(fichier);
				} else {
					try {
						BasicFileAttributes attrs = Files.readAttributes(fichier.toPath(), BasicFileAttributes.class);
						FileTime createTime = attrs.creationTime();

						FileTime modifiedTime = attrs.lastModifiedTime();

						// FileTime accessTime = attrs.lastAccessTime();

						pw.println(fichier.getPath() + "      Created " + returnDate(new Date(createTime.toMillis()))
								+ "    Modif " + returnDate(new Date(modifiedTime.toMillis())));
					} catch (AccessDeniedException ex) {
						System.out.println(ex);
					}
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
							try {
								BasicFileAttributes attrs = Files.readAttributes(fichierTemp.toPath(),
										BasicFileAttributes.class);
								FileTime createTime = attrs.creationTime();

								FileTime modifiedTime = attrs.lastModifiedTime();

								// FileTime accessTime = attrs.lastAccessTime();

								pw.println(fichierTemp.getPath() + "      Created "
										+ returnDate(new Date(createTime.toMillis())) + "    Modif "
										+ returnDate(new Date(modifiedTime.toMillis())));
							} catch (AccessDeniedException ex) {
								System.out.println(ex);
							} catch (FileSystemException ex) {
								System.out.println(ex);
							} catch (InvalidPathException ex) {
								System.out.println(ex);
							}

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
