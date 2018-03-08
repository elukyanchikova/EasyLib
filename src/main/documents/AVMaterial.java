package documents;

import org.json.JSONObject;
import storage.Database;

import java.util.ArrayList;

public class AVMaterial extends Document{

    public AVMaterial(int id, String title, ArrayList<String> authors, ArrayList<String> keywords, int price,
                       int numberOfRequests, ArrayList<Copy> availableCopies, ArrayList<Copy> takenCopies, int lastCopyID){
        super(id, title, "AV material", authors, keywords, price, numberOfRequests, availableCopies, takenCopies, lastCopyID);
        this.checkOutTime = 14;
    }

    public AVMaterial(String title, ArrayList<String> authors, ArrayList<String> keywords, int price,
                      ArrayList<Copy> copies){
        this(++lastID, title, authors, keywords, price, 0, copies, new ArrayList<>(), 1);
    }

    public AVMaterial(String title, ArrayList<String> authors, ArrayList<String> keywords, int price){
        this(++lastID, title, authors, keywords, price, 0, new ArrayList<>(), new ArrayList<>(),1);
    }

    public AVMaterial(int id, JSONObject data, Database database){
        super(id, data, database);
        this.docType = "AV material";
        this.checkOutTime = 14;
    }
}
