import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;

public class CheckVideoFiles {

	public CheckVideoFiles() {
			HashSet<File> listeFichier = FileDirParcours.getParcours("z://test/ultracc/films", new String[]{".mkv",".mp4",".avi"});

			for(File fichier : listeFichier) {
				ProcessBuilder processBuilder = new ProcessBuilder("c:\\ffmpeg\\bin\\ffmpeg.exe", "-i", fichier.toPath().toString());

				try {
					Process process = processBuilder.start();

					BufferedReader reader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
					String line;

					while ((line = reader.readLine()) != null) {
						if (line.contains("Duration") || line.contains("Stream")) {
							System.out.println(line);  // Print relevant metadata
						}
					}

					process.waitFor();

				} catch (IOException | InterruptedException e) {
					e.printStackTrace();
				}

			}
	}


	public static void main(String[] args) {
		CheckVideoFiles populate = new CheckVideoFiles();

	}

}
