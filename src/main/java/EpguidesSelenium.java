
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class EpguidesSelenium {
	Properties appProps;
	String rootPath;

	public EpguidesSelenium() throws MalformedURLException, SQLException, ClassNotFoundException, IOException {
		ArrayList<String> listIndex = new ArrayList<String>();

		// ChromeOptions options = new ChromeOptions();
		// options.setBinary("C://Program Files/Google/Chrome/Application/chrome.exe");
		// System.setProperty("webdriver.chrome.driver", "f://temp/chromedriver.exe");
		System.setProperty("webdriver.chrome.driver",
				"c://programdata/chocolatey/lib/chromedriver/tools/chromedriver.exe");
		List<String> listURL = new ArrayList<String>();
		WebDriver driver = new ChromeDriver();
		// WebDriver driver = new RemoteWebDriver(new URL("http://10.0.0.100:9515"), new
		// ChromeOptions);

//		driver.get("http://Epguides.com/menu/comedy.shtml");
		driver.get("http://epguides.com/menu/current.shtml");
		List<WebElement> listElements = driver.findElements(By.tagName("a"));
		List<String> listURLs = new ArrayList<String>();

		for (WebElement webE : listElements) {
			listURLs.add(webE.getAttribute("href"));
		}

		int compteur = 0;
		for (String webElement : listURLs) {
			System.out.println(compteur+" / " + listURLs.size());

			if (webElement != null) {
				driver.get(webElement);

				String body = driver.getPageSource();
				StringReader str = new StringReader(body);
				BufferedReader br = new BufferedReader(str);

				String line = "";
				boolean testYear = false;
				boolean testSeason = false;
				boolean testNetflix = true;
				while (line != null) {
					line = br.readLine();
					if (line != null) {
						if ((line.contains("2000") || line.contains("2001") || line.contains("2002") || line.contains("2003") || line.contains("2004") || line.contains("2005") || line.contains("2006") || line.contains("2007") || line.contains("2008") || line.contains("2009") || line.contains("2010") || line.contains("2011") || line.contains("2012") || line.contains("2013") || line.contains("2014") || line.contains("2015") || line.contains("2016") || line.contains("2017") || line.contains("2018") || line.contains("2019") || line.contains("2020")) && !line.contains("<em>") && !line.contains("Text copyright")) {
							if(line.contains("Start date") || line.contains("End date")) {
							testYear = true;
							}
						}
//						if(line.contains("Season 2") || line.contains("season 2")) {
							testSeason = true;
//						}
						if(line.contains("Netflix")||line.contains("Amazon")||line.contains("30 min")) {
							testNetflix = false;
						}
					}
				}
				if(testSeason && testYear&&testNetflix) {
					FileWriter fw = new FileWriter("d://epguides/epguides" + compteur + ".html");
					PrintWriter pw = new PrintWriter(fw);
					pw.println(body);
					pw.flush();
					fw.flush();
					pw.close();
					fw.close();
				}
				compteur++;
				br.close();
				str.close();
			}
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			EpguidesSelenium ep = new EpguidesSelenium();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}