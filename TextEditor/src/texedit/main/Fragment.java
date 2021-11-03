package texedit.main;

public abstract class Fragment {

    protected String text;
    protected int length;
    protected int startPos;
    protected int endPos;

    protected final int getLength() {
        return this.length;
    }

    protected abstract void print();

    public abstract String toString();

    protected abstract void join(Fragment f);

    public Fragment(String text, int len, int startPos) {
        this.text = text;
        this.length = len;
        this.startPos = startPos;
        this.endPos = startPos + len;
    }
}

class TextFragment extends Fragment {

    public TextFragment(String text, int len, int startPos) {
        super(text, len, startPos);
    }

    public void print() {
        if (Cursor.fragment == this) {
            print(0, Cursor.positionInFragment - 1);
            Cursor.print();
            print(Cursor.positionInFragment, length);
        } else {
            System.out.print(this.text);
        }
    }


    public void print(int from, int until) {
        if (from < length) {
            for (int i = from; i <= until; i++) {
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

    public String toString() {
        return this.text;
    }
}

class Url extends TextFragment {

    protected String address;

    public Url(String text, int len, int startPos, String address) {
        super(text, len, startPos);
        this.address = address;
    }

    public Url(String text, int len, int startPos) {
        this(text, len, startPos, text);
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

    public String toString() {
        return "[Text]: " + super.toString() + "[Url]: " + this.address;
    }
}