package storage;

import documents.Document;

import javax.print.Doc;
import java.util.ArrayList;
import java.util.function.Predicate;

public class Filter{

    public String title;
    public Class<Document> dccumentType;
    public ArrayList<String> authors = new ArrayList<>(); //TODO more partition
    public ArrayList<String> keywords = new ArrayList<>();  //TODO more partition
    public Integer minPrice;
    public Integer maxPrice;
    public String publisher;
    public int publicationYear;
    public String editor;

    //TODO: have a copy, bestSell, edition


    public boolean filter(Document document) {
        if (title != null)
            if (!document.title.toLowerCase().contains(title.toLowerCase())) return false;
        if(minPrice != null)
            if(document.price < minPrice) return false;
        if(maxPrice != null)
            if(document.price < maxPrice) return false;
        return true;
    }
}
