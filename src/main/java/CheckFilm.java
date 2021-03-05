import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import entity.NZBEntity;

public class CheckFilm {
	Properties appProps;
	String rootPath;
	NZBEntity nzbEntity = new NZBEntity();
	Connection conn;
	FileWriter fw;
	PrintWriter pw;

	final static Logger logger = Logger.getLogger(CheckFilm.class);

	public CheckFilm() {
		try {
			fw = new FileWriter("c://temp/log_populate.txt");
			pw = new PrintWriter(fw);

			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("Driver O.K.");

			String url = "jdbc:mysql://10.0.0.100:3306/nzb?serverTimezone=UTC";
			String user = "test";
			String passwd = "test";

			conn = DriverManager.getConnection(url, user, passwd);
			System.out.println("Connexion effective !");

		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}

		// NZBService nzbService = new NZBService();

		File basePrincipal = new File("f://NZBFile/new");

		File[] listPrincipal = basePrincipal.listFiles();

		HashSet<File> listePrincipale = new HashSet<File>();

		for (File fichier : listPrincipal) {
			listePrincipale.add(fichier);
		}

		int compteur = 0;
		int total = 0;
		int change = 0;
		for (File fichierTemp : listePrincipale) {
			compteur++;
			try {
				String lineFichier = FileUtils.readFileToString(fichierTemp);

				FileReader fr = new FileReader(fichierTemp);
				BufferedReader br = new BufferedReader(fr);

				String line = "";
				String realName = "";
				while (line != null) {
					line = br.readLine();
					if (line!=null&&line.contains("subject")) {
						realName = fichierTemp.getName();
						realName = realName.substring(0, realName.length() - 4);
							String queryExist = ("SELECT * from articles_nzb where nzb_name like '%"+realName+"%'");
							PreparedStatement stmt = conn.prepareStatement(queryExist);

							//stmt.setString(1, line);
//							realName = fichierTemp.getName();
//							realName = realName.substring(0, realName.length() - 4);
//							stmt.setString(1, realName);
							ResultSet result = stmt.executeQuery();

							if (!result.next()) {
								FileUtils.copyFile(fichierTemp, new File("c://nzb/new/"+fichierTemp.getName()));
								break;
							} else {
								total++;
								break;
							}
					}
				}
				System.out.println("Fichier " + compteur + " / " + listePrincipale.size() + "    changes " + change
						+ "    ignore " + total);
				br.close();
				fr.close();
			}
			// fichierTemp.renameTo(new File(fichierTemp + ".old"));
			catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		try

		{
			conn.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		System.out.println("Chargement " + change);
	}

	public static void main(String[] args) {
		CheckFilm populate = new CheckFilm();

	}

}
