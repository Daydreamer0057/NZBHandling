import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
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

public class PopulateNZB {
	Properties appProps;
	String rootPath;
	NZBEntity nzbEntity = new NZBEntity();
	Connection conn;
	FileWriter fw;
	PrintWriter pw;

	final static Logger logger = Logger.getLogger(PopulateNZB.class);

	public PopulateNZB() {
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

		File basePrincipal = new File("e://NZBFile");

		File[] listPrincipal = basePrincipal.listFiles();

		HashSet<File> listePrincipale = new HashSet<File>();

		for (File fichier : listPrincipal) {
			listePrincipale.add(fichier);
		}

		File base = new File("e://NZBFile");

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

		int compteur = 0;
		int total = 0;
		int change = 0;
		for (File fichierTemp : listeFichier) {
			compteur++;
			try {
				// BasicFileAttributes attrs = Files.readAttributes(fichierTemp.toPath(),
				// BasicFileAttributes.class);
				// FileTime createTime = attrs.creationTime();
				// long today = System.currentTimeMillis() - (60 * 60 * 24 * 1000);
				//
				// if (createTime.toMillis() > today) {

				String lineFichier = FileUtils.readFileToString(fichierTemp);

				FileReader fr = new FileReader(fichierTemp);
				BufferedReader br = new BufferedReader(fr);

				String line = "";
				String realName = "";
				while (line != null) {
					line = br.readLine();
					if (line!=null&&line.contains("subject")) {
						//if (!listePrincipale.contains(fichierTemp)) {
							String queryExist = ("SELECT * from articles_nzb where filename = ?");
							PreparedStatement stmt = conn.prepareStatement(queryExist);

							stmt.setString(1, fichierTemp.getName());
							ResultSet result = stmt.executeQuery();

							if (!result.next()) {

								String query = "insert into articles_nzb (id_nzb, real_name,nzb_name,filename)"
										+ " values (?, ?, ?,?)";

								PreparedStatement preparedStmt = conn.prepareStatement(query);
								preparedStmt.setLong(1, 0L);
								realName = fichierTemp.getName();
								realName = realName.substring(0, realName.length() - 4);
								preparedStmt.setString(2, realName);
								preparedStmt.setString(3, line);
								preparedStmt.setString(4, fichierTemp.getName());

								// execute the preparedstatement
								int success = preparedStmt.executeUpdate();

								boolean successResult = (success > 0);
								if (successResult) {
									change++;
								}
								break;
							} else {
								total++;
						//	}
						}
					}
					System.out.println("Fichier " + compteur + " / " + listeFichier.size() + "    changes " + change
							+ "    ignore " + total);
				}
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
		PopulateNZB populate = new PopulateNZB();

	}

}
