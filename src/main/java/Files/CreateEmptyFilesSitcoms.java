package Files;

import Utilities.FileDirParcours;

import java.io.File;
import java.io.PrintWriter;
import java.util.HashSet;

public class CreateEmptyFilesSitcoms {

	public CreateEmptyFilesSitcoms() {
		int compteurE = 0;

		HashSet<File> listFiles = FileDirParcours.getParcours("e://series", new String[]{".mp4", ".mkv", ".avi"});

		for(File fichierTemp : listFiles) {
			try {
				PrintWriter pw = new PrintWriter(new File("e://sitcoms/" + fichierTemp.getName()));
				pw.flush();
				pw.close();
			} catch (Exception ex){
				ex.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CreateEmptyFilesSitcoms du = new CreateEmptyFilesSitcoms();
	}

}
