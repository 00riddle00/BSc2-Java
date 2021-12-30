package txedt.guieditor;

import com.sun.java.swing.plaf.gtk.GTKLookAndFeel;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.*;
import javax.swing.text.DefaultEditorKit.*;
import javax.swing.text.StyledEditorKit.*;

public class GUIEditor {

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
    private JComboBox workaround;
    private String workaroundArray[] = {"0", "1", "2", "3", "4", "5"};
    private String alignments[] = {"Left", "Center", "Right", "Justified"};
    private JButton alignmentButtons[];

    public static void begin(String[] args) throws Exception {
        UIManager.put("TextPane.font", new Font("Source Code Pro", Font.PLAIN, 20));
        UIManager.setLookAndFeel(new GTKLookAndFeel());

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new GUIEditor().createAndShowGui();
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

        Icon boldIcon = new ImageIcon(getClass().getClassLoader().getResource("resources/Bold.png"));
        JButton boldButton = new JButton(boldIcon);
        boldButton.setPreferredSize(new Dimension(30, 30));
        boldButton.setActionCommand("Bold");
        boldButton.addActionListener(new boldItalicUnderlineHandler());
        boldButton.addActionListener(new CutCopyPasteHandler());

        Icon italicIcon = new ImageIcon(getClass().getClassLoader().getResource("resources/Italic.png"));
        JButton italicButton = new JButton(italicIcon);
        italicButton.setPreferredSize(new Dimension(30, 30));
        italicButton.setActionCommand("Italic");
        italicButton.addActionListener(new boldItalicUnderlineHandler());
        italicButton.addActionListener(new CutCopyPasteHandler());

        Icon underlineIcon = new ImageIcon(getClass().getClassLoader().getResource("resources/Underline.png"));
        JButton underlineButton = new JButton(underlineIcon);
        underlineButton.setPreferredSize(new Dimension(30, 30));
        underlineButton.setActionCommand("Underline");
        underlineButton.addActionListener(new boldItalicUnderlineHandler());
        underlineButton.addActionListener(new CutCopyPasteHandler());

        Icon setColorIcon = new ImageIcon(getClass().getClassLoader().getResource("resources/setColor.png"));
        JButton colorButton = new JButton(setColorIcon);
        colorButton.setPreferredSize(new Dimension(30, 30));
        colorButton.addActionListener(new ColorHandler());

        Icon addImageIcon = new ImageIcon(getClass().getClassLoader().getResource("resources/addImage.png"));
        JButton imageButton = new JButton(addImageIcon);
        imageButton.setPreferredSize(new Dimension(30, 30));
        imageButton.addActionListener(new ImageHandler());

        alignmentButtons = new JButton[alignments.length];

        for (int i = 0; i < alignments.length; i++) {
            alignmentButtons[i] = new JButton(new ImageIcon(getClass().getClassLoader().getResource("resources/" + alignments[i] + ".png")));
            alignmentButtons[i].setActionCommand(alignments[i]);
            alignmentButtons[i].setPreferredSize(new Dimension(30, 30));
            alignmentButtons[i].addActionListener(new TextAlignHandler());
        }

        workaround = new JComboBox(workaroundArray);

        JPanel stylePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        stylePanel.add(fontDropDown);
        stylePanel.add(textSizeDropdown);
        stylePanel.add(boldButton);
        stylePanel.add(italicButton);
        stylePanel.add(underlineButton);
        stylePanel.add(colorButton);
        stylePanel.add(imageButton);

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

    private class boldItalicUnderlineHandler implements ActionListener {

        public void actionPerformed(ActionEvent event) {
            switch (event.getActionCommand()) {
                case "Bold":
                    workaround.setAction(new BoldAction());
                    break;
                case "Italic":
                    workaround.setAction(new ItalicAction());
                    break;
                case "Underline":
                    workaround.setAction(new UnderlineAction());
                    break;
            }

            workaround.setSelectedIndex(0);
            textPane.requestFocusInWindow();
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

    private class ImageHandler implements ActionListener {

        public void actionPerformed(ActionEvent event) {
            File image = choosePictureFile();

            if (image != null) {
                ImageIcon icon = new ImageIcon(image.toString());
                JButton imgButton = new JButton(icon);
                imgButton.setBorder(new LineBorder(Color.WHITE));
                imgButton.setMargin(new Insets(0, 0, 0, 0));
                imgButton.setAlignmentY(.9f);
                imgButton.setAlignmentX(.9f);
                imgButton.addFocusListener(new ImageFocusHandler());
                textPane.insertComponent(imgButton);
                textPane.requestFocusInWindow();
            }
        }

        private File choosePictureFile() {
            JFileChooser chooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("PNG, JPG & GIF Images", "png", "jpg", "gif");
            chooser.setFileFilter(filter);

            if (chooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
                return chooser.getSelectedFile();
            } else {
                return null;
            }
        }
    }

    private class ImageFocusHandler implements FocusListener {

        public void focusGained(FocusEvent event) {
            JButton button = (JButton) event.getComponent();
            button.setBorder(new LineBorder(Color.GRAY));
        }

        public void focusLost(FocusEvent event) {
            ((JButton) event.getComponent()).setBorder(new LineBorder(Color.WHITE));
        }
    }

    private class TextAlignHandler implements ActionListener {

        public void actionPerformed(ActionEvent event) {
            String alignment = event.getActionCommand();
            int choice = Arrays.asList(alignments).indexOf(alignment);

            workaround.setAction(new AlignmentAction(alignment, choice));
            workaround.setSelectedIndex(0);
            textPane.requestFocusInWindow();
        }
    }

    private class CutCopyPasteHandler implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            textPane.requestFocusInWindow();
        }
    }
}