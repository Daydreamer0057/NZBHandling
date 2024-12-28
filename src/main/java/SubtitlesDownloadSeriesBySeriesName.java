import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SubtitlesDownloadSeriesBySeriesName {
    public static int index503 = 0;
    public static String cheminFinal = "";

    public SubtitlesDownloadSeriesBySeriesName() throws Exception{
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

        for(File fichierTemp : listInitFiles) {
            FileUtils.forceMkdir(new File("C:/Users/bmonnet/Downloads/subtitles/"+fichierTemp.getName()));
//            if(!fichierTemp.getName().contains("24")) {
            String nameSeries = fichierTemp.getName();
            nameSeries = nameSeries.replace(" ", "+");

            Document doc = null;
            while(doc == null) {
                try {
                    doc = Jsoup.connect("https://www.podnapisi.net/en/subtitles/search/?keywords=" + nameSeries + "&movie_type=tv-series").get();
                } catch (SocketTimeoutException ex) {

                }
            }

            String pageSource = doc.outerHtml();


            int pageMax2 = 1;

            Pattern pattern = Pattern.compile(".*(;page=[0-9]+).*");
            Matcher matcher = pattern.matcher(pageSource);
            try {
                while (matcher.find()) {
                    Pattern pattern2 = Pattern.compile(".*([0-9]+).*");
                    Matcher matcher2 = pattern2.matcher(matcher.group(1));
                    while (matcher2.find()) {
                        if (Integer.parseInt(matcher2.group(1)) > pageMax2) {
                            pageMax2 = Integer.parseInt(matcher2.group(1));
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            System.out.println("pageMax " + pageMax2);
            System.out.println("serie " + fichierTemp.getPath());
            int pageMax = pageMax2;
            String name = nameSeries;

            String name3 = nameSeries;
            int pageMaxFinal = pageMax;
            File fichierFinal = fichierTemp;
            cheminFinal = chemin;

            for (int page = 1; page < (pageMaxFinal + 1); page++) {
                System.out.println("Page " + page);
                Document doc2 = null;
//                                    new Document("test");
                while (doc2 == null) {
                    try {
                        doc2 = Jsoup.connect("https://www.podnapisi.net/en/subtitles/search/?keywords=" + name3 + "&movie_type=tv-series&page=" + page).get();
                    } catch (Exception ex) {
//                                    ex.printStackTrace();
                    }
                }


                Elements element = doc2.select("a");


                //for(;it.hasNext();){
                for (;index503 < element.size(); index503++) {
                    Element elementTemp = element.get(index503);

//                                        System.out.println("Element "+index+" / "+element.size());
                    String elementString = elementTemp.attr("abs:href");

                    if (elementString != null && elementString.toLowerCase().indexOf("download") != -1 && (!elementString.toLowerCase().contains("fr") && elementString.toLowerCase().contains("en"))
                            && elementString.toLowerCase().endsWith("download")) {
                        try {
                            Pattern pattern3 = Pattern.compile(".*([sS][0-9]+[xXeE][0-9]+).*");
                            Matcher matcher3 = pattern3.matcher(elementString);

                            try {
                                if (matcher3.find() && (!new File(cheminFinal + "/" + FilenameUtils.removeExtension(fichierFinal.getName()) + "/" + index503 + ".zip").exists())) {
                                    while (Thread.activeCount() > 10) {
                                        Thread.sleep(1000);
                                    }

                                    Thread t = new Thread(() -> {
                                        try {
                                            FileUtils.copyURLToFile(new URL(elementString), new File(cheminFinal + "/" + FilenameUtils.removeExtension(fichierFinal.getName()) + "/" + index503 + ".zip"));
                                        } catch (Exception ex) {
                                            if (ex.toString().toLowerCase().contains("503") || ex.toString().toLowerCase().contains("504") || ex.toString().toLowerCase().contains("502")) {
                                                try {
                                                    FileUtils.copyURLToFile(new URL(elementString), new File(cheminFinal + "/" + FilenameUtils.removeExtension(fichierFinal.getName()) + "/" + index503 + ".zip"));
                                                } catch (Exception ex1) {
                                                    if (ex1.toString().toLowerCase().contains("503") || ex1.toString().toLowerCase().contains("504") || ex1.toString().toLowerCase().contains("502")) {
                                                        try {
                                                            FileUtils.copyURLToFile(new URL(elementString), new File(cheminFinal + "/" + FilenameUtils.removeExtension(fichierFinal.getName()) + "/" + index503 + ".zip"));
                                                        } catch (Exception ex2) {
                                                            if (ex2.toString().toLowerCase().contains("503") || ex2.toString().toLowerCase().contains("504") || ex2.toString().toLowerCase().contains("502")) {
                                                                try {
                                                                    FileUtils.copyURLToFile(new URL(elementString), new File(cheminFinal + "/" + FilenameUtils.removeExtension(fichierFinal.getName()) + "/" + index503 + ".zip"));
                                                                } catch (Exception ex3) {
                                                                    if (ex3.toString().toLowerCase().contains("503") || ex3.toString().toLowerCase().contains("504") || ex3.toString().toLowerCase().contains("502")) {
                                                                        try {
                                                                            FileUtils.copyURLToFile(new URL(elementString), new File(cheminFinal + "/" + FilenameUtils.removeExtension(fichierFinal.getName()) + "/" + index503 + ".zip"));
                                                                        } catch (Exception ex4) {
                                                                            if (ex4.toString().toLowerCase().contains("503") || ex4.toString().toLowerCase().contains("504") || ex4.toString().toLowerCase().contains("502")) {
                                                                                try {
                                                                                    FileUtils.copyURLToFile(new URL(elementString), new File(cheminFinal + "/" + FilenameUtils.removeExtension(fichierFinal.getName()) + "/" + index503 + ".zip"));
                                                                                } catch (Exception ex5) {
                                                                                    if (ex5.toString().toLowerCase().contains("503") || ex5.toString().toLowerCase().contains("504") || ex5.toString().toLowerCase().contains("502")) {
                                                                                        try {
                                                                                            FileUtils.copyURLToFile(new URL(elementString), new File(cheminFinal + "/" + FilenameUtils.removeExtension(fichierFinal.getName()) + "/" + index503 + ".zip"));
                                                                                        } catch (Exception ex6) {
                                                                                            if (ex6.toString().toLowerCase().contains("503") || ex6.toString().toLowerCase().contains("504") || ex6.toString().toLowerCase().contains("502")) {
                                                                                                try {
                                                                                                    FileUtils.copyURLToFile(new URL(elementString), new File(cheminFinal + "/" + FilenameUtils.removeExtension(fichierFinal.getName()) + "/" + index503 + ".zip"));
                                                                                                } catch (
                                                                                                        Exception ex7) {
                                                                                                    if (ex7.toString().toLowerCase().contains("503") || ex7.toString().toLowerCase().contains("504") || ex7.toString().toLowerCase().contains("502")) {
                                                                                                        try {
                                                                                                            FileUtils.copyURLToFile(new URL(elementString), new File(cheminFinal + "/" + FilenameUtils.removeExtension(fichierFinal.getName()) + "/" + index503 + ".zip"));
                                                                                                        } catch (
                                                                                                                Exception ex8) {
                                                                                                            if (ex8.toString().toLowerCase().contains("503") || ex8.toString().toLowerCase().contains("504") || ex8.toString().toLowerCase().contains("502")) {
                                                                                                                try {
                                                                                                                    FileUtils.copyURLToFile(new URL(elementString), new File(cheminFinal + "/" + FilenameUtils.removeExtension(fichierFinal.getName()) + "/" + index503 + ".zip"));
                                                                                                                } catch (
                                                                                                                        Exception ex9) {
                                                                                                                    if (ex9.toString().toLowerCase().contains("503") || ex9.toString().toLowerCase().contains("504") || ex9.toString().toLowerCase().contains("502")) {
                                                                                                                        try {
                                                                                                                            FileUtils.copyURLToFile(new URL(elementString), new File(cheminFinal + "/" + FilenameUtils.removeExtension(fichierFinal.getName()) + "/" + index503 + ".zip"));
                                                                                                                        } catch (
                                                                                                                                Exception ex10) {
                                                                                                                        }
                                                                                                                    }
                                                                                                                }
                                                                                                            }
                                                                                                        }
                                                                                                    }
                                                                                                }
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    });
                                    t.start();
                                }
                            } catch (Exception ex) {
//                                        ex.printStackTrace();
                            }
                        } catch (Exception ex) {

                        }
                    }
                }
//                }
            }
            System.out.println("Download done");
        }
    }


    public static void main(String[] args) {
        try {
            SubtitlesDownloadSeriesBySeriesName nzb = new SubtitlesDownloadSeriesBySeriesName();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}