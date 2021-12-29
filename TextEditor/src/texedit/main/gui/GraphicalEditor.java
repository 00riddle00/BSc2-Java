package texedit.main.gui;

import com.sun.java.swing.plaf.gtk.GTKLookAndFeel;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.DefaultEditorKit.*;

public class GraphicalEditor {

    private JFrame frame;
    private JTextPane textPane;

    private static final String MAIN_TITLE = "txedt";
    private static final String DEFAULT_FONT_FAMILY = "Source Code Pro";
    private static final int DEFAULT_FONT_SIZE = 20;

    public static void begin() throws Exception {

        UIManager.put("TextPane.font", new Font(DEFAULT_FONT_FAMILY, Font.PLAIN, DEFAULT_FONT_SIZE));
        UIManager.setLookAndFeel(new GTKLookAndFeel());

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new GraphicalEditor().createAndShowGui();
            }
        });
    }

    private void createAndShowGui() {

        frame = new JFrame(MAIN_TITLE);
        textPane = new JTextPane();
        textPane.setEditorKit(JEditorPane.createEditorKitForContentType("text/html"));

        JScrollPane scrollPane = new JScrollPane(textPane);

        CutCopyPasteHandler cutCopyPasteHandler = new CutCopyPasteHandler();

        JButton cutButton = new JButton(new CutAction());
        cutButton.setHideActionText(true);
        cutButton.setText("Cut");
        cutButton.addActionListener(cutCopyPasteHandler);

        JButton copyButton = new JButton(new CopyAction());
        copyButton.setHideActionText(true);
        copyButton.setText("Copy");
        copyButton.addActionListener(cutCopyPasteHandler);

        JButton pasteButton = new JButton(new PasteAction());
        pasteButton.setHideActionText(true);
        pasteButton.setText("Paste");
        pasteButton.addActionListener(cutCopyPasteHandler);

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.add(cutButton);
        panel.add(copyButton);
        panel.add(pasteButton);

        JPanel toolBarPanel = new JPanel();
        toolBarPanel.setLayout(new BoxLayout(toolBarPanel, BoxLayout.PAGE_AXIS));
        toolBarPanel.add(panel);

        frame.add(toolBarPanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);

        frame.setSize(800, 600);
        frame.setLocation(200, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        textPane.requestFocusInWindow();
    }

    private class CutCopyPasteHandler implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            textPane.requestFocusInWindow();
        }
    }
}