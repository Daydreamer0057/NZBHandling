import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import java.awt.event.KeyEvent;

public class SteamLook {
    ChromeDriver driver;
    ChromeDriver driver2;
    ChromeDriver driver3;

    public SteamLook() throws Exception {
        Thread.sleep(5000);
        FileReader fr = new FileReader("c:/log/wishlist.txt");
        BufferedReader br = new BufferedReader(fr);

        long seconds = System.currentTimeMillis();

        System.setProperty("webdriver.chrome.driver", "e://temp/chrome/chromedriver.exe");
        driver = new ChromeDriver();

        String line = "";
        while (line != null) {
            line = br.readLine();
            if (line != null) {
                driver.get("https://store.steampowered.com/");

                // Input Email id and Password If you are already Register
                driver.findElement(By.name("term")).sendKeys(line);

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
            }
        }
        br.close();
        fr.close();
    }


    public static void main(String[] args) {
        try {
            SteamLook nzb = new SteamLook();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
