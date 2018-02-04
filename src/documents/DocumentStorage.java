package documents;

import java.util.ArrayList;

public class DocumentStorage {
    //TODO: make real Document Storage
    /**
     * Templar storage of documents
     * @return data from pseudo-storage
     */
    public static ArrayList<Document> getDocuments(){
        ArrayList<Document> documents = new ArrayList<>();

        ArrayList<Person> authors1 = new ArrayList<>();
        authors1.add(new Person("Thomas", "Cormen", "Thomas H. Cormen"));
        authors1.add(new Person("Charles", "Leiserson", "Charles E. Leiserson"));
        authors1.add(new Person("Ronald", "Rivest", "Ronald L. Rivest"));
        authors1.add(new Person("Clifford", "Stein", "Clifford Stein"));
        ArrayList<String> keywords1 = new ArrayList<>();
        keywords1.add("Algorithms");
        keywords1.add("Programming");
        Book book1 = new Book("Introduction to Algorithms", authors1, keywords1, 5200, 5,
                "MIT Press", 2009, true);

        ArrayList<Person> authors2 = new ArrayList<>();
        authors2.add(new Person("Gilbert", "Strang","Gilbert Strang"));
        ArrayList<String> keywords2 = new ArrayList<>();
        keywords2.add("Linear algebra");
        keywords2.add("Mathematics");
        AVMaterial avMaterial = new AVMaterial("MIT linear algebra lecture", authors2, keywords2, 200, 10);


        ArrayList<Person> authors3 = new ArrayList<>();
        authors3.add(new Person("Edsger", "Dijkstra","Edsger W. Dijkstra"));
        ArrayList<String> keywords3 = new ArrayList<>();
        keywords3.add("ACM");
        JournalArticle article = new JournalArticle("Go to Statement Considered Harmful", "Communication ACM", authors3,
                keywords3,500,3, new Issue("Edward Nash Yourdon", "March 1968"));

        documents.add(book1);
        documents.add(avMaterial);
        documents.add(article);

        return documents;
    }
}
