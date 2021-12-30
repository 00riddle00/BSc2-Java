package texedit.main.cmdeditor.colorable;

import texedit.main.cmdeditor.exceptions.InvalidColorException;

public interface Colorable {
    String getColor();
    void setColor(String color) throws InvalidColorException;
}
