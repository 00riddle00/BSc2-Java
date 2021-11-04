package texedit.main.fragments;

public abstract class Fragment {

    public String text;
    public int length;
    public int startPos;
    public int endPos;

    public Fragment(String text, int len, int startPos) {
        this.text = text;
        this.length = len;
        this.startPos = startPos;
        this.endPos = startPos + len;
    }

    public abstract String toString();

    public abstract void print();

    public abstract void join(Fragment f);

    public final int getLength() {
        return this.length;
    }
}
