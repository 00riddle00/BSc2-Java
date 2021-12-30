package txedt.cmdeditor.cursor;

import txedt.cmdeditor.fragments.Fragment;

/**
 * Singleton
 */
public final class Cursor {

    public static Fragment fragment;
    private static int positionInFragment;

    private static Cursor cursor;
    private static final char CURSOR_SIGN = '|';

    public static void print() {
        System.out.print(CURSOR_SIGN);
    }

    public static int getPositionInFragment() {
        return positionInFragment;
    }

    public static void setPositionInFragment(int pos) {
        if (pos <= 0) {
            System.out.println("[Error]: Negative cursor position in a fragment!");
            System.exit(1);
        }
        positionInFragment = pos;
    }

    private int position;

    private Cursor() {
        this.position = 1;
    }

    public static Cursor getInstance() {
        if (cursor == null) {
            cursor = new Cursor();
        }
        return cursor;
    }

    public void move(int delta) {
        setPos(getPos() + delta);
    }

    private int getPos() {
        return position;
    }

    private void setPos(int pos) {
        if (pos <= 0) {
            System.out.println("[Error]: Negative cursor position");
            System.exit(1);
        }
        this.position = pos;
    }
}