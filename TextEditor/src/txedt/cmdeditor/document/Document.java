package txedt.cmdeditor.document;

import txedt.cmdeditor.fragments.Fragment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Document implements Serializable {

    private String title;
    private Date createdDate;
    private Date lastModifiedDate;
    private int charCount;
    private int lineCount;

    private ArrayList<Fragment> fragments;

    public Document(String title) {
        this.title = title;
        this.createdDate = new Date();
        this.lastModifiedDate = this.createdDate;
        this.fragments = new ArrayList<Fragment>();
        // TODO change depending on fragment sizes
        this.charCount = 0;
        // TODO change depending on fragment sizes
        this.lineCount = 1;
    }

    public String getTitle() {
        return this.title;
    }

    public int getCharCount() {
        return this.charCount;
    }

    public int getLineCount() {
        return this.lineCount;
    }

    public ArrayList<Fragment> getFragments() {
        return this.fragments;
    }
}
