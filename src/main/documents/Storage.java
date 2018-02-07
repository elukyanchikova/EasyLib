package documents;


import users.Faculty;
import users.Librian;
import users.Student;
import users.UserCard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Storage {
    //TODO: make real Storage
    /**
     * Templar storage of main.documents
     * @return data from pseudo-storage
     */
    public static ArrayList<Document> getDocuments(){
        ArrayList<Document> documents = new ArrayList<>();

        Book book1 = new Book("Introduction to Painting", new ArrayList<String>(Collections.singletonList("John Lanber")),
                new ArrayList<String>(Collections.singletonList("Painting")), 5200, 2,
                "Aress", 2009, false);
        documents.add(book1);//0

        Book book4 = new Book("The art of thinking", new ArrayList<String>(Arrays.asList("Demi Lessy", "Mike Bolly")),
                new ArrayList<String>(Collections.singletonList("Science")), 600, 2,
                "FreeThink", 2015, false);
        documents.add(book4);//4

        Book book8 = new Book("Psychological Games", new ArrayList<String>(Arrays.asList("Mark Philip Clark")),
                new ArrayList<String>(Collections.singletonList("Psychology")), 1500, 5,
                "FreeThink", 2013, false);
        documents.add(book8);//8
        Book book9 = new Book("Java for beginners", new ArrayList<String>(Arrays.asList("Bill Mates")),
                new ArrayList<String>(Collections.singletonList("Java")), 3000, 0,
                "OracleBook", 2017, true);
        documents.add(book9);//9
        /*ArrayList<String> authors1 = new ArrayList<>();
        authors1.add("Thomas H. Cormen");
        authors1.add("Charles E. Leiserson");
        authors1.add( "Ronald L. Rivest");
        authors1.add("Clifford Stein");
        ArrayList<String> keywords1 = new ArrayList<>();
        keywords1.add("Algorithms");
        keywords1.add("Programming");
        Book book1 = new Book("Introduction to Algorithms", authors1, keywords1, 5200, 5,
                "MIT Press", 2009, true);

        ArrayList<String> authors2 = new ArrayList<>();
        authors2.add("Gilbert Strang");
        ArrayList<String> keywords2 = new ArrayList<>();
        keywords2.add("Linear algebra");
        keywords2.add("Mathematics");
        AVMaterial avMaterial = new AVMaterial("MIT linear algebra lecture", authors2, keywords2, 200, 0);


        ArrayList<String> authors3 = new ArrayList<>();
        authors3.add("Edsger W. Dijkstra");
        ArrayList<String> keywords3 = new ArrayList<>();
        keywords3.add("ACM");
        JournalArticle article = new JournalArticle("Go to Statement Considered Harmful", "Communication ACM", authors3,
                keywords3,500,3, new Issue("Edward Nash Yourdon", "March 1968"));

        documents.add(book1);
        documents.add(avMaterial);
        documents.add(article);
*/

        return documents;
    }

    /**
     * Templar storage of main.users
     * @return data from pseudo-storage
     */
    public static ArrayList<UserCard> getUsers(){
        ArrayList<UserCard> users = new ArrayList<>();
        users.add(new UserCard("Kate", "White", new Librian(),"8900312377", "Purple st 2"));
        users.add(new UserCard("Lily", "Smith", new Student(),"8900355355", "Eiffel st 2"));
        users.add(new UserCard("Mike", "Landgraab", new Student(),"8967999966", "Meyer avenue 10"));
        users.add(new UserCard("Bertrand", "Meyer", new Faculty(),"8555444333", "Eiffel st 11"));
        return users;
    }
}
