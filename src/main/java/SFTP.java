import com.jcraft.jsch.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Vector;

public class SFTP {

	public SFTP() {
		try{
			JSch jsch = new JSch();
			Session session = null;
			try {
				session = jsch.getSession("daydreamer057", "169.150.223.207", 22);
				session.setConfig("StrictHostKeyChecking", "no");
				session.setPassword("Victordavion3062!");
				session.connect();
				System.out.println("Connected to session successfully");
				Channel channel = session.openChannel("sftp");
				channel.connect();
				System.out.println("Connected to Channel successfully");
				ChannelSftp sftpChannel = (ChannelSftp) channel;

				// this will read file with the name test.txt and write to remote directory
				Vector<ChannelSftp.LsEntry> listFilms = sftpChannel.ls("/home35/daydreamer057/downloads/Films");
				Vector<ChannelSftp.LsEntry> listSeries = sftpChannel.ls("/home35/daydreamer057/downloads/Series");
				listFilms.addAll(listSeries);

				for(ChannelSftp.LsEntry ls : listFilms) {
					if(!ls.getAttrs().isDir()) {
						File fichier = new File("c://");
						if (ls.getFilename().toLowerCase().contains("films")) {
							fichier = new File("z://test/film temp/" + ls.getFilename());
							InputStream is = sftpChannel.getInputStream();
						} else {
							fichier = new File("z://test/film temp/" + ls.getFilename());
						}
					}
				}
				sftpChannel.cd("/root/usr/path");
				File f = new File("test.txt");
				sftpChannel.put(new FileInputStream(f), f.getName()); // here you can also change the target file name by replacing f.getName() with your choice of name

				sftpChannel.exit();
				session.disconnect();
			} catch (JSchException e) {
				e.printStackTrace();
			}

		} catch (Exception ex){
			ex.printStackTrace();
		}
	}

	private ChannelSftp setupJsch() throws JSchException {
		JSch jsch = new JSch();
		jsch.setKnownHosts("/Users/john/.ssh/known_hosts");
		Session jschSession = jsch.getSession("daydreamer057", "skyron.usbx.me");
		jschSession.setPassword("Victordavion3062!");
		jschSession.connect();
		return (ChannelSftp) jschSession.openChannel("sftp");
	}

	public static void main(String[] args) {
		SFTP populate = new SFTP();

	}

}
