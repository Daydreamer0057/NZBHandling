import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.*;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MissingEpisodesEpguides {

    public MissingEpisodesEpguides() throws Exception {
        PrintWriter pw = new PrintWriter("e://temp/liste_episodes_missing.txt");

        String URLSeries = "";
        String nameSeries = "";
        String episode = "";
        int seasonMax = 0;
        int episodeMax = 0;

        System.setProperty("webdriver.chrome.driver", "e://temp/chrome/chromedriver.exe");
        ChromeDriver driver = null;

        File[] folders = new File("z://series").listFiles();

        for (int i = 0; i < folders.length; i++) {
            URLSeries = "";
            nameSeries = "";
            episode = "";
            seasonMax = 0;
            episodeMax = 0;

            FileReader fr = new FileReader("c://temp/myshow/myshow_parse.txt");
            BufferedReader br = new BufferedReader(fr);
//            for (int j = 1; j < 295; j++) {
            String line = "";
            while (line != null) {
                line = br.readLine();
                if (line != null && line.toLowerCase().contains(folders[i].getName().toLowerCase())) {
                    StringTokenizer stk = new StringTokenizer(line, "--");
                    nameSeries = stk.nextToken();
                    URLSeries = stk.nextToken();
                    break;
                }
            }
            br.close();
            fr.close();


            if (!URLSeries.toLowerCase().equals("")) {
                driver = new ChromeDriver();

//                driver.manage().window().setPosition(new Point(-6000, 0));

                driver.get("https://epguides.com/menu" + nameSeries);

                // Input Email id and Password If you are already Register

                String pageSource = driver.getPageSource();
                StringReader sr = new StringReader(pageSource);
                BufferedReader brSr = new BufferedReader(sr);

                line = "";
                seasonMax = 0;


                while (line != null) {
                    line = brSr.readLine();
                    if (line != null) {
                        Pattern pattern = Pattern.compile("[0-9]+-[0-9]+}");
                        Matcher matcher = pattern.matcher(line);
                        while (matcher.find()) {

                        }
                    }
                    brSr.close();
                    sr.close();
                    driver.close();
                }


                pw.flush();
                pw.close();
            }
        }
    }

    public static void main (String[]args){
        try {
            MissingEpisodesEpguides m = new MissingEpisodesEpguides();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
