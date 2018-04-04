package documents;

import org.json.JSONObject;
import storage.DatabaseManager;

import java.util.ArrayList;

public class Book extends Document {
    public String publisher;
    public int year;
    public String edition;
    public boolean isBestseller;

    /**
     * The constructor is used for restoring object from all existing information about book
     */
    public Book(int id,
                String title,
                ArrayList<String> authors, ArrayList<String> keywords,
                int price,
                int numberOfRequests,
                ArrayList<Copy> availableCopies, ArrayList<Copy> takenCopies, ArrayList<Copy> bookedCopies,
                int lastCopyID,
                String publisher, int year, String edition, boolean isBestseller) {

        super(id, title, "Book", authors, keywords, price, numberOfRequests,
                availableCopies, takenCopies, bookedCopies, lastCopyID);
        this.publisher = publisher;
        this.year = year;
        this.edition = edition;
        this.isBestseller = isBestseller;
        this.checkOutTime = 21;
    }

    /**
     * The constructor is used for creating object and fill copies
     */
    public Book(String title,
                ArrayList<String> authors, ArrayList<String> keywords, int price,
                ArrayList<Copy> copies, String publisher,
                int year, String edition, boolean isBestseller) {

        this(++lastID, title, authors, keywords, price, 0,
                copies, new ArrayList<>(), new ArrayList<>(), 1,
                publisher, year,edition, isBestseller);
    }

    /**
     * The constructor is used for creating object without copies
     */
    public Book(String title,
                ArrayList<String> authors, ArrayList<String> keywords, int price,
                String publisher, int year, String edition, boolean isBestseller) {

        this(++lastID, title,
                authors, keywords, price, 0,
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 1,
                publisher, year, edition,isBestseller );
    }

    /**
     * The constructor is used for creating object using information from JSON File
     * @param id of document
     * @param data is JSON object which stores information about JSON
     * @param databaseManager is the current database manager
     */
    public Book(int id, JSONObject data, DatabaseManager databaseManager){
        super(id, data, databaseManager);
        this.docType = "Book";
        this.isBestseller = data.getBoolean("Bestseller");
        this.publisher = data.getString("Publisher");
        this.edition = data.getString("Edition");
        this.year = data.getInt("Year");
        this.checkOutTime = 21;
    }


    @Override
    public int getCheckOutTime(boolean longCheckOutPermission, boolean lowerCheckOut){
        if(longCheckOutPermission) return 28;
        if(lowerCheckOut) return 21;
        if(isBestseller) return 14;
        return checkOutTime;
    }

    @Override
    public JSONObject serialize() {
        JSONObject data = super.serialize();
        data.put("Publisher", publisher);
        data.put("Year", year);
        data.put("Edition", edition);
        data.put("Bestseller", isBestseller);
        return data;
    }
}
