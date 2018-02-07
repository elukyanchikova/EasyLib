
import documents.Document;
import documents.Storage;
import forms.MainForm;
import org.junit.*;
import users.Patron;
import users.Session;
import users.UserCard;

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
    public void testCase10() {
        ArrayList<Document> documents = Storage.getDocuments();
        ArrayList<UserCard> userCards = Storage.getUsers();

        int user1ID = 1;
        int doc1ID = 2;//TODO change on 8 when every book will be done
        int doc2ID = 3;//TODO change on 9 when every book will be done
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
