import com.uwetrottmann.tmdb2.Tmdb;
import com.uwetrottmann.tmdb2.entities.Movie;
import com.uwetrottmann.tmdb2.entities.MovieResultsPage;
import com.uwetrottmann.tmdb2.services.MoviesService;
import com.uwetrottmann.tmdb2.services.SearchService;
import retrofit2.Call;
import retrofit2.Response;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VerifyDuration {

    public VerifyDuration() throws Exception{
        HashMap<String,String> map = new HashMap<>();

        FileReader fr = new FileReader(new File("e:/log/temp.csv"));
        BufferedReader br = new BufferedReader(fr);

        String line = "";

        while(line!=null){
            line = br.readLine();
            if(line!=null){
                try {
                    String duration = line.substring(line.lastIndexOf(",") + 1);
                    String name = line.substring(0, line.indexOf(duration) - 1);
                    String taille = name.substring(name.lastIndexOf(",") + 1);
                    name = name.substring(0, name.indexOf(taille) - 1);
                    map.put(name, duration);
                } catch (Exception ex){
                }
            }
        }

        br.close();
        fr.close();

        HashSet<File> listeFichierATraiter = FileDirParcours.getParcours("z://test/film temp", new String[]{".mp4",".mkv",".avi"});

        File[] listFiles = new File[listeFichierATraiter.size()+1];
        listeFichierATraiter.toArray(listFiles);

//        int compteur = 0;
        for(File fichierTemp : listFiles) {
//            System.out.println(compteur+" / "+listFiles.length);
//            compteur++;
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
                                    int durationMovie = movie.runtime;
                                    String durationFile = map.get(fichierTemp.getName());
                                    int durationFileInt = 0;
                                    if(durationFile!=null) {
                                        StringTokenizer stk2 = new StringTokenizer(durationFile, ":");
                                        durationFileInt += Integer.parseInt(stk2.nextToken()) * 60;
                                        durationFileInt += Integer.parseInt(stk2.nextToken());
                                    }

//                                    if(!(durationMovie-1==durationFileInt||durationMovie+1==durationFileInt||(durationMovie==durationFileInt-1)||(durationMovie==durationFileInt+1)||(durationMovie==durationFileInt))){
                                    if(durationMovie<durationFileInt&&(durationFileInt-durationMovie>5)){
                                        fichierTemp.delete();
                                        System.out.println(fichierTemp.getPath()+"    "+durationMovie+"    "+durationFileInt);
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
    public String getVideoDuration(String videoFilePath) {
        String duration = null;
        String command = "C://ffmpeg/bin/ffmpeg.exe -i " + "\""+videoFilePath+"\"";

        try {
            ProcessBuilder processBuilder = new ProcessBuilder(command.split(" "));
            processBuilder.redirectErrorStream(true); // FFmpeg outputs to stderr
            Process process = processBuilder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            Pattern pattern = Pattern.compile("Duration: (\\d{2}):(\\d{2}):(\\d{2}\\.\\d{2})");

            while ((line = reader.readLine()) != null) {
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    duration = matcher.group(1) + ":" + matcher.group(2) + ":" + matcher.group(3);
                    break;
                }
            }
            process.waitFor();

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return duration;
    }

    public static void main(String[] args) {
        try {
            VerifyDuration verifyDuration = new VerifyDuration();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}

