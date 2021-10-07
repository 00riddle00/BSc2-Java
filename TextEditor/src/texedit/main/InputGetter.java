package texedit.main;

import java.util.Scanner;

/**
 * Klasė, priimanti ir apdorojanti įvestį iš klaviatūros (tiek teksto įvestį, tiek papildomų
 * klavišų * kombinacijų įvestį).
 */
public class InputGetter {

    public InputGetter() {
    }

    public void getInput() {
        Scanner sc = new Scanner(System.in);    //System.in is a standard input stream
        System.out.print("[Enter your text]");
        String input = sc.nextLine();
        System.out.println("[Your text]" + input);
    }
}

