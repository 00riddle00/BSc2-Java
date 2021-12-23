package texedit.main.gui;

import java.awt.*;
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;


public class GraphicalEditor {

    private JFrame frame;
    private JTextPane editor;

    private static final String MAIN_TITLE = "EDT";
    private static final String DEFAULT_FONT_FAMILY = "SansSerif";
    private static final int DEFAULT_FONT_SIZE = 18;

    public static void begin() throws Exception {

        UIManager.put("TextPane.font", new Font(DEFAULT_FONT_FAMILY, Font.PLAIN, DEFAULT_FONT_SIZE));
        UIManager.setLookAndFeel(new NimbusLookAndFeel());

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new GraphicalEditor().createAndShowGUI();
            }
        });
    }

    private void createAndShowGUI() {

        frame = new JFrame(MAIN_TITLE);
        editor = new JTextPane();
        JScrollPane editorScrollPane = new JScrollPane(editor);

        editor.setDocument(new DefaultStyledDocument());

        frame.add(editorScrollPane, BorderLayout.CENTER);
        frame.setSize(900, 500);
        frame.setLocation(150, 80);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        editor.requestFocusInWindow();
    }
}