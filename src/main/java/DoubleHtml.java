import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;

public class DoubleHtml {
	public DoubleHtml() {
		try {
			FileReader fr = new FileReader("f:///series/differences.html");
			BufferedReader br = new BufferedReader(fr);

			String line = "";

			ArrayList<String> listeBr = new ArrayList<String>();
			ArrayList<String> listeNew = new ArrayList<String>();

			while (line != null) {
				line = br.readLine();
				listeBr.add(line);
			}

			br.close();
			fr.close();

			for (String element : listeBr) {

				// If this element is not present in newList
				// then add it
				if (!listeNew.contains(element)) {

					listeNew.add(element);
				}
			}

			PrintWriter pw = new PrintWriter("f:///series/final.html");
			pw.println("<html><body>");
			for (String lineTemp : listeNew) {
				pw.println(lineTemp);
			}
			pw.println("</body></html>");
			pw.flush();
			pw.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void main(String[] args) {
		DoubleHtml populate = new DoubleHtml();

	}

}
