import documents.*;
import forms.MainForm;
import org.junit.Assert;
import org.junit.Test;
import storage.DatabaseManager;
import users.*;

import java.util.ArrayList;


public class TestCases2 {

    final static String DATABASE_FILE_NAME = "TestCases";
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
        DatabaseManager databaseManager = new DatabaseManager(DATABASE_FILE_NAME);
        databaseManager.resetDatabase();

        ArrayList<Copy> l1_checkedOutCopies = new ArrayList<Copy>();
        ArrayList<Document> l1_requestedDocuments = new ArrayList<Document>();

        UserCard librarian_1 = new UserCard("Irma", "Pins", new Librarian(), "8981351785", "north of London",
                l1_checkedOutCopies, l1_requestedDocuments);
        databaseManager.saveUserCard(librarian_1);
        Session session = new Session(databaseManager.getUserCard(librarian_1.getId()).userType, 5, 3);
        Assert.assertTrue("Session is leading by  librarian.", Librarian.class.isAssignableFrom(session.getUser().getClass()));

        Assert.assertTrue("The databaseManager contains only one user", databaseManager.getAllUsers().size() == 1);
        Assert.assertTrue("This only one user is a librarian.", (Librarian.class.isAssignableFrom(databaseManager.getUserCard(1).userType.getClass())));
        Assert.assertTrue("There is no any documents in the databaseManager.", databaseManager.getAllDocuments().size() == 0);

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
                b2_keywords, 0, b2_copies, "Addison-Wesley Professional", 2003, "First edition", true);
        databaseManager.saveDocuments(b2);
        b2.setCopy(new Copy(b2, 1, 1));
        b2.setCopy(new Copy(b2, 2, 1));

        databaseManager.saveDocuments(b2);

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

        databaseManager.saveDocuments(b3);
        //////////////////////////////////////////////////////////////////////////////////////////////////////////
        ArrayList<String> av1_keywords = new ArrayList<String>();
        av1_keywords.add("none");
        ArrayList<String> av1_authors = new ArrayList<String>();
        av1_authors.add("Tony Hoare");

        AVMaterial av1 = new AVMaterial("Null References: The Billion Dollar Mistake", av1_authors, av1_keywords, 0);
        databaseManager.saveDocuments(av1);
        av1.setCopy(new Copy(av1, 2, 1));
        databaseManager.saveDocuments(av1);
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ArrayList<String> av2_keywords = new ArrayList<String>();
        av2_keywords.add("none");
        ArrayList<String> av2_authors = new ArrayList<String>();
        av2_authors.add("Claude Shannon");

        AVMaterial av2 = new AVMaterial("Information Entropy", av2_authors, av2_keywords, 0);
        databaseManager.saveDocuments(av2);
        av2.setCopy(new Copy(av2, 2, 1));
        databaseManager.saveDocuments(av2);

        /////////////////////////////////////////////////////////////////////////////////////////////////

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
        //////////////////////////////////////////////////////////////////
        //Effect
        int a = 0;// number of all copies of all docs i.e. all physically available in library
        for (int i = 1; i <= databaseManager.getAllDocuments().size(); i++) {
            if (!(databaseManager.getDocuments(i).getNumberOfAllCopies() == 0)) {
                for (int j = 1; j <= databaseManager.getDocuments(i).getNumberOfAllCopies(); j++) {
                    a++;
                }
            }
        }
        Assert.assertTrue("Number of documents equals to 8", (a == 8));
        Assert.assertTrue("Number of users equals to 4", (databaseManager.getAllUsers().size() == 4));
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
        TestCase1();
        DatabaseManager databaseManager = new DatabaseManager(DATABASE_FILE_NAME);

        Session session = new Session((databaseManager.getUserCard(1).userType), 5, 3);
        int a = 0;// number of all copies of all docs i.e. all physically available in library
        for (int i = 1; i <= databaseManager.getAllDocuments().size(); i++) {
            if (!(databaseManager.getDocuments(i).getNumberOfAllCopies() == 0)) {
                for (int j = 1; j <= databaseManager.getDocuments(i).getNumberOfAllCopies(); j++) {
                    a++;
                }
            }
        }
        Assert.assertTrue("Number of documents equals to 8", (a == 8));
        Assert.assertTrue("Number of users equals to 4", (databaseManager.getAllUsers().size() == 4));
        Assert.assertTrue("Session is leading by  librarian", Librarian.class.isAssignableFrom(session.getUser().getClass()));

        //Action
        //Book b1 = (Book)databaseManager.getDocuments(1);
        databaseManager.getDocuments(1).removeCopy(databaseManager.getDocuments(1).availableCopies.get(1));
        databaseManager.saveDocuments(databaseManager.getDocuments(1));
        databaseManager.getDocuments(1).removeCopy(databaseManager.getDocuments(1).availableCopies.get(0));
        databaseManager.saveDocuments(databaseManager.getDocuments(1));
        databaseManager.getDocuments(3).removeCopy(databaseManager.getDocuments(3).availableCopies.get(0));
        databaseManager.saveDocuments(databaseManager.getDocuments(3));

        databaseManager.removeUserCard(databaseManager.getUserCard(1011));
        // Effect
        int b = 0;// number of all copies of all docs i.e. all physically available in library
        for (int i = 1; i <= databaseManager.getAllDocuments().size(); i++) {
            if (!(databaseManager.getDocuments(i).getNumberOfAllCopies() == 0)) {
                for (int j = 1; j <= databaseManager.getDocuments(i).getNumberOfAllCopies(); j++) {
                    b++;
                }
            }
        }
        Assert.assertTrue("Number of documents equals to 5", (b == 5));
        Assert.assertTrue("Number of users equals to 3", (databaseManager.getAllUsers().size() == 3));
    }

    /**
     * Initial state: TC1
     * Action: librarian checks the information of patron p1 and p3
     * Effect: got all the information
     */
    @Test
    public void TestCase3() {
        //Initial state
        TestCase1();
        DatabaseManager databaseManager = new DatabaseManager(DATABASE_FILE_NAME);

        Session session = new Session((databaseManager.getUserCard(1).userType), 9, 3);
        int c = 0;// number of all copies of all docs i.e. all physically available in library
        for (int i = 1; i <= databaseManager.getAllDocuments().size(); i++) {
            if (!(databaseManager.getDocuments(i).getNumberOfAllCopies() == 0)) {
                for (int j = 1; j <= databaseManager.getDocuments(i).getNumberOfAllCopies(); j++) {
                    c++;
                }
            } else {
                c++;
            }
        }
        Assert.assertTrue("Number of documents equals to 8", (c == 8));
        Assert.assertTrue("Number of users equals to 4", (databaseManager.getAllUsers().size() == 4));
        Assert.assertTrue("Session is leading by  librarian", Librarian.class.isAssignableFrom(session.getUser().getClass()));
        // Action and Effect
        UserCard first = databaseManager.getUserCard(1010);
        UserCard third = databaseManager.getUserCard(1100);

        Assert.assertTrue("Name of p1 is correct.", ((first.name.equals(databaseManager.getUserCard(1010).name))
                && (first.name.equals("Sergey"))));
        Assert.assertTrue("Surname of p1 is correct.", ((first.surname.equals(databaseManager.getUserCard(1010).surname))
                && (first.surname.equals("Afonso"))));
        Assert.assertTrue("Address of p1 is correct.", ((first.address.equals(databaseManager.getUserCard(1010).address)
                && (first.address.equals("Via Margutta, 3")))));
        Assert.assertTrue("Phone Number of p1 is correct.", ((first.phoneNumb.equals(databaseManager.getUserCard(1010).phoneNumb))
                && (first.phoneNumb.equals("30001"))));
        Assert.assertTrue("id of p1 is correct.", ((first.getId() == databaseManager.getUserCard(1010).getId())
                && (first.getId() == 1010)));
        Assert.assertTrue("User Type of p1 is correct", Faculty.class.isAssignableFrom(first.userType.getClass()));
        Assert.assertTrue("List of checked out documents is correct", first.checkedOutCopies== databaseManager.getUserCard(1010).checkedOutCopies);
        for (int i = 1; i <= first.checkedOutCopies.size() ; i++) {
            Assert.assertTrue("List of due dates of checked out documents is correct", first.checkedOutCopies.get(i).getDueDate().equals(databaseManager.getUserCard(1010).checkedOutCopies));
        }
        /////////////////////////////////////////////////////////////////////////////////////////////
        Assert.assertTrue("Name of p2 is correct.", ((third.name.equals(databaseManager.getUserCard(1100).name))
                && (third.name.equals("Elvira"))));
        Assert.assertTrue("Surname of p2 is correct.", ((third.surname.equals(databaseManager.getUserCard(1100).surname))
                && (third.surname.equals("Espindola"))));
        Assert.assertTrue("Address of p2 is correct.", ((third.address.equals(databaseManager.getUserCard(1100).address)
                && (third.address.equals("Via del Corso, 22")))));
        Assert.assertTrue("Phone Number of p2 is correct.", ((third.phoneNumb.equals(databaseManager.getUserCard(1100).phoneNumb))
                && (third.phoneNumb.equals("30003"))));
        Assert.assertTrue("id of p2 is correct.", ((third.getId() == databaseManager.getUserCard(1100).getId())
                && (third.getId() == 1100)));
        Assert.assertTrue("User Type of p2 is correct", Student.class.isAssignableFrom(third.userType.getClass()));
        Assert.assertTrue("List of checked out documents is correct", third.checkedOutCopies== databaseManager.getUserCard(1100).checkedOutCopies);
        for (int i = 1; i <= third.checkedOutCopies.size() ; i++) {
            Assert.assertTrue("List of due dates of checked out documents is correct", third.checkedOutCopies.get(i).getDueDate().equals(databaseManager.getUserCard(1100).checkedOutCopies));
        }
    }

    /**
     * Initial state:  TC2
     * Action: The librarian checks the information of patron p2 and p3
     * Effect: got the information about p3 but not p3
     */
    @Test
    public void TestCase4() {
        TestCase2();
        DatabaseManager databaseManager = new DatabaseManager(DATABASE_FILE_NAME);

        Assert.assertNull(databaseManager.getUserCard(1011));

        Assert.assertEquals(databaseManager.getUserCard(1100).name,"Elvira");
        Assert.assertEquals(databaseManager.getUserCard(1100).address,"Via del Corso, 22");
        Assert.assertEquals(databaseManager.getUserCard(1100).phoneNumb,"30003");
        Assert.assertEquals(databaseManager.getUserCard(1100).userType.getClass().getName().replace("users.",""),"Student");
        Assert.assertEquals(databaseManager.getUserCard(1100).getId(),1100);

    }

    /**
     * Initial state: TC2
     * Action: Patron p2 checks out book b1
     * Effect: nothing happen
     */
    @Test
    public void TestCase5() {
        TestCase2();
        DatabaseManager databaseManager = new DatabaseManager(DATABASE_FILE_NAME);

        Assert.assertNull(databaseManager.getUserCard(1011));
    }

    /**
     * Initial state: TC2
     * Action:
     * p1 checks out b1
     * p3 checks out b1
     * p1 checks out b2
     * the librarian checks the information of p1 and p3
     * Effect: correct information about users
     */
    @Test
    public void TestCase6() {
        TestCase2();
        DatabaseManager databaseManager = new DatabaseManager(DATABASE_FILE_NAME);

        Session session = new Session((databaseManager.getUserCard(1010).userType), 5, 3);
        session.userCard = databaseManager.getUserCard(1010);
        MainForm mainForm = new MainForm();
        mainForm.setSession(session);
        mainForm.setDatabaseManager(databaseManager);

        mainForm.selectDocument(0);
        mainForm.checkOut(databaseManager.getDocuments(1));
        ///////////////////////////////////////////////////////////
        Session session1 = new Session((databaseManager.getUserCard(1100).userType),5,3);
        session1.userCard = databaseManager.getUserCard(1100);
        mainForm.setSession(session1);

        mainForm.selectDocument(0);
        mainForm.checkOut(databaseManager.getDocuments(1));

        mainForm.selectDocument(1);
        mainForm.checkOut(databaseManager.getDocuments(2));
        ///////////////////////////////////////////////////////////

        Assert.assertEquals(databaseManager.getUserCard(1010).name,"Sergey");
        Assert.assertEquals(databaseManager.getUserCard(1010).address,"Via Margutta, 3");
        Assert.assertEquals(databaseManager.getUserCard(1010).phoneNumb,"30001");
        Assert.assertEquals(databaseManager.getUserCard(1010).userType.getClass().getName().replace("users.",""),"Faculty");
        Assert.assertEquals(databaseManager.getUserCard(1010).getId(),1010);
        Assert.assertEquals(databaseManager.getUserCard(1010).checkedOutCopies.size(), 1);
        Assert.assertEquals(databaseManager.getUserCard(1010).checkedOutCopies.get(0).getDueDate(), "1 April");

        Assert.assertEquals(databaseManager.getUserCard(1100).name,"Elvira");
        Assert.assertEquals(databaseManager.getUserCard(1100).address,"Via del Corso, 22");
        Assert.assertEquals(databaseManager.getUserCard(1100).phoneNumb,"30003");
        Assert.assertEquals(databaseManager.getUserCard(1100).userType.getClass().getName().replace("users.",""),"Student");
        Assert.assertEquals(databaseManager.getUserCard(1100).getId(),1100);
        Assert.assertEquals(databaseManager.getUserCard(1100).checkedOutCopies.size(), 1);
        Assert.assertEquals(databaseManager.getUserCard(1100).checkedOutCopies.get(0).getDueDate(), "18 March");

    }

    /**
     * Initial state: TC1
     * Action:
     * p1 checks out books b1, b2, b3 and video material av1
     * p2 checks out books b1, b2 and video material av2
     * the librarian checks the information of p1 and p2
     * Effect: correct information about users
     */
    @Test
    public void TestCase7(){
        TestCase1();
        DatabaseManager databaseManager = new DatabaseManager(DATABASE_FILE_NAME);

        ///////////////////////////////////////////////////////////////////////////////////////
        Session session = new Session((databaseManager.getUserCard(1010).userType), 5, 3);
        session.userCard = databaseManager.getUserCard(1010);
        MainForm mainForm = new MainForm();
        mainForm.setSession(session);
        mainForm.setDatabaseManager(databaseManager);

        mainForm.selectDocument(0);
        mainForm.checkOut(databaseManager.getDocuments(1));
        mainForm.selectDocument(1);
        mainForm.checkOut(databaseManager.getDocuments(2));
        mainForm.selectDocument(2);
        mainForm.checkOut(databaseManager.getDocuments(3));
        mainForm.selectDocument(3);
        mainForm.checkOut(databaseManager.getDocuments(4));
        /////////////////////////////////////////////////////////////////////////////////////////
        Session session1 = new Session((databaseManager.getUserCard(1011).userType), 5, 3);
        session1.userCard = databaseManager.getUserCard(1011);
        mainForm.setSession(session1);

        mainForm.selectDocument(0);
        mainForm.checkOut(databaseManager.getDocuments(1));
        mainForm.selectDocument(1);
        mainForm.checkOut(databaseManager.getDocuments(2));
        mainForm.selectDocument(4);
        mainForm.checkOut(databaseManager.getDocuments(5 ));
        //////////////////////////////////////////////////////////////////////////////////////////
        Session session2 = new Session((databaseManager.getUserCard(1).userType), 5, 3);
        session2.userCard = databaseManager.getUserCard(1);
        mainForm.setSession(session2);

        UserCard user1 = databaseManager.getUserCard(1010);
        Assert.assertEquals(user1.name + " " + user1.surname, "Sergey Afonso");
        Assert.assertEquals(user1.address, "Via Margutta, 3");
        Assert.assertEquals(user1.phoneNumb, "30001");
        Assert.assertEquals(user1.getId(), 1010);
        Assert.assertEquals(user1.userType.getClass().getName().replace("users.", ""), "Faculty");

        Assert.assertEquals(user1.checkedOutCopies.size(), 3);
        Assert.assertEquals(user1.checkedOutCopies.get(0).getDocumentID(), databaseManager.getDocuments(1).getID());
        Assert.assertEquals(user1.checkedOutCopies.get(0).getDueDate(), "1 April");

        Assert.assertEquals(user1.checkedOutCopies.get(1).getDocumentID(), databaseManager.getDocuments(2).getID());
        Assert.assertEquals(user1.checkedOutCopies.get(1).getDueDate(), "1 April");

        Assert.assertEquals(user1.checkedOutCopies.get(2).getDocumentID(), databaseManager.getDocuments(4).getID());
        Assert.assertEquals(user1.checkedOutCopies.get(2).getDueDate(), "18 March");

        UserCard user2 = databaseManager.getUserCard(1011);
        Assert.assertEquals(user2.name + " " + user2.surname, "Nadia Teixeira");
        Assert.assertEquals(user2.address, "Via Sacra, 13");
        Assert.assertEquals(user2.phoneNumb, "30002");
        Assert.assertEquals(user2.getId(), 1011);
        Assert.assertEquals(user2.userType.getClass().getName().replace("users.", ""), "Student");

        Assert.assertEquals(user2.checkedOutCopies.size(), 3);
        Assert.assertEquals(user2.checkedOutCopies.get(0).getDocumentID(), databaseManager.getDocuments(1).getID());
        Assert.assertEquals(user2.checkedOutCopies.get(0).getDueDate(), "25 March");

        Assert.assertEquals(user2.checkedOutCopies.get(1).getDocumentID(), databaseManager.getDocuments(2).getID());
        Assert.assertEquals(user2.checkedOutCopies.get(1).getDueDate(), "18 March");

        Assert.assertEquals(user2.checkedOutCopies.get(2).getDocumentID(), databaseManager.getDocuments(5).getID());
        Assert.assertEquals(user2.checkedOutCopies.get(2).getDueDate(), "18 March");
    }


    /**
     * Initial state:
     * p1 checked-out b1 on February 9th and b2 on February 2nd
     * p2 checked-out b1 on February 5th and av1 on February 17th
     * Action:
     * the librarian checks the due dates of documents checked out by p1
     * the librarian checks the due dates of documents checked out by p2
     * Effect: overdue should be right
     */
    @Test
    public void TestCase8() {
        TestCase1();
        DatabaseManager databaseManager = new DatabaseManager(DATABASE_FILE_NAME);

        Session session = new Session((databaseManager.getUserCard(1010).userType), 9, 2);
        session.userCard = databaseManager.getUserCard(1010);
        MainForm mainForm = new MainForm();
        mainForm.setSession(session);
        mainForm.setDatabaseManager(databaseManager);

        mainForm.selectDocument(0);
        mainForm.checkOut(databaseManager.getDocuments(1));

        Session session1 = new Session((databaseManager.getUserCard(1010).userType), 2, 2);
        session1.userCard = databaseManager.getUserCard(1010);
        mainForm.setSession(session1);

        mainForm.selectDocument(1);
        mainForm.checkOut(databaseManager.getDocuments(2));
        ///////////////////////////////////////////////////////////////////////////////
        Session session2 = new Session((databaseManager.getUserCard(1011).userType), 5, 2);
        session2.userCard = databaseManager.getUserCard(1011);
        mainForm.setSession(session2);

        mainForm.selectDocument(0);
        mainForm.checkOut(databaseManager.getDocuments(1));

        Session session3 = new Session((databaseManager.getUserCard(1011).userType), 17, 2);
        session3.userCard = databaseManager.getUserCard(1011);
        mainForm.setSession(session3);
        mainForm.selectDocument(3);
        mainForm.checkOut(databaseManager.getDocuments(4 ));
        ////////////////////////////////////////////////////////////////////////////////////
        Session sessionLib = new Session((databaseManager.getUserCard(1).userType), 5, 3);
        sessionLib.userCard = databaseManager.getUserCard(1);
        mainForm.setSession(sessionLib);

        UserCard user1 = databaseManager.getUserCard(1010);

        Assert.assertEquals(user1.checkedOutCopies.size(), 2);
        Assert.assertEquals(user1.checkedOutCopies.get(0).getDocumentID(), databaseManager.getDocuments(1).getID());
        Assert.assertEquals(user1.checkedOutCopies.get(0).getOverdue(sessionLib), -1);

        Assert.assertEquals(user1.checkedOutCopies.get(1).getDocumentID(), databaseManager.getDocuments(2).getID());
        Assert.assertEquals(user1.checkedOutCopies.get(1).getOverdue(sessionLib), 3);

        UserCard user2 = databaseManager.getUserCard(1011);

        Assert.assertEquals(user2.checkedOutCopies.size(), 2);
        Assert.assertEquals(user2.checkedOutCopies.get(0).getDocumentID(), databaseManager.getDocuments(1).getID());
        Assert.assertEquals(user2.checkedOutCopies.get(0).getOverdue(sessionLib), 7);

        Assert.assertEquals(user2.checkedOutCopies.get(1).getDocumentID(), databaseManager.getDocuments(4).getID());
        Assert.assertEquals(user2.checkedOutCopies.get(1).getOverdue(sessionLib), 2);
    }

    /**
     * Initial state: TC1
     * Action:
     * simulate a power loss (stop the application)
     * re-run the application
     * the librarian checks patrons and documents of the system
     * Effect: all information should be saved
     */
    @Test
    public void TestCase9() {
        TestCase1();
        DatabaseManager databaseManager = new DatabaseManager(DATABASE_FILE_NAME);

        Session session = new Session(databaseManager.getUserCard(1).userType, 9, 3);

        session.endSession();

        DatabaseManager databaseManager1 = new DatabaseManager(DATABASE_FILE_NAME);

        Assert.assertEquals(databaseManager1.getDocuments(1).availableCopies.size(), 3);
        Assert.assertEquals(databaseManager1.getDocuments(2).availableCopies.size(), 2);
        Assert.assertEquals(databaseManager1.getDocuments(3).availableCopies.size(), 1);
        Assert.assertEquals(databaseManager1.getDocuments(4).availableCopies.size(), 1);
        Assert.assertEquals(databaseManager1.getDocuments(5).availableCopies.size(), 1);
        Assert.assertNotNull(databaseManager1.getUserCard(1010));
        Assert.assertNotNull(databaseManager1.getUserCard(1011));
        Assert.assertNotNull(databaseManager1.getUserCard(1100));

    }

}
