
import documents.Book;
import documents.Copy;
import documents.Document;
import documents.Storage;
import forms.MainForm;
import org.junit.*;
import users.Patron;
import users.Session;
import users.Student;
import users.UserCard;

import java.util.ArrayList;

public class TestCases {
    /**
     * Initial state: 1 patron, 1 librian, 2 copies of book 'b'(not a reference)
     * Action: Patron p checks out a copy of book b
     * Effect: 1 copy is checked out by 'p', 1 copy is in the library
     */
    @Test
    public void testCase1(){
        ArrayList<Document> documents = Storage.getDocuments();
        ArrayList<UserCard> userCards = Storage.getUsers();

        int user1ID = 1;
        int doc1ID = 0;

        Session session = new Session(userCards.get(user1ID).getUserType());
        session.userCard = userCards.get(user1ID);

        Assert.assertTrue(Patron.class.isAssignableFrom(session.getUser().getClass()));
        Assert.assertTrue(session.userCard.checkedOutDocs.isEmpty());
        Assert.assertEquals(documents.get(doc1ID).getNumberOfCopies(), 2);

        MainForm mainForm = new MainForm();
        mainForm.setSession(session);
        mainForm.checkOut(documents.get(doc1ID));

        Assert.assertTrue(session.userCard.checkedOutDocs.contains(documents.get(doc1ID)));
    }

    /**
     * Initial state: 1 patron, 1 librarian, no books by author A
     * Action: patron 'p' checks out book by A.
     * Effect: The system does not change its state. Maybe a message notifying the patron can read: the
     library does not have book 'b'
     */
    @Test
    public void testCase2(){
        ArrayList<Document> documents = Storage.getDocuments();
        ArrayList<UserCard> userCards = Storage.getUsers();

        int user1ID = 1;
        String authorName = "No Any Name";
        int numberOfDocs;

        Session session = new Session(userCards.get(user1ID).getUserType());
        session.userCard = userCards.get(user1ID);

        numberOfDocs = userCards.get(user1ID).checkedOutDocs.size();

        Assert.assertTrue(Patron.class.isAssignableFrom(session.getUser().getClass()));

        Document doc = null;
        for(int i = 0; i < documents.size(); i++) {
            for(int j = 0; j < documents.get(i).getAuthors().size(); j++) {
                if(documents.get(i).getAuthors().get(j).toLowerCase().equals(authorName.toLowerCase())) {
                    doc = documents.get(i);
                }
            }
        }

        MainForm mainForm = new MainForm();
        mainForm.setSession(session);

        if(doc != null) mainForm.checkOut(doc);
        ArrayList<Document> docs = session.userCard.checkedOutDocs;
        Assert.assertEquals(docs.size(), numberOfDocs);
        if(docs.size() > 0){
            for(int j = 0; j < docs.get(docs.size() - 1).getAuthors().size(); j++) {
                Assert.assertNotSame(docs.get(docs.size() - 1).getAuthors().get(j).toLowerCase(),
                        authorName.toLowerCase());
            }
        }
    }

    /**
     * Initial state: 1 patron, 1 librarian in the system, copy c of a book b is checked out by a patron p
     * Action: patron p tries to check out another copy c' of book b.
     * Effect: None
     */
    @Test
    public void testCase6() {
        ArrayList<Document> documents = Storage.getDocuments();
        ArrayList<UserCard> userCards = Storage.getUsers();

        int user1ID = 1;
        int doc1ID = 1;//TODO change on 4 when every book will be done
        int numberOfCopies;

        Session session = new Session(userCards.get(user1ID).getUserType());
        session.userCard = userCards.get(user1ID);

        Assert.assertTrue(documents.get(doc1ID).getNumberOfCopies() >= 2);
        Assert.assertTrue(Patron.class.isAssignableFrom(session.getUser().getClass()));

        numberOfCopies = session.userCard.checkedOutCopies.size();

        MainForm mainForm = new MainForm();
        mainForm.setSession(session);
        mainForm.checkOut(documents.get(doc1ID));
        mainForm.checkOut(documents.get(doc1ID));

        Assert.assertTrue(session.userCard.checkedOutCopies.size() == numberOfCopies + 1);
    }

    /**
     * Initial state: 2 patrons, 1 librarian. It has two no reference copies of book b1.
     * Action: student check out b1
     * Effect: the system should track both bookings
     */
    @Test
    public void testCase7(){
        ArrayList<Document> documents = Storage.getDocuments();
        ArrayList<UserCard> userCards = Storage.getUsers();

        int user1ID = 1;
        int user2ID = 2;
        int doc1ID = 0;
        int numberOfCopies1;
        int numberOfCopies2;

        Assert.assertTrue(Patron.class.isAssignableFrom(userCards.get(user1ID).getUserType().getClass()));
        Assert.assertTrue(Patron.class.isAssignableFrom(userCards.get(user2ID).getUserType().getClass()));
        Assert.assertTrue(documents.get(doc1ID).getNumberOfCopies() >= 2);
        Assert.assertTrue(Book.class.isAssignableFrom(documents.get(doc1ID).getClass()));

        MainForm mainForm = new MainForm();

        Session session = new Session(userCards.get(user1ID).getUserType());
        session.userCard = userCards.get(user1ID);

        ArrayList<Copy> copies1 = session.userCard.checkedOutCopies;
        numberOfCopies1 = copies1.size();

        mainForm.setSession(session);
        mainForm.checkOut(documents.get(doc1ID));

        session = new Session(userCards.get(user2ID).getUserType());
        session.userCard = userCards.get(user2ID);

        ArrayList<Copy> copies2 = session.userCard.checkedOutCopies;
        numberOfCopies2 = copies2.size();

        mainForm.setSession(session);
        mainForm.checkOut(documents.get(doc1ID));

        Assert.assertTrue(userCards.get(user1ID).checkedOutDocs.contains(documents.get(doc1ID)));
        Assert.assertEquals(copies1.size(), numberOfCopies1+1);
        Assert.assertEquals(copies1.get(copies1.size()-1).getDocument(),documents.get(doc1ID));

        Assert.assertTrue(userCards.get(user2ID).checkedOutDocs.contains(documents.get(doc1ID)));
        Assert.assertEquals(copies2.size(), numberOfCopies2+1);
        Assert.assertEquals(copies2.get(copies2.size()-1).getDocument(),documents.get(doc1ID));
    }
    /**
     * Initial state: 2 patrons (1 student, 1 faculty), 1 librarian. It has two no reference copies of book b1.
     * Action: patrons check out b1
     * Effect: the due time of checked out book is 3 week
     */
    @Test
    public void testCase8(){
        ArrayList<Document> documents = Storage.getDocuments();
        ArrayList<UserCard> userCards = Storage.getUsers();

        int user1ID = 1;
        int doc1ID = 0;
        int numberOfCopies;

        Session session = new Session(userCards.get(user1ID).getUserType());
        session.userCard = userCards.get(user1ID);
        Assert.assertTrue(Student.class.isAssignableFrom(session.userCard.getUserType().getClass()));
        Assert.assertTrue(documents.get(doc1ID).getNumberOfCopies() >= 1);
        Assert.assertTrue(Book.class.isAssignableFrom(documents.get(doc1ID).getClass()));
        Assert.assertFalse(((Book)documents.get(doc1ID)).isBestseller());

        ArrayList<Copy> copies = session.userCard.checkedOutCopies;
        numberOfCopies = copies.size();

        MainForm mainForm = new MainForm();
        mainForm.setSession(session);
        mainForm.checkOut(documents.get(doc1ID));

        Assert.assertTrue(session.userCard.checkedOutDocs.contains(documents.get(doc1ID)));
        Assert.assertEquals(copies.size(), numberOfCopies+1);
        Assert.assertEquals(copies.get(copies.size()-1).getDocument(),documents.get(doc1ID));
        Assert.assertEquals(copies.get(copies.size()-1).checkOutTime,21);
    }

    /**
     * Initial state: 2 patrons (1 student, 1 faculty), 1 librarian. It has two no reference copies of book b1 - bestseller.
     * Action: patrons check out b1
     * Effect: the due time of checked out book is 2 week
     */
    @Test
    public void testCase9(){
        ArrayList<Document> documents = Storage.getDocuments();
        ArrayList<UserCard> userCards = Storage.getUsers();

        int user1ID = 1;
        int doc1ID = 2;//TODO to 6
        int numberOfCopies;

        Assert.assertTrue(Book.class.isAssignableFrom(documents.get(doc1ID).getClass()));
        Assert.assertTrue(((Book)documents.get(doc1ID)).isBestseller());

        Assert.assertTrue(Student.class.isAssignableFrom(userCards.get(user1ID).getUserType().getClass()));

        MainForm mainForm = new MainForm();

        Session session = new Session(userCards.get(user1ID).getUserType());
        session.userCard = userCards.get(user1ID);

        ArrayList<Copy> copies = session.userCard.checkedOutCopies;
        numberOfCopies = copies.size();

        mainForm.setSession(session);
        mainForm.checkOut(documents.get(doc1ID));

        Assert.assertTrue(session.userCard.checkedOutDocs.contains(documents.get(doc1ID)));
        Assert.assertEquals(copies.size(), numberOfCopies+1);
        Assert.assertEquals(copies.get(copies.size()-1).getDocument(),documents.get(doc1ID));
        Assert.assertEquals(copies.get(copies.size()-1).checkOutTime,14);
    }

    @Test
    public void testCase10() {
        ArrayList<Document> documents = Storage.getDocuments();
        ArrayList<UserCard> userCards = Storage.getUsers();

        int user1ID = 1;
        int doc1ID = 3;//TODO change on 8 when every book will be done
        int doc2ID = 4;//TODO change on 9 when every book will be done
        int numberOfDocs;

        Session session = new Session(userCards.get(user1ID).getUserType());
        session.userCard = userCards.get(user1ID);
        Assert.assertTrue(documents.get(doc1ID).getNumberOfCopies() >= 1);
        Assert.assertEquals(documents.get(doc2ID).getNumberOfCopies(), 0);
        Assert.assertTrue(Patron.class.isAssignableFrom(session.getUser().getClass()));

        numberOfDocs = session.userCard.checkedOutDocs.size();

        MainForm mainForm = new MainForm();
        mainForm.setSession(session);
        mainForm.checkOut(documents.get(doc1ID));
        mainForm.checkOut(documents.get(doc2ID));

        Assert.assertTrue(session.userCard.checkedOutDocs.size() == numberOfDocs+ 1);
        Assert.assertEquals(session.userCard.checkedOutDocs.get(session.userCard.checkedOutDocs.size()-1), documents.get(doc1ID));
    }
}
