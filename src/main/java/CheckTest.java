import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class CheckTest {

	public CheckTest() {
		try{
			FileInputStream fis = new FileInputStream(new File("e://"));
			InputStreamReader istreamReader = new InputStreamReader(fis,"ISO-8859-1");

			BufferedReader br = new BufferedReader(istreamReader);

			int compteur = 0;
			int compteur2 = 0;
			String line = "";
			while(line!=null) {
				compteur++;
				line = br.readLine();

				if (line != null) {
					String lineParam = line.substring(0,line.toLowerCase().indexOf("d:\\test") - 1);
					if (!(lineParam.toLowerCase().contains("mbps") || lineParam.toLowerCase().contains("kbps"))){
						compteur2++;
						String fileName = line.substring(line.toLowerCase().indexOf("d:\\test") + 2, line.length());
						fileName = "d:" + fileName;
						File fichier = new File(fileName);
						if(!fichier.exists()){
							System.out.println(fileName);
						} else {
							System.out.println(fileName);
						}
						if(!fichier.getName().toLowerCase().endsWith("srt")) {
							fichier.delete();
						}

					}
				}
			}

			System.out.println("Compteur final "+compteur2);
			br.close();
			istreamReader.close();
			fis.close();

		} catch (Exception ex){
			ex.printStackTrace();
		}
	}

	public static void main(String[] args) {
		CheckTest populate = new CheckTest();

	}

}
