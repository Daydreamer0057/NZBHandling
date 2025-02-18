package torrent;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jcraft.jsch.*;
import gui.SFTPMultiDownloadGUI;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;

public class DownloadSFTPTorrent {
    private static final String BASE_URL = "https://daydreamer057.skyron.usbx.me/qbittorrent/"; // Replace with your qBittorrent Web UI URL
    private static final String USERNAME = "daydreamer057"; // Replace with your username
    private static final String PASSWORD = "Victordavion3062!"; // Replace with your password
    private static int compteurThread = 0;
    private static int compteurTrack = 0;
    private static long totalDownloaded = 0;

    public DownloadSFTPTorrent() throws Exception {
            SFTPMultiDownloadGUI frame = new SFTPMultiDownloadGUI(10);
            frame.setVisible(true);

        String[] listThreadsAvailable = new String[10];
        while (true) {

            PrintWriter pw = new PrintWriter(new FileOutputStream(new File("c:/log/error.txt"), true /* append = true */));

            while (new File("z://").getFreeSpace() < 2000000000000L) {
                System.out.println("Free space " + (new File("z://").getFreeSpace() / 1000000000L));
                Thread.sleep(1000);
            }

//            String cookie = authenticate();
//
//            List<TorrentInfo> listTorrentInfo = getAllTorrentsName(cookie);
//
//            HashSet<Torrents> listTorrents = new HashSet<>();
//
//            for (TorrentInfo torrentInfoTemp : listTorrentInfo) {
//                List<TorrentFile> listTorrentFile = getAllTorrentFiles(cookie, torrentInfoTemp.hash);
//
//                if (listTorrentFile != null) {
//                    for (TorrentFile torrentFileTemp : listTorrentFile) {
//                        listTorrents.add(new Torrents(torrentFileTemp.index,
//                                torrentFileTemp.name.substring(torrentFileTemp.name.lastIndexOf('/') + 1, torrentFileTemp.name.length()).toLowerCase(),
//                                torrentInfoTemp.hash));
//                    }
//                }
//            }

            HashSet<String> listDownload = new HashSet<>();

            listDownload = getAllFiles(listDownload);

            for (String line : listDownload) {
                String eraseFolder = line.substring(0, line.lastIndexOf('/'));
                String eraseFile = line.substring(line.lastIndexOf('/') + 1);

                while (Thread.activeCount() > 10) {
                    Thread.sleep(1000);
                }

                new Thread(() -> {
                    compteurThread++;

                    boolean connected = false;

                    int fileIndexTemp = -1;
                    for(int i=0;i<10;i++){
                        if(listThreadsAvailable[i].isEmpty()){
                            fileIndexTemp = i;
                        }
                    }

                    final int fileIndex = fileIndexTemp;

                    while (!connected) {
                        try {
                            Session session = new JSch().getSession("daydreamer057", "skyron.usbx.me", 22);
                            session.setPassword("Victordavion3062!");
                            session.setConfig("StrictHostKeyChecking", "no");

                            session.connect();
                            session.setTimeout(30000); // 10 seconds timeout

                            connected = true;

                            Channel channel = session.openChannel("sftp");
                            ChannelSftp channelSftp = (ChannelSftp) channel;
                            channelSftp.connect(60000);

                            String remoteFile = eraseFolder + "/" + eraseFile;
                            String localFile = "z://test/ultracc/";

                            String[] dirParts = eraseFolder.substring(eraseFolder.indexOf("downloads") + 10).split("/");
                            for (String dirPart : dirParts) {
                                localFile += dirPart + "/";
                                FileUtils.forceMkdir(new File(localFile.substring(0, localFile.length() - 1)));
                            }
                            localFile += eraseFile;

                            final long fileSize = channelSftp.stat(remoteFile).getSize();


                            // Create progress monitor
                            SftpProgressMonitor monitor = new SftpProgressMonitor() {
                                private long transferred = 0;
                                private long totalSize = fileSize;

                                @Override
                                public void init(int op, String src, String dest, long max) {
                                    System.out.println("⬇️ Downloading " + remoteFile);
                                }

                                @Override
                                public boolean count(long count) {
                                    transferred += count;
                                    int progress = (int) ((transferred * 100) / totalSize);
                                    frame.updateProgress(fileIndex, progress, remoteFile);
                                    return true; // Continue download
                                }

                                @Override
                                public void end() {
                                    frame.updateProgress(fileIndex, 100, "✅ " + remoteFile + " Done!");
                                }
                            };

                            channelSftp.get(remoteFile, localFile, monitor, ChannelSftp.RESUME);

                            compteurTrack++;

                            totalDownloaded += fileSize;
                            System.out.println("Total downloaded: " + (totalDownloaded/1000000));

//                            System.out.println("Downloaded " + remoteFile);
//                            System.out.println("Count " + compteurTrack + "    " + "📁 File Size: " + fileSize + " mb");

                            channelSftp.rm(remoteFile);

//                            Torrents torrent = torrentContainsName(listTorrents, eraseFile);
//
//                            if (torrent == null) {
//                                torrent = torrentContainsName(listTorrents, eraseFile.toLowerCase());
//                            }
//                            if (torrent != null) {
//                                try {
//                                    boolean testRemoved = deselectFile(cookie, torrent.hash, torrent.index);
//                                } catch (Exception ex) {
////                                            System.out.println("File not found " + torrent.name + "    " + eraseFolder + "    " + eraseFile);
//                                }
//                            }

                            channelSftp.exit();
                            session.disconnect();
                            compteurThread--;
                            listThreadsAvailable[fileIndex] = "";
                            frame.resetProgressBar(fileIndex);
                        } catch (JSchException e) {
                            connected = false;
//                                    e.printStackTrace();
                            if (e.getMessage().contains("timeout")) {
                                System.err.println("Timeout...Session");
                            }
                        } catch (SftpException e) {
                            if (!e.getMessage().toLowerCase().contains("failed to resume") && !e.getMessage().toLowerCase().contains("no such file")) {
//                                        e.printStackTrace();
                                if (e.getMessage().toLowerCase().contains("inputstream is closed") || e.getMessage().toLowerCase().contains("timeout")) {
                                    connected = false;
                                }
                            }
                        } catch (IOException e) {
                            connected = false;
//                                    e.printStackTrace();
                        }
                    }
//                            session.disconnect();

                }).start();

            }



            //==========================================================================================================

            Session session = new JSch().getSession("daydreamer057", "skyron.usbx.me", 22);
            session.setPassword("Victordavion3062!");
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();

            ChannelExec channel = (ChannelExec) session.openChannel("exec");

            channel.setCommand("find /home35/daydreamer057/downloads/ -empty -type d -print -delete");

            channel.connect(60000);

            while (channel.isConnected()) {
                Thread.sleep(100);
            }
            int status = channel.getExitStatus();

            /**
             * Create TV Show directory if disappeared
             */
            channel = (ChannelExec) session.openChannel("exec");

            channel.setCommand("mkdir \"/home35/daydreamer057/downloads/TV Show\"");

            channel.connect(60000);

            while (channel.isConnected()) {
                Thread.sleep(100);
            }

            status = channel.getExitStatus();

            /**
             * Create Anime directory if disappeared
             */
            channel = (ChannelExec) session.openChannel("exec");

            channel.setCommand("mkdir \"/home35/daydreamer057/downloads/Anime\"");

            channel.connect(60000);

            while (channel.isConnected()) {
                Thread.sleep(100);
            }

            status = channel.getExitStatus();

            /**
             * Create Movies directory if disappeared
             */
            channel = (ChannelExec) session.openChannel("exec");

            channel.setCommand("mkdir \"/home35/daydreamer057/downloads/Movies\"");

            channel.connect(60000);

            while (channel.isConnected()) {
                Thread.sleep(100);
            }

            status = channel.getExitStatus();

            /**
             * Create Sitcoms directory if disappeared
             */
            channel = (ChannelExec) session.openChannel("exec");

            channel.setCommand("mkdir \"/home35/daydreamer057/downloads/Sitcoms\"");

            channel.connect(60000);

            while (channel.isConnected()) {
                Thread.sleep(100);
            }

            status = channel.getExitStatus();

            /**
             * Create Series directory if disappeared
             */
            channel = (ChannelExec) session.openChannel("exec");

            channel.setCommand("mkdir \"/home35/daydreamer057/downloads/Series\"");

            channel.connect(60000);

            while (channel.isConnected()) {
                Thread.sleep(100);
            }

            status = channel.getExitStatus();

            /**
             * Create Documentaires directory if disappeared
             */
            channel = (ChannelExec) session.openChannel("exec");

            channel.setCommand("mkdir \"/home35/daydreamer057/downloads/Documentaires\"");

            channel.connect(60000);

            while (channel.isConnected()) {
                Thread.sleep(100);
            }

            status = channel.getExitStatus();


            System.out.println("Wait");
            for(int i=1;i<60;i++) {
                Thread.sleep(60000);
                System.out.println(60-i);
            }
        }
    }


    public int runFilezilla() {
        try {
            Process process = Runtime.getRuntime().exec("c://AU3/FileZilla.exe");
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

    private static String authenticate() throws Exception {
        URL url = new URL(BASE_URL + "api/v2/auth/login");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);

        try (OutputStream os = conn.getOutputStream()) {
            String body = "username=" + URLEncoder.encode(USERNAME, "UTF-8") +
                    "&password=" + URLEncoder.encode(PASSWORD, "UTF-8");
            os.write(body.getBytes());
        }

        if (conn.getResponseCode() == 200) {
            return conn.getHeaderField("Set-Cookie");
        } else {
            throw new RuntimeException("Failed to authenticate: " + conn.getResponseMessage());
        }
    }

    private static List<TorrentInfo> getAllTorrentsName(String cookie) throws Exception {
        URL url = new URL(BASE_URL + "api/v2/torrents/info");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Cookie", cookie);

        if (conn.getResponseCode() == 200) {
            try (InputStream inputStream = conn.getInputStream()) {
                ObjectMapper mapper = new ObjectMapper();
                return mapper.readValue(inputStream, new TypeReference<List<TorrentInfo>>() {});
            }
        } else {
            throw new RuntimeException("Failed to fetch torrent info: " + conn.getResponseMessage());
        }
    }

    // Fetch file indices for a specific torrent hash
    private static List<TorrentFile> getAllTorrentFiles(String cookie, String torrentHash) throws Exception {
        URL url = new URL(BASE_URL + "api/v2/torrents/files?hash=" + URLEncoder.encode(torrentHash, "UTF-8"));
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Cookie", cookie);

        if (conn.getResponseCode() == 200) {
            try (InputStream inputStream = conn.getInputStream()) {
                ObjectMapper mapper = new ObjectMapper();
                return mapper.readValue(inputStream, new TypeReference<List<TorrentFile>>() {});

            }
        } else {
            System.out.println("Failed to fetch torrent files: " + conn.getResponseMessage());
            return null;
        }
    }

    public Torrents torrentContainsName(final HashSet<Torrents> list, final String name){
        Optional<Torrents> result = list.stream()
                .filter(o -> o.name.toLowerCase().contains(name.toLowerCase()))
                .findFirst();

        if (result.isPresent()) {
            Torrents obj = result.get();
            return obj;
        } else {
            return null;
        }
    }

    // Deselect a file in the torrent
    private static boolean deselectFile(String cookie, String hash, int fileIndex) throws Exception {
        URL url = new URL(BASE_URL + "api/v2/torrents/filePrio");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Cookie", cookie);
        conn.setDoOutput(true);

        try (OutputStream os = conn.getOutputStream()) {
            String body = "hash=" + URLEncoder.encode(hash, "UTF-8") +
                    "&id=" + fileIndex +
                    "&priority=0";
            os.write(body.getBytes());
        }

        if (conn.getResponseCode() == 200) {
            System.out.println("File deselected successfully.");
            return true;
        } else {
            throw new RuntimeException("Failed to deselect file: " + conn.getResponseMessage());
        }
    }

    public HashSet<String> getAllFiles(HashSet<String> listDownlad){
        HashSet<String> listDirectories = new HashSet<>();
        while(true) {
            Session sessionLs= null;
            Channel channelLs= null;
            ChannelSftp sftpChannel = null;
            try {
                sessionLs = new JSch().getSession("daydreamer057", "skyron.usbx.me", 22);
                sessionLs.setPassword("Victordavion3062!");
                sessionLs.setConfig("StrictHostKeyChecking", "no");

                sessionLs.connect();
                sessionLs.setTimeout(30000); // 10 seconds timeout

                channelLs = sessionLs.openChannel("sftp");
//                                            channel.connect();
                sftpChannel = (ChannelSftp) channelLs;
                sftpChannel.connect(300000);

                sftpChannel.cd("/home35/daydreamer057/downloads");
                Vector<ChannelSftp.LsEntry> files = sftpChannel.ls(".");
                for (ChannelSftp.LsEntry file : files) {
                    String fileName = file.getFilename();

                    // Skip "." and ".." directories
                    if (!fileName.equals(".") && !fileName.equals("..")) {
                        if (file.getAttrs().isDir()) {
                            listDirectories.add("/home35/daydreamer057/downloads/" + fileName);
                        } else {
                            if (!fileName.toLowerCase().endsWith("!qb") && !fileName.toLowerCase().endsWith(".parts")) {
                                listDownlad.add("/home35/daydreamer057/downloads/" + fileName);
                            }
                        }
                    }
                }
            } catch(Exception ex) {
                ex.printStackTrace();
            }

            while (!listDirectories.isEmpty()) {
                try {
                    sessionLs = new JSch().getSession("daydreamer057", "skyron.usbx.me", 22);
                    sessionLs.setPassword("Victordavion3062!");
                    sessionLs.setConfig("StrictHostKeyChecking", "no");

                    sessionLs.connect();
                    sessionLs.setTimeout(30000); // 10 seconds timeout

                    channelLs = sessionLs.openChannel("sftp");
//                                            channel.connect();
                    sftpChannel = (ChannelSftp) channelLs;
                    sftpChannel.connect(300000);

                    String nameTemp = listDirectories.toArray()[0].toString();

                    sftpChannel.cd(nameTemp);

                    Vector<ChannelSftp.LsEntry> listFiles = sftpChannel.ls(".");
                    for (ChannelSftp.LsEntry file : listFiles) {
                        String fileNameTemp = file.getFilename();

                        // Skip "." and ".." directories
                        if (!fileNameTemp.equals(".") && !fileNameTemp.equals("..")) {
                            if (file.getAttrs().isDir()) {
                                listDirectories.add(nameTemp + "/" + fileNameTemp);
                            } else {
                                if (!fileNameTemp.toLowerCase().endsWith("!qb") && !fileNameTemp.toLowerCase().endsWith(".parts")) {
                                    listDownlad.add(nameTemp + "/" + fileNameTemp);
                                }
                            }
                        }
                    }
                    listDirectories.remove(nameTemp);

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            return listDownlad;
        }
    }

    public static void main(String[] args) {
        try {
            DownloadSFTPTorrent epguides = new DownloadSFTPTorrent();
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    // TorrentInfo class for JSON mapping
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class TorrentInfo {
        public int added_on;
        public Object amount_left;
        public boolean auto_tmm;
        public double availability;
        public String category;
        public Object completed;
        public int completion_on;
        public String content_path;
        public int dl_limit;
        public int dlspeed;
        public String download_path;
        public Object downloaded;
        public Object downloaded_session;
        public long eta;
        public boolean f_l_piece_prio;
        public boolean force_start;
        public String hash;
        public String infohash_v1;
        public String infohash_v2;
        public int last_activity;
        public String magnet_uri;
        public int max_ratio;
        public int max_seeding_time;
        public String name;
        public int num_complete;
        public int num_incomplete;
        public int num_leechs;
        public int num_seeds;
        public int priority;
        public double progress;
        public double ratio;
        public int ratio_limit;
        public String save_path;
        public int seeding_time;
        public int seeding_time_limit;
        public int seen_complete;
        public boolean seq_dl;
        public Object size;
        public String state;
        public boolean super_seeding;
        public String tags;
        public int time_active;
        public Object total_size;
        public String tracker;
        public int trackers_count;
        public int up_limit;
        public Object uploaded;
        public Object uploaded_session;
        public int upspeed;

    }

    // TorrentFile class for JSON mapping
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class TorrentFile {
        public int availability;
        public int index;
        public boolean is_seed;
        public String name;
        public ArrayList<Integer> piece_range;
        public int priority;
        public double progress;
        public Object size;

    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    static class Torrents {
        public int index; // File index
        public String name; // File name
        public String hash;

        public Torrents(int index, String name, String hash){
            this.index = index;
            this.name = name;
            this.hash = hash;
        }
    }
}
