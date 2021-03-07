import org.apache.commons.io.FileExistsException;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import Utilities.UnzipFiles;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SubtitlesDownload {
    ChromeDriver driver;
    ChromeDriver driver2;

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
        //try {
        //File fichierDriver = new File("g://temp/chromedriver");
        //File[] listeDriver = fichierDriver.listFiles();

        //for (File fichierTemp : listeDriver) {
        //  boolean test = false;
        try {
            System.setProperty("webdriver.chrome.driver", "e://temp/chromedriver.exe");
            driver = new ChromeDriver();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        //if (!test) {
        //   break;
        //}
        //}

        int compteur = 0;
        for (File fichierTemp : listeFichier) {
            compteur++;
            if (compteur >= 0) {
                System.out.println("Compteur " + compteur);
                Pattern pattern = Pattern.compile("[0-9][0-9][0-9][0-9]");
                Matcher matcher = pattern.matcher(fichierTemp.getName());
                String year = "";
                Integer pos = 0;
                // Check all occurrences
                if (matcher.find()) {
                    if (matcher.group(0).indexOf("(") != -1) {
                        year = matcher.group(0).substring(1, matcher.start(0) - 1);
                        pos = matcher.start(0) - 1;
                    } else {
                        year = matcher.group(0);
                        pos = matcher.start(0) - 1;
                    }

                    String name = fichierTemp.getName().substring(0, pos);
                    name = name.replaceAll("_","'");
                    name = name.trim();

                    driver.get("https://www.podnapisi.net/en/subtitles/search/advanced");

                    while (driver.getPageSource().contains("503 Service Temporarily Unavailable")) {
                        driver.get("https://www.podnapisi.net/en/subtitles/search/advanced");
                    }

                    // String page = driver.getPageSource();

                    // Input Email id and Password If you are already Register
                    List<WebElement> keyWords = driver.findElements(By.name("keywords"));

                    WebElement webK = keyWords.get(1);

                    webK.sendKeys(name);

                    driver.findElement(By.name("keywords")).sendKeys(name);

                    List<WebElement> years = driver.findElements(By.name("year"));

                    WebElement webE = years.get(1);

                    webE.sendKeys(year);

                    driver.findElement(By.name("keywords")).submit();

                    List<WebElement> links = driver.findElements(By.tagName("a"));

                    File fichierDriver2 = new File("g://temp/chromedriver");
                    File[] listeDriver2 = fichierDriver2.listFiles();

                    try {
                        System.setProperty("webdriver.chrome.driver", "e://temp/chromedriver.exe");
                        driver2 = new ChromeDriver();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                    for (WebElement webElement : links) {
                        try {
                            if (webElement!=null&&webElement.getAttribute("href")!=null&&webElement.getAttribute("href").indexOf("download") != -1) {
                                driver2.get(webElement.getAttribute("href"));
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }

                    driver2.close();

                    Path p2 = Paths.get(fichierTemp.getPath());
                    Path folder2 = p2.getParent();
                    String path2 = folder2.toString();
                    path2 = path2.replaceAll("\\\\", "/");

                    File downloadLocation = new File(path2);
                    File sub = new File("C://Users/bmonnet/Downloads");
                    File[] listSrt = sub.listFiles();

                        for (File fileZip : listSrt) {
                            StringTokenizer stk = new StringTokenizer(name," ");
                            String nameZip = stk.nextToken();
                            nameZip = nameZip.toLowerCase();

                            if (fileZip.getName().contains(nameZip)) {
                                try {
                                    FileUtils.moveFile(fileZip, new File("c://temp/subtitles/" + fileZip.getName()));
                                    Path p3 = Paths.get(fileZip.getPath());
                                    Path folder3 = p3.getParent();
                                    String path3 = folder3.toString();
                                    path3 = path3.replaceAll("\\\\", "/");

                                    try {
                                        UnzipFiles.unzip("c://temp/subtitles/" + fileZip.getName(), "c://temp/subtitles");
                                    } catch (Exception ex) {
                                        ex.printStackTrace();
                                    }
                                }catch(Exception ex){
                                    ex.printStackTrace();
                                }
                            }
                        }

                    sub = new File("C://temp/subtitles");

                    File[] fichiers2 = sub.listFiles();

                    HashSet<File> listeFichier2 = new HashSet<File>();
                    ArrayList<File> listeDirectory2 = new ArrayList<File>();
                    // listeDirectory.add(new File("z://temp/film"));

                    for (File fichier : fichiers2) {
                        if (fichier.isDirectory()) {
                            listeDirectory2.add(fichier);
                        } else {
                            if (fichier.getName().endsWith("srt")) {
                                listeFichier2.add(fichier);
                            }
                        }
                    }

                    while (listeDirectory2.size() > 0) {
                        File fichier = listeDirectory2.get(0);

                        File[] fichierListe2 = fichier.listFiles();

                        for (File fichierTemp2 : fichierListe2) {
                            if (fichierTemp2.isDirectory()) {
                                listeDirectory2.add(fichierTemp2);
                            } else {
                                if (fichierTemp2.getName().endsWith("srt")) {
                                    listeFichier2.add(fichierTemp2);
                                }
                            }
                        }
                        listeDirectory2.remove(0);
                    }

                    for (File fileSrt : listeFichier2) {
                        try {
                            FileUtils.moveFile(fileSrt, new File(path2 + "/" + fileSrt.getName()));
                        } catch (FileExistsException fe) {

                            // if (new File(path2 + "/" + fileSrt.getName()).delete()) {
                            // try {
                            // FileUtils.moveFile(fileSrt, new File(path2 + "/" + fileSrt.getName()));
                            // } catch (IOException e) {
                            // e.printStackTrace();
                            // }
                            // }
                        } catch (Exception e) {

                            e.printStackTrace();
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
