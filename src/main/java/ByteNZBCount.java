import javax.annotation.processing.Filer;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;

public class ByteNZBCount {

	public ByteNZBCount() {
		try {

			File basePrincipal = new File("u://nzb/film en attente/temp");

			File[] listPrincipal = basePrincipal.listFiles();

			double tailleTotale = 0;
			for (File fichier : listPrincipal) {
				FileReader fr = new FileReader(fichier);
				BufferedReader br = new BufferedReader(fr);

				String line ="";
				long taille = 0;

				while(line!=null){
					line = br.readLine();
					if(line!=null&&line.contains("bytes=")){
						taille += Long.parseLong(line.substring(line.indexOf("bytes=")+7,line.indexOf("number")-2));
					}
				}

				br.close();
				fr.close();

				double tailleDouble = ((double)taille)/1024/1024/1024;
				tailleTotale += tailleDouble;
				System.out.println(""+tailleDouble);
			}
			System.out.println(""+tailleTotale);

		} catch(Exception ex) {
			ex.printStackTrace();
		}


	}

	public static void main(String[] args) {
		ByteNZBCount populate = new ByteNZBCount();

	}

}
