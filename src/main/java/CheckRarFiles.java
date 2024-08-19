import net.sf.sevenzipjbinding.IInArchive;
import net.sf.sevenzipjbinding.PropID;
import net.sf.sevenzipjbinding.SevenZip;
import net.sf.sevenzipjbinding.impl.RandomAccessFileInStream;
import net.sf.sevenzipjbinding.simple.ISimpleInArchive;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;

public class CheckRarFiles {

	public CheckRarFiles() {
		try {
			PrintWriter pw = new PrintWriter(new File("z://film/list_volumes.txt"));
			HashSet<File> listeFichier = FileDirParcours.getParcours("z://test/film a traiter", new String[]{".rar"});
			HashSet<String>	listIgnore = new HashSet<>();

			int compteur = 0;
			for (File fichierTemp : listeFichier) {
				System.out.println("Compteur " + compteur + " / " + listeFichier.size());
				compteur++;

				Path p = Paths.get(fichierTemp.getPath()).getParent();

				String path = p.toString();
				String chemin = path.replaceAll("\\\\", "/");
				chemin = chemin.replaceAll("w:","d:");
				chemin = chemin.replaceAll("z:","d:");

				if(!listIgnore.contains(chemin)) {
					boolean test = isArchivePasswordProtected(fichierTemp.getPath(), pw, listIgnore, chemin);

					if (test) {
						System.out.println(fichierTemp.getPath());
						pw.println("===============================================================    " + fichierTemp.getPath());
//						HashSet<File> listeDelete = FileDirParcours.getParcours(chemin, new String[]{".rar"});
//						fichierDelete.delete();
					}
				}
			}
			pw.flush();
			pw.close();
		} catch (Exception ex){
			ex.printStackTrace();
		}
	}

	public boolean isArchivePasswordProtected(String pathToFile, PrintWriter pw, HashSet listIgnore, String chemin)
	{
		try {
			RandomAccessFile randomAccessFile = new RandomAccessFile(new File(pathToFile), "r");
			IInArchive inArchive = SevenZip.openInArchive(null, // autodetect archive type
					new RandomAccessFileInStream(randomAccessFile));
			ISimpleInArchive simpleInArchive = inArchive.getSimpleInterface();

			if(inArchive.getArchiveProperty(PropID.VOLUME_INDEX)!=null) {
				Long volumeIndex = Long.parseLong(""+inArchive.getArchiveProperty(PropID.VOLUME_INDEX));
				if (volumeIndex == 0) {
					pw.println(pathToFile);
					pw.flush();

					listIgnore.add(chemin);

					return Boolean.TRUE.equals(inArchive.getArchiveProperty(PropID.ENCRYPTED));
				}


			return Boolean.TRUE.equals(inArchive.getArchiveProperty(PropID.ENCRYPTED));
			}
			return false;

		} catch (IOException ex){
			ex.printStackTrace();
		}

		return  false;
	}

	public static void main(String[] args) {
		CheckRarFiles populate = new CheckRarFiles();

	}

}
