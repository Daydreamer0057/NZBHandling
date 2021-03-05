import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import config.AppConfig;
import entity.NZBEntity;
import service.INZBService;


public class PopulateSpring {
	Properties appProps;
	String rootPath;
	NZBEntity nzbEntity = new NZBEntity();

	public PopulateSpring() {
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		 
        INZBService service = (INZBService) context.getBean("nzbservice");
        
		//NZBService nzbService = new NZBService();

		File base = new File("f:///nzb");

		File[] fichiers = base.listFiles();

		HashSet<File> listeFichier = new HashSet<File>();
		ArrayList<File> listeDirectory = new ArrayList<File>();

		for(File fichier : fichiers) {
			if(fichier.isDirectory()) {
				listeDirectory.add(fichier);
			} else {
				listeFichier.add(fichier);
			}
		}

		while(listeDirectory.size()>0) {
			File fichier = listeDirectory.get(0);

			File[] fichierListe = fichier.listFiles();

			for(File fichierTemp : fichierListe) {
				if(fichierTemp.isDirectory()) {
					listeDirectory.add(fichierTemp);
				}else { 
					listeFichier.add(fichierTemp);
				}
			}
			listeDirectory.remove(0);
		}

		int compteur = 0;

		for(File fichierTemp : listeFichier) {
			System.out.println("Fichier "+compteur+" / "+listeFichier.size());
			boolean nameTest = false;
			boolean titleTest = false;
			try {
				BufferedReader br = new BufferedReader(new FileReader(fichierTemp));

				String line = "";
				nzbEntity = new NZBEntity();

				while(line !=null) {
					line = br.readLine();

					if(line != null) {
						if(line.indexOf("Name:")!=-1) {
							line = br.readLine();
							line = br.readLine();
							line = br.readLine();
							line = line.trim();
							line = line.replace('\'', ' ');
							nzbEntity.setTitle(line);
							nameTest = true;
						}

						if (line.endsWith(".mkv")||line.endsWith(".mp4")||line.endsWith(".avi")||line.endsWith(".m4v")) {
							titleTest = true;
							line = line.trim();
							line = line.replace('\'', ' ');
							line = line.replace('\\', ' ');
							nzbEntity.setName(line);
							break;
						}
					}
				}
				br.close();
			} catch (Exception fex) {
			fex.printStackTrace();
		}
		compteur++;
		if(nameTest && titleTest) {
			try {
			    fichierTemp.delete();
			    
				long id = service.getIdMax();
				
				//List<NZBEntity> listeEntity = service.getNzbByNameAndTitle(nzbEntity.getTitle(), nzbEntity.getName());

				//int numberRow = listeEntity.size();
			    
			    nzbEntity.setId_nzb(id);
			    
			    service.updateNzb(nzbEntity);
				
			} catch (Exception ex) {
				ex.printStackTrace();
			} 
		}

		//if(nameTest && titleTest) nzbService.addNzb(nzbEntity);
	}
	try {
		context.close();
	} catch(Exception ex) {
		ex.printStackTrace();
	}
}

public static void main(String[] args) {
	PopulateSpring populate = new PopulateSpring();

}

}
