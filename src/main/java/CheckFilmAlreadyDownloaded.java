import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.StringTokenizer;

public class CheckFilmAlreadyDownloaded {

    public CheckFilmAlreadyDownloaded() {
        try {
            File base = new File("z://film/new");

            File[] fichiers = base.listFiles();

            HashSet<File> listeFichier = new HashSet<File>();
            ArrayList<File> listeDirectory = new ArrayList<File>();

            for (File fichier : fichiers) {
                if (fichier.isDirectory()) {
                    listeDirectory.add(fichier);
                } else if (fichier.isFile()) {
                    if ((fichier.getName().endsWith("mkv") || fichier.getName().endsWith("mp4")
                            || fichier.getName().endsWith("avi"))) {
                        listeFichier.add(fichier);
                    }
                }
            }

            while (listeDirectory.size() > 0) {
                File fichier = listeDirectory.get(0);

                File[] fichierListe = fichier.listFiles();

                if (fichierListe != null) {
                    for (File fichierTemp : fichierListe) {
                        if (fichierTemp.isDirectory()) {
                            listeDirectory.add(fichierTemp);
                        } else if (fichierTemp.isFile()) {
                            if ((fichierTemp.getName().endsWith("mkv") || fichierTemp.getName().endsWith("mp4")
                                    || fichierTemp.getName().endsWith("avi"))) {
                                listeFichier.add(fichierTemp);
                            }
                        }
                    }
                }
                listeDirectory.remove(0);
            }

            File fichierNZB = new File("x://nzb/main");
            File[] listeNZB = fichierNZB.listFiles();

            int compteur = 0;
            for (File fichierNZBTemp : listeNZB) {
                boolean testNZB = true;
                System.out.println(compteur+" / "+listeNZB.length);
                compteur++;
                for(File fichierTempFilm : listeFichier) {
                    StringTokenizer stk = new StringTokenizer(fichierTempFilm.getName(),"-");
                    String nameFilm = "";
                    if(stk.hasMoreTokens()) {
                         nameFilm = stk.nextToken();
                    }
                    if(nameFilm!=""){
                        int pos1 = nameFilm.indexOf("(");
                        if(pos1!=-1) {
                            String nameFilmMain = nameFilm.substring(0, pos1);
                            nameFilmMain = nameFilmMain.trim();
                            String year = nameFilm.substring(pos1, nameFilm.length());
                            year = year.replace('(', ' ');
                            year = year.replace(')', ' ');
                            year = year.trim();

                            int sizePath = 0;
                            if(fichierTempFilm.getName().contains("2160p")){
                                sizePath = 2160;
                            }
                            if(fichierTempFilm.getName().contains("1080p")){
                                sizePath = 1080;
                            }
                            if(fichierTempFilm.getName().contains("720p")){
                                sizePath = 720;
                            }
                            if(fichierTempFilm.getName().contains("576p")){
                                sizePath = 576;
                            }
                            if(fichierTempFilm.getName().contains("480p")){
                                sizePath = 480;
                            }

                            int sizeRes = 0;
                            if(fichierNZBTemp.getName().contains("2160p")){
                                sizeRes = 2160;
                            }
                            if(fichierNZBTemp.getName().contains("1080p")){
                                sizeRes = 1080;
                            }
                            if(fichierNZBTemp.getName().contains("720p")){
                                sizeRes = 720;
                            }
                            if(fichierNZBTemp.getName().contains("576p")){
                                sizeRes = 576;
                            }
                            if(fichierNZBTemp.getName().contains("480p")){
                                sizeRes = 480;
                            }

                            if (sizePath>=sizeRes) {
//                                System.out.println(fichierNZBTemp+"    "+fichierTempFilm);
                                testNZB = false;
                                break;
//                                System.out.println(fichierNZBTemp.getName());
                            }
                        }
                    }
                }
                if(!testNZB){
//                    fichierNZBTemp.renameTo(new File("x://nzb/error/" + fichierNZBTemp.getName()));
//                        fichierNZBTemp.delete();
                    System.out.println(fichierNZBTemp.getPath());
                }
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public static void main(String... args){
        CheckFilmAlreadyDownloaded ch = new CheckFilmAlreadyDownloaded();
    }
}
