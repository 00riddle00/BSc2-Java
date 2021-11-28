package texedit.main;

import texedit.main.colorable.Colorable;
import texedit.main.cursor.Cursor;
import texedit.main.document.Document;
import texedit.main.exceptions.InvalidColorException;
import texedit.main.exceptions.NegativeCountException;
import texedit.main.fragments.Fragment;
import texedit.main.fragments.TextFragment;
import texedit.main.fragments.Url;

import java.util.ArrayList;

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

    private Document document;
    private String title;
    private int charCount;
    private int lineCount;
    private ArrayList<Fragment> fragments;

    private Fragment currFragment;
    private Cursor cursor;
    private final String border =
            "====================================" +
                    "==========================================";

    public void newDocument() {
        this.document = new Document("Untitled");
        this.title = document.getTitle();
        this.charCount = document.getCharCount();
        this.lineCount = document.getLineCount();
        this.fragments = document.getFragments();
        this.cursor = Cursor.getInstance();
    }

    public void openDocument(String fileName) {
        // Not implemented yet
    }

    public String getTitle() {
        return this.title;
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

    public void duplicateSelection() {
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

        duplicateSelection();
        redraw();

        wait(1000);

        setSelectionColor("BLUE");
        redraw();
        wait(1000);

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

        switch (args.length) {
            case 0 -> editor.newDocument();
            case 1 -> editor.openDocument(args[0]);
            default -> { System.out.println("[Error]: Too many arguments"); System.exit(1); }
        }

        editor.run();
    }
}