import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.File;
import java.util.*;

public class RemoveGeekNames {
    public RemoveGeekNames() {
        HashMap<String, List<File>> mapTreated = new HashMap<>();
        File base = new File("z://film/new/treated");

        File[] fichiers = base.listFiles();

        ArrayList<File> listeDirectory = new ArrayList<File>();

        listeDirectory.add(new File("z:/test/test"));

        HashSet<File> listeFichierATraiter = new HashSet<File>();
        for (File fichier : fichiers) {
            if (fichier.isDirectory()) {
                listeDirectory.add(fichier);
            } else {
                if (!fichier.getName().endsWith(".srt") && (fichier.getName().endsWith(".mp4") || fichier.getName().endsWith(".mkv") || fichier.getName().endsWith(".avi"))) {
                    List listTemp = mapTreated.get(fichier.getName().toLowerCase().substring(0,2));
                    if(listTemp==null){
                        listTemp = new ArrayList();
                    }
                    listTemp.add(fichier);
                    mapTreated.put(fichier.getName().toLowerCase().substring(0,2),listTemp);
                }
            }
        }

        while (listeDirectory.size() > 0) {
            File fichier = listeDirectory.get(0);
            File[] fichierListe = fichier.listFiles();

            for (File fichierTemp : fichierListe) {
                if (fichierTemp.isDirectory()) {
                    listeDirectory.add(fichierTemp);
                } else {
                    if (!fichierTemp.getName().endsWith(".srt") && (fichierTemp.getName().endsWith(".mp4") || fichierTemp.getName().endsWith(".mkv") || fichierTemp.getName().endsWith(".avi"))) {
                        List listTemp = mapTreated.get(fichierTemp.getName().toLowerCase().substring(0,1));
                        if(listTemp==null){
                            listTemp = new ArrayList();
                        }
                        listTemp.add(fichierTemp);
                        mapTreated.put(fichierTemp.getName().toLowerCase().substring(0,2),listTemp);
                    }
                }
            }
            listeDirectory.remove(0);
        }

        ArrayList<String> listNot = new ArrayList<String>();

        try {
            // TimeUnit.HOURS.sleep(8);
            WebDriver driver = null;

            try {
//                System.setProperty("webdriver.chrome.driver", "e://temp/chrome/chromedriver.exe");
                driver = WebDriverManager.chromedriver().create();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            driver.get("https://nzbgeek.info/dashboard.php");

            // Input Email id and Password If you are already Register
            driver.findElement(By.name("username")).sendKeys("Daydreamer057b");
            driver.findElement(By.name("password")).sendKeys("iuwggpAe7vIWeI1k3KZ0uDjgSI66fP2W");

            WebElement webElementTemp2 = driver.findElement(By.name("username"));
            webElementTemp2.submit();

            driver.get("https://nzbgeek.info/dashboard.php?mycart");

            List<WebElement> listHrefBase = driver.findElements(By.tagName("a"));
            List<WebElement> listHref = new ArrayList<>();

            for(WebElement element : listHrefBase) {
                if (element.getAttribute("href").toLowerCase().contains("geekseek.php?guid=")) {
                    listHref.add(element);
                }
            }

            compareNames(driver, mapTreated, listHref);

            WebElement elementFinal = driver.findElement(By.id("mycart2"));

            elementFinal.click();

            WebElement elementFinalSubmit= driver.findElement(By.id("account_warning_button"));

            elementFinalSubmit.click();

            driver.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void compareNames(WebDriver driver, Map<String, List<File>> mapDownload, List<WebElement> listHref){
        try{
            int compteur = 0;
            int compteurCheck = 0;
            int compteurHref = 0;
            for (WebElement href : listHref) {
                System.out.println(compteurHref + " / " + listHref.size());
                compteurHref++;
                List<File> listFile = mapDownload.get(href.getAttribute("innerText").toLowerCase().substring(0,2));
//                System.out.println("Debug " + href.getAttribute("innerText"));
                if(listFile!=null) {
                    for (File fichierTreated : mapDownload.get(href.getAttribute("innerText").toLowerCase().substring(0, 2))) {
                        int resolutionTreated = 0;
                        int resolutionDownload = 0;

                        if (fichierTreated.getName().toLowerCase().contains("2160")) {
                            resolutionTreated = 2160;
                        }
                        if (fichierTreated.getName().toLowerCase().contains("1080")) {
                            resolutionTreated = 1080;
                        }
                        if (fichierTreated.getName().toLowerCase().contains("720")) {
                            resolutionTreated = 720;
                        }
                        if (fichierTreated.getName().toLowerCase().contains("576")) {
                            resolutionTreated = 576;
                        }
                        if (fichierTreated.getName().toLowerCase().contains("480")) {
                            resolutionTreated = 480;
                        }
                        if (fichierTreated.getName().toLowerCase().contains("360")) {
                            resolutionTreated = 360;
                        }
                        if (fichierTreated.getName().toLowerCase().contains("240")) {
                            resolutionTreated = 240;
                        }
                        if (fichierTreated.getName().toLowerCase().contains("dvd")) {
                            resolutionTreated = 480;
                        }

                        //Get name of href then resolution
                        String nameHref = href.getAttribute("innerText");

                        if (nameHref.contains("2160")) {
                            resolutionDownload = 2160;
                        }
                        if (nameHref.contains("1080")) {
                            resolutionDownload = 1080;
                        }
                        if (nameHref.contains("720")) {
                            resolutionDownload = 720;
                        }
                        if (nameHref.contains("576")) {
                            resolutionDownload = 576;
                        }
                        if (nameHref.contains("480")) {
                            resolutionDownload = 480;
                        }
                        if (nameHref.contains("360")) {
                            resolutionDownload = 360;
                        }
                        if (nameHref.contains("240")) {
                            resolutionDownload = 240;
                        }
                        if (nameHref.contains("dvd")) {
                            resolutionDownload = 480;
                        }

                        String nameFile = fichierTreated.getName();

                        String year = fichierTreated.getName().substring(nameFile.indexOf("(") + 1, nameFile.indexOf(")"));

                        if (nameHref.contains(year)) {

                            try {
                                if (nameFile.indexOf("(") != -1) {
                                    nameFile = nameFile.substring(0, nameFile.indexOf("("));
                                    nameFile = nameFile.trim();
                                    nameFile = nameFile.replaceAll("[-]", " ");
                                    nameFile = nameFile.replaceAll("[.]", " ");
                                }
                            } catch (Exception ex) {
                                ex.printStackTrace();
                                System.out.println("namefile " + nameFile);
                            }

                            if (nameFile.length() > 4) {
                                StringTokenizer stk = new StringTokenizer(nameFile, " ");
                                int nbToken = stk.countTokens();
//                        boolean[] testCount = new boolean[nbToken];

//                        for (int i = 0; i < stk.countTokens(); i++) {
//                            testCount[i] = false;
//                        }

                                boolean testFinal = true;
                                while (stk.hasMoreTokens()) {
                                    if (!nameHref.toLowerCase().contains(stk.nextToken().toLowerCase())) {
                                        testFinal = false;
                                        break;
                                    }

                                }


//                        for (int i = 0; i < nbToken; i++) {
//                            if (testCount[i]) {
//                                testFinal = true;
//                            } else {
//                                testFinal = false;
//                                break;
//                            }
//                        }

                                if (testFinal && (resolutionDownload <= resolutionTreated)) {
                                    System.out.println(nameHref + "    " + fichierTreated.getName() + "    " + resolutionDownload + "    " + resolutionTreated);
                                    String refCheckbox = href.getAttribute("href");
                                    refCheckbox = refCheckbox.substring(refCheckbox.indexOf("guid=") + 5, refCheckbox.length());
                                    WebElement checkBox = driver.findElement(By.id(refCheckbox));
                                    checkBox.click();

                            compteurCheck++;
//                            if(compteurCheck > 1000){
//                                break;
//                            }
                                }
                            }
                        }
                    }
                }
            }
            System.out.println("Compteur check "+compteurCheck);
        } catch(Exception ex){
            ex.printStackTrace();

        }
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        RemoveGeekNames nz = new RemoveGeekNames();
    }
}
