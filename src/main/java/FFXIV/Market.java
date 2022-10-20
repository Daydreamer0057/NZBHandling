package FFXIV;

import sun.awt.image.IntegerComponentRaster;

import java.io.*;
import java.util.*;

public class Market {
    public ArrayList<String> set;

    public Market() throws Exception{
        PrintWriter pw = new PrintWriter("e://ffxiv/log.csv");

        pw.println("name" + "," + "lowestPrice" + "," + "average" + "," + "sold" + "," + "buy");

        File base = new File("e://ffxiv/csv");
        File[] fichiers = base.listFiles();

        set = new ArrayList<>();

        String line = "";

        for(File fichierTemp : fichiers) {
            System.out.println(fichierTemp.getPath());
            FileReader fr = new FileReader(fichierTemp);
            BufferedReader br = new BufferedReader(fr);

            String lineTemp = "";
            while(lineTemp != null) {
                lineTemp = br.readLine();

                if(lineTemp!=null) {
                    line += lineTemp;
                }

            }

            br.close();
            fr.close();
        }

        int pos =0;
        int posLine = 0;
        while(pos!=-1) {
            pos = 0;
            posLine = 0;

            System.out.println(line.length());

            pos = line.indexOf("debutitem");
            posLine += pos;

            String lineTemp = line.substring(pos + 9, line.length());
            pos = lineTemp.indexOf(",");
            posLine += pos;

            String name = "";
            if(posLine<lineTemp.length()) {
                name = lineTemp.substring(0, pos);
            } else {
                break;
            }

            lineTemp = lineTemp.substring(pos + 1, lineTemp.length());
            pos = lineTemp.indexOf(",");
            posLine += pos;
            String lowestPrice = "";
            if(posLine<lineTemp.length()) {
                lowestPrice  = lineTemp.substring(0, pos);
            } else {
                break;
            }
            lowestPrice = lowestPrice.replaceAll("\\?","");

            lineTemp = lineTemp.substring(pos + 1, lineTemp.length());
            pos = lineTemp.indexOf(",");
            posLine += pos;
            lineTemp = lineTemp.substring(pos + 1, lineTemp.length());
            pos = lineTemp.indexOf(",");
            posLine += pos;
            lineTemp = lineTemp.substring(pos + 1, lineTemp.length());
            pos = lineTemp.indexOf(",");
            posLine += pos;
            lineTemp = lineTemp.substring(pos + 1, lineTemp.length());
            pos = lineTemp.indexOf(",");
            posLine += pos;
            lineTemp = lineTemp.substring(pos + 1, lineTemp.length());
            pos = lineTemp.indexOf(",");
            posLine += pos;

            String average = "";
            if(posLine<lineTemp.length()) {
                average  = lineTemp.substring(0, pos);
            } else {
                break;
            }
            average = average.replaceAll("\\?","");

            lineTemp = lineTemp.substring(pos + 1, lineTemp.length());
            pos = lineTemp.indexOf(",");
            posLine += pos;
            lineTemp = lineTemp.substring(pos + 1, lineTemp.length());
            pos = lineTemp.indexOf(",");
            posLine += pos;
            lineTemp = lineTemp.substring(pos + 1, lineTemp.length());
            pos = lineTemp.indexOf(",");
            posLine += pos;

            String sold = "";
            if(posLine<lineTemp.length()) {
                sold  = lineTemp.substring(0, pos);
            } else {
                break;
            }
            sold = sold.replaceAll("\\?","");

            int buy = 0;

            if(!average.equals("") && !lowestPrice.equals("")) {
                buy = Integer.parseInt(average) - Integer.parseInt(lowestPrice);
            }

            if(!name.equalsIgnoreCase("") && !lowestPrice.equalsIgnoreCase("") && !average.equalsIgnoreCase("")&& !sold.equalsIgnoreCase("") && buy > 0) {
                set.add(name + "," + lowestPrice + "," + average + "," + sold + "," + buy);
                if(Integer.parseInt(sold)>100) {
                    pw.println(name + "," + lowestPrice + "," + average + "," + sold + "," + buy);
                }
            }

            if(posLine<line.length()) {
                line = line.substring(posLine, line.length());
            } else {
                break;
            }
        }

//        Collections.sort(set, new Comparator<String>() {
//            @Override
//            public int compare(String o1, String o2) {
//                int retour = 0;
//                String name = "";
//                String name2 = "";
//                int lowestPrice1 = 0;
//                int lowestPrice2 = 0;
//                int average1 = 0;
//                int average2 = 0;
//
//                StringTokenizer stk = new StringTokenizer(o1, ",");
//
//                if(stk.hasMoreTokens()) {
//                    name = stk.nextToken();
//                }
//
//                if(stk.hasMoreTokens()) {
//                    lowestPrice1 = Integer.parseInt(stk.nextToken());
//                }
//
//                if(stk.hasMoreTokens()) {
//                    average1 = Integer.parseInt(stk.nextToken());
//                }
//
//                StringTokenizer stk2 = new StringTokenizer(o2, ",");
//
//                if(stk2.hasMoreTokens()) {
//                    name2 = stk2.nextToken();
//                }
//
//                if(stk2.hasMoreTokens()) {
//                    lowestPrice2 = Integer.parseInt(stk2.nextToken());
//                }
//
//                if(stk2.hasMoreTokens()) {
//                    average2 = Integer.parseInt(stk2.nextToken());
//                }
//
//                if ((average1 - lowestPrice1) < (average2 - lowestPrice2)) {
//                    retour = -1;
//                }
//
//                if ((average1 - lowestPrice1) > (average2 - lowestPrice2)) {
//                    retour = 1;
//                }
//
//                if ((average1 - lowestPrice1) == (average2 - lowestPrice2)) {
//                    retour = 0;
//                }
//
//
//                return retour;
//
//            } });

//        ArrayList<String> listCopy = new ArrayList<>();
//        for (String setTemp : set) {
//            StringTokenizer stk = new StringTokenizer(setTemp, ",");
//
//            String name2 = "";
//            int averageValue = 0;
//            int lowestPrice2 = 0;
//            int soldValue = 0;
//
//            if(stk.hasMoreTokens()){
//                name2 = stk.nextToken();
//            }
//
//            if(stk.hasMoreTokens()){
//                averageValue = Integer.parseInt(stk.nextToken());
//            }
//
//            if(stk.hasMoreTokens()){
//                lowestPrice2 = Integer.parseInt(stk.nextToken());
//            }
//
//            if(stk.hasMoreTokens()){
//                soldValue = Integer.parseInt(stk.nextToken());
//            }
//
//            if ((averageValue > lowestPrice2) && (soldValue > 10) && (!listCopy.contains(setTemp))) {
//                pw.println(setTemp);
//                pw.flush();
//                listCopy.add(setTemp);
//            }
//        }


        pw.flush();
        pw.close();
    }


    public static void main(String[] args) {
        try {
            Market m = new Market();
        } catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
