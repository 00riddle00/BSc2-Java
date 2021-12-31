package txedt.cmdeditor.exceptions;

/**
 * Happens when the count becomes negative
 */
public class NegativeCountException extends TextEditorException {

    private int count;

    public int getCount() {
        return count;
    }

    /**
     * @param msg   the message of the exception
     * @param count the value of the count that became negative
     */
    public NegativeCountException(String msg, int count) {
        super(msg);
        this.count = count;
    }
}
