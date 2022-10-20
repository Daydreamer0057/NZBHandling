import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;

public class TrimQB {

	public TrimQB() {
		try {
			PrintWriter pw = new PrintWriter("e://temp/list_games.txt");

			FileReader fr = new FileReader("e://temp/games.txt");
			BufferedReader br = new BufferedReader(fr);

			String line = "";

			while (line != null) {
				line = br.readLine();
				if (line != null) {
					try {
						line = line.substring(line.indexOf("&dn=") + 4, line.length());
						line = line.substring(0, line.indexOf("&tr="));

						pw.println(line);
					} catch(StringIndexOutOfBoundsException ex){
						ex.printStackTrace();
					}
				}
			}

			br.close();
			fr.close();
			pw.flush();
			pw.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void main(String[] args) {
		TrimQB t = new TrimQB();
		}
	}
