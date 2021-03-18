import Utilities.UnzipFiles;
import org.apache.commons.io.FileExistsException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.hibernate.annotations.Check;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SubtitlesDownloadSeries {
    ChromeDriver driver;
    ChromeDriver driver2;
    ChromeDriver driver3;

    public SubtitlesDownloadSeries() {
        long seconds = System.currentTimeMillis();
        File base = new File("z://series");

        File[] fichiers = base.listFiles();

        ArrayList<File> listeDirectory = new ArrayList<File>();

        for (File fichier : fichiers) {
            if(fichier.isDirectory()) {
                listeDirectory.add(fichier);
            }
        }

        //listeDirectory.add(new File("z://series/A Discovery of Witches"));

        int compteur = 0;
        for (File fichierTemp : listeDirectory) {
            compteur++;
            if (compteur >= 0 && !fichierTemp.getName().equalsIgnoreCase("3%") && !fichierTemp.getName().equalsIgnoreCase("see") && !fichierTemp.getName().equalsIgnoreCase("dark")) {
                try {
                    System.setProperty("webdriver.chrome.driver", "e://temp/chromedriver.exe");
                    driver3 = new ChromeDriver();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                driver3.get("https://www.podnapisi.net/en/subtitles/search/?episodes=&movie_type=&year=&keywords=" + fichierTemp.getName() + "&seasons=&type=");

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
                        System.setProperty("webdriver.chrome.driver", "e://temp/chromedriver.exe");
                        driver = new ChromeDriver();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    System.out.println("Page " + page);
                    driver.get("https://www.podnapisi.net/en/subtitles/search/?episodes=&movie_type=&year=&keywords=" + fichierTemp.getName() + "&seasons=&type=&page=" + page);

                    while (driver.getPageSource().contains("503 Service Temporarily Unavailable")) {
                        driver.get("https://www.podnapisi.net/en/subtitles/search/?episodes=&movie_type=&year=&keywords=" + fichierTemp.getName() + "&seasons=&type=&page=" + page);
                    }

                    List<WebElement> links = driver.findElements(By.tagName("a"));

                    System.out.println("Links done");

                    try {
                        System.setProperty("webdriver.chrome.driver", "e://temp/chromedriver.exe");
                        driver2 = new ChromeDriver();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                    for (WebElement webElement : links) {
                        try {
                            if (webElement != null && webElement.getAttribute("href") != null && webElement.getAttribute("href").toLowerCase().indexOf("download") != -1) {
                                driver2.get(webElement.getAttribute("href"));
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }

                    driver2.close();

                    driver.close();
                }

                System.out.println("Download done");



                File sub = new File("C://Users/bmonnet/Downloads");
                File[] listSrt = sub.listFiles();

                for (File fileZip : listSrt) {
                    try {
                        FileUtils.moveFile(fileZip, new File("c://temp/subtitles/" + fileZip.getName()));

                        UnzipFiles.unzip("c://temp/subtitles/" + fileZip.getName(), "c://temp/subtitles/temp");
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }

                System.out.println("Unzip done");

                File zipPath = new File("c://temp/subtitles/temp");

                File[] listZip = zipPath.listFiles();

                HashSet<File> listeFichier = new HashSet<File>();
                ArrayList<File> listeDirectory2 = new ArrayList<File>();

                for (File fichier : listZip) {
                    if (fichier.isDirectory()) {
                        listeDirectory2.add(fichier);
                    } else {
                        listeFichier.add(fichier);
                    }
                }

                while (listeDirectory2.size() > 0) {
                    File fichier = listeDirectory2.get(0);
                    File[] fichierListe = fichier.listFiles();

                    for (File fichierTemp2 : fichierListe) {
                        if (fichierTemp2.isDirectory()) {
                            listeDirectory2.add(fichierTemp2);
                        } else {
                            listeFichier.add(fichierTemp2);
                        }
                    }
                    listeDirectory2.remove(0);
                }

                for (File fichierZip : listeFichier) {
                    String nameOut = fichierZip.getName();
                    File unzipPath = new File(fichierTemp.getPath());
                    File[] listUnzip = unzipPath.listFiles();
                    for (File fichierUnzip : listUnzip) {
                        int index = 1;
                        while (nameOut.equalsIgnoreCase(fichierUnzip.getName())) {
                            nameOut = FilenameUtils.removeExtension(fichierZip.getName()) + "_" + index + ".srt";
                            index++;
                        }
                    }
                    try {
                        FileUtils.moveFile(fichierZip, new File(fichierTemp.getPath() + "/" + nameOut));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
    }


    public static void main(String[] args) {
        SubtitlesDownloadSeries nzb = new SubtitlesDownloadSeries();
    }

}
