package texedit.main;

// TODO make this a singleton object
public class Cursor {
    private int position;
    public static final char CURSOR_SIGN = '|';

    public Cursor(int position) {
        this.position = position;
    }

    public Cursor() {
        this(1);
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void println() {
        System.out.print(CURSOR_SIGN);
    }
}
