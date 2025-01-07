package Files;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class MoveSitcoms {

	public MoveSitcoms() {
		int compteurE = 0;
		int compteurZ = 0;

		File baseE = new File("e://series");
		File[] fichiersE = baseE.listFiles();

		File baseZ = new File("z://series");

		File[] fichiersZ = baseZ.listFiles();

		for(File fichierTempE : fichiersE) {
			System.out.println("E " + compteurE + " / " + fichiersE.length);
			for(File fichierTempZ : fichiersZ) {
				if(!fichierTempZ.getName().toLowerCase().equals("h")&&fichierTempE.getName().toLowerCase().equals(fichierTempZ.getName().toLowerCase())) {
					try {
						FileUtils.moveDirectory(fichierTempZ, new File("e://sitcoms/"+fichierTempE.getName()));
					} catch(IOException ex){
						ex.printStackTrace();
					}
				}
			}
			compteurE++;
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MoveSitcoms du = new MoveSitcoms();
	}

}
