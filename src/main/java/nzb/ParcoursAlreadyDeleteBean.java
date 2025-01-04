package nzb;

import java.io.File;
import java.util.HashSet;

public class ParcoursAlreadyDeleteBean {
    private HashSet<File> listeFichierATraiter = new HashSet<File>();

    public ParcoursAlreadyDeleteBean(HashSet<File> liste) {
        this.listeFichierATraiter = liste;
    }
    @Override
    public String toString() {
        return "test";
    }

    public HashSet<File> getListeFichierATraiter() {
        return listeFichierATraiter;
    }
    public void setListeFichierATraiter(HashSet<File> listeFichierATraiter) {
        this.listeFichierATraiter = listeFichierATraiter;
    }


}
