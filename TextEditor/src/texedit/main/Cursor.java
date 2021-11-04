package texedit.main;

public class Cursor {

    public static Fragment fragment;
    public static int positionInFragment;

    private static Cursor cursor;
    private static final char CURSOR_SIGN = '|';

    public static void print() {
        System.out.print(CURSOR_SIGN);
    }

    public static Cursor getInstance() {
        if (cursor == null) {
            cursor = new Cursor();
        }
        return cursor;
    }

    private int position;

    private Cursor() {
        this.position = 1;
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
}