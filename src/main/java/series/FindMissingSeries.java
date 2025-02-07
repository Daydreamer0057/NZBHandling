package series;

import Utilities.FileDirParcours;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FindMissingSeries {

    public FindMissingSeries() throws Exception {
        PrintWriter pw = new PrintWriter(new File("c:/temp/list_episodes_missing.txt"));

        File base = new File("e:/series");
        File[] listFolders = base.listFiles();

        int compteur = 0;
        for (File fichierTemp : listFolders) {
            System.out.println(compteur + " / " + listFolders.length);
            compteur++;
            String name = fichierTemp.getName();
            name = name.replace(".", " ");

            if(!name.toLowerCase().contains("france")&&!name.toLowerCase().contains("anime")) {

                ArrayList<String> listFilesEpisodes = FileDirParcours.getNameParcours(fichierTemp.getPath(), new String[]{".mp4", ".mkv", ".avi"});

                String fileName = name;
                fileName = fileName.trim();

                fileName = fileName.replace("é", "e");
                fileName = fileName.replace("è", "e");
                fileName = fileName.replace("ê", "e");
                fileName = fileName.replace("à", "a");

                ArrayList<String> listEpisodes = new ArrayList<>();
                for (String fichierEpisodeTemp : listFilesEpisodes) {
                    Pattern p = Pattern.compile(".*([sS][0-9]+[eExX][0-9]+).*");
                    Matcher m = p.matcher(fichierEpisodeTemp.toLowerCase());

                    if (m.matches()) {
                        listEpisodes.add(m.group(1));
                    }
                }

//					fileName = fileName.replace(" ","%20");

//					String auctionUrl = "https://api.themoviedb.org/3/search/movie?query="+fileName+"&primary_release_year="+year+"&include_adult=true&apikey=dcc0613cc47f8538e16634ad625b72cc";
//					String auctionUrl = "https://api.themoviedb.org/3/authentication?apikey=dcc0613cc47f8538e16634ad625b72cc";
                String showName = fileName;
                String API_KEY = "dcc0613cc47f8538e16634ad625b72cc";

                // Search for the TV show
                String searchUrl = "https://api.themoviedb.org/3/search/tv?api_key=" + API_KEY + "&query=" + showName.replace(" ", "%20");
                JsonObject searchResult = makeApiRequest(searchUrl);
                JsonArray results = searchResult.getAsJsonArray("results");

                if (!results.isEmpty()) {
                    JsonObject firstResult = results.get(0).getAsJsonObject();
                    int showId = firstResult.get("id").getAsInt();

                    // Fetch TV show details
                    String detailsUrl = "https://api.themoviedb.org/3/tv/" + showId + "?api_key=" + API_KEY;
                    JsonObject showDetails = makeApiRequest(detailsUrl);

                    if(showDetails!=null) {
                        int numberOfSeasons = showDetails.get("number_of_seasons").getAsInt();

                        JsonArray seasons = showDetails.getAsJsonArray("seasons");
                        for (int i = 0; i < numberOfSeasons; i++) {
                            JsonObject season = seasons.get(i).getAsJsonObject();
                            int seasonNumber = season.get("season_number").getAsInt();
                            int episodeCount = season.get("episode_count").getAsInt();

                            String seasonDetailsUrl = "https://api.themoviedb.org/3/tv/" + showId + "/season/" + seasonNumber + "?api_key=" + API_KEY;
                            JsonObject seasonResponse = makeApiRequest(seasonDetailsUrl);

                            JsonArray episodes = seasonResponse.getAsJsonArray("episodes");

                            for (int j = 1; j <= episodeCount; j++) {
                                if (!containsName(listEpisodes, seasonNumber, j)) {
                                    JsonObject episode = (JsonObject) episodes.get(j-1);
                                    String episodeTitle = episode.get("name").getAsString();
                                    if (seasonNumber < 10) {
                                        if (j < 10) {
                                            pw.println(fileName + " " + "S0" + seasonNumber + "E0" + j + " " + episodeTitle);
                                            pw.flush();
                                        } else {
                                            pw.println(fileName + " " + "S0" + seasonNumber + "E" + j + " " + episodeTitle);
                                            pw.flush();
                                        }
                                    } else {
                                        if (j < 10) {
                                            pw.println(fileName + " " + "S" + seasonNumber + "E0" + j + " " + episodeTitle);
                                            pw.flush();
                                        } else {
                                            pw.println(fileName + " " + "S" + seasonNumber + "E" + j + " " + episodeTitle);
                                            pw.flush();
                                        }
                                    }
                                }
                            }
                        }
                    }
                } else {
                    System.out.println("TV show not found.");
                }
            }
        }
        pw.flush();
        pw.close();
    }

    public boolean containsName(final List<String> list, final int season, final int episode) {
        return list.stream().anyMatch(o -> (o.toLowerCase().contains("s0"+season) && o.toLowerCase().contains("e0"+episode)) ||
                (o.toLowerCase().contains("s0"+season) && o.toLowerCase().contains("e"+episode)) ||
                (o.toLowerCase().contains("s"+season) && o.toLowerCase().contains("e0"+episode)) ||
                (o.toLowerCase().contains("s"+season) && o.toLowerCase().contains("e"+episode))
        );
    }

    private static JsonObject makeApiRequest(String apiUrl) throws Exception {
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        connection.disconnect();

        return JsonParser.parseString(content.toString()).getAsJsonObject();
    }

    public static void main(String[] args) {
        try {
            FindMissingSeries verifyDuration = new FindMissingSeries();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}



