import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class FindMissingSeries {
        private static final String API_KEY = "dcc0613cc47f8538e16634ad625b72cc"; // Replace with your API key
        private static final String BASE_URL = "https://api.themoviedb.org/3/";

        public FindMissingSeries() {
            try {
                PrintWriter pw = new PrintWriter(new File("e://temp/list_episodes_missing"));

                File base = new File("z://series");
                File[] listFiles = base.listFiles();

                for (File fichierTemp : listFiles) {
                    if (!(fichierTemp.getName().toLowerCase().contains("anime") || fichierTemp.getName().toLowerCase().contains("france"))) {
                        String tvShowName = fichierTemp.getName(); // Example TV show name
                        tvShowName = tvShowName.replace(" ", "%20");

                        // Step 1: Find TV show ID using search query
                        String tvShowId = getTVShowId(tvShowName);

                        if (tvShowId != null) {
                            // Step 2: Get the seasons for the TV show
                            TVShowSeasonResponse seasonResponse = getSeasons(tvShowId);
                            System.out.println("Total Seasons: " + seasonResponse.seasons.length);
                            for (TVSeason season : seasonResponse.seasons) {
                                File listBase = new File("z:/series/" + tvShowName);
                                File[] listFilesSeason = listBase.listFiles();

                                for (File fichierSeason : listFilesSeason) {
                                    String episode = "";
                                    if(season.season_number<10&&season.episode_count<10){
                                        episode = "s0"+season.season_number+"e0"+season.episode_count;
                                    }
                                    if(season.season_number<10&&season.episode_count>10){
                                        episode = "s0"+season.season_number+"e"+season.episode_count;
                                    }
                                    if(season.season_number>10&&season.episode_count<10){
                                        episode = "s"+season.season_number+"e0"+season.episode_count;
                                    }
                                    if(season.season_number>10&&season.episode_count>10){
                                        episode = "s"+season.season_number+"e"+season.episode_count;
                                    }

                                    if(!fichierTemp.getName().toLowerCase().contains(episode)){
                                        pw.println(tvShowName+" "+episode);
                                        pw.flush();
                                    }
                                }
                                System.out.println("Season " + season.season_number + ": " + season.episode_count + " episodes");
                            }
                        } else {
                            System.out.println("TV show not found.");
                        }
                    }
                }
                pw.flush();
                pw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        public static void main(String[] args) {
            FindMissingSeries f = new FindMissingSeries();
        }

        // Get TV show ID by searching for the TV show
        public static String getTVShowId(String tvShowName) throws IOException {
            String url = BASE_URL + "search/tv?api_key=" + API_KEY + "&query=" + tvShowName;
            HttpClient client = HttpClients.createDefault();
            HttpGet request = new HttpGet(url);

            HttpResponse response = client.execute(request);
            String jsonResponse = EntityUtils.toString(response.getEntity());

            Gson gson = new Gson();
            TVShowSearchResponse searchResponse = gson.fromJson(jsonResponse, TVShowSearchResponse.class);

            if (searchResponse.results.length > 0) {
                return String.valueOf(searchResponse.results[0].id); // Return the ID of the first search result
            }
            return null;
        }

        // Get seasons for the given TV show ID
        public TVShowSeasonResponse getSeasons(String tvShowId) throws IOException {
            String url = BASE_URL + "tv/" + tvShowId + "?api_key=" + API_KEY;
            HttpClient client = HttpClients.createDefault();
            HttpGet request = new HttpGet(url);

            HttpResponse response = client.execute(request);
            String jsonResponse = EntityUtils.toString(response.getEntity());

            Gson gson = new Gson();
            TVShowSeasonResponse seasonResponse = gson.fromJson(jsonResponse, TVShowSeasonResponse.class);

            return seasonResponse;
        }

        // Helper classes to represent the API response structure
        static class TVShowSearchResponse {
            TVShowResult[] results;
        }

        static class TVShowResult {
            int id;
        }

        static class TVShowSeasonResponse {
            TVSeason[] seasons;
        }

        static class TVSeason {
            int season_number;
            int episode_count;
        }
}

