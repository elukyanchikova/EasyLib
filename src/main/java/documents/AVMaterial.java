package documents;

import org.json.JSONObject;
import storage.DatabaseManager;

import java.util.ArrayList;

public class AVMaterial extends Document{

    /**
     * The constructor is used for restoring object from all existing information about AVMaterial
     */
    public AVMaterial(int id, String title, 
                      ArrayList<String> authors, ArrayList<String> keywords, 
                      int price, int numberOfRequests, 
                      ArrayList<Copy> availableCopies, ArrayList<Copy> takenCopies, ArrayList<Copy> bookedCopies,
                      int lastCopyID){
        
        super(id, title, "AV material", 
                authors, keywords, price, numberOfRequests, 
                availableCopies, takenCopies, bookedCopies, lastCopyID);
        this.checkOutTime = 14;
    }

    /**
     * The constructor is used for creating object and fill copies
     */
    public AVMaterial(String title, ArrayList<String> authors, ArrayList<String> keywords, int price,
                      ArrayList<Copy> copies){
        this(++lastID, title, authors, keywords, price, 0, 
                copies, new ArrayList<>(), new ArrayList<>(), 1);
    }

    /**
     * The constructor is used for creating object without copies
     */
    public AVMaterial(String title, ArrayList<String> authors, ArrayList<String> keywords, int price){
        this(++lastID, title, authors, keywords, price, 0, 
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 1);
    }

    /**
     * The constructor is used for creating object using information from JSON File
     * @param id of document
     * @param data is JSON object which stores information about JSON
     * @param databaseManager is the current database manager
     */
    public AVMaterial(int id, JSONObject data, DatabaseManager databaseManager){
        super(id, data, databaseManager);
        this.docType = "AV material";
        this.checkOutTime = 14;
    }
}
