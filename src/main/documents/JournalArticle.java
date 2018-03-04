package documents;

import java.util.ArrayList;

public class JournalArticle extends Document {
    public String editor;
    public String publicationDate;
    public String journal;

    public  JournalArticle(int id, String title, ArrayList<String> authors, ArrayList<String> keywords, int price,
                           int numberOfRequests, ArrayList<Copy> availableCopies, ArrayList<Copy> takenCopies, int lastCopyID,
                           String journal, String editor, String publicationDate ) {
        super(id, title, "Journal Article", authors, keywords, price, numberOfRequests, availableCopies, takenCopies, lastCopyID);
        this.journal = journal;
        this.editor = editor;
        this.publicationDate = publicationDate;
        this.checkOutTime = 14;
    }

    public  JournalArticle(String title, ArrayList<String> authors, ArrayList<String> keywords, int price,
                           ArrayList<Copy> copies, String journal, String editor, String publicationDate ) {
        this(++lastID, title, authors, keywords, price, 0, copies, new ArrayList<>(), 1,journal, editor, publicationDate);
    }

    public  JournalArticle(String title, ArrayList<String> authors, ArrayList<String> keywords, int price,
                           String journal, String editor, String publicationDate ) {
        this(++lastID, title, authors, keywords, price, 0, new ArrayList<>(), new ArrayList<>(), 1, journal, editor, publicationDate);
    }

}
