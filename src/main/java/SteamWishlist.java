import Utilities.UnzipFiles;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SteamWishlist {
    ChromeDriver driver;
    ChromeDriver driver2;
    ChromeDriver driver3;

    public SteamWishlist() throws Exception{
        FileReader fr = new FileReader("c:/log/wishlist.txt");
        BufferedReader br = new BufferedReader(fr);
            System.setProperty("webdriver.chrome.driver", "e://temp/chrome/chromedriver.exe");
            driver = new ChromeDriver();
            //driver.manage().window().setPosition(new Point(-2000, 0));

        String line = "";
        while(line!=null) {
            line = br.readLine();
            if(line!=null) {
                driver.get("https://www.dlcompare.fr/");


                driver.findElement(By.id("right-label-large")).sendKeys(line);

                WebElement webElementTemp2 = driver.findElement(By.id("right-label-large"));
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

    public static void main(String[] args)
    {
        try {
            SteamWishlist nzb = new SteamWishlist();
        } catch (Exception ex) {
        ex.printStackTrace();
    }

}



}
