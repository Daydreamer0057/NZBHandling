import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RemoveM4V {
    public RemoveM4V(){
        //File base = new File("e://convert");
        File base = new File("z://temp/convert");
        File[] fichiers = base.listFiles();

        for(File fichierTemp : fichiers){
            String name = fichierTemp.getName();
            Pattern pattern = Pattern.compile("[-][0-9]+[.]");
            Matcher matcher = pattern.matcher(fichierTemp.getName());
            while(matcher.find()) {
                int pos1 = name.lastIndexOf('-');
                int pos2 = name.lastIndexOf('.');
                if (!(pos1 == -1 || pos2 == -1)) {
                    name = name.substring(0, pos1-1) + name.substring(pos2, name.length());

                    Path p = Paths.get(fichierTemp.getPath());
                    Path folder = p.getParent();
                    String path = folder.toString();
                    path = path.replaceAll("\\\\", "//");

                    fichierTemp.renameTo(new File(path + "/" + name));
                }
            }
        }
    }

    public static void main(String[] args) {
        RemoveM4V rem = new RemoveM4V();
    }
}
