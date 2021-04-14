import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class G2A {
    ChromeDriver driver;
    ChromeDriver driver2;
    ChromeDriver driver3;

    public G2A() throws Exception{
        FileReader fr = new FileReader("c:/log/wishlist.txt");
        BufferedReader br = new BufferedReader(fr);
            System.setProperty("webdriver.chrome.driver", "e://temp/chromedriver.exe");
            driver = new ChromeDriver();
            //driver.manage().window().setPosition(new Point(-2000, 0));

        String line = "";
        while(line!=null) {
            line = br.readLine();
            if(line!=null) {
                Robot robot = new Robot();

                driver.get("https://www.instant-gaming.com/");

                //driver.findElement(By.className("checkbox-wrapper__checkbox--regular")).click();

                //driver.findElement(By.className("button--type-full")).click();


                driver.findElement(By.id("ig-header-search-box-input")).sendKeys(line);

                robot.keyPress(KeyEvent.VK_ENTER);
                robot.keyRelease(KeyEvent.VK_ENTER);

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
            G2A nzb = new G2A();
        } catch (Exception ex) {
        ex.printStackTrace();
    }

}



}
