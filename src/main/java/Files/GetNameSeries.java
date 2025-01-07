package Files;

import java.io.File;
import java.io.PrintWriter;

public class GetNameSeries {

    public GetNameSeries() throws Exception {
        PrintWriter pw = new PrintWriter("e:/temp/liste_series.txt");
        File base = new File("e://series");

        File[] fichiers = base.listFiles();

        for(File fichierTemp : fichiers) {
            pw.println(fichierTemp.getName());
            pw.flush();
        }

        pw.flush();
        pw.close();
    }

    public static void main(String[] args) {
        try {
            GetNameSeries t = new GetNameSeries();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
