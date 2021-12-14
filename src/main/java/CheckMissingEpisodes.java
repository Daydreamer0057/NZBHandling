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

public class CheckMissingEpisodes {

	public CheckMissingEpisodes() {
		FileWriter fw = null;
		try {
			fw = new FileWriter("e://temp/downloaded/01.txt");

			PrintWriter pw = new PrintWriter(fw);

			File base = new File("z://series");
			File[] listBase = base.listFiles();

			for (File series : listBase) {
				ArrayList<String> listNot = preparation(series.getName());

				for (int season = 1; season < 10; season++) {
					System.out.println(season + " / " + season);
					for (int episode = 1; episode < 25; episode++) {
						if (season < 10) {
							if (episode < 10) {
								if (!listNot.contains("S0" + season + "E0" + episode)) {
									pw.println(series.getName() + " S0" + season + "E0" + episode);
									pw.flush();
									fw.flush();
								}
							} else {
								if (!listNot.contains("S0" + season + "E" + episode)) {
									pw.println(series.getName() + " S0" + season + "E" + episode);
									pw.flush();
									fw.flush();
								}
							}
						} else {
							if (episode < 10) {
								if (!listNot.contains("S" + season + "E0" + episode)) {
									pw.println(series.getName() + " S" + season + "E0" + episode);
									pw.flush();
									fw.flush();
								}
							} else {
								if (!listNot.contains("S" + season + "E" + episode)) {
									pw.println(series.getName() + " S" + season + "E" + episode);
									pw.flush();
									fw.flush();
								}
							}
						}
					}
				}
			}


			try {
				pw.close();
				fw.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	public ArrayList<String> preparation(String line) {
		ArrayList<String> list = new ArrayList<String>();

		if (line != "") {
			File fichier = new File("z://series/"+line);
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
				list.add(episode);
			}
		}
		return list;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
				CheckMissingEpisodes nz = new CheckMissingEpisodes();
		}
}
