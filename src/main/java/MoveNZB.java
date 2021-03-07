import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.StringTokenizer;

public class MoveNZB {

	public MoveNZB() {
		try {

			File basePrincipal = new File("c://users/bmonnet/downloads");

			File[] listN = basePrincipal.listFiles();

			ArrayList<File> listNZB = new ArrayList<File>();

			for (File fichierNZB : listN) {
				if(fichierNZB.isFile()) {
					listNZB.add(fichierNZB);
				}
			}

			File baseSeries = new File("c://users/bmonnet/downloads");

			File[] listS = baseSeries.listFiles();

			ArrayList<File> listSeries = new ArrayList<File>();

			for(File fichierTemp : listS){
				if(fichierTemp.isDirectory()){
					listSeries.add(fichierTemp);
				}
			}

			int compteur = 0;
			for (File fichierSeries : listSeries) {
				ArrayList<String> words = new ArrayList<String>();
				if(fichierSeries.isDirectory()) {
					StringTokenizer stk = new StringTokenizer(fichierSeries.getName(), " ");
					while (stk.hasMoreTokens()) {
						words.add(stk.nextToken());
					}
				}

				basePrincipal = new File("c://users/bmonnet/downloads");
				listN = basePrincipal.listFiles();

				listNZB = new ArrayList<File>();

				for (File fichierNZB : listN) {
					if(fichierNZB.isFile()) {
						listNZB.add(fichierNZB);
					}
				}

				System.out.println(compteur+" / "+listSeries.size()+"    size "+listNZB.size()+"    "+fichierSeries.getName());
				compteur++;

				for (File fichierNZB : listNZB) {
					boolean test = false;
					if (fichierSeries.isDirectory() && fichierNZB.isFile() && !fichierNZB.getName().contains("3%") && !fichierNZB.getName().contains("dark") && !fichierNZB.getName().contains("see")) {
						for(String line : words){
							String nomNZB = fichierNZB.getName().toLowerCase();
							String lineLow = line.toLowerCase();
							if(!nomNZB.contains(lineLow)){
								test = true;
								break;
							}
						}
						if(!test){
							FileUtils.moveFileToDirectory(fichierNZB,new File("c://users/bmonnet/downloads/"+fichierSeries.getName()),true);
						}
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

		public static void main(String[] args) {
			MoveNZB populate = new MoveNZB();

		}

	}
