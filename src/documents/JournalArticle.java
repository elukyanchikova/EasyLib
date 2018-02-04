package documents;

public class JournalArticle extends Document {
    Issue issue;

    JournalArticle(Issue issue) {
        this.issue = issue;
        docType="Journal article";
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
