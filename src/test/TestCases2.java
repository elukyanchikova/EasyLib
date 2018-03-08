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
        Session session = new Session(database.getUserCard(librarian_1.getId()).userType, 5, 3);
        Assert.assertTrue("Session is leading by  librarian.", Librarian.class.isAssignableFrom(session.getUser().getClass()));

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

        b3.setReference(true);
        b3.setCopy(new Copy(b3, 1, 1));

        database.saveDocuments(b3);
        //////////////////////////////////////////////////////////////////////////////////////////////////////////
        ArrayList<String> av1_keywords = new ArrayList<String>();
        av1_keywords.add("none");
        ArrayList<String> av1_authors = new ArrayList<String>();
        av1_authors.add("Tony Hoare");

        AVMaterial av1 = new AVMaterial("Null References: The Billion Dollar Mistake", av1_authors, av1_keywords, 0);
        database.saveDocuments(av1);
        av1.setCopy(new Copy(av1, 2, 1));
        database.saveDocuments(av1);
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ArrayList<String> av2_keywords = new ArrayList<String>();
        av1_keywords.add("none");
        ArrayList<String> av2_authors = new ArrayList<String>();
        av1_authors.add("Claude Shannon");

        AVMaterial av2 = new AVMaterial("Information Entropy", av1_authors, av1_keywords, 0);
        database.saveDocuments(av2);
        av2.setCopy(new Copy(av2, 2, 1));
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
    @Test
    public void TestCase2() {
        //Initial state
        Database database = new Database("Case1");
        TestCase1();
        database.load();
        Session session = new Session((database.getUserCard(1).userType), 5, 3);
        int a = 0;// number of all copies of all docs i.e. all physically available in library
        for (int i = 1; i <= database.getAllDocuments().size(); i++) {
            if (!(database.getDocuments(i).getNumberOfAllCopies() == 0)) {
                for (int j = 1; j <= database.getDocuments(i).getNumberOfAllCopies(); j++) {
                    a++;
                }
            }
        }
        Assert.assertTrue("Number of documents equals to 8", (a == 8));
        Assert.assertTrue("Number of users equals to 4", (database.getAllUsers().size() == 4));
        Assert.assertTrue("Session is leading by  librarian", Librarian.class.isAssignableFrom(session.getUser().getClass()));

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
            if (!(database.getDocuments(i).getNumberOfAllCopies() == 0)) {
                for (int j = 1; j <= database.getDocuments(i).getNumberOfAllCopies(); j++) {
                    b++;
                }
            }
        }
        Assert.assertTrue("Number of documents equals to 5", (b == 5));
        Assert.assertTrue("Number of users equals to 3", (database.getAllUsers().size() == 3));
    }

    /**
     * Initial state: TC1
     * Action: librarian checks the information of patron p1 and p3
     * Effect: got all the information
     */
    @Test
    public void TestCase3() {
        //Initial state
        Database database = new Database("Case1");
        TestCase1();
        database.load();
        Session session = new Session((database.getUserCard(1).userType), 9, 3);
        int c = 0;// number of all copies of all docs i.e. all physically available in library
        for (int i = 1; i <= database.getAllDocuments().size(); i++) {
            if (!(database.getDocuments(i).getNumberOfAllCopies() == 0)) {
                for (int j = 1; j <= database.getDocuments(i).getNumberOfAllCopies(); j++) {
                    c++;
                }
            } else {
                c++;
            }
        }
        Assert.assertTrue("Number of documents equals to 8", (c == 8));
        Assert.assertTrue("Number of users equals to 4", (database.getAllUsers().size() == 4));
        Assert.assertTrue("Session is leading by  librarian", Librarian.class.isAssignableFrom(session.getUser().getClass()));
        // Action and Effect
        UserCard librarian = database.getUserCard(1);
        UserCard first = database.getUserCard(1010);
        UserCard third = database.getUserCard(1012);

        Assert.assertTrue("Name of p1 is correct.", ((first.name.equals(database.getUserCard(1010).name))
                && (first.name.equals("Sergey"))));
        Assert.assertTrue("Surname of p1 is correct.", ((first.surname.equals(database.getUserCard(1010).surname))
                && (first.surname.equals("Afonso"))));
        Assert.assertTrue("Address of p1 is correct.", ((first.address.equals(database.getUserCard(1010).address)
                && (first.address.equals("Via Margutta, 3")))));
        Assert.assertTrue("Phone Number of p1 is correct.", ((first.phoneNumb.equals(database.getUserCard(1010).phoneNumb))
                && (first.phoneNumb.equals("30001"))));
        Assert.assertTrue("id of p1 is correct.", ((first.getId() == database.getUserCard(1010).getId())
                && (first.getId() == 1010)));
        Assert.assertTrue("User Type of p1 is correct", Faculty.class.isAssignableFrom(first.userType.getClass()));
        Assert.assertTrue("List of checked out documents is correct", first.checkedOutCopies==database.getUserCard(1010).checkedOutCopies);
        for (int i = 1; i <= first.checkedOutCopies.size() ; i++) {
            Assert.assertTrue("List of due dates of checked out documents is correct", first.checkedOutCopies.get(i).getDueDate().equals(database.getUserCard(1010).checkedOutCopies));
        }
        /////////////////////////////////////////////////////////////////////////////////////////////
        Assert.assertTrue("Name of p2 is correct.", ((third.name.equals(database.getUserCard(1012).name))
                && (third.name.equals("Elvira"))));
        Assert.assertTrue("Surname of p2 is correct.", ((third.surname.equals(database.getUserCard(1012).surname))
                && (third.surname.equals("Espindola"))));
        Assert.assertTrue("Address of p2 is correct.", ((third.address.equals(database.getUserCard(1012).address)
                && (third.address.equals("Via del Corso, 22")))));
        Assert.assertTrue("Phone Number of p2 is correct.", ((third.phoneNumb.equals(database.getUserCard(1012).phoneNumb))
                && (third.phoneNumb.equals("30003"))));
        Assert.assertTrue("id of p2 is correct.", ((third.getId() == database.getUserCard(1012).getId())
                && (third.getId() == 1012)));
        Assert.assertTrue("User Type of p2 is correct", Student.class.isAssignableFrom(third.userType.getClass()));
        Assert.assertTrue("List of checked out documents is correct", third.checkedOutCopies==database.getUserCard(1012).checkedOutCopies);
        for (int i = 1; i <= third.checkedOutCopies.size() ; i++) {
            Assert.assertTrue("List of due dates of checked out documents is correct", third.checkedOutCopies.get(i).getDueDate().equals(database.getUserCard(1012).checkedOutCopies));
        }
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
        Database database = new Database("Case1");
        database.resetDatabase();
        TestCase2();
        database.load();

        Assert.assertNull(database.getUserCard(1011));
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
        Assert.assertEquals(user1.checkedOutCopies.get(1).getDueDate(), "1 April");

        Assert.assertEquals(user1.checkedOutCopies.get(2).getDocumentID(), database.getDocuments(4).getID());
        Assert.assertEquals(user1.checkedOutCopies.get(2).getDueDate(), "18 March");

        UserCard user2 = database.getUserCard(1011);
        Assert.assertEquals(user2.name + " " + user2.surname, "Nadia Teixeira");
        Assert.assertEquals(user2.address, "Via Sacra, 13");
        Assert.assertEquals(user2.phoneNumb, "30002");
        Assert.assertEquals(user2.getId(), 1011);
        Assert.assertEquals(user2.userType.getClass().getName().replace("users.", ""), "Student");

        Assert.assertEquals(user2.checkedOutCopies.size(), 3);
        Assert.assertEquals(user2.checkedOutCopies.get(0).getDocumentID(), database.getDocuments(1).getID());
        Assert.assertEquals(user2.checkedOutCopies.get(0).getDueDate(), "25 March");

        Assert.assertEquals(user2.checkedOutCopies.get(1).getDocumentID(), database.getDocuments(2).getID());
        Assert.assertEquals(user2.checkedOutCopies.get(1).getDueDate(), "18 March");

        Assert.assertEquals(user2.checkedOutCopies.get(2).getDocumentID(), database.getDocuments(5).getID());
        Assert.assertEquals(user2.checkedOutCopies.get(2).getDueDate(), "18 March");
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
        Database database = new Database("Case1");
        database.resetDatabase();
        TestCase1();

        database.load();


        Session session = new Session(database.getUserCard(1).userType, 9, 3);

        session.endSession();


        Database database1 = new Database("Case1");
        database.resetDatabase();
        TestCase1();

        database.load();

        Session session1 = new Session(database.getUserCard(1).userType,1,1);

        Assert.assertEquals(database.getDocuments(1).availableCopies.size(), 1);
        Assert.assertEquals(database.getDocuments(2).availableCopies.size(), 2);
        Assert.assertEquals(database.getDocuments(3).availableCopies.size(), 1);
        Assert.assertEquals(database.getDocuments(4).availableCopies.size(), 1);
        Assert.assertEquals(database.getDocuments(5).availableCopies.size(), 1);
        Assert.assertNotNull(database.getUserCard(1010));
        Assert.assertNotNull(database.getUserCard(1011));
        Assert.assertNotNull(database.getUserCard(1012));





    }

}
