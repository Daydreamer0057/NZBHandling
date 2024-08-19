import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;

public class FileDirParcours {
    public static HashSet<File> getParcours(String chemin, String[] fileType) {
        File base = new File(chemin);

        File[] fichiers = base.listFiles();

        HashSet<File> listeFichier = new HashSet<File>();
        ArrayList<File> listeDirectory = new ArrayList<File>();
        //listeDirectory.add(new File("d://series"));
        //listeDirectory.add(new File("d://temp 2/main"));

        for (File fichier : fichiers) {
            if (fichier.isDirectory()) {
                listeDirectory.add(fichier);
            } else {
                for(int i = 0;i<fileType.length;i++) {
                    if (fichier.getName().toLowerCase().endsWith(fileType[i].toLowerCase())) {
                        listeFichier.add(fichier);
                    }
                }
            }
        }

        while (listeDirectory.size() > 0) {
            File fichier = listeDirectory.get(0);
            System.out.println("Dir Size "+listeDirectory.size()+"    "+fichier.getPath());

            File[] fichierListe = fichier.listFiles();

            if(fichierListe!=null) {
                for (File fichierTemp : fichierListe) {
                    if (fichierTemp.isDirectory()) {
                        listeDirectory.add(fichierTemp);
                    } else {
                        for (int i = 0; i < fileType.length; i++) {
                            if (fichierTemp.getName().toLowerCase().endsWith(fileType[i].toLowerCase())) {
                                listeFichier.add(fichierTemp);
                            }
                        }
                    }
                }
            }
            listeDirectory.remove(0);
        }
        return listeFichier;
    }
}
