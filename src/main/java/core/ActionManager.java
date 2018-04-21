package core;

import documents.Copy;
import documents.Document;
import storage.DatabaseManager;
import storage.Filter;
import users.Session;
import users.UserCard;

import javax.print.Doc;
import java.util.ArrayList;

public class ActionManager {
    private DatabaseManager databaseManager;
    private Session session;


    public ActionManager(DatabaseManager databaseManager, Session session){
        this.databaseManager = databaseManager;
        this.session = session;
    }

    /* *
     * Check out the document
     *
     * @return true if the document has checked out*/

    //TODO isAvailiable for user , updateSession
    public boolean checkOut(Document document) {
        //boolean flag = isAvailableForUser(document);
        if (!document.isReference() && document.getNumberOfAvailableCopies() > 0 /*&& flag*/) {
            document.takeCopy(session.userCard, session);
            databaseManager.saveUserCard(session.userCard);
            databaseManager.saveDocuments(document);
            databaseManager.load();
            //   updateSession();
            return true;
        }
        return false;
    }

    /**
     * Method for Return Button
     * Allows librarian return patron's book
     * removes a copy from checked out copies array list
     * <p>
     * void
     */
//TODO openDocumentID  , usersListView
    public void returnBtn() {
       /* if (openDocumentID > -1) {
            ArrayList<UserCard> userCardsWithCopy = new ArrayList<>();
            Document document = databaseManager.getDocuments(databaseManager.getDocumentsID()[openDocumentID]);
            for (int i = 0; i < document.takenCopies.size(); i++) {
                userCardsWithCopy.add(document.takenCopies.get(i).getCheckoutByUser());
            }
            //UserCard userCard = userCardsWithCopy.get(userListView.getSelectionModel().getSelectedIndex());
            for (int i = 0; i < document.takenCopies.size(); i++) {
                *//*if (document.takenCopies.get(i).getCheckoutByUser().getId() == userCard.getId())
                {
              //      returnCopy(document.takenCopies.get(i), databaseManager.getUserCard(userCard.getId()));
                }*//*
            }

            databaseManager.saveDocuments( databaseManager.getDocuments(document.getID()));
           // databaseManager.saveUserCard(databaseManager.getUserCard(userCard.getId()));

        }*/
    }

    //TODO autobooking
    public void returnCopy(Copy copy, UserCard userCard) {
        databaseManager.getUserCard(userCard.getId()).checkedOutCopies.remove(copy);
        databaseManager.getDocuments(databaseManager.getDocuments(copy.getDocumentID()).getID()).returnCopy(copy);
        //this.autobooking(databaseManager.getDocuments(copy.getDocumentID()));
    }
    //TODO request  search

    public ArrayList<Document> filter(Filter filter){
        return databaseManager.filterDocument(filter);
    }

}
