package txedt.cmdeditor.fragments;

public final class Url extends TextFragment {

    private String address;

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

    public void join(Fragment f) {
        System.out.println("[Error]: Url cannot be joined");
        System.exit(1);
    }
}
