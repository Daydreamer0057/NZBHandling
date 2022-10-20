import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.*;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MissingEpisodes {

    public MissingEpisodes() throws Exception {
        PrintWriter pw = new PrintWriter("e://temp/liste_episodes_missing.txt");

        String URLSeries = "" ;
        String nameSeries = "" ;
        String episode = "" ;
        int seasonMax = 0;
        int episodeMax = 0;

        System.setProperty("webdriver.chrome.driver", "e://temp/chrome/chromedriver.exe");
        ChromeDriver driver = null;

        File[] folders = new File("z://series").listFiles();

        for (int i = 0; i < folders.length; i++) {
            URLSeries = "" ;
            nameSeries = "";
            episode = "";
            seasonMax = 0;
            episodeMax = 0;

            FileReader fr = new FileReader("c://temp/myshow/myshow_parse.txt");
            BufferedReader br = new BufferedReader(fr);
//            for (int j = 1; j < 295; j++) {
            String line = "";
            while(line!=null){
                line = br.readLine();
                if (line!=null&&line.toLowerCase().contains(folders[i].getName().toLowerCase())) {
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

                driver.get("https://nzbplanet.net");

                // Input Email id and Password If you are already Register
                driver.findElement(By.name("username")).sendKeys("mbrebis092");
                driver.findElement(By.name("password")).sendKeys("9cEx4MUWaqRIL]_u");

                WebElement webElementTemp2 = driver.findElement(By.name("username"));
                webElementTemp2.submit();

                driver.get(URLSeries);

                String pageSource = driver.getPageSource();
                StringReader sr = new StringReader(pageSource);
                BufferedReader brSr = new BufferedReader(sr);

                line = "" ;
                seasonMax = 0;

                while (line != null) {
                    line = brSr.readLine();
                    if (line != null&&(line.toLowerCase().contains("season=s0")||line.toLowerCase().contains("season=s1"))) {
                        int seasonTemp = Integer.parseInt(line.substring(line.toLowerCase().indexOf("season=s")+8,line.toLowerCase().indexOf("season=s")+10));
                        if (seasonTemp > seasonMax) {
                            seasonMax = seasonTemp;
                        }
                    }
                }
                brSr.close();
                sr.close();
                driver.close();
            }

            for (int m = 1; m <= seasonMax; m++) {
                String URLSeriesTemp = "" ;
                if (m < 10) {
                    URLSeriesTemp = URLSeries + "?season=S0" + m + "&filter=" ;
                } else {
                    URLSeriesTemp = URLSeries + "?season=S" + m + "&filter=" ;
                }

                System.setProperty("webdriver.chrome.driver", "e://temp/chrome/chromedriver.exe");
                ChromeDriver driver2 = new ChromeDriver();

                driver2.manage().window().setPosition(new Point(-6000, 0));

                driver2.get("https://nzbplanet.net");

                                // Input Email id and Password If you are already Register
                driver2.findElement(By.name("username")).sendKeys("mbrebis092");
                driver2.findElement(By.name("password")).sendKeys("9cEx4MUWaqRIL]_u");

                WebElement webElementTemp2 = driver2.findElement(By.name("username"));
                webElementTemp2.submit();

                driver2.get(URLSeriesTemp);

                String pageSource = driver2.getPageSource();

                StringReader sr = new StringReader(pageSource);
                BufferedReader brSr = new BufferedReader(sr);

                line = "" ;
                while (line != null) {
                    line = brSr.readLine();
                    if (line != null && line.toLowerCase().contains("episode:")) {
                        Pattern pattern = Pattern.compile("[0-9]{2}");
                        Matcher matcher = pattern.matcher(line);
                        while (matcher.find()) {
                            int episodeCompare = Integer.parseInt(matcher.group());
                            if (episodeCompare > episodeMax) {
                                episodeMax = episodeCompare;
                            }
                        }
                    }
                }

                brSr.close();
                sr.close();

                File[] fileSearch = folders[i].listFiles();
                String episodeSearch = "" ;
                boolean testMissing = false;
                int seasonFile = m;
                    for (int episodeFile = 1; episodeFile <= episodeMax; episodeFile++) {
                        testMissing = false;
                        for (File fileSearchTemp : fileSearch) {

                            if (seasonFile < 10) {
                                if (episodeFile < 10) {
                                    episodeSearch = "s0" + seasonFile + "e0" + episodeFile;
                                } else {
                                    episodeSearch = "s0" + seasonFile + "e" + episodeFile;
                                }

                            } else {
                                if (episodeFile < 10) {
                                    episodeSearch = "s" + seasonFile + "e0" + episodeFile;
                                } else {
                                    episodeSearch = "s" + seasonFile + "e" + episodeFile;
                                }
                            }
                            if (fileSearchTemp.getName().toLowerCase().contains(episodeSearch)) {
                                testMissing = true;
                                break;
                            }
                        }
                        if(!testMissing) {
                            pw.println(nameSeries + " " + episodeSearch);
                            pw.flush();
                        }
                    }
                driver2.close();
                Thread.sleep(15000);
            }
        }
        pw.flush();
        pw.close();
    }

    public static void main(String[] args) {
        try {
            MissingEpisodes m = new MissingEpisodes();
        } catch(Exception ex){
            ex.printStackTrace();
        }

    }
}
