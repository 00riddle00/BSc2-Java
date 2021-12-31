package txedt;

import txedt.cmdeditor.CmdEditor;
import txedt.guieditor.GUIEditor;

/**
 * The entry point of the progrram
 * See also <a href="{@docRoot}/../Readme.md">Readme.md</a>
 */
public class Main {

    /**
     * From the main method either CMDEditor or GUIEditor can be run.
     * @param args an array of command-line arguments for the application
     */
    public static void main(String[] args) throws Exception {
        //CmdEditor.begin(args);
        GUIEditor.begin(args);
    }
}
