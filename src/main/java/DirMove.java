import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;

public class DirMove {

    public DirMove() throws Exception {
        File fichier = new File("d://film");

        File[] fichiers = fichier.listFiles();

        for(File fichierTemp : fichiers) {
            Runnable runnable = () -> {
                try {
                    FileUtils.moveDirectory(fichierTemp, new File("z://test/film" + fichierTemp.getName()));
                } catch(Exception ex){
                    ex.printStackTrace();
                }
            };

            while (Thread.activeCount() > 10){
                Thread.sleep(1000);
            }

            Thread t = new Thread(runnable);
            t.start();
        }
    }

    public static void main(String[] args) {
        try {
            DirMove nzb = new DirMove();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
