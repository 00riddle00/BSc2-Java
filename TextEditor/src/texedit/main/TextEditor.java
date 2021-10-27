package texedit.main;

import java.util.ArrayList;
import java.util.Date;

/**
 * @author Tomas Giedraitis
 * <p>
 * Text Editor - the main class of the project
 */
public class TextEditor {

    public static final short MAX_CHARS = 400;

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

    public static int countChar(String s, char c) {
        int count = 0;

        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == c)
                count++;
        }
        return count;
    }

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

    public TextEditor(String title, Date cd) {
        this.title = title;
        creationDate = cd;
        this.fragments = new ArrayList<Fragment>();
        this.cursor = new Cursor();
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

    public int getCharCount() {
        return this.charCount;
    }

    public int getLineCount() {
        return this.lineCount;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void updateCharCount(int delta) {
        if (delta < 0 && this.charCount < Math.abs(delta)) {
            System.out.println("[Error]: Negative char count");
            System.exit(1);
        }
        this.charCount += delta;
    }

    public void updateLineCount(int delta) {
        if (delta < 0 && this.lineCount < Math.abs(delta)) {
            System.out.println("[Error]: Negative line count");
            System.exit(1);
        }
        this.lineCount += delta;
    }

    public void createFragment(String s) {
        int len = s.length();
        Fragment fragment = new TextFragment(s, charCount, len);
        this.fragments.add(fragment);
        int newlineCount = countChar(s, '\n');
        System.out.println("NN" + newlineCount);
        this.updateCharCount(len);
        this.updateLineCount(newlineCount);
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
        printFragments();
    }

    public void run() {
        redraw();

        wait(2000);

        createFragment("Test\n");
        redraw();

        wait(2000);

        setTitle("TestDocument");
        redraw();

        wait(2000);
    }
}