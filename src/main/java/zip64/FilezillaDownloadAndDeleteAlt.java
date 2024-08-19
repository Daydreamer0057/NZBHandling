package zip64;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;

public class FilezillaDownloadAndDeleteAlt {
    public FilezillaDownloadAndDeleteAlt() {

        while(true) {
            try {
                int resultCode = runFilezilla();

                //				while(true){
                //					System.out.println("Clipboard Get");
                //					clipBoard = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
                //					if(clipBoard.equals("")) {
                //						int clipboardTest = runFilezillaClipboard();
                //						System.out.println("Clipboard Rerun");
                //					} else {
                //						break;
                //					}
                //				}
                //				System.out.println("Clipboard Received");

                HashMap<String, HashSet<String>> map = new HashMap<>();
                HashMap<String, String> mapCd = new HashMap<>();

                FileReader fr = new FileReader("y:/temp/listeFilezilla.txt");
                BufferedReader br = new BufferedReader(fr);

                String line = "";
                int compteurLine = 0;
                while (line != null) {
                    if (line != null) {
                        line = br.readLine();

                        if(line!=null){
                            String clipBoardUTF = java.net.URLDecoder.decode(line, "UTF-8");

                            //							clipBoardUTF = clipBoardUTF.substring(clipBoardUTF.indexOf("home35"), clipBoardUTF.length());

                            String[] parts = clipBoardUTF.split("/");

                            HashSet<String> listString = new HashSet<>();

                            String mapString = "";
                            String deleteDirectory = "";

                            //							System.out.println("Clipboardutf "+clipBoardUTF+"    "+parts.length);

                            if(parts[6].toLowerCase().contains("books")) {
                                mapString = parts[8];
                                mapCd.put(mapString,parts[5] + "/" + parts[6] + "/" + parts[7]);
                            }
                            if(parts[6].toLowerCase().contains("films")) {
                                mapString = parts[7];
                                mapCd.put(mapString,parts[5] + "/" + parts[6]);
                            }
                            if(parts[6].toLowerCase().contains("games")) {
                                mapString = parts[9];
                                mapCd.put(mapString,parts[5] + "/" + parts[6] + "/" + parts[7] + "/" + parts[8]);
                            }
                            if(parts[6].toLowerCase().contains("series")) {
                                mapString = parts[7];
                                mapCd.put(mapString,parts[5] + "/" + parts[6]);
                            }
                            if(parts[6].toLowerCase().contains("udemy")) {
                                mapString = parts[8];
                                mapCd.put(mapString,parts[5] + "/" + parts[6] + "/" + parts[7]);
                            }

                            listString = map.get(mapString);

                            if (listString != null) {
                                listString.add(parts[parts.length-1]);
                            } else {
                                listString = new HashSet<>();
                                listString.add(parts[parts.length-1]);
                            }

                            map.put(mapString, listString);
                            compteurLine++;
                        }
                    }
                }

                br.close();
                fr.close();

                System.out.println("Directories sorted");

                HashMap<String, String> listDelete = new HashMap<>();
                for(String key : map.keySet()) {
                    if (!key.equals("")) {
                        HashSet<String> list = map.get(key);

                        boolean test = false;
                        if (list != null) {
                            for (String fichier : list) {
                                if (fichier.toLowerCase().contains(".!qb")) {
                                    test = true;
                                    break;
                                }
                            }
                            if (!test) {
                                listDelete.put(key,mapCd.get(key));
                            }
                        }
                    }
                }

                System.out.println("qb verified");



                final Session session = new JSch().getSession("daydreamer057", "skyron.usbx.me", 22);
                session.setPassword("Victordavion3062!");
                session.setConfig("StrictHostKeyChecking", "no");
                session.connect();

                int compteur = 0;
                for(String key : listDelete.keySet()) {
                    try {
                        System.out.println(compteur+" / "+listDelete.size());
                        compteur++;

                        String remove = key;

                        System.out.println("Debut Thread "+remove);

                        ChannelExec channel = (ChannelExec) session.openChannel("exec");

                        String cd = listDelete.get(key);
                        cd = cd.replace(" ","\\ ");
//                            cd += "/";

                        channel.setCommand("cd ~/"+cd+";"+"rm -r \""+remove+"\"");
                        //							channel.setCommand("find . -type d -name '*"+deleteEnd+"*' -print -exec rm -rv {} +");

                        channel.connect(60000);

                        while (channel.isConnected()) {
                            Thread.sleep(100);
                        }
                        System.out.println("Exit status "+channel.getExitStatus());
                        System.out.println("Fin Thread "+remove);
                    } catch(Exception ex) {
                        ex.printStackTrace();
                    }
                }
                System.out.println("Wait");
                int compteurTimer = 0;
                for(int i=1;i<60;i++) {
                    Thread.sleep(60000);
                    System.out.println(60-i);
                }
            } catch(Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public int runFilezilla() {
        try {
            Process process = Runtime.getRuntime().exec("c://AU3_2/FileZilla.exe");
            // Don't forget to close all streams!
            process.getErrorStream().close();
            process.getInputStream().close();
            process.getOutputStream().close();

            return process.waitFor();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    public int runFilezillaClipboard() {
        try {
            Process process = Runtime.getRuntime().exec("c://AU3_2/FileZilla_Clipboard.exe");
            // Don't forget to close all streams!
            process.getErrorStream().close();
            process.getInputStream().close();
            process.getOutputStream().close();

            return process.waitFor();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    public static void main(String[] args) {
        FilezillaDownloadAndDeleteAlt epguides = new FilezillaDownloadAndDeleteAlt();
    }
}

