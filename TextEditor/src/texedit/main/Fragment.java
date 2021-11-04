package texedit.main;

public abstract class Fragment {

    protected String text;
    protected int length;
    protected int startPos;
    protected int endPos;

    public Fragment(String text, int len, int startPos) {
        this.text = text;
        this.length = len;
        this.startPos = startPos;
        this.endPos = startPos + len;
    }

    public abstract String toString();

    protected abstract void print();

    protected abstract void join(Fragment f);

    protected final int getLength() {
        return this.length;
    }
}

class TextFragment extends Fragment {

    protected boolean isUnderlined;

    public TextFragment(String text, int len, int startPos) {
        super(text, len, startPos);
    }

    public String toString() {
        return this.text;
    }

    public void print() {
        if (Cursor.fragment == this) {
            print(0, Cursor.positionInFragment - 1);
            Cursor.print();
            print(Cursor.positionInFragment, length);
        } else {
            // TODO change to calling this.print(from, to);
            System.out.print(this.text);
        }
    }

    public void print(int from, int to) {
        if (isUnderlined) {
            System.out.print("\033[4m");
            printText(from, to);
            System.out.print("\033[0m");
        } else {
            printText(from, to);
        }
    }

    public void printText(int from, int to) {
        if (from < length) {
            for (int i = from; i <= to; i++) {
                System.out.print(this.text.charAt(i));
            }
        }
    }

    // TODO join rules depending of object types
    protected void join(Fragment f) {
        this.text += f.text;
        this.length += f.length;
        this.endPos = f.endPos;
    }
}

class Url extends TextFragment {

    protected String address;

    public Url(String text, int len, int startPos, String address) {
        super(text, len, startPos);
        this.address = address;
        this.isUnderlined = true;
    }

    public Url(String text, int len, int startPos) {
        this(text, len, startPos, text);
    }

    public String toString() {
        return "[Text]: " + super.toString() + "[Url]: " + this.address;
    }

    public void print() {
        System.out.print("\033]8;;" + this.address + "\033\\");
        super.print();
        System.out.print("\033]8;;\033\\");
    }

    protected void join(Fragment f) {
        System.out.println("[Error]: Url cannot be joined");
        System.exit(1);
    }
}