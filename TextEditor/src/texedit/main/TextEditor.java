package texedit.main;

/**
 * @author Tomas Giedraitis
 * <p>
 * Text Editor - the main class of the project
 */

import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

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

    private String title;
    private Date creationDate;
    private ArrayList<Fragment> fragments;
    private int fragmentCount;
    private Cursor cursor;
    public static final int MAX_CHARACTERS = 400;
    public static final String border = "============================";

    public TextEditor() {
        this("Untitled");
        creationDate = new Date();
        this.fragments = new ArrayList<Fragment>();
        this.cursor = new Cursor();
    }

    public TextEditor(String title) {
        this.title = title;
        creationDate = new Date();
        this.fragments = new ArrayList<Fragment>();
        this.cursor = new Cursor();
    }

    public TextEditor(String title, Date cd) {
        this.title = title;
        creationDate = cd;
        this.fragments = new ArrayList<Fragment>();
        this.cursor = new Cursor();
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void printHeader() {
        System.out.println("[Title] " + this.getTitle());
        System.out.println(this.border);
    }

    public void getInput() {
        Scanner sc = new Scanner(System.in);    //System.in is a standard input stream
        System.out.print("[Enter your text] ");
        String input = sc.nextLine();
        Fragment fragment = new Fragment(input);
        this.fragments.add(fragment);
        this.fragmentCount++;
    }

    public void run() {
        this.printHeader();

        while (true) {
            this.printFragments();
            this.getInput();
        }
    }

    public void printFragments() {
        for (int i = 0; i < fragmentCount; i++) {
            fragments.get(i).print();
        }
        if (this.fragmentCount > 0) {
            System.out.println();
        }
    }
}