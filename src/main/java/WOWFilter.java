import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class WOWFilter {
	public WOWFilter() {
		try {
			FileReader fr = new FileReader("c://temp/remove.txt");
			BufferedReader br = new BufferedReader(fr);

			FileReader fr2 = new FileReader("c://temp/liste.txt");
			BufferedReader br2 = new BufferedReader(fr2);

			String line = "";

			ArrayList<String> listeRemove = new ArrayList<String>();
			ArrayList<String> listeListe = new ArrayList<String>();

			line = br.readLine();

			StringTokenizer stk = new StringTokenizer(line, ",");
			while (stk.hasMoreTokens()) {
				listeRemove.add(stk.nextToken());
			}

			br.close();
			fr.close();

			line = br2.readLine();

			StringTokenizer stk2 = new StringTokenizer(line, ",");
			while (stk2.hasMoreTokens()) {
				listeListe.add(stk2.nextToken());
			}

			br2.close();
			fr2.close();

			for (String lineTemp : listeRemove) {
				if (listeListe.contains(lineTemp)) {
					listeListe.remove(lineTemp);
				}
			}

			PrintWriter pw = new PrintWriter("c://temp/resultat.txt");
			for (String lineTemp : listeListe) {
				pw.print(lineTemp + ",");
			}
			pw.flush();
			pw.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void main(String[] args) {
		WOWFilter populate = new WOWFilter();

	}

}
