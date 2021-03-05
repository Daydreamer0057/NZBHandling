import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;

import org.apache.commons.io.FileUtils;

public class MoveGames {
	FileWriter fw;
	PrintWriter pw;

	public MoveGames() {
		long ms = System.currentTimeMillis();
		System.out.println("debut " + ms);
		try {
			fw = new FileWriter("f://log/" + ms + "_log_populate.txt");
			pw = new PrintWriter(fw);

			File base = new File("z://Games");

			File[] fichiers = base.listFiles();

			HashSet<File> listeFichier = new HashSet<File>();
			ArrayList<File> listeDirectory = new ArrayList<File>();

			for (File fichier : fichiers) {
				if (fichier.isDirectory()) {
					listeDirectory.add(fichier);
				} else if (fichier.isFile()) {
					if (!fichier.getName().endsWith("!qb")) {
						listeFichier.add(fichier);
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
						} else if (fichierTemp.isFile()) {
							if (!fichier.getName().endsWith("!qb") || !fichier.getName().endsWith("!qB")) {
								listeFichier.add(fichierTemp);
							}
						}
					}
				}
				listeDirectory.remove(0);
			}

			System.out.println("Debut");
			int compteur = 0;
			for (File fichierTemp : listeFichier) {
				compteur++;
				System.out.println("Compteur " + compteur + " / " + listeFichier.size());
				listeFichier.forEach(res -> {
					while (Thread.activeCount() > 50) {
						try {
							Thread.sleep(1000);
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
					final String name = res.getPath().replace('z', 'h');
					Thread t = new Thread(() -> {
						try {
							FileUtils.moveFileToDirectory(res, new File(name), true);
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					});
					t.start();
				});

			}

			pw.flush();
			fw.flush();
			pw.close();
			fw.close();
			System.out.println("fin " + ((System.currentTimeMillis() - ms) / 60000));
		} catch (

		Exception e) {
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
		MoveGames populate = new MoveGames();

	}

}
