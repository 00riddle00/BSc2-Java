package txedt.guieditor.fileio;

import javax.swing.JFrame;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.text.DefaultStyledDocument;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * Saves the current document being edited * to a file as a styled document
 * Works in a separate thread.
 */
public class FileSaver implements Runnable {

    JFrame frame;
    JTextPane textPane;
    File file;

    /**
     * @param frame    the only frame the GUI is displayed in (containing a menu bar, a toolbar, and a text pane)
     * @param textPane the text pane where the document is contained in and displayed in
     * @param file     the file object indicating the file the document should be saved to
     */
    public FileSaver(JFrame frame, JTextPane textPane, File file) {
        this.textPane = textPane;
        this.file = file;
        this.frame = frame;
    }

    /**
     * Gets the document object that is associated with the text pane,
     * casts it to a styled document object and saves it to a file using
     * object output stream. Also, adds the .edt extension to the file.
     * <p>
     * Then, changes the title of the frame to represent the newly saved
     * filename. This is done via Swing invokeLater() method, which passes
     * the code for the Event Dispatch Thread to run.
     *
     * @throws IOException if there is a problem writing the document to a file
     */
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

        SwingUtilities.invokeLater(() -> frame.setTitle("txedt - " + file.getName()));
    }
}

