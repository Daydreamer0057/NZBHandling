import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CheckListNZBGeek {
    public CheckListNZBGeek(){
        try{
            PrintWriter pw = new PrintWriter(new File("e:/temp/list_found"));
            FileReader fr = new FileReader(("c://temp/liste_films.txt"));
            BufferedReader br = new BufferedReader(fr);

            ArrayList<String> listFilms = new ArrayList<>();

            String line = "";
            while (line != null) {
                line = br.readLine();
                if (line != null) {
                    listFilms.add(line.toLowerCase());
                }
            }

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
            driver.findElement(By.name("username")).sendKeys("vbuffey091");
            driver.findElement(By.name("password")).sendKeys("7w3meFXClByi59s3T4ujSl0IcWYQEwBb");

            WebElement webElementTemp2 = driver.findElement(By.id("username"));
            webElementTemp2.submit();

                for(int i=1;i<6;i++) {
                    driver.get("https://nzbgeek.info/geekseek.php?browseincludewords=&browseignorewords=&browsepostedby=&browsequality=&browseformat=&c=2000&browsesizemin=&browsesizemax=&browsemingrabs=&browsepostage=10&browsereleasesub=&browsereleaselang=English&movietitle=&actors=&director=&writer=&year=&genre=&minrating=5&maxrating=&savedseek=&view=2&search="+i);


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

                    List<WebElement> countLinks = driver.findElements(By.className("overlay_title"));
                    for(WebElement element : countLinks){
                        if(!containsName(listFilms,element.getText())) {
                            pw.println(element.getText());
                            pw.flush();
                        }
                    }
                }

            br.close();
            fr.close();
            pw.flush();
            pw.close();

            driver.close();

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public boolean containsName(final List<String> list, final String name){
        return list.stream().filter(o -> o.toLowerCase().contains(name.toLowerCase())).findFirst().isPresent();
    }

    public static void main(String[] args) {
        CheckListNZBGeek epguides = new CheckListNZBGeek();

    }
}
