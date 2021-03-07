import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NZBGeekDownloadSeriesMissing {

	public NZBGeekDownloadSeriesMissing() {
		// ArrayList<String> listNot = new ArrayList<String>();

		ArrayList<String> listNot = preparation("z://series/");

		int code = 366383;
		int seasons = 2;
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
				System.setProperty("webdriver.chrome.driver", "ef://temp/chromedriver.exe");
				driver = new ChromeDriver();
			} catch (Exception ex) {
				// test = true;
				ex.printStackTrace();
			}
			// if (!test) {
			// break;
			// }
			// }

			driver.get("https://nzbgeek.info/logon.php");

			// Input Email id and Password If you are already Register
			driver.findElement(By.name("username")).sendKeys("Daydreamer057");
			driver.findElement(By.name("password")).sendKeys("ULbwkKMxNM0umW7AmcppNLEhXwFW0K0K");

			WebElement webElementTemp2 = driver.findElement(By.name("username"));
			webElementTemp2.submit();

			// webElementTemp2 = driver.findElement(By.className("warning_button"));
			// webElementTemp2.submit();

			List<String> listHref = new ArrayList<String>();

			driver.get("https://nzbgeek.info/geekseek.php?tvid=" + code);
			List<WebElement> linkSeason = driver.findElements(By.tagName("a"));

			ArrayList<Integer> listSeasons = new ArrayList<Integer>();
			for (WebElement webElement : linkSeason) {
				if (webElement.getAttribute("href")!=null&&webElement.getAttribute("href").contains("season=S")&&!webElement.getAttribute("href").contains("episode=")){
					listSeasons.add(Integer.valueOf(webElement.getAttribute("href").substring(webElement.getAttribute("href").indexOf("season=")+8,webElement.getAttribute("href").length())));
				}
			}

			for (Integer season : listSeasons) {
				System.out.println(season+" / "+listSeasons.size());

				if (season < 10) {
					driver.get(
							"https://nzbgeek.info/geekseek.php?tvid=" + code + "&season=S0" + season + "&episode=all");
				}
				if (season >= 10) {
					driver.get(
							"https://nzbgeek.info/geekseek.php?tvid=" + code + "&season=S" + season + "&episode=all");
				}

				List<WebElement> links = driver.findElements(By.tagName("a"));

				// System.out.println("links size " + links.size());
				int compteurEpisodes = 0;
				for (WebElement webElement : links) {
					// if (webElement.getAttribute("title").equalsIgnoreCase("Download NZB")) {
					if (webElement.getAttribute("href") != null
							&& webElement.getAttribute("href").contains("api?t=get")) {
						listHref.add(webElement.getAttribute("href"));
						compteurEpisodes++;
					}
				}
				System.out.println("compteur " + compteurEpisodes);
			}

			driver.close();

			driver = new ChromeDriver();
			driver.get("https://nzbgeek.info/logon.php");

			// Input Email id and Password If you are already Register
			driver.findElement(By.name("username")).sendKeys("Daydreamer057");
			driver.findElement(By.name("password")).sendKeys("ULbwkKMxNM0umW7AmcppNLEhXwFW0K0K");

			WebElement webElementTemp3 = driver.findElement(By.name("username"));
			webElementTemp3.submit();

//			webElementTemp3 = driver.findElement(By.className("warning_button"));
//			webElementTemp3.submit();

			int compteurFinal = 0;
			for (String lineTemp : listHref) {
				Pattern pattern = Pattern.compile("S[0-9]+E[0-9]+");
				Matcher matcher = pattern.matcher(lineTemp);
				String episode = "";
				try {
					if (matcher.find()) {
						if (!listNot.contains(matcher.group(0))){
							System.out.println(compteurFinal + " / " + listHref.size());
							driver.get(lineTemp);
							compteurFinal++;
						}
					}
				} catch (Exception e) {

				}
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
				Pattern pattern = Pattern.compile("S[0-9]+E[0-9]+");
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
		NZBGeekDownloadSeriesMissing nz = new NZBGeekDownloadSeriesMissing();
	}

}
