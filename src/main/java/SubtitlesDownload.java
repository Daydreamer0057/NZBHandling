import Utilities.UnzipFiles;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SubtitlesDownload {
    ChromeDriver driver;
    ChromeDriver driver2;
    ChromeDriver driver3;

    public SubtitlesDownload() {
        long seconds = System.currentTimeMillis();
        File base = new File("z://film/new");

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
            System.setProperty("webdriver.chrome.driver", "e://temp/chromedriver.exe");
            driver = new ChromeDriver();
            driver.manage().window().setPosition(new Point(-2000, 0));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        int compteur = 0;
        for (File fichierTemp : listeFichier) {
            compteur++;
            if (compteur >= 2505) {
                System.out.println("Compteur " + compteur+ " / "+listeFichier.size());
                /*Pattern pattern = Pattern.compile("\\([0-9]+");
                Matcher matcher = pattern.matcher(fichierTemp.getName());
                */
                StringTokenizer stk = new StringTokenizer(fichierTemp.getName(),"(");
                String year = "";
                Integer pos = 0;
                // Check all occurrences
                if (stk.hasMoreTokens()){
                    String name = stk.nextToken();
                    name = name.trim();

                    if (stk.hasMoreTokens()) {
                        year = stk.nextToken().substring(0, 4);
                        year = year.trim();

                        try {
                            System.setProperty("webdriver.chrome.driver", "e://temp/chromedriver.exe");
                            driver3 = new ChromeDriver();
                            driver3.manage().window().setPosition(new Point(0, 2000));
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        driver3.get("https://www.podnapisi.net/en/subtitles/search/?episodes=&movie_type=&year=" + year + "&keywords=" + name + "&seasons=&type=");


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
                                driver.manage().window().setPosition(new Point(-2000, 0));
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                            System.out.println("Page " + page);
                            driver.get("https://www.podnapisi.net/en/subtitles/search/?episodes=&movie_type=&year=" + year + "&keywords=" + name + "&seasons=&type=&page=" + page);

                            while (driver.getPageSource().contains("503 Service Temporarily Unavailable")) {
                                driver.get("https://www.podnapisi.net/en/subtitles/search/?episodes=&movie_type=&year=" + year + "&keywords=" + name + "&seasons=&type=&page=" + page);
                            }

                            List<WebElement> links = driver.findElements(By.tagName("a"));

                            System.out.println("Links done");

                            try {
                                System.setProperty("webdriver.chrome.driver", "e://temp/chromedriver.exe");
                                driver2 = new ChromeDriver();
                                driver2.manage().window().setPosition(new Point(-2000, 0));
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

                        HashSet<File> listFile = new HashSet<File>();
                        ArrayList<File> listeDirectory2 = new ArrayList<File>();

                        for (File fichier : listZip) {
                            if (fichier.isDirectory()) {
                                listeDirectory2.add(fichier);
                            } else {
                                listFile.add(fichier);
                            }
                        }

                        while (listeDirectory2.size() > 0) {
                            File fichier = listeDirectory2.get(0);
                            File[] fichierListe = fichier.listFiles();

                            for (File fichierTemp2 : listFile) {
                                if (fichierTemp2.isDirectory()) {
                                    listeDirectory2.add(fichierTemp2);
                                } else {
                                    listFile.add(fichierTemp2);
                                }
                            }
                            listeDirectory2.remove(0);
                        }

                        for (File fichierZip : listFile) {
                            String nameOut = fichierZip.getName();
                            char nameOutChar = fichierTemp.getName().charAt(0);
                            if (nameOutChar > '0' && nameOutChar <= '9') {
                                nameOutChar = '0';
                            }

                            if(nameOutChar == '#'){
                                nameOutChar = '0';
                            }

                            File unzipPath = new File("z://film/new/subtitles/" + nameOutChar);
                            File[] listUnzip = unzipPath.listFiles();
                            if (listUnzip == null) {
                                System.out.println("test");
                            }
                            for (File fichierUnzip : listUnzip) {
                                int index = 1;
                                while (nameOut.equalsIgnoreCase(fichierUnzip.getName())) {
                                    nameOut = FilenameUtils.removeExtension(fichierZip.getName()) + "_" + index + ".srt";
                                    index++;
                                }
                            }
                            try {
                                nameOut = nameOut.replaceAll(":"," ");
                                nameOut = nameOut.replaceAll(";"," ");
                                nameOut = nameOut.replaceAll("!"," ");
                                FileUtils.moveFile(fichierZip, new File(unzipPath + "/" + nameOut));
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        SubtitlesDownload nzb = new SubtitlesDownload();
    }

}
