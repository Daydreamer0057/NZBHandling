import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DeleteFilmBeforeConvertEnCours {
    static String path2;
    static String year;
    static String nameSeries;
    static int resolutionFilmNew = 0;
    static int compteurDelete = 0;

    static long taille = 0L;

    public DeleteFilmBeforeConvertEnCours() {
        ArrayList<String> listPriority = new ArrayList<>();
        listPriority.add("poiyhoiyh");

        // Dossier a supprimer
        File base = new File("z://test/film");
        //		File base = new File("Z://film/new/treated");
        //File base = new File("e://theatre/convert");
        // File base = new File("f://Graver/Theatre/Convert");

        File[] fichiers = base.listFiles();

        HashSet<File> listeFichierATraiter = new HashSet<File>();
        ArrayList<File> listeDirectory = new ArrayList<File>();

        for (File fichier : fichiers) {
            if (fichier.isDirectory() && !fichier.getPath().toLowerCase().contains("retry")) {
                listeDirectory.add(fichier);
            } else {
                if(!fichier.getName().endsWith(".srt")&&(fichier.getName().endsWith(".mp4")||fichier.getName().endsWith(".mkv")||fichier.getName().endsWith(".avi"))) {
                    Pattern p = Pattern.compile(".*([sS][0-9]+[xXeE][0-9]+).*");
                    Matcher m = p.matcher(fichier.getName().toLowerCase());

                    if (!m.matches()) {
                        listeFichierATraiter.add(fichier);
                    } else {
                        fichier.renameTo(new File("z://test/main temp/"+fichier.getName()));
                    }
                }
            }
        }

        while (listeDirectory.size() > 0) {
            File fichier = listeDirectory.get(0);
            File[] fichierListe = fichier.listFiles();

            for (File fichierTemp : fichierListe) {
                if (fichierTemp.isDirectory() && fichierTemp.getPath().toLowerCase().contains("retry")) {
                    listeDirectory.add(fichierTemp);
                } else {
                    if(!fichierTemp.getName().endsWith(".srt")&&(fichierTemp.getName().endsWith(".mp4")||fichierTemp.getName().endsWith(".mkv")||fichierTemp.getName().endsWith(".avi"))) {
                        Pattern p = Pattern.compile(".*([sS][0-9]+[xXeE][0-9]+).*");
                        Matcher m = p.matcher(fichier.getName().toLowerCase());

                        if (!m.matches()) {
                            listeFichierATraiter.add(fichierTemp);
                        } else {
                            fichierTemp.renameTo(new File("z://test/main temp/"+fichierTemp.getName()));
                        }
                    }
                }
            }
            listeDirectory.remove(0);
        }

        int compteur = 0;
        HashSet<File> fileBase = new HashSet<File>();
        HashSet<File> filesFilmNew = new HashSet<File>();
        //		fileBase.add(new File("e://convert"));
        fileBase.add(new File("z:/film/new"));
        fileBase.add(new File("z:/film/treated"));
        //		fileBase.add(new File("z:/temp/convert"));
        //		fileBase.add(new File("z://test/temp"));

        ArrayList<File> listeDirectory2 = new ArrayList<File>();

        for (File filesTemp : fileBase) {
            System.out.println(fileBase.size());
            if (filesTemp.isDirectory()) {
                listeDirectory2.add(filesTemp);
            } else {
                if (filesTemp.getName().toLowerCase().endsWith("mkv") || filesTemp.getName().toLowerCase().endsWith("mp4")
                        || filesTemp.getName().toLowerCase().endsWith("avi")
                        || filesTemp.getName().toLowerCase().endsWith("m4v")) {
                    filesFilmNew.add(filesTemp);
                }
            }
        }

        while (listeDirectory2.size() > 0) {
            File fichier2 = listeDirectory2.get(0);
            File[] fichierListe2 = fichier2.listFiles();
            System.out.println(filesFilmNew.size()+"    "+listeDirectory2.size());

            for (File fichierTemp : fichierListe2) {

                if (fichierTemp.isDirectory()) {
                    listeDirectory2.add(fichierTemp);
                } else {
                    if (fichierTemp.getName().toLowerCase().endsWith("mkv") || fichierTemp.getName().toLowerCase().endsWith("mp4")
                            || fichierTemp.getName().toLowerCase().endsWith("avi")
                            || fichierTemp.getName().toLowerCase().endsWith("m4v")) {
                        filesFilmNew.add(fichierTemp);
                    }
                }
            }
            listeDirectory2.remove(0);
        }

        System.out.println("Debut files");
        for (File fichierFilmNew : filesFilmNew) {
            boolean testFiles = false;
            String nomFichier = "";
            final String path = "";
            if ((fichierFilmNew.getName().indexOf("_") > fichierFilmNew.getName().length() - 6) && (
                    fichierFilmNew.getName().indexOf("_0") != -1 || fichierFilmNew.getName().indexOf("_1") != -1
                            || fichierFilmNew.getName().indexOf("_2") != -1 || fichierFilmNew.getName().indexOf("_3") != -1
                            || fichierFilmNew.getName().indexOf("_4") != -1 || fichierFilmNew.getName().indexOf("_5") != -1
                            || fichierFilmNew.getName().indexOf("_6") != -1 || fichierFilmNew.getName().indexOf("_7") != -1
                            || fichierFilmNew.getName().indexOf("_8") != -1 || fichierFilmNew.getName().indexOf("_9") != -1)) {
                try {
                    path2 = fichierFilmNew.getName().substring(0, fichierFilmNew.getName().length() - 6);
                } catch (Exception ex){
                    ex.printStackTrace();
                }
            } else {
                // System.out.println("Compteur " + compteur + " / " + files.length);
                compteur++;
                path2 = fichierFilmNew.getName();
                //path2 = path.replaceAll("_", "'");
                path2 = FilenameUtils.removeExtension(path2);
            }
            if (path2 != "") {
                StringTokenizer stk = new StringTokenizer(path2, "(");
                if (stk.hasMoreTokens()) {
                    try {
                        nameSeries = stk.nextToken().trim();
                    } catch (Exception ex) {
                        testFiles = true;
                    }
                }
                if (stk.hasMoreTokens()) {
                    try {
                        String line = stk.nextToken();
                        year = line.substring(0, 4).trim();
                    } catch (Exception ex) {
                        testFiles = true;
                    }
                }
            }

            if (!testFiles) {

                if (fichierFilmNew.getName().toLowerCase().contains("2160p")) {
                    resolutionFilmNew = 2160;
                }
                if (fichierFilmNew.getName().toLowerCase().contains("1080p")) {
                    resolutionFilmNew = 1080;
                }
                if (fichierFilmNew.getName().toLowerCase().contains("720p")) {
                    resolutionFilmNew = 720;
                }
                if (fichierFilmNew.getName().toLowerCase().contains("576p")) {
                    resolutionFilmNew = 576;
                }
                if (fichierFilmNew.getName().toLowerCase().contains("480p")) {
                    resolutionFilmNew = 480;
                }


                listeFichierATraiter.forEach(fichierTestTest -> {
                    try {
                        String nameRes = fichierTestTest.getName();
                        nameRes = nameRes.replace('.',' ');
                        //						System.out.println(nameRes);
                        if (!nameRes.equalsIgnoreCase(".classpath")) {
//							nameRes = FilenameUtils.removeExtension(nameRes);
                            //						System.out.println(path2+"    "+nameRes);
                            //							StringTokenizer stk = new StringTokenizer(nameRes, "(");

                            String nameFinal = "";
                            String yearFinal = "";

                            try {
                                int compteurSplit = 0;
                                String[] nameFinalSplit = nameRes.split(" ");
                                boolean nameTest = false;
                                for(String lineTemp : nameFinalSplit){
                                    if(lineTemp.charAt(0)>='0'&&lineTemp.charAt(0)<='9'){
                                        int compteurSplit2 = 1;
                                        for(String lineTempSplit : nameFinalSplit){
                                            if(compteurSplit2<=compteurSplit){
                                                nameFinal += lineTempSplit + " ";
                                            } else {
                                                nameTest = true;
                                                break;
                                            }
                                            compteurSplit2++;
                                        }
                                        if(nameTest){
                                            break;
                                        }
                                    }
                                    compteurSplit++;
                                }
                                nameFinal = nameFinal.trim();

                                compteurSplit = 0;
                                String[] yearFinalSplit = nameRes.split(" ");
                                nameTest = false;
                                for(String lineTemp : yearFinalSplit){
                                    if(lineTemp.charAt(lineTemp.length()-1)>='0'&&lineTemp.charAt(lineTemp.length()-1)<='9'){
                                        yearFinal = lineTemp.trim();
                                        break;
                                    }
                                    compteurSplit++;
                                }
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }

                            int resolutionTestTest = 0;
                            if (nameRes.toLowerCase().contains("2160p")) {
                                resolutionTestTest = 2160;
                            }
                            if (nameRes.toLowerCase().contains("1080p")) {
                                resolutionTestTest = 1080;
                            }
                            if (nameRes.toLowerCase().contains("720p")) {
                                resolutionTestTest = 720;
                            }
                            if (nameRes.toLowerCase().contains("576p")) {
                                resolutionTestTest = 576;
                            }
                            if (nameRes.toLowerCase().contains("480p")) {
                                resolutionTestTest = 480;
                            }


                            //						System.out.println("nameFinal "+nameFinal+"    nameSeries "+nameSeries+"    yearFinal "+yearFinal+"    year "+year+"    delete "+compteurDelete++);
                            //						if (nameFinal.equalsIgnoreCase(nameSeries)&&(year.equalsIgnoreCase(yearFinal))){
                            //							System.out.println("nameFinal "+nameFinal+"    nameSeries "+nameSeries+"    yearFinal "+yearFinal+"    year "+year+"    resolutionTestTest "+resolutionTestTest+"    sizeSeries "+resolutionSeries);
                            //						}
                            //							if (nameFinal.equalsIgnoreCase(nameSeries) && (year.equalsIgnoreCase(yearFinal)) && (resolutionSeries <= resolutionTestTest)) {
                            if (nameFinal.equalsIgnoreCase(nameSeries) && (year.equalsIgnoreCase(yearFinal))) {
                                //System.out.println(nameFinal + "    " + nameSeries + "    delete " + compteurDelete+++"    nameres "+resolutionSeries+"    final "+resolutionTestTest);
                                if ((resolutionTestTest > resolutionFilmNew)) {
                                    System.out.println("Fichier Film New   " + fichierFilmNew.getPath() + "    " + fichierTestTest.getName());
                                    try {
                                        taille += fichierFilmNew.length();
//                                        fichierFilmNew.delete();
                                    } catch (Exception ex) {

                                    }
                                }
                                if ((resolutionTestTest == resolutionFilmNew)) {
                                    if (fichierFilmNew.getPath().toLowerCase().contains("treated")) {
                                        System.out.println("Fichier Test   " + fichierTestTest.getPath() + "    " + fichierFilmNew.getName());
                                        try {
                                            taille += fichierTestTest.length();
//											fichierTestTest.delete();
                                        } catch (Exception ex) {

                                        }
                                    } else {
                                        System.out.println("Fichier New  " + fichierTestTest.getPath() + "    " + fichierFilmNew.getName());
                                        try {
                                            taille += fichierFilmNew.length();
//                                            fichierFilmNew.delete();
                                        } catch (Exception ex) {

                                        }
                                    }
                                    if ((resolutionTestTest < resolutionFilmNew)) {
                                        System.out.println("Fichier Test    " + fichierTestTest.getPath() + "    " + fichierFilmNew.getName());
                                        try {
                                            taille += fichierTestTest.length();
//											fichierTestTest.delete();
                                        } catch (Exception ex) {

                                        }
                                    }
                                }
                            }
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        System.exit(0);
                    }
                });
            }
        }
        System.out.println("Taille gagnee "+(taille/1000000000L));
    }

    public static void main(String[] args) {
        DeleteFilmBeforeConvertEnCours epguides = new DeleteFilmBeforeConvertEnCours();

    }

}
