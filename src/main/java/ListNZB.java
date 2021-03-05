import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

public class ListNZB {
	FileWriter fw;
	PrintWriter pw;

	public ListNZB() {
		try {
			fw = new FileWriter("c://log/listNzb.txt");
			pw = new PrintWriter(fw);

			FileReader fr = new FileReader("c:/log/shows.html");
			BufferedReader br = new BufferedReader(fr);

			String line = "";

			while (line != null) {
				line = br.readLine();
				if (line != null) {
					if (line.contains("t=5040") && line.indexOf("<font size=\"3\">") != -1) {
						String href = line.substring(line.indexOf("<font size=\"3\">") + 15,
								line.indexOf("</font></a>"));
						pw.println(href);
					}
				}
			}

			br.close();
			fr.close();

			pw.flush();
			pw.close();
			fw.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public String returnDate(Date date) {
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(date);
		SimpleDateFormat fmt = new SimpleDateFormat("dd-MMM-yyyy");
		fmt.setCalendar(gc);
		return fmt.format(gc.getTime());
	}

	public static void main(String[] args) {
		ListNZB populate = new ListNZB();

	}

}
