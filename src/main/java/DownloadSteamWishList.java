import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DownloadSteamWishList {

	public DownloadSteamWishList() {
		 ArrayList<String> listNot = new ArrayList<String>();

		int compteurMax = 0;
		int pages = 20;
		//String[] hero = {"alien","cable","captain america","champions","children of the atom","daredevil","eternals","fantastic four","hellions","iron man","the marvels","marauders","star wars","sword","thor","venom","wolverine","x-men","x-factor","x-force","jupiter legacy"};
		String[] hero = {"deathlok","deathstroke","dragon ball","dragonball","elseworlds","gunnm","highlander","invicible","mass effect","october faction","predator","stargate","terminator","the boys","the division","the dresden files","the old guard","the walking déad","the witcher","tpb","warcraft","what if","manowar"};

		int change = 0;
		try {
			WebDriver driver = null;

			try {
				System.setProperty("webdriver.chrome.driver", "e://temp/chrome/chromedriver.exe");
				driver = new ChromeDriver();
			} catch (Exception ex) {
				ex.printStackTrace();
			}

			driver.get("https://store.steampowered.com/wishlist/profiles/76561198028546793/#sort=name&type=Game&ex_vr=1");

			System.exit(0);
			// Input Email id and Password If you are already Register
			driver.findElement(By.name("username")).sendKeys("Daydreamer057a");
			driver.findElement(By.name("password")).sendKeys("N0dQMkLgH3KP3yxae6CZj3CnkBQLlNfp");

			WebElement webElementTemp2 = driver.findElement(By.name("username"));
			webElementTemp2.submit();

			Set<String> listHref = new HashSet<>();

			HashMap<String,Set> mapComics = new HashMap<>();

			for(String heroComics : hero) {
				listHref = new HashSet<>();
				for (int season = 1; season <= 100; season++) {
					System.out.println(season + " / " + pages);
					driver.get("https://nzbgeek.info/geekseek.php?c=7030&browseincludewords=" + heroComics + "&search=" + season);


					List<WebElement> links = driver.findElements(By.tagName("a"));

					// System.out.println("links size " + links.size());
					int compteurEpisodes = 0;
					for (WebElement webElement : links) {
						if (webElement.getAttribute("href") != null && webElement.getAttribute("href").contains("api?t=get")) {
							listHref.add(webElement.getAttribute("href"));
							compteurEpisodes++;
						}
					}
					if (compteurEpisodes == 0) {
						break;
					}
				}
				mapComics.put(heroComics,listHref);
			}

			driver.close();

			for(String heroComics : mapComics.keySet()) {
				Set listHrefComics = mapComics.get(heroComics);

				driver = new ChromeDriver();
				driver.get("https://nzbgeek.info/logon.php");

				// Input Email id and Password If you are already Register
				driver.findElement(By.name("username")).sendKeys("Daydreamer057a");
				driver.findElement(By.name("password")).sendKeys("N0dQMkLgH3KP3yxae6CZj3CnkBQLlNfp");

				WebElement webElementTemp3 = driver.findElement(By.name("username"));
				webElementTemp3.submit();


				int compteurFinal = 0;
				for (Object lineTemp : listHrefComics) {
					System.out.println(compteurFinal + " / " + listHrefComics.size());
					driver.get((String)lineTemp);
					compteurFinal++;
				}
				driver.close();

				File downloads = new File("c://users/bmonnet/downloads");
				File[] listDownload = downloads.listFiles();

				File newDownload = new File("e://graver/"+heroComics);

				for(File fichier : listDownload){
					FileUtils.moveFileToDirectory(fichier,newDownload,true);
				}
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
		DownloadSteamWishList nz = new DownloadSteamWishList();
	}

}
