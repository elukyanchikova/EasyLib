import core.ActionManager;
import documents.Book;
import documents.Copy;
import documents.Document;

import forms.AddUserForm;
import forms.MainForm;
import forms.ReturnForm;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import storage.DatabaseManager;
import storage.Filter;
import storage.SecuredDatabaseManager;
import users.Notification;
import users.Session;
import users.UserCard;
import users.userTypes.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class TestCases4 {

    private DatabaseManager databaseManager = new DatabaseManager("TestCases4");

    private final int ADMIN_ID = 0;

    private Integer l1Id;
    private Integer l2Id;
    private Integer l3Id;

    private Integer b1Id;
    private Integer b2Id;
    private Integer b3Id;

    private Integer p1Id;
    private Integer p2Id;
    private Integer p3Id;

    private Integer sId;
    private Integer vId;

    /**
     * Essential settings
     */
    @Before
    public void init() {
        databaseManager.resetDatabase();
        l1Id = null;
        l2Id = null;
        l3Id = null;

        b1Id = null;
        b2Id = null;
        b3Id = null;

        p1Id = null;
        p2Id = null;
        p3Id = null;

        sId = null;
        vId = null;
    }

    /**
     * Method to create book d1
     * @return document 1
     */
    private Book makeB1() {
        ArrayList<Copy> b1_copies = new ArrayList<>();

        ArrayList<String> b1_authors = new ArrayList<>();
        b1_authors.add("Thomas H. Cormen");
        b1_authors.add("Charles E. Leiserson");
        b1_authors.add("Ronald L. Rivest");
        b1_authors.add("Clifford Stein");

        ArrayList<String> b1_keywords = new ArrayList<>();
        b1_keywords.add("Algorithms");
        b1_keywords.add("Data Structures");
        b1_keywords.add("Complexity");
        b1_keywords.add("Computational Theory");

        Book b1 = new Book("Introduction to Algorithms", b1_authors,
                b1_keywords, 5000, b1_copies, "MIT Press", 2009, "Third edition", false);
        b1.setCopy(new Copy(b1, 1, 1));
        b1.setCopy(new Copy(b1, 2, 1));
        b1.setCopy(new Copy(b1, 3, 1));
        return b1;
    }

    /**
     * Method to create book d2
     * @return document d2
     */
    private Book makeB2() {
        ArrayList<Copy> b2_copies = new ArrayList<Copy>();


        ArrayList<String> b2_authors = new ArrayList<String>();
        b2_authors.add("Niklaus Wirth");

        ArrayList<String> b2_keywords = new ArrayList<String>();
        b2_keywords.add("Algorithms");
        b2_keywords.add("Data Structures");
        b2_keywords.add("Search Algorithms");
        b2_keywords.add("Pascal");

        Book b2 = new Book("Algorithms + Data Structures = Programs", b2_authors,
                b2_keywords, 5000, b2_copies, "Prentice Hall PTR", 1978, "First edition", false);
        b2.setCopy(new Copy(b2, 1, 1));
        b2.setCopy(new Copy(b2, 2, 1));
        b2.setCopy(new Copy(b2, 3, 1));
        return b2;
    }

    /**
     * Method to create book d3
     * @return document d3
     */
    private Book makeB3() {
        ArrayList<Copy> b3_copies = new ArrayList<Copy>();

        ArrayList<String> b3_authors = new ArrayList<String>();
        b3_authors.add("Donald E. Knuth");

        ArrayList<String> b3_keywords = new ArrayList<String>();
        b3_keywords.add("Algorithms");
        b3_keywords.add("Combinatorial Algorithms");
        b3_keywords.add("Recursion");


        Book b3 = new Book("The Art of Computer Programming", b3_authors,
                b3_keywords, 5000, b3_copies, "AddisonWesley Longman Publishing Co., Inc.", 1997, "Third edition", false);
        b3.setCopy(new Copy(b3, 1, 1));
        b3.setCopy(new Copy(b3, 2, 1));
        b3.setCopy(new Copy(b3, 3, 1));
        return b3;

    }

    SecuredDatabaseManager securedFor(int id) {
        return new SecuredDatabaseManager(databaseManager, databaseManager.getUserCard(id), 1, 2);
    }

    /**
     * initial state: there's one admin in system
     * action: try to create second admin
     * effect: system do nothing
     */
    @Test
    public void Test1() {
        SecuredDatabaseManager securedDatabaseManager = securedFor(ADMIN_ID);
        UserCard adminTry = new UserCard("AdminPrime", "AdminPrime", new Admin(), "123", "Inno");
        securedDatabaseManager.addUserCard(adminTry);

        long admins = databaseManager.getAllUsers().stream()
                .filter(u -> u.userType instanceof Admin)
                .count();

        assertEquals("The system contains only one Admin", 1, admins);
    }

    /**
     * Test case 2
     * Creates 3 admins
     * Checks are there 3 admins
     * This test is an initial statement for TC3
     */
    @Test
    public void Test2() {
        Session session = new Session(databaseManager.getUserCard(ADMIN_ID).userType, 25, 4);
        assertTrue("Session is leads by admin", String.valueOf(session.getUser()).contains("Admin"));


        UserCard l1 = new UserCard("Lib1", "Librarian1", new Librarian(), "123", "Inno");
        l1Id = l1.getId();
        UserCard l2 = new UserCard("Lib2", "Librarian2", new Librarian(), "124", "Inno");
        l2Id = l2.getId();
        UserCard l3 = new UserCard("Lib3", "Librarian3", new Librarian(), "125", "Inno");
        l3Id = l3.getId();

        SecuredDatabaseManager securedDatabaseManager = securedFor(ADMIN_ID);

        securedDatabaseManager.saveUserCard(l1);
        securedDatabaseManager.saveUserCard(l2);
        securedDatabaseManager.saveUserCard(l3);

        l1 = securedDatabaseManager.getUserCard(l1Id);
        ((Librarian) l1.userType).setPriv1();

        l2 = securedDatabaseManager.getUserCard(l2Id);
        ((Librarian) l2.userType).setPriv2();

        l3 = securedDatabaseManager.getUserCard(l3Id);
        ((Librarian) l3.userType).setPriv3();

        securedDatabaseManager.saveUserCard(l1);
        securedDatabaseManager.saveUserCard(l2);
        securedDatabaseManager.saveUserCard(l3);

        long librarians = securedDatabaseManager.getAllUsers()
                .stream()
                .filter(uc -> uc.userType instanceof Librarian)
                .count();

        assertEquals("Number of librarians in system equals 3", 3, librarians);
    }

    /**
     * Test Case 3
     * Checks availability of librarian
     * Checks availability of some documents
     * This test case is an initial state for TC3
     */
    @Test
    public void Test3() {
        Test2();

        SecuredDatabaseManager securedDatabaseManager = securedFor(l1Id);
        UserCard l1 = databaseManager.getUserCard(1);
        Session session1 = new Session(l1.userType, 25, 4);
        assertTrue("Session is leads by L1 without any permissions", String.valueOf(session1.getUser()).contains("Librarian"));

        Book b1 = makeB1();
        Book b2 = makeB2();
        Book b3 = makeB3();

        securedDatabaseManager.addDocuments(b1);
        securedDatabaseManager.addDocuments(b2);
        securedDatabaseManager.addDocuments(b3);

        int docs = securedDatabaseManager.getAllDocuments().size();
        assertEquals("No books were added because of absence of corresponding permissions(l1)", 0, docs);
    }

    /**
     * Test Case 4
     * Checks availability of librarian
     * Checks amount of copies for used docs
     * This test case is an initial state for test cases 5, 6, 7
     */
    @Test
    public void Test4() {
        Test3();

        SecuredDatabaseManager securedDatabaseManager = securedFor(l2Id);
        UserCard l2 = securedDatabaseManager.getUserCard(l2Id);

        Session session = new Session(l2.userType, 25, 4);
        assertTrue("Session is leads by l2 with permissions 2", String.valueOf(session.getUser()).contains("Librarian"));

        Book b1 = makeB1();
        Book b2 = makeB2();
        Book b3 = makeB3();

        securedDatabaseManager.addDocuments(b1);
        securedDatabaseManager.addDocuments(b2);
        securedDatabaseManager.addDocuments(b3);


        int numbOfCopies = 0;
        int numbOfDocs = databaseManager.getAllDocuments().size();

        for (Document d : databaseManager.getAllDocuments()) {
            numbOfCopies += d.getNumberOfAllCopies();
        }
        assertEquals("L2 add 9 copies", 9, numbOfCopies);
        assertEquals("L2 add 3 books", 3, numbOfDocs);

        b1Id = b1.getID();
        b2Id = b2.getID();
        b3Id = b3.getID();

        int numbOfUserCardsInit = securedDatabaseManager.getAllUsers().size();

        ArrayList<Copy> p1_checkedOutCopies = new ArrayList<Copy>();
        ArrayList<Document> p1_requestedDocs = new ArrayList<Document>();
        UserCard p1 = new UserCard(1010, "Sergey", "Afonso", new Professor(),
                "30001", "Via Margutta, 3", p1_checkedOutCopies, p1_requestedDocs);
        securedDatabaseManager.addUserCard(p1);
        p1Id = p1.getId();

        ArrayList<Copy> p2_checkedOutCopies = new ArrayList<Copy>();
        ArrayList<Document> p2_requestedDocs = new ArrayList<Document>();
        UserCard p2 = new UserCard(1011, "Nadia", "Teixeria", new Professor(),
                "30002", "Via Sacra, 13", p2_checkedOutCopies, p2_requestedDocs);
        securedDatabaseManager.addUserCard(p2);
        p2Id = p2.getId();

        ArrayList<Copy> p3_checkedOutCopies = new ArrayList<Copy>();
        ArrayList<Document> p3_requestedDocs = new ArrayList<Document>();
        UserCard p3 = new UserCard(1100, "Elvira", "Espindola", new Professor(),
                "30003", "Via del Corso, 22", p3_checkedOutCopies, p3_requestedDocs);
        securedDatabaseManager.addUserCard(p3);
        p3Id = p3.getId();

        ArrayList<Copy> s_checkedOutCopies = new ArrayList<Copy>();
        ArrayList<Document> s_requestedDocs = new ArrayList<Document>();
        UserCard s = new UserCard(1101, "Andrey", "Velo", new Student(),
                "30004", "Avenida Mazatlan 250", s_checkedOutCopies, s_requestedDocs);
        securedDatabaseManager.addUserCard(s);
        sId = s.getId();

        ArrayList<Copy> v_checkedOutCopies = new ArrayList<Copy>();
        ArrayList<Document> v_requestedDocs = new ArrayList<Document>();
        UserCard v = new UserCard(1110, "Veronika", "Rama", new VisitingProfessor(),
                "30005", "Stret Atocha, 27", v_checkedOutCopies, v_requestedDocs);
        securedDatabaseManager.addUserCard(v);
        vId = v.getId();

        int numbOfUsers = databaseManager.getAllUsers().size();
        assertEquals("L2 added 5 users", 5, numbOfUsers - numbOfUserCardsInit);

    }

    /**
     * Checks availability of user
     * Checks amount of copies for used docs
     */
    @Test
    public void Test5() {
        Test4();

        SecuredDatabaseManager securedDatabaseManagerL3 = securedFor(l3Id);

        UserCard l3 = databaseManager.getUserCard(l3Id);

        Session session1 = new Session(l3.userType, 25, 4);
        assertTrue("Session is leads by l3 with all permissions", String.valueOf(session1.getUser()).contains("Librarian"));

        Document b1 = securedDatabaseManagerL3.getDocuments(b1Id);
        b1.removeCopy(b1.availableCopies.get(2));
        securedDatabaseManagerL3.saveDocuments(b1);

        b1 = securedDatabaseManagerL3.getDocuments(b1Id);
        assertEquals("Number of b1 copies equals 2", 2, b1.availableCopies.size());

        assertTrue("L3 can check information about user 4", securedFor(l3Id).getUserType().isHasAccessPerm());
    }

    /**
     * p1 checks out d3.
     * p2 checks out d3.
     * s checks out d3.
     * v checks out d3.
     * p3 checks out d3.
     * Librarian l1 places an outstanding request on document d3
     * This test case is an initial state for TC8
     */
    @Test
    public void Test6() {
        Test4();
        securedFor(p1Id);

        int day = 26;
        int month = 3;

        securedFor(p1Id).checkOut(databaseManager.getDocuments(b3Id), day, month);
        securedFor(p2Id).checkOut(databaseManager.getDocuments(b3Id), day, month);
        securedFor(sId).checkOut(databaseManager.getDocuments(b3Id), day, month);
        securedFor(vId).checkOut(databaseManager.getDocuments(b3Id), day, month);
        securedFor(p3Id).checkOut(databaseManager.getDocuments(b3Id), day, month);
        securedFor(l1Id).outstandingRequest(databaseManager.getDocuments(b3Id));

        ArrayList<Copy> takenCopies = databaseManager.getDocuments(b3Id).takenCopies;
        assertTrue("p1 checked out b3", takenCopies.stream().anyMatch(
                c -> databaseManager.getUserCard(p1Id).checkedOutCopies.contains(c)
        ));
        assertTrue("p2 checked out b3", takenCopies.stream().anyMatch(
                c -> databaseManager.getUserCard(p2Id).checkedOutCopies.contains(c)
        ));
        assertTrue("s checked out b3", takenCopies.stream().anyMatch(
                c -> databaseManager.getUserCard(sId).checkedOutCopies.contains(c)
        ));

        assertTrue("b3 has a queue", databaseManager.getDocuments(b3Id).requestedBy.containsAll(
                Arrays.asList(databaseManager.getUserCard(p3Id), databaseManager.getUserCard(vId))
        ));

        assertFalse("l1 not in queue", databaseManager.getDocuments(b3Id).requestedBy.contains(
                databaseManager.getUserCard(l1Id)
        ));
        assertFalse("l1 not checked out b3", takenCopies.stream().anyMatch(
                c -> databaseManager.getUserCard(l1Id).checkedOutCopies.contains(c)
        ));
    }

    /**
     * Same actiaons as TC6 but with another librarian and different out
     */
    @Test
    public void Test7() {
        Test4();
        securedFor(p1Id);

        int day = 26;
        int month = 3;

        securedFor(p1Id).checkOut(databaseManager.getDocuments(b3Id), day, month);
        securedFor(p2Id).checkOut(databaseManager.getDocuments(b3Id), day, month);
        securedFor(sId).checkOut(databaseManager.getDocuments(b3Id), day, month);
        securedFor(vId).checkOut(databaseManager.getDocuments(b3Id), day, month);
        securedFor(p3Id).checkOut(databaseManager.getDocuments(b3Id), day, month);
        securedFor(l3Id).outstandingRequest(databaseManager.getDocuments(b3Id));

        ArrayList<Copy> takenCopies = databaseManager.getDocuments(b3Id).takenCopies;

        assertTrue("p1 checked out b3", takenCopies.stream().anyMatch(
                c -> databaseManager.getUserCard(p1Id).checkedOutCopies.contains(c)
        ));
        assertTrue("p2 checked out b3", takenCopies.stream().anyMatch(
                c -> databaseManager.getUserCard(p2Id).checkedOutCopies.contains(c)
        ));
        assertTrue("s checked out b3", takenCopies.stream().anyMatch(
                c -> databaseManager.getUserCard(sId).checkedOutCopies.contains(c)
        ));

        assertTrue("b3 has no queue", databaseManager.getDocuments(b3Id).requestedBy.isEmpty());

        assertTrue("Waiting list for b3 is empty", databaseManager.getDocuments(b3Id).requestedBy.isEmpty());
        UserCard u = databaseManager.getUserCard(p1Id);
        assertTrue("p1 has notification", databaseManager.getUserCard(p1Id).notifications.stream().anyMatch(
                n -> n.id == Notification.OUTDATNDING_REQUEST_NOTIFICATION && n.docID == b3Id
        ));
        assertTrue("p2 has notification", databaseManager.getUserCard(p2Id).notifications.stream().anyMatch(
                n -> n.id == Notification.OUTDATNDING_REQUEST_NOTIFICATION && n.docID == b3Id
        ));
    }

    /**
     * i.         date1: admin1 created librarian l1.
     * ii.        date: admin1 created librarian l2.
     * iii.       date: admin1 created librarian l3.
     * iv.        date: l2 created 3 copies of d1.
     * v.         date: l2 created 3 copies of d2.
     * vi.        date: l2 created 3 copies of d3.
     * vii.       date: l2 created patrons s, p1, p2, p3 and v.
     * viii.      date: l2 checked the System’s information.
     * ix.        p1 checked out d3.
     * x.         p2 checked out d3.
     * xi.        s checked out d3.
     * xii.       v checked out d3.
     * xiii.      p3 checked out d3.
     * xiv.       l1 placed an outstanding request on document
     * d3:        request was denied.
     */

    @Test
    public void Test8() {
        Test6();
        String log = databaseManager.actionManager.getLog();
        for (String s : new String[]{
                "[01/02]Librarian Lib1 Librarian1(ID:1) made an outstanding request on Book The Art of Computer Programming(ID:6) access was denied",
                "[01/02]Librarian Lib2 Librarian2(ID:2) added Student Andrey Velo(ID:1101)",
                "[01/02]Librarian Lib2 Librarian2(ID:2) added Professor Elvira Espindola(ID:1100)",
                "[01/02]Librarian Lib2 Librarian2(ID:2) added Professor Nadia Teixeria(ID:1011)",
                "[01/02]Librarian Lib2 Librarian2(ID:2) added Professor Sergey Afonso(ID:1010)",
                "[01/02]Admin Admin Admin(ID:0) added Librarian Lib2 Librarian2(ID:2)",
                "[01/02]Admin Admin Admin(ID:0) added Librarian Lib1 Librarian1(ID:1)",
                "[01/02]Admin Admin Admin(ID:0) added Librarian Lib3 Librarian3(ID:3)",
                "[01/02]Admin Admin Admin(ID:0) added Librarian Lib2 Librarian2(ID:2)",
                "[01/02]Admin Admin Admin(ID:0) added Librarian Lib1 Librarian1(ID:1)",
                "[26/03]Professor Elvira Espindola(ID:1100) requested Book The Art of Computer Programming(ID:6)",
                "[26/03]VisitingProfessor Veronika Rama(ID:1110) requested Book The Art of Computer Programming(ID:6)",
                "[26/03]Student Andrey Velo(ID:1101) checked out Book The Art of Computer Programming(ID:6)",
                "[26/03]Professor Nadia Teixeria(ID:1011) checked out Book The Art of Computer Programming(ID:6)",
                "[26/03]Professor Sergey Afonso(ID:1010) checked out Book The Art of Computer Programming(ID:6)"}
                ) {
            assertTrue("Log has " + s, log.contains(s));
        }
    }

    /**
     * Same as TC8 but with another initial state
     */
    @Test
    public void Test9() {
        Test7();
        String log = databaseManager.actionManager.getLog();
        for (String s : new String[]{
                "[01/02]Librarian Lib3 Librarian3(ID:3) made an outstanding request on Book The Art of Computer Programming(ID:6)",
                "[01/02]Librarian Lib3 Librarian3(ID:3) removed waiting list of Book The Art of Computer Programming(ID:6)",
                "[01/02]Librarian Lib3 Librarian3(ID:3) notified to return Book The Art of Computer Programming(ID:6) taken by: .Student Andrey Velo(ID:1101)",
                "[01/02]Librarian Lib3 Librarian3(ID:3) notified to return Book The Art of Computer Programming(ID:6) taken by: .Professor Nadia Teixeria(ID:1011)",
                "[01/02]Librarian Lib3 Librarian3(ID:3) notified to return Book The Art of Computer Programming(ID:6) taken by: .Professor Sergey Afonso(ID:1010)",
                "[01/02]Librarian Lib3 Librarian3(ID:3) notified doc not available Book The Art of Computer Programming(ID:6) and that the user was removed from wait list: .VisitingProfessor Veronika Rama(ID:1110)",
                "[01/02]Librarian Lib3 Librarian3(ID:3) notified doc not available Book The Art of Computer Programming(ID:6) and that the user was removed from wait list: .Professor Elvira Espindola(ID:1100)",
                "[01/02]Librarian Lib2 Librarian2(ID:2) added Student Andrey Velo(ID:1101)",
                "[01/02]Librarian Lib2 Librarian2(ID:2) added Professor Elvira Espindola(ID:1100)",
                "[01/02]Librarian Lib2 Librarian2(ID:2) added Professor Nadia Teixeria(ID:1011)",
                "[01/02]Librarian Lib2 Librarian2(ID:2) added Professor Sergey Afonso(ID:1010)",
                "[01/02]Admin Admin Admin(ID:0) added Librarian Lib2 Librarian2(ID:2)",
                "[01/02]Admin Admin Admin(ID:0) added Librarian Lib1 Librarian1(ID:1)",
                "[01/02]Admin Admin Admin(ID:0) added Librarian Lib3 Librarian3(ID:3)",
                "[01/02]Admin Admin Admin(ID:0) added Librarian Lib2 Librarian2(ID:2)",
                "[01/02]Admin Admin Admin(ID:0) added Librarian Lib1 Librarian1(ID:1)",
                "[26/03]Professor Elvira Espindola(ID:1100) requested Book The Art of Computer Programming(ID:6)",
                "[26/03]VisitingProfessor Veronika Rama(ID:1110) requested Book The Art of Computer Programming(ID:6)",
                "[26/03]Student Andrey Velo(ID:1101) checked out Book The Art of Computer Programming(ID:6)",
                "[26/03]Professor Nadia Teixeria(ID:1011) checked out Book The Art of Computer Programming(ID:6)",
                "[26/03]Professor Sergey Afonso(ID:1010) checked out Book The Art of Computer Programming(ID:6)"
        }) {
            assertTrue("Log has " + s, log.contains(s));
        }
    }

    /**
     * TC to check search availability
     * TC4 is an initial state
     */
    @Test
    public void Test10() {
        Test4();

        Filter filter = new Filter();
        filter.title = "Introduction to Algorithms";

        ArrayList<Document> found = securedFor(vId).filterDocument(filter);
        String[] out = new String[found.size()];
        int i = 0;
        for (Document doc : found) {
            out[i] = String.valueOf(doc.title + " by " + doc.authors.get(0));
            i++;
        }
        Assert.assertEquals("Found matched doc", "Introduction to Algorithms by Thomas H. Cormen", out[0]);
    }

    /**
     * TC to check search availability
     * TC4 is an initial state
     */
    @Test
    public void Test11() {
        Test4();

        Filter filter = new Filter();
        filter.title = "Algorithms";

        ArrayList<Document> found = securedFor(vId).filterDocument(filter);
        int in = found.size();
        String[] out = new String[in];
        for (int i = 0; i < in; i++) {
            out[i] = String.valueOf(found.get(i).title + " by " + found.get(i).authors.get(0));
        }
        Assert.assertEquals("Found matched doc 1 ", "Introduction to Algorithms by Thomas H. Cormen", out[0]);
        Assert.assertEquals("Found matched doc 2 ", "Algorithms + Data Structures = Programs by Niklaus Wirth", out[1]);
    }

    /**
     * TC to check search availability
     * TC4 is an initial state
     */
    @Test
    public void Test12() {
        Test4();

        Filter filter = new Filter();
        filter.keywords = "Algorithms";

        ArrayList<Document> found = securedFor(vId).filterDocument(filter);
        int in = found.size();
        String[] out = new String[in];
        for (int i = 0; i < in; i++) {
            out[i] = String.valueOf(found.get(i).title + " by " +
                    found.get(i).authors.
                            get(0));
        }
        Assert.assertEquals("Found matched doc 1 ", "Introduction to Algorithms by Thomas H. Cormen", out[0]);
        Assert.assertEquals("Found matched doc 2 ", "Algorithms + Data Structures = Programs by Niklaus Wirth", out[1]);
        Assert.assertEquals("Found matched doc 3 ", "The Art of Computer Programming by Donald E. Knuth", out[2]);
    }

    /**
     * TC to check search availability
     * TC4 is an initial state
     */
    @Test
    public void Test13() {
        Test4();


        Filter filter = new Filter();
        filter.title = "Algorithms Programming";
        filter.conjunction = true;

        String s = String.valueOf(securedFor(vId).filterDocument(filter));

        Assert.assertEquals("No match found", "[]", s);
    }

    /**
     * TC to check search availability
     * TC4 is an initial state
     */
    @Test
    public void Test14() {
        Test4();

        Filter filter = new Filter();
        filter.title = "Algorithms Programming";
        filter.conjunction = false;

        ArrayList<Document> found = securedFor(vId).filterDocument(filter);
        int in = found.size();
        String[] out = new String[in];
        for (int i = 0; i < in; i++) {
            out[i] = String.valueOf(found.get(i).title + " by " +
                    found.get(i).authors.
                            get(0));
        }
        Assert.assertEquals("Found matched doc 1 ", "Introduction to Algorithms by Thomas H. Cormen", out[0]);
        Assert.assertEquals("Found matched doc 2 ", "Algorithms + Data Structures = Programs by Niklaus Wirth", out[1]);
        Assert.assertEquals("Found matched doc 3 ", "The Art of Computer Programming by Donald E. Knuth", out[2]);
    }


}
