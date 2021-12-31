package txedt.cmdeditor.exceptions;

/**
 * A general exception class refering to the exceptions
 * thrown while working with the text editor, which should
 * extend from this class.
 */
public class TextEditorException extends Exception {

    /**
     * Empty no-args constructor
     */
    public TextEditorException() {
    }

    /**
     * @param msg the message of the exception
     */
    public TextEditorException(String msg) {
        super(msg);
    }
}