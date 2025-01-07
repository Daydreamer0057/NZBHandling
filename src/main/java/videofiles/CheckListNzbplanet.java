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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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

            fr = new FileReader(("e://temp/list_file_cancel.txt"));
            br = new BufferedReader(fr);

            ArrayList<String> listFilmsCancel = new ArrayList<>();

            line = "";
            while (line != null) {
                line = br.readLine();
                if (line != null) {
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

//            PrintWriter pwDoc = new PrintWriter("c://temp/liste temp.txt");
//            pw.println(doc);
//            pw.flush();
//            pw.close();
//            System.exit(0);

//            String doc = "";

//            Path filePath = Paths.get("c:/temp/liste temp.txt");
//            doc = Files.readString(filePath);
//            System.out.println("Begin details");
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

                        int pos3 = lineSource.indexOf("title=\"");
                        String year = "(" + lineSource.substring(pos3 + 7, pos3 + 11) + ")";

                        String yearDoc = lineSource.substring(pos3 + 7, pos3 + 11);

                        int resolutionDoc = 0;
//                        movie = movie.replaceAll(":","");

                        List<String> listCheck = getAllByName(listDoc, movie + " " + yearDoc);

                        boolean testResolution = false;
                        for (String lineTemp : listCheck) {
                            Pattern p2 = Pattern.compile(".*([0-9]{4}[pP]).*");
                            Matcher m2 = p2.matcher(lineTemp.toLowerCase());

                            if (m2.matches()) {
                                int resolutionTemp = Integer.parseInt(m2.group(1).toLowerCase().substring(0, m2.group(1).length() - 1));
                                if (resolutionTemp > resolutionDoc) {
                                    resolutionDoc = resolutionTemp;
                                }
                            }
                        }
                        int resolutionSource = 0;

                        String lineSourceTemp = null;
                        try {
                            lineSourceTemp = getByName(listFilms, movie + " " + year);
                        } catch(NoSuchElementException ex) {

                        }

                        if (lineSourceTemp != null) {
                            Pattern p3 = Pattern.compile(".*([0-9]{4}[pP]).*");
                            Matcher m3 = p3.matcher(lineSourceTemp.toLowerCase());

                            if (m3.matches()) {
                                resolutionSource = Integer.parseInt(m3.group(1).toLowerCase().substring(0, m3.group(1).length() - 1));
                            }

                            String lineCancel = null;

                            try {
                                lineCancel = getByName(listFilmsCancel, movie + " " + year);
                            } catch(NoSuchElementException ex) {

                            }

                            if ((resolutionDoc > resolutionSource)&&lineCancel==null&&resolutionSource!=0&&resolutionDoc!=0) {
                                pw.println(movie + " " + year);
                                pw.flush();
                            }
                        } else {
                                pw.println(movie + " " + year);
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

    public String getByName(final List<String> list, final String name) throws NoSuchElementException {
        return list.stream().filter(o -> o.toLowerCase().contains(name.toLowerCase())).findFirst().get();
    }

    public List<String> getAllByName(final List<String> list, final String name){
        return list.stream().filter(str -> str.toLowerCase().contains(name.toLowerCase())).collect(Collectors.toList());
    }

    public static void main(String[] args) {
        CheckListNzbplanet epguides = new CheckListNzbplanet();

    }
}
