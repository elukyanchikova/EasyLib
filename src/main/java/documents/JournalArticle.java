package documents;

import org.json.JSONObject;
import storage.DatabaseManager;

import java.util.ArrayList;

public class JournalArticle extends Document {
    public String editor;
    public String publicationDate;
    public String journalName;

    public  JournalArticle(int id, String title, ArrayList<String> authors, ArrayList<String> keywords, int price,
                           int numberOfRequests, ArrayList<Copy> availableCopies, ArrayList<Copy> takenCopies, int lastCopyID,
                           String journalName, String editor, String publicationDate ) {
        super(id, title, "Journal Article", authors, keywords, price, numberOfRequests, availableCopies, takenCopies, lastCopyID);
        this.journalName = journalName;
        this.editor = editor;
        this.publicationDate = publicationDate;
        this.checkOutTime = 14;
    }

    public  JournalArticle(String title, ArrayList<String> authors, ArrayList<String> keywords, int price,
                           ArrayList<Copy> copies, String journalName, String editor, String publicationDate ) {
        this(++lastID, title, authors, keywords, price, 0, copies, new ArrayList<>(), 1,journalName, editor, publicationDate);
    }

    public  JournalArticle(String title, ArrayList<String> authors, ArrayList<String> keywords, int price,
                           String journalName, String editor, String publicationDate ) {
        this(++lastID, title, authors, keywords, price, 0, new ArrayList<>(), new ArrayList<>(), 1, journalName, editor, publicationDate);
    }

    public JournalArticle(int id, JSONObject data, DatabaseManager databaseManager){
        super(id, data, databaseManager);
        this.docType = "Journal Article";
        this.editor = data.getString("Editor");
        this.publicationDate = data.getString("PublicationDate");
        this.journalName = data.getString("JournalName");
        this.checkOutTime = 14;
    }

    @Override
    public JSONObject serialize() {
        JSONObject data = super.serialize();
        data.put("Editor", editor);
        data.put("PublicationDate", publicationDate);
        data.put("JournalName", journalName);
        return data;
    }
}
