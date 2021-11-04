package texedit.main;

import texedit.main.cursor.Cursor;
import texedit.main.fragments.Fragment;
import texedit.main.fragments.TextFragment;
import texedit.main.fragments.Url;

import java.util.ArrayList;
import java.util.Date;

/**
 * @author Tomas Giedraitis
 * <p>
 * Text Editor - the main class of the project
 */
public class TextEditor {

    public static final short MAX_CHARS = 400;

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void wait(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    public static int countChar(String s, char c) {
        int count = 0;

        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == c)
                count++;
        }
        return count;
    }

    private String title;
    private Date creationDate;
    private ArrayList<Fragment> fragments;
    private Fragment currFragment;
    private Cursor cursor;
    private final String border =
            "====================================" +
                    "==========================================";
    private int charCount;
    private int lineCount = 1;

    public TextEditor(String title, Date cd) {
        this.title = title;
        creationDate = cd;
        this.fragments = new ArrayList<Fragment>();
        this.cursor = Cursor.getInstance();
    }

    public TextEditor(String title) {
        this(title, new Date());
    }

    public TextEditor() {
        this("Untitled");
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCharCount() {
        return this.charCount;
    }

    public void updateCharCount(int delta) {
        if (delta < 0 && this.charCount < Math.abs(delta)) {
            System.out.println("[Error]: Negative char count");
            System.exit(1);
        }
        this.charCount += delta;
    }

    public int getLineCount() {
        return this.lineCount;
    }

    public void updateLineCount(int delta) {
        if (delta < 0 && this.lineCount < Math.abs(delta)) {
            System.out.println("[Error]: Negative line count");
            System.exit(1);
        }
        this.lineCount += delta;
    }

    public void addText(String text) {
        int len = text.length();
        Fragment fragment = new TextFragment(text, len, charCount);
        this.fragments.add(fragment);
        int newlineCount = countChar(text, '\n');
        this.updateCharCount(len);
        this.updateLineCount(newlineCount);

        // TODO move to Fragment's constructor?
        Cursor.fragment = fragment;
        Cursor.positionInFragment = len;
    }

    public void addUrl(String text, String address) {
        int len = text.length();
        int newlineCount = countChar(text, '\n');

        if (newlineCount > 0) {
            System.out.println("[Error]: URL cannot contain newlines!");
            System.exit(1);
        }

        Fragment fragment = new Url(text, len, charCount, address);
        this.fragments.add(fragment);
        this.updateCharCount(len);

        // TODO move to Fragment's constructor?
        Cursor.fragment = fragment;
        Cursor.positionInFragment = len;
    }

    public void printHeader() {
        System.out.println();
        System.out.println(this.border);
        System.out.println("[Title] " + this.getTitle() + " [lines] " +
                this.getLineCount() + " [characters] " + this.getCharCount());
        System.out.println(this.border);
    }

    public void printFragments() {
        for (Fragment fragment : fragments) {
            fragment.print();
        }
    }

    public void redraw() {
        clearScreen();
        printHeader();
        if (fragments.size() > 0) {
            printFragments();
        } else {
            Cursor.print();
        }
    }

    public void run() {
        redraw();

        wait(1000);

        addText("Test\n");
        redraw();

        wait(1000);

        setTitle("TestDocument");
        redraw();

        wait(1000);

        addUrl("Hyperlink", "https://www.google.com");
        redraw();

        wait(1000);

        // TODO move to finalize?
        System.out.println();
    }

    public static void main(String[] args) {
        TextEditor editor = new TextEditor();
        editor.run();
    }
}