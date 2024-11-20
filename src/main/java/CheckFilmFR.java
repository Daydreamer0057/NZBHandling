import com.uwetrottmann.tmdb2.Tmdb;
import com.uwetrottmann.tmdb2.entities.BaseMovie;
import com.uwetrottmann.tmdb2.entities.MovieResultsPage;
import com.uwetrottmann.tmdb2.services.SearchService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import retrofit2.Call;
import retrofit2.Response;

import java.io.File;
import java.nio.file.FileSystemException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckFilmFR {
	public CheckFilmFR() {
		try {
//				HashSet<File> listeFichierATraiter = FileDirParcours.getParcours("z://test/stockage", new String[]{".mp4",".mkv",".avi"});
			HashSet<File> listeFichierATraiter = FileDirParcours.getParcours("z://test/film temp", new String[]{".mp4",".mkv",".avi"});

			File[] listFiles = new File[listeFichierATraiter.size()+1];
			listeFichierATraiter.toArray(listFiles);

			int compteurFiles = 0;
			for(File fichierTemp : listFiles) {
				System.out.println("Compteur "+compteurFiles+" / "+listFiles.length);
				compteurFiles++;
				if(fichierTemp!=null) {
					String name = fichierTemp.getName();
					name = name.replace(".", " ");
					String fileName = "";
					String year = "";
					StringTokenizer stk = new StringTokenizer(name, " ");
					while (stk.hasMoreTokens()) {
						String lineTemp = stk.nextToken();
						Pattern p = Pattern.compile(".*([0-9]{4}).*");
						Matcher m = p.matcher(lineTemp.toLowerCase());

						if (m.matches()) {
							year = m.group(1);
							break;
						} else {
							fileName += lineTemp + " ";
						}
					}

					fileName = fileName.trim();

					fileName = fileName.replace("é", "e");
					fileName = fileName.replace("è", "e");
//					fileName = fileName.replace(" ","%20");

//					String auctionUrl = "https://api.themoviedb.org/3/search/movie?query="+fileName+"&primary_release_year="+year+"&include_adult=true&apikey=dcc0613cc47f8538e16634ad625b72cc";
//					String auctionUrl = "https://api.themoviedb.org/3/authentication?apikey=dcc0613cc47f8538e16634ad625b72cc";
					Tmdb tmdb = new Tmdb("dcc0613cc47f8538e16634ad625b72cc");

					SearchService searchService = tmdb.searchService();

					// Movie title and year to search for
					String movieTitle = fileName;
					String releaseYear = year;

					// Call the searchMovies method with the title and release year
					Call<MovieResultsPage> call = searchService.movie(
							movieTitle,           // The movie title to search for
							null,                 // Optional language
							null,          // Release year filter
							null,                 // Optional primary release year
							null,                 // Optional page number
							null,                 // Include adult content (null for default)
							null                  // Region
					);

					Response<MovieResultsPage> response = call.execute();

					try {
						if (response.isSuccessful()) {
							MovieResultsPage results = response.body();
							if (results != null && results.results != null && !results.results.isEmpty()) {
								for (BaseMovie resultsPageTemp : results.results) {
									Date release = resultsPageTemp.release_date;
									if (release != null) {
										GregorianCalendar gc = new GregorianCalendar();
										gc.setTime(release);

										if (fichierTemp.getName().toLowerCase().contains(resultsPageTemp.title.toLowerCase()) && fichierTemp.getName().toLowerCase().contains("" + gc.get(Calendar.YEAR)) && resultsPageTemp.original_language.equals("fr")) {
											System.out.println(fichierTemp.getName());
											int compteur = 0;
											String chemin = "z://test/films france/" + fichierTemp.getName();
											String ext = FilenameUtils.getExtension(fichierTemp.getName());
											String nameFile = FilenameUtils.removeExtension(fichierTemp.getName());

											if(new File("z://test/films france/" + fichierTemp.getName()).exists()){
												ext = FilenameUtils.getExtension(fichierTemp.getName());
												nameFile = FilenameUtils.removeExtension(fichierTemp.getName());
												chemin = "z://test/films france/"+nameFile+"_0."+ext;
											}
											compteur++;
											while(new File("z://test/films france/"+nameFile+"_"+compteur+"."+ext).exists()) {
												chemin = "z://test/films france/"+nameFile+"_"+compteur+"."+ext;
												compteur++;
											}
											try {
												FileUtils.moveFile(fichierTemp, new File(chemin));
											} catch(FileSystemException ex){
												ex.printStackTrace();
											}

//											break;
										}
									}
								}
							}
						}
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}
		} catch (Exception ex){
			ex.printStackTrace();
		}
	}

	public long calculTaille(File file) {
		long taille = 0;
		HashSet<File> listeFichierDownload = new HashSet<File>();
		ArrayList<File> listeDirectoryDownload = new ArrayList<File>();
		listeDirectoryDownload.add(file);

		while (listeDirectoryDownload.size() > 0) {
//			System.out.println(listeFichierDownload.size() + "    " + listeDirectoryDownload.size());
			File[] fichierListeDownload = listeDirectoryDownload.get(0).listFiles();

			if (fichierListeDownload != null) {
				for (File fichierTempDownload : fichierListeDownload) {
					if (fichierTempDownload.isDirectory()) {
						listeDirectoryDownload.add(fichierTempDownload);
					} else {
						taille += fichierTempDownload.length();
					}
				}
			}
			listeDirectoryDownload.remove(0);
		}
		return taille;
	}

	public static void main(String[] args) {
		CheckFilmFR epguides = new CheckFilmFR();

	}

}
