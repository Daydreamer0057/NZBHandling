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

import org.apache.log4j.Logger;

import entity.NZBEntity;

public class Populate {
	Properties appProps;
	String rootPath;
	NZBEntity nzbEntity = new NZBEntity();
	Connection conn;
	FileWriter fw;
	PrintWriter pw;

	final static Logger logger = Logger.getLogger(Populate.class);

	public Populate() {
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

		File base = new File("f://NZB");

		File[] fichiers = base.listFiles();

		HashSet<File> listeFichier = new HashSet<File>();
		ArrayList<File> listeDirectory = new ArrayList<File>();

		for (File fichier : fichiers) {
			if (fichier.isDirectory()) {
				listeDirectory.add(fichier);
			} else {
				if (!fichier.getName().endsWith("rar"))
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
			System.out.println("Fichier " + compteur + " / " + listeFichier.size());
			compteur++;
			boolean nameTest = false;
			boolean titleTest = false;
			try {
				// String lineFichier = FileUtils.readFileToString(fichierTemp);

				FileReader fr = new FileReader(fichierTemp);
				BufferedReader br = new BufferedReader(fr);

				String line = "";
				String mediaName = "";
				String fileName = "";
				int compteurTest = 0;
				while (line != null) {
					line = br.readLine();
					if (line != null) {
						if (line.indexOf("Name:") != -1) {
							line = br.readLine();
							line = br.readLine();
							line = br.readLine();
							mediaName = line.trim();
							mediaName = mediaName.replace('\'', ' ');
						}

						if (line.indexOf(".rar") != -1 || line.indexOf(".par") != -1 || line.indexOf(".mkv") != -1
								|| line.indexOf(".mp4") != -1 || line.indexOf(".avi") != -1
								|| line.indexOf(".pdf") != -1 || line.indexOf(".par2") != -1
								|| line.indexOf("yEnc") != -1) {
							fileName += line + " ";
							compteurTest++;
							if (compteurTest == 5) {
								break;
							}
						}
					}
				}
				if (!mediaName.equals("") && !fileName.equals("")) {
					String queryExist = ("SELECT * from articles_full  where filename = ? ");
					PreparedStatement stmt = conn.prepareStatement(queryExist);
					if (fileName.length() > 255) {
						fileName = fileName.substring(0, 255);
					}
					stmt.setString(1, fileName);
					long debutQuery = System.currentTimeMillis();
					ResultSet result = stmt.executeQuery();
					long finQuery = System.currentTimeMillis() - debutQuery;
					System.out.println("Temps " + finQuery + "    " + change + "   i " + fileName.length() / 255);
					if (!result.next()) {
						for (int i = 0; i < fileName.length() / 255; i++) {
							String query = "insert into articles_full (id_nzb, medianame,filename)"
									+ " values (?, ?, ?)";

							PreparedStatement preparedStmt = conn.prepareStatement(query);
							preparedStmt.setLong(1, 0L);
							if (mediaName.length() > 255) {
								mediaName = mediaName.substring(0, 255);
							}
							preparedStmt.setString(2, mediaName);
							fileName = fileName.substring(0 + i * 255, 255 + i * 255);
							preparedStmt.setString(3, fileName);

							// execute the preparedstatement
							int success = preparedStmt.executeUpdate();

							boolean successResult = (success > 0);
							if (successResult) {
								change++;
							}
						}

						mediaName = "";
						fileName = "";
					}
				}
				br.close();
				fr.close();
				// fichierTemp.renameTo(new File(fichierTemp + ".old"));
			} catch (Exception ex) {
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
		Populate populate = new Populate();

	}

}
