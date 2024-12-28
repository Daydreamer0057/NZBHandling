import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbSearch;
import info.movito.themoviedbapi.TmdbTV;
import info.movito.themoviedbapi.model.tv.TvSeries;
import retrofit2.Call;
import retrofit2.Response;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VerifyDurationSeries {

    public VerifyDurationSeries() throws Exception{
        HashMap<String,String> map = new HashMap<>();

        FileReader fr = new FileReader(new File("e:/log/test_films.csv"));
        BufferedReader br = new BufferedReader(fr);

        String line = "";

        while(line!=null){
            line = br.readLine();
            if(line!=null&&!line.contains("Duration")&&!line.isEmpty()){
                try {
                    String duration = line.substring(line.lastIndexOf(",")+2,line.length()-1 );
                    String name = line.substring(1, line.indexOf(duration) - 3);
                    String taille = name.substring(name.lastIndexOf(",")+2);
                    name = name.substring(0, name.indexOf(taille) - 3);
                    map.put(name, duration);
                } catch (Exception ex){
                }
            }
        }

        br.close();
        fr.close();


        HashSet<File> listeFichierATraiter = FileDirParcours.getParcours("z://test/stockage", new String[]{".mp4",".mkv",".avi"});

        File[] listFiles = new File[listeFichierATraiter.size()+1];
        listeFichierATraiter.toArray(listFiles);

        int compteur = 0;
        for(File fichierTemp : listFiles) {
            boolean test = false;
            System.out.print(compteur+"  ");
            compteur++;
            if(fichierTemp!=null) {
                String name = fichierTemp.getName();
                name = name.replace(".", " ");
                String fileName = "";
                String year = "";
                StringTokenizer stk = new StringTokenizer(name, " ");
                while (stk.hasMoreTokens()) {
                    String lineTemp = stk.nextToken();
                    Pattern p = Pattern.compile(".*([sS][0-9]+[eExX][0-9]+).*");
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
                TmdbApi tmdbApi = new TmdbApi("dcc0613cc47f8538e16634ad625b72cc");

                TmdbSearch searchService = tmdbApi.getSearch();
                List<TvSeries> searchResults = searchService.searchTv(fileName, "en", 1).getResults();

                if (!searchResults.isEmpty()) {
                    // Step 2: Get the TV show ID
                    int tvShowId = searchResults.get(0).getId(); // Get the first result

                    // Step 3: Fetch TV show details
                    TmdbTV tvSeriesService = tmdbApi.getTvSeries();
                    TvSeries tvSeriesDetails = tvSeriesService.getSeries(tvShowId, "en");

                    // Step 4: Get episode runtime
                    List<Integer> runtimes = tvSeriesDetails.getEpisodeRuntime();

                    if (!runtimes.isEmpty()) {
                        try {
                            boolean testRuntime = false;
                            for(Integer runtime : runtimes) {
                                int durationMovie = runtime;
                                String durationFile = map.get(fichierTemp.getName());
                                int durationFileInt = 0;
                                if (durationFile != null) {
                                    StringTokenizer stk2 = new StringTokenizer(durationFile, ":");
                                    durationFileInt += Integer.parseInt(stk2.nextToken()) * 60;
                                    durationFileInt += Integer.parseInt(stk2.nextToken());

                                    if (Math.abs(durationFileInt - durationMovie) <= 5 && durationMovie != 0) {
                                        testRuntime = true;
                                        break;
                                    }
                                }
                            }
                            if(!testRuntime){
                                fichierTemp.delete();
                                System.out.println(fichierTemp.getPath());
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
            VerifyDurationSeries verifyDuration = new VerifyDurationSeries();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}

