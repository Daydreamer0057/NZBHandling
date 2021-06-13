import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.*;

public class SortNZBFilm {
	Connection conn;

	public SortNZBFilm() {
		try {
			File basePrincipal = new File("u://nzb/Film En Attente");

			File[] listN = basePrincipal.listFiles();

			ArrayList<File> listNZB = new ArrayList<File>();

			for (File fichierNZB : listN) {
				if (fichierNZB.isFile()) {
					listNZB.add(fichierNZB);
				}
			}

			File baseSeries = new File("z://film/new");

			File[] fichiers = baseSeries.listFiles();

			HashSet<File> listeFichier = new HashSet<File>();
			ArrayList<File> listeDirectory = new ArrayList<File>();
			// listeDirectory.add(new File("z://test/film"));

			for (File fichier : fichiers) {
				if (fichier.isDirectory()) {
					listeDirectory.add(fichier);
				} else {
					if (fichier.getName().endsWith("mkv") || fichier.getName().endsWith("mp4")
							|| fichier.getName().endsWith("avi")) {
						listeFichier.add(fichier);
					}
				}
			}

			while (listeDirectory.size() > 0) {
				//System.out.println("Size "+listeDirectory.size());
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


				Class.forName("com.mysql.cj.jdbc.Driver");
				System.out.println("Driver O.K.");

				String url = "jdbc:mysql://localhost:3306/nzb?serverTimezone=UTC";
				String user = "root";
				String passwd = "test";

				conn = DriverManager.getConnection(url, user, passwd);
				System.out.println("Connexion effective !");


			for (File fichierDB : listeFichier) {
					Statement stmt = conn.createStatement();

					String sql = "Select * from movies where movie_name like \"%" + fichierDB.getName() + "%\"";

					ResultSet result = stmt.executeQuery(sql);

					if (!result.next()) {
						String query = "insert into movies (id_movies, movie_name)"
								+ " values (?, ?)";

						PreparedStatement preparedStmt = conn.prepareStatement(query);
						preparedStmt.setLong(1, 0L);
						preparedStmt.setString(2, fichierDB.getName());

						// execute the preparedstatement
						int success = preparedStmt.executeUpdate();
					}

			}


			int compteur = 0;
			int compteurMax = 0;
				Statement stmt = conn.createStatement();

				String sql = "Select movie_name from movies";

				ResultSet result = stmt.executeQuery(sql);

				ArrayList<String> listMovieName = new ArrayList<>();

				while(result.next()){
					listMovieName.add(result.getString("movie_name"));
				}

				for(String fichierFilm : listMovieName) {
					compteur++;
					int index = fichierFilm.indexOf('(') - 1;
					if (index >= 0) {
						//System.out.println(fichierFilm.getName()+"    index "+index);
						String name = fichierFilm.substring(0, index).trim();
						String year = "";
						try {
							year = fichierFilm.substring(index + 2, index + 6);
						} catch (Exception ex) {
							ex.printStackTrace();
						}

						ArrayList<String> words = new ArrayList<String>();
						StringTokenizer stk = new StringTokenizer(name, " ");
						while (stk.hasMoreTokens()) {
							String nextWord = stk.nextToken();
							if(nextWord.length()>1){
								words.add(nextWord + ".");
							}
						}

						ArrayList<String> wordsSpace = new ArrayList<String>();
						StringTokenizer stkSpace = new StringTokenizer(name, " ");
						while (stkSpace.hasMoreTokens()) {
							String nextWord = stkSpace.nextToken();
							if(nextWord.length()>1) {
								wordsSpace.add(nextWord + " ");
							}
						}

						//System.out.println(compteurMax+" / "+listeFichier.size());
						compteurMax++;

						for (File fichierNZB : listNZB) {
							//System.out.println(name+" "+year+"    "+fichierNZB.getName());

							ArrayList<String> arrayTest = new ArrayList<>();
							for (String checkWord : words) {
								if (fichierNZB.getName().toLowerCase().contains(checkWord.toLowerCase())) {
									arrayTest.add(checkWord);
								}
							}

							ArrayList<String> arrayTestSpace = new ArrayList<>();
							for (String checkWordSpace : wordsSpace) {
								if (fichierNZB.getName().toLowerCase().contains(checkWordSpace.toLowerCase())) {
									arrayTestSpace.add(checkWordSpace);
								}
							}

							if ((arrayTest.size() == words.size() || arrayTestSpace.size() == wordsSpace.size()) && !year.equals("") && fichierNZB.getName().contains(year)) {
								try {
									fichierNZB.delete();
								} catch (Exception ioex) {
									ioex.printStackTrace();
								}
								System.out.println(name + " " + year + "    " + fichierNZB.getName());
							}
						}
					}
				}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void main(String[] args) {
		SortNZBFilm populate = new SortNZBFilm();

	}

}
