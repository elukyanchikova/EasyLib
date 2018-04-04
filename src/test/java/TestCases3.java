import documents.AVMaterial;
import documents.Book;
import documents.Copy;
import documents.Document;
import forms.MainForm;
import forms.ManageForm;
import forms.ReturnForm;
import main.Main;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import storage.DatabaseManager;
import users.*;

import javax.jws.soap.SOAPBinding;
import javax.print.Doc;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class TestCases3 {


    private DatabaseManager databaseManager = new DatabaseManager("TestCases3");

    public void initialState(){
        databaseManager.resetDatabase();
        Book d1 = new Book( "Introduction to Algorithms",
                new ArrayList<>(Arrays.asList("Thomas H. Cormen", "Charles E. Leiserson", "Ronald L. Rivest", "Clifford Stein")),
                new ArrayList<>(), 5000, "MIT Press", 2009, "Third edition",false);
        for(int i = 0; i < 3; i++)
            d1.setCopy(new Copy(d1, 3, 310));
        databaseManager.saveDocuments(d1);

        Book d2 = new Book( "Design Patterns: Elements of Reusable Object-Oriented Software",
                new ArrayList<>(Arrays.asList("Erich Gamma", "Ralph Johnson", "John Vlissides", "Richard Helm")),
                new ArrayList<>(), 1700, "Addison-Wesley Professional", 2003, "First edition",true);
        for(int i = 0; i < 3; i++)
            d2.setCopy(new Copy(d2, 3, 310));
        databaseManager.saveDocuments(d2);

        AVMaterial d3 =  new AVMaterial( "Null References: The Billion Dollar Mistake",
                new ArrayList<>(Collections.singletonList("Tony Hoare")), new ArrayList<>(), 700 );
        for(int i = 0; i < 2; i++)
            d3.setCopy(new Copy(d3, 2, 201));
        databaseManager.saveDocuments(d3);

        UserCard p1 = new UserCard(1010, "Sergey", "Afonso", new Professor(), "30001", "Via Margutta, 3");
        databaseManager.saveUserCard(p1);

        UserCard p2 = new UserCard(1011, "Nadia", "Teixeira", new Professor(), "30002", "Via Sacra, 13");
        databaseManager.saveUserCard(p2);

        UserCard p3 = new UserCard(1100, "Elvira", "Espindola", new Professor(), "30003", "Via del Corso, 22");
        databaseManager.saveUserCard(p3);

        UserCard s = new UserCard(1101, "Andrey", "Velo",  new Student(), "30004", "Avenida Mazatlan 250");
        databaseManager.saveUserCard(s);

        UserCard v = new UserCard(1110, "Veronika", "Rama", new VisitingProfessor(), "30005", "Stret Atocha, 27");
        databaseManager.saveUserCard(v);
    }

    @Test
    public void Test1() {
        initialState();
        databaseManager.resetDatabase();

        ArrayList<Copy> l1_checkedOutCopies = new ArrayList<Copy>();
        ArrayList<Document> l1_requestedDocuments = new ArrayList<Document>();

        UserCard librarian_1 = new UserCard("Irma", "Pins", new Librarian(), "8981351785", "north of London",
                l1_checkedOutCopies, l1_requestedDocuments);
        databaseManager.saveUserCard(librarian_1);
        Session session = new Session(databaseManager.getUserCard(librarian_1.getId()).userType, 5, 3);
        session.userCard = librarian_1;

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
                b3_keywords, 700, b3_copies, "none", 2018, "none", false);
        b3.setReference(true);
        b3.setCopy(new Copy(b3, 1, 1));
        b3.setCopy(new Copy(b3, 1, 2));

        databaseManager.saveDocuments(b3);
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        /**Initial state**/
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        b1.takeCopy(p1, session);
        databaseManager.saveDocuments(b1);
        databaseManager.saveUserCard(p1);
        b2.takeCopy(p1, session);
        databaseManager.saveDocuments(b2);
        databaseManager.saveUserCard(p1);
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        /**Action**/
        Session curSession = new Session(librarian_1.userType, 2, 4);
        Assert.assertTrue("Session is leading by  librarian.", Librarian.class.isAssignableFrom(session.getUser().getClass()));
        int a = p1.getFine(p1, curSession, databaseManager);
        int b = p1.checkedOutCopies.get(0).getOverdue(curSession);
        b1.returnCopy(p1.checkedOutCopies.get(0));
        databaseManager.saveDocuments(b1);
        databaseManager.saveUserCard(p1);
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        /**Effect**/
        Assert.assertTrue("Fine of p1 is equal 0", a == 0);
        Assert.assertTrue("Overdue of p1 is equal 0", b == 0);
        Assert.assertTrue("p1 have only one checked out copies", p1.checkedOutCopies.size() == 1);

    }

  @Test
    public void Test2() {
        databaseManager.resetDatabase();

        ArrayList<Copy> l1_checkedOutCopies = new ArrayList<Copy>();
        ArrayList<Document> l1_requestedDocuments = new ArrayList<Document>();

        UserCard librarian_1 = new UserCard("Irma", "Pins", new Librarian(), "8981351785", "north of London",
                l1_checkedOutCopies, l1_requestedDocuments);
        databaseManager.saveUserCard(librarian_1);
        Session session = new Session(databaseManager.getUserCard(librarian_1.getId()).userType, 5, 3);
        session.userCard = librarian_1;

        ArrayList<Copy> p1_checkedOutCopies = new ArrayList<Copy>();
        ArrayList<Document> p1_requestedDocs = new ArrayList<Document>();
        UserCard p1 = new UserCard(1010, "Sergey", "Afonso", new Professor(), "30001", "Via Margutta, 3", p1_checkedOutCopies, p1_requestedDocs);
        databaseManager.saveUserCard(p1);
        /////////////////////////////////////////////////////////////////
        ArrayList<Copy> p2_checkedOutCopies = new ArrayList<Copy>();
        ArrayList<Document> p2_requestedDocs = new ArrayList<Document>();
        UserCard p2 = new UserCard(1011, "Nadia", "Teixeira", new Professor(), "30002", "Via Sacra, 13", p2_checkedOutCopies, p2_requestedDocs);
        databaseManager.saveUserCard(p2);
        //////////////////////////////////////////////////////////////////
        ArrayList<Copy> p3_checkedOutCopies = new ArrayList<Copy>();
        ArrayList<Document> p3_requestedDocs = new ArrayList<Document>();
        UserCard p3 = new UserCard(1100, "Elvira", "Espindola", new Professor(), "30003", "Via del Corso, 22", p3_checkedOutCopies, p3_requestedDocs);
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
                b3_keywords, 700, b3_copies, "none", 2018, "none", false);
        b3.setReference(true);
        b3.setCopy(new Copy(b3, 1, 1));
        b3.setCopy(new Copy(b3, 1, 2));

        databaseManager.saveDocuments(b3);
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ArrayList<Copy> s_checkedOutCopies = new ArrayList<Copy>();
        ArrayList<Document> s_requestedDocs = new ArrayList<Document>();
        UserCard s = new UserCard( 1101, "Andrey", "Velo", new Student(), "30004", "Avenida Mazatlan 250", s_checkedOutCopies, s_requestedDocs);
        databaseManager.saveUserCard(s);
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ArrayList<Copy> v_checkedOutCopies = new ArrayList<Copy>();
        ArrayList<Document> v_requestedDocs = new ArrayList<Document>();
        UserCard v = new UserCard( 1110, "Veronika", "Rama", new VisitingProfessor(), "30005", "Stret Atocha, 27", v_checkedOutCopies, v_requestedDocs);
        databaseManager.saveUserCard(v);
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**Initial state*/
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        b1.takeCopy(p1, session);
        databaseManager.saveDocuments(b1);
        databaseManager.saveUserCard(p1);
        b2.takeCopy(p1, session);
        databaseManager.saveDocuments(b2);
        databaseManager.saveUserCard(p1);

        b1.takeCopy(s, session);
        databaseManager.saveDocuments(b1);
        databaseManager.saveUserCard(s);
        b2.takeCopy(s, session);
        databaseManager.saveDocuments(b2);
        databaseManager.saveUserCard(s);

        b1.takeCopy(v, session);
        databaseManager.saveDocuments(b1);
        databaseManager.saveUserCard(v);
        b2.takeCopy(v, session);
        databaseManager.saveDocuments(b2);
        databaseManager.saveUserCard(v);
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**Action*/
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        Session curSession = new Session(librarian_1.userType, 2, 4);
        Assert.assertTrue("Session is leading by  librarian.", Librarian.class.isAssignableFrom(session.getUser().getClass()));

        int x1 = p1.getFine(p1, curSession, databaseManager);
        int y1 = p1.checkedOutCopies.get(0).getOverdue(curSession)  + p1.checkedOutCopies.get(1).getOverdue(curSession);

        int x2 = s.getFine(s, curSession, databaseManager);
        int y2 = s.checkedOutCopies.get(0).getOverdue(curSession) + s.checkedOutCopies.get(1).getOverdue(curSession);

        int x3 = v.getFine(v, curSession, databaseManager);
        int y3 = v.checkedOutCopies.get(0).getOverdue(curSession)+v.checkedOutCopies.get(1).getOverdue(curSession);
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////


