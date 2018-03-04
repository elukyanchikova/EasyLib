package documents;

import java.util.ArrayList;

public abstract class Document {

    public static int lastID = 0;
    public int id;
    protected String title;
    protected ArrayList<String> authors;
    protected  String docType;
    protected  ArrayList<String> keywords;
    protected  int price;

    protected int checkOutTime;
    //If number of copies equal -1 it is not able to be checked out
    protected int numberOfCopies;
    protected  int numberOfRequests = 0;

    public ArrayList<Copy> copies;

    public Document(int id, String title, String docType, ArrayList<String> authors, ArrayList<String> keywords, int price,
                    int numberOfCopies, int numberOfRequests) {
        this.title = title;
        this.authors = authors;
        this.keywords = keywords;
        this.price = price;
        this.numberOfCopies = numberOfCopies;
        this.numberOfRequests = numberOfRequests;
        this.id = id;
        this.docType = docType;
        lastID = lastID < id?id:lastID;
    }

    public Document(int id, String title, String docType, ArrayList<String> authors, ArrayList<String> keywords, int price,
                    int numberOfCopies) {
        this(id,title, docType,authors, keywords,price,numberOfCopies, 0);
    }

    public Document(String title, String docType, ArrayList<String> authors, ArrayList<String> keywords, int price,
                    int numberOfCopies, int numberOfRequests){
        this(++lastID, title, docType,authors, keywords,price,numberOfCopies, numberOfRequests);
    }

    public Document(String title, String docType, ArrayList<String> authors, ArrayList<String> keywords, int price,
                    int numberOfCopies){
        this(++lastID,title, docType,authors, keywords,price,numberOfCopies, 0);
    }

    public ArrayList<String> getKeywords() {
        return keywords;
    }

    public int getPrice() {
        return price;
    }

    public void setRequest(int requests){
        this.numberOfRequests = requests;
    }

    public String getTitle() {
        return title;
    }

    public int getNumberOfCopies() {
        return numberOfCopies;
    }

    public ArrayList<String> getAuthors() {
        return authors;
    }

    public int getNumberOfRequests() {
        return numberOfRequests;
    }

    public String getDocType() {
        return docType;
    }

    public int getCheckOutTime(boolean longCheckOutPermission){
        return checkOutTime;
    }

    public void setNumberOfCopies(int numberOfCopies){
        this.numberOfCopies = numberOfCopies;
    }

}