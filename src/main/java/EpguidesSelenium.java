
import java.io.*;
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
		ArrayList<String> series = new ArrayList<>();
		ArrayList<String> seriesCompare = new ArrayList<>();
		ArrayList<String> listIndex = new ArrayList<String>();
		WebDriver driver = null;

//		FileInputStream fin = new FileInputStream("c:/temp/series check.txt");
//		ObjectInputStream ois = new ObjectInputStream(fin);
//		seriesCompare = (ArrayList<String>) ois.readObject();
//		ois.close();
//		fin.close();

		try {
			// System.out.println(fichierTemp.getPath());
			System.setProperty("webdriver.chrome.driver", "e://temp/chrome/chromedriver.exe");
			driver = new ChromeDriver();
		} catch (Exception ex) {
			// test = true;
			ex.printStackTrace();
		}
		driver.get("https://nzbplanet.net");

		// Input Email id and Password If you are already Register
		driver.findElement(By.name("username")).sendKeys("mbrebis092");
		driver.findElement(By.name("password")).sendKeys("9cEx4MUWaqRIL]_u");

		WebElement webElementTemp2 = driver.findElement(By.name("username"));
		webElementTemp2.submit();

		String[] adresse = {"0-9", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
		for (int i = 1; i < 27; i++) {
			driver.get("https://nzbplanet.net/series/" + adresse[i]);

			List<WebElement> linkSeason = driver.findElements(By.tagName("a"));

			for (WebElement web : linkSeason) {
				if (web.getAttribute("href").toLowerCase().contains("series")) {
					series.add(web.getAttribute("text"));
				}
			}
//			try {
//				FileOutputStream fout = new FileOutputStream(new File("c:/temp/series check.txt"));
//				ObjectOutputStream oos = new ObjectOutputStream(fout);
//				oos.writeObject(series);
//				oos.flush();
//				oos.close();
//				fout.flush();
//				fout.close();
//			} catch (Exception ex) {
//				ex.printStackTrace();
//			}

			ArrayList<String> list1 = new ArrayList<>();
			ArrayList<String> list2 = new ArrayList<>();
			list2 = seriesCompare;
			for (String fichier1 : series) {
				for (String fichier2 : series) {
					if (fichier1.toLowerCase().equals(fichier2.toLowerCase())) {
						try {
							list2.remove(fichier1);
						}catch(Exception ex){
							ex.printStackTrace();
						}
					}
				}
			}
			for(String line : list2){
				System.out.println(line);
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