package texedit.main;

public class Cursor {
    private int position;
    private static final char CURSOR_SIGN = '|';

    public Fragment fragment;
    public static int positionInFragment;

    private Cursor() {
        this.position = 1;
    }

    private static Cursor cursor;

    public static Cursor getInstance() {
        if (cursor == null) {
            cursor = new Cursor();
        }
        return cursor;
    }

    public int getPos() {
        return position;
    }

    public void setPos(int pos) {
        if (pos <= 0) {
            System.out.println("[Error]: Negative cursor position");
            System.exit(1);
        }
        this.position = pos;
    }

    public void move(int delta) {
        position += delta;
        if (position <= 0) {
            System.out.println("[Error]: Negative cursor count");
            System.exit(1);
        }
    }

    public static void print() {
        System.out.print(CURSOR_SIGN);
    }
}