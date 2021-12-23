package texedit.main.fileio;

import texedit.main.document.Document;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class FileSaver implements Runnable {

    Document document;

    public FileSaver(Document document) {
        this.document = document;
    }

    @Override
    public void run() {
        try {
            FileOutputStream fileOut = new FileOutputStream("/home/riddle/" + this.document.getTitle() + ".edt");
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(this.document);
            objectOut.flush();
            objectOut.close();
        } catch (IOException e) {
            System.out.println("[Error]: Cannot write document to a file");
            e.printStackTrace();
            System.exit(1);
        }
    }
}
