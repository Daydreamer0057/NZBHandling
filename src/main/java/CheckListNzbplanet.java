import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CheckListNzbplanet {
    public CheckListNzbplanet(){
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

            br.close();
            fr.close();

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

            driver.get("https://nzbplanet.net");


            // Input Email id and Password If you are already Register
            driver.findElement(By.name("username")).sendKeys("vlm.nzbplanet@gmail.com");
            driver.findElement(By.name("password")).sendKeys("<fdDDL0gMeqy");

            WebElement webElementTemp2 = driver.findElement(By.id("username"));
            webElementTemp2.submit();


            driver.get("https://nzbplanet.net/movies?t=2000&title=&actors=&director=&rating=5&genre=&from=&to=&ob=&offset=&per_page_nzb=250#results");



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

            String doc = driver.getPageSource();

            StringReader sr = new StringReader(doc);
            BufferedReader sbr = new BufferedReader(sr);

            String lineSource = "";
            while (lineSource!=null){
                lineSource = sbr.readLine();
                if(lineSource!=null){
                    if(lineSource.toLowerCase().contains("title=\"view movie\"")){
                        lineSource = sbr.readLine();

                        int pos2 = lineSource.indexOf("</a>");
                        String movie = lineSource.substring(0,pos2);

                        int pos3 = lineSource.indexOf("title=\"");
                        String year = "(" + lineSource.substring(pos3+7,pos3+11) + ")";

                        if(!containsName(listFilms,movie+" "+year)) {
                            pw.println(movie+" "+year);
                            pw.flush();
                        }
                    }
                }
            }

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
        CheckListNzbplanet epguides = new CheckListNzbplanet();

    }
}
