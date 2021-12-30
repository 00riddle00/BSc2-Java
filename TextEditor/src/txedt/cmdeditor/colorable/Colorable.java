package txedt.cmdeditor.colorable;

import txedt.cmdeditor.exceptions.InvalidColorException;

public interface Colorable {
    String getColor();
    void setColor(String color) throws InvalidColorException;
}
