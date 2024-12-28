package film;

import Utilities.FileDirParcoursArrayList;
import com.uwetrottmann.tmdb2.Tmdb;
import com.uwetrottmann.tmdb2.entities.Genre;
import com.uwetrottmann.tmdb2.entities.Movie;
import com.uwetrottmann.tmdb2.entities.MovieResultsPage;
import com.uwetrottmann.tmdb2.services.MoviesService;
import com.uwetrottmann.tmdb2.services.SearchService;
import org.apache.commons.io.FileUtils;
import retrofit2.Call;
import retrofit2.Response;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import Utilities.FileDirParcours;

public class GetSciFiFilms {

    public GetSciFiFilms() throws Exception{

        ArrayList<File> listeFichierATraiter = FileDirParcoursArrayList.getParcours("z://film/new", new String[]{".mp4",".mkv",".avi"});
        listeFichierATraiter.addAll(FileDirParcoursArrayList.getParcours("z://film/treated", new String[]{".mp4",".mkv",".avi"}));


        File[] listFiles = new File[listeFichierATraiter.size()+1];
        listeFichierATraiter.toArray(listFiles);

        int compteur = 0;
        for(File fichierTemp : listFiles) {
            System.out.println(compteur+"  ");
            compteur++;
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
                        year,          // Release year filter
                        null,                 // Optional primary release year
                        null,                 // Optional page number
                        null,                 // Include adult content (null for default)
                        null                  // Region
                );

                Response<MovieResultsPage> response = call.execute();

                if (response.isSuccessful()) {
                    MovieResultsPage results = response.body();
                    if (results != null && results.results != null && !results.results.isEmpty()) {
                        MoviesService moviesService = tmdb.moviesService();

                        int movieId = results.results.get(0).id; // Example: 550 is the ID for Fight Club

                        // Call the summary method to get movie details
                        Call<Movie> callMovie = moviesService.summary(movieId, "en-US");

                        try {
                            // Execute the request
                            Response<Movie> responseMovie = callMovie.execute();

                            if (responseMovie.isSuccessful()) {
                                Movie movie = responseMovie.body();
                                if (movie != null) {
                                    if(movie.vote_average>=7){
                                        List<Genre> listGenre = movie.genres;
                                        for(Genre genreTemp : listGenre) {
                                            if(genreTemp.name.toLowerCase().contains("science fiction")){
                                                System.out.println(fichierTemp.getName());
                                                FileUtils.moveFile(fichierTemp,new File("e:/video/film/"+fichierTemp.getName()));
                                            }
                                        }
                                    }
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        try {
            GetSciFiFilms verifyDuration = new GetSciFiFilms();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}

