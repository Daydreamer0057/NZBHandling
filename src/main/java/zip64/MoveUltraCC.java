package zip64;

import java.io.File;

public class MoveUltraCC {
	static String path2;
	static int compteurDelete = 0;

	static double tailleGagnee = 0.0;
	public MoveUltraCC() throws Exception{
		while (true) {

			System.out.println("Debut");
			File base = new File("e://test/ultracc");
				File[] listBase = base.listFiles();

				if(listBase!=null) {
					for(File fichierTemp : listBase) {
						if(fichierTemp.isDirectory()) {

						}
					}
				}

			System.out.println("Done");
		}
	}

	public int moveDirectoryMovies(File fichier) {
		try {
			String sourceFolder = "e:///"+fichier.getName();
			String destFolder = "d://test/film/"+fichier.getName();

			int compteur = 0;
			if(new File(destFolder).exists()) {
				destFolder += "_"+compteur;
				compteur++;
			}

			while(new File(destFolder).exists()) {
				destFolder = destFolder.substring(0,destFolder.length()-2)+"_"+compteur;
				compteur++;
			}

			StringBuffer rbCmd = new StringBuffer();
			if ((sourceFolder != null) && (destFolder != null))
			{
				sourceFolder = sourceFolder.replaceAll("/", "\\\\");
				destFolder = destFolder.replaceAll("/", "\\\\");

				rbCmd.append("robocopy \"" + sourceFolder + "\"  \"" + destFolder + "\" /e /move /r:10 /w:1  /ETA /xd *\"unpack\"* /is /it /im /compress");

				Process p = Runtime.getRuntime().exec(rbCmd.toString());

				p.getErrorStream().close();
				p.getInputStream().close();
				p.getOutputStream().close();

				return p.waitFor();
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return 0;
	}


	public static void main(String[] args) {
		try {
			MoveUltraCC epguides = new MoveUltraCC();
		}catch(Exception ex) {
			ex.printStackTrace();
		}

	}
}
