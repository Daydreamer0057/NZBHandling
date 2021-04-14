import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.Buffer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TrimWishlist {

	public TrimWishlist() throws Exception{
		FileWriter fw = new FileWriter("c://log/wishlist.txt");
		PrintWriter pw = new PrintWriter(fw);

		FileReader fr = new FileReader("c://log/log_wishlist.txt");
		BufferedReader br = new BufferedReader(fr);

		String line = "";
		String source = "";
		while(line!=null){
			line = br.readLine();
			if(line!=null) {
				source += line;
			}

		}

		br.close();
		fr.close();

		Pattern pattern = Pattern.compile("\"name\":\"[a-zA-Z0-9 ]+\"");
		Matcher matcher = pattern.matcher(source);
		String episode = "";
		try {
			while (matcher.find()) {
				String found = matcher.group();
				found = found.substring(8,found.length()-1);
				pw.println(found);
				pw.flush();
				fw.flush();
			}
		} catch (Exception e) {

		}

		pw.flush();
		fw.flush();
		pw.close();
		fw.close();



	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			TrimWishlist test = new TrimWishlist();
		} catch(Exception ex){
			ex.printStackTrace();
		}
	}

}
