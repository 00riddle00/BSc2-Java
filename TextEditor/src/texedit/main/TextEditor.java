package texedit.main;

/**
 * @author Tomas Giedraitis
 * <p>
 * Text Editor - the main class of the project
 */

/**
 * Tikslai
 *
 * Sugebėti apibrėžti klasę kaip mažiausią programos statybinį elementą, suprasti kokias paslaugas klasė teikia, kuri jos dalis yra vieša, o kuri ne.
 * Sugebėti apibrėžti tinkamus konstruktorius. Sugebėti sukurti klasės objektus su reikiama būsena bei metodų kvietimais pertvarkyti būseną į norimą.
 * <p>
 * Užduotis
 * <p>
 * Pasirinktąja semestro projektine tema parašyti (nedideles bet prasmingas) klases, kurios geba užtikrinti reikiamą modeliuojamo objekto funkcionalumą.
 * Klasėje/-se turi būti apibrėžti:
 *          - konstruktoriai, iš kurių vieną beargumentis, panaudoti this() konstrukciją
 *          - laukai, kuriems priega užtikrinama get/set metodais. Bent vienas laukas turi būti inicijuotas pradine reikšme. Bent vienas laukas turi būti
 *              nuorodos tipo.
 *          - (nestatinius) metodus. Bent vienas metodas turi būti perkrautas (overloaded). Bent vienas metodas (ne setter'is) turėtų keisti objekto būseną.
 *          - Apibrėžti metodą println(), kuris išveda objekto turinį į išvedimo srautą
 *          - Įtraukti į klasės apibrėžimą ir prasmingai panaugoti static bei final elementus
 *
 * Apibrėžti kitą (testinę klasę), kuri sukurtų apibrėžtų klasių objektus, jais pasinaudotų, kviesdama metodus, ir išvedinėtų laukų būsenas. Projektas
 * turi susidėti mažiausiai iš 3 klasių.
 */
public class TextEditor {

    private final InputGetter inputGetter;
    private final InputProcessor inputProcessor;
    private final DataSaver dataSaver;

    public TextEditor() {
        inputGetter = new InputGetter();
        inputProcessor = new InputProcessor();
        dataSaver = new DataSaver();
    }

    public void println() {
        System.out.println(dataSaver.getTitle());
        dataSaver.printLastFragment();
    }

    public void println(String border) {
        System.out.println("[Title]" + dataSaver.getTitle());
        System.out.println(border);
        dataSaver.printLastFragment();
        System.out.println(border);
    }

    public void startEditing() {
        dataSaver.setTitle("Testas.ed");
        inputGetter.getInput();
    }
}