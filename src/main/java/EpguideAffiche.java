import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class EpguideAffiche {

	public EpguideAffiche() {
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
			// if (!test) {
			// break;
			// }
			// }

//			driver.get("http://epguides.com/" + seriesSeasons);
//			driver.get("http://epguides.com/" + alt);

//			String body = driver.getPageSource();
//
//			Pattern pattern = Pattern.compile("Season[ ]*[0-9]+");
//			Matcher matcher = pattern.matcher(body);
//			int compteurSeason = 0;
//			try {
//				while (matcher.find()) {
//					Pattern pattern2 = Pattern.compile("[0-9]+");
//					Matcher matcher2 = pattern2.matcher(matcher.group());
//					if (matcher2.find()) {
//						if (Integer.parseInt(matcher2.group()) > compteurSeason) {
//							compteurSeason = Integer.parseInt(matcher2.group());
//						}
//					}
//				}
//			} catch (Exception e) {
//
//			}
//			
//			s = compteurSeason;

			driver.get("https://nzbgeek.info/logon.php");

			// Input Email id and Password If you are already Register
			driver.findElement(By.name("username")).sendKeys("Daydreamer057a");
			driver.findElement(By.name("password")).sendKeys("N0dQMkLgH3KP3yxae6CZj3CnkBQLlNfp");

			WebElement webElementTemp2 = driver.findElement(By.name("username"));
			webElementTemp2.submit();

			/*webElementTemp2 = driver.findElement(By.className("warning_button"));*/
			/*webElementTemp2.submit();*/

			List<String> listHref = new ArrayList<String>();

			for (int season = 1; season < 15; season++) {
				System.out.println(season + " / " + season);
				for (int episode = 1; episode < 100; episode++) {
					boolean test = false;
					// driver.get("https://nzbgeek.info/geekseek.php?c=5000&browseincludewords=will%20grace&search="
					// + i);
					if (season < 10) {
						if (episode < 10) {
							if (!listNot.contains("S0" + season + "E0" + episode)) {
								driver.get(
										"https://nzbgeek.info/geekseek.php?moviesgeekseek=1&c=5000&browseincludewords="
												+ series + "+s0" + season + "e0" + episode);
								test = true;
							}
						} else {
							if (!listNot.contains("S0" + season + "E" + episode)) {
								driver.get(
										"https://nzbgeek.info/geekseek.php?moviesgeekseek=1&c=5000&browseincludewords="
												+ series + "+s0" + season + "e" + episode);
								test = true;
							}
						}
					} else {
						if (episode < 10) {
							if (!listNot.contains("S" + season + "E0" + episode)) {
								driver.get(
										"https://nzbgeek.info/geekseek.php?moviesgeekseek=1&c=5000&browseincludewords="
												+ series + "+s" + season + "e0" + episode);
								test = true;
							}
						} else {
							if (!listNot.contains("S" + season + "E" + episode)) {
								driver.get(
										"https://nzbgeek.info/geekseek.php?moviesgeekseek=1&c=5000&browseincludewords="
												+ series + "+s" + season + "e" + episode);
								test = true;
							}
						}
					}

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
					System.out.println("compteur " + compteurEpisodes);
					if (test&&compteurEpisodes == 0) {
						break;
					}
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
		EpguideAffiche nz = new EpguideAffiche();
	}

}
