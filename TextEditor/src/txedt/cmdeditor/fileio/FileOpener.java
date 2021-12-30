package txedt.cmdeditor.fileio;

import txedt.cmdeditor.document.Document;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class FileOpener implements Runnable {

    private Document openedDocument;
    private String filepath;

    public FileOpener(String filepath) {
        this.filepath = filepath;
    }

    public Document getOpenedDocument() {
        return openedDocument;
    }

    @Override
    public void run() {
        try {
            FileInputStream fileIn = new FileInputStream(this.filepath);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            this.openedDocument = (Document) objectIn.readObject();
            objectIn.close();
        } catch (ClassNotFoundException e) {
            System.out.println("[Error]: Cannot read from a file");
            e.printStackTrace();
            System.exit(1);
        } catch (IOException e) {
            System.out.println("[Error]: Cannot open file '" + filepath + "'");
            e.printStackTrace();
            System.exit(1);
        }
    }
}

