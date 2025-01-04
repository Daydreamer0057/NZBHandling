package nzb;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.EntityUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DeleteAlreadyDownload {
	static String path2;

	static double tailleGagnee = 0.0;
	public DeleteAlreadyDownload() {
		try {
			int compteurDelete = 0;
			ParcoursAlreadyDeleteBean bean = null;

			try (FileInputStream fileIn = new FileInputStream("c:/temp/listeFilms.txt");
				 ObjectInputStream in = new ObjectInputStream(fileIn)) {
				bean = (ParcoursAlreadyDeleteBean) in.readObject();
				in.close();
				fileIn.close();
				System.out.println("Object has been deserialized");
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}

			HashSet<File> listeFilmsATraiter = bean.getListeFichierATraiter();

			try (FileInputStream fileIn = new FileInputStream("c:/temp/listeSeries.txt");
				 ObjectInputStream in = new ObjectInputStream(fileIn)) {
				bean = (ParcoursAlreadyDeleteBean) in.readObject();
				in.close();
				fileIn.close();
				System.out.println("Object has been deserialized");
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}

			HashSet<File> listeSeriesATraiter = bean.getListeFichierATraiter();

			while (true) {

				int compteurDownload = 0;
				int compteurTreated = 0;
				int compteurDeleteSession = 0;
				int compteurPrevious = compteurDelete;

				String auctionUrl = "http://localhost:8080/sabnzbd/api?mode=queue&output=json&apikey=3865b6afbae84de6a11e8509e5f09fae";
				CloseableHttpClient httpClient = HttpClients.createDefault();
				HttpGet request = new HttpGet(auctionUrl);
				CloseableHttpResponse response = httpClient.execute(request);
//				System.out.println("Response Code: " + response.getCode());
				String jsonResponse = EntityUtils.toString(response.getEntity());

				ObjectMapper mapper = new ObjectMapper();

				JsonNode rootNode = mapper.readTree(jsonResponse);
				JsonNode queueNode = rootNode.path("queue");
				JsonNode itemsNode = queueNode.path("slots");

				String id = "";
				for (JsonNode itemNode : itemsNode) {
					boolean testDelete = true;
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

					for (File fichierTreated : listeFilmsATraiter) {
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

							if (!nameTreated.toLowerCase().contains("book")&&!nameDownload.toLowerCase().contains("book")&&nameTreated.toLowerCase().contains(nameDownload.toLowerCase()) && yearDownload.toLowerCase().equals(yearTreated) && yearTreated != "" && nameTreated != "" && nameDownload != "" && yearDownload != "") {
								if (resolutionDownload <= resolutionTreated) {
									try {
										//								if (fichierTreated.getPath().toLowerCase().contains("treated")) {
										System.out.println("Resolution Equal Delete " + nameDownload + "    " + nameTreated + "    download " + compteurDownload + " /" + itemsNode.size() + "    treated " + compteurTreated + " /" + listeFilmsATraiter.size());

										auctionUrl = "http://localhost:8080/sabnzbd/api?mode=queue&name=delete&value=" + idTemp + "&output=json&apikey=3865b6afbae84de6a11e8509e5f09fae&nzbkey=90b006a9f0ad4ef493110e1c8e80393a";
										CloseableHttpClient httpClient2 = HttpClients.createDefault();
										HttpGet request2 = new HttpGet(auctionUrl);
										CloseableHttpResponse response2 = httpClient2.execute(request2);
										//										System.out.println("Response Code: " + response2.getCode());
										compteurDelete++;
										compteurDeleteSession++;
										System.out.println("Nb delete : " + compteurDelete);
										testDelete = false;
									} catch (Exception ex) {
										ex.printStackTrace();
									}
								}
							}
						}
					}
					if(!((nameTemp.toLowerCase().contains("web")&&nameTemp.toLowerCase().contains("2160"))||
							(nameTemp.toLowerCase().contains("web")&&nameTemp.toLowerCase().contains("1080"))||
							(nameTemp.toLowerCase().contains("web")&&nameTemp.toLowerCase().contains("720"))||
							(nameTemp.toLowerCase().contains("web")&&nameTemp.toLowerCase().contains("576"))||
							(nameTemp.toLowerCase().contains("web")&&nameTemp.toLowerCase().contains("480"))||
							(nameTemp.toLowerCase().contains("web")&&nameTemp.toLowerCase().contains("360"))||
							(nameTemp.toLowerCase().contains("blu")&&nameTemp.toLowerCase().contains("2160"))||
							(nameTemp.toLowerCase().contains("blu")&&nameTemp.toLowerCase().contains("1080"))||
							(nameTemp.toLowerCase().contains("blu")&&nameTemp.toLowerCase().contains("720"))||
							(nameTemp.toLowerCase().contains("blu")&&nameTemp.toLowerCase().contains("576"))||
							(nameTemp.toLowerCase().contains("blu")&&nameTemp.toLowerCase().contains("480"))||
							(nameTemp.toLowerCase().contains("blu")&&nameTemp.toLowerCase().contains("360"))||
							(nameTemp.toLowerCase().contains("remux")&&nameTemp.toLowerCase().contains("2160"))||
							(nameTemp.toLowerCase().contains("remux")&&nameTemp.toLowerCase().contains("1080"))||
							(nameTemp.toLowerCase().contains("remux")&&nameTemp.toLowerCase().contains("720"))||
							(nameTemp.toLowerCase().contains("remux")&&nameTemp.toLowerCase().contains("576"))||
							(nameTemp.toLowerCase().contains("remux")&&nameTemp.toLowerCase().contains("480"))||
							(nameTemp.toLowerCase().contains("remux")&&nameTemp.toLowerCase().contains("360"))||
							(nameTemp.toLowerCase().contains("dvd"))
					)
							&& !nameTemp.toLowerCase().contains("book")
					) {
						auctionUrl = "http://localhost:8080/sabnzbd/api?mode=queue&name=delete&value=" + idTemp + "&output=json&apikey=3865b6afbae84de6a11e8509e5f09fae&nzbkey=90b006a9f0ad4ef493110e1c8e80393a";
						CloseableHttpClient httpClient2 = HttpClients.createDefault();
						HttpGet request2 = new HttpGet(auctionUrl);
						CloseableHttpResponse response2 = httpClient2.execute(request2);
						System.out.println("Delete " + nameTemp);
						//						System.out.println("Response Code: " + response2.getCode());
						if(testDelete) {
							compteurDelete++;
							compteurDeleteSession++;
							System.out.println("Nb delete : " + compteurDelete + "    previous " + compteurPrevious + "    session "+compteurDeleteSession);
						}
					}

					if(itemNode.path("priority").asText().toLowerCase().contains("high")) {
						auctionUrl = "http://localhost:8080/sabnzbd/api?mode=queue&name=delete&value="+ idTemp +  "&apikey=3865b6afbae84de6a11e8509e5f09fae&nzbkey=90b006a9f0ad4ef493110e1c8e80393a";
						CloseableHttpClient httpClient2 = HttpClients.createDefault();
						HttpGet request2 = new HttpGet(auctionUrl);
						CloseableHttpResponse response2 = httpClient2.execute(request2);
					}

				}
				System.out.println("Nb delete : " + compteurDelete + "    previous " + compteurPrevious + "    session "+compteurDeleteSession);
				httpClient.close();
				deleteSeriesAlreadyDownloaded(listeSeriesATraiter);
				try {
					System.out.println("Wait");
					for (int i = 1; i < 5; i++) {
						Thread.sleep(60000);
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
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

	public void endDeleteAlreadyDownload() {
		System.exit(0);
	}

	private void deleteSeriesAlreadyDownloaded(HashSet<File> listeSeriesATraiter){
		try {
			int compteurDelete = 0;

			while (true) {

				int compteurDownload = 0;
				int compteurTreated = 0;
				int compteurDeleteSession = 0;
				int compteurPrevious = compteurDelete;

				String auctionUrl = "http://localhost:8080/sabnzbd/api?mode=queue&output=json&apikey=3865b6afbae84de6a11e8509e5f09fae";
				CloseableHttpClient httpClient = HttpClients.createDefault();
				HttpGet request = new HttpGet(auctionUrl);
				CloseableHttpResponse response = httpClient.execute(request);
//				System.out.println("Response Code: " + response.getCode());
				String jsonResponse = EntityUtils.toString(response.getEntity());

				ObjectMapper mapper = new ObjectMapper();

				JsonNode rootNode = mapper.readTree(jsonResponse);
				JsonNode queueNode = rootNode.path("queue");
				JsonNode itemsNode = queueNode.path("slots");

				String id = "";
				for (JsonNode itemNode : itemsNode) {
					boolean testDelete = true;
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
							Pattern p = Pattern.compile(".*([sS][0-9]+[xXeE][0-9]+).*");
							Matcher m = p.matcher(lineTemp.toLowerCase());

							if (m.matches()) {
								yearDownload = m.group(1);
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

					for (File fichierTreated : listeSeriesATraiter) {
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
									Pattern p = Pattern.compile(".*([sS][0-9]+[xXeE][0-9]+).*");
									Matcher m = p.matcher(lineTemp.toLowerCase());

									if (m.matches()) {
										yearTreated = m.group(1);
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

							if (!nameTreated.toLowerCase().contains("book")&&!nameDownload.toLowerCase().contains("book")&&nameTreated.toLowerCase().contains(nameDownload.toLowerCase()) && yearDownload.toLowerCase().equals(yearTreated) && yearTreated != "" && nameTreated != "" && nameDownload != "" && yearDownload != "") {
								if (resolutionDownload <= resolutionTreated) {
									try {
										//								if (fichierTreated.getPath().toLowerCase().contains("treated")) {
										System.out.println("Resolution Equal Delete " + nameDownload + "    " + nameTreated + "    download " + compteurDownload + " /" + itemsNode.size() + "    treated " + compteurTreated + " /" + listeSeriesATraiter.size());

										auctionUrl = "http://localhost:8080/sabnzbd/api?mode=queue&name=delete&value=" + idTemp + "&output=json&apikey=3865b6afbae84de6a11e8509e5f09fae&nzbkey=90b006a9f0ad4ef493110e1c8e80393a";
										CloseableHttpClient httpClient2 = HttpClients.createDefault();
										HttpGet request2 = new HttpGet(auctionUrl);
										CloseableHttpResponse response2 = httpClient2.execute(request2);
										//										System.out.println("Response Code: " + response2.getCode());
										compteurDelete++;
										compteurDeleteSession++;
										System.out.println("Nb delete : " + compteurDelete);
										testDelete = false;
									} catch (Exception ex) {
										ex.printStackTrace();
									}
								}
							}
						}
					}
				}
				System.out.println("Nb delete : " + compteurDelete + "    previous " + compteurPrevious + "    session "+compteurDeleteSession);
				httpClient.close();
			}
		} catch (Exception ex){
			ex.printStackTrace();
		}
	}

	public static void main(String[] args) {
		DeleteAlreadyDownload epguides = new DeleteAlreadyDownload();
		epguides.endDeleteAlreadyDownload();
	}

}
