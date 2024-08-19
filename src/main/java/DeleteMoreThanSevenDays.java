import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class DeleteMoreThanSevenDays {
	static String path2;
	static int compteurDelete = 0;

	static double tailleGagnee = 0.0;
	public DeleteMoreThanSevenDays() throws Exception{
		while (true) {
			while (new File("f://").getFreeSpace() > 350000000000L) {
//				System.out.println(new File("f://").getFreeSpace() / 1000000000L);
				try {
					Thread.sleep(60000);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}

			runAutoit();

			double timeMax = 0;

			int compteurBase = 0;
			System.out.println("Started");
			HashSet<File> listeFichier = new HashSet<>();
			FileReader fr = new FileReader("c://temp/list_files_gz.txt");
			BufferedReader br = new BufferedReader(fr);

			String line = "";
			while(line!=null){
				line = br.readLine();
				if(line!=null){
					listeFichier.add(new File(line));
				}
			}

			br.close();
			fr.close();

			ArrayList<File> listFileGZ = new ArrayList<>();
			listFileGZ.addAll(listeFichier);

			Collections.sort(listFileGZ, new Comparator<File>() {
				@Override
				public int compare(File lhs, File rhs) {
					if (lhs.lastModified() > rhs.lastModified()) {
						return 1;
					}
					if (lhs.lastModified() == rhs.lastModified()) {
						return 0;
					}
					if (lhs.lastModified() < rhs.lastModified()) {
						return -1;
					}
					return 0;
				}
			});

			int compteur = 0;
			int compteurDelete = 0;
			long sizeDelete = 0;
			for (File fichier : listFileGZ) {
				compteur++;
//				System.out.println("Compteur "+compteur + " / " + listeFichier.size());

				double sizeDirectory = 0;
				if (fichier.getParentFile().getName().toLowerCase().contains("admin")) {
					sizeDirectory = Double.parseDouble("" + calculTaille(fichier.getParentFile().getParentFile())) / 1000000000;
				} else {
					sizeDirectory = Double.parseDouble("" + calculTaille(fichier.getParentFile())) / 1000000000;
				}
				System.out.println("Final " + timeMax + "    Current " + timeMax + "   size " + sizeDirectory + "    compteur " + compteur + " / " + listFileGZ.size());
//					pw.println("Final " + timeFinal + "    Current " + ((System.currentTimeMillis() - fichier.lastModified()) / (24 * 3600 * 1000)) + "    size " + sizeDirectory);
//					pw.flush();
				try {
					fichier.renameTo(new File("c://NZB/Download/" + fichier.getName()));
					if (fichier.getParentFile().getName().toLowerCase().contains("admin")) {
						sizeDelete += calculTaille(fichier);
						FileUtils.deleteDirectory(fichier.getParentFile().getParentFile());
						compteurDelete++;
					} else {
						sizeDelete += calculTaille(fichier);
						FileUtils.deleteDirectory(fichier.getParentFile());
						compteurDelete++;
					}
					if (new File("f://").getFreeSpace() > 1000000000000L) {
						break;
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}

			System.out.println("Repertoires Effaces " + compteurDelete);
			double sizeDeleteDouble = Double.parseDouble("" + sizeDelete);
			System.out.println("Taille effacee " + (sizeDeleteDouble / 1000000000));

//			pw.println("Repertoires Effaces " + compteurDelete + "    Taille effacee " + (sizeDeleteDouble / 1000000000));
//			pw.flush();
//			pw.close();

//			File fichierDownload = new File("c:/nzb/download");
//			File[] fichiersDownload = fichierDownload.listFiles();
//			for (File fichierTemp : fichiersDownload) {
//				if (fichierTemp.isFile()) {
//					unzip(fichierTemp.getPath(), "c:/nzb/sabnzbd");
//				}
//			}
		}
	}





	public static void unzip(String zipFilePath, String destDir) {
//        File dir = new File(destDir);
		// create output directory if it doesn't exist
		// if (!dir.exists())
		// dir.mkdirs();
		FileInputStream fis;
		// buffer for read and write data to file
		byte[] buffer = new byte[1024];
		try {
			fis = new FileInputStream(zipFilePath);
			ZipInputStream zis = new ZipInputStream(fis, Charset.forName("Cp437"));
			ZipEntry ze = zis.getNextEntry();
			while (ze != null) {
				String fileName = ze.getName();
				try {
					File newFile = new File(destDir + File.separator + fileName);
					int compteur = 0;
//                    while (newFile.exists()) {
//                        newFile = new File(destDir + File.separator + FilenameUtils.removeExtension(fileName) + "_" + compteur + fileName.substring(fileName.length()-4,fileName.length()));
//                        compteur++;
//                    }
//                    if (!newFile.exists()) {
					System.out.println("Unzipping to " + newFile.getAbsolutePath());
					// create directories for sub directories in zip
					new File(newFile.getParent()).mkdirs();
					FileOutputStream fos = new FileOutputStream(newFile);
					int len;
					while ((len = zis.read(buffer)) > 0) {
						fos.write(buffer, 0, len);
					}
					fos.flush();
					fos.close();
//                    }
				} catch(Exception ex){
					ex.printStackTrace();
				}
				// close this ZipEntry
				zis.closeEntry();
				ze = zis.getNextEntry();
			}
			// close last ZipEntry
			zis.closeEntry();
			zis.close();
			fis.close();
			File fichier = new File(zipFilePath);
			fichier.delete();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public long calculTaille(File file) {
		long taille = 0;
		HashSet<File> listeFichierDownload = new HashSet<File>();
		ArrayList<File> listeDirectoryDownload = new ArrayList<File>();
		listeDirectoryDownload.add(file);

		while (listeDirectoryDownload.size() > 0) {
//			System.out.println(listeFichierDownload.size() + "    " + listeDirectoryDownload.size());
			File[] fichierListeDownload = listeDirectoryDownload.get(0).listFiles();

			if (fichierListeDownload != null) {
				for (File fichierTempDownload : fichierListeDownload) {
					if (fichierTempDownload.isDirectory()) {
						listeDirectoryDownload.add(fichierTempDownload);
					} else {
						taille += fichierTempDownload.length();
					}
				}
			}
			listeDirectoryDownload.remove(0);
		}
		return taille;
	}

	public int runAutoit() {
		try {
			Process process = Runtime.getRuntime().exec("c://AU3_2/FileParcours.exe");
// Don't forget to close all streams!
			process.getErrorStream().close();
			process.getInputStream().close();
			process.getOutputStream().close();

			return process.waitFor();
		} catch(Exception ex){
			ex.printStackTrace();
		}
		return 0;
	}

	public static void main(String[] args) {
		try {
			DeleteMoreThanSevenDays epguides = new DeleteMoreThanSevenDays();
		}catch(Exception ex){
			ex.printStackTrace();
		}

	}

}
