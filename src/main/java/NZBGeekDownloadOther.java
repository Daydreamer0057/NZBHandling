import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NZBGeekDownloadOther {

	public NZBGeekDownloadOther() {
		 ArrayList<String> listNot = new ArrayList<String>();

		String series = "Your Honor";
//		String alt = "KominskyMethod";
//		String seriesSeasons = series;
//		seriesSeasons = seriesSeasons.replace(" ","");
//		ArrayList<String> listNot = preparation("f://humour/" + series);
//		series = series.replace(' ', '+');
		int s = 1;

		int compteurMax = 0;

		int change = 0;
		try {
			// TimeUnit.HOURS.sleep(8);
			WebDriver driver = null;

			// File fichierChrome = new File("e://temp/chromedriver 2");
			// File[] fichiers = fichierChrome.listFiles();
			//
			// for (File fichierTemp : fichiers) {
			// boolean test = false;
			try {
				// System.out.println(fichierTemp.getPath());
				System.setProperty("webdriver.chrome.driver", "e://temp/chromedriver.exe");
				driver = new ChromeDriver();
			} catch (Exception ex) {
				// test = true;
				ex.printStackTrace();
			}

			driver.get("https://nzbgeek.info/logon.php");

			// Input Email id and Password If you are already Register
			driver.findElement(By.name("username")).sendKeys("Daydreamer057");
			driver.findElement(By.name("password")).sendKeys("ULbwkKMxNM0umW7AmcppNLEhXwFW0K0K");

			WebElement webElementTemp2 = driver.findElement(By.name("username"));
			webElementTemp2.submit();

			/*webElementTemp2 = driver.findElement(By.className("warning_button"));*/
			/*webElementTemp2.submit();*/

			List<String> listHref = new ArrayList<String>();

								driver.get(
										"https://nzbgeek.info/geekseek.php?&browseincludewords=dvdfab&search=2");


					List<WebElement> links = driver.findElements(By.tagName("a"));

					// System.out.println("links size " + links.size());
					int compteurEpisodes = 0;
					for (WebElement webElement : links) {
						// if (webElement.getAttribute("title").equalsIgnoreCase("Download NZB")) {
						if (webElement.getAttribute("href")!=null&&webElement.getAttribute("href").contains("api?t=get")) {
							listHref.add(webElement.getAttribute("href"));
							compteurEpisodes++;
						}
					}


			// for (int i = 0; i < 12; i++) {

			// }
			driver.close();

			driver = new ChromeDriver();
			driver.get("https://nzbgeek.info/logon.php");

			// Input Email id and Password If you are already Register
			driver.findElement(By.name("username")).sendKeys("Daydreamer057");
			driver.findElement(By.name("password")).sendKeys("ULbwkKMxNM0umW7AmcppNLEhXwFW0K0K");

			WebElement webElementTemp3 = driver.findElement(By.name("username"));
			webElementTemp3.submit();


			int compteurFinal = 0;
			for (String lineTemp : listHref) {
				System.out.println(compteurFinal+" / "+listHref.size());
				driver.get(lineTemp);
				compteurFinal++;
			}
			driver.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public ArrayList<String> preparation(String line) {
		ArrayList<String> list = new ArrayList<String>();

		if (line != "") {
			File fichier = new File(line);
			File[] fichiers = fichier.listFiles();

			for (File fichierTemp : fichiers) {
				Pattern pattern = Pattern.compile("S[0-9]*E[0-9]*");
				Matcher matcher = pattern.matcher(fichierTemp.getName());
				String episode = "";
				try {
					while (matcher.find()) {
						list.add(matcher.group(0));
					}
				} catch (Exception e) {

				}
			}
		}
		return list;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		NZBGeekDownloadOther nz = new NZBGeekDownloadOther();
	}

}
