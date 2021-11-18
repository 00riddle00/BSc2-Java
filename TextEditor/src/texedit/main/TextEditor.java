package texedit.main;

import texedit.main.colorable.Colorable;
import texedit.main.cursor.Cursor;
import texedit.main.exceptions.InvalidColorException;
import texedit.main.exceptions.NegativeCountException;
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
public final class TextEditor {

    public static final short MAX_CHARS = 400;

    private static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private static void wait(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    private static int countChar(String s, char c) {
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

    public void setCharCount(int count) throws NegativeCountException {
        if (count < 0) {
            throw new NegativeCountException("Negative char count", count);
        }
        this.charCount = count;
    }

    public int getLineCount() {
        return this.lineCount;
    }

    public void setLineCount(int count) throws NegativeCountException {
        if (count < 0) {
            throw new NegativeCountException("Negative line count", count);
        }
        this.lineCount = count;
    }

    public void updateCharCount(int delta) {
        try {
            setCharCount(getCharCount() + delta);
        } catch (NegativeCountException e) {
            System.out.println(e + ". Count: " + e.getCount());
            System.exit(1);
        }
    }

    public void updateLineCount(int delta) {
        try {
            setLineCount(getLineCount() + delta);
        } catch (NegativeCountException e) {
            System.out.println(e + ". Count: " + e.getCount());
            System.exit(1);
        }
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
        Cursor.setPositionInFragment(len);
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
        Cursor.setPositionInFragment(len);
    }

    public void duplicate() {
        Fragment currFragment = this.fragments.get(this.fragments.size() - 1);

        try {
            Fragment newFragment = ((TextFragment) currFragment).clone();

            this.fragments.add(newFragment);
            int newlineCount = countChar(newFragment.toString(), '\n');
            this.updateCharCount(newFragment.getLength());
            this.updateLineCount(newlineCount);

            // TODO move to Fragment's constructor?
            Cursor.fragment = newFragment;
            Cursor.setPositionInFragment(newFragment.getLength());

        } catch (CloneNotSupportedException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }

    public void setSelectionColor(String colorName) {
        Fragment currFragment = Cursor.fragment;
        if (currFragment instanceof Colorable) {
            try {
                ((Colorable) currFragment).setColor(colorName);
            } catch (InvalidColorException e) {
                System.out.println(e + ". Color: " + e.getColor());
                System.exit(1);
            }
        }
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

        duplicate();
        redraw();

        wait(1000);

        setSelectionColor("BLUE");
        redraw();
        wait(1000);

        setTitle("TestDocument");
        redraw();

        wait(1000);

        addUrl("Hyperlink", "https://www.google.com");
        redraw();

        wait(1000);

        // By selection we mean the fragment
        // which has the cursor in it
        setSelectionColor("RED");
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