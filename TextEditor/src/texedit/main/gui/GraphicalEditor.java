package texedit.main.gui;

import com.sun.java.swing.plaf.gtk.GTKLookAndFeel;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.text.DefaultEditorKit.*;
import javax.swing.text.StyledEditorKit.*;

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
        JScrollPane scrollPane = new JScrollPane(textPane);

        textPane.setDocument(new DefaultStyledDocument());

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

        JButton boldButton = new JButton(new BoldAction());
        boldButton.setHideActionText(true);
        boldButton.setText("Bold");
        boldButton.addActionListener(cutCopyPasteHandler);

        JButton italicButton = new JButton(new ItalicAction());
        italicButton.setHideActionText(true);
        italicButton.setText("Italic");
        italicButton.addActionListener(cutCopyPasteHandler);

        JButton underlineButton = new JButton(new UnderlineAction());
        underlineButton.setHideActionText(true);
        underlineButton.setText("Underline");
        underlineButton.addActionListener(cutCopyPasteHandler);

        JButton colorButton = new JButton("Set Color");
        colorButton.addActionListener(new ColorActionListener());

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.add(cutButton);
        panel.add(copyButton);
        panel.add(pasteButton);
        panel.add(new JSeparator(SwingConstants.VERTICAL));
        panel.add(boldButton);
        panel.add(italicButton);
        panel.add(underlineButton);
        panel.add(new JSeparator(SwingConstants.VERTICAL));
        panel.add(colorButton);
        panel.add(new JSeparator(SwingConstants.VERTICAL));

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

    private class ColorActionListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {

            Color newColor = JColorChooser.showDialog(frame, "Choose a color",
                    Color.BLACK);
            if (newColor == null) {

                textPane.requestFocusInWindow();
                return;
            }

            SimpleAttributeSet attr = new SimpleAttributeSet();
            StyleConstants.setForeground(attr, newColor);
            textPane.setCharacterAttributes(attr, false);
            textPane.requestFocusInWindow();
        }
    }
}