package storage;

import core.ActionNote;
import documents.*;
import org.json.JSONObject;
import users.Notification;
import users.Session;
import users.UserCard;
import users.userTypes.Admin;
import users.userTypes.Librarian;
import users.userTypes.UserType;

import java.util.ArrayList;
import java.util.HashMap;

public class SecuredDatabaseManager extends Session{
    DatabaseManager databaseManager;
    UserType userType;
    UserCard userCard;


    public SecuredDatabaseManager(DatabaseManager databaseManager, UserCard userCard, int day, int month) {
        super(userCard.userType, day, month);
        this.databaseManager = databaseManager;
        setFor(userCard);
        databaseManager.load();
    }

    public void setFor(UserCard userCard) {
        this.userType = userCard.userType;
        this.userCard = userCard;
    }

    public void setDatabaseManager(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }

    /**
     * Cached JSON Object that connected with .json file
     */
    private JSONObject jsonData;
    private JSONObject userCardData;
    private JSONObject documentsData;

    /**
     * Loaded in database manager objects with access in runtime
     */
    private HashMap<Integer, UserCard> userCards = new HashMap<>();
    private HashMap<Integer, Document> documents = new HashMap<>();

    /**
     * Name of JSON file(database) which manager works with
     */
    private String fileDataName;

    public void resetDatabase() {
        if (this.userType.isHasAccessPerm() || this.userType.isHasEditingLibrarianPerm()) {
            this.databaseManager.resetDatabase();
        }
    }


    public UserType getUserType() {
        return userType;
    }

    /**
     * This method save new state in database
     * Use it to add or edit user card
     *
     * @param userCard that should be saved
     */
    public void saveUserCard(UserCard userCard) {

        boolean forLibrarian = this.userType.isHasAccessPerm() && this.userType.isHasModifyPerm()
                && this.userType.isHasAddPerm() && !(Librarian.class.isAssignableFrom(userCard.userType.getClass()));

        boolean forAdmin = this.userType.isHasEditingLibrarianPerm() && Librarian.class.isAssignableFrom(userCard.userType.getClass());

        if (forAdmin || forLibrarian) {
            this.databaseManager.saveUserCard(userCard);
            databaseManager.actionManager.actionNotes.add(new ActionNote(this.userCard, day, month, ActionNote.ADD_USER_ACTION_ID, userCard));
        }
    }

    public void removeUserCard(UserCard userCard) {

        boolean forLibrarian = this.userType.isHasAccessPerm() && this.userType.isHasModifyPerm()
                && this.userType.isHasDeletePerm() && !(Librarian.class.isAssignableFrom(userCard.userType.getClass()));

        boolean forAdmin = this.userType.isHasEditingLibrarianPerm() && Librarian.class.isAssignableFrom(userCard.userType.getClass());
        if (forAdmin || forLibrarian) {
            userCardData.remove(Integer.toString(userCard.getId()));
            this.databaseManager.update();
            this.databaseManager.loadUserCards();
        }
    }

    public void addDocuments(Document document) {
        if (this.userType.isHasAddPerm()) {
            this.saveDocuments(document);
        }
    }

    public void addUserCard(UserCard userCard) {
        if (this.userType.isHasAddPerm() && (!(Admin.class.isAssignableFrom(userCard.userType.getClass())))) {
            this.saveUserCard(userCard);
        }
    }

    public void saveDocuments(Document document) {
        //Update data of userCard
        if (this.userType.isHasAccessPerm() && this.userType.isHasModifyPerm()) {
            Document old = databaseManager.getDocuments(document.getID());
            int oldnum = old == null ? 0 : old.getNumberOfAllCopies();
            this.databaseManager.saveDocuments(document);
            if (document.getNumberOfAllCopies() != oldnum) {
                for (int i = 0; document.getNumberOfAllCopies() + i < oldnum; i++) {
                    databaseManager.actionManager.actionNotes.add(
                            new ActionNote(userCard, day, month, ActionNote.ADD_COPY_ACTION_ID, document)
                    );
                }
            }
        }
    }

    public void removeDocuments(Document document) {
        documentsData.remove(Integer.toString(document.getID()));
        this.databaseManager.update();
        this.databaseManager.loadDocuments();
    }

    public UserCard getUserCard(int id) {
        if (this.userType.isHasAccessPerm() || this.userType.isHasEditingLibrarianPerm()) {
            return this.databaseManager.getUserCard(id);
        }
        //if the user has no access permission he will get nothing
        else return null /*this.userCard*/;
    }

    public Document getDocuments(int id) {
        if (this.userType.isHasAccessPerm()) {
            return this.databaseManager.getDocuments(id);
        } else return null;
    }

