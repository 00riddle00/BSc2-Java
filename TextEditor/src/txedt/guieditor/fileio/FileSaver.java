package txedt.guieditor.fileio;

import javax.swing.JFrame;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.text.DefaultStyledDocument;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class FileSaver implements Runnable {

    JFrame frame;
    JTextPane textPane;
    File file;

    public FileSaver(JFrame frame, JTextPane textPane, File file) {
        this.textPane = textPane;
        this.file = file;
        this.frame = frame;
    }

    public void run() {
        try {
            FileOutputStream fileOut = new FileOutputStream(this.file + ".edt");
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject((DefaultStyledDocument) textPane.getDocument());
            objectOut.flush();
            objectOut.close();
        } catch (IOException e) {
            System.out.println("[Error]: Cannot write document to a file");
            e.printStackTrace();
            System.exit(1);
        }

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                frame.setTitle("txedt - " + file.getName());
            }
        });
    }
}

