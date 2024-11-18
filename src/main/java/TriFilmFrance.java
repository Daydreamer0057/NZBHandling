import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TriFilmFrance {
    public TriFilmFrance() {
        String url = "jdbc:mysql://localhost:3306/lexique?serverTimezone=UTC"; // Replace 'mydb' with your DB name
        String username = "root"; // Replace with your MySQL username
        String password = "abcd"; // Replace with your MySQL password

        try {
//            HashSet<File> listeFichier = FileDirParcours.getParcours("z:/test/film a traiter",new String[]{"avi","mp4","mkv"});
//            HashSet<File> listeFichier = FileDirParcours.getParcours("z:/test/ultracc/films france",new String[]{"avi","mp4","mkv"});
//            HashSet<File> listeFichier = FileDirParcours.getParcours("z:/test/film temp",new String[]{"avi","mp4","mkv"});
            HashSet<File> listeFichier = FileDirParcours.getParcours("z:/test/film temp",new String[]{"avi","mp4","mkv"});

            Class.forName("com.mysql.cj.jdbc.Driver");


            Connection connection = DriverManager.getConnection(url, username, password);

            System.out.println("Connection established successfully!");

            int compteur = 0;
            for(File fichierTemp : listeFichier){
//                if(fichierTemp.getName().toLowerCase().contains("retour")){
//                    System.out.println("test");
//                }
                System.out.println(compteur + " / " + listeFichier.size());
                compteur++;
                String name = fichierTemp.getName();
                name = name.replace("."," ");
                StringTokenizer stk = new StringTokenizer(name," ");
                while(stk.hasMoreTokens()) {
                    String lineTemp = stk.nextToken();
                    Pattern p = Pattern.compile(".*([0-9]{4}).*");
                    Matcher m = p.matcher(lineTemp.toLowerCase());

                    if (m.matches()) {
                        break;
                    }

                    if (lineTemp.length() > 3) {
                        String sql = "SELECT * FROM lexique WHERE ortho = ?";

                        PreparedStatement statement = connection.prepareStatement(sql);

                        // Set the parameter for the query
                        statement.setString(1, lineTemp);

                        // Execute the query
                        ResultSet resultSet = statement.executeQuery();
                        // Process the result set
                        if (resultSet.next()) {
                            System.out.println("Found " + fichierTemp.getPath());
                            String chemin = "z:/test/download/" + fichierTemp.getName();
                            int compteurExist = 0;
                            while (new File(chemin).exists()) {
                                String extension = FilenameUtils.getExtension(chemin);
                                chemin = FilenameUtils.removeExtension(chemin) + "_" + compteurExist + "." + extension;
                            }
                            FileUtils.moveFile(fichierTemp, new File(chemin));
                            break;
                        }
                    }
                }
            }
            connection.close();
        } catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        TriFilmFrance a = new TriFilmFrance();
    }
}