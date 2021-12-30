package texedit.main.cmdeditor.exceptions;

public class InvalidColorException extends TextEditorException {

    private String color;

    public String getColor() {
        return color;
    }

    // Constructor with message text and additional info
    public InvalidColorException(String msg, String color) {
        super(msg);
        this.color = color;
    }
}
