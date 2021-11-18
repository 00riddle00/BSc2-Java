package texedit.main.exceptions;

public class TextEditorException extends Exception {

    // No-args constructor
    public TextEditorException() {}

    // Constructor with message text
    public TextEditorException(String msg) {
        super(msg);
    }
}