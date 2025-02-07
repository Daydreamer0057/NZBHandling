package videofiles;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

public class CheckListNzbplanetUpdate {
    public CheckListNzbplanetUpdate(){
        try{
            ArrayList<String> listDuplicates = new ArrayList<>();

            PrintWriter pw = new PrintWriter(new File("e:/temp/list_found"));
            FileReader fr = new FileReader(("c://temp/liste_films.txt"));
            BufferedReader br = new BufferedReader(fr);

            ArrayList<String> listFilms = new ArrayList<>();

            String line = "";
            while (line != null) {
                line = br.readLine();
                if (line != null) {
                    line = line.replace(":","");
                    listFilms.add(line.toLowerCase());
                }
            }

            br.close();
            fr.close();

            WebDriver driver = null;

            File chromeDriverFile = new File("e:/temp/chrome");
            File[] listDriver = chromeDriverFile.listFiles();

            for(File fichierTemp : listDriver) {
                try {
                    if(fichierTemp.getName().endsWith("exe")) {
                        System.setProperty("webdriver.chrome.driver", fichierTemp.getPath());
                        ChromeOptions options = new ChromeOptions();
                        options.addArguments("--disable-search-engine-choice-screen");
                        driver = new ChromeDriver(options);
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


            driver.get("https://nzbplanet.net/movies?t=2000&title=&actors=&director=&rating=5&genre=&from=&to=&ob=&offset=&fper_page_nzb=#results");




            String doc = driver.getPageSource();

            ArrayList<String> listDoc = new ArrayList<>();

            //store list of links in memory
            StringReader srDoc = new StringReader(doc);
            BufferedReader sbrDoc = new BufferedReader(srDoc);

            String lineDoc = "";
            while (lineDoc!=null) {
                lineDoc = sbrDoc.readLine();
                if (lineDoc != null) {
                    lineDoc = lineDoc.trim();
                    if (lineDoc.toLowerCase().contains("/details/")) {
                        lineDoc = lineDoc.replace('.',' ');
                        lineDoc = lineDoc.replace(":","");
                        listDoc.add(lineDoc);
                    }
                }
            }

            sbrDoc.close();
            srDoc.close();

            StringReader sr = new StringReader(doc);
            BufferedReader sbr = new BufferedReader(sr);

            String lineSource = "";
            while (lineSource!=null) {
                lineSource = sbr.readLine();
                if (lineSource != null) {
                    lineSource = lineSource.trim();
                }
                if (lineSource != null) {
                    if (lineSource.toLowerCase().contains("title=\"view movie\"")) {
                        lineSource = sbr.readLine();
                        if (lineSource != null) {
                            lineSource = lineSource.trim();
                        }

                        int pos2 = lineSource.indexOf("</a>");
                        String movie = lineSource.substring(0, pos2);
                        movie = movie.toLowerCase();
                        String movieFinal = movie;
                        movie = movie.replace(":","");

                        String lineDuplicate = getByName(listDuplicates, movie);
                        if(lineDuplicate==null) {
                            listDuplicates.add(movie);
                            int pos3 = lineSource.indexOf("title=\"");
                            String year = "(" + lineSource.substring(pos3 + 7, pos3 + 11) + ")";

                            String lineSourceTemp = null;
                            try {
                                lineSourceTemp = getByName(listFilms, movie + " " + year);
                            } catch (NoSuchElementException ex) {

                            }

                            if (lineSourceTemp == null) {
                                pw.println(movieFinal + " " + year);
                                pw.flush();
                            }
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

    public String getByName(final List<String> list, final String name) throws NoSuchElementException {
        Optional<String> result = list.stream().filter(o -> o.toLowerCase().contains(name.toLowerCase())).findFirst();
        if(result.isPresent()) {
            return result.get();
        } else {
            return null;
        }

    }

    public List<String> getAllByName(final List<String> list, final String name){
        return list.stream().filter(str -> str.toLowerCase().contains(name.toLowerCase())).collect(Collectors.toList());
    }

    public static void main(String[] args) {
        CheckListNzbplanetUpdate epguides = new CheckListNzbplanetUpdate();

    }
}
