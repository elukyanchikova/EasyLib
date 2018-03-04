package documents;

import org.json.JSONObject;
import storage.Database;

import java.util.ArrayList;

public class Book extends Document {
    public String publisher;
    public int year;
    public boolean isBestseller;

    public Book(int id, String title, ArrayList<String> authors, ArrayList<String> keywords, int price,
                 int numberOfRequests, ArrayList<Copy> availableCopies, ArrayList<Copy> takenCopies, int lastCopyID,
                 String publisher, int year, boolean isBestseller) {

        super(id, title, "Book", authors, keywords, price, numberOfRequests, availableCopies, takenCopies, lastCopyID);
        this.publisher = publisher;
        this.year = year;
        this.isBestseller = isBestseller;
        this.checkOutTime = 21;
    }

    public Book(String title, ArrayList<String> authors, ArrayList<String> keywords, int price,
                ArrayList<Copy> copies, String publisher, int year, boolean isBestseller) {

        this(++lastID, title, authors, keywords, price,0, copies, new ArrayList<>(), 1, publisher, year, isBestseller);
    }

    public Book(String title, ArrayList<String> authors, ArrayList<String> keywords, int price,
                 String publisher, int year, boolean isBestseller) {

        this(++lastID, title, authors, keywords, price,0, new ArrayList<>(), new ArrayList<>(), 1, publisher, year, isBestseller);
    }

    public Book(int id, JSONObject data, Database database){
        super(id, data,database);
        this.docType = "Book";
        this.isBestseller = data.getBoolean("Bestseller");
        this.publisher = data.getString("Publisher");
        this.year = data.getInt("Year");
    }


    @Override
    public int getCheckOutTime(boolean longCheckOutPermission){
        if(isBestseller) return 14;
        if(longCheckOutPermission) return 28;
        return checkOutTime;
    }

    @Override
    public JSONObject serialize() {
        JSONObject data = super.serialize();
        data.put("Publisher", publisher);
        data.put("Year", year);
        data.put("Bestseller", isBestseller);
        return data;
    }

}
