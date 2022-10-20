import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;

public class DLCompraeLook {
    ChromeDriver driver;
    ChromeDriver driver2;
    ChromeDriver driver3;

    public DLCompraeLook() throws Exception {
        File base = new File("i://games");
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

        long seconds = System.currentTimeMillis();

        System.setProperty("webdriver.chrome.driver", "e://temp/chrome/chromedriver.exe");
        driver = new ChromeDriver();

        for(File fichierTemp : listeDirectoryFinal){
                driver.get("https://dlcompare.com/");

                // Input Email id and Password If you are already Register
                int pos = fichierTemp.getPath().lastIndexOf("\\");
                String path = fichierTemp.getPath().substring(pos+1,fichierTemp.getPath().length());
                driver.findElement(By.name("q")).sendKeys(path);
                System.out.println(path);

                WebElement webElementTemp2 = driver.findElement(By.name(("q")));
                webElementTemp2.submit();

                Robot robot = new Robot();
                robot.keyPress(KeyEvent.VK_CONTROL);
                robot.keyPress(KeyEvent.VK_T);
                robot.keyRelease(KeyEvent.VK_CONTROL);
                robot.keyRelease(KeyEvent.VK_T);

                ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
                //System.out.println("Handle info"+ driver.getWindowHandles());
                driver.switchTo().window(tabs.get(tabs.size() - 1));
        }
    }


    public static void main(String[] args) {
        try {
            DLCompraeLook nzb = new DLCompraeLook();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
