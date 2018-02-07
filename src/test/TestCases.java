
import documents.Book;
import documents.Copy;
import documents.Document;
import documents.Storage;
import forms.MainForm;
import org.junit.*;
import users.*;

import java.util.ArrayList;

public class TestCases {

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

    @Test
    public void testCase3(){
        ArrayList<Document> documents = Storage.getDocuments();
        ArrayList<UserCard> userCards = Storage.getUsers();

        int userID=3, bookID = 0, numberOfCopies;

        Assert.assertTrue(Faculty.class.isAssignableFrom(userCards.get(userID).getUserType().class));
        Assert.assertTrue(Book.class.isAssignableFrom(documents.get(bookID).getClass()));
        Assert.assertTrue(documents.get(bookID).getNumberOfCopies()>=1);

        Session session = new Session(userCards.get(userID).getUserType());
        session.userCard = userCards.get(userID);

        MainForm mainForm = new MainForm();

        mainForm.setSession(session);

        numberOfCopies = session.userCard.getCheckedOutCopies().size();

        mainForm.checkOut(documents.get(bookID));

        ArrayList<Copy> copies = session.userCard.checkedOutCopies;

        Assert.assertEqual(copies.size(),numberOfCopies+1);
        Assert.assertSame(copies.get(copies.size()-1).getDocument(),documents.get(bookID));
        Assert.assertEqual(copies.get(copies.size()-1).checkOutTime,28);



    }

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
