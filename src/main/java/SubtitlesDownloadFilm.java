import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SubtitlesDownloadFilm {
    public SubtitlesDownloadFilm() {
        String dirFind = "";
        String fileFind = "";
        String episode = "";
        String season = "";

        PrintWriter pw = null;
        ArrayList<String> listAlreadyDownloaded = new ArrayList<>();

        try {
            FileReader fr = new FileReader(new File("z:/film/already_downloaded.txt"));
            BufferedReader br = new BufferedReader(fr);

            String line = "";
            while (line!=null){
                line = br.readLine();
                if(line!= null){
                    listAlreadyDownloaded.add(line);
                }
            }
            br.close();
            fr.close();
        } catch(Exception ex){

        }

        try {
            pw = new PrintWriter("z:/film/already_downloaded.txt");
        }catch (IOException ex){
            ex.printStackTrace();
        }

        File baseInit = new File("z:/film/treated");
        File[] listInitFiles = baseInit.listFiles();

        File fichierBase = new File("C:/Users/bmonnet/Downloads/Film Subtitles/");
        File[] listeFichierBase2 = fichierBase.listFiles();

        HashSet<File> listeFichier = new HashSet<File>();
        ArrayList<File> listeDirectory = new ArrayList<File>();
//        listeDirectory.add(new File("z:/film/treated"));
        // listeDirectory.add(new File("z://temp/film"));

        for (File fichier : listInitFiles) {
            if (fichier.isDirectory()) {
                listeDirectory.add(fichier);
            } else {
                listeFichier.add(fichier);
            }
        }

        while (listeDirectory.size() > 0) {
            File fichierBase2 = listeDirectory.get(0);

            File[] fichierListeBase2 = fichierBase2.listFiles();

            for (File fichierTemp : fichierListeBase2) {
                if (fichierTemp.isDirectory()) {
                    listeDirectory.add(fichierTemp);
                } else {
                    listeFichier.add(fichierTemp);
                }
            }
            listeDirectory.remove(0);
        }

        int compteurMax = 0;

        int change = 0;

        int compteur = 0;
        for(File fichierTemp : listeFichier) {
            System.out.println(compteur + " / " + listeFichier.size());
            compteur++;
            if (compteur > 0) {

//            if (fichierTemp.getName().toLowerCase().contains("atlantis")) {
                String chemin = "C:/Users/bmonnet/Downloads/Film Subtitles/" + fichierTemp.getName().charAt(0) + "/subtitles/";
                String chemin2 = fichierTemp.getPath();
                try {
                    FileUtils.forceMkdir(new File(chemin));
                } catch (Exception ex) {

                }
//                Document doc = new Document("test");
//                boolean testName = true;
//                while (testName) {
//                    try {
//                        doc = Jsoup.connect("https://www.podnapisi.net/en/subtitles/search/?keywords=" + fichierTemp.getName()).get();
//                        testName = false;
//                    } catch (Exception ex) {
//                        testName = true;
//                    }
//                }

                if (fichierTemp.getName().indexOf("(") != -1 && fichierTemp.getName().indexOf("-") != -1 && !fichierTemp.getName().contains("#")) {
                    final String name = fichierTemp.getName().substring(0, fichierTemp.getName().indexOf("-") - 1);

                    System.out.println(fichierTemp.getName() + "    name " + name);

                    String name3 = name;
                    String season2 = season;
                    String episode2 = episode;
                    File fichierFinal = fichierTemp;
                    String cheminFinal = chemin;

                    for (int page = 1; page < 1000; page++) {
//                        System.out.println("Page " + page + " / " + pageMaxFinal);
                        Document doc2 = null;
//                                    new Document("test");
                        boolean testTimeOut = true;
                        int compteurLinks = 0;
                        while (doc2 == null) {
//                            System.out.println(compteur + " / " + listeFichier.size()+"    ligne 104");
                            if (name3.contains("%")) {
                                break;
                            }
                                try {
                                    System.out.println("doc 2 download");
                                    doc2 = Jsoup.connect("https://www.podnapisi.net/en/subtitles/search/?keywords=" + name3 + "&movie_type=&seasons=&episodes=&year=&type=&page=" + page).get();
                                    Elements elementList = doc2.select("a");
                                    for (Element elementTemp : elementList) {
                                        String element = elementTemp.attr("abs:href");
                                        if (element.toLowerCase().contains("download")) {
                                            if (!element.toLowerCase().contains("list")) {
                                                compteurLinks++;
                                            }
                                        }
                                    }
                                } catch(Exception ex){
                                    ex.printStackTrace();
                                    if (ex.toString().toLowerCase().contains("404")) {
                                        break;
                                    }
                                    System.out.println("doc2 retry");
                                    doc2 = null;
                                }
                        }
                        if (compteurLinks == 0) {
                            break;
                        }


                        Elements element = doc2.select("a");

                        System.out.println("Compteur Links " + fichierTemp.getPath() + "    " + compteurLinks + "    page=" + page);

                        //for(;it.hasNext();){
                        for (int index503 = 0; index503 < element.size(); index503++) {
//                            System.out.println(compteur + " / " + listeFichier.size()+"    ligne 134");
                            int compteurTry = 0;
//                            System.out.print("Index "+index503+" / "+element.size()+" ");
                            Element elementTemp = element.get(index503);

//                                        System.out.println("Element "+index+" / "+element.size());
                            String elementString = elementTemp.attr("abs:href");

                            if (elementString != null) {
                                if (!listAlreadyDownloaded.contains(elementString)) {
                                    listAlreadyDownloaded.add(elementString);
                                    pw.println(elementString);
                                    pw.flush();

                                    Pattern p = Pattern.compile(".*([sS][0-9]+[xXeE][0-9]+).*");
                                    Matcher m = p.matcher(elementString.toLowerCase());

                                    if (!m.matches()) {

                                        if (elementString != null && elementString.toLowerCase().indexOf("download") != -1 && (!elementString.toLowerCase().contains("fr") && elementString.toLowerCase().contains("en"))
                                                && elementString.toLowerCase().contains("download") && !elementString.toLowerCase().contains("list") && !elementString.toLowerCase().contains("%")) {
                                            try {
                                                while (new File(cheminFinal + "/" + FilenameUtils.removeExtension(fichierFinal.getName()) + compteurTry + ".zip").exists()) {
                                                    compteurTry++;
                                                }

                                                System.out.println(elementString);
                                                FileUtils.copyURLToFile(new URL(elementString), new File(cheminFinal + "/" + FilenameUtils.removeExtension(fichierFinal.getName()) + compteurTry + ".zip"));


                                            } catch (Exception ex) {
//                                        ex.printStackTrace();
                                                if (ex.toString().toLowerCase().contains("503") || ex.toString().toLowerCase().contains("504") || ex.toString().toLowerCase().contains("502")) {
                                                    index503--;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                System.out.println("Fin traitement " + fichierTemp);
//                        if(listeFichier.size()==1){
//                            while(Thread.activeCount()>0){

//            }
            }
        }
        pw.flush();
        pw.close();
        System.out.println("Download done");
    }


    public static void main(String[] args) {
        SubtitlesDownloadFilm nzb = new SubtitlesDownloadFilm();
    }
}