import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class SeriesCompare {

	public SeriesCompare() throws IOException {
		FileReader fr = new FileReader("f:///series/number.txt");
		BufferedReader br = new BufferedReader(fr);

		int number = Integer.parseInt(br.readLine());

		br.close();
		fr.close();

		FileReader frOld = new FileReader("f:///series/series_" + (number - 2) + ".txt");
		BufferedReader brOld = new BufferedReader(frOld);

		FileReader frNew = new FileReader("f:///series/series_" + (number - 1) + ".txt");
		BufferedReader brNew = new BufferedReader(frNew);

		ArrayList<String> liste = new ArrayList<String>();

		String line = "";

		while (line != null) {
			line = brOld.readLine();
			if (line != null)
				liste.add(line);
		}

		ArrayList<String> listeNew = new ArrayList<String>();
		PrintWriter pw = new PrintWriter("f:///series/differences.html");
		line = "";
		while (line != null) {
			line = brNew.readLine();
			if (!liste.contains(line) && !listeNew.contains(line)) {
				// pw.println("<a href='" + line + "'>" + line + "</a></br></br>");
				pw.println(line);
				listeNew.add(line);
			}
		}

		pw.flush();
		pw.close();
		brOld.close();
		frOld.close();
		brNew.close();
		frNew.close();

		PrintWriter pw2 = new PrintWriter("f:///series/number.txt");
		pw2.println("" + number);
		pw.flush();
		pw.close();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			SeriesCompare serie = new SeriesCompare();
		} catch (IOException ioex) {
			ioex.printStackTrace();
		}
	}

}
