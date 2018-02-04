package documents;

import java.util.ArrayList;
import java.util.LinkedList;

public abstract class Document {
    ArrayList<String> keywords;
    float price;
    boolean ifCheckOutable;
    public String title;//Changed into public
    static int maxCheckOutTime;
    ArrayList<Author> authors;
    //copies - я хз что это будет
    int numberOfRequests;
    String docType;

    public ArrayList<String> getKeywords() {
        return keywords;
    }

    public float getPrice() {
        return price;
    }

    public boolean isIfCheckOutable() {
        return ifCheckOutable;
    }

    public String getTitle() {
        return title;
    }

    public int getMaxCheckOutTime() {
        return maxCheckOutTime;
    }

    public ArrayList<Author> getAuthors() {
        return authors;
    }

    public int getNumberOfRequests() {
        return numberOfRequests;
    }

    public String getDocType() {
        return docType;
    }

    public Document() {
        keywords = null;
        ifCheckOutable = false;
        title = "none";
        maxCheckOutTime = 0;
        authors = null;
        docType = "none";
    }

    private class Author {
        String name;
        String surname;

        Author(String name, String surname) {
            this.name = name;
            this.surname = surname;
        }
    }

}