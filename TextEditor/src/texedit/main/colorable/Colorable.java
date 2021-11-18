package texedit.main.colorable;

import texedit.main.exceptions.InvalidColorException;

public interface Colorable {
    String getColor();
    void setColor(String color) throws InvalidColorException;
}
