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
    int numberOfCopies;
    int numberOfRequests;
    String docType;

    public ArrayList<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(ArrayList<String> keywords) {
        this.keywords = keywords;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public boolean isIfCheckOutable() {
        return ifCheckOutable;
    }

    public void setIfCheckOutable(boolean ifCheckOutable) {
        this.ifCheckOutable = ifCheckOutable;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getNumberOfCopies() {
        return numberOfCopies;
    }

    public void setNumberOfCopies(int numberOfCopies) {
        this.numberOfCopies = numberOfCopies;
    }

    public int getMaxCheckOutTime() {
        return maxCheckOutTime;
    }

    public static void setMaxCheckOutTime(int maxCheckOutTime) {
        Document.maxCheckOutTime = maxCheckOutTime;
    }

    public ArrayList<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(ArrayList<Author> authors) {
        this.authors = authors;
    }

    public int getNumberOfRequests() {
        return numberOfRequests;
    }

    public void setNumberOfRequests(int numberOfRequests) {
        this.numberOfRequests = numberOfRequests;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
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