import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import entity.NZBEntity;

public class CheckNZB {

	public CheckNZB() {
		try {
			
		File basePrincipal = new File("c://nzb/old");

		File[] listPrincipal = basePrincipal.listFiles();

		HashSet<File> listePrincipale = new HashSet<File>();

		for (File fichier : listPrincipal) {
			listePrincipale.add(fichier);
		}
		
		File basePrincipalNew = new File("c://nzb/new");

		File[] listPrincipalNew = basePrincipalNew.listFiles();

		HashSet<File> listePrincipaleNew = new HashSet<File>();

		for (File fichier : listPrincipalNew) {
			listePrincipaleNew.add(fichier);
		}
		
		int compteur = 0;
		for(File fichier : listePrincipaleNew) {
			compteur++;
			System.out.println(compteur+" / "+listePrincipaleNew.size());
			if(listePrincipale.contains(fichier)) {
				fichier.delete();
			}
		}
		
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void main(String[] args) {
		CheckNZB populate = new CheckNZB();

	}

}
