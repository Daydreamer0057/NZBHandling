package Utilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;

public class Lexique {
    public Lexique() {
        try {
            PrintWriter pw = new PrintWriter("e:/temp/Lexique383/Lexique383.sql");
            FileReader fr = new FileReader(new File("e://temp/Lexique383/Lexique383.tsv"));
            BufferedReader br = new BufferedReader(fr);

            String line = "";
            while(line!=null){
                line = br.readLine();
                if(line!=null){
                    String name = "INSERT INTO `lexique` VALUES\\('";
                    line = line.replaceAll(name,"");
                    name = name + (line.substring(0,line.indexOf("'")+1)) + "\\);";
                    name = name.replaceAll("\\\\","");
                    pw.println(name);
                    pw.flush();
                }
            }

            pw.flush();
            pw.close();
            br.close();
            fr.close();
        } catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Lexique a = new Lexique();
    }
}