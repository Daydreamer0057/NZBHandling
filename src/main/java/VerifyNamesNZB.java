import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Properties;

public class VerifyNamesNZB {
	Properties appProps;
	String rootPath;
	Connection conn;
	FileWriter fw;
	PrintWriter pw;

	public VerifyNamesNZB() {
		File base = new File("z://test/film");
//		File base = new File("F://The Mandalorian");

		File[] fichiers = base.listFiles();

		HashSet<File> listeFichier = new HashSet<File>();
		ArrayList<File> listeDirectory = new ArrayList<File>();
		// listeDirectory.add(new File("z://test/film"));

		for (File fichier : fichiers) {
			if (fichier.isDirectory()) {
				listeDirectory.add(fichier);
			} else {
				if (fichier.getName().endsWith("mkv") || fichier.getName().endsWith("mp4")
						|| fichier.getName().endsWith("avi") || fichier.getName().endsWith("srt")) {
					listeFichier.add(fichier);
				}
			}
		}

		while (listeDirectory.size() > 0) {
			File fichier = listeDirectory.get(0);

			File[] fichierListe = fichier.listFiles();

			for (File fichierTemp : fichierListe) {
				if (fichierTemp.isDirectory()) {
					listeDirectory.add(fichierTemp);
				} else {
					if (fichierTemp.getName().endsWith("mkv") || fichierTemp.getName().endsWith("mp4")
							|| fichierTemp.getName().endsWith("avi")) {
						listeFichier.add(fichierTemp);
					}
				}
			}
			listeDirectory.remove(0);
		}

		try {
			fw = new FileWriter("c://temp/log_verify.txt");
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
		}

		int compteur = 0;
		int renamed = 0;

		for (File fichierTemp : listeFichier) {
			System.out.println("Fichier " + compteur + " / " + listeFichier.size());
			compteur++;
			if (fichierTemp.getName().length() > 5 && fichierTemp.isFile()
					&& (fichierTemp.getName().endsWith(".mkv") || fichierTemp.getName().endsWith(".mp4")
							|| fichierTemp.getName().endsWith(".avi") || fichierTemp.getName().endsWith(".iso")
							|| fichierTemp.getName().endsWith(".ts") || fichierTemp.getName().endsWith(".cbr")
							|| fichierTemp.getName().endsWith(".cbz") || fichierTemp.getName().endsWith(".pdf"))) {
				try {
					boolean test = false;
					String nomFichier = "";
					String pathFichier = "";
					String extension = "";

					if ((fichierTemp.getName().indexOf("_1") != -1 || fichierTemp.getName().indexOf("_2") != -1
							|| fichierTemp.getName().indexOf("_3") != -1 || fichierTemp.getName().indexOf("_4") != -1
							|| fichierTemp.getName().indexOf("_5") != -1 || fichierTemp.getName().indexOf("_6") != -1
							|| fichierTemp.getName().indexOf("_7") != -1 || fichierTemp.getName().indexOf("_8") != -1
							|| fichierTemp.getName().indexOf("_9") != -1)
							&& (!fichierTemp.getName().endsWith("yEnc.mkv"))) {
						nomFichier = fichierTemp.getName().substring(0, fichierTemp.getName().length() - 6);
						extension = fichierTemp.getName().substring(fichierTemp.getName().length() - 4,
								fichierTemp.getName().length());
						nomFichier = nomFichier + extension;
						Path p2 = Paths.get(fichierTemp.getPath());
						Path folder2 = p2.getParent();

						String path2 = folder2.toString();
						String chemin2 = path2.replaceAll("\\\\", "/");

						pathFichier = nomFichier.substring(0, nomFichier.length() - 4);
						nomFichier = chemin2 + "/" + nomFichier;

						test = true;
					}
					if (fichierTemp.getName().lastIndexOf("(1)") != -1 && test == false) {
						nomFichier = fichierTemp.getName().substring(0, fichierTemp.getName().lastIndexOf("(1)") - 1);
						extension = fichierTemp.getName().substring(fichierTemp.getName().length() - 4,
								fichierTemp.getName().length());
						nomFichier = nomFichier + extension;
						nomFichier = nomFichier.trim();
						Path p2 = Paths.get(fichierTemp.getPath());
						Path folder2 = p2.getParent();

						String path2 = folder2.toString();
						String chemin2 = path2.replaceAll("\\\\", "/");

						pathFichier = nomFichier.substring(0, nomFichier.length() - 4);
						nomFichier = chemin2 + "/" + nomFichier;

						test = true;

					}
					if (fichierTemp.getName().lastIndexOf("(2)") != -1 && test == false) {
						nomFichier = fichierTemp.getName().substring(0, fichierTemp.getName().lastIndexOf("(2)") - 1);
						nomFichier = nomFichier.trim();
						extension = fichierTemp.getName().substring(fichierTemp.getName().length() - 4,
								fichierTemp.getName().length());
						nomFichier = nomFichier + extension;
						Path p2 = Paths.get(fichierTemp.getPath());
						Path folder2 = p2.getParent();

						String path2 = folder2.toString();
						String chemin2 = path2.replaceAll("\\\\", "/");

						pathFichier = nomFichier.substring(0, nomFichier.length() - 4);
						nomFichier = chemin2 + "/" + nomFichier;

						test = true;

					}

					if (fichierTemp.getName().indexOf("[") != -1 && test == false) {
						nomFichier = fichierTemp.getName().substring(0, fichierTemp.getName().indexOf("[") - 1);
						nomFichier = nomFichier.trim();
						extension = fichierTemp.getName().substring(fichierTemp.getName().length() - 4,
								fichierTemp.getName().length());
						nomFichier = nomFichier + extension;
						Path p2 = Paths.get(fichierTemp.getPath());
						Path folder2 = p2.getParent();

						String path2 = folder2.toString();
						String chemin2 = path2.replaceAll("\\\\", "/");

						pathFichier = nomFichier.substring(0, nomFichier.length() - 4);
						nomFichier = chemin2 + "/" + nomFichier;

						test = true;

					}

					if (!test) {
						nomFichier = fichierTemp.getPath();
						pathFichier = fichierTemp.getName().substring(0, fichierTemp.getName().length() - 4);
					}

					Statement stmt = conn.createStatement();

					String sql = "Select * from articles_nzb where nzb_name like '%" + pathFichier + "%'";

					ResultSet result = stmt.executeQuery(sql);

					if (result.next()) {
						extension = nomFichier.substring(nomFichier.length() - 4, nomFichier.length());

						// String fileName = fichierTemp.getPath();
						Path p = Paths.get(nomFichier);
						Path folder = p.getParent();

						String path = folder.toString();
						String chemin = path.replaceAll("\\\\", "/");

						File out = new File(chemin + "/" + result.getString("real_name") + extension);
						int compteurFichier = 0;
						while (out.exists()) {
							out = new File(
									chemin + "/" + result.getString("real_name") + "_" + compteurFichier + extension);
							compteurFichier++;
						}
						System.out.println("    " + fichierTemp.getName() + "    " + out.getName());
						fichierTemp.renameTo(out);

						// System.out.println("Found " + chemin + "/Convert/" +
						// result.getString("medianame") + extension+ " " + fichierTemp.getName());
						// System.out.println(out.getPath());
						pw.println(out.getPath());
						pw.flush();
						renamed++;
						// FileUtils.copyFile(fichierTemp, new
						// File(chemin+"/Convert/"+result.getString("medianame")+extension));
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
		System.out.println("Fichiers rennom�s " + renamed);
		pw.close();
		try {
			fw.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public static void main(String[] args) {
		VerifyNamesNZB populate = new VerifyNamesNZB();

	}

}
