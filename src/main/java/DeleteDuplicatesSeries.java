import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;

public class DeleteDuplicatesSeries {
    FileWriter fw;
    PrintWriter pw;

    public DeleteDuplicatesSeries() {
        double taille3 = 0;
        HashMap<String, ArrayList> map240 = new HashMap<String, ArrayList>();
        HashMap<String, ArrayList> map360 = new HashMap<String, ArrayList>();
        HashMap<String, ArrayList> map480 = new HashMap<String, ArrayList>();
        HashMap<String, ArrayList> map576 = new HashMap<String, ArrayList>();
        HashMap<String, ArrayList> map720 = new HashMap<String, ArrayList>();
        HashMap<String, ArrayList> map1080 = new HashMap<String, ArrayList>();
        HashMap<String, ArrayList> map2160 = new HashMap<String, ArrayList>();

        long ms = System.currentTimeMillis();
        System.out.println("debut " + ms);
        try {
            HashSet<File> listeFichier = FileDirParcours.getParcours("z://test/stockage", new String[]{".mp4",".mkv",".avi"});

            for (File fichierTemp : listeFichier) {
                String episode = "";
                StringTokenizer stk = new StringTokenizer(fichierTemp.getName(), "-");
                if (stk.hasMoreTokens()) {
                    episode = stk.nextToken().toLowerCase().trim();
                }
                if(stk.hasMoreTokens()) {
                    episode += " - " + stk.nextToken();
                }

                episode = episode.toLowerCase().trim();

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
                }
            }


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
                                    taille3 = Double.parseDouble(rate)*1000;
                                }
                            }
                            if(rate.contains("kbps")){
                                rate = rate.substring(0, rate.indexOf("kbps") - 1);
                                rate = rate.replaceAll(",", "").trim();
                                if (Double.parseDouble(rate) >= taille) {
                                    fichier = fichierTemp;
                                    taille3 = Double.parseDouble(rate);
                                }
                            }
                        }

                        for (File fichierTemp : listFile2) {
                            if (fichierTemp != fichier) {
                                System.out.println(fichierTemp.getPath() + "    " + fichierTemp.length());
                                taille3 += fichierTemp.length();
                                fichierTemp.renameTo(new File("z://test/temp/"+fichierTemp.getName()));
                            }
                        }

                        if (map1080.containsKey(lineTemp)) {
                            ArrayList<File> listDelete = map1080.get(lineTemp);
                            map1080.remove(lineTemp);
                            for (File fichierTemp : listDelete) {
                                try {
                                    System.out.println(fichierTemp.getPath() + "    " + fichierTemp.length());
                                    taille3 += fichierTemp.length();
                                    fichierTemp.renameTo(new File("z://test/temp/"+fichierTemp.getName()));
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
                                    taille3 += fichierTemp.length();
                                    fichierTemp.renameTo(new File("z://test/temp/"+fichierTemp.getName()));
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
                                    taille3 += fichierTemp.length();
                                    fichierTemp.renameTo(new File("z://test/temp/"+fichierTemp.getName()));
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
                                    taille3 += fichierTemp.length();
                                    fichierTemp.renameTo(new File("z://test/temp/"+fichierTemp.getName()));
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
                                    taille3 += fichierTemp.length();
                                    fichierTemp.renameTo(new File("z://test/temp/"+fichierTemp.getName()));
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
                                    taille3 += fichierTemp.length();
                                    fichierTemp.renameTo(new File("z://test/temp/"+fichierTemp.getName()));
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
                                    taille3 = Double.parseDouble(rate)*1000;
                                }
                            }
                            if(rate.contains("kbps")){
                                rate = rate.substring(0, rate.indexOf("kbps") - 1);
                                rate = rate.replaceAll(",", "").trim();
                                if (Double.parseDouble(rate) >= taille) {
                                    fichier = fichierTemp;
                                    taille3 = Double.parseDouble(rate);
                                }
                            }
                        }

                        for (File fichierTemp : listFile2) {
                            if (fichierTemp != fichier) {
                                System.out.println(fichierTemp.getPath() + "    " + fichierTemp.length());
                                taille3 += fichierTemp.length();
                                fichierTemp.renameTo(new File("z://test/temp/"+fichierTemp.getName()));
                            }
                        }

                        if (map720.containsKey(lineTemp)) {
                            ArrayList<File> listDelete = map720.get(lineTemp);
                            map720.remove(lineTemp);
                            for (File fichierTemp : listDelete) {
                                try {
                                    System.out.println(fichierTemp.getPath() + "    " + fichierTemp.length());
                                    taille3 += fichierTemp.length();
                                    fichierTemp.renameTo(new File("z://test/temp/"+fichierTemp.getName()));
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
                                    taille3 += fichierTemp.length();
                                    fichierTemp.renameTo(new File("z://test/temp/"+fichierTemp.getName()));
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
                                    taille3 += fichierTemp.length();
                                    fichierTemp.renameTo(new File("z://test/temp/"+fichierTemp.getName()));
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
                                    taille3 += fichierTemp.length();
                                    fichierTemp.renameTo(new File("z://test/temp/"+fichierTemp.getName()));
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
                                    taille3 += fichierTemp.length();
                                    fichierTemp.renameTo(new File("z://test/temp/"+fichierTemp.getName()));
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
                                    taille3 = Double.parseDouble(rate)*1000;
                                }
                            }
                            if(rate.contains("kbps")){
                                rate = rate.substring(0, rate.indexOf("kbps") - 1);
                                rate = rate.replaceAll(",", "").trim();
                                if (Double.parseDouble(rate) >= taille) {
                                    fichier = fichierTemp;
                                    taille3 = Double.parseDouble(rate);
                                }
                            }
                        }

                        for (File fichierTemp : listFile2) {
                            if (fichierTemp != fichier) {
                                System.out.println(fichierTemp.getPath() + "    " + fichierTemp.length());
                                taille3 += fichierTemp.length();
                                fichierTemp.renameTo(new File("z://test/temp/"+fichierTemp.getName()));
                            }
                        }

                        if (map576.containsKey(lineTemp)) {
                            ArrayList<File> listDelete = map576.get(lineTemp);
                            map576.remove(lineTemp);
                            for (File fichierTemp : listDelete) {
                                try {
                                    System.out.println(fichierTemp.getPath() + "    " + fichierTemp.length());
                                    taille3 += fichierTemp.length();
                                    fichierTemp.renameTo(new File("z://test/temp/"+fichierTemp.getName()));
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
                                    taille3 += fichierTemp.length();
                                    fichierTemp.renameTo(new File("z://test/temp/"+fichierTemp.getName()));
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
                                    taille3 += fichierTemp.length();
                                    fichierTemp.renameTo(new File("z://test/temp/"+fichierTemp.getName()));
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
                                    taille3 += fichierTemp.length();
                                    fichierTemp.renameTo(new File("z://test/temp/"+fichierTemp.getName()));
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
                                    taille3 = Double.parseDouble(rate)*1000;
                                }
                            }
                            if(rate.contains("kbps")){
                                rate = rate.substring(0, rate.indexOf("kbps") - 1);
                                rate = rate.replaceAll(",", "").trim();
                                if (Double.parseDouble(rate) >= taille) {
                                    fichier = fichierTemp;
                                    taille3 = Double.parseDouble(rate);
                                }
                            }
                        }

                        for (File fichierTemp : listFile2) {
                            if (fichierTemp != fichier) {
                                System.out.println(fichierTemp.getPath() + "    " + fichierTemp.length());
                                taille3 += fichierTemp.length();
                                fichierTemp.renameTo(new File("z://test/temp/"+fichierTemp.getName()));
                            }
                        }

                        if (map480.containsKey(lineTemp)) {
                            ArrayList<File> listDelete = map480.get(lineTemp);
                            map480.remove(lineTemp);
                            for (File fichierTemp : listDelete) {
                                try {
                                    System.out.println(fichierTemp.getPath() + "    " + fichierTemp.length());
                                    taille3 += fichierTemp.length();
                                    fichierTemp.renameTo(new File("z://test/temp/"+fichierTemp.getName()));
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
                                    taille3 += fichierTemp.length();
                                    fichierTemp.renameTo(new File("z://test/temp/"+fichierTemp.getName()));
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
                                    taille3 += fichierTemp.length();
                                    fichierTemp.renameTo(new File("z://test/temp/"+fichierTemp.getName()));
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
                                    taille3 = Double.parseDouble(rate)*1000;
                                }
                            }
                            if(rate.contains("kbps")){
                                rate = rate.substring(0, rate.indexOf("kbps") - 1);
                                rate = rate.replaceAll(",", "").trim();
                                if (Double.parseDouble(rate) >= taille) {
                                    fichier = fichierTemp;
                                    taille3 = Double.parseDouble(rate);
                                }
                            }
                        }


                        for (File fichierTemp : listFile2) {
                            if (fichierTemp != fichier) {
                                System.out.println(fichierTemp.getPath() + "    " + fichierTemp.length());
                                taille3 += fichierTemp.length();
                                fichierTemp.renameTo(new File("z://test/temp/"+fichierTemp.getName()));
                            }
                        }

                        if (map360.containsKey(lineTemp)) {
                            ArrayList<File> listDelete = map360.get(lineTemp);
                            map360.remove(lineTemp);
                            for (File fichierTemp : listDelete) {
                                try {
                                    System.out.println(fichierTemp.getPath() + "    " + fichierTemp.length());
                                    taille3 += fichierTemp.length();
                                    fichierTemp.renameTo(new File("z://test/temp/"+fichierTemp.getName()));
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
                                    taille3 += fichierTemp.length();
                                    fichierTemp.renameTo(new File("z://test/temp/"+fichierTemp.getName()));
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
                                    taille3 = Double.parseDouble(rate)*1000;
                                }
                            }
                            if(rate.contains("kbps")){
                                rate = rate.substring(0, rate.indexOf("kbps") - 1);
                                rate = rate.replaceAll(",", "").trim();
                                if (Double.parseDouble(rate) >= taille) {
                                    fichier = fichierTemp;
                                    taille3 = Double.parseDouble(rate);
                                }
                            }
                        }


                        for (File fichierTemp : listFile2) {
                            if (fichierTemp != fichier) {
                                System.out.println(fichierTemp.getPath() + "    " + fichierTemp.length());
                                taille3 += fichierTemp.length();
                                fichierTemp.renameTo(new File("z://test/temp/"+fichierTemp.getName()));
                            }
                        }

                        if (map240.containsKey(lineTemp)) {
                            ArrayList<File> listDelete = map240.get(lineTemp);
                            map240.remove(lineTemp);
                            for (File fichierTemp : listDelete) {
                                try {
                                    System.out.println(fichierTemp.getPath() + "    " + fichierTemp.length());
                                    taille3 += fichierTemp.length();
                                    fichierTemp.renameTo(new File("z://test/temp/"+fichierTemp.getName()));
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
                                    taille3 = Double.parseDouble(rate)*1000;
                                }
                            }
                            if(rate.contains("kbps")){
                                rate = rate.substring(0, rate.indexOf("kbps") - 1);
                                rate = rate.replaceAll(",", "").trim();
                                if (Double.parseDouble(rate) >= taille) {
                                    fichier = fichierTemp;
                                    taille3 = Double.parseDouble(rate);
                                }
                            }
                        }


                        for (File fichierTemp : listFile2) {
                            if (fichierTemp != fichier) {
                                double taille2 = fichierTemp.length()/1024/1024/1024;
                                System.out.println(fichierTemp.getPath() + "    " + taille2);
                                taille3 += fichierTemp.length();
                                fichierTemp.renameTo(new File("z://test/temp/"+fichierTemp.getName()));
                            }
                        }

                    }
                }
            }

        } catch (

                Exception ex) {
            ex.printStackTrace();
        }
        System.out.println("Taille gagnee "+(taille3/1000000000));
//		DeleteFilmBeforeConvert del = new DeleteFilmBeforeConvert();
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
