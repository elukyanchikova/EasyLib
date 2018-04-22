

import documents.Book;
import documents.Copy;
import documents.Document;
import forms.MainForm;
import org.junit.*;
import storage.DatabaseManager;
import users.*;
import users.userTypes.*;

import java.util.ArrayList;
import java.util.Arrays;

public class TestCases1 {

    /*
/**
     * Initial state: 1 patron, 1 librian, 2 copies of book 'b'(not a reference)
     * Action: Patron p checks out a copy of book b
     * Effect: 1 copy is checked out by 'p', 1 copy is in the library
     *//*




    private DatabaseManager databaseManager = new DatabaseManager("TestCases1");

    public void initialState(){
        databaseManager.resetDatabase();

        Book d1 = new Book("Introduction to Algorithms",
                new ArrayList<>(Arrays.asList("Thomas H. Cormen", "Charles E. Leiserson", "Ronald L. Rivest", "Clifford Stein")),
                new ArrayList<>(), 5000, "MIT Press", 2009, "Third edition", true);
        for (int i = 0; i < 3; i++)
            d1.setCopy(new Copy(d1, 3, 310));
        databaseManager.saveDocuments(d1);


        ArrayList<Copy> l1_checkedOutCopies = new ArrayList<Copy>();
        ArrayList<Document> l1_requestedDocuments = new ArrayList<Document>();
        UserCard librarian_1 = new UserCard("Irma", "Pins", new Librarian(), "8981351785", "north of London",
                l1_checkedOutCopies, l1_requestedDocuments);
        databaseManager.saveUserCard(librarian_1);

        UserCard v = new UserCard(1, "Veronika", "Rama", new Student(), "30005", "Stret Atocha, 27");
        databaseManager.saveUserCard(v);

    }

    @Test
    public void testCase1(){
        ArrayList<Document> documents = databaseManager.getAllDocuments();
        ArrayList<UserCard> userCards = databaseManager.getAllUsers();

        int user1ID = 1;
        int doc1ID = 0;

        int numberOfCopies;

        Session session = new Session(databaseManager.getUserCard(user1ID).userType,1,1);
        session.userCard = userCards.get(user1ID);

        Assert.assertTrue(Patron.class.isAssignableFrom(session.getUser().getClass()));
        Assert.assertTrue(session.userCard.checkedOutCopies.isEmpty());

        Assert.assertTrue(Book.class.isAssignableFrom(documents.get(doc1ID).getClass()));
        Assert.assertEquals(documents.get(doc1ID).getNumberOfAllCopies(), 2);

        numberOfCopies = session.userCard.checkedOutCopies.size();

        MainForm mainForm = new MainForm();
        mainForm.setSession(session);

        mainForm.setDatabaseManager(databaseManager);
        mainForm.setActionManager(databaseManager.actionManager);

        mainForm.checkOut(documents.get(doc1ID));


        Assert.assertEquals(session.userCard.checkedOutCopies.size(),numberOfCopies + 1);
        Assert.assertTrue(session.userCard.checkedOutCopies.contains(documents.get(doc1ID)));
    }

  */
/*  *
     * Initial state: 1 patron, 1 librarian, no books by author A
     * Action: patron 'p' checks out book by A.
     * Effect: The system does not change its state. Maybe a message notifying the patron can read: the
     library does not have book 'b'*//*

    @Test
    public void testCase2() {
        ArrayList<Document> documents = databaseManager.getAllDocuments();
        ArrayList<UserCard> userCards = databaseManager.getAllUsers();

        int user1ID = 1;
        int doc1ID = 0;

        String authorName = "No Any Name";
        int numberOfCopies;

        Session session = new Session(userCards.get(user1ID).userType, 1, 1);
        session.userCard = userCards.get(user1ID);

        numberOfCopies = userCards.get(user1ID).checkedOutCopies.size();

        Assert.assertTrue(Patron.class.isAssignableFrom(session.getUser().getClass()));

        Document doc = null;
        for (int i = 0; i < documents.size(); i++) {
            for (int j = 0; j < documents.get(i).authors.size(); j++) {
                if (documents.get(i).authors.get(j).toLowerCase().equals(authorName.toLowerCase())) {
                    doc = documents.get(i);
                }
            }
        }

        MainForm mainForm = new MainForm();
        mainForm.setSession(session);

        if (doc != null) mainForm.checkOut(doc);

        ArrayList<Copy> copies = session.userCard.checkedOutCopies;
        Assert.assertEquals(copies.size(), numberOfCopies);
      */
/*  if(copies.size() > 0){
            for(int j = 0; j < copies.get(copies.size() - 1).getDocument().getAuthors().size(); j++) {
                Assert.assertNotSame(copies.get(copies.size() - 1).getDocument().getAuthors().get(j).toLowerCase(),
                        authorName.toLowerCase());
            }*//*

    }


    */
/**
     * Initial state: 1 faculty, 1 student, 1 librarian in the system, copy c of a book b is checked out by a faculty f
     * Action:  'f' checks out book 'b'
     * Effect: the due time of checked out book is 4 week*//*

    @Test
    public void testCase3(){
        ArrayList<Document> documents = databaseManager.getAllDocuments();
        ArrayList<UserCard> userCards = databaseManager.getAllUsers();

        int user1ID = 1;
        int doc1ID = 0;

        int userID=3, bookID = 0, numberOfCopies;

        Assert.assertTrue(Faculty.class.isAssignableFrom(userCards.get(userID).userType.getClass()));
        Assert.assertTrue(Book.class.isAssignableFrom(documents.get(bookID).getClass()));
        Assert.assertTrue(documents.get(bookID).getNumberOfAllCopies()>=1);

        Session session = new Session(userCards.get(userID).userType,1,1);
        session.userCard = userCards.get(userID);

        MainForm mainForm = new MainForm();

        mainForm.setSession(session);

        numberOfCopies = session.userCard.checkedOutCopies.size();

        mainForm.checkOut(documents.get(bookID));

        ArrayList<Copy> copies = session.userCard.checkedOutCopies;

        Assert.assertEquals(copies.size(),numberOfCopies+1);
        Assert.assertSame(copies.get(copies.size()-1).getDocumentID(),documents.get(bookID));
        Assert.assertEquals(copies.get(copies.size()-1).checkOutTime,28);

    }

    */
/**
     * Initial state: 1 faculty, 1 student, 1 librarian in the system, copy c of a book b(bestseller) is checked out by a faculty f.
     * Action:  'f' checks out book 'b'
     * Effect: the due time of checked out book is 2 week*//*

    @Test
    public void testCase4(){

        ArrayList<Document> documents = databaseManager.getAllDocuments();
        ArrayList<UserCard> userCards = databaseManager.getAllUsers();


        int userID=3, bookID = 2, numberOfCopies;

        Assert.assertTrue(Faculty.class.isAssignableFrom(userCards.get(userID).userType.getClass()));
        Assert.assertTrue(Book.class.isAssignableFrom(documents.get(bookID).getClass()));
        Assert.assertTrue(documents.get(bookID).getNumberOfAllCopies()>=1);

        Session session = new Session(userCards.get(userID).userType,1,1);
        session.userCard = userCards.get(userID);

        MainForm mainForm = new MainForm();

        mainForm.setSession(session);

        numberOfCopies = session.userCard.checkedOutCopies.size();

        mainForm.checkOut(documents.get(bookID));

        ArrayList<Copy> copies = session.userCard.checkedOutCopies;

        Assert.assertEquals(copies.size(),numberOfCopies+1);
        Assert.assertSame(copies.get(copies.size()-1).getDocumentID(),documents.get(bookID));
        Assert.assertEquals(copies.get(copies.size()-1).checkOutTime,14);
    }

    */
/**
     * Initial state: 3 patrons, 1 librarian in the system, two copies c of a book b is checked out by a faculty f
     * Action:  Three patrons try to check out book 'b'
     * Effect: t: Only first two patrons can check out the copy of book A. The third patron sees an empty list of
     books*//*

    @Test
    public void testCase5(){

        ArrayList<Document> documents = databaseManager.getAllDocuments();
        ArrayList<UserCard> userCards = databaseManager.getAllUsers();



        int user1ID=1, user2ID=2 ,user3ID = 3, bookID = 0, numberOfCopies1, numberOfCopies2,numberOfCopies3;

        Assert.assertTrue(Patron.class.isAssignableFrom(userCards.get(user1ID).userType.getClass()));
        Assert.assertTrue(Patron.class.isAssignableFrom(userCards.get(user2ID).userType.getClass()));
        Assert.assertTrue(Patron.class.isAssignableFrom(userCards.get(user3ID).userType.getClass()));


        MainForm mainForm = new MainForm();

        Session session = new Session(userCards.get(user1ID).userType,1,1);
        session.userCard = userCards.get(user1ID);
        mainForm.setSession(session);
        numberOfCopies1 = session.userCard.checkedOutCopies.size();
        mainForm.checkOut(documents.get(bookID));
        ArrayList<Copy> copies1 = session.userCard.checkedOutCopies;

        session = new Session(userCards.get(user2ID).userType,1,1);
        session.userCard = userCards.get(user2ID);
        mainForm.setSession(session);
        numberOfCopies2 = session.userCard.checkedOutCopies.size();
        mainForm.checkOut(documents.get(bookID));
        ArrayList<Copy> copies2 = session.userCard.checkedOutCopies;

        session = new Session(userCards.get(user3ID).userType,1,1);
        session.userCard = userCards.get(user3ID);
        mainForm.setSession(session);
        numberOfCopies3 = session.userCard.checkedOutCopies.size();
        mainForm.checkOut(documents.get(bookID));
        ArrayList<Copy> copies3 = session.userCard.checkedOutCopies;

        Assert.assertEquals(copies1.size(),numberOfCopies1+1);
        Assert.assertSame(copies1.get(copies1.size()-1).getDocumentID(),documents.get(bookID));

        Assert.assertEquals(copies2.size(),numberOfCopies2+1);
        Assert.assertSame(copies2.get(copies2.size()-1).getDocumentID(),documents.get(bookID));

        Assert.assertEquals(copies3.size(),numberOfCopies3);
    }


    @Test
    public void testCase6() {
        ArrayList<Document> documents = databaseManager.getAllDocuments();
        ArrayList<UserCard> userCards = databaseManager.getAllUsers();

        int user1ID = 1;
        int doc1ID = 1;
        int numberOfCopies;

        Session session = new Session(userCards.get(user1ID).userType,1,1);
        session.userCard = userCards.get(user1ID);
        Assert.assertTrue(Patron.class.isAssignableFrom(session.getUser().getClass()));

        Assert.assertTrue(Book.class.isAssignableFrom(documents.get(doc1ID).getClass()));
        Assert.assertTrue(documents.get(doc1ID).getNumberOfAllCopies() >= 2);

        numberOfCopies = session.userCard.checkedOutCopies.size();

        MainForm mainForm = new MainForm();
        mainForm.setSession(session);
        mainForm.checkOut(documents.get(doc1ID));
        mainForm.checkOut(documents.get(doc1ID));

        Assert.assertTrue(session.userCard.checkedOutCopies.size() == numberOfCopies + 1);
    }

  */
/*  *
     * Initial state: 2 patrons, 1 librarian. It has two no reference copies of book b1.
     * Action: student check out b1
     * Effect: the system should track both bookings*//*

    @Test
    public void testCase7(){
        ArrayList<Document> documents = databaseManager.getAllDocuments();
        ArrayList<UserCard> userCards = databaseManager.getAllUsers();

        int user1ID = 1;
        int user2ID = 2;
        int doc1ID = 0;
        int numberOfCopies1;
        int numberOfCopies2;

        Assert.assertTrue(Patron.class.isAssignableFrom(userCards.get(user1ID).userType.getClass()));
        Assert.assertTrue(Patron.class.isAssignableFrom(userCards.get(user2ID).userType.getClass()));
        Assert.assertTrue(documents.get(doc1ID).getNumberOfAllCopies() >= 2);
        Assert.assertTrue(Book.class.isAssignableFrom(documents.get(doc1ID).getClass()));

        MainForm mainForm = new MainForm();

        Session session = new Session(userCards.get(user1ID).userType,1,1);
        session.userCard = userCards.get(user1ID);

        ArrayList<Copy> copies1 = session.userCard.checkedOutCopies;
        numberOfCopies1 = copies1.size();

        mainForm.setSession(session);
        mainForm.checkOut(documents.get(doc1ID));

        session = new Session(userCards.get(user2ID).userType,1,1);
        session.userCard = userCards.get(user2ID);

        ArrayList<Copy> copies2 = session.userCard.checkedOutCopies;
        numberOfCopies2 = copies2.size();

        mainForm.setSession(session);
        mainForm.checkOut(documents.get(doc1ID));

        Assert.assertTrue(userCards.get(user1ID).checkedOutCopies.contains(documents.get(doc1ID)));
        Assert.assertEquals(copies1.size(), numberOfCopies1+1);
        Assert.assertEquals(copies1.get(copies1.size()-1).getDocumentID(),documents.get(doc1ID));

        Assert.assertTrue(userCards.get(user2ID).checkedOutCopies.contains(documents.get(doc1ID)));
        Assert.assertEquals(copies2.size(), numberOfCopies2+1);
        Assert.assertEquals(copies2.get(copies2.size()-1).getDocumentID(),documents.get(doc1ID));
    }
  */
/*  *
     * Initial state: 2 patrons (1 student, 1 faculty), 1 librarian. It has two no reference copies of book b1.
     * Action: patrons check out b1
     * Effect: the due time of checked out book is 3 week*//*

    @Test
    public void testCase8(){
        ArrayList<Document> documents = databaseManager.getAllDocuments();
        ArrayList<UserCard> userCards = databaseManager.getAllUsers();

        int user1ID = 1;
        int doc1ID = 0;
        int numberOfCopies;

        Session session = new Session(userCards.get(user1ID).userType,1,1);
        session.userCard = userCards.get(user1ID);
        Assert.assertTrue(Student.class.isAssignableFrom(session.userCard.userType.getClass()));
        Assert.assertTrue(documents.get(doc1ID).getNumberOfAllCopies() >= 1);
        Assert.assertTrue(Book.class.isAssignableFrom(documents.get(doc1ID).getClass()));
        Assert.assertFalse(((Book)documents.get(doc1ID)).isBestseller);

        ArrayList<Copy> copies = session.userCard.checkedOutCopies;
        numberOfCopies = copies.size();

        MainForm mainForm = new MainForm();
        mainForm.setSession(session);
        mainForm.checkOut(documents.get(doc1ID));

        Assert.assertTrue(session.userCard.checkedOutCopies.contains(documents.get(doc1ID)));
        Assert.assertEquals(copies.size(), numberOfCopies+1);
        Assert.assertEquals(copies.get(copies.size()-1).getDocumentID(),documents.get(doc1ID));
        Assert.assertEquals(copies.get(copies.size()-1).checkOutTime,21);
    }
   */
/* *
     * Initial state: 2 patrons (1 student, 1 faculty), 1 librarian. It has two no reference copies of book b1 - bestseller.
     * Action: patrons check out b1
     * Effect: the due time of checked out book is 2 week*//*

    @Test
    public void testCase9(){
        ArrayList<Document> documents = databaseManager.getAllDocuments();
        ArrayList<UserCard> userCards = databaseManager.getAllUsers();

        int user1ID = 1;
        int doc1ID = 2;
        int numberOfCopies;

        Assert.assertTrue(Book.class.isAssignableFrom(documents.get(doc1ID).getClass()));
        Assert.assertTrue(((Book)documents.get(doc1ID)).isBestseller);

        Assert.assertTrue(Student.class.isAssignableFrom(userCards.get(user1ID).userType.getClass()));

        MainForm mainForm = new MainForm();

        Session session = new Session(userCards.get(user1ID).userType,1,1);
        session.userCard = userCards.get(user1ID);

        ArrayList<Copy> copies = session.userCard.checkedOutCopies;
        numberOfCopies = copies.size();

        mainForm.setSession(session);
        mainForm.checkOut(documents.get(doc1ID));

        Assert.assertTrue(session.userCard.checkedOutCopies.contains(documents.get(doc1ID)));
        Assert.assertEquals(copies.size(), numberOfCopies+1);
        Assert.assertEquals(copies.get(copies.size()-1).getDocumentID(),documents.get(doc1ID));
        Assert.assertEquals(copies.get(copies.size()-1).checkOutTime,14);
    }

  */
/*  *
     * Initial state: 1 patron,1 librarian, 1 book A, 1 reference book B
     * Action: patron tries to check out the book A and reference book B.
     * Effect:The system allows to check out only the book A. The reference book B is not available for checking out.*//*

    @Test
    public void testCase10() {
        ArrayList<Document> documents = databaseManager.getAllDocuments();
        ArrayList<UserCard> userCards = databaseManager.getAllUsers();

        int user1ID = 1;
        int doc1ID = 3;
        int doc2ID = 4;
        int numberOfDocs;

        Assert.assertTrue(Book.class.isAssignableFrom(documents.get(doc1ID).getClass()));
        Assert.assertTrue(Book.class.isAssignableFrom(documents.get(doc2ID).getClass()));
        Assert.assertTrue(documents.get(doc1ID).getNumberOfAllCopies() >= 1);
        Assert.assertEquals(documents.get(doc2ID).getNumberOfAllCopies(), 0);

        Session session = new Session(userCards.get(user1ID).userType,1,1);
        session.userCard = userCards.get(user1ID);

        Assert.assertTrue(Patron.class.isAssignableFrom(session.getUser().getClass()));
        numberOfDocs = session.userCard.checkedOutCopies.size();

        MainForm mainForm = new MainForm();
        mainForm.setSession(session);
        mainForm.checkOut(documents.get(doc1ID));
        mainForm.checkOut(documents.get(doc2ID));

        Assert.assertTrue(session.userCard.checkedOutCopies.size() == numberOfDocs+ 1);
        Assert.assertEquals(session.userCard.checkedOutCopies.get(session.userCard.checkedOutCopies.size()-1), documents.get(doc1ID));
    }*/
}

