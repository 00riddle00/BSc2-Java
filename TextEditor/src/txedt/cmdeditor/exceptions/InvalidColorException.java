package txedt.cmdeditor.exceptions;

/**
 * Happens when the name of the color refers to a nonexistent color in this program
 *
 * @see txedt.cmdeditor.colorable.ColorableImpl
 */
public class InvalidColorException extends TextEditorException {

    private String color;

    public String getColor() {
        return color;
    }

    /**
     * @param msg   the message of the exception
     * @param color the color String that refers to a nonexistent color
     */
    public InvalidColorException(String msg, String color) {
        super(msg);
        this.color = color;
    }
}
