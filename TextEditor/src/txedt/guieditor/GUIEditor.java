package txedt.guieditor;

import txedt.guieditor.fileio.FileSaver;
import txedt.guieditor.fileio.FileOpener;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.text.*;
import javax.swing.text.DefaultEditorKit.*;
import javax.swing.text.StyledEditorKit.*;

/**
 * @author Tomas Giedraitis
 * <p>
 * GUIEditor - text editor made with Java Swing GUI
 * @version 1.0
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

    /**
     * Firstly, sets the fonts, look and feel via UIManager.
     * <p>
     * Then, creates a GUIEditor object and calls its method createAndShowGui().
     * This is done inside SwingUtitlities.invokeLater method so that this code
     * would be handled by the Swing's Event Dispatch Thread.
     *
     * @param args Passed from the main entry point of the program (the main method).
     *             Either no arguments or a single argument - the name of the file to
     *             be opened with the editor. If there are more arguments, the method
     *             will produce an error.
     * @throws UnsupportedLookAndFeelException can occur when a chosen look and feel is not supported by the system
     * @see UIManager
     */
    public static void begin(String[] args) throws UnsupportedLookAndFeelException {
        UIManager.put("TextPane.font", getNewFont(18));
        UIManager.put("Menu.font", getNewFont(14));
        UIManager.put("MenuBar.font", getNewFont(14));
        UIManager.put("MenuItem.font", getNewFont(14));
        UIManager.put("Button.font", getNewFont(12));
        UIManager.put("ColorChooser.font", getNewFont(12));
        UIManager.put("ComboBox.font", getNewFont(12));

        UIManager.setLookAndFeel(new MetalLookAndFeel());

        SwingUtilities.invokeLater(() -> new GUIEditor().createAndShowGui());
    }

    /**
     * @param size - the size of the Font
     * @return the newly created Font object with the specified size
     */
    public static Font getNewFont(int size) {
        return new Font("Source Code Pro", Font.PLAIN, size);
    }

    /**
     * Creates all the GUI elements, binds listeners to them, and after that
     * makes the GUI visible - the editing thus can be performed from that moment.
     */
    private void createAndShowGui() {
        // Create three main components:
        // frame - a container to hold the content
        // text pane - where the document will be placed and edited
        // scroll pane - which encapsulates the text pane and adds the
        //      vertical and horizontal scrolling functionality to it
        frame = new JFrame();
        frame.setTitle("txedt - Untitled");
        textPane = new JTextPane();
        textPane.setDocument(new DefaultStyledDocument());
        JScrollPane scrollPane = new JScrollPane(textPane);

        // Create dropdowns for selecting font and text size
        fontDropDown = new JComboBox(fonts);
        fontDropDown.addItemListener(new FontHandler());
        fontDropDown.setSelectedItem("Source Code Pro");

        textSizeDropdown = new JComboBox(textSizes);
        textSizeDropdown.addItemListener(new TextSizeHandler());

        // Create buttons with representing icons for changing style,
        // color of the selected text, and for adding an image
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

        // Create a style panel with the flow layout and
        // add the created dropdowns and buttons to it
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

        // Create a toolbar with the box layout which
        // encapsulates the style panel, and add it to the frame
        JPanel toolBarPanel = new JPanel();
        toolBarPanel.setLayout(new BoxLayout(toolBarPanel, BoxLayout.Y_AXIS));
        toolBarPanel.add(stylePanel);

        frame.add(toolBarPanel, BorderLayout.NORTH);

        // Add the scroll pane (which has a text pane in it) to the frame
        frame.add(scrollPane, BorderLayout.CENTER);

        // Create 'File' menu
        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);

        // Create menu items and add them to the 'File' menu
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

        // Create 'Edit' menu
        JMenu editMenu = new JMenu("Edit");
        editMenu.setMnemonic(KeyEvent.VK_E);

        // Create menu items and add them to the 'Edit' menu
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

        // Create a menubar and put 'File' and 'Edit' menus in it
        // Then, add it to the frame
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        frame.setJMenuBar(menuBar);

        // set the needed properties of the frame and make it visible
        frame.setSize(800, 600);
        frame.setLocation(200, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // put focus on the text pane (on the blinking cursor)
        textPane.requestFocusInWindow();
    }

    /**
     * Handles selecting different fonts. Sets the specified font to the selected text, and
     * makes it the default font for the new text to be entered at the current caret position.
     * After setting the font, the focus is put back to the cursor in the text pane.
     */
    private class FontHandler implements ItemListener {

        public void itemStateChanged(ItemEvent event) {
            if ((event.getStateChange() == ItemEvent.SELECTED) && (fontDropDown.getSelectedIndex() != 0)) {
                String font = (String) event.getItem();
                fontDropDown.setAction(new FontFamilyAction(font, font));
                textPane.requestFocusInWindow();
            }
        }
    }

    /**
     * Handles selecting different text size. Changes the size of the selected text, and
     * makes it the default text size for the new text to be entered at the current caret position.
     * After setting the text size, the focus is put back to the cursor in the text pane.
     */
    private class TextSizeHandler implements ItemListener {

        public void itemStateChanged(ItemEvent event) {
            if ((event.getStateChange() == ItemEvent.SELECTED) && (textSizeDropdown.getSelectedIndex() != 0)) {
                String textSize = (String) event.getItem();
                textSizeDropdown.setAction(new FontSizeAction(textSize, Integer.parseInt(textSize)));
                textPane.requestFocusInWindow();
            }
        }
    }

    /**
     * Handles setting the style of the selected text to either bold, italic or underline via
     * three separate buttons. Changes the style of the selected text, and makes it the default
     * style for the new text to be entered at the current caret position.
     * Afterwards, the focus is put back to the cursor in the text pane.
     */
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

    /**
     * Handles setting the foreground color of the selected text.
     */
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

    /**
     * Handles inserting an image in the text pane.
     * The image is placed as a button, having the icon with the image specified.
     * The pop-up file chooser menu is used to select the image.
     * <p>
     * A focus handler is added to the image afterwards, and the focus is brought
     * back to the cursor in the text pane.
     */
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

        /**
         * The pop-up file chooser menu appears for the user to select the image
         * from his file tree. The files in the file tree are filtered so as to only
         * display the ones with the extensions of "png", "jpg" or "gif".
         *
         * @return File object pointing to the needed image
         */
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

    /**
     * Handles both appearing mouse focus on the existing image in the text pane
     * as well as losing mouse focus.
     * <p>
     * The button object behind an image changes its border color to gray instead
     * of white, thus making the effect of the image having been selected, and changes
     * back to white after the mouse focus is lost.
     */
    private class ImageFocusHandler implements FocusListener {

        /**
         * Make the image containing button's border gray on focus gained
         */
        public void focusGained(FocusEvent event) {
            JButton button = (JButton) event.getComponent();
            button.setBorder(new LineBorder(Color.GRAY));
        }

        /**
         * Make the image containing button's border white (ie. invisible) on focus lost
         */
        public void focusLost(FocusEvent event) {
            ((JButton) event.getComponent()).setBorder(new LineBorder(Color.WHITE));
        }
    }

    /**
     * Handles setting the alignment of the selected text to either left, center, right, or
     * justified via four separate buttons. Changes the alignment of the selected text, and
     * makes it the default style for the new text to be entered at the current caret position.
     * Afterwards, the focus is put back to the cursor in the text pane.
     */
    private class TextAlignHandler implements ActionListener {

        public void actionPerformed(ActionEvent event) {
            String alignment = event.getActionCommand();
            int choice = Arrays.asList(alignments).indexOf(alignment);

            workaround.setAction(new AlignmentAction(alignment, choice));
            workaround.setSelectedIndex(0);
            textPane.requestFocusInWindow();
        }
    }

    /**
     * Handles opening a new empty document
     * <p>
     * Beforehand, moves existing attributes of textPane (in the form
     * of an immutable attribute set object) to the new mutable simple attribute
     * set object and attaches it again to the text pane.
     * <p>
     * Then, sets the text pane's document to a new styled document, and updates
     * the frame's title.
     */
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

    /**
     * Handles opening a file and saving to a file
     * <p>
     * The pop-up file chooser menu is used to select the file or enter a new file
     * name. Then, the file of the type {@link File} is stored in a variable.
     * <p>
     * The open and save operations are performed in a separate thread, and are
     * handled by {@link FileOpener} and {@link FileSaver} classes. The current
     * frame, text pane and file variable are passed to these classes.
     *
     * @see FileOpener
     * @see FileSaver
     */
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

        /**
         * The pop-up file chooser menu appears for the user to select the file
         * from his file tree. The user can also enter a file name in the text
         * field (more relevant when saving to a file).
         * <p>
         * In the future versions the files in the file tree will filtered
         * to only display the ones with the extensions of "edt". Also, a prompt
         * will open with the warning if the user selects to save to an existing
         * file (ie. overwrite it).
         *
         * @return File object pointing to the selected/entered file. In case of
         * saving to a new file, the file is created at first.
         */
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