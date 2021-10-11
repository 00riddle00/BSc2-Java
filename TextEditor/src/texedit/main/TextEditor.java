package texedit.main;

/**
 * @author Tomas Giedraitis
 * <p>
 * Text Editor - the main class of the project
 */

import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

//  TODO Šioje projekto fazėje duomenų įvedimas nereikalingas: viską
//   užtikrinkite kviesdami operacijas su objektais. Kursoriaus objektas
//   atrodo nesusietas su likusiais - panaudokite.

/**
 * Tikslai
 * <p>
 * Sugebėti apibrėžti klasę kaip mažiausią programos statybinį elementą, suprasti kokias paslaugas klasė teikia, kuri jos dalis yra vieša, o kuri ne.
 * Sugebėti apibrėžti tinkamus konstruktorius. Sugebėti sukurti klasės objektus su reikiama būsena bei metodų kvietimais pertvarkyti būseną į norimą.
 * <p>
 * Užduotis
 * <p>
 * Pasirinktąja semestro projektine tema parašyti (nedideles bet prasmingas) klases, kurios geba užtikrinti reikiamą modeliuojamo objekto funkcionalumą.
 * Klasėje/-se turi būti apibrėžti:
 * - konstruktoriai, iš kurių vieną beargumentis, panaudoti this() konstrukciją
 * - laukai, kuriems priega užtikrinama get/set metodais. Bent vienas laukas turi būti inicijuotas pradine reikšme. Bent vienas laukas turi būti
 * nuorodos tipo.
 * - (nestatinius) metodus. Bent vienas metodas turi būti perkrautas (overloaded). Bent vienas metodas (ne setter'is) turėtų keisti objekto būseną.
 * - Apibrėžti metodą println(), kuris išveda objekto turinį į išvedimo srautą
 * - Įtraukti į klasės apibrėžimą ir prasmingai panaugoti static bei final elementus
 * <p>
 * Apibrėžti kitą (testinę klasę), kuri sukurtų apibrėžtų klasių objektus, jais pasinaudotų, kviesdama metodus, ir išvedinėtų laukų būsenas. Projektas
 * turi susidėti mažiausiai iš 3 klasių.
 */
public class TextEditor {

    public static final short MAX_CHARS = 400;

    private String title;
    private Date creationDate;
    private ArrayList<Fragment> fragments;
    private Fragment currFragment;
    private Cursor cursor;
    private final String border = "===========================================";
    private int charCount;
    private int lineCount;

    public TextEditor(String title, Date cd) {
        this.title = title;
        creationDate = cd;
        this.fragments = new ArrayList<Fragment>();
        this.cursor = new Cursor();
    }

    public TextEditor(String title) {
        this(title, new Date());
    }

    public TextEditor() {
        this("Untitled");
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLineCount(int delta) {
        if (delta < 0 && this.charCount < Math.abs(delta)) {
            System.out.println("[Error]: Negative line count");
            System.exit(1);
        }
        this.lineCount = delta;
    }

    public int getLineCount() {
        return this.lineCount;
    }

    public void printHeader() {
        System.out.println();
        System.out.println(this.border);
        System.out.println("[Title] " + this.getTitle() + " [lines] " +
                this.getLineCount() + " [characters] " + this.getCharCount());
        System.out.println(this.border);
    }

    public void getInput() {
        Scanner sc = new Scanner(System.in);
        System.out.print("> ");
        String input = sc.nextLine();
        int len = input.length();
        Fragment fragment = new Fragment(input, len);
        this.fragments.add(fragment);
        updateCharCount(len);
    }

    public void updateCharCount(int delta) {
        if (delta < 0 && this.charCount < Math.abs(delta)) {
            System.out.println("[Error]: Negative character count");
            System.exit(1);
        }

        this.charCount += delta;
    }

    public int getCharCount() {
        return this.charCount;
    }

    public void run() {

        while (true) {
            TextEditor.clearScreen();
            this.printFragments();
            this.printHeader();
            this.getInput();
        }
    }

    public void printFragments() {
        for (Fragment fragment : fragments) {
            fragment.print();
        }
    }
}