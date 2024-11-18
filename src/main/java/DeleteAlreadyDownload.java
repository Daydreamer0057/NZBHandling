import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.EntityUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DeleteAlreadyDownload {
	static String path2;
	static int compteurDelete = 0;

	static double tailleGagnee = 0.0;
	public DeleteAlreadyDownload() {
		try {
			while (true) {
				int compteurDownload = 0;
				int compteurTreated = 0;

				File base = new File("z://film/treated");

				File[] fichiers = base.listFiles();

				HashSet<File> listeFichierATraiter = new HashSet<File>();
				ArrayList<File> listeDirectory = new ArrayList<File>();

				listeDirectory.add(new File("z:/test/test"));
//		listeDirectory.add(new File("z:/film/new"));

				for (File fichier : fichiers) {
					System.out.println(listeDirectory.size());
					if (fichier.isDirectory()) {
						listeDirectory.add(fichier);
					} else {
						if (!fichier.getName().endsWith(".srt") && (fichier.getName().endsWith(".mp4") || fichier.getName().endsWith(".mkv") || fichier.getName().endsWith(".avi"))) {
							Pattern p = Pattern.compile(".*([sS][0-9]+[xXeE][0-9]+).*");
							Matcher m = p.matcher(fichier.getName().toLowerCase());

							if (!m.matches()) {
								listeFichierATraiter.add(fichier);
							}
						}
					}
				}

				while (listeDirectory.size() > 0) {
					System.out.println(listeDirectory.size());
					File fichier = listeDirectory.get(0);
					File[] fichierListe = fichier.listFiles();

					for (File fichierTemp : fichierListe) {
						if (fichierTemp.isDirectory()) {
							listeDirectory.add(fichierTemp);
						} else {
							if (!fichierTemp.getName().endsWith(".srt") && (fichierTemp.getName().endsWith(".mp4") || fichierTemp.getName().endsWith(".mkv") || fichierTemp.getName().endsWith(".avi"))) {
								Pattern p = Pattern.compile(".*([sS][0-9]+[xXeE][0-9]+).*");
								Matcher m = p.matcher(fichier.getName().toLowerCase());

								if (!m.matches()) {
									listeFichierATraiter.add(fichierTemp);
								}
							}
						}
					}
					listeDirectory.remove(0);
				}

				String auctionUrl = "http://10.0.0.10:8080/sabnzbd/api?mode=queue&output=json&apikey=3865b6afbae84de6a11e8509e5f09fae";
				CloseableHttpClient httpClient = HttpClients.createDefault();
				HttpGet request = new HttpGet(auctionUrl);
				CloseableHttpResponse response = httpClient.execute(request);
				System.out.println("Response Code: " + response.getCode());
				String jsonResponse = EntityUtils.toString(response.getEntity());

				ObjectMapper mapper = new ObjectMapper();

				JsonNode rootNode = mapper.readTree(jsonResponse);
				JsonNode queueNode = rootNode.path("queue");
				JsonNode itemsNode = queueNode.path("slots");

				String id = "";
				for (JsonNode itemNode : itemsNode) {
					String idTemp = itemNode.path("nzo_id").asText();
					String nameTemp = itemNode.path("filename").asText();
				
				
					compteurDownload++;
					compteurTreated = 0;
					int resolutionDownload = 0;
					String nameDownloadBase = nameTemp.replace('.', ' ');

					if (nameTemp.toLowerCase().contains("2160")) {
						resolutionDownload = 2160;
					}
					if (nameTemp.toLowerCase().contains("1080")) {
						resolutionDownload = 1080;
					}
					if (nameTemp.toLowerCase().contains("720")) {
						resolutionDownload = 720;
					}
					if (nameTemp.toLowerCase().contains("576")) {
						resolutionDownload = 576;
					}
					if (nameTemp.toLowerCase().contains("480")) {
						resolutionDownload = 480;
					}
					if (nameTemp.toLowerCase().contains("360")) {
						resolutionDownload = 360;
					}
					if (nameTemp.toLowerCase().contains("240")) {
						resolutionDownload = 240;
					}
					if (nameTemp.toLowerCase().contains("dvd")) {
						resolutionDownload = 480;
					}

					String yearDownload = "";

					int compteurSplit = 0;
					String[] nameFinalSplit = nameDownloadBase.split(" ");
					String nameDownload = "";
					boolean nameTest = false;
					for (String lineTemp : nameFinalSplit) {
						if (lineTemp != null && lineTemp.length() > 0) {
							Pattern p = Pattern.compile(".*([0-9]{4}).*");
							Matcher m = p.matcher(lineTemp.toLowerCase());

							if (m.matches()) {
								int compteurSplit2 = 1;
								for (String lineTempSplit : nameFinalSplit) {
									if (compteurSplit2 <= compteurSplit) {
										nameDownload += lineTempSplit + " ";
									} else {
										nameTest = true;
										break;
									}
									compteurSplit2++;
								}
								if (nameTest) {
									break;
								}
							}
							compteurSplit++;
						}
					}
					nameDownload = nameDownload.trim();

					String[] yearFinalSplit = nameDownloadBase.split(" ");
					nameTest = false;
					for (String lineTemp : yearFinalSplit) {
						if (lineTemp != null && lineTemp.length() > 0) {
							Pattern p = Pattern.compile(".*([0-9]{4}).*");
							Matcher m = p.matcher(lineTemp.toLowerCase());

							if (m.matches()) {
								yearDownload = lineTemp.trim();
								break;
							}
						}
					}

					for (File fichierTreated : listeFichierATraiter) {
						compteurTreated++;

						String nameTreatedBase = fichierTreated.getName().replace('.', ' ');

						int resolutionTreated = 0;

						if (fichierTreated.getName().toLowerCase().contains("2160")) {
							resolutionTreated = 2160;
						}
						if (fichierTreated.getName().toLowerCase().contains("1080")) {
							resolutionTreated = 1080;
						}
						if (fichierTreated.getName().toLowerCase().contains("720")) {
							resolutionTreated = 720;
						}
						if (fichierTreated.getName().toLowerCase().contains("576")) {
							resolutionTreated = 576;
						}
						if (fichierTreated.getName().toLowerCase().contains("480")) {
							resolutionTreated = 480;
						}
						if (fichierTreated.getName().toLowerCase().contains("360")) {
							resolutionTreated = 360;
						}
						if (fichierTreated.getName().toLowerCase().contains("240")) {
							resolutionTreated = 240;
						}
						if (fichierTreated.getName().toLowerCase().contains("dvd")) {
							resolutionTreated = 480;
						}

						if (resolutionDownload == resolutionTreated) {

//					System.out.println("fichier download " + nameTemp);

							String yearTreated = "";

							compteurSplit = 0;
							String[] nameFinalSplittreated = nameTreatedBase.split(" ");
							String nameTreated = "";
							nameTest = false;
							for (String lineTemp : nameFinalSplittreated) {
								if (lineTemp != null && lineTemp.length() > 0) {
									Pattern p = Pattern.compile(".*([0-9]{4}).*");
									Matcher m = p.matcher(lineTemp.toLowerCase());

									if (m.matches()) {
										int compteurSplit2 = 1;
										for (String lineTempSplit : nameFinalSplittreated) {
											if (compteurSplit2 <= compteurSplit) {
												nameTreated += lineTempSplit + " ";
											} else {
												nameTest = true;
												break;
											}
											compteurSplit2++;
										}
										if (nameTest) {
											break;
										}
									}
									compteurSplit++;
								}
							}
							nameTreated = nameTreated.trim();

							String[] yearFinalSplitTreated = nameTreatedBase.split(" ");
							nameTest = false;
							for (String lineTemp : yearFinalSplitTreated) {
								if (lineTemp != null && lineTemp.length() > 0) {
									Pattern p = Pattern.compile(".*([0-9]{4}).*");
									Matcher m = p.matcher(lineTemp.toLowerCase());

									if (m.matches()) {
										yearTreated = lineTemp.trim();
										yearTreated = yearTreated.replace("(", "");
										yearTreated = yearTreated.replace(")", "");
										break;
									}
								}
							}

							if (nameTreated.toLowerCase().contains(nameDownload.toLowerCase()) && yearDownload.toLowerCase().equals(yearTreated) && yearTreated != "" && nameTreated != "" && nameDownload != "" && yearDownload != "") {
								if (resolutionDownload <= resolutionTreated) {
									try {
//								if (fichierTreated.getPath().toLowerCase().contains("treated")) {
										System.out.println("Resolution Equal Delete " + nameDownload + "    " + nameTreated + "    download " + compteurDownload + " /" + itemsNode.size() + "    treated " + compteurTreated + " /" + listeFichierATraiter.size());

										auctionUrl = "http://10.0.0.10:8080/sabnzbd/api?mode=queue&name=delete&value=" + idTemp + "&output=json&apikey=3865b6afbae84de6a11e8509e5f09fae&nzbkey=90b006a9f0ad4ef493110e1c8e80393a";
										CloseableHttpClient httpClient2 = HttpClients.createDefault();
										HttpGet request2 = new HttpGet(auctionUrl);
										CloseableHttpResponse response2 = httpClient2.execute(request2);
										System.out.println("Response Code: " + response2.getCode());
									} catch (Exception ex) {
										ex.printStackTrace();
									}
								}
							}
						}
					}
				}
				System.out.println("Taille gagnee " + (tailleGagnee / 1000000000L));
				try {
					System.out.println("Wait");
					for (int i = 1; i < 60; i++) {
						Thread.sleep(60000);
						System.out.println(60 - i);
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				httpClient.close();
			}
		} catch (Exception ex){
			ex.printStackTrace();
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

	public static void main(String[] args) {
		DeleteAlreadyDownload epguides = new DeleteAlreadyDownload();

	}

}
