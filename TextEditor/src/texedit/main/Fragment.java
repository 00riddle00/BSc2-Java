package texedit.main;

public abstract class Fragment {

    protected String text;
    protected int length;
    protected int startPos;
    protected int endPos;

    protected abstract int getLength();

    protected abstract void print();

    protected void printRaw() {
        System.out.print(this.text);
    }

    protected abstract void join(Fragment f);
}

class TextFragment extends Fragment {

    public TextFragment(String text, int len, int startPos) {
        this.text = text;
        this.length = len;
        this.startPos = startPos;
        this.endPos = startPos + len;
    }

    public int getLength() {
        return this.length;
    }

    public void print() {
        System.out.println(this.text);
    }

    protected void join(Fragment f) {
        this.text += f.text;
        this.length += f.length;
        this.endPos = f.endPos;
    }
}

class Url extends Fragment {

    protected String pointsTo;

    public Url(String text, int len, int startPos, String pointsTo) {
        this.text = text;
        this.length = len;
        this.startPos = startPos;
        this.endPos = startPos + len;
        this.pointsTo = pointsTo;
    }

    public Url(String text, int len, int startPos) {
        this(text, len, startPos, "");
    }

    public int getLength() {
        return this.length;
    }

    public void print() {
        System.out.println(this.text);
    }

    protected void join(Fragment f) {
        System.out.println("[Error]: Url cannot be joined");
        System.exit(1);
    }
}