import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;

public class CheckNZB {

	public CheckNZB() {
		try {

			File basePrincipal = new File("x://nzb/old");

			File[] listPrincipal = basePrincipal.listFiles();

			HashSet<File> listePrincipale = new HashSet<File>();
			ArrayList<File> listTri = new ArrayList<>();


			for (File fichier : listPrincipal) {
				listePrincipale.add(fichier);
			}

			File basePrincipalNew = new File("x://nzb/new");

			File[] listPrincipalNew = basePrincipalNew.listFiles();

			HashSet<File> listePrincipaleNew = new HashSet<File>();

			for (File fichier : listPrincipalNew) {
				listePrincipaleNew.add(fichier);
				if(!fichier.getPath().toLowerCase().contains("web")&&!fichier.getPath().toLowerCase().contains("blu")&&!fichier.getPath().toLowerCase().contains("dvd")) {
					listTri.add(fichier);
				}
			}

			ArrayList<File> listFiles = new ArrayList<>();
			int compteur = 0;
			for(File fichierNew : listePrincipaleNew) {
				String name = fichierNew.getName();
				if(fichierNew.getName().contains("(1)")||fichierNew.getName().contains("(2)")||fichierNew.getName().contains("(3)")||fichierNew.getName().contains("(4)")){
					name = fichierNew.getName().substring(0,fichierNew.getName().length()-7)+".nzb";
				}
				compteur++;
				System.out.println(compteur+" / "+listePrincipaleNew.size());
				for(File fichierOld : listePrincipale){
					if(name.equalsIgnoreCase(fichierOld.getName())) {
						listFiles.add(fichierNew);
					}
				}
			}

			listFiles.forEach(res -> {
				res.delete();
			});

			for(File fichierTemp : listTri){
				try{
					fichierTemp.delete();
				}catch (Exception ex){
					ex.printStackTrace();
				}
			}

		} catch(Exception ex) {
			ex.printStackTrace();
		}


	}

	public static void main(String[] args) {
		CheckNZB populate = new CheckNZB();

	}

}
