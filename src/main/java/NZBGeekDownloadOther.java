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
			} catch (Exception ex) {
				// test = true;
				ex.printStackTrace();
			}

			for(int i=1;i<15;i++) {

				driver = new ChromeDriver();

				driver.get("https://nzbgeek.info/logon.php");

				// Input Email id and Password If you are already Register
				driver.findElement(By.name("username")).sendKeys("Daydreamer057a");
				driver.findElement(By.name("password")).sendKeys("N0dQMkLgH3KP3yxae6CZj3CnkBQLlNfp");

				WebElement webElementTemp2 = driver.findElement(By.name("username"));
				webElementTemp2.submit();

				/*webElementTemp2 = driver.findElement(By.className("warning_button"));*/
				/*webElementTemp2.submit();*/

				List<String> listHref = new ArrayList<String>();

				driver.get(
						"https://nzbgeek.info/geekseek.php?moviesgeekseek=1&c=5000&browseincludewords=olympic&search=" +i);


				List<WebElement> links = driver.findElements(By.tagName("a"));

				// System.out.println("links size " + links.size());
				int compteurEpisodes = 0;
				for (WebElement webElement : links) {
					// if (webElement.getAttribute("title").equalsIgnoreCase("Download NZB")) {
					if (webElement.getAttribute("href") != null && webElement.getAttribute("href").contains("api?t=get")) {
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
				driver.findElement(By.name("username")).sendKeys("Daydreamer057a");
				driver.findElement(By.name("password")).sendKeys("N0dQMkLgH3KP3yxae6CZj3CnkBQLlNfp");

				WebElement webElementTemp3 = driver.findElement(By.name("username"));
				webElementTemp3.submit();


				int compteurFinal = 0;
				for (String lineTemp : listHref) {
					compteurFinal++;
					System.out.println(compteurFinal + " / " + listHref.size());
					driver.get(lineTemp);
				}
				driver.close();
			}
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
