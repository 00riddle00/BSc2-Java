package txedt.cmdeditor.colorable;

import txedt.cmdeditor.exceptions.InvalidColorException;

import java.io.Serializable;

public class ColorableImpl implements Serializable, ColorableWithANSI, Cloneable {
    private String colorName;
    private String colorEscSeq;
    private boolean isColored;

    public String getColor() {
        if (!this.isColored) {
            System.out.println("[Error]: No color is set!");
            System.exit(1);
        }
        return this.colorEscSeq;
    }
    public void setColor(String colorName) throws InvalidColorException {
        switch (colorName) {
            case "BLACK":
                colorEscSeq = "\033[0;30m";
                break;
            case "RED":
                colorEscSeq = "\033[0;31m";
                break;
            case "GREEN":
                colorEscSeq = "\033[0;32m";
                break;
            case "YELLOW":
                colorEscSeq = "\033[0;33m";
                break;
            case "BLUE":
                colorEscSeq = "\033[0;34m";
                break;
            case "PURPLE":
                colorEscSeq = "\033[0;35m";
                break;
            case "CYAN":
                colorEscSeq = "\033[0;36m";
                break;
            case "WHITE": colorEscSeq = "\033[0;37m";
            default:
                throw new InvalidColorException("No such color", colorName);
        }
        this.colorName = colorName;
        this.isColored = true;
    }

    public boolean checkIfColored() {
        return this.isColored;
    }

    public void printColorEsqSeq() {
        System.out.print(this.getColor());
    }

    public ColorableImpl clone() throws CloneNotSupportedException {
        return (ColorableImpl) super.clone();
    }
}