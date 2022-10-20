import org.openqa.selenium.chrome.ChromeDriver;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;
import java.util.List;

public class EpguidesCheck {
    ChromeDriver driver;
    ChromeDriver driver2;
    ChromeDriver driver3;

    public EpguidesCheck() throws Exception {
        long taille = 0;
        File base = new File("x://nzb/main");
        //File base = new File("d://film/new/Film 20200226");

        File[] fichiers = base.listFiles();
        List<File> listFiles = Arrays.asList(fichiers);

//        Collections.sort(listFiles, new Comparator<File>() {
//            @Override
//            public int compare(File lhs, File rhs) {
//                // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
//                return lhs.length() > rhs.length() ? -1 : (lhs.length() < rhs.length()) ? 1 : 0;
//            }
//        });

        int compteur = 0;
        for(File fichier : listFiles) {
            System.out.println(compteur+" / "+fichiers.length);
            compteur++;

            FileReader fr = new FileReader(fichier);
            BufferedReader br = new BufferedReader(fr);

            String line = "";

            while (line != null) {
                line = br.readLine();
                if (line != null && line.toLowerCase().contains("<segment bytes=")) {
                    taille += new Long(line.substring(line.indexOf("<segment bytes=") + 16, line.indexOf("number") - 2)).longValue();
                }
            }

            br.close();
            fr.close();

            fichier.renameTo(new File("x:/nzb/film/"+fichier.getName()));

            long tailleMax = 4L*1024L*1024L*1024L*1024L;
            System.out.println("NZB "+fichier.getName()+"    taille " + taille+" "+tailleMax);
            if(taille > tailleMax){
                System.exit(0);
            }
        }
    }


    public static void main(String[] args) {
        try {
            EpguidesCheck nzb = new EpguidesCheck();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
