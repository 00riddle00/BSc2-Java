package texedit.main.cmdeditor.colorable;

public interface ColorableWithANSI extends Colorable {
    String RESET = "\033[0m";

    static void reset() {
        System.out.print(RESET);
    }

    default void printColorEsqSeq() {
        System.out.print(this.getColor());
    }

    boolean checkIfColored();
}
