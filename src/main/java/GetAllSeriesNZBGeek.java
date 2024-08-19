import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.util.List;
import java.util.NoSuchElementException;

public class GetAllSeriesNZBGeek {
    public GetAllSeriesNZBGeek(){
        try{
            int season = 26;
            int code = 75897;

            WebDriver driver = null;

                // System.out.println(fichierTemp.getPath());
                File chromeDriverFile = new File("e:/temp/chrome");
                File[] listDriver = chromeDriverFile.listFiles();

                for(File fichierTemp : listDriver) {
                    try {
                        if(fichierTemp.getName().endsWith("exe")) {
                            System.setProperty("webdriver.chrome.driver", fichierTemp.getPath());
                            driver = new ChromeDriver();
                            break;
                        }
                    } catch (Exception ex) {
                        // test = true;
                        ex.printStackTrace();
                    }
                }

            driver.get("https://nzbgeek.info");


            // Input Email id and Password If you are already Register
            driver.findElement(By.name("username")).sendKeys("monnetcarriere");
            driver.findElement(By.name("password")).sendKeys("M4N3CCGmHOhGOrahxMfwsPSxyVA3xcrU");

            WebElement webElementTemp2 = driver.findElement(By.id("username"));
            webElementTemp2.submit();

                for(int i=1;i<=season;i++) {
                    if(i<10) {
                        driver.get("https://nzbgeek.info/geekseek.php?tvid="+code+"&season=S0" + i + "&episode=all");
                    } else {
                        driver.get("https://nzbgeek.info/geekseek.php?tvid="+code+"&season=S" + i + "&episode=all");
                    }


//            List<WebElement> webElementHref = driver.findElements(By.tagName("a"));

                    int compteur = 0;
//            for(WebElement element : webElementHref) {
//                System.out.println("Principal "+compteur++ + " / " + webElementHref.size());
//                if (element.getAttribute("title").toLowerCase().contains("view movie")&&!element.getText().toLowerCase().equals("")) {
//                    if(!containsName(listFilms, element.getText().toLowerCase())){
//                        pw.println(element.getText());
//                        pw.flush();
//                    }
//                }
//            }

                    WebElement selectAll = driver.findElement(By.name("select-all-1"));
                    selectAll.click();
                    WebElement download = driver.findElement(By.name("mycart2"));
                    download.click();

                    Thread.sleep(2000);

                    try {
                        WebElement send = driver.findElement(By.id("confirm_button"));
                        send.submit();
                    } catch(NoSuchElementException ex){
                        ex.printStackTrace();
                    } finally {
                        System.out.println("Compteur fin "+i);
                    }
//                    Thread.sleep(10000);
                }

            driver.close();

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public boolean containsName(final List<String> list, final String name){
        return list.stream().filter(o -> o.toLowerCase().contains(name.toLowerCase())).findFirst().isPresent();
    }

    public static void main(String[] args) {
        GetAllSeriesNZBGeek epguides = new GetAllSeriesNZBGeek();

    }
}
