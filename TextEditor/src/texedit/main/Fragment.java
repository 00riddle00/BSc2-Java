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

    Fragment(String text, int len, int startPos) {
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
        System.out.println(this.text);
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

    protected String pointsTo;

    public Url(String text, int len, int startPos, String pointsTo) {
        super(text, len, startPos);
        this.pointsTo = pointsTo;
    }

    public Url(String text, int len, int startPos) {
        this(text, len, startPos, "");
    }

    protected void join(Fragment f) {
        System.out.println("[Error]: Url cannot be joined");
        System.exit(1);
    }

    public String toString() {
        return super.toString();
    }
}