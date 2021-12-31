package txedt.guieditor;

import txedt.guieditor.fileio.FileSaver;
import txedt.guieditor.fileio.FileOpener;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import javax.swing.text.*;
import javax.swing.text.DefaultEditorKit.*;
import javax.swing.text.StyledEditorKit.*;

/**
 * @author Tomas Giedraitis
 * <p>
 * GUIEditor - text editor made with Java Swing GUI
 *
 *  @version 1.0
 */
public class GUIEditor {

    private File file;
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

    public static void begin(String[] args) throws UnsupportedLookAndFeelException {
        UIManager.put("TextPane.font", getNewFont(18));
        UIManager.put("Menu.font", getNewFont(14));
        UIManager.put("MenuBar.font", getNewFont(14));
        UIManager.put("MenuItem.font", getNewFont(14));
        UIManager.put("Button.font", getNewFont(12));
        UIManager.put("ColorChooser.font", getNewFont(12));
        UIManager.put("ComboBox.font", getNewFont(12));

        UIManager.setLookAndFeel(new NimbusLookAndFeel());

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new GUIEditor().createAndShowGui();
            }
        });
    }

    public static Font getNewFont(int size) {
        return new Font("Source Code Pro", Font.PLAIN, size);
    }

    private void createAndShowGui() {
        frame = new JFrame();
        frame.setTitle("txedt - Untitled");
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
        boldButton.addActionListener(actionEvent -> textPane.requestFocusInWindow());

        Icon italicIcon = new ImageIcon(getClass().getClassLoader().getResource("resources/Italic.png"));
        JButton italicButton = new JButton(italicIcon);
        italicButton.setPreferredSize(new Dimension(30, 30));
        italicButton.setActionCommand("Italic");
        italicButton.addActionListener(new boldItalicUnderlineHandler());
        italicButton.addActionListener(actionEvent -> textPane.requestFocusInWindow());

        Icon underlineIcon = new ImageIcon(getClass().getClassLoader().getResource("resources/Underline.png"));
        JButton underlineButton = new JButton(underlineIcon);
        underlineButton.setPreferredSize(new Dimension(30, 30));
        underlineButton.setActionCommand("Underline");
        underlineButton.addActionListener(new boldItalicUnderlineHandler());
        underlineButton.addActionListener(actionEvent -> textPane.requestFocusInWindow());

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

        for (JButton button : alignmentButtons) {
            stylePanel.add(button);
        }

        JPanel toolBarPanel = new JPanel();
        toolBarPanel.setLayout(new BoxLayout(toolBarPanel, BoxLayout.Y_AXIS));
        toolBarPanel.add(stylePanel);

        frame.add(toolBarPanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);

        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);

        JMenuItem newItem = new JMenuItem("New");
        newItem.setMnemonic(KeyEvent.VK_N);
        newItem.addActionListener(new NewFileHandler());
        JMenuItem openItem = new JMenuItem("Open...");
        openItem.setActionCommand("Open");
        openItem.setMnemonic(KeyEvent.VK_O);
        openItem.addActionListener(new OpenSaveFileHandler());
        JMenuItem saveItem = new JMenuItem("Save...");
        saveItem.setActionCommand("Save");
        saveItem.setMnemonic(KeyEvent.VK_S);
        saveItem.addActionListener(new OpenSaveFileHandler());
        JMenuItem quitItem = new JMenuItem("Quit");
        quitItem.setMnemonic(KeyEvent.VK_X);
        quitItem.addActionListener(e -> System.exit(0));

        fileMenu.add(newItem);
        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.add(quitItem);

        JMenu editMenu = new JMenu("Edit");
        editMenu.setMnemonic(KeyEvent.VK_E);

        JMenuItem cutItem = new JMenuItem("Cut");
        cutItem.setMnemonic(KeyEvent.VK_X);
        cutItem.setAction(new CutAction());
        cutItem.addActionListener(actionEvent -> textPane.requestFocusInWindow());
        JMenuItem copyItem = new JMenuItem("Copy");
        copyItem.setMnemonic(KeyEvent.VK_C);
        copyItem.setAction(new CopyAction());
        copyItem.addActionListener(actionEvent -> textPane.requestFocusInWindow());
        JMenuItem pasteItem = new JMenuItem("Paste");
        pasteItem.setMnemonic(KeyEvent.VK_P);
        pasteItem.setAction(new PasteAction());
        pasteItem.addActionListener(actionEvent -> textPane.requestFocusInWindow());

        editMenu.add(cutItem);
        editMenu.add(copyItem);
        editMenu.add(pasteItem);

        menuBar.add(fileMenu);
        menuBar.add(editMenu);

        frame.setJMenuBar(menuBar);
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

    private class NewFileHandler implements ActionListener {

        public void actionPerformed(ActionEvent event) {
            AttributeSet currentAttributes = textPane.getCharacterAttributes();
            SimpleAttributeSet simpleAttributes = new SimpleAttributeSet(currentAttributes);
            simpleAttributes.removeAttributes(currentAttributes);
            textPane.setCharacterAttributes(simpleAttributes, true);

            textPane.setDocument(new DefaultStyledDocument());
            file = null;
            frame.setTitle("txedt - Untitled");
        }

    }

    private class OpenSaveFileHandler implements ActionListener {

        public void actionPerformed(ActionEvent event) {
            file = chooseFile();
            if (file == null) {
                return;
            }

            switch (event.getActionCommand()) {
                case "Open":
                    FileOpener fileOpener = new FileOpener(frame, textPane, file);
                    new Thread(fileOpener).start();
                    break;
                case "Save":
                    FileSaver fileSaver = new FileSaver(frame, textPane, file);
                    new Thread(fileSaver).start();
            }
        }

        private File chooseFile() {
            JFileChooser chooser = new JFileChooser();

            if (chooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
                return chooser.getSelectedFile();
            } else {
                return null;
            }
        }
    }
}