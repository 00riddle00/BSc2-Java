package txedt.cmdeditor.exceptions;

public class NegativeCountException extends TextEditorException {

    private int count;

    public int getCount() {
        return count;
    }

    // Constructor with message text and additional info
    public NegativeCountException(String msg, int count) {
        super(msg);
        this.count = count;
    }
}
