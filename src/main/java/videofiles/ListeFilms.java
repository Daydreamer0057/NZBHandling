package videofiles;

import Utilities.FileDirParcours;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ListeFilms {

    public ListeFilms() throws Exception {
        PrintWriter pw = new PrintWriter("z://film/liste_films.txt");

        HashSet<File> listeFichier = FileDirParcours.getParcours("z:/film/new",new String[]{"avi","mp4","mkv"});
        listeFichier.addAll(FileDirParcours.getParcours("z:/film/treated",new String[]{"avi","mp4","mkv"}));
        listeFichier.addAll(FileDirParcours.getParcours("z:/test/test",new String[]{"avi","mp4","mkv"}));

//        listeFichier.addAll(listeFichier2);
//        listeFichier.addAll(listeFichier3);

        HashMap<String,String> listeName = new HashMap<>();

        int compteur = 0;
        for (File fichierTemp : listeFichier) {
            System.out.println(compteur + " / " + listeFichier.size());
            compteur++;
            Pattern p = Pattern.compile(".*([sS][0-9]+[eExX]+[0-9]+).*");
            Matcher m = p.matcher(fichierTemp.getName());

            if (!m.matches()) {
                String name = fichierTemp.getName();
                name = name.replace(".", " ");
                String fileName = "";
                String year = "";
                StringTokenizer stk = new StringTokenizer(name, " ");
                while (stk.hasMoreTokens()) {
                    String lineTemp = stk.nextToken();
                    Pattern p2 = Pattern.compile(".*([0-9]{4}).*");
                    Matcher m2 = p2.matcher(lineTemp.toLowerCase());

                    if (m2.matches()) {
                        year = "("+m2.group(1)+")";
                        break;
                    } else {
                        fileName += lineTemp + " ";
                    }
                }

                fileName = fileName.replace(":","");
                fileName = fileName.trim();


                String nameFinal = fileName + " " + year;

                if(!listeName.containsKey(nameFinal)){
                    Pattern p2 = Pattern.compile(".*([0-9]{4}p).*");
                    Matcher m2 = p2.matcher(name.toLowerCase());

                    if (m2.matches()) {
                        if(listeName.get(nameFinal)!=null&&Integer.parseInt(listeName.get(nameFinal).toLowerCase().substring(0,listeName.get(nameFinal).length()-1))>Integer.parseInt(m2.group(1).substring(0,m2.group(1).length()-1))) {

                            nameFinal += " " + m2.group(1);
                            if(fichierTemp.getPath().toLowerCase().contains("treated")){
                                nameFinal += " treated";
                            }
                            nameFinal.trim();
                            pw.println(nameFinal);
                            pw.flush();
                            System.out.println(nameFinal);
                        } else {
                            if(listeName.get(nameFinal)==null) {
                                listeName.put(nameFinal,m2.group(1));
                                nameFinal += " " + m2.group(1);
                                if(fichierTemp.getPath().toLowerCase().contains("treated")){
                                    nameFinal += " treated";
                                }
                                nameFinal.trim();
                                pw.println(nameFinal);
                                pw.flush();
                                System.out.println(nameFinal);
                            }
                        }
                    } else {
                        Pattern p3 = Pattern.compile(".*([0-9]{3}p).*");
                        Matcher m3 = p3.matcher(name.toLowerCase());

                        if (m3.matches()) {
                            if(listeName.get(nameFinal)!=null&&Integer.parseInt(listeName.get(nameFinal).toLowerCase().substring(0,listeName.get(nameFinal).length()-1))>Integer.parseInt(m3.group(1).substring(0,m3.group(1).length()-1))) {
                                nameFinal += " " + m3.group(1);
                                if(fichierTemp.getPath().toLowerCase().contains("treated")){
                                    nameFinal += " treated";
                                }
                                nameFinal.trim();
                                pw.println(nameFinal);
                                pw.flush();
                                System.out.println(nameFinal);
                            } else {
                                if(listeName.get(nameFinal)==null) {
                                    listeName.put(nameFinal,m3.group(1));
                                    nameFinal += " " + m3.group(1);
                                    if(fichierTemp.getPath().toLowerCase().contains("treated")){
                                        nameFinal += " treated";
                                    }
                                    nameFinal.trim();
                                    pw.println(nameFinal);
                                    pw.flush();
                                    System.out.println(nameFinal);
                                }
                            }
                        }
                    }
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
            ListeFilms verifyDuration = new ListeFilms();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}



