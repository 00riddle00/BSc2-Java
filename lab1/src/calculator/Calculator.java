
package calculator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

/**
 * @author Tomas Giedraitis
 * <p>
 * 11. Skaičiuoklė faile. Faile surašytas reiškinys, susidedantis iš skaičių bei sumavimo/atimties
 * ženklų bei tarpų ir besibaigiantis ženklu '=', pvz., 1 + 3245 -12=
 * Naudojant java.util.Scanner klasės galimybes nuskaityti reiškinį iš failo ir apskaičiuoti jo reikšmę.
 */
public class Calculator {
    /**
     * Main function
     *
     * @param args - the name of the file
     */
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Proper usage is: Calculator filename");
            System.exit(0);
        }

        try {
            File file = new File(args[0]);

            if (!(file.isFile() && file.canRead())) {
                System.out.println("ERROR: Problem reading the file");
                System.exit(1);
            }

            Scanner input = new Scanner(file);

            while (input.hasNextLine()) {
                String line = input.nextLine();

                String regex = "(?<=[-+()=])|(?=[-+()=])";
                String[] expressionArray = line.split(regex);

                String lastElement = expressionArray[expressionArray.length - 1];
                if (!(lastElement.trim().equals("="))) {
                    System.out.println("ERROR: Each expression should end with the '=' sign");
                    System.exit(1);
                }

                int result = 0;

                for (int i = 0, state = 1; i < expressionArray.length - 1; i++) {
                    String element = expressionArray[i];

                    try {
                        int num = parseInt(element.trim());

                        // state: 0 means -, 1 means +
                        if (state == 1) {
                            result = result + num;
                        } else {
                            result = result - num;
                        }

                    } catch (Exception e) {
                        switch (element.trim()) {
                            case "+" -> state = 1;
                            case "-" -> state = 0;
                            default -> {
                                System.out.println("ERROR: invalid input '" + element + "'");
                                System.exit(1);
                            }
                        }
                    }
                }
                System.out.println("Result: " + result);
            }
            input.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
