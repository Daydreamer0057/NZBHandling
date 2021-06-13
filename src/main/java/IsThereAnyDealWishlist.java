import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class IsThereAnyDealWishlist {
    ChromeDriver driver;
    ChromeDriver driver2;
    ChromeDriver driver3;

    public IsThereAnyDealWishlist() throws Exception{
        FileReader fr = new FileReader("c:/log/wishlist.txt");
        BufferedReader br = new BufferedReader(fr);
            System.setProperty("webdriver.chrome.driver", "e://temp/chromedriver.exe");
            driver = new ChromeDriver();
            //driver.manage().window().setPosition(new Point(-2000, 0));

        String line = "";
        while(line!=null) {
            line = br.readLine();
            if(line!=null) {
                driver.get("https://isthereanydeal.com/");


                driver.findElement(By.id("searchbox")).sendKeys(line);

                WebElement webElementTemp2 = driver.findElement(By.id("searchbox"));
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
            IsThereAnyDealWishlist nzb = new IsThereAnyDealWishlist();
        } catch (Exception ex) {
        ex.printStackTrace();
    }

}



}
