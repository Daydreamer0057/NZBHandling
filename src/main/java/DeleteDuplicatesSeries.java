import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DeleteDuplicatesSeries {
    FileWriter fw;
    PrintWriter pw;

    public DeleteDuplicatesSeries() {
        long ms = System.currentTimeMillis();
        System.out.println("debut " + ms);
        try {
            File base = new File("z://test/main a traiter");

            File[] fichiers = base.listFiles();

            HashSet<File> listeFichier = new HashSet<File>();
            ArrayList<File> listeDirectory = new ArrayList<File>();

            int compteur = 0;
            for (File fichier : fichiers) {
                System.out.println(listeFichier.size()+"    "+listeDirectory.size());
                compteur++;
                if (fichier.isDirectory()) {
                    listeDirectory.add(fichier);
                } else if (fichier.isFile()) {
                    if ((fichier.getName().endsWith("mkv") || fichier.getName().endsWith("mp4")
                            || fichier.getName().endsWith("avi"))) {
                        listeFichier.add(fichier);
                    }
                }
            }

            int compteurFile = 0;
            int compteurFolder = 0;
            while (listeDirectory.size() > 0) {
                System.out.println(listeFichier.size()+"    "+listeDirectory.size());
                File fichier = listeDirectory.get(0);

                File[] fichierListe = fichier.listFiles();

                if (fichierListe != null) {
                    for (File fichierTemp : fichierListe) {
                        if (fichierTemp.isDirectory()) {
                            listeDirectory.add(fichierTemp);
                            compteurFolder++;
                        } else if (fichierTemp.isFile()) {
                            if ((fichierTemp.getName().endsWith("mkv") || fichierTemp.getName().endsWith("mp4")
                                    || fichierTemp.getName().endsWith("avi"))) {
                                listeFichier.add(fichierTemp);
                                compteurFile++;
                            }
                        }
                    }
                }
                listeDirectory.remove(0);
            }

            HashMap<String, ArrayList> map240 = new HashMap<String, ArrayList>();
            HashMap<String, ArrayList> map360 = new HashMap<String, ArrayList>();
            HashMap<String, ArrayList> map480 = new HashMap<String, ArrayList>();
            HashMap<String, ArrayList> map576 = new HashMap<String, ArrayList>();
            HashMap<String, ArrayList> map720 = new HashMap<String, ArrayList>();
            HashMap<String, ArrayList> map1080 = new HashMap<String, ArrayList>();
            HashMap<String, ArrayList> map2160 = new HashMap<String, ArrayList>();

            for (File fichierTemp : listeFichier) {
                String episode = "";
                StringTokenizer stk = new StringTokenizer(fichierTemp.getName(),"-");
                if(stk.hasMoreTokens()) {
                    episode = stk.nextToken();
                }
                if(stk.hasMoreTokens()) {
                    episode += " - " + stk.nextToken();
                }

//                Pattern pattern = Pattern.compile("[a-zA-z0-9$()& ]+ - S[0-9]*E[0-9]*");
//                Matcher matcher = pattern.matcher(fichierTemp.getName());
//                String episode = "";
//                try {
//                    while (matcher.find()) {
//                        episode = matcher.group(0);
//                    }
//                } catch (Exception e) {
//
//                }
                if (!episode.equals("") && episode != null) {
                    if (fichierTemp.getName().toLowerCase().contains("720p")) {
                        ArrayList<File> listFile = map720.get(episode);
                        if (listFile == null) {
                            listFile = new ArrayList<File>();
                            listFile.add(fichierTemp);
                            map720.put(episode, listFile);
                        } else {
                            listFile.add(fichierTemp);
                            map720.put(episode, listFile);
                        }
                    }

                    if (fichierTemp.getName().toLowerCase().contains("1080p")) {
                        ArrayList<File> listFile = map1080.get(episode);
                        if (listFile == null) {
                            listFile = new ArrayList<File>();
                            listFile.add(fichierTemp);
                            map1080.put(episode, listFile);
                        } else {
                            listFile.add(fichierTemp);
                            map1080.put(episode, listFile);
                        }
                    }

                    if (fichierTemp.getName().toLowerCase().contains("2160p")) {
                        ArrayList<File> listFile = map2160.get(episode);
                        if (listFile == null) {
                            listFile = new ArrayList<File>();
                            listFile.add(fichierTemp);
                            map2160.put(episode, listFile);
                        } else {
                            listFile.add(fichierTemp);
                            map2160.put(episode, listFile);
                        }
                    }

                    if (fichierTemp.getName().toLowerCase().contains("576p")) {
                        ArrayList<File> listFile = map576.get(episode);
                        if (listFile == null) {
                            listFile = new ArrayList<File>();
                            listFile.add(fichierTemp);
                            map576.put(episode, listFile);
                        } else {
                            listFile.add(fichierTemp);
                            map576.put(episode, listFile);
                        }
                    }

                    if (fichierTemp.getName().toLowerCase().contains("480p")) {
                        ArrayList<File> listFile = map480.get(episode);
                        if (listFile == null) {
                            listFile = new ArrayList<File>();
                            listFile.add(fichierTemp);
                            map480.put(episode, listFile);
                        } else {
                            listFile.add(fichierTemp);
                            map480.put(episode, listFile);
                        }
                    }

                    if (fichierTemp.getName().toLowerCase().contains("360p")) {
                        ArrayList<File> listFile = map360.get(episode);
                        if (listFile == null) {
                            listFile = new ArrayList<File>();
                            listFile.add(fichierTemp);
                            map360.put(episode, listFile);
                        } else {
                            listFile.add(fichierTemp);
                            map360.put(episode, listFile);
                        }
                    }

                    if (fichierTemp.getName().toLowerCase().contains("240p")) {
                        ArrayList<File> listFile = map240.get(episode);
                        if (listFile == null) {
                            listFile = new ArrayList<File>();
                            listFile.add(fichierTemp);
                            map240.put(episode, listFile);
                        } else {
                            listFile.add(fichierTemp);
                            map240.put(episode, listFile);
                        }
                    }
                }
            }

            compteur = 0;

            // ====================================2160
            if (map2160.size() > 0) {
                Set<String> set = map2160.keySet();
                for (String lineTemp : set) {
                    ArrayList<File> listFile2 = map2160.get(lineTemp);
                    if (listFile2.size() > 0) {
                        File fichier = listFile2.get(0);
                        double taille = 0;
                        double tailleTemp = 0;
                        boolean testHdr = false;
                        for (File fichierTemp : listFile2) {
                            StringTokenizer stk = new StringTokenizer(fichierTemp.getName(),"-");
                            String rate = "";
                            while(!(rate.contains("Mbps")||rate.contains("kbps"))&&stk.hasMoreTokens()){
                                rate = stk.nextToken();
                            }
                            if(rate.contains("Mbps")){
                                rate = rate.substring(0, rate.indexOf("Mbps") - 1);
                                rate = rate.replaceAll(",", "").trim();
                                if (Double.parseDouble(rate) * 1000 >= taille) {
                                    fichier = fichierTemp;
                                    taille = Double.parseDouble(rate)*1000;
                                }
                            }
                            if(rate.contains("kbps")){
                                rate = rate.substring(0, rate.indexOf("kbps") - 1);
                                rate = rate.replaceAll(",", "").trim();
                                if (Double.parseDouble(rate) >= taille) {
                                    fichier = fichierTemp;
                                    taille = Double.parseDouble(rate);
                                }
                            }
                        }

                        if(!testHdr){
                            for (File fichierTemp : listFile2) {
                                StringTokenizer stk = new StringTokenizer(fichierTemp.getName(),"-");
                                String rate = "";
                                while(!(rate.contains("Mbps")||rate.contains("kbps"))&&stk.hasMoreTokens()){
                                    rate = stk.nextToken();
                                }
                                if(rate.contains("Mbps")){
                                    rate = rate.substring(0, rate.indexOf("Mbps") - 1);
                                    rate = rate.replaceAll(",", "").trim();
                                    if (Double.parseDouble(rate) * 1000 >= taille) {
                                        fichier = fichierTemp;
                                        taille = Double.parseDouble(rate)*1000;
                                    }
                                }
                                if(rate.contains("kbps")){
                                    rate = rate.substring(0, rate.indexOf("kbps") - 1);
                                    rate = rate.replaceAll(",", "").trim();
                                    if (Double.parseDouble(rate) >= taille) {
                                        fichier = fichierTemp;
                                        taille = Double.parseDouble(rate);
                                    }
                                }
                            }
                        }

                        for (File fichierTemp : listFile2) {
                            if (fichierTemp != fichier) {
                                System.out.println(fichierTemp.getPath() + "    " + fichierTemp.length());
                                fichierTemp.delete();
                                compteur++;
                            }
                        }
                        if (map1080.containsKey(lineTemp)) {
                            ArrayList<File> listDelete = map1080.get(lineTemp);
                            map1080.remove(lineTemp);
                            for (File fichierTemp : listDelete) {
                                try {
                                    System.out.println(fichierTemp.getPath() + " " + fichierTemp.length());
                                    fichierTemp.delete();
                                } catch (Exception ex) {

                                }
                            }
                        }

                        if (map720.containsKey(lineTemp)) {
                            ArrayList<File> listDelete = map720.get(lineTemp);
                            map720.remove(lineTemp);
                            for (File fichierTemp : listDelete) {
                                try {
                                    System.out.println(fichierTemp.getPath() + "    " + fichierTemp.length());
                                    fichierTemp.delete();
                                    compteur++;
                                } catch (Exception ex) {

                                }
                            }
                        }

                        if (map576.containsKey(lineTemp)) {
                            ArrayList<File> listDelete = map576.get(lineTemp);
                            map576.remove(lineTemp);
                            for (File fichierTemp : listDelete) {
                                try {
                                    System.out.println(fichierTemp.getPath() + "    " + fichierTemp.length());
                                    fichierTemp.delete();
                                    compteur++;
                                } catch (Exception ex) {

                                }
                            }
                        }

                        if (map480.containsKey(lineTemp)) {
                            ArrayList<File> listDelete = map480.get(lineTemp);
                            map480.remove(lineTemp);
                            for (File fichierTemp : listDelete) {
                                try {
                                    System.out.println(fichierTemp.getPath() + "    " + fichierTemp.length());
                                    fichierTemp.delete();
                                    compteur++;
                                } catch (Exception ex) {

                                }
                            }
                        }

                        if (map360.containsKey(lineTemp)) {
                            ArrayList<File> listDelete = map360.get(lineTemp);
                            map360.remove(lineTemp);
                            for (File fichierTemp : listDelete) {
                                try {
                                    System.out.println(fichierTemp.getPath() + "    " + fichierTemp.length());
                                    fichierTemp.delete();
                                    compteur++;
                                } catch (Exception ex) {

                                }
                            }
                        }

                        if (map240.containsKey(lineTemp)) {
                            ArrayList<File> listDelete = map240.get(lineTemp);
                            map240.remove(lineTemp);
                            for (File fichierTemp : listDelete) {
                                try {
                                    System.out.println(fichierTemp.getPath() + "    " + fichierTemp.length());
                                    fichierTemp.delete();
                                    compteur++;
                                } catch (Exception ex) {

                                }
                            }
                        }

                    }
                }
            }

            // ====================================1080
            if (map1080.size() > 0) {
                Set<String> set = map1080.keySet();
                for (String lineTemp : set) {
                    ArrayList<File> listFile2 = map1080.get(lineTemp);
                    if (listFile2.size() > 0) {
                        File fichier = listFile2.get(0);
                        double taille = 0;
                        double tailleTemp = 0;
                        for (File fichierTemp : listFile2) {
                            StringTokenizer stk = new StringTokenizer(fichierTemp.getName(), "-");
                            String rate = "";
                            while (!rate.contains("Mbps") && stk.hasMoreTokens()) {
                                rate = stk.nextToken();
                            }
                            if(rate.contains("Mbps")){
                                rate = rate.substring(0, rate.indexOf("Mbps") - 1);
                                rate = rate.replaceAll(",", "").trim();
                                if (Double.parseDouble(rate) * 1000 >= taille) {
                                    fichier = fichierTemp;
                                    taille = Double.parseDouble(rate)*1000;
                                }
                            }
                            if(rate.contains("kbps")){
                                rate = rate.substring(0, rate.indexOf("kbps") - 1);
                                rate = rate.replaceAll(",", "").trim();
                                if (Double.parseDouble(rate) >= taille) {
                                    fichier = fichierTemp;
                                    taille = Double.parseDouble(rate);
                                }
                            }
                        }

                        for (File fichierTemp : listFile2) {
                            if (fichierTemp != fichier) {
                                System.out.println(fichierTemp.getPath() + "    " + fichierTemp.length());
                                fichierTemp.delete();
                                compteur++;
                            }
                        }
                        if (map2160.containsKey(lineTemp)) {
                            ArrayList<File> listDelete = map2160.get(lineTemp);
                            map2160.remove(lineTemp);
                            for (File fichierTemp : listDelete) {
                                try {
                                    System.out.println(fichierTemp.getPath() + "    " + fichierTemp.length());
                                    fichierTemp.delete();
                                    compteur++;
                                } catch (Exception ex) {

                                }
                            }
                        }

                        if (map720.containsKey(lineTemp)) {
                            ArrayList<File> listDelete = map720.get(lineTemp);
                            map720.remove(lineTemp);
                            for (File fichierTemp : listDelete) {
                                try {
                                    System.out.println(fichierTemp.getPath() + "    " + fichierTemp.length());
                                    fichierTemp.delete();
                                    compteur++;
                                } catch (Exception ex) {

                                }
                            }
                        }

                        if (map576.containsKey(lineTemp)) {
                            ArrayList<File> listDelete = map576.get(lineTemp);
                            map576.remove(lineTemp);
                            for (File fichierTemp : listDelete) {
                                try {
                                    System.out.println(fichierTemp.getPath() + "    " + fichierTemp.length());
                                    fichierTemp.delete();
                                    compteur++;
                                } catch (Exception ex) {

                                }
                            }
                        }

                        if (map480.containsKey(lineTemp)) {
                            ArrayList<File> listDelete = map480.get(lineTemp);
                            map480.remove(lineTemp);
                            for (File fichierTemp : listDelete) {
                                try {
                                    System.out.println(fichierTemp.getPath() + "    " + fichierTemp.length());
                                    fichierTemp.delete();
                                    compteur++;
                                } catch (Exception ex) {

                                }
                            }
                        }

                        if (map360.containsKey(lineTemp)) {
                            ArrayList<File> listDelete = map360.get(lineTemp);
                            map360.remove(lineTemp);
                            for (File fichierTemp : listDelete) {
                                try {
                                    System.out.println(fichierTemp.getPath() + "    " + fichierTemp.length());
                                    fichierTemp.delete();
                                    compteur++;
                                } catch (Exception ex) {

                                }
                            }
                        }

                        if (map240.containsKey(lineTemp)) {
                            ArrayList<File> listDelete = map240.get(lineTemp);
                            map240.remove(lineTemp);
                            for (File fichierTemp : listDelete) {
                                try {
                                    System.out.println(fichierTemp.getPath() + "    " + fichierTemp.length());
                                    fichierTemp.delete();
                                    compteur++;
                                } catch (Exception ex) {

                                }
                            }
                        }
                    }
                }
            }

            // ====================================720
            if (map720.size() > 0) {
                Set<String> set = map720.keySet();
                for (String lineTemp : set) {
                    ArrayList<File> listFile2 = map720.get(lineTemp);
                    if (listFile2.size() > 0) {
                        File fichier = listFile2.get(0);
                        double taille = 0;
                        double tailleTemp = 0;
                        for (File fichierTemp : listFile2) {
                            StringTokenizer stk = new StringTokenizer(fichierTemp.getName(), "-");
                            String rate = "";
                            while (!rate.contains("Mbps") && stk.hasMoreTokens()) {
                                rate = stk.nextToken();
                            }
                            if(rate.contains("Mbps")){
                                rate = rate.substring(0, rate.indexOf("Mbps") - 1);
                                rate = rate.replaceAll(",", "").trim();
                                if (Double.parseDouble(rate) * 1000 >= taille) {
                                    fichier = fichierTemp;
                                    taille = Double.parseDouble(rate)*1000;
                                }
                            }
                            if(rate.contains("kbps")){
                                rate = rate.substring(0, rate.indexOf("kbps") - 1);
                                rate = rate.replaceAll(",", "").trim();
                                if (Double.parseDouble(rate) >= taille) {
                                    fichier = fichierTemp;
                                    taille = Double.parseDouble(rate);
                                }
                            }
                        }

                        for (File fichierTemp : listFile2) {
                            if (fichierTemp != fichier) {
                                System.out.println(fichierTemp.getPath() + "    " + fichierTemp.length());
                                fichierTemp.delete();
                                compteur++;
                            }
                        }

                        if (map576.containsKey(lineTemp)) {
                            ArrayList<File> listDelete = map576.get(lineTemp);
                            map576.remove(lineTemp);
                            for (File fichierTemp : listDelete) {
                                try {
                                    System.out.println(fichierTemp.getPath() + "    " + fichierTemp.length());
                                    fichierTemp.delete();
                                    compteur++;
                                } catch (Exception ex) {

                                }
                            }
                        }

                        if (map480.containsKey(lineTemp)) {
                            ArrayList<File> listDelete = map480.get(lineTemp);
                            map480.remove(lineTemp);
                            for (File fichierTemp : listDelete) {
                                try {
                                    System.out.println(fichierTemp.getPath() + "    " + fichierTemp.length());
                                    fichierTemp.delete();
                                    compteur++;
                                } catch (Exception ex) {

                                }
                            }
                        }

                        if (map360.containsKey(lineTemp)) {
                            ArrayList<File> listDelete = map360.get(lineTemp);
                            map360.remove(lineTemp);
                            for (File fichierTemp : listDelete) {
                                try {
                                    System.out.println(fichierTemp.getPath() + "    " + fichierTemp.length());
                                    fichierTemp.delete();
                                    compteur++;
                                } catch (Exception ex) {

                                }
                            }
                        }

                        if (map240.containsKey(lineTemp)) {
                            ArrayList<File> listDelete = map240.get(lineTemp);
                            map240.remove(lineTemp);
                            for (File fichierTemp : listDelete) {
                                try {
                                    System.out.println(fichierTemp.getPath() + "    " + fichierTemp.length());
                                    fichierTemp.delete();
                                    compteur++;
                                } catch (Exception ex) {

                                }
                            }
                        }
                    }
                }
            }

            // ====================================576
            if (map576.size() > 0) {
                Set<String> set = map576.keySet();
                for (String lineTemp : set) {
                    ArrayList<File> listFile2 = map576.get(lineTemp);
                    if (listFile2.size() > 0) {
                        File fichier = listFile2.get(0);
                        double taille = 0;
                        double tailleTemp = 0;
                        for (File fichierTemp : listFile2) {
                            StringTokenizer stk = new StringTokenizer(fichierTemp.getName(), "-");
                            String rate = "";
                            while (!rate.contains("Mbps") && stk.hasMoreTokens()) {
                                rate = stk.nextToken();
                            }
                            if(rate.contains("Mbps")){
                                rate = rate.substring(0, rate.indexOf("Mbps") - 1);
                                rate = rate.replaceAll(",", "").trim();
                                if (Double.parseDouble(rate) * 1000 >= taille) {
                                    fichier = fichierTemp;
                                    taille = Double.parseDouble(rate)*1000;
                                }
                            }
                            if(rate.contains("kbps")){
                                rate = rate.substring(0, rate.indexOf("kbps") - 1);
                                rate = rate.replaceAll(",", "").trim();
                                if (Double.parseDouble(rate) >= taille) {
                                    fichier = fichierTemp;
                                    taille = Double.parseDouble(rate);
                                }
                            }
                        }

                        for (File fichierTemp : listFile2) {
                            if (fichierTemp != fichier) {
                                System.out.println(fichierTemp.getPath() + "    " + fichierTemp.length());
                                fichierTemp.delete();
                                compteur++;
                            }
                        }

                        if (map480.containsKey(lineTemp)) {
                            ArrayList<File> listDelete = map480.get(lineTemp);
                            map480.remove(lineTemp);
                            for (File fichierTemp : listDelete) {
                                try {
                                    System.out.println(fichierTemp.getPath() + "    " + fichierTemp.length());
                                    fichierTemp.delete();
                                    compteur++;
                                } catch (Exception ex) {

                                }
                            }
                        }

                        if (map360.containsKey(lineTemp)) {
                            ArrayList<File> listDelete = map360.get(lineTemp);
                            map360.remove(lineTemp);
                            for (File fichierTemp : listDelete) {
                                try {
                                    System.out.println(fichierTemp.getPath() + "    " + fichierTemp.length());
                                    fichierTemp.delete();
                                    compteur++;
                                } catch (Exception ex) {

                                }
                            }
                        }

                        if (map240.containsKey(lineTemp)) {
                            ArrayList<File> listDelete = map240.get(lineTemp);
                            map240.remove(lineTemp);
                            for (File fichierTemp : listDelete) {
                                try {
                                    System.out.println(fichierTemp.getPath() + "    " + fichierTemp.length());
                                    fichierTemp.delete();
                                    compteur++;
                                } catch (Exception ex) {

                                }
                            }
                        }
                    }
                }
            }

            // ====================================480
            if (map480.size() > 0) {
                Set<String> set = map480.keySet();
                for (String lineTemp : set) {
                    ArrayList<File> listFile2 = map480.get(lineTemp);
                    if (listFile2.size() > 0) {
                        File fichier = listFile2.get(0);
                        double taille = 0;
                        double tailleTemp = 0;
                        for (File fichierTemp : listFile2) {
                            StringTokenizer stk = new StringTokenizer(fichierTemp.getName(),"-");
                            String rate = "";
                            while(!(rate.contains("Mbps")||rate.contains("kbps"))&&stk.hasMoreTokens()){
                                rate = stk.nextToken();
                            }
                            if(rate.contains("Mbps")){
                                rate = rate.substring(0, rate.indexOf("Mbps") - 1);
                                rate = rate.replaceAll(",", "").trim();
                                if (Double.parseDouble(rate) * 1000 >= taille) {
                                    fichier = fichierTemp;
                                    taille = Double.parseDouble(rate)*1000;
                                }
                            }
                            if(rate.contains("kbps")){
                                rate = rate.substring(0, rate.indexOf("kbps") - 1);
                                rate = rate.replaceAll(",", "").trim();
                                if (Double.parseDouble(rate) >= taille) {
                                    fichier = fichierTemp;
                                    taille = Double.parseDouble(rate);
                                }
                            }
                        }

                        for (File fichierTemp : listFile2) {
                            if (fichierTemp != fichier) {
                                System.out.println(fichierTemp.getPath() + "    " + fichierTemp.length());
                                fichierTemp.delete();
                                compteur++;
                            }
                        }

                        if (map360.containsKey(lineTemp)) {
                            ArrayList<File> listDelete = map360.get(lineTemp);
                            map360.remove(lineTemp);
                            for (File fichierTemp : listDelete) {
                                try {
                                    System.out.println(fichierTemp.getPath() + "    " + fichierTemp.length());
                                    fichierTemp.delete();
                                    compteur++;
                                } catch (Exception ex) {

                                }
                            }
                        }

                        if (map240.containsKey(lineTemp)) {
                            ArrayList<File> listDelete = map240.get(lineTemp);
                            map240.remove(lineTemp);
                            for (File fichierTemp : listDelete) {
                                try {
                                    System.out.println(fichierTemp.getPath() + "    " + fichierTemp.length());
                                    fichierTemp.delete();
                                    compteur++;
                                } catch (Exception ex) {

                                }
                            }
                        }
                    }
                }
            }

            // ====================================360
            if (map360.size() > 0) {
                Set<String> set = map360.keySet();
                for (String lineTemp : set) {
                    ArrayList<File> listFile2 = map360.get(lineTemp);
                    if (listFile2.size() > 0) {
                        File fichier = listFile2.get(0);
                        double taille = 0;
                        double tailleTemp = 0;
                        for (File fichierTemp : listFile2) {
                            StringTokenizer stk = new StringTokenizer(fichierTemp.getName(),"-");
                            String rate = "";
                            while(!(rate.contains("Mbps")||rate.contains("kbps"))&&stk.hasMoreTokens()){
                                rate = stk.nextToken();
                            }
                            if(rate.contains("Mbps")){
                                rate = rate.substring(0, rate.indexOf("Mbps") - 1);
                                rate = rate.replaceAll(",", "").trim();
                                if (Double.parseDouble(rate) * 1000 >= taille) {
                                    fichier = fichierTemp;
                                    taille = Double.parseDouble(rate)*1000;
                                }
                            }
                            if(rate.contains("kbps")){
                                rate = rate.substring(0, rate.indexOf("kbps") - 1);
                                rate = rate.replaceAll(",", "").trim();
                                if (Double.parseDouble(rate) >= taille) {
                                    fichier = fichierTemp;
                                    taille = Double.parseDouble(rate);
                                }
                            }
                        }

                        for (File fichierTemp : listFile2) {
                            if (fichierTemp != fichier) {
                                System.out.println(fichierTemp.getPath() + "    " + fichierTemp.length());
                                fichierTemp.delete();
                                compteur++;
                            }
                        }

                        if (map240.containsKey(lineTemp)) {
                            ArrayList<File> listDelete = map240.get(lineTemp);
                            map240.remove(lineTemp);
                            for (File fichierTemp : listDelete) {
                                try {
                                    System.out.println(fichierTemp.getPath() + "    " + fichierTemp.length());
                                    fichierTemp.delete();
                                    compteur++;
                                } catch (Exception ex) {

                                }
                            }
                        }
                    }
                }
            }

            // ====================================240
            if (map240.size() > 0) {
                Set<String> set = map240.keySet();
                for (String lineTemp : set) {
                    ArrayList<File> listFile2 = map240.get(lineTemp);
                    if (listFile2.size() > 0) {
                        File fichier = listFile2.get(0);
                        double taille = 0;
                        double tailleTemp = 0;
                        for (File fichierTemp : listFile2) {
                            StringTokenizer stk = new StringTokenizer(fichierTemp.getName(),"-");
                            String rate = "";
                            while(!(rate.contains("Mbps")||rate.contains("kbps"))&&stk.hasMoreTokens()){
                                rate = stk.nextToken();
                            }
                            if(rate.contains("Mbps")){
                                rate = rate.substring(0, rate.indexOf("Mbps") - 1);
                                rate = rate.replaceAll(",", "").trim();
                                if (Double.parseDouble(rate) * 1000 >= taille) {
                                    fichier = fichierTemp;
                                    taille = Double.parseDouble(rate)*1000;
                                }
                            }
                            if(rate.contains("kbps")){
                                rate = rate.substring(0, rate.indexOf("kbps") - 1);
                                rate = rate.replaceAll(",", "").trim();
                                if (Double.parseDouble(rate) >= taille) {
                                    fichier = fichierTemp;
                                    taille = Double.parseDouble(rate);
                                }
                            }
                        }

                        for (File fichierTemp : listFile2) {
                            if (fichierTemp != fichier) {
                                System.out.println(fichierTemp.getPath() + "    " + fichierTemp.length());
                                fichierTemp.delete();
                                compteur++;
                            }
                        }

                    }
                }
            }
            System.out.println("Deleted " + compteur);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public String returnDate(Date date) {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(date);
        SimpleDateFormat fmt = new SimpleDateFormat("dd-MMM-yyyy");
        fmt.setCalendar(gc);
        return fmt.format(gc.getTime());
    }

    public static void main(String[] args) {
        DeleteDuplicatesSeries populate = new DeleteDuplicatesSeries();

    }

}
