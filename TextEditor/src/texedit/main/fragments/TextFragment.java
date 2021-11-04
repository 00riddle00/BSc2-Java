package texedit.main.fragments;

import texedit.main.cursor.Cursor;

public class TextFragment extends Fragment {

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
    public void join(Fragment f) {
        this.text += f.text;
        this.length += f.length;
        this.endPos = f.endPos;
    }
}
