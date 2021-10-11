package texedit.main;

public class Fragment {

    private String text;
    private int length;

    public Fragment(String text, int len) {
        if (len != text.length()) {
            System.out.println("[Error]: cannot create new fragment - length mistmatch");
            System.exit(1);
        }
        this.text = text;
        this.length = len;
    }

    public Fragment(int len) {
        this("", len);
    }

    public Fragment() {
        this("", 0);
    }

    public int getLength() {
        return this.length;
    }

    public void append(String newText) {
        this.text = this.text + newText;
    }

    public void print() {
        System.out.print(this.text);
    }

    public void print(int until) {
        for (int i = 0; i < until + 1; i++) {
            System.out.print(this.text.charAt(i));
        }
    }

    public void print(int from, int until) {
        for (int i = from - 1; i < until + 1; i++) {
            System.out.print(this.text.charAt(i));
        }
    }
}

