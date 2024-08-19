import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class ParseListWords {
	static String path2;

	public ParseListWords() {
		String chemin = "e://temp/liste_films_torrent.txt";

		try {
			FileReader fr = new FileReader(chemin);
			BufferedReader br = new BufferedReader(fr);

			PrintWriter pw = new PrintWriter("e://temp/liste_films_torrent_words.txt");

			String line = "";

			while(line!=null){
				line = br.readLine();
				if(line!=null){
					int taille = 0;
					String word  = "";
					StringTokenizer stk = new StringTokenizer(line, " ");
					while(stk.hasMoreTokens()){
						String lineTemp = stk.nextToken();
						if(lineTemp.length()>taille){
							taille = lineTemp.length();
							word = lineTemp;
						}
					}
					pw.println(word);
				}
			}

			pw.flush();
			pw.close();
			br.close();
			fr.close();
		} catch (IOException ex){
			ex.printStackTrace();
		}



	}

	public static void main(String[] args) {
		ParseListWords epguides = new ParseListWords();

	}

}
