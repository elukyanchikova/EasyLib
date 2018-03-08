import documents.*;
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
     * * 2 copies of book b2,
     * * 1 copy of book b3,
     * * 2 Video materials: av1 and av2
     * * patrons p1, p2 and p3
     * Effect: number of documents in the System is 8 and the number of users is 4.
     */
    @Test
    public void TestCase1() {

        //Initial state
        Database database = new Database("Case1");
        database.resetDatabase();
        database.load();

        ArrayList<Copy> l1_checkedOutCopies = new ArrayList<Copy>();
        ArrayList<Document> l1_requestedDocuments = new ArrayList<Document>();

        UserCard librarian_1 = new UserCard("Irma", "Pins", new Librarian(), "8981351785", "north of London",
                l1_checkedOutCopies, l1_requestedDocuments);
        database.saveUserCard(librarian_1);
        Session session = new Session(database.getUserCard(librarian_1.getId()).userType, 9, 3);

        Assert.assertTrue("The database contains only one user", database.getAllUsers().size() == 1);
        Assert.assertTrue("This only one user is a librarian.", (Librarian.class.isAssignableFrom(database.getUserCard(1).userType.getClass())));
        Assert.assertTrue("There is no any documents in the database.", database.getAllDocuments().size() == 0);

        ///////////////////////////////////////////////////////////////
        //Action
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
        database.saveDocuments(b1);

        b1.setCopy(new Copy(b1, 1, 1));
        b1.setCopy(new Copy(b1, 2, 1));
        b1.setCopy(new Copy(b1, 3, 1));

        database.saveDocuments(b1);

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
                b2_keywords, 0, b2_copies, "Addison-Wesley Professional", 2003, "First edition", true);
        database.saveDocuments(b2);
        b2.setCopy(new Copy(b2, 1, 1));
        b2.setCopy(new Copy(b2, 2, 1));

        database.saveDocuments(b2);

        ///////////////////////////////////////////////////////////////
        ArrayList<Copy> b3_copies = new ArrayList<Copy>();

        ArrayList<String> b3_authors = new ArrayList<String>();
        b3_authors.add("Brooks.Jr.");
        b3_authors.add("Frederick P");

        ArrayList<String> b3_keywords = new ArrayList<String>();
        b3_keywords.add("none");

        Book b3 = new Book("The Mythical Man-month", b3_authors,
                b3_keywords, 0, b3_copies, "Addison-Wesley Longman Publishing Co.,Inc", 1995, "Second edition", false);
        database.saveDocuments(b3);

        b3.setCopy(new Copy(b3, 1, 1));

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
        database.saveUserCard(p3);
        //////////////////////////////////////////////////////////////////
        //Effect
        int a = 0;// number of all copies of all docs i.e. all physically available in library
        for (int i = 1; i <= database.getAllDocuments().size(); i++) {
            if (!(database.getDocuments(i).getNumberOfAllCopies() == 0)) {
                for (int j = 1; j <= database.getDocuments(i).getNumberOfAllCopies(); j++) {
                    a++;
                }
            } else {
                a++;
            }

        }
        Assert.assertTrue("Number of documents equals to 8", (a == 8));
        Assert.assertTrue("Number of users equals to 4", (database.getAllUsers().size() == 4));

    }

    /**
     * Initial state: TC1
     * Action: The librarian removes * 2 copies of book b1,
     * * 1 copy of book b3
     * * patron p2
     * Effect:  number of documents in the System is 5 and the number of users is 3.
     */
 /*   @Test
    public void TestCase2() {
        //Initial state
        Database database = new Database("Case1");
        TestCase1();
        database.load();
        Session session = new Session((database.getUserCard(1).userType), 9, 3);
        int a = 0;// number of all copies of all docs i.e. all physically available in library
        for (int i = 1; i <= database.getAllDocuments().size(); i++) {
            if (!(database.getDocuments(i).getNumberOfAllCopies() == 0)) {
                for (int j = 1; j <= database.getDocuments(i).getNumberOfAllCopies(); j++) {
                    a++;
                }
            } else {
                a++;
            }
        }
        Assert.assertTrue("Number of documents equals to 8", (a == 8));
        Assert.assertTrue("Number of users equals to 4", (database.getAllUsers().size() == 4));

        //Action
        //Book b1 = (Book)database.getDocuments(1);
        database.getDocuments(1).removeCopy(database.getDocuments(1).availableCopies.get(1));
        database.saveDocuments(database.getDocuments(1));
        database.getDocuments(1).removeCopy(database.getDocuments(1).availableCopies.get(0));
        database.saveDocuments(database.getDocuments(1));

        database.getDocuments(3).removeCopy(database.getDocuments(3).availableCopies.get(0));
        database.saveDocuments(database.getDocuments(3));

        database.removeUserCard(database.getUserCard(1011));
        // Effect
        int b = 0;// number of all copies of all docs i.e. all physically available in library
        for (int i = 1; i <= database.getAllDocuments().size(); i++) {
            if ((database.getDocuments(i).getNumberOfAllCopies() == 0) && ((database.getDocuments(i).getClass() == AVMaterial.class) ||
                    (database.getDocuments(i).getClass() == JournalArticle.class))) {
                b=b+1;
            } else if (!(database.getDocuments(i).getNumberOfAllCopies() == 0) && (database.getDocuments(i).getClass() == Book.class)) {
                for (int j = 1; j <= database.getDocuments(i).getNumberOfAllCopies(); j++) {
                    b=b+1;
                }
            }

        }

        Assert.assertTrue("Number of documents equals to 5", (b == 5));
        Assert.assertTrue("Number of users equals to 3",(database.getAllUsers().size() ==3));
}
*/



