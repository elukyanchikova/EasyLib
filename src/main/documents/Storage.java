package documents;


//import storage.Database;
import users.Faculty;
import users.Librarian;
import users.Student;
import users.UserCard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Storage {
    //TODO: make real storage
    /**
     * Templar storage of main.documents
     * @return data from pseudo-storage
     */
    public static ArrayList<Document> getDocuments(){
        ArrayList<Document> documents = new ArrayList<>();

        Book book1 = new Book(0,"Introduction to Painting", new ArrayList<String>(Collections.singletonList("John Lanber")),
                new ArrayList<String>(Collections.singletonList("Painting")), 5200, 2,
                "Aress", 2009, false);
        documents.add(book1);

        Book book4 = new Book(1,"The art of thinking", new ArrayList<String>(Arrays.asList("Demi Lessy", "Mike Bolly")),
                new ArrayList<String>(Collections.singletonList("Science")), 600, 2,
                "FreeThink", 2015, false);
        documents.add(book4);

        Book book6 = new Book(2,"Introduction to algorithms",
                new ArrayList<String>(Arrays.asList("Thomas H. Cormen", "Charles E. Leiserson",  "Ronald L. Rivest", "Clifford Stein")),
                new ArrayList<String>(Arrays.asList("Algorithms")), 600, 2,
                "FreeThink", 2015, true);
        documents.add(book6);

        Book book7 = new Book(3,"J", new ArrayList<String>(Arrays.asList("Bill Mates")),
                new ArrayList<String>(Collections.singletonList("Java")), 3000, 0,
                "OracleBook", 2017, true);

        Book book8 = new Book(4,"Psychological Games", new ArrayList<String>(Arrays.asList("Mark Philip Clark")),
                new ArrayList<String>(Collections.singletonList("Psychology")), 1500, 5,
                "FreeThink", 2013, false);
        documents.add(book8);
        Book book9 = new Book(5,"Java for beginners", new ArrayList<String>(Arrays.asList("Bill Mates")),
                new ArrayList<String>(Collections.singletonList("Java")), 3000, 0,
                "OracleBook", 2017, true);
        documents.add(book9);

        return documents;
    }

    /**
     * Templar storage of main.users
     * @return data from pseudo-storage
     */
    public static ArrayList<UserCard> getUsers(){
        ArrayList<UserCard> users = new ArrayList<>();
        users.add(new UserCard("Kate", "White", new Librarian(),"8900312377", "Purple st 2"));
        users.add(new UserCard("Lily", "Smith", new Student(),"8900355355", "Eiffel st 2"));
        users.add(new UserCard("Mike", "Landgraab", new Student(),"8967999966", "Meyer avenue 10",
                new ArrayList<>(Collections.singletonList(new Copy(getDocuments().get(0),0,-1))), new ArrayList<>()));
        users.add(new UserCard("Bertrand", "Meyer", new Faculty(),"8555444333", "Eiffel st 11"));

      /*  Database db = new Database();
        db.load();
        db.saveUserCard(users.get(0));
        db.saveUserCard(users.get(2));*/
        return users;
    }
}
