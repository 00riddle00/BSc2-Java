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

public class FileOpener implements Runnable {

    private JFrame frame;
    private JTextPane textPane;
    private File file;
    private StyledDocument styledDoc;

    public FileOpener(JFrame frame, JTextPane textPane, File file) {
        this.frame = frame;
        this.textPane = textPane;
        this.file = file;
    }

    public void run() {
        try {
            Optional<String> ext = getFileExtension(this.file.getName());

            if (!(ext.equals(Optional.of("edt")))) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        JOptionPane.showMessageDialog(frame, "Try to save and open files with .edt extension");
                    }
                });
            }

            FileInputStream fileIn = new FileInputStream(this.file);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);

            this.styledDoc = (DefaultStyledDocument) objectIn.readObject();
            objectIn.close();
        } catch (FileNotFoundException e) {
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    JOptionPane.showMessageDialog(frame, "File not found");
                }
            });
        } catch (ClassNotFoundException e) {
            System.out.println("[Error]: Cannot read from a file");
            e.printStackTrace();
            System.exit(1);
        } catch (IOException e) {
            System.out.println("[Error]: Cannot open file '" + file + "'");
            e.printStackTrace();
            System.exit(1);
        }

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                textPane.setDocument(styledDoc);
                addFocusPropertyToImages(styledDoc);
                frame.setTitle("txedt - " + file.getName());
            }
        });
    }

    private void addFocusPropertyToImages(StyledDocument doc) {
        ElementIterator iterator = new ElementIterator(doc);
        Element element;

        while ((element = iterator.next()) != null) {
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

    private Optional<String> getFileExtension(String filename) {
        return Optional.ofNullable(filename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(filename.lastIndexOf(".") + 1));
    }
}
