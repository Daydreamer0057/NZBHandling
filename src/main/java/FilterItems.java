import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FilterItems {

	public FilterItems() {
		try {
			FileReader fr;

			fr = new FileReader("f://liste items.txt");

			BufferedReader br = new BufferedReader(fr);

			String total ="";
			String line = "";

			while(line!=null) {
				line = br.readLine();
				if(line!=null) {
					total+=line;
				}
			}

			//Pattern pattern = Pattern.compile("i:[0-9]*");
			Pattern pattern = Pattern.compile("p:[0-9]*:[0-9]*:[0-9]*:");
			Matcher matcher = pattern.matcher(total);
			while(matcher.find()) {
			    // affichage de la sous-chaîne capturée,
			    // de l'index de début dans la chaîne originale
			    // et de l'index de fin
			    System.out.println(matcher.group()+",");
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FilterItems f = new FilterItems();
	}

}
