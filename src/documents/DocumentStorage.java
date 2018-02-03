package documents;


import java.util.ArrayList;

public class DocumentStorage {
    /**
     * Templar storage of documents
     * @return data from pseudo-storage
     */
    public static ArrayList<Document> getDocuments(){
        ArrayList<Document> documents = new ArrayList<>();

        Book book1 = new Book();
        Book book2 = new Book();
        book1.title = "Test1";
        book2.title = "Test2";
        documents.add(book1);
        documents.add(book2);

        return documents;
    }
}
