package documents;

import java.util.ArrayList;

public class JournalArticle extends Document {
    Issue issue;
    String journal;

    public  JournalArticle(String title,String journal, ArrayList<Person> authors, ArrayList<String> keywords, int price,
                           int numberOfCopies, int numberOfRequests, Issue issue) {
        super(title, "Journal Article", authors, keywords, price, numberOfCopies,numberOfRequests);
        this.journal = journal;
        this.issue = issue;
    }

    public  JournalArticle(String title,String journal,ArrayList<Person> authors, ArrayList<String> keywords, int price,
                           int numberOfCopies, Issue issue) {
        this(title, journal,authors, keywords, price, numberOfCopies,0, issue);
    }

    public Issue getIssue() {
        return issue;
    }

    public String getJournal() {
        return journal;
    }
}
