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

public class SteamWishlist {
    ChromeDriver driver;
    ChromeDriver driver2;
    ChromeDriver driver3;

    public SteamWishlist() {
        try {
            System.setProperty("webdriver.chrome.driver", "e://temp/chromedriver.exe");
            driver = new ChromeDriver();
            //driver.manage().window().setPosition(new Point(-2000, 0));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

            driver.get("https://store.steampowered.com/wishlist/profiles/76561198028546793/#sort=releasedate&term=");


        List<WebElement> links = driver.findElements(By.tagName("a"));

        for(WebElement link : links){
            System.out.println(link.getAttribute("href"));
        }



    }

    public static void main(String[] args) {
        SteamWishlist nzb = new SteamWishlist();
    }



}
