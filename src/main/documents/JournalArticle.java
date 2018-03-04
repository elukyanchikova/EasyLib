package documents;

import java.util.ArrayList;

public class JournalArticle extends Document {
    Issue issue;
    String journal;

    public  JournalArticle(int id, String title,String journal, ArrayList<String> authors, ArrayList<String> keywords, int price,
                           int numberOfCopies, int numberOfRequests, Issue issue) {
        super(id, title, "Journal Article", authors, keywords, price, numberOfCopies,numberOfRequests);
        this.journal = journal;
        this.issue = issue;
        this.checkOutTime = 14;
    }

    public  JournalArticle(int id, String title,String journal,ArrayList<String> authors, ArrayList<String> keywords, int price,
                           int numberOfCopies, Issue issue) {
        this(id, title, journal,authors, keywords, price, numberOfCopies,0, issue);
    }

    public Issue getIssue() {
        return issue;
    }

    public String getJournal() {
        return journal;
    }
}
