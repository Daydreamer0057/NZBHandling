import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SubtitlesSeriesDownload {
    ChromeDriver driver;
    ChromeDriver driver2;
    ChromeDriver driver3;

    public SubtitlesSeriesDownload() {
        File baseInit = new File("z:/series");
        File[] listInitFiles = baseInit.listFiles();

        for (File fichierInit : listInitFiles) {
            long seconds = System.currentTimeMillis();
//        File base = new File("Z://film/new/main");
            String chemin = "c:\\users\\bmonnet\\downloads\\" + fichierInit.getName();
            String chemin2 = "Z:/series/" + fichierInit.getName();
            try {
                FileUtils.forceMkdir(new File(chemin));
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            HashSet<String> nameList = new HashSet<>();

//        File base = new File("Z://film/new/main");
            File base = new File("Z:/Series/" + fichierInit.getName());

            File[] fichiers = base.listFiles();

            HashSet<File> listeFichier = new HashSet<File>();
            ArrayList<File> listeDirectory = new ArrayList<File>();
            // listeDirectory.add(new File("z://temp/film"));

            for (File fichier : fichiers) {
                if (fichier.isDirectory()) {
                    listeDirectory.add(fichier);
                } else {
                    if (fichier.getName().endsWith("mkv") || fichier.getName().endsWith("mp4")
                            || fichier.getName().endsWith("avi")) {
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
                        if (fichierTemp.getName().endsWith("mkv") || fichierTemp.getName().endsWith("mp4")
                                || fichierTemp.getName().endsWith("avi")) {
                            listeFichier.add(fichierTemp);
                        }
                    }
                }
                listeDirectory.remove(0);
            }

            int compteurMax = 0;

            int change = 0;

            try {
                System.setProperty("webdriver.chrome.driver", "e://temp/chrome/chromedriver.exe");
                driver = new ChromeDriver();
//            driver.manage().window().setPosition(new Point(0, -2000));
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            int compteur = 0;
            for (File fichierTemp : listeFichier) {
                compteur++;
                if (compteur >= 0) {
                    System.out.println("Compteur " + compteur + " / " + listeFichier.size());
                /*Pattern pattern = Pattern.compile("\\([0-9]+");
                Matcher matcher = pattern.matcher(fichierTemp.getName());
                */
                    StringTokenizer stk = new StringTokenizer(fichierTemp.getName(), "-");

                    Integer pos = 0;
                    String name = "";
                    // Check all occurrences
                    if (stk.hasMoreTokens()) {
                        name = stk.nextToken();
                        name = name.trim();
                        if (nameList.contains(name)) {
                            break;
                        } else {
                            nameList.add(name);
                        }
                    }

                    try {
                        System.setProperty("webdriver.chrome.driver", "e://temp/chrome/chromedriver.exe");
                        driver3 = new ChromeDriver();
//                            driver3.manage().window().setPosition(new Point(0, -2000));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    driver3.get("https://www.podnapisi.net/en/subtitles/search/?episodes=&keywords=" + name);


                    String pageSource = driver3.getPageSource();

                    int pageMax = 1;

                    Pattern pattern = Pattern.compile(";page=[0-9]+");
                    Matcher matcher = pattern.matcher(pageSource);
                    try {
                        while (matcher.find()) {
                            Pattern pattern2 = Pattern.compile("[0-9]+");
                            Matcher matcher2 = pattern2.matcher(matcher.group());
                            while (matcher2.find()) {
                                if (Integer.parseInt(matcher2.group(0)) > pageMax) {
                                    pageMax = Integer.parseInt(matcher2.group(0));
                                }
                            }
                        }
                    } catch (Exception e) {

                    }

                    driver3.close();

                    System.out.println("pageMax " + pageMax);
                    System.out.println("serie " + fichierTemp.getName());


                    for (int page = 1; page < (pageMax + 1); page++) {
                        try {
                            System.setProperty("webdriver.chrome.driver", "e://temp/chrome/chromedriver.exe");
                            driver = new ChromeDriver();
                            driver.manage().window().setPosition(new Point(0, -2000));
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        System.out.println("Page " + page);
                        driver.get("https://www.podnapisi.net/en/subtitles/search/?episodes=&keywords=" + name + "&page=" + page);

                        while (driver.getPageSource().contains("503 Service Temporarily Unavailable")) {
                            driver.get("https://www.podnapisi.net/en/subtitles/search/?episodes=&keywords=" + name + "$page=" + page);
                        }

                        List<WebElement> links = driver.findElements(By.tagName("a"));

                        List<WebElement> countLinks = driver.findElements(By.className("active"));

                        System.out.println("Links done");

                        try {
                            System.setProperty("webdriver.chrome.driver", "e://temp/chrome/chromedriver.exe");
                            HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
                            chromePrefs.put("download.default_directory", chemin);
                            ChromeOptions options = new ChromeOptions();
                            options.setExperimentalOption("prefs", chromePrefs);
                            driver2 = new ChromeDriver(options);
                            driver2.manage().window().setPosition(new Point(0, -2000));
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }

                        for (WebElement webElement : links) {
                            try {
                                if (webElement != null && webElement.getAttribute("href") != null && webElement.getAttribute("href").toLowerCase().indexOf("download") != -1 && (!webElement.getAttribute("href").toLowerCase().contains("fr") && webElement.getAttribute("href").toLowerCase().contains("en"))) {
                                    driver2.get(webElement.getAttribute("href"));
                                }
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }

                        driver2.close();

                        driver.close();

                        try {
                            ProcessBuilder p = new ProcessBuilder("d://au3/process killer.exe");
                            p.start();
                            Thread.sleep(2000);
                        }catch(Exception ex){
                            ex.printStackTrace();
                        }


                    }

                    System.out.println("Download done");
                }
            }
        }
    }

    public static void main(String[] args) {
        SubtitlesSeriesDownload nzb = new SubtitlesSeriesDownload();
    }

}
