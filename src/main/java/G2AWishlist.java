import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class G2AWishlist {
    ChromeDriver driver;
    ChromeDriver driver2;
    ChromeDriver driver3;

    public G2AWishlist() throws Exception{
        FileReader fr = new FileReader("c:/log/wishlist.txt");
        BufferedReader br = new BufferedReader(fr);
            System.setProperty("webdriver.chrome.driver", "e://temp/chromedriver.exe");
            driver = new ChromeDriver();
            //driver.manage().window().setPosition(new Point(-2000, 0));

        String line = "";
        while(line!=null) {
            line = br.readLine();
            if(line!=null) {
                line = line.replaceAll(" ","%20");
                driver.get("https://www.g2a.com/search?query="+line);

                WebElement webElement = driver.findElement(By.id("checkbox-all-agreed"));
                webElement.click();;

                WebElement webElementTemp2 = driver.findElement(By.className("button button--size-large button--type-full"));
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
            G2AWishlist nzb = new G2AWishlist();
        } catch (Exception ex) {
        ex.printStackTrace();
    }

}



}
