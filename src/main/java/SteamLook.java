import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;

import java.awt.event.KeyEvent;
import java.util.HashSet;

public class SteamLook {
    ChromeDriver driver;
    ChromeDriver driver2;
    ChromeDriver driver3;

    public SteamLook() throws Exception {
        FileWriter fw = new FileWriter("e://temp/list_steam.txt");
        PrintWriter pw = new PrintWriter(fw);
        File base = new File("z://games");
        //File base = new File("d://film/new/Film 20200226");

        File[] fichiers = base.listFiles();

        ArrayList<File> listeDirectory = new ArrayList<File>();
       HashSet<File> listeDirectoryFinal = new HashSet<>();

        for (File fichier : fichiers) {
            if (fichier.isDirectory()) {
                listeDirectory.add(fichier);
                listeDirectoryFinal.add(fichier);
            }
        }

        while (listeDirectory.size() > 0) {
            System.out.println(listeDirectoryFinal.size()+"    "+listeDirectory.size());
            File fichier = listeDirectory.get(0);

            File[] fichierListe = fichier.listFiles();

            if (fichierListe != null) {
                for (File fichierTemp : fichierListe) {
                    if (fichierTemp.isDirectory()) {
                        listeDirectoryFinal.add(fichierTemp);
                    }
                }
            }
            listeDirectory.remove(0);
        }

        for(File fichierTemp : listeDirectoryFinal){
            pw.println(fichierTemp.getName());
        }

        pw.flush();
        pw.close();
        fw.close();

//        System.exit(0);

        long seconds = System.currentTimeMillis();

        System.setProperty("webdriver.chrome.driver", "e://temp/chrome/chromedriver.exe");
        driver = new ChromeDriver();

        for(File fichierTemp : listeDirectoryFinal){
                driver.get("https://store.steampowered.com/");

                // Input Email id and Password If you are already Register
                int pos = fichierTemp.getPath().lastIndexOf("\\");
                String path = fichierTemp.getPath().substring(pos+1,fichierTemp.getPath().length());
                driver.findElement(By.name("term")).sendKeys(path);
                System.out.println(path);

                WebElement webElementTemp2 = driver.findElement(By.name("term"));
                webElementTemp2.submit();

                Robot robot = new Robot();
                robot.keyPress(KeyEvent.VK_CONTROL);
                robot.keyPress(KeyEvent.VK_T);
                robot.keyRelease(KeyEvent.VK_CONTROL);
                robot.keyRelease(KeyEvent.VK_T);

                ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
                //System.out.println("Handle info"+ driver.getWindowHandles());
                driver.switchTo().window(tabs.get(tabs.size() - 1));
//                Thread.sleep(5000);
        }
    }


    public static void main(String[] args) {
        try {
            SteamLook nzb = new SteamLook();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
