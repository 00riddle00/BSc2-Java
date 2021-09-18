
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
        if(args.length == 0) {
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
                String[] expression_array = line.split(regex);

                String last_element = expression_array[expression_array.length - 1];
                if (!(last_element.trim().equals("="))) {
                    System.out.println("ERROR: Each expression should end with the '=' sign");
                    System.exit(1);
                }

                int result = 0;
                int state = 1; // 0 means -, 1 means +
                int num;

                for (int i = 0; i < expression_array.length - 1; i++) {
                    String element = expression_array[i];


                    try {
                        num = parseInt(element.trim());

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