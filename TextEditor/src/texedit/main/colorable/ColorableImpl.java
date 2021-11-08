package texedit.main.colorable;

public class ColorableImpl implements ColorableWithANSI {
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

    public void setColor(String colorName) {
        switch (colorName) {
            case "BLACK" -> colorEscSeq = "\033[0;30m";
            case "RED" -> colorEscSeq = "\033[0;31m";
            case "GREEN" -> colorEscSeq = "\033[0;32m";
            case "YELLOW" -> colorEscSeq = "\033[0;33m";
            case "BLUE" -> colorEscSeq = "\033[0;34m";
            case "PURPLE" -> colorEscSeq = "\033[0;35m";
            case "CYAN" -> colorEscSeq = "\033[0;36m";
            case "WHITE" -> colorEscSeq = "\033[0;37m";
            default -> {
                System.out.println("[Error]: No such color!");
                System.exit(1);
            }
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
}