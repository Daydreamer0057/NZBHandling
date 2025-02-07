package Utilities;

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
                for (int i = 0; i < fileType.length; i++) {
                    if (fichier.getName().toLowerCase().endsWith(fileType[i].toLowerCase()) || fileType[i].equals("*")) {
                            listeFichier.add(fichier);
                    }
                }
            }
        }

        while (listeDirectory.size() > 0) {
            File fichier = listeDirectory.get(0);

            if (fichier != null) {
                File[] fichierListe = fichier.listFiles();

                System.out.println("Dir Size " + listeDirectory.size() + "    nb fichiers " + listeFichier.size() + "    " + fichier.getPath() + "    Threads "+Thread.activeCount());

                if (fichierListe != null) {
                    for (File fichierTemp : fichierListe) {
                        if (fichierTemp.isDirectory()) {
                                listeDirectory.add(fichierTemp);
                        } else {
                            for (int i = 0; i < fileType.length; i++) {
                                if (fichierTemp.getName().toLowerCase().endsWith(fileType[i].toLowerCase()) || fileType[i].equals("*")) {
                                        listeFichier.add(fichierTemp);
                                }
                            }
                        }
                    }
                }
            }
            listeDirectory.remove(0);
        }

        return listeFichier;
    }

    public static ArrayList<String> getNameParcours(String chemin, String[] fileType) {
        File base = new File(chemin);

        File[] fichiers = base.listFiles();

        ArrayList<String> listeFichier = new ArrayList<>();
        ArrayList<File> listeDirectory = new ArrayList<File>();
        //listeDirectory.add(new File("d://series"));
        //listeDirectory.add(new File("d://temp 2/main"));

        for (File fichier : fichiers) {
            if (fichier.isDirectory()) {
                    listeDirectory.add(fichier);
            } else {
                for (int i = 0; i < fileType.length; i++) {
                    if (fichier.getName().toLowerCase().endsWith(fileType[i].toLowerCase()) || fileType[i].equals("*")) {
                            listeFichier.add(fichier.getName());
                    }
                }
            }
        }

        while (listeDirectory.size() > 0) {
            File fichier = listeDirectory.get(0);

            if (fichier != null) {
                File[] fichierListe = fichier.listFiles();

                System.out.println("Dir Size " + listeDirectory.size() + "    nb fichiers " + listeFichier.size() + "    " + fichier.getPath() + "    Threads "+Thread.activeCount());

                if (fichierListe != null) {
                    for (File fichierTemp : fichierListe) {
                        if (fichierTemp.isDirectory()) {
                            while (Thread.activeCount() > 50) {
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException ex) {
                                    ex.printStackTrace();
                                }
                            }
                            new Thread(() -> {
                                listeDirectory.add(fichierTemp);
                            }).start();
                        } else {
                            for (int i = 0; i < fileType.length; i++) {
                                if (fichierTemp.getName().toLowerCase().endsWith(fileType[i].toLowerCase()) || fileType[i].equals("*")) {
                                    while (Thread.activeCount() > 50) {
                                        try {
                                            Thread.sleep(1000);
                                        } catch (InterruptedException ex) {
                                            ex.printStackTrace();
                                        }
                                    }
                                    new Thread(() -> {
                                        listeFichier.add(fichierTemp.getName());
                                    }).start();
                                }
                            }
                        }
                    }
                }
            }
            listeDirectory.remove(0);
        }
        while(Thread.activeCount()>2){
            try {
//                System.out.println("Threads " + Thread.activeCount());
                Thread.sleep(1000);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        Thread thread = Thread.currentThread();
        thread.interrupt();
        return listeFichier;
    }
}