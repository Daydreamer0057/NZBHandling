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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NZBGeekDownloadMissing {

	public NZBGeekDownloadMissing(int index) {
		int start = 0 + (260/10*index);
		int end = (260/10)+(260/10*index);

		FileWriter fw = null;
		//FileReader fr = new FileReader("e://temp/downloaded/\"+index+\".txt");

		try {
			fw = new FileWriter("e://temp/downloaded/"+index+".txt");

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

		for (int p = start; p < end;p++) {
			System.out.println("================================================================================="+p+" / "+listeChemin.size());

			String series = listeChemin.get(p).getName();
			//		String alt = "KominskyMethod";
			//		String seriesSeasons = series;
			//		seriesSeasons = seriesSeasons.replace(" ","");
					ArrayList<String> listNot = preparation("z://series/" + series);
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
					driver = new ChromeDriver(chromeOptions);
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
				driver.findElement(By.name("username")).sendKeys("Daydreamer057");
				driver.findElement(By.name("password")).sendKeys("ULbwkKMxNM0umW7AmcppNLEhXwFW0K0K");

				WebElement webElementTemp2 = driver.findElement(By.name("username"));
				webElementTemp2.submit();

				/*webElementTemp2 = driver.findElement(By.className("warning_button"));*/
				/*webElementTemp2.submit();*/

				HashSet<String> listHref = new HashSet<String>();

				for (int season = 1; season < 20; season++) {
					boolean testSeason = false;
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

						pw.println("["+series+"]    S"+season+"E"+episode);
						pw.flush();
						fw.flush();

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
						if(compteurEpisodes>0){
							testSeason = true;
						}
						System.out.println("compteur " + compteurEpisodes+"    "+p+" / "+listeChemin.size());
						if (test && compteurEpisodes == 0) {
							break;
						}
					}
					if(!testSeason){
						break;
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
					System.out.println(compteurFinal + " / " + listHref.size()+"    "+p+" / "+listeChemin.size());
					driver.get(lineTemp);
					compteurFinal++;
				}
				Thread.sleep(10000);
				driver.close();
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
				Pattern pattern = Pattern.compile("S[0-9]+[0-9]+");
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
		for(int y=0;y<11;y++) {
			final int n = y;
			Thread t = new Thread(() -> {
				NZBGeekDownloadMissing nz = new NZBGeekDownloadMissing(n);
			});
			t.start();
		}

	}

}
