import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.ArrayList;
import java.util.List;

public class NZBGeekDownloadMoviesJSoup {

	public NZBGeekDownloadMoviesJSoup() {
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
			driver.findElement(By.name("username")).sendKeys("Daydreamer");
			driver.findElement(By.name("password")).sendKeys("OwNWyITMG0XLzcSA64RCFKTYTvS563LC");

			WebElement webElementTemp2 = driver.findElement(By.name("username"));
			webElementTemp2.submit();

			for(int i = 0;i<24;i++) {
				List<String> listHref = new ArrayList<String>();

				driver.get("https://nzbgeek.info/dashboard.php?mycart");
//			Thread.sleep(30000);
				List<WebElement> linkSeason = driver.findElements(By.tagName("a"));
				List<WebElement> listCheckBox = driver.findElements(By.cssSelector("input[type='checkbox']"));
//			List<WebElement> listCheckBoxFinal = new ArrayList<>();

				Thread t = new Thread(new NZBGeekDownloadMoviesThread(driver, linkSeason,listCheckBox));
				t.start();

				WebElement fin = driver.findElement(By.id("mycart2"));
				fin.click();

				Thread.sleep(2000);

				WebElement confirmFin = driver.findElement(By.id("account_warning_button"));
				confirmFin.submit();

				Thread.sleep(60000);

//				driver.close();
			}
		} catch (

				Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		NZBGeekDownloadMoviesJSoup nz = new NZBGeekDownloadMoviesJSoup();
	}

}
