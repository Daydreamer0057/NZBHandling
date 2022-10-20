import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NZBGeekDownloadMovies {

	public NZBGeekDownloadMovies() {
		ArrayList<String> listNot = new ArrayList<String>();

		//ArrayList<String> listNot = preparation("z://series/Trial & Error");

		int code = 260315;
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
				System.setProperty("webdriver.chrome.driver", "e://temp/chrome/chromedriver.exe");
				driver = new ChromeDriver();
			} catch (Exception ex) {
				// test = true;
				ex.printStackTrace();
			}
			driver.get("https://nzbgeek.info/logon.php");

			// Input Email id and Password If you are already Register
			driver.findElement(By.name("username")).sendKeys("Daydreamer057b");
			driver.findElement(By.name("password")).sendKeys("sOEHZJJr0fqPqXiIyuWdslNrimGQCMng");

			WebElement webElementTemp2 = driver.findElement(By.name("username"));
			webElementTemp2.submit();

			for(int i = 0;i<24;i++) {
				List<String> listHref = new ArrayList<String>();

				driver.get("https://nzbgeek.info/dashboard.php?mycart");
//			Thread.sleep(30000);
				List<WebElement> linkSeason = driver.findElements(By.tagName("a"));
				List<WebElement> listCheckBox = driver.findElements(By.cssSelector("input[type='checkbox']"));
				HashSet<String> duplicates = new HashSet<>();
//			List<WebElement> listCheckBoxFinal = new ArrayList<>();

				int compteurFinal = 0;
				int compteurTotal = 0;
				for (WebElement lineTemp : linkSeason) {
					String url = "";
					try {
						url = lineTemp.getAttribute("href");
						if(duplicates.contains(url)) {
							url = "";
						} else {
								duplicates.add(url);
							}
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
				WebElement fin = driver.findElement(By.id("mycart2"));
				fin.click();

				Thread.sleep(2000);

				WebElement confirmFin = driver.findElement(By.id("account_warning_button"));
				confirmFin.submit();

//				Thread.sleep(60000);

//				driver.close();
			}
		} catch (

				Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		NZBGeekDownloadMovies nz = new NZBGeekDownloadMovies();
	}

}
