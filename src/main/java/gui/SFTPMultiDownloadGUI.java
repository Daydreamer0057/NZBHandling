package gui;

import javax.swing.*;
import java.awt.*;

public class SFTPMultiDownloadGUI extends JFrame {
    JProgressBar[] progressBars = null;
    JLabel[] labels = null;

    public SFTPMultiDownloadGUI(int nbFrames) {
        progressBars = new JProgressBar[nbFrames];
        labels = new JLabel[nbFrames];
        setTitle("Multi-File SFTP Download Progress");
        setSize(500, 400);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(nbFrames, 2, 10, 10));

        // Initialize labels and progress bars
        for (int i = 0; i < nbFrames; i++) {
            labels[i] = new JLabel("Waiting...");
            progressBars[i] = new JProgressBar(0, 100);
            progressBars[i].setStringPainted(true);

            add(labels[i]);
            add(progressBars[i]);
        }
    }

    public void resetProgressBar(int fileIndex){
        SwingUtilities.invokeLater(() -> {
            labels[fileIndex].setText("");
            progressBars[fileIndex].setValue(0);
        });
    }

    public void updateProgress(int fileIndex, int progress, String fileName) {
        SwingUtilities.invokeLater(() -> {
            labels[fileIndex].setText(fileName);
            progressBars[fileIndex].setValue(progress);
        });
    }
}
