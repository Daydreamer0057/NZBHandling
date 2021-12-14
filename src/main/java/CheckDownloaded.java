import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;

public class CheckDownloaded {
    public double sizeBase = 0;
    public double sizeDiff = 0;
    public double sizeFinal = 0;
    public CheckDownloaded(){
        sizeBase = calcul();
        while(true){
            sizeDiff = calcul();
            sizeFinal = sizeDiff-sizeBase;
            Date currentDate = new Date(System.currentTimeMillis());
            System.out.println(currentDate.toString()+"    Size Base "+new DecimalFormat("##.##").format(sizeBase/1024/1024/1024)+"     sizeDiff "+new DecimalFormat("##.##").format(sizeFinal/1024/1024/1024));

            try {
                Thread.sleep(600000L);
            } catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    public double calcul(){
        File base = new File("z://test/main");

        File[] fichiers = base.listFiles();

        HashSet<File> listeFichier = new HashSet<File>();
        ArrayList<File> listeDirectory = new ArrayList<File>();

        for (File fichier : fichiers) {
            if (fichier.isDirectory()) {
                listeDirectory.add(fichier);
            } else if (fichier.isFile()) {
                    listeFichier.add(fichier);
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
                            listeFichier.add(fichierTemp);
                    }
                }
            }
            listeDirectory.remove(0);
        }
        double size = 0;
        for(File fichierTemp : listeFichier){
            size += fichierTemp.length();
        }

        return size;
    }

    public static void main(String... args){
        CheckDownloaded checkDownloaded = new CheckDownloaded();
    }
}