/**Effect*/
           ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        Assert.assertTrue("Fine of p1 is equal 0", x1 == 0);
        Assert.assertTrue("Overdue of p1 is equal 0", y1 == 0);

        Assert.assertTrue("Fine of s is equal 2100", x2 == 2100);
        Assert.assertTrue("Overdue of s is equal 7+14=21 days", y2 == 21);

        Assert.assertTrue("Fine of s is equal 2100+1700 = 3800", x3 == 3800);
        Assert.assertTrue("Overdue of s is equal 21+21 days", y3 == 42);
    }


    @Test
    public void Test3() {

        databaseManager.resetDatabase();

        ArrayList<Copy> l1_checkedOutCopies = new ArrayList<Copy>();
        ArrayList<Document> l1_requestedDocuments = new ArrayList<Document>();

        UserCard librarian_1 = new UserCard("Irma", "Pins", new Librarian(), "8981351785", "north of London",
                l1_checkedOutCopies, l1_requestedDocuments);
        databaseManager.saveUserCard(librarian_1);
        Session session = new Session(databaseManager.getUserCard(librarian_1.getId()).userType, 29, 3);
        session.userCard = librarian_1;

        ArrayList<Copy> p1_checkedOutCopies = new ArrayList<Copy>();
        ArrayList<Document> p1_requestedDocs = new ArrayList<Document>();
        UserCard p1 = new UserCard(1010, "Sergey", "Afonso", new Professor(), "30001", "Via Margutta, 3", p1_checkedOutCopies, p1_requestedDocs);
        databaseManager.saveUserCard(p1);
        /////////////////////////////////////////////////////////////////
        ArrayList<Copy> p2_checkedOutCopies = new ArrayList<Copy>();
        ArrayList<Document> p2_requestedDocs = new ArrayList<Document>();
        UserCard p2 = new UserCard(1011, "Nadia", "Teixeira", new Professor(), "30002", "Via Sacra, 13", p2_checkedOutCopies, p2_requestedDocs);
        databaseManager.saveUserCard(p2);
        //////////////////////////////////////////////////////////////////
        ArrayList<Copy> p3_checkedOutCopies = new ArrayList<Copy>();
        ArrayList<Document> p3_requestedDocs = new ArrayList<Document>();
        UserCard p3 = new UserCard(1100, "Elvira", "Espindola", new Professor(), "30003", "Via del Corso, 22", p3_checkedOutCopies, p3_requestedDocs);
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
                b3_keywords, 700, b3_copies, "none", 2018, "none", false);
        b3.setReference(true);
        b3.setCopy(new Copy(b3, 1, 1));
        b3.setCopy(new Copy(b3, 1, 2));

        databaseManager.saveDocuments(b3);
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ArrayList<Copy> s_checkedOutCopies = new ArrayList<Copy>();
        ArrayList<Document> s_requestedDocs = new ArrayList<Document>();
        UserCard s = new UserCard( 1101, "Andrey", "Velo", new Student(), "30004", "Avenida Mazatlan 250", s_checkedOutCopies, s_requestedDocs);
        databaseManager.saveUserCard(s);
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ArrayList<Copy> v_checkedOutCopies = new ArrayList<Copy>();
        ArrayList<Document> v_requestedDocs = new ArrayList<Document>();
        UserCard v = new UserCard( 1110, "Veronika", "Rama", new VisitingProfessor(), "30005", "Stret Atocha, 27", v_checkedOutCopies, v_requestedDocs);
        databaseManager.saveUserCard(v);
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        /**Initial state*/
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        b1.takeCopy(p1, session);
        databaseManager.saveDocuments(b1);
        databaseManager.saveUserCard(p1);

        b2.takeCopy(s, session);
        databaseManager.saveDocuments(b2);
        databaseManager.saveUserCard(s);

        b2.takeCopy(v, session);
        databaseManager.saveDocuments(b2);
        databaseManager.saveUserCard(v);
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        /**Action*/
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        Session curSession = new Session(librarian_1.userType, 2, 4);
        ReturnForm returnForm = new ReturnForm();

        returnForm.setSession(session);

        returnForm.renew(p1,p1_checkedOutCopies.get(0));
        databaseManager.saveDocuments(b1);
        databaseManager.saveUserCard(p1);

        returnForm.renew(s,s_checkedOutCopies.get(0));
        databaseManager.saveDocuments(b2);
        databaseManager.saveUserCard(s);

        returnForm.renew(v,v_checkedOutCopies.get(0));
        databaseManager.saveDocuments(b2);
        databaseManager.saveUserCard(v);
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        /**Action*/
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        int a = p1.checkedOutCopies.get(0).getOverdue(curSession);
        Assert.assertTrue("Overdue p1(b1) is equal 0", a==0);

        int b = s.checkedOutCopies.get(0).getOverdue(curSession);
        Assert.assertTrue("Overdue s(b2) is equal 0", b==0);

        int c = v.checkedOutCopies.get(0).getOverdue(curSession);
        Assert.assertTrue("Overdue v(b2) is equal 0", c==0);
    }

    @Test
    public void Test4() {

        ArrayList<Copy> l1_checkedOutCopies = new ArrayList<Copy>();
        ArrayList<Document> l1_requestedDocuments = new ArrayList<Document>();

        UserCard librarian_1 = new UserCard("Irma", "Pins", new Librarian(), "8981351785", "north of London",
                l1_checkedOutCopies, l1_requestedDocuments);
        databaseManager.saveUserCard(librarian_1);
        Session session = new Session(databaseManager.getUserCard(librarian_1.getId()).userType, 29, 3);
        session.userCard = librarian_1;

        ArrayList<Copy> p1_checkedOutCopies = new ArrayList<Copy>();
        ArrayList<Document> p1_requestedDocs = new ArrayList<Document>();
        UserCard p1 = new UserCard(1010, "Sergey", "Afonso", new Faculty(), "30001", "Via Margutta, 3", p1_checkedOutCopies, p1_requestedDocs);
        databaseManager.saveUserCard(p1);

        /////////////////////////////////////////////////////////////////

        ArrayList<Copy> s_checkedOutCopies = new ArrayList<Copy>();
        ArrayList<Document> s_requestedDocs = new ArrayList<Document>();
        UserCard s = new UserCard(1101, "Andrey", "Velo", new Student(), "30004", "Avenida Mazatlan 250", s_checkedOutCopies, s_requestedDocs);
        databaseManager.saveUserCard(s);

        //////////////////////////////////////////////////////////////////

        ArrayList<Copy> v_checkedOutCopies = new ArrayList<Copy>();
        ArrayList<Document> v_requestedDocs = new ArrayList<Document>();
        UserCard v = new UserCard(1110, "Veronika", "Rama", new VisitingProfessor(), "30005", "Stret Atocha, 27", v_checkedOutCopies, v_requestedDocs);
        databaseManager.saveUserCard(v);

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
                b3_keywords, 700, b3_copies, "none", 2018, "none", false);
        b3.setReference(true);
        b3.setCopy(new Copy(b3, 1, 1));
        b3.setCopy(new Copy(b3, 1, 2));

        databaseManager.saveDocuments(b3);

        /////////////////////////////////////////////////////////////////////

        b1.takeCopy(p1, session);
        databaseManager.saveDocuments(b1);
        databaseManager.saveUserCard(p1);
        b2.takeCopy(s, session);
        databaseManager.saveDocuments(b2);
        databaseManager.saveUserCard(s);
        b2.takeCopy(v, session);
        databaseManager.saveDocuments(b2);
        databaseManager.saveUserCard(v);

        ///////////////////////////////////////////////////////////////////////////
        //initialState();
        ///////////////////////////////////////////////////////////////////////////

        ManageForm manageForm = new ManageForm();
        ReturnForm returnForm = new ReturnForm();
        manageForm.setSession(session);
        returnForm.outstandingRequest(b2);

        session = new Session(p1.userType, 29, 3);
        session.userCard = p1;
        returnForm.setSession(session);
        for (int i = 0; i < b1.takenCopies.size(); i++) {
            if (b1.takenCopies.get(i).getCheckoutByUser().getId() == p1.getId()) {
                returnForm.renew(p1,b1.takenCopies.get(i));
            }
        }

        session = new Session(s.userType, 29, 3);
        session.userCard = s;
        returnForm.setSession(session);
        for (int i = 0; i < b2.takenCopies.size(); i++) {
            if (b2.takenCopies.get(i).getCheckoutByUser().getId() == s.getId()) {
                returnForm.renew(s,b2.takenCopies.get(i));
            }
        }

        session = new Session(v.userType, 29, 3);
        session.userCard = v;
        returnForm.setSession(session);
        for (int i = 0; i < b2.takenCopies.size(); i++) {
            if (b2.takenCopies.get(i).getCheckoutByUser().getId() == v.getId()) {
                returnForm.renew(v,b2.takenCopies.get(i));
            }
        }

        session = new Session(databaseManager.getUserCard(librarian_1.getId()).userType, 29, 3);
        session.userCard = librarian_1;

        Assert.assertEquals("users.Faculty",p1.userType.getClass().getName());
        Assert.assertEquals(1010,p1.getId());
        Assert.assertEquals("Sergey",p1.name);
        Assert.assertEquals("Afonso",p1.surname);
        for (int i = 0; i < b1.takenCopies.size(); i++) {
            if (b1.takenCopies.get(i).getCheckoutByUser().getId() == p1.getId()) {

                Assert.assertTrue(p1.checkedOutCopies.contains(b1.takenCopies.get(i)));
            }
        }


        Assert.assertEquals("users.Student",p1.userType.getClass().getName());
        Assert.assertEquals(1101,p1.getId());
        Assert.assertEquals("Andrey",p1.name);
        Assert.assertEquals("Velo",p1.surname);
        for (int i = 0; i < b2.takenCopies.size(); i++) {
            if (b2.takenCopies.get(i).getCheckoutByUser().getId() == s.getId()) {
                Assert.assertTrue(p1.checkedOutCopies.contains(b2.takenCopies.get(i)));
            }
        }


        Assert.assertEquals("users.VisitingProfessor",p1.userType.getClass().getName());
        Assert.assertEquals(1110,p1.getId());
        Assert.assertEquals("Veronika",p1.name);
        Assert.assertEquals("Rama",p1.surname);
        for (int i = 0; i < b2.takenCopies.size(); i++) {
            if (b2.takenCopies.get(i).getCheckoutByUser().getId() == v.getId()) {
                Assert.assertTrue(p1.checkedOutCopies.contains(b2.takenCopies.get(i)));
            }
        }







    }

    @Test
    public void Test5() {

        /*initialState();

        ArrayList<Copy> l1_checkedOutCopies = new ArrayList<Copy>();
        ArrayList<Document> l1_requestedDocuments = new ArrayList<Document>();

        UserCard librarian_1 = new UserCard("Irma", "Pins", new Librarian(), "8981351785", "north of London",
                l1_checkedOutCopies, l1_requestedDocuments);
        databaseManager.saveUserCard(librarian_1);
        Session session = new Session(databaseManager.getUserCard(librarian_1.getId()).userType, 29, 3);
        session.userCard = librarian_1;

        ArrayList<Copy> p1_checkedOutCopies = new ArrayList<Copy>();
        ArrayList<Document> p1_requestedDocs = new ArrayList<Document>();
        UserCard p1 = new UserCard(1010, "Sergey", "Afonso", new Faculty(), "30001", "Via Margutta, 3", p1_checkedOutCopies, p1_requestedDocs);
        databaseManager.saveUserCard(p1);

        /////////////////////////////////////////////////////////////////

        ArrayList<Copy> s_checkedOutCopies = new ArrayList<Copy>();
        ArrayList<Document> s_requestedDocs = new ArrayList<Document>();
        UserCard s = new UserCard(1101, "Andrey", "Velo", new Student(), "30004", "Avenida Mazatlan 250", s_checkedOutCopies, s_requestedDocs);
        databaseManager.saveUserCard(s);

        //////////////////////////////////////////////////////////////////

        ArrayList<Copy> v_checkedOutCopies = new ArrayList<Copy>();
        ArrayList<Document> v_requestedDocs = new ArrayList<Document>();
        UserCard v = new UserCard(1110, "Veronika", "Rama", new VisitingProfessor(), "30005", "Stret Atocha, 27", v_checkedOutCopies, v_requestedDocs);
        databaseManager.saveUserCard(v);

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
                b3_keywords, 700, b3_copies, "none", 2018, "none", false);
        b3.setReference(true);
        b3.setCopy(new Copy(b3, 1, 1));
        b3.setCopy(new Copy(b3, 1, 2));

        databaseManager.saveDocuments(b3);
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //initialState
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        b3.takeCopy(p1, session);
        databaseManager.saveDocuments(b3);
        databaseManager.saveUserCard(p1);
        b3.takeCopy(s, session);
        databaseManager.saveDocuments(b3);
        databaseManager.saveUserCard(s);
        MainForm mainForm = new MainForm();

        session = new Session(v.userType, 29, 3);
        session.userCard = v;
        mainForm.setSession(session);
        mainForm.request(b3);

        session = new Session(databaseManager.getUserCard(librarian_1.getId()).userType, 29, 3);
        session.userCard = librarian_1;

        UserCard[] pq = new UserCard[b3.requestedBy.size()];
        pq = b3.requestedBy.toArray(pq);
        UserCard[] pqcheck = new UserCard[pq.length];


        Assert.assertEquals(v,b3.requestedBy.peek());
*/


    }

    @Test
    public void Test6() {
        initialState();
        MainForm mainForm = new MainForm();
        mainForm.setDatabaseManager(databaseManager);

        UserCard userCard = databaseManager.getUserCard(1010);
        Document document = databaseManager.getDocuments(3);
        Session session = new Session(userCard.userType, 26, 3);
        session.userCard = userCard;
        mainForm.setSession(session);
        if(document.availableCopies.size() > 0)
            mainForm.checkOut(document);
        else mainForm.request(document);
        document = databaseManager.getDocuments(document.getID());

        UserCard userCard2 = databaseManager.getUserCard(1011);
        Session session2 = new Session(userCard2.userType, 26, 3);
        session2.userCard = userCard2;
        mainForm.setSession(session2);
        if(document.availableCopies.size() > 0)
            mainForm.checkOut(document);
        else mainForm.request(document);
        document = databaseManager.getDocuments(document.getID());

        UserCard userCard3 = databaseManager.getUserCard(1101);
        Session session3 = new Session(userCard3.userType, 26, 3);
        session3.userCard = userCard3;
        mainForm.setSession(session3);
        if(document.availableCopies.size() > 0)
            mainForm.checkOut(document);
        else mainForm.request(document);
        document = databaseManager.getDocuments(document.getID());

        UserCard userCard4 = databaseManager.getUserCard(1110);
        Session session4 = new Session(userCard4.userType, 26, 3);
        session4.userCard = userCard4;
        mainForm.setSession(session4);
        if(document.availableCopies.size() > 0)
            mainForm.checkOut(document);
        else mainForm.request(document);
        document = databaseManager.getDocuments(document.getID());

        UserCard userCard5 = databaseManager.getUserCard(1100);
        Session session5 = new Session(userCard5.userType, 26, 3);
        session5.userCard = userCard5;
        mainForm.setSession(session5);
        if(document.availableCopies.size() > 0)
            mainForm.checkOut(document);
        else mainForm.request(document);
        document = databaseManager.getDocuments(document.getID());

        UserCard[] userCards = document.requestedBy.toArray(new UserCard[0]);
        Arrays.sort(userCards);
        Assert.assertEquals(userCard3.getId(), userCards[2].getId());
        Assert.assertEquals(userCard4.getId(), userCards[1].getId());
        Assert.assertEquals(userCard5.getId(), userCards[0].getId());
    }

    @Test
    public void Test7() {
    }

    @Test
    public void Test8() {
    }

    @Test
    public void Test9() {
    }

    @Test
    public void Test10() {
        initialState();

        MainForm mainForm = new MainForm();
        mainForm.setDatabaseManager(databaseManager);

        UserCard userCard = databaseManager.getUserCard(1010);
        Document document = databaseManager.getDocuments(1);

        Session session = new Session(userCard.userType, 26, 3);
        session.userCard = userCard;
        mainForm.setSession(session);

        mainForm.checkOut(document);

        Copy copy = null;
        for(int i = 0; i < document.takenCopies.size(); i++){
            if(document.takenCopies.get(i).getCheckoutByUser().getId() == userCard.getId())
                copy = document.takenCopies.get(i);
        }
        Assert.assertNotNull(copy);
        Assert.assertEquals(copy.getCheckedOutDate(), "26 March");

        Session session2 = new Session(userCard.userType, 29, 3);
        session2.userCard = userCard;
        mainForm.setSession(session2);
        ReturnForm returnForm = new ReturnForm();
        returnForm.setSession(session2);
        returnForm.renew(userCard, copy);
        Assert.assertEquals(copy.getCheckedOutDate(), "29 March");


        UserCard userCard2 = databaseManager.getUserCard(1110);

        Session session3 = new Session(userCard2.userType, 26, 3);
        session3.userCard = userCard2;
        mainForm.setSession(session3);

        mainForm.checkOut(document);

        Copy copy2 = null;
        for(int i = 0; i < document.takenCopies.size(); i++){
            if(document.takenCopies.get(i).getCheckoutByUser().getId() == userCard2.getId())
                copy2 = document.takenCopies.get(i);
        }
        Assert.assertNotNull(copy2);
        Assert.assertEquals(copy2.getCheckedOutDate(), "26 March");

        Session session4 = new Session(userCard2.userType, 29, 3);
        session2.userCard = userCard2;
        mainForm.setSession(session4);
        returnForm.setSession(session4);
        returnForm.renew(userCard2, copy2);
        Assert.assertEquals(copy2.getCheckedOutDate(), "29 March");

        Assert.assertEquals( 1, userCard.checkedOutCopies.size());
        Assert.assertEquals("26 April", copy.getDueDate());

        Assert.assertEquals( 1, userCard.checkedOutCopies.size());
        Assert.assertEquals("5 April", copy2.getDueDate());
    }
}