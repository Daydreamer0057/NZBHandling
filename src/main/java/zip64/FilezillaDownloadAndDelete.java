package zip64;

import com.jcraft.jsch.*;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;

public class FilezillaDownloadAndDelete {
    public static String lineFiles = "";

    public FilezillaDownloadAndDelete() {
        try {
            String[] arrayCat = {"Books","Films","Games","Series","Udemy"};

            HashSet<String> listFiles  = new HashSet<>();

            final Session session = new JSch().getSession("daydreamer057", "skyron.usbx.me", 22);
            session.setPassword("Victordavion3062!");
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();

            for(int i=0;i<5;i++) {
                int finalI = i;
                final ChannelExec channelExec = (ChannelExec) session.openChannel("exec");;
                System.out.println(Thread.activeCount());
                Runnable runnable = () -> {
                    try {
                        String lineTemp = "";
                        int p = finalI;
                        channelExec.setCommand("find ~/downloads/" + arrayCat[p] + " -type f -name '*.*' -print");

                        channelExec.connect(60000);

                        byte[] buffer = new byte[1024];

                        InputStream in = channelExec.getInputStream();
                        String line2 = "";
                        while (true) {
                            while (in.available() > 0) {
                                int l = in.read(buffer, 0, 1024);
                                if (l < 0) {
                                    break;
                                }
                                line2 = new String(buffer, 0, l);
                                lineTemp += line2;
//                                System.out.println(line2);
                            }

                            if (line2.contains("logout")) {
                                break;
                            }

                            if (channelExec.isClosed()) {
                                break;
                            }
                            try {
                                Thread.sleep(1000);
                            } catch (Exception ee) {
                            }
                        }


                        while (channelExec.isConnected()) {
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException ex) {
                                ex.printStackTrace();
                            }
                        }
                        lineFiles += lineTemp;
                    } catch (Exception e) {
                        System.out.println("Error while reading channel output: " + e);
                    }
                };
                new Thread(runnable).start();
            }

            while(Thread.activeCount()>3){
                System.out.println("Threads "+Thread.activeCount());
                Thread.sleep(1000);
            }

            int compteur = 0;
            String[] arrayLine = lineFiles.split("\\n");
            for(String lineTemp : arrayLine) {
                System.out.println("Compteur "+compteur+" / "+arrayLine.length);
                compteur++;

                Runnable runnable = () -> {
//                    ChannelSftp channelSftp = (ChannelSftp) session.openChannel("sftp");
//                    channelSftp.connect(60000);
                    if(lineTemp.length()>3&&lineTemp.charAt(lineTemp.length()-4)=='.'&&!lineTemp.toLowerCase().contains("!qb")) {
                        try {
                            String[] partCd = lineTemp.split("/");
                            String part = partCd[4] + "/" + partCd[5];
                            FileUtils.forceMkdir(new File("e:/test/Ultra/" + part));
//                            channelSftp.get(lineTemp, "e:/test/Ultra/" + part + "/" + partCd[partCd.length - 1], null, ChannelSftp.RESUME);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                };
                new Thread(runnable).start();

                while(Thread.activeCount()>20){
                    Thread.sleep(100);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public HashSet<String> sortLine(String[] arrayLine){
        HashMap<String, HashSet<String>> map = new HashMap<>();

        for(String lineTemp : arrayLine){
            String[] parts = lineTemp.split("/");

            HashSet<String> listString = new HashSet<>();

            String mapString = "";
            String deleteDirectory = "";

            //							System.out.println("Clipboardutf "+clipBoardUTF+"    "+parts.length);
            if (lineTemp.toLowerCase().contains("/games")||lineTemp.toLowerCase().contains("/udemy")||lineTemp.toLowerCase().contains("/books")) {
                mapString = parts[5] + "/" + parts[6] + "/" + parts[7] + "/" + parts[8];

            } else {
                mapString = parts[5] + "/" + parts[6] + "/" + parts[7];
            }

            listString = map.get(mapString);

            if (listString != null) {
                listString.add(parts[parts.length - 1]);
            } else {
                listString = new HashSet<>();
                listString.add(parts[parts.length - 1]);
            }

            map.put(mapString, listString);
        }

        HashSet<String> listDelete = new HashSet<>();
        for(String key : map.keySet()) {
            HashSet<String> list = map.get(key);

            boolean test = false;
            if (list != null) {
                for (String fichier : list){
                    if(fichier.toLowerCase().contains(".!qb")){
                        test = true;
                        break;
                    }
                }
                if(!test){
                    listDelete.add(key);
                }
            }
        }
        return listDelete;
    }

    public static void main(String[] args) {
        FilezillaDownloadAndDelete epguides = new FilezillaDownloadAndDelete();
    }
}

