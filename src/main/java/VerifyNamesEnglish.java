import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Properties;
import java.util.StringTokenizer;

public class VerifyNamesEnglish {
	Properties appProps;
	String rootPath;
	Connection conn;
	FileWriter fw;
	PrintWriter pw;

	public VerifyNamesEnglish() {
		File base = new File("z://temp/film");

		File[] fichiers = base.listFiles();

		HashSet<File> listeFichier = new HashSet<File>();
		ArrayList<File> listeDirectory = new ArrayList<File>();

		for (File fichier : fichiers) {
			if (fichier.isDirectory()) {
				listeDirectory.add(fichier);
			} else {
				listeFichier.add(fichier);
			}
		}

		while (listeDirectory.size() > 0) {
			File fichier = listeDirectory.get(0);

			File[] fichierListe = fichier.listFiles();

			for (File fichierTemp : fichierListe) {
				if (fichierTemp.isDirectory()) {
					listeDirectory.add(fichierTemp);
				} else {
					listeFichier.add(fichierTemp);
				}
			}
			listeDirectory.remove(0);
		}

		try {
			fw = new FileWriter("c://temp/log_verify_english.txt");
			pw = new PrintWriter(fw);
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("Driver O.K.");

			String url = "jdbc:mysql://10.0.0.100:3306/entries?serverTimezone=UTC";
			String user = "test";
			String passwd = "test";

			conn = DriverManager.getConnection(url, user, passwd);
			System.out.println("Connexion effective !");

		} catch (Exception e) {
			e.printStackTrace();
		}

		int compteur = 0;
		int renamed = 0;

		for (File fichierTemp : listeFichier) {
			System.out.println("Fichier " + compteur + " / " + listeFichier.size());
			compteur++;
			boolean testFind = false;
			StringTokenizer stk = new StringTokenizer(fichierTemp.getName(), " ");
			while (stk.hasMoreTokens()) {
				String word = stk.nextToken();

				try {
					Statement stmt = conn.createStatement();

					String sql = "Select word from entries where word = \"" + word + "\"";

					ResultSet result = stmt.executeQuery(sql);

					if (result.next()) {
						testFind = true;
						renamed++;
						break;
					}
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
			stk = new StringTokenizer(fichierTemp.getName(), ".");
			while (stk.hasMoreTokens()) {
				String word = stk.nextToken();

				try {
					Statement stmt = conn.createStatement();

					String sql = "Select word from entries where word = \"" + word + "\"";

					ResultSet result = stmt.executeQuery(sql);

					if (result.next()) {
						testFind = true;
						renamed++;
						break;
					}
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}

			if (!testFind) {
				fichierTemp.renameTo(new File(fichierTemp.getPath() + ".old"));
				System.out.println(fichierTemp.getPath());
				pw.println(fichierTemp.getPath());
			}
		}
		System.out.println("Fichiers rennomés " + renamed);
		pw.close();
		try {
			fw.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public static void main(String[] args) {
		VerifyNamesEnglish populate = new VerifyNamesEnglish();

	}

}
