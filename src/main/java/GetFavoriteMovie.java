import org.apache.commons.io.FileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.HashSet;

public class GetFavoriteMovie {

	public GetFavoriteMovie() {
		try {
			FileReader fr = new FileReader(new File("e://temp/list_films_apart.txt"));
			BufferedReader br = new BufferedReader(fr);

			HashSet<String> listFavorites = new HashSet<>();

			String line = "";
			while(line!=null){
				line = br.readLine();
				if(line!=null) {
					listFavorites.add(line);
				}
			}

			br.close();
			fr.close();

			PrintWriter pw = new PrintWriter(new File("e://temp/list_films_apart_2.txt"));

			HashSet<File> listeFichier = FileDirParcours.getParcours("z://film/new", new String[]{".mp4",".avi",".mkv"});
			HashSet<File> listeFichier2 = FileDirParcours.getParcours("z://film/treated", new String[]{".mp4",".avi",".mkv"});
			listeFichier.addAll(listeFichier2);

			int compteur = 0;
			for (String lineTemp : listFavorites) {
				boolean test = false;
				for(File fichierFilm : listeFichier){
//						if(lineTemp.toLowerCase().contains("colonia ")&&fichierFilm.getName().toLowerCase().contains("colonia ")){
//						System.out.println("test");
//					}
					if(fichierFilm.getName().toLowerCase().contains(lineTemp.toLowerCase())){
						System.out.println("z://film/viewed/"+fichierFilm.getName());
						FileUtils.copyFile(fichierFilm,new File("z://film/viewed/"+fichierFilm.getName()));
						test = true;
						break;
					}
				}
				if(!test){
					pw.println(lineTemp);
				}
			}
			pw.flush();
			pw.println();
		} catch (Exception ex){
			ex.printStackTrace();
		}
	}

	public static void main(String[] args) {
		GetFavoriteMovie populate = new GetFavoriteMovie();

	}

}