/**
 * Initial state:
 * Action:
 * Effect:
 */
@Test
public void TestCase3(){
        }

/**
 * Initial state:
 * Action:
 * Effect:
 */
@Test
public void TestCase4(){
        Database database=new Database("Case4");
        }


/**
 * Initial state:
 * Action:
 * Effect:
 */
@Test
public void TestCase5(){
        Database database=new Database("Case5");
        }


/**
 * Initial state:
 * Action:
 * Effect:
 */
@Test
public void TestCase6(){
        Database database=new Database("Case6");
        }


<<<<<<< HEAD
/**
 * Initial state:
 * Action:
 * Effect:
 */
@Test
public void TestCase7(){
    Database database = new Database("Case1");
    database.resetDatabase();
    TestCase1();
    database.load();

    ///////////////////////////////////////////////////////////////////////////////////////
    Session session = new Session((database.getUserCard(1010).userType), 5, 3);
    session.userCard = database.getUserCard(1010);
    MainForm mainForm = new MainForm();
    mainForm.setSession(session);
    mainForm.setDatabase(database);

    mainForm.selectDocument(0);
    mainForm.checkOut(database.getDocuments(1));
    mainForm.selectDocument(1);
    mainForm.checkOut(database.getDocuments(2));
    mainForm.selectDocument(2);
    mainForm.checkOut(database.getDocuments(3));
    mainForm.selectDocument(3);
    mainForm.checkOut(database.getDocuments(4));
    /////////////////////////////////////////////////////////////////////////////////////////
    Session session1 = new Session((database.getUserCard(1011).userType), 5, 3);
    session1.userCard = database.getUserCard(1011);
    mainForm.setSession(session1);

    mainForm.selectDocument(0);
    mainForm.checkOut(database.getDocuments(1));
    mainForm.selectDocument(1);
    mainForm.checkOut(database.getDocuments(2));
    mainForm.selectDocument(4);
    mainForm.checkOut(database.getDocuments(5 ));
    //////////////////////////////////////////////////////////////////////////////////////////
    Session session2 = new Session((database.getUserCard(1).userType), 5, 3);
    session2.userCard = database.getUserCard(1);
    mainForm.setSession(session2);

    UserCard user1 = database.getUserCard(1010);
    Assert.assertEquals(user1.name + " " + user1.surname, "Sergey Afonso");
    Assert.assertEquals(user1.address, "Via Margutta, 3");
    Assert.assertEquals(user1.phoneNumb, "30001");
    Assert.assertEquals(user1.getId(), 1010);
    Assert.assertEquals(user1.userType.getClass().getName().replace("users.", ""), "Faculty");

    Assert.assertEquals(user1.checkedOutCopies.size(), 3);
    Assert.assertEquals(user1.checkedOutCopies.get(0).getDocumentID(), database.getDocuments(1).getID());
    Assert.assertEquals(user1.checkedOutCopies.get(0).getDueDate(), "1 April");

    Assert.assertEquals(user1.checkedOutCopies.get(1).getDocumentID(), database.getDocuments(2).getID());
    Assert.assertEquals(user1.checkedOutCopies.get(1).getDueDate(), "18 March");

    Assert.assertEquals(user1.checkedOutCopies.get(2).getDocumentID(), database.getDocuments(4).getID());
    Assert.assertEquals(user1.checkedOutCopies.get(2).getDueDate(), "18 March");
}
=======
    /**
     * Initial state:
     * Action:
     * Effect:
     */
    @Test
    public void TestCase7() {
    }
>>>>>>> 85210f07d63a6e197e9b2e19e268296ad9805544

/**
 * Initial state:
 * Action:
 * Effect:
 */
@Test
public void TestCase8(){
        }

/**
 * Initial state:
 * Action:
 * Effect:
 */
@Test
public void TestCase9(){
        }

        }
