package txedt.cmdeditor.fragments;

import txedt.cmdeditor.colorable.Colorable;
import txedt.cmdeditor.colorable.ColorableImpl;
import txedt.cmdeditor.colorable.ColorableWithANSI;
import txedt.cmdeditor.cursor.Cursor;
import txedt.cmdeditor.exceptions.InvalidColorException;

public class TextFragment extends Fragment implements Colorable, Cloneable {

    protected boolean isUnderlined;

    // Colorable field
    private ColorableImpl clr = new ColorableImpl();

    public TextFragment(String text, int len, int startPos) {
        super(text, len, startPos);
    }

    public String toString() {
        return this.text;
    }

    // Delegate Colorable interface calls to clr field
    public String getColor() {
        return clr.getColor();
    }

    public void setColor(String colorName) throws InvalidColorException {
        try {
            clr.setColor(colorName);
        } catch (InvalidColorException e) {
            throw e;
        }
    }

    public boolean checkIfColored() {
        return clr.checkIfColored();
    }

    public void printColorEsqSeq() {
        clr.printColorEsqSeq();
    }

    public TextFragment clone() throws CloneNotSupportedException {
        try {
            TextFragment tf = (TextFragment) super.clone();
            tf.clr = clr.clone();
            return tf;
        } catch (CloneNotSupportedException e) {
            throw e;
        }
    }

    public void print() {
        if (Cursor.fragment == this) {
            print(0, Cursor.getPositionInFragment() - 1);
            Cursor.print();
            print(Cursor.getPositionInFragment(), length);
        } else {
            System.out.print(this.text);
        }
    }

    public void print(int from, int to) {
        boolean needsReset = false;

        if (checkIfColored()) {
            printColorEsqSeq();
            needsReset = true;
        }

        if (isUnderlined) {
            System.out.print("\033[4m");
            needsReset = true;
        }

        printText(from, to);

        if (needsReset) {
            ColorableWithANSI.reset();
        }
    }

    public void printText(int from, int to) {
        if (from < length) {
            for (int i = from; i <= to; i++) {
                System.out.print(this.text.charAt(i));
            }
        }
    }

    public void join(Fragment f) {
        this.text += f.text;
        this.length += f.length;
        this.endPos = f.endPos;
    }
}
