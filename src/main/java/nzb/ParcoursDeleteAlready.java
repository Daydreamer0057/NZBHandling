package nzb;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParcoursDeleteAlready {
    public ParcoursDeleteAlready() {
        File base = new File("z://film/treated");

        File[] fichiers = base.listFiles();

        HashSet<File> listeFilmsATraiter = new HashSet<File>();
        HashSet<File> listeSeriesATraiter = new HashSet<File>();
        ArrayList<File> listeDirectory = new ArrayList<File>();
        ArrayList<File> listeDirectorySeries = new ArrayList<File>();

        listeDirectory.add(new File("z:/test/test"));
        listeDirectorySeries.add(new File("z:/series"));

        /**
         * Liste films
          */
        for (File fichier : fichiers) {
            System.out.println(listeDirectory.size());
            if (fichier.isDirectory()) {
                listeDirectory.add(fichier);
            } else {
                if (!fichier.getName().endsWith(".srt") && (fichier.getName().endsWith(".mp4") || fichier.getName().endsWith(".mkv") || fichier.getName().endsWith(".avi"))) {
                    Pattern p = Pattern.compile(".*([sS][0-9]+[xXeE][0-9]+).*");
                    Matcher m = p.matcher(fichier.getName().toLowerCase());

                    if (!m.matches()) {
                        listeFilmsATraiter.add(fichier);
                    }
                }
            }
        }

        while (listeDirectory.size() > 0) {
            System.out.println(listeDirectory.size());
            File fichier = listeDirectory.get(0);
            File[] fichierListe = fichier.listFiles();

            for (File fichierTemp : fichierListe) {
                if (fichierTemp.isDirectory()) {
                    listeDirectory.add(fichierTemp);
                } else {
                    if (!fichierTemp.getName().endsWith(".srt") && (fichierTemp.getName().endsWith(".mp4") || fichierTemp.getName().endsWith(".mkv") || fichierTemp.getName().endsWith(".avi"))) {
                        Pattern p = Pattern.compile(".*([sS][0-9]+[xXeE][0-9]+).*");
                        Matcher m = p.matcher(fichier.getName().toLowerCase());

                        if (!m.matches()) {
                            listeFilmsATraiter.add(fichierTemp);
                        }
                    }
                }
            }
            listeDirectory.remove(0);
        }

        /**
         * Liste Series
         */
        for (File fichier : fichiers) {
            System.out.println(listeDirectorySeries.size());
            if (fichier.isDirectory()) {
                listeDirectorySeries.add(fichier);
            } else {
                if (!fichier.getName().endsWith(".srt") && (fichier.getName().endsWith(".mp4") || fichier.getName().endsWith(".mkv") || fichier.getName().endsWith(".avi"))) {
                    Pattern p = Pattern.compile(".*([sS][0-9]+[xXeE][0-9]+).*");
                    Matcher m = p.matcher(fichier.getName().toLowerCase());

                    if (m.matches()) {
                        listeSeriesATraiter.add(fichier);
                    }
                }
            }
        }

        while (listeDirectorySeries.size() > 0) {
            System.out.println(listeDirectory.size());
            File fichier = listeDirectory.get(0);
            File[] fichierListe = fichier.listFiles();

            for (File fichierTemp : fichierListe) {
                if (fichierTemp.isDirectory()) {
                    listeDirectorySeries.add(fichierTemp);
                } else {
                    if (!fichierTemp.getName().endsWith(".srt") && (fichierTemp.getName().endsWith(".mp4") || fichierTemp.getName().endsWith(".mkv") || fichierTemp.getName().endsWith(".avi"))) {
                        Pattern p = Pattern.compile(".*([sS][0-9]+[xXeE][0-9]+).*");
                        Matcher m = p.matcher(fichier.getName().toLowerCase());

                        if (m.matches()) {
                            listeSeriesATraiter.add(fichierTemp);
                        }
                    }
                }
            }
            listeDirectory.remove(0);
        }

        ParcoursAlreadyDeleteBean beanFilm = new ParcoursAlreadyDeleteBean(listeFilmsATraiter);
        ParcoursAlreadyDeleteBean beanSeries = new ParcoursAlreadyDeleteBean(listeSeriesATraiter);



        try (FileOutputStream fileOut = new FileOutputStream("c:/temp/listeFilms.txt");
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(beanFilm);
            out.flush();
            out.close();
            System.out.println("Object Film has been serialized and saved");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (FileOutputStream fileOut = new FileOutputStream("c:/temp/listeSeries.txt");
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(beanSeries);
            out.flush();
            out.close();
            System.out.println("Object Series has been serialized and saved");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        ParcoursDeleteAlready p = new ParcoursDeleteAlready();
    }

}
