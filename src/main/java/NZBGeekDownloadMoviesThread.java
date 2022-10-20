import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.ArrayList;
import java.util.List;

public class NZBGeekDownloadMoviesThread implements Runnable{
    WebDriver driver;
    List<WebElement> linkSeason;
    List<WebElement> listCheckBox;
    public NZBGeekDownloadMoviesThread(WebDriver driver, List<WebElement> linkSeason, List<WebElement> listCheckBox){
        this.driver = driver;
        this.linkSeason = linkSeason;
        this.listCheckBox = listCheckBox;
    }

    @Override
    public void run() {
        int compteurFinal = 0;
        int compteurTotal = 0;
        for (WebElement lineTemp : linkSeason) {
            String url = "";
            try {
                url = lineTemp.getAttribute("href");
            } catch (Exception ex) {
                ex.printStackTrace();
                driver.navigate().back();
                break;
            }
            System.out.println("Downloaded " + compteurFinal + "    Compteur " + compteurTotal + " / " + linkSeason.size());

            if (url.contains("api?t=get&id=")) {
                boolean testCheck = false;
                WebElement checkTemp = null;
                for (WebElement checkbox : listCheckBox) {
                    if (url.contains(checkbox.getAttribute("id"))) {
//							if(!listCheckBoxFinal.contains(checkbox)) {
                        checkbox.click();
//								listCheckBoxFinal.add(checkbox);
                        testCheck = true;
                        checkTemp = checkbox;
                        break;
//							}
                    }
                }

                if (testCheck) {
                    listCheckBox.remove(checkTemp);
                }

                driver.get(url);
//						String source = driver.getPageSource();
//						System.out.println(source);
//						if(source.toLowerCase().contains("code=\"300\"")){
//							driver.navigate().back();
//							break;
//						}
//					System.exit(0);
                compteurFinal++;
            }
            compteurTotal++;
        }
    }
}
