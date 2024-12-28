import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SubtitlesDownloadSeries {
    public SubtitlesDownloadSeries() {
        String chemin2 = "Z:/series";
        String chemin = "C:/Users/bmonnet/Downloads/subtitles";

        String dirFind = "";
        String fileFind = "";
        String episode = "";
        String season = "";


        //                ==========================================================================================================

        File fichierBase = new File("C:/Users/bmonnet/Downloads/subtitles/");
        File[] listeFichierBase2 = fichierBase.listFiles();

        HashSet<File> listeFichierBase = new HashSet<File>();
        ArrayList<File> listeDirectoryBase = new ArrayList<File>();
        // listeDirectory.add(new File("z://temp/film"));

        for (File fichier : listeFichierBase2) {
            if (fichier.isDirectory()) {
                listeDirectoryBase.add(fichier);
            } else {
                listeFichierBase.add(fichier);
            }
        }

        while (listeDirectoryBase.size() > 0) {
            File fichierBase2 = listeDirectoryBase.get(0);

            File[] fichierListeBase2 = fichierBase2.listFiles();

            for (File fichierTemp : fichierListeBase2) {
                if (fichierTemp.isDirectory()) {
                    listeDirectoryBase.add(fichierTemp);
                } else {
                    listeFichierBase.add(fichierTemp);
                }
            }
            listeDirectoryBase.remove(0);
        }


        File baseInit = new File("z:/series");
        File[] listInitFiles = baseInit.listFiles();

        boolean test = false;
        boolean testFile = false;
        for (File fichierInit : listInitFiles) {
            if ( fichierInit != null) {
                long seconds = System.currentTimeMillis();
                try {
                    FileUtils.forceMkdir(new File(chemin));
                } catch (IOException ex) {
//                    ex.printStackTrace();
                }
                HashSet<File> listeFichier = new HashSet<File>();
                ArrayList<File> listeDirectory = new ArrayList<File>();
                if (listInitFiles.length != 1) {
                    File base = new File(chemin2);

                    File[] fichiers = base.listFiles();
                    if (fichiers != null && fichiers.length > 0) {
                        for (File fichierTemp : fichiers) {
                            if (fichierTemp.isDirectory()) {
                                listeDirectory.add(fichierTemp);
                            } else if (fichierTemp.isFile()) {
                                listeFichier.add(fichierTemp);
                            }
                        }
                    }
                }

                if (listInitFiles.length == 1) {
                    listeFichier.add(fichierInit);
                }
                // listeDirectory.add(new File("z://temp/film"));

                while (listeDirectory.size() > 0) {
                    File fichier = listeDirectory.get(0);

                    File[] fichierListe = fichier.listFiles();

                    for (File fichierTemp : fichierListe) {
                        if (fichierTemp.isDirectory()) {
                            listeDirectory.add(fichierTemp);
                            try {
                                FileUtils.forceMkdir(fichierTemp);
                            } catch (Exception ex) {
//                                ex.printStackTrace();
                            }
                        } else {
                            if (fichierTemp.getName().endsWith("mkv") || fichierTemp.getName().endsWith("mp4")
                                    || fichierTemp.getName().endsWith("avi")) {
                                listeFichier.add(fichierTemp);
                            }
                        }
                    }
                    listeDirectory.remove(0);
                }

                HashSet<String> setLine = new HashSet<>();

                for (File fichierTemp : listeFichier) {
                    setLine.add(fichierTemp.getName());
                }

                int compteurMax = 0;

                int change = 0;

                int compteur = 0;
                for (File fichierTemp : listeFichier) {
                    boolean testDuplicate = false;
                    if (!(setLine.contains(fichierTemp))) {

						/*Pattern pattern = Pattern.compile("\\([0-9]+");
                Matcher matcher = pattern.matcher(fichierTemp.getName());
						 */
                        StringTokenizer stk = new StringTokenizer(fichierTemp.getName(), "-");
                        Integer pos = 0;
                        String name2 = "";
                        // Check all occurrences
                        boolean testName = false;
                        if (stk.hasMoreTokens()) {
                            name2 = stk.nextToken();
                            name2 = name2.trim();
                            name2 = name2.replaceAll(" ", "+");
                            System.out.println("name " + name2);

                            Pattern pattern = Pattern.compile("[sS][0-9]+");
                            Matcher matcher = pattern.matcher(fichierTemp.getName());

                            try {
                                int compteurMatch = 0;
                                if (matcher.find()) {
                                    season = matcher.group(0).substring(1, 3);
                                }
                            } catch (Exception e) {

                            }

                            Pattern pattern2 = Pattern.compile("[eE][0-9]+");
                            Matcher matcher2 = pattern2.matcher(fichierTemp.getName());

                            try {
                                int compteurMatch = 0;
                                if (matcher2.find()) {
                                    episode = matcher2.group(0).substring(1, 3);
                                }
                            } catch (Exception e) {

                            }
                        } else {
                            testName = true;
                        }

                        if (!testName) {
                            Document doc = new Document("test");
                            testName = true;
                            while (testName) {
                                try {
                                    doc = Jsoup.connect("https://www.podnapisi.net/en/subtitles/search/?keywords=" + name2 + "&movie_type=tv-series&seasons=" + season + "&episodes=" + episode + "&year=").get();
                                    https:
//www.podnapisi.net/en/subtitles/search/?keywords=stargate+atlantis&movie_type=tv-series&seasons=4&episodes=3&year=
                                    testName = false;
                                } catch (Exception ex) {
//                                ex.printStackTrace();
                                    testName = true;
                                }
                            }

                            String pageSource = doc.text();


                            int pageMax2 = 1;

                            Pattern pattern = Pattern.compile(";page=[0-9]+");
                            Matcher matcher = pattern.matcher(pageSource);
                            try {
                                while (matcher.find()) {
                                    Pattern pattern2 = Pattern.compile("[0-9]+");
                                    Matcher matcher2 = pattern2.matcher(matcher.group());
                                    while (matcher2.find()) {
                                        if (Integer.parseInt(matcher2.group(0)) > pageMax2) {
                                            pageMax2 = Integer.parseInt(matcher2.group(0));
                                        }
                                    }
                                }
                            } catch (Exception e) {

                            }

                            System.out.println("pageMax " + pageMax2);
                            System.out.println("serie " + fichierTemp.getPath());
                            final int pageMax = pageMax2;
                            final String name = name2;

                            final String name3 = name2;
                            final String season2 = season;
                            final String episode2 = episode;
                            final int pageMaxFinal = pageMax;
                            final File fichierFinal = fichierTemp;
                            final String cheminFinal = chemin;

                            for (int page = 1; page < (pageMaxFinal + 1); page++) {
                                System.out.println("Page " + page);
                                Document doc2 = null;
//                                    new Document("test");
                                boolean testTimeOut = true;
                                while (doc2 == null) {
                                    try {
                                        doc2 = Jsoup.connect("https://www.podnapisi.net/en/subtitles/search/?keywords=" + name3 + "&movie_type=tv-series&seasons=" + season2 + "&episodes=" + episode2 + "&year=" + "&page=" + page).get();
                                    } catch (Exception ex) {
//                                    ex.printStackTrace();
                                    }
                                }


                                Elements element = doc2.select("a");


                                //for(;it.hasNext();){
                                for (int index503 = 0; index503 < element.size(); index503++) {
                                    Element elementTemp = element.get(index503);

//                                        System.out.println("Element "+index+" / "+element.size());
                                    String elementString = elementTemp.attr("abs:href");

                                    if (elementString != null && elementString.toLowerCase().indexOf("download") != -1 && (!elementString.toLowerCase().contains("fr") && elementString.toLowerCase().contains("en"))
                                            && elementString.toLowerCase().endsWith("download")) {
                                        try {
                                            Pattern pattern3 = Pattern.compile("[sS][0-9]+[xXeE][0-9]+");
                                            Matcher matcher3 = pattern3.matcher(elementString);

                                            Pattern pattern4 = Pattern.compile("[sS][0-9]+[xXeE][0-9]+");
                                            Matcher matcher4 = pattern4.matcher(fichierFinal.getName());
                                            try {
                                                if (matcher3.find() && matcher4.find() && matcher3.group(0).toLowerCase().equals(matcher4.group(0).toLowerCase())) {
                                                    FileUtils.copyURLToFile(new URL(elementString), new File(cheminFinal + "/" + FilenameUtils.removeExtension(fichierFinal.getName()) + index503 + ".zip"));
                                                }
                                            } catch (Exception ex) {
//                                        ex.printStackTrace();
                                                if (ex.toString().toLowerCase().contains("503") || ex.toString().toLowerCase().contains("504") || ex.toString().toLowerCase().contains("502")) {
                                                    index503--;
                                                    try {
                                                        Thread.sleep(10000);
                                                    } catch (Exception ex2) {
//                                                ex2.printStackTrace();
                                                    }
                                                }
                                            }
                                        } catch (Exception ex) {

                                        }
                                    }
                                }
                            }
                            System.out.println("Download done");
                        }
                    }
                }
            }
        }
    }


    public static void main(String[] args) {
        SubtitlesDownloadSeries nzb = new SubtitlesDownloadSeries();
    }
}