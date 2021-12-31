package txedt;

import txedt.cmdeditor.CmdEditor;
import txedt.guieditor.GUIEditor;

import javax.swing.UnsupportedLookAndFeelException;

/**
 * The entry point of the program
 */
public class Main {

    /**
     * From the main method either CMDEditor or GUIEditor can be run.
     * This method contains only one line of code which call any of the
     * two text editors.
     *
     * Call either `CmdEditor.begin(args)` or `GUIEditor.begin(args)`
     * from this method.
     *
     * For additional requirements to run GUIEditor on linux see
     * <a href="{@docRoot}/../Readme.md">Readme.md</a>
     *
     * @param args an array of command-line arguments for the program.
     *             There should be either no arguments or a single argument -
     *             the name of the file to be opened with the editor.
     *             If there are more arguments, the program will produce
     *             an error.
     *
     * @throws InterruptedException can occur the Thread which opens or saves to a file is interrupted
     * @throws UnsupportedLookAndFeelException can occur when a chosen look and feel is not supported by the system
     */
    public static void main(String[] args) throws InterruptedException, UnsupportedLookAndFeelException {
//        CmdEditor.begin(args);
        GUIEditor.begin(args);
    }
}
