package Files;

import java.io.File;
import java.io.PrintWriter;

public class GetEmptyGames {

	public GetEmptyGames() {
		try {
			PrintWriter pw = new PrintWriter("c:/temp/liste_games_empty.txt");

			File base = new File("z://test/ultracc/games install");
			File[] listLevel1 = base.listFiles();

			for(File fichierTemp : listLevel1){
				if(!fichierTemp.getPath().toLowerCase().contains("tools")&&!fichierTemp.getPath().toLowerCase().contains("music")){
					File base2 = fichierTemp;
					File[] listLevel2 = base2.listFiles();

					for(File fichierLvl2 : listLevel2){
						File base3 = fichierLvl2;
						File[] listLevel3 = base3.listFiles();
						if(listLevel3==null||listLevel3.length==0){
							pw.println(fichierLvl2.getName());
							pw.flush();
						}
					}
				}
			}
			pw.flush();
			pw.close();
		} catch (Exception ex){
			ex.printStackTrace();
		}
	}

	public static void main(String[] args) {
		GetEmptyGames populate = new GetEmptyGames();

	}

}
