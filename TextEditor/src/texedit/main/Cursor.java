package texedit.main;

public class Cursor {
    private int position;
    public static final char CURSOR_SIGN = '|';

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
