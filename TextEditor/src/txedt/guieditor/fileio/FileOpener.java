package txedt.guieditor.fileio;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Element;
import javax.swing.text.ElementIterator;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Optional;

/**
 * Opens a file as a styled document
 * Works in a separate thread.
 */
public class FileOpener implements Runnable {

    private JFrame frame;
    private JTextPane textPane;
    private File file;
    private StyledDocument styledDoc;

    /**
     * @param frame    the only frame the GUI is displayed in (containing a menu bar, a toolbar, and a text pane)
     * @param textPane the text pane where the document is contained in and displayed in
     * @param file     the file object indicating the file to be opened
     */
    public FileOpener(JFrame frame, JTextPane textPane, File file) {
        this.frame = frame;
        this.textPane = textPane;
        this.file = file;
    }

    /**
     * Opens the file specified as a styled document object.
     * <p>
     * Checks for the file extension, and if it is not .edt, shows a message with the
     * recommendation no using files with this extension (in the later version this will
     * be a strict requirement). Throws correspoding exceptions, if there are problems
     * reading from a file, and shows the error message in GUI.
     * <p>
     * Finally, after the file is read, the text pane is set to the new document object,
     * the frame's title is edited to include the opened filename, and for all the images
     * in the document the focus handler is added.
     * <p>
     * Showing GUI messages, changing frame's title and adding focus handler to images is
     * done from the Swing's invokeLater() method, which passes the code for the Event
     * Dispatch Thread to run.
     *
     * @throws FileNotFoundException  if the file specified does not exist
     * @throws ClassNotFoundException if there are problems reading from a file via object input stream
     * @throws IOException            if other problems occur while reading a file
     */
    public void run() {
        try {
            Optional<String> ext = getFileExtension(this.file.getName());

            if (!(ext.equals(Optional.of("edt")))) {
                SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(frame, "Try to save and open files with .edt extension"));
            }

            FileInputStream fileIn = new FileInputStream(this.file);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);

            this.styledDoc = (DefaultStyledDocument) objectIn.readObject();
            objectIn.close();
        } catch (FileNotFoundException e) {
            SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(frame, "File not found"));
        } catch (ClassNotFoundException e) {
            System.out.println("[Error]: Cannot read from a file");
            e.printStackTrace();
            System.exit(1);
        } catch (IOException e) {
            System.out.println("[Error]: Cannot open file '" + file + "'");
            e.printStackTrace();
            System.exit(1);
        }

        SwingUtilities.invokeLater(() -> {
            textPane.setDocument(styledDoc);
            addFocusPropertyToImages(styledDoc);
            frame.setTitle("txedt - " + file.getName());
        });
    }

    /**
     * Uses an iterator to iterate over all the images in the newly
     * opened document and adds a focus handler to each image.
     */
    private void addFocusPropertyToImages(StyledDocument doc) {
        ElementIterator it = new ElementIterator(doc);
        Element element;

        while ((element = it.next()) != null) {
            AttributeSet attributes = element.getAttributes();

            if (attributes.containsAttribute(AbstractDocument.ElementNameAttribute, StyleConstants.ComponentElementName)) {
                JButton picButton = (JButton) StyleConstants.getComponent(attributes);
                picButton.addFocusListener(new FocusListener() {

                    public void focusGained(FocusEvent event) {
                        JButton button = (JButton) event.getComponent();
                        button.setBorder(new LineBorder(Color.GRAY));
                    }

                    public void focusLost(FocusEvent event) {
                        ((JButton) event.getComponent()).setBorder(new LineBorder(Color.WHITE));
                    }
                });
            }
        }
    }

    /**
     * Gets the extension of a file, by using and functional programming
     * elements - Optional class with filter and map actions.
     */
    private Optional<String> getFileExtension(String filename) {
        return Optional.ofNullable(filename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(filename.lastIndexOf(".") + 1));
    }
}
