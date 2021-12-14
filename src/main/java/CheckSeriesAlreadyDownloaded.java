import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.StringTokenizer;

public class CheckSeriesAlreadyDownloaded {

    public CheckSeriesAlreadyDownloaded() {
        try {
            File base = new File("z://series");

            File[] fichiers = base.listFiles();

            HashSet<File> listeFichier = new HashSet<File>();
            ArrayList<File> listeDirectory = new ArrayList<File>();

            for (File fichier : fichiers) {
                if (fichier.isDirectory()) {
                    listeDirectory.add(fichier);
                } else {
                    if(fichier.getName().endsWith(".mp4")||fichier.getName().endsWith(".mkv")||fichier.getName().endsWith(".avi")) {
                        listeFichier.add(fichier);
                    }
                }
            }

            while (listeDirectory.size() > 0) {
                File fichier = listeDirectory.get(0);
                File[] fichierListe = fichier.listFiles();

                for (File fichierTemp : fichierListe) {
                    if (fichierTemp.isDirectory()) {
                        listeDirectory.add(fichierTemp);
                    } else {
                        if(fichier.getName().endsWith(".mp4")||fichier.getName().endsWith(".mkv")||fichier.getName().endsWith(".avi")) {
                            listeFichier.add(fichierTemp);
                        }
                    }
                }
                listeDirectory.remove(0);
            }

            File fichierNZB = new File("u://nzb/film en attente/series");
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
                         nameFilm = stk.nextToken().trim();
                    }
                    String episode = "";
                    if(stk.hasMoreTokens()) {
                        episode = stk.nextToken().trim();
                    }
                    if(nameFilm!=""&&episode!=""){
                            ArrayList<String> listNamePart = new ArrayList<>();

                            StringTokenizer stkBlanc = new StringTokenizer(nameFilm, " ");
                            while (stkBlanc.hasMoreTokens()) {
                                listNamePart.add(stkBlanc.nextToken().trim());
                            }

//                            System.out.println(fichierNZBTemp.getName()+"    "+nameFilmMain+"    "+year);
                            ArrayList<Boolean> listTest = new ArrayList<Boolean>();
                            for (String lineTemp : listNamePart) {
                                if ((fichierNZBTemp.getName().toLowerCase().contains(lineTemp.toLowerCase()))) {
                                    listTest.add(true);
                                }
                            }
                            if (listNamePart.size()!=0 && listTest.size()!=0 &&(listNamePart.size()==listTest.size())&&(fichierNZBTemp.getName().toLowerCase().contains(episode))) {
                                System.out.println(fichierNZBTemp+"    "+fichierTempFilm);
                                testNZB = false;
                                break;
//                                System.out.println(fichierNZBTemp.getName());
                            }
                    }
                }
                if(!testNZB){
                    fichierNZBTemp.renameTo(new File("z://test/error/" + fichierNZBTemp.getName()));
                }
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public static void main(String... args){
        CheckSeriesAlreadyDownloaded ch = new CheckSeriesAlreadyDownloaded();
    }
}