    public void checkOut(Document document, int sessionDay, int sessionMonth) {
        boolean flag = isAvailableForUser(document);
        if (!document.isReference() && flag) {
            if (document.getNumberOfAvailableCopies() > 0) {
                document.takeCopy(userCard, sessionDay, sessionMonth);
                databaseManager.saveUserCard(userCard);
                databaseManager.saveDocuments(document);
                //databaseManager.load();
                databaseManager.actionManager.actionNotes.add(new ActionNote(userCard, sessionDay, sessionMonth, ActionNote.CHECK_OUT_DOCUMENT_ACTION_ID, document));
            } else {
                document.putInPQ(userCard);
                databaseManager.saveDocuments(document);
                databaseManager.saveUserCard(userCard);
                updateSession();
                databaseManager.actionManager.actionNotes.add(new ActionNote(userCard, sessionDay, sessionMonth, ActionNote.REQUEST_DOCUMENT_ACTION_ID, document));
            }
            databaseManager.update();
            updateSession();
        }
    }

    private void updateSession() {
        userCard = databaseManager.getUserCard(userCard.getId());
    }

    private boolean isAvailableForUser(Document document) {
       /* if(!(session.userCard.checkedOutCopies.isEmpty())){
        for (int i = 0; i < session.userCard.checkedOutCopies.size(); i++) {
            if (session.userCard.checkedOutCopies.get(i).getDocumentID() == openDocumentID)
                return false;
        }}*/

        for (int i = 0; i < document.bookedCopies.size(); i++) {
            if (document.bookedCopies.get(i).getCheckoutByUser().getId() == userCard.getId())
                return false;
        }

        for (int i = 0; i < document.requestedBy.size(); i++) {
            if (document.requestedBy.contains(databaseManager.getUserCard(userCard.getId())))
                return false;
        }

        return true;
    }


    public Integer[] getUserCardsID() {
        if (this.userType.isHasAccessPerm()) {
            return userCards.keySet().toArray(new Integer[0]);
        } else {
            Integer[] s = new Integer[userCards.size()];
            for (int i = 0; i < s.length; i++) {
                s[i] = -1;

            }
            return s;
        }
    }

    public Integer[] getDocumentsID() {
        if (this.userType.isHasAccessPerm()) {
            return documents.keySet().toArray(new Integer[0]);
        } else {
            Integer[] s = new Integer[userCards.size()];
            for (int i = 0; i < s.length; i++) {
                s[i] = -1;

            }
            return s;
        }
    }

    public void outstandingRequest(Document doc) {
        boolean ok = false;
        if (userType.isHasDeletePerm()) {
            ok = true;
            for (UserCard userCard : doc.requestedBy) {
                userCard.notifications.add(new Notification(Notification.REQECT_NOTIFICATION, doc.getID()));
                databaseManager.saveUserCard(userCard);
                databaseManager.actionManager.actionNotes.add(new ActionNote(
                        this.userCard, day, month, ActionNote.NOTIFY_REMOVED_FROM_WAITING_LIST_ACTION_ID, userCard, doc
                ));
            }
            for (Copy c : doc.takenCopies) {
                UserCard u = c.getCheckoutByUser();
                u.notifications.add(new Notification(Notification.OUTDATNDING_REQUEST_NOTIFICATION_FOR_CHECKED_OUT_US, doc.getID()));
                databaseManager.saveUserCard(u);
                databaseManager.actionManager.actionNotes.add(new ActionNote(
                        userCard, day, month, ActionNote.NOTIFY_TO_RETURN_ACTION_ID, u, doc
                ));
            }
            doc.deletePQ();
            saveDocuments(doc);
            databaseManager.actionManager.actionNotes.add(new ActionNote(
               userCard,day, month, ActionNote.REMOVE_WAITING_LIST_ACTION_ID, doc
            ));
        }
        databaseManager.actionManager.actionNotes.add(new ActionNote(
                userCard, day, month, ActionNote.OUTSTANDING_REQUEST_ACTION_ID, userCard, doc, ok
        ));
    }

    public ArrayList<Document> getAllDocuments() {
        ArrayList<Document> result = new ArrayList<>();
        if (this.userType.isHasAccessPerm() || this.userType.isHasEditingLibrarianPerm()) {
            result = this.databaseManager.getAllDocuments();
            return result;
        }
        return null;
    }

    public ArrayList<UserCard> getAllUsers() {

        ArrayList<UserCard> result = new ArrayList<>();
        if (this.userType.isHasAccessPerm() || this.userType.isHasEditingLibrarianPerm()) {
            result = this.databaseManager.getAllUsers();
            return result;
        }
        return null;
    }


    public ArrayList<Document> filterDocument(Filter filter) {
        return databaseManager.filterDocument(filter);
    }

    /**
     * method that set the reference label to a doc if it has a certain number of copies
     *
     * @param doc
     */
    public void rebalanceForReferenceType(Document doc) {
        SecuredDatabaseManager securedDatabaseManager = this;
        if (Book.class.isAssignableFrom(doc.getClass())) {
            if (doc.getNumberOfAvailableCopies() == 1) {
                doc.isReference = true;
            }
        } else if (AVMaterial.class.isAssignableFrom(doc.getClass())) {
            if (doc.getNumberOfAvailableCopies() == 0) {
                doc.isReference = true;
            }
        } else if (JournalArticle.class.isAssignableFrom(doc.getClass())) {
            if (doc.getNumberOfAvailableCopies() == 1) {
                doc.isReference = true;
            }
        }
        securedDatabaseManager.saveDocuments(doc);
    }

}
