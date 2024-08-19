package Utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class UnzipFiles {
    public static void main(String[] args) {
        File base = new File("C:/Users/bmonnet/Downloads/Subtitles");

        File[] fichiers = base.listFiles();

        HashSet<File> listeFichier = new HashSet<File>();
        ArrayList<File> listeDirectory = new ArrayList<File>();

        for (File fichier : fichiers) {
            if (fichier.isDirectory()) {
                listeDirectory.add(fichier);
            } else {
                if (!fichier.getName().endsWith(".srt")) {
                    listeFichier.add(fichier);
                }
            }
        }

        while (listeDirectory.size() > 0) {
            File fichier = listeDirectory.get(0);
            File[] fichierListe = fichier.listFiles();

            for (File fichierTemp : fichierListe) {
                if (fichierTemp.isDirectory()) {
                    listeDirectory.add(fichierTemp);
                } else {
                    if (!fichierTemp.getName().endsWith(".srt")) {
                        listeFichier.add(fichierTemp);
                    }
                }
            }
            listeDirectory.remove(0);
        }

        int compteur = 0;
        for (File fichier : listeFichier) {
            System.out.println(compteur + " / " + listeFichier.size());
            compteur++;
            if (fichier.getName().endsWith(".zip")) {
                Path p2 = Paths.get(fichier.getPath());
                Path folder2 = p2.getParent();
                String path2 = folder2.toString();

                String zipFilePath = path2+"/"+fichier.getName();

                String destDir =  path2;

                unzip(zipFilePath, destDir);
            }
        }
    }
    public static void unzip(String zipFilePath, String destDir) {
//        File dir = new File(destDir);
        // create output directory if it doesn't exist
        // if (!dir.exists())
        // dir.mkdirs();
        FileInputStream fis;
        // buffer for read and write data to file
        byte[] buffer = new byte[1024];
        try {
            fis = new FileInputStream(zipFilePath);
            ZipInputStream zis = new ZipInputStream(fis, Charset.forName("Cp437"));
            ZipEntry ze = zis.getNextEntry();
            while (ze != null) {
                String fileName = ze.getName();
                try {
                    File newFile = new File(destDir + File.separator + fileName);
                    int compteur = 0;
//                    while (newFile.exists()) {
//                        newFile = new File(destDir + File.separator + FilenameUtils.removeExtension(fileName) + "_" + compteur + fileName.substring(fileName.length()-4,fileName.length()));
//                        compteur++;
//                    }
//                    if (!newFile.exists()) {
                        System.out.println("Unzipping to " + newFile.getAbsolutePath());
                        // create directories for sub directories in zip
                        new File(newFile.getParent()).mkdirs();
                        FileOutputStream fos = new FileOutputStream(newFile);
                        int len;
                        while ((len = zis.read(buffer)) > 0) {
                            fos.write(buffer, 0, len);
                        }
                        fos.flush();
                        fos.close();
//                    }
                } catch(Exception ex){
                    ex.printStackTrace();
                }
                // close this ZipEntry
                zis.closeEntry();
                ze = zis.getNextEntry();
            }
            // close last ZipEntry
            zis.closeEntry();
            zis.close();
            fis.close();
            File fichier = new File(zipFilePath);
            fichier.delete();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
