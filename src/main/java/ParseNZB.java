import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.FileUrlResource;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;

public class ParseNZB {
	static String path;

	public ParseNZB() throws Exception{
		path = "U://NZB//Comics/NZB";
		String path2 = "U://NZB/SABNZBD";
		File fichier  = new File(path);
		File[] fichiers = fichier.listFiles();

		int compteurIndex = 0;
		for(File fichierTemp : fichiers) {
			System.out.println(compteurIndex+" / "+fichiers.length);
			if (fichierTemp.isFile()) {
				path = FilenameUtils.removeExtension(fichierTemp.getPath());
				FileReader fr = new FileReader(path + ".nzb");
				BufferedReader br = new BufferedReader(fr);

				FileWriter fw = new FileWriter("d://temp/test.txt");
				PrintWriter pw = new PrintWriter(fw);

				String line = "";

				int compteur = 0;
				boolean testFin = false;
				while (line != null) {
					if (!testFin) {
						fw = new FileWriter(path2 + "/" + FilenameUtils.removeExtension(fichierTemp.getName()) + "_" + compteurIndex + ".nzb");
						pw = new PrintWriter(fw);
						pw.println("<?xml version='1.0' encoding=\"ISO-8859-1\" standalone=\"yes\"?>");
						pw.println("<!DOCTYPE nzb PUBLIC \"-//newzBin//DTD NZB 1.0//EN\" \"http://www.newzbin.com/DTD/nzb/nzb-1.0.dtd\">");
						pw.println("<nzb>");
						testFin = true;
						compteurIndex++;
					}
					line = br.readLine();
					if (line != null) {
						if (line.toLowerCase().contains("<file subject")) {
							pw.println(line);
							compteur++;
						}
						if (line.toLowerCase().contains("group")) {
							pw.println(line);
						}
						if (line.toLowerCase().contains("segment")) {
							pw.println(line);
						}
						if (line.toLowerCase().contains("</file>")) {
							if (testFin) {
								pw.println(line);
								compteur++;
								if (compteur == 500) {
									pw.println("</nzb>");
									testFin = false;
									compteur = 0;
									pw.flush();
									fw.flush();
									pw.close();
									fw.close();
								}
							}
						}
					}
				}

				br.close();
				fr.close();
			}
		}
	}

	public static void main(String[] args) {
		try {
			ParseNZB epguides = new ParseNZB();
		} catch(Exception ex){
			ex.printStackTrace();
		}

	}

}
