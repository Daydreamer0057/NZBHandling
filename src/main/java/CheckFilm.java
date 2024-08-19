import java.io.*;

public class CheckFilm {

	public CheckFilm() {
		try{
//			String compareString = "d:\\temp";
			String compareString = "d:\\test";
			String driveLetter = "z:";

			FileInputStream fis = new FileInputStream(new File("z://temp/mediainfo.txt"));
			InputStreamReader istreamReader = new InputStreamReader(fis,"ISO-8859-1");

			BufferedReader br = new BufferedReader(istreamReader);

			int compteur = 0;
			int compteur2 = 0;
			String line = "";
			while(line!=null) {
				compteur++;
				line = br.readLine();

				if (line != null && (line.toLowerCase().contains("mp4") || line.toLowerCase().contains("mkv") || line.toLowerCase().contains("avi")) && line.toLowerCase().contains("|")) {
					String lineParam = line.substring(0,line.toLowerCase().indexOf(compareString) - 1);
					if (!(lineParam.toLowerCase().contains("mbps") || lineParam.toLowerCase().contains("kbps"))){
						compteur2++;
						String fileName = line.substring(line.toLowerCase().indexOf(compareString) + 2, line.length());
						fileName = "z:" + fileName;
						File fichier = new File(fileName);
						if(fichier.exists()&&!fichier.getName().toLowerCase().endsWith("srt")) {
							System.out.println(fileName);
//							fichier.delete();
//							fichier.renameTo(new File("z://test/stockage/"+fichier.getName()));
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
		CheckFilm populate = new CheckFilm();

	}

}
