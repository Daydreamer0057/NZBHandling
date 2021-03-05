import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import config.AppConfig;
import entity.NZBEntity;
import service.INZBService;

public class VerifyNamesSpring {
	Properties appProps;
	String rootPath;
	Connection conn;

	public VerifyNamesSpring() {
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

		INZBService service = (INZBService) context.getBean(INZBService.class);

		File base = new File("z://temp/main");

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
			compteur++;
			if(fichierTemp.isFile()&&(fichierTemp.getName().endsWith(".mkv")||fichierTemp.getName().endsWith(".mp4")||fichierTemp.getName().endsWith(".avi"))) {
				try {
					boolean test = false;
					String nomFichier = "";
					String pathFichier = "";

					if(fichierTemp.getName().startsWith("(")) {
						nomFichier = fichierTemp.getName().substring(4, fichierTemp.getName().length());
						Path p2 = Paths.get(fichierTemp.getPath());
						Path folder2 = p2.getParent();

						String path2 = folder2.toString();
						String chemin2 = path2.replaceAll("\\\\", "/");

						pathFichier = nomFichier.substring(0,nomFichier.length()-4);
						nomFichier = chemin2+"/"+nomFichier;

						test = true;
					} else {
						nomFichier = fichierTemp.getPath();
						pathFichier = fichierTemp.getName().substring(0,fichierTemp.getName().length()-4);
					}

					List<NZBEntity> listeTemp = service.getNzbByName(pathFichier);

					String extension = nomFichier.substring(nomFichier.length()-4, nomFichier.length());

					//String fileName = fichierTemp.getPath();
					Path p = Paths.get(nomFichier);
					Path folder = p.getParent();

					String path = folder.toString();
					String chemin = path.replaceAll("\\\\", "/");

					fichierTemp.renameTo(new File(chemin+"/"+listeTemp.get(0).getTitle()+extension));
					//FileUtils.copyFile(fichierTemp, new File(chemin+"/Convert/"+result.getString("title")+extension));
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
	}

		public static void main(String[] args) {
			VerifyNamesSpring populate = new VerifyNamesSpring();

		}
	}

