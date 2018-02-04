package documents;

public class JournalArticle extends Document {
    Issue issue;

   public  JournalArticle(Issue issue) {
        this.issue = issue;
        docType="Journal article";
    }

    public Issue getIssue() {
        return issue;
    }

    public void setIssue(Issue issue){
       this.issue=issue;
    }

    private class Issue {
        String editor;
        String publicationDate;

        Issue(String editor, String publicationDate) {
            this.editor = editor;
            this.publicationDate = publicationDate;
        }

        Issue() {
            editor = "NoName";
            publicationDate = "unknown";
        }
    }


}
