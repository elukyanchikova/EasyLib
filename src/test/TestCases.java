
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

}
