package texedit.main.gui;

import com.sun.java.swing.plaf.gtk.GTKLookAndFeel;

import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.text.DefaultEditorKit.*;
import javax.swing.text.StyledEditorKit.*;

public class GraphicalEditor {

    private JFrame frame;
    private JTextPane textPane;
    private JComboBox fontDropDown;
    private String fonts[] = {"DejaVu Sans", "DejaVu Sans Mono", "DejaVu Serif", "DejaVuSansMono Nerd Font",
            "DejaVuSansMono Nerd Font Mono", "DroidSansMono Nerd Font", "DroidSansMono Nerd Font Mono", "Inconsolata Nerd Font",
            "Inconsolata Nerd Font Mono", "Liberation Mono", "Liberation Sans", "Liberation Serif", "Monospaced", "Noto Color Emoji",
            "Noto Sans", "Noto Serif", "Palemonas", "RobotoMono Nerd Font", "RobotoMono Nerd Font Mono", "SansSerif",
            "SauceCodePro Nerd Font", "SauceCodePro Nerd Font Mono", "Serif", "Source Code Pro", "Symbola"};
    private JComboBox textSizeDropdown;
    private String textSizes[] = {"8", "10", "12", "14", "16", "18", "20", "22", "24", "26", "28", "30"};
    private JComboBox textAlignDropDown;
    private String alignments[] = {"Left", "Center", "Right", "Justified"};
    private JButton alignmentButtons[];

    public static void begin() throws Exception {
        UIManager.put("TextPane.font", new Font("Source Code Pro", Font.PLAIN, 20));
        UIManager.setLookAndFeel(new GTKLookAndFeel());

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new GraphicalEditor().createAndShowGui();
            }
        });
    }

    private void createAndShowGui() {
        frame = new JFrame("txedt");
        textPane = new JTextPane();
        textPane.setDocument(new DefaultStyledDocument());
        JScrollPane scrollPane = new JScrollPane(textPane);

        fontDropDown = new JComboBox(fonts);
        fontDropDown.addItemListener(new FontHandler());
        fontDropDown.setSelectedItem("Source Code Pro");

        textSizeDropdown = new JComboBox(textSizes);
        textSizeDropdown.addItemListener(new TextSizeHandler());

        JButton boldButton = new JButton(new BoldAction());
        boldButton.setText("Bold");
        boldButton.addActionListener(new CutCopyPasteHandler());

        JButton italicButton = new JButton(new ItalicAction());
        italicButton.setText("Italic");
        italicButton.addActionListener(new CutCopyPasteHandler());

        JButton underlineButton = new JButton(new UnderlineAction());
        underlineButton.setText("Underline");
        underlineButton.addActionListener(new CutCopyPasteHandler());

        Icon setColorIcon = new ImageIcon(getClass().getClassLoader().getResource("resources/setColor.png"));
        JButton colorButton = new JButton(setColorIcon);
        colorButton.setPreferredSize(new Dimension(30, 30));
        colorButton.addActionListener(new ColorHandler());

        textAlignDropDown = new JComboBox(alignments);
        alignmentButtons = new JButton[alignments.length];

        for (int i = 0; i < alignments.length; i++) {
            alignmentButtons[i] = new JButton(new ImageIcon(getClass().getClassLoader().getResource("resources/" + alignments[i] + ".png")));
            alignmentButtons[i].setActionCommand(alignments[i]);
            alignmentButtons[i].setPreferredSize(new Dimension(30, 30));
            alignmentButtons[i].addActionListener(new TextAlignHandler());
        }

        JPanel stylePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        stylePanel.add(fontDropDown);
        stylePanel.add(textSizeDropdown);
        stylePanel.add(boldButton);
        stylePanel.add(italicButton);
        stylePanel.add(underlineButton);
        stylePanel.add(colorButton);

        for (int i = 0; i < alignments.length; i++) {
            stylePanel.add(alignmentButtons[i]);
        }

        JButton cutButton = new JButton(new CutAction());
        cutButton.setText("Cut");
        cutButton.addActionListener(new CutCopyPasteHandler());

        JButton copyButton = new JButton(new CopyAction());
        copyButton.setText("Copy");
        copyButton.addActionListener(new CutCopyPasteHandler());

        JButton pasteButton = new JButton(new PasteAction());
        pasteButton.setText("Paste");
        pasteButton.addActionListener(new CutCopyPasteHandler());

        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        actionPanel.add(cutButton);
        actionPanel.add(copyButton);
        actionPanel.add(pasteButton);

        JPanel toolBarPanel = new JPanel();
        toolBarPanel.setLayout(new BoxLayout(toolBarPanel, BoxLayout.Y_AXIS));
        toolBarPanel.add(stylePanel);
        toolBarPanel.add(actionPanel);

        frame.add(toolBarPanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);

        frame.setSize(800, 600);
        frame.setLocation(200, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        textPane.requestFocusInWindow();
    }

    private class FontHandler implements ItemListener {

        public void itemStateChanged(ItemEvent event) {
            if ((event.getStateChange() == ItemEvent.SELECTED) && (fontDropDown.getSelectedIndex() != 0)) {
                String font = (String) event.getItem();
                fontDropDown.setAction(new FontFamilyAction(font, font));
                textPane.requestFocusInWindow();
            }
        }
    }

    private class TextSizeHandler implements ItemListener {

        public void itemStateChanged(ItemEvent event) {
            if ((event.getStateChange() == ItemEvent.SELECTED) && (textSizeDropdown.getSelectedIndex() != 0)) {
                String textSize = (String) event.getItem();
                textSizeDropdown.setAction(new FontSizeAction(textSize, Integer.parseInt(textSize)));
                textPane.requestFocusInWindow();
            }
        }
    }

    private class ColorHandler implements ActionListener {

        public void actionPerformed(ActionEvent event) {
            Color color = JColorChooser.showDialog(frame, "", Color.BLACK);

            if (color != null) {
                SimpleAttributeSet attr = new SimpleAttributeSet();
                StyleConstants.setForeground(attr, color);
                textPane.setCharacterAttributes(attr, false);
            }
            textPane.requestFocusInWindow();
        }
    }

    private class TextAlignHandler implements ActionListener {

        public void actionPerformed(ActionEvent event) {
            String alignment = event.getActionCommand();
            int choice = Arrays.asList(alignments).indexOf(alignment);

            textAlignDropDown.setAction(new AlignmentAction(alignment, choice));
            textAlignDropDown.setSelectedIndex(0);
            textPane.requestFocusInWindow();
        }
    }

    private class CutCopyPasteHandler implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            textPane.requestFocusInWindow();
        }
    }
}