import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.net.URL;

public class SubtitlesDownloadSeriesByDirectories {
    public SubtitlesDownloadSeriesByDirectories() {
        String dirFind = "";
        String fileFind = "";
        String episode = "";
        String season = "";



        File baseInit = new File("z:/series");
        File[] listInitFiles = baseInit.listFiles();

        int compteurMax = 0;

        int change = 0;

        int compteur = 0;
        for(File fichierTemp : listInitFiles) {
//            if (fichierTemp.getName().toLowerCase().contains("atlantis")) {
                String chemin = "C:/Users/bmonnet/Downloads/subtitles/" + fichierTemp.getName()+"/subtitles";
                String chemin2 = "Z:/series/" + fichierTemp.getName();
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


                final String name = fichierTemp.getName();

                System.out.println(fichierTemp.getName());

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
                            try {
                                doc2 = Jsoup.connect("https://www.podnapisi.net/en/subtitles/search/?keywords=" + name3 + "&movie_type=&seasons=&episodes=&year=&type=&page="+page).get();
                                Elements elementList = doc2.select("a");
                                for(Element elementTemp : elementList) {
                                    String element = elementTemp.attr("abs:href");
                                    if(element.toLowerCase().contains("download")){
                                        if(!element.toLowerCase().contains("list")) {
                                            compteurLinks++;
                                        }
                                    }
                                }
                            } catch (Exception ex) {
//                                    ex.printStackTrace();
                                if (ex.toString().toLowerCase().contains("404")) {
                                    break;
                                }
                            }
                        }
                        System.out.println("Compteur Links "+fichierTemp.getPath()+"    "+compteurLinks + "    page="+page );
                        if(compteurLinks==0){
                            break;
                        }


                        Elements element = doc2.select("a");

                        System.out.println("Compteur Links "+fichierTemp.getPath()+"    "+compteurLinks + "    page="+page );
                        //for(;it.hasNext();){
                        for (int index503 = 0; index503 < element.size(); index503++) {
//                            if(index503==370 && page==15){
//                                System.out.println("test");
//                            }
                            int compteurTry = 0;
//                            System.out.print("Index "+index503+" / "+element.size()+" ");
                            Element elementTemp = element.get(index503);

//                                        System.out.println("Element "+index+" / "+element.size());
                            String elementString = elementTemp.attr("abs:href");

                            if (elementString != null && elementString.toLowerCase().indexOf("download") != -1 && (!elementString.toLowerCase().contains("fr") && elementString.toLowerCase().contains("en"))
                                    && elementString.toLowerCase().contains("download")) {
                                try {
                                    try {
                                        while(new File(cheminFinal + "/" + FilenameUtils.removeExtension(fichierFinal.getName()) + compteurTry + ".zip").exists()) {
                                            compteurTry++;
                                        }

                                            FileUtils.copyURLToFile(new URL(elementString), new File(cheminFinal + "/" + FilenameUtils.removeExtension(fichierFinal.getName()) + compteurTry + ".zip"));

                                    } catch (Exception ex) {
//                                        ex.printStackTrace();
                                        if (ex.toString().toLowerCase().contains("503") || ex.toString().toLowerCase().contains("504") || ex.toString().toLowerCase().contains("502")) {
                                            index503--;
                                        }
                                    }
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            }
                        }
                    }
            System.out.println("Fin traitement "+fichierTemp);
//                        if(listeFichier.size()==1){
//                            while(Thread.activeCount()>0){

//            }
        }
        System.out.println("Download done");

    }






    public static void main(String[] args) {
        SubtitlesDownloadSeriesByDirectories nzb = new SubtitlesDownloadSeriesByDirectories();
    }
}