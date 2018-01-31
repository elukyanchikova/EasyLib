package documents;

import java.util.ArrayList;
import java.util.LinkedList;

public abstract class Document {
    ArrayList<String> keywords;
    float price;
    boolean ifCheckOutable;
    String title;
    static int maxCheckOutTime;
    ArrayList<String> authors;
    //copies - я хз что это будет
    int numberOfRequests;

    Document(){
        keywords=null;
        ifCheckOutable=false;
        title="none";
        maxCheckOutTime=0;
        authors=null;
    }

}