package series;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.io.FileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetSitcoms {

    public GetSitcoms() throws Exception {
        File base = new File("z://test/stockage");
        File[] listFolders = base.listFiles();

        int compteur = 0;
        for (File fichierTemp : listFolders) {
            System.out.println(compteur + " / " + listFolders.length);

            Pattern p2 = Pattern.compile(".*([sS][0-9]+[eExX]?[0-9]*).*");
            Matcher m2 = p2.matcher(fichierTemp.getName().toLowerCase());

            if(m2.matches()) {

                compteur++;
                String name = fichierTemp.getName();
                name = name.replace(".", " ");
                String fileName = "";
                String year = "";
                StringTokenizer stk = new StringTokenizer(name, " ");
                while (stk.hasMoreTokens()) {
                    String lineTemp = stk.nextToken();
                    Pattern p = Pattern.compile(".*([sS][0-9]+[eExX]?[0-9]*).*");
                    Matcher m = p.matcher(lineTemp.toLowerCase());

                    if (m.matches()) {
                        year = m.group(1);
                        break;
                    } else {
                        if(!lineTemp.contains("-")) {
                            fileName += lineTemp + " ";
                        }
                    }
                }

                fileName = fileName.trim();

                fileName = fileName.replace("é", "e");
                fileName = fileName.replace("è", "e");
                fileName = fileName.replace("ê", "e");
                fileName = fileName.replace("à", "a");

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

                    if (showDetails != null) {
                        JsonArray genresArray = showDetails.getAsJsonArray("genres");
                        boolean testComedy = false;
                        for (int i = 0; i < genresArray.size(); i++) {
                            JsonObject genreObject = genresArray.get(i).getAsJsonObject();
                            String genreName = genreObject.get("name").getAsString();
                            if (genreName.toLowerCase().contains("comedy")) {
                                testComedy = true;
                            }
                        }
                        if (testComedy) {
                            FileUtils.moveFile(fichierTemp, new File("z://test/download/" + fichierTemp.getName()));
                        }
                    }
                }
            }
        }
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
            GetSitcoms verifyDuration = new GetSitcoms();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}



