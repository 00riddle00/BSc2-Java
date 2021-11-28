package texedit.main.document;

import texedit.main.fragments.Fragment;

import java.util.ArrayList;
import java.util.Date;

public class Document {

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
        this.charCount = 0;
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
