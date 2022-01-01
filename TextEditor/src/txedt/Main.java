package txedt;

import txedt.cmdeditor.CMDEditor;
import txedt.guieditor.GUIEditor;

import javax.swing.UnsupportedLookAndFeelException;

/**
 * The entry point of the program
 */
public class Main {

    /**
     * From the main method either CMDEditor or GUIEditor can be run.
     * <p>
     * For additional requirements to run GUIEditor on linux see
     * <a href="{@docRoot}/../Readme.md">Readme.md</a>
     *
     * @param args command-line arguments for the program. If the optional
     *             flag `--terminal` is passed as the first argument, the
     *             CMDEditor will be run, else by default GUIEditor will
     *             be launched.
     *             <p>
     *             Then as the second argument in case of CMDEditor, and the
     *             first argument in case of GUIEditor, the file name can
     *             be passed which to be opened with the editor.
     *             <p>
     *             If there are more arguments, the program will produce
     *             an error.
     * @throws InterruptedException            can occur the Thread which opens or saves to a file is interrupted
     * @throws UnsupportedLookAndFeelException can occur when a chosen look and feel is not supported by the system
     */
    public static void main(String[] args) throws InterruptedException, UnsupportedLookAndFeelException {

        if (args.length == 0) {
            GUIEditor.begin(args);
        } else if (args.length > 2) {
            System.out.println("[Error]: Too many arguments");
            System.exit(1);
        } else if (args[0].equals("--terminal")) {
            if (args.length > 1) {
                String[] _args = {args[1]};
                CMDEditor.begin(_args);
            } else {
                String[] _args = {};
                CMDEditor.begin(_args);
            }
        } else {
            GUIEditor.begin(args);
        }
    }
}
