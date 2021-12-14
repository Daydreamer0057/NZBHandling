import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;

public class CheckNZB {

	public CheckNZB() {
		try {

			File basePrincipal = new File("w://nzb/old");

			File[] listPrincipal = basePrincipal.listFiles();

			HashSet<File> listePrincipale = new HashSet<File>();


			for (File fichier : listPrincipal) {
				listePrincipale.add(fichier);
			}

			File basePrincipalNew = new File("w://nzb/new");

			File[] listPrincipalNew = basePrincipalNew.listFiles();

			HashSet<File> listePrincipaleNew = new HashSet<File>();

			for (File fichier : listPrincipalNew) {
				listePrincipaleNew.add(fichier);
			}

			ArrayList<File> listFiles = new ArrayList<>();
			int compteur = 0;
			for(File fichierNew : listePrincipaleNew) {
				compteur++;
				System.out.println(compteur+" / "+listePrincipaleNew.size());
				for(File fichierOld : listePrincipale){
					if(fichierNew.getName().equalsIgnoreCase(fichierOld.getName())) {
						listFiles.add(fichierNew);
					}
				}
			}

			listFiles.forEach(res -> {
				res.delete();
			});

		} catch(Exception ex) {
			ex.printStackTrace();
		}


	}

	public static void main(String[] args) {
		CheckNZB populate = new CheckNZB();

	}

}
