package documents;

public class Issue {
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
