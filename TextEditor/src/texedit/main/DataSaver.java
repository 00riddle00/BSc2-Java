package texedit.main;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Date;

/**
 * Klasė, sauganti duomenis - tiek visą einamąjį tekstą, tiek papildomą informaciją apie teksto
 * dalis, kurioms buvo suteiktos įvairios savybės teksto redagavimo metu. Kartu ši klasė atlieka ir
 * teksto versijavimą, jei vartotojas norėtų atšaukti pakeitimus
 */
public class DataSaver {

    private String title;
    private Date creationDate;
    private ArrayList<TextFragment> textFragments;
    public static final int MAX_CHARACTERS = 400;

    /**
     * Non-args constructor
     */
    public DataSaver() {
        this("Untitled");
        creationDate = new Date();
    }

    public DataSaver(String title) {
        this.title = title;
        creationDate = new Date();
    }

    public DataSaver(String title, Date cd) {
        this.title = title;
        creationDate = cd;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void printLastFragment() {
        String lastFragment = textFragments.get(textFragments.size() - 1);
        System.out.println(lastFragment);
    }
}