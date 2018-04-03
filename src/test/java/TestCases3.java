<<<<<<< HEAD
import documents.Book;
import documents.Copy;
import documents.Document;
import org.junit.Assert;
import org.junit.Test;
import storage.DatabaseManager;
import users.*;

import java.util.ArrayList;

public class TestCases3 {

    @Test
    public void Test1(){

    DatabaseManager databaseManager = new DatabaseManager("Test3");
        databaseManager.resetDatabase();

        ArrayList<Copy> l1_checkedOutCopies = new ArrayList<Copy>();
        ArrayList<Document> l1_requestedDocuments = new ArrayList<Document>();

        UserCard librarian_1 = new UserCard("Irma", "Pins", new Librarian(), "8981351785", "north of London",
                l1_checkedOutCopies, l1_requestedDocuments);
        databaseManager.saveUserCard(librarian_1);
        Session session = new Session(databaseManager.getUserCard(librarian_1.getId()).userType, 5, 3);

        ArrayList<Copy> p1_checkedOutCopies = new ArrayList<Copy>();
        ArrayList<Document> p1_requestedDocs = new ArrayList<Document>();
        UserCard p1 = new UserCard(1010, "Sergey", "Afonso", new Faculty(), "30001", "Via Margutta, 3", p1_checkedOutCopies, p1_requestedDocs);
        databaseManager.saveUserCard(p1);
        /////////////////////////////////////////////////////////////////
        ArrayList<Copy> p2_checkedOutCopies = new ArrayList<Copy>();
        ArrayList<Document> p2_requestedDocs = new ArrayList<Document>();
        UserCard p2 = new UserCard(1011, "Nadia", "Teixeira", new Student(), "30002", "Via Sacra, 13", p2_checkedOutCopies, p2_requestedDocs);
        databaseManager.saveUserCard(p2);
        //////////////////////////////////////////////////////////////////
        ArrayList<Copy> p3_checkedOutCopies = new ArrayList<Copy>();
        ArrayList<Document> p3_requestedDocs = new ArrayList<Document>();
        UserCard p3 = new UserCard(1100, "Elvira", "Espindola", new Student(), "30003", "Via del Corso, 22", p3_checkedOutCopies, p3_requestedDocs);
        databaseManager.saveUserCard(p3);
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ArrayList<Copy> b1_copies = new ArrayList<Copy>();

        ArrayList<String> b1_authors = new ArrayList<String>();
        b1_authors.add("Thomas H. Cormen");
        b1_authors.add("Charles E. Leiserson");
        b1_authors.add("Ronald L. Rivest");
        b1_authors.add("Clifford Stein");

        ArrayList<String> b1_keywords = new ArrayList<String>();
        b1_keywords.add("none");

        Book b1 = new Book("Introduction to Algorithms", b1_authors,
                b1_keywords, 5000, b1_copies, "MIT Press", 2009, "Third edition", false);
        databaseManager.saveDocuments(b1);

        b1.setCopy(new Copy(b1, 1, 1));
        b1.setCopy(new Copy(b1, 2, 1));
        b1.setCopy(new Copy(b1, 3, 1));

        databaseManager.saveDocuments(b1);

        /////////////////////////////////////////////////////////////
        ArrayList<Copy> b2_copies = new ArrayList<Copy>();

        ArrayList<String> b2_authors = new ArrayList<String>();
        b2_authors.add("Erich Gamma");
        b2_authors.add("Ralph Johnson");
        b2_authors.add("John Vlissides");
        b2_authors.add("Richard Helm");

        ArrayList<String> b2_keywords = new ArrayList<String>();
        b2_keywords.add("none");

        Book b2 = new Book("Design Patterns: Elements of Reusable Object-Oriented Software", b2_authors,
                b2_keywords, 1700, b2_copies, "Addison-Wesley Professional", 2003, "First edition", true);
        databaseManager.saveDocuments(b2);
        b2.setCopy(new Copy(b2, 1, 1));
        b2.setCopy(new Copy(b2, 2, 1));
        b2.setCopy(new Copy(b2, 3, 1));

        databaseManager.saveDocuments(b2);

        ///////////////////////////////////////////////////////////////
        ArrayList<Copy> b3_copies = new ArrayList<Copy>();

        ArrayList<String> b3_authors = new ArrayList<String>();
        b3_authors.add("Tony Hoare");

        ArrayList<String> b3_keywords = new ArrayList<String>();
        b3_keywords.add("none");

        Book b3 = new Book("Null References: The Billion Dollar Mistake", b3_authors,
                b3_keywords, 700, b3_copies, "none",2018, "none", false);
        b3.setReference(true);
        b3.setCopy(new Copy(b3, 1, 1));
        b3.setCopy(new Copy(b3, 1, 2));

        databaseManager.saveDocuments(b3);
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        /**Initial state**/
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        p1.checkedOutCopies.add(b1.availableCopies.get(1));
        p1.checkedOutCopies.add(b2.availableCopies.get(1));
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        /**Action**/
        Session curSession = new Session(librarian_1.userType, 2,4);
        Assert.assertTrue("Session is leading by  librarian.", Librarian.class.isAssignableFrom(session.getUser().getClass()));
        int a = p1.getFine(p1, curSession,databaseManager);
        int b = p1.checkedOutCopies.get(0).getOverdue(curSession);
        p1.checkedOutCopies.get(0).returnCopy();

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        /**Effect**/

        Assert.assertTrue("Fine of p1 is equal 0",a == 0 );
        Assert.assertTrue("Overdue of p1 is equal 0",b ==0 );
        Assert.assertTrue("p1 have only one cheched out copies", p1.checkedOutCopies.size()==1);




    }
    @Test
    public void Test2(){}
    @Test
    public void Test3(){}
    @Test
    public void Test4(){}
    @Test
    public void Test5(){}
    @Test
    public void Test6(){}
    @Test
    public void Test7(){}
    @Test
    public void Test8(){}
    @Test
    public void Test9(){}
    @Test
    public void Test10(){}

=======
public class TestCases3 {
>>>>>>> 676f896aecb7ef74e8bb683e9857334d2e778f37
}
