package texedit.main;

public class Fragment {

    private String text;
    private int length;

    public Fragment() {
        this.length = 0;
    }

    public Fragment(String text) {
        this.text = text;
        this.length = text.length();
    }

    public int getLength() {
        return this.length;
    }

    public void append(String text) {
        this.text = this.text + text;
    }

    public void print() {
        System.out.print(this.text);
    }

    public void println(int until) {
        for (int i = 0; i < until + 1; i++) {
            System.out.print(this.text.charAt(i));
        }
    }

    public void println(int from, int until) {
        for (int i = from - 1; i < until + 1; i++) {
            System.out.print(this.text.charAt(i));
        }
    }
}

