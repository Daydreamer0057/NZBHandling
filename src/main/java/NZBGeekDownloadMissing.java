import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NZBGeekDownloadMissing {

	public NZBGeekDownloadMissing() {
		FileWriter fw = null;
		//FileReader fr = new FileReader("e://temp/downloaded/\"+index+\".txt");

		try {
			fw = new FileWriter("e://temp/downloaded/01.txt");

		} catch(Exception ex) {
			ex.printStackTrace();
		}

		PrintWriter pw = new PrintWriter(fw);

		ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.setProxy(null);

		File chemin = new File("z://series");

		File[] chemins = chemin.listFiles();

		ArrayList<File> listeChemin = new ArrayList<File>();

		for (File fichier : chemins) {
			if (!fichier.getName().contains("3%") && !fichier.getName().contains("Dark")&& !fichier.getName().contains("See")){
				listeChemin.add(fichier);
			}
		}

		int compteurSeries = 0;
		for (File fichierNzb : listeChemin) {
			System.out.println("================================================================================="+compteurSeries+" / "+listeChemin.size());
			compteurSeries++;

			String series = fichierNzb.getName();
			ArrayList<String> listNot = preparation("z://series/" + series);
			int s = 1;

			int compteurMax = 0;

			int change = 0;
			try {
				WebDriver driver = null;

				try {
					System.setProperty("webdriver.chrome.driver", "e://temp/chromedriver.exe");
					driver = new ChromeDriver(chromeOptions);
				} catch (Exception ex) {
					// test = true;
					ex.printStackTrace();
				}

				driver.get("https://nzbgeek.info/logon.php");

				// Input Email id and Password If you are already Register
				driver.findElement(By.name("username")).sendKeys("Victordavion3062c");
				driver.findElement(By.name("password")).sendKeys("lM23XAkz3ekpchUkspLfmorCXpGF6ymq");

				WebElement webElementTemp2 = driver.findElement(By.name("username"));
				webElementTemp2.submit();

				boolean testFinSeason = false;
				HashSet<String> listHref = new HashSet<String>();
				ArrayList<String> testLine = new ArrayList<>();
				int compteurFinSerie = 0;
				for (int season = 1; season < 20; season++) {
					boolean testSeason = false;
					System.out.println("Season "+season + " / 20");
					for (int episode = 1; episode < 100; episode++) {

						boolean test = false;
						// driver.get("https://nzbgeek.info/geekseek.php?c=5000&browseincludewords=will%20grace&search="
						// + i);
						if (!listNot.contains("S0" + season + "E0" + episode)) {
							driver.get("https://nzbgeek.info/geekseek.php?moviesgeekseek=1&c=&browseincludewords=" + series + "+s0" + season + "e0" + episode);
							test = true;
							testLine.add(series + " S0" + season + "E0" + episode);


							List<WebElement> links = driver.findElements(By.tagName("a"));

							// System.out.println("links size " + links.size());
							int compteurEpisodes = 0;
							for (WebElement webElement : links) {
								// if (webElement.getAttribute("title").equalsIgnoreCase("Download NZB")) {
								if (webElement.getAttribute("href") != null && webElement.getAttribute("href").contains("?tvid")) {
									listHref.add(webElement.getAttribute("href"));
									compteurEpisodes++;
								}
							}
//						System.out.println("season");
							if (compteurEpisodes > 0) {
								testSeason = true;
							} else {
								testLine.remove(series + " S0" + season + "E0" + episode);
							}
							System.out.println("compteur " + compteurEpisodes + "     epsiode " + episode + "    " + listeChemin.size());

							if (episode == 1) {
								driver.get("https://nzbgeek.info/geekseek.php?moviesgeekseek=1&c=&browseincludewords=" + series + "+s0" + season + "e0" + episode);
								links = driver.findElements(By.tagName("a"));
								compteurEpisodes = 0;
								for (WebElement webElement : links) {
									// if (webElement.getAttribute("title").equalsIgnoreCase("Download NZB")) {
									if (webElement.getAttribute("href") != null && webElement.getAttribute("href").contains("?tvid")) {
										listHref.add(webElement.getAttribute("href"));
										compteurEpisodes++;
									}
								}
								if (compteurEpisodes == 0) {
									testFinSeason = true;
									break;
								}
							}
							if (test && compteurEpisodes == 0) {
								break;
							}
						}
					}
					if(testFinSeason){
						break;
					}
					System.out.println("test");
				}
				for(String lineTemp : testLine){
					pw.println(lineTemp);
					pw.flush();
					fw.flush();
				}
				driver.close();
				// for (int i = 0; i < 12; i++) {

				// }


//				driver = new ChromeDriver();
//				driver.get("https://nzbgeek.info/logon.php");

				// Input Email id and Password If you are already Register
//				driver.findElement(By.name("username")).sendKeys("Victordavion3062a");
//				driver.findElement(By.name("password")).sendKeys("6ruwXPqSzVqGO7w42YJeS0OsTcvPIdtI");
//
//				WebElement webElementTemp3 = driver.findElement(By.name("username"));
//				webElementTemp3.submit();
//
//
//				int compteurFinal = 0;
//				for (String lineTemp : listHref) {
//					System.out.println(compteurFinal + " / " + listHref.size()+"    "+compteurSeries+" / "+listeChemin.size());
//					compteurSeries++;
//					driver.get(lineTemp);
//					compteurFinal++;
//				}
//				Thread.sleep(10000);
//				driver.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		try {
			pw.close();
			fw.close();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}

	public ArrayList<String> preparation(String line) {
		ArrayList<String> list = new ArrayList<String>();

		if (line != "") {
			File fichier = new File(line);
			File[] fichiers = fichier.listFiles();

			for (File fichierTemp : fichiers) {
				String episode = "";
				StringTokenizer stk = new StringTokenizer(fichierTemp.getName(),"-");
				if(stk.hasMoreTokens()) {
					episode = stk.nextToken();
				}
				if(stk.hasMoreTokens()) {
					episode = stk.nextToken().trim();
				}
				episode = episode.toUpperCase();
				list.add(episode);
			}
		}
		return list;
	}

	public static void main(String[] args) {
		NZBGeekDownloadMissing nz = new NZBGeekDownloadMissing();
	}

}
