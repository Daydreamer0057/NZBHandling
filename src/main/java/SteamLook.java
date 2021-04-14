import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;

import java.awt.event.KeyEvent;

public class SteamLook {
    ChromeDriver driver;
    ChromeDriver driver2;
    ChromeDriver driver3;

    public SteamLook() throws Exception {
        Thread.sleep(5000);
        long seconds = System.currentTimeMillis();
        File base = new File("w://games");

        File[] fichiers = base.listFiles();

        ArrayList<String> listeDirectory = new ArrayList<>();
        listeDirectory.add("$RECYCLE.BIN");
        listeDirectory.add("__rhi_15692.5131");
        listeDirectory.add("2D Scroller");
        listeDirectory.add("Benchmark");
        listeDirectory.add("Games");
        listeDirectory.add("Intellij");
        listeDirectory.add("Music");
        listeDirectory.add("Tools");
        listeDirectory.add("Webstorm");

        System.setProperty("webdriver.chrome.driver", "e://temp/chromedriver.exe");
        driver = new ChromeDriver();

        for (File fichier : fichiers) {
            if(!listeDirectory.contains(fichier.getName())) {
                File[] listGames = fichier.listFiles();
                for(File fichierTemp : listGames) {


                    driver.get("https://store.steampowered.com/");

                    // Input Email id and Password If you are already Register
                    driver.findElement(By.name("term")).sendKeys(fichierTemp.getName());

                    WebElement webElementTemp2 = driver.findElement(By.name("term"));
                    webElementTemp2.submit();

                    Robot robot = new Robot();
                    robot.keyPress(KeyEvent.VK_CONTROL);
                    robot.keyPress(KeyEvent.VK_T);
                    robot.keyRelease(KeyEvent.VK_CONTROL);
                    robot.keyRelease(KeyEvent.VK_T);

                    ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
                    //System.out.println("Handle info"+ driver.getWindowHandles());
                    driver.switchTo().window(tabs.get(tabs.size()-1));
                }
            }
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
