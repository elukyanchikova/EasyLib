import documents.AVMaterial;
import documents.Book;
import documents.Copy;
import documents.Document;
import forms.MainForm;
import org.junit.Assert;
import org.junit.Test;
import storage.Database;
import users.*;

import java.util.ArrayList;


public class TestCases2 {
    /**
     * Initial state:  system does not have any documents, any patron.
     * The system only contains one user who is a librarian.
     * Action:  librarian adds  * 3 copies of book b1,
     *                          * 2 copies of book b2,
     *                          * 1 copy of book b3,
     *                          * 2 Video materials: av1 and av2
     *                          * patrons p1, p2 and p3
     * Effect: number of documents in the System is 8 and the number of users is 4.
     */

    @Test
    public void TestCase1() {
        //Initial state
        Database database = new Database("TestCase1");

        ArrayList<Copy> l1_checkedOutCopies = new ArrayList<Copy>();
        ArrayList<Document> l1_requestedDocuments = new ArrayList<Document>();

        UserCard librarian_1 = new UserCard("Irma", "Pins", new Librarian(), "8981351785", "north of London",
                l1_checkedOutCopies, l1_requestedDocuments);
        database.saveUserCard(librarian_1);
        Session session = new Session(database.getUserCard(librarian_1.getId()).userType, 9, 3);
        Assert.assertTrue(Librarian.class.isAssignableFrom(session.getUser().getClass()));

        Assert.assertTrue("The database contains only one user",database.getAllUsers().size() == 1);
        Assert.assertTrue("This only one user is a librarian.",( Librarian.class.isAssignableFrom(database.getUserCard(1).getClass())));
        Assert.assertTrue("There is no any documents in the database.",database.getAllUsers().size() == 0);

        ///////////////////////////////////////////////////////////////
        ArrayList<Copy> b1_copies = new ArrayList<Copy>();

        ArrayList<String> b1_authors = new ArrayList<String>();
        b1_authors.add("Thomas H. Cormen");
        b1_authors.add("Charles E. Leiserson");
        b1_authors.add("Ronald L. Rivest");
        b1_authors.add("Clifford Stein");

        ArrayList<String> b1_keywords = new ArrayList<String>();
        b1_keywords.add("none");

        Book b1 = new Book("Introduction to Algorithms", b1_authors,
                b1_keywords, 0, b1_copies, "MIT Press", 2009, "Third edition", false);
        b1.setCopy(new Copy(b1, 1, 1));
        b1.setCopy(new Copy(b1, 2, 1));
        b1.setCopy(new Copy(b1, 3, 1));

        database.saveDocuments(b1);

        /////////////////////////////////////////////////////////////
        ArrayList<Copy> b2_copies = new ArrayList<Copy>();

        ArrayList<String> b2_authors = new ArrayList<String>();
        b1_authors.add("Erich Gamma");
        b1_authors.add("Ralph Johnson");
        b1_authors.add("John Vlissides");
        b1_authors.add("Richard Helm");

        ArrayList<String> b2_keywords = new ArrayList<String>();
        b1_keywords.add("none");

        Book b2 = new Book("Design Patterns: Elements of Reusable Object-Oriented Software", b2_authors,
                b2_keywords, 0, b2_copies, "Addison-Wesley Professional", 2003, "First edition", true);
        b1.setCopy(new Copy(b2, 1, 1));
        b1.setCopy(new Copy(b2, 2, 1));

        database.saveDocuments(b2);

        ///////////////////////////////////////////////////////////////
        ArrayList<Copy> b3_copies = new ArrayList<Copy>();

        ArrayList<String> b3_authors = new ArrayList<String>();
        b3_authors.add("Brooks.Jr.");
        b3_authors.add("Frederick P");

        ArrayList<String> b3_keywords = new ArrayList<String>();
        b1_keywords.add("none");

        Book b3 = new Book("The Mythical Man-month", b3_authors,
                b3_keywords, 0, b3_copies, "Addison-Wesley Longman Publishing Co.,Inc", 1995, "Second edition", false);

        database.saveDocuments(b3);
        //////////////////////////////////////////////////////////////////////////////////////////////////////////
        ArrayList<String> av1_keywords = new ArrayList<String>();
        av1_keywords.add("none");
        ArrayList<String> av1_authors = new ArrayList<String>();
        av1_authors.add("Tony Hoare");

        AVMaterial av1 = new AVMaterial("Null References: The Billion Dollar Mistake", av1_authors, av1_keywords, 0);
        database.saveDocuments(av1);
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ArrayList<String> av2_keywords = new ArrayList<String>();
        av1_keywords.add("none");
        ArrayList<String> av2_authors = new ArrayList<String>();
        av1_authors.add("Claude Shannon");

        AVMaterial av2 = new AVMaterial("Information Entropy", av1_authors, av1_keywords, 0);
        database.saveDocuments(av2);
        /////////////////////////////////////////////////////////////////////////////////////////////////

        ArrayList<Copy> p1_checkedOutCopies = new ArrayList<Copy>();
        ArrayList<Document> p1_requestedDocs = new ArrayList<Document>();
        UserCard p1 = new UserCard(1010, "Sergey", "Afonso", new Faculty(), "30001", "Via Margutta, 3", p1_checkedOutCopies, p1_requestedDocs);
        database.saveUserCard(p1);
        /////////////////////////////////////////////////////////////////
        ArrayList<Copy> p2_checkedOutCopies = new ArrayList<Copy>();
        ArrayList<Document> p2_requestedDocs = new ArrayList<Document>();
        UserCard p2 = new UserCard(1011, "Nadia", "Teixeira", new Student(), "30002", "Via Sacra, 13", p2_checkedOutCopies, p2_requestedDocs);
        database.saveUserCard(p2);
        //////////////////////////////////////////////////////////////////
        ArrayList<Copy> p3_checkedOutCopies = new ArrayList<Copy>();
        ArrayList<Document> p3_requestedDocs = new ArrayList<Document>();
        UserCard p3 = new UserCard(1012, "Elvira", "Espindola", new Student(), "30003", "Via del Corso, 22", p3_checkedOutCopies, p3_requestedDocs);
        database.saveUserCard(p2);
        //////////////////////////////////////////////////////////////////
        Assert.assertTrue("Number of documents equals to 8", (database.getAllDocuments().size() == 8));
        Assert.assertTrue("Number of users equals to 4", (database.getAllUsers().size() == 4));

    }

    /**
     * Initial state: TC1
     * Action: The librarian removes * 2 copies of book b1,
     *                               * 1 copy of book b3
     *                               * patron p2
     * Effect:  number of documents in the System is 5 and the number of users is 3.
     */
    @Test
    public void TestCase2() {
    }

    /**
     * Initial state:
     * Action:
     * Effect:
     */
    @Test
    public void TestCase3() {
    }

    /**
     * Initial state:
     * Action:
     * Effect:
     */
    @Test
    public void TestCase4() {
        Database database = new Database("Case4");
    }


    /**
     * Initial state:
     * Action:
     * Effect:
     */
    @Test
    public void TestCase5() {
        Database database = new Database("Case5");
    }


    /**
     * Initial state:
     * Action:
     * Effect:
     */
    @Test
    public void TestCase6() {
        Database database = new Database("Case6");
    }


    /**
     * Initial state:
     * Action:
     * Effect:
     */
    @Test
    public void TestCase7() {
    }

    /**
     * Initial state:
     * Action:
     * Effect:
     */
    @Test
    public void TestCase8() {
    }

    /**
     * Initial state:
     * Action:
     * Effect:
     */
    @Test
    public void TestCase9() {
    }

}
