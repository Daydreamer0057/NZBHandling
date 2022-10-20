import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;

public class TriSeriesMyShows {
    ChromeDriver driver;
    ChromeDriver driver2;
    ChromeDriver driver3;

    public TriSeriesMyShows() throws Exception{
        System.setProperty("webdriver.chrome.driver", "e://temp/chrome/chromedriver.exe");
        driver = new ChromeDriver();

        String pathPrincipal = "z://test/error";
        File base = new File(pathPrincipal);
//		File base = new File("F://The Mandalorian");

        File[] fichiers = base.listFiles();

        HashSet<File> listeFichier = new HashSet<File>();
        ArrayList<File> listeDirectory = new ArrayList<File>();
        // listeDirectory.add(new File("z://test/film"));

        for (File fichier : fichiers) {
            System.out.println("Files "+listeFichier.size()+"    Directory "+listeDirectory.size());
            if (fichier.isDirectory()) {
                listeDirectory.add(fichier);
            } else {
                if (fichier.getName().endsWith(".mkv") || fichier.getName().endsWith(".mp4")
                        || fichier.getName().endsWith(".avi")) {
                    listeFichier.add(fichier);
                }
            }
        }

        while (listeDirectory.size() > 0) {
            System.out.println("Files "+listeFichier.size()+"    Directory "+listeDirectory.size());
            File fichier = listeDirectory.get(0);

            File[] fichierListe = fichier.listFiles();

            for (File fichierTemp : fichierListe) {
                if (fichierTemp.isDirectory()) {
                    listeDirectory.add(fichierTemp);
                } else {
                    if (fichierTemp.getName().endsWith(".mkv") || fichierTemp.getName().endsWith(".mp4")
                            || fichierTemp.getName().endsWith(".avi")) {
                        listeFichier.add(fichierTemp);
                    }
                }
            }
            listeDirectory.remove(0);
        }

        ArrayList<String> listContain = new ArrayList<>();

        driver.get("https://nzbplanet.net");

        // Input Email id and Password If you are already Register
        driver.findElement(By.name("username")).sendKeys("russellmatthew7532");
        driver.findElement(By.name("password")).sendKeys("|E_SK9l1j_@eq9?7");

        WebElement webElementTemp2 = driver.findElement(By.name("username"));
        webElementTemp2.submit();

        String line = "";
        for(File fichier : listeFichier){
            String name = fichier.getName().substring(0,fichier.getName().indexOf("-"));
            name = name.trim();

            if(!listContain.contains(name)) {
                driver.get("https://nzbplanet.net/series");

                WebElement webElementTemp3 = driver.findElement(By.name("searchword"));

                driver.findElement(By.name("searchword")).sendKeys(name);

                webElementTemp3.submit();

                Robot robot = new Robot();
                robot.keyPress(KeyEvent.VK_CONTROL);
                robot.keyPress(KeyEvent.VK_T);
                robot.keyRelease(KeyEvent.VK_CONTROL);
                robot.keyRelease(KeyEvent.VK_T);

                ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
                //System.out.println("Handle info"+ driver.getWindowHandles());
                driver.switchTo().window(tabs.get(tabs.size() - 1));

                listContain.add(name);
            }
        }
    }

    public static void main(String[] args)
    {
        try {
            TriSeriesMyShows nzb = new TriSeriesMyShows();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }



}
