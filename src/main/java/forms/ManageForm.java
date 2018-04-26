package forms;

import core.ActionManager;
import core.ActionNote;
import documents.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import storage.DatabaseManager;
import users.Notification;
import users.Session;
import users.UserCard;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Return form allows librarian to see the list of documents and users that have checked out it
 * also allow to return the book for any users
 */
public class ManageForm {

    private Stage stage;
    private Scene scene;
    private Session session;
    private DatabaseManager databaseManager;
    private ActionManager actionManager;

    //ArrayList<Document> documents = new ArrayList<>();
    //ArrayList<UserCard> users = new ArrayList<>();
    private int openDocumentID = -1;

    @FXML
    private GridPane documentInfoPane;

    @FXML
    private ListView<Document> documentListView;
    @FXML
    private ListView<UserCard> userBookingListView;
    @FXML
    private ListView<UserCard> userRequestsListView;

    @FXML
    private static Button backBtn;
    @FXML
    private static Button acceptBtn;
    @FXML
    private static Button rejectBtn;
    @FXML
    private static Button outstandingRequestBtn;

    /**
     * @param primaryStage    - Stage
     * @param currentSession  - Session
     * @param databaseManager - brings database link to the current form for modifying
     * @throws Exception
     */
    public void startForm(Stage primaryStage, Session currentSession, DatabaseManager databaseManager, ActionManager actionManager) throws Exception {
        this.session = currentSession;
        this.stage = primaryStage;
        this.databaseManager = databaseManager;
        this.actionManager = actionManager;
        sceneInitialization();
        stage.setScene(scene);
        stage.show();
    }
    /**
     * Set new session to the form
     */
    public void setSession(Session session) {
        this.session = session;
    }

    public void setActionManager(ActionManager actionManager) {
        this.actionManager = actionManager;
    }

    private void sceneInitialization() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLFiles/ManageForm.fxml"));
        loader.setController(this);
        GridPane root = loader.load();
        this.scene = new Scene(root, 1000, 700);

        documentListView = (ListView<Document>) scene.lookup("#documentListView");
        userBookingListView = (ListView<UserCard>) scene.lookup("#userBookingListView");
        userRequestsListView = (ListView<UserCard>) scene.lookup("#userRequestsListView");

        // requestedByListView = (ListView<UserCard>) scene.lookup("#requestedByListView");

        documentInfoPane = (GridPane) scene.lookup("#documentInfoPane");

        outstandingRequestBtn = (Button) scene.lookup("#outstandingRequestButton");

        acceptBtn = (Button) scene.lookup("#acceptBtn");
        rejectBtn = (Button) scene.lookup("#rejectBtn");
        backBtn = (Button) scene.lookup("#backBtn");

        documentListView.setItems(FXCollections.observableArrayList(databaseManager.getAllDocuments()));
        documentListView.setCellFactory(new Callback<ListView<Document>, ListCell<Document>>() {
            public ListCell<Document> call(ListView<Document> documentListView) {
                return new ListCell<Document>() {
                    @Override
                    protected void updateItem(Document document, boolean flag) {
                        super.updateItem(document, flag);
                        if (document != null) {
                            setText(document.title);
                        }
                    }
                };
            }
        });


    }

    @FXML
    public void clickOnDocumentListView() {

        //get selected element
        if (documentListView.getSelectionModel().getSelectedIndex() > -1) {
            //If no document was opened
            if (openDocumentID == -1) {
                documentInfoPane.setVisible(true);
            }
        }

        Document chosenDocument = selectDocument(documentListView.getSelectionModel().getSelectedIndex()); //chosen document

        ArrayList<UserCard> userCardsBookedCopy = new ArrayList<>();

        for (int i = 0; i < chosenDocument.bookedCopies.size(); i++) {
            userCardsBookedCopy.add(chosenDocument.bookedCopies.get(i).getCheckoutByUser());
        }

        userBookingListView.setItems(FXCollections.observableArrayList(userCardsBookedCopy));
        userBookingListView.setCellFactory(new Callback<ListView<UserCard>, ListCell<UserCard>>() {
            public ListCell<UserCard> call(ListView<UserCard> userListView) {
                return new ListCell<UserCard>() {
                    @Override
                    protected void updateItem(UserCard userCard, boolean flag) {
                        super.updateItem(userCard, flag);
                        if (userCard != null)
                            setText(userCard.name);
                    }
                };
            }
        });

        ArrayList<UserCard> userCardsRequested = new ArrayList<>();
        userCardsRequested.addAll((chosenDocument.requestedBy));
        Collections.sort(userCardsRequested);

        userRequestsListView.setItems(FXCollections.observableArrayList(userCardsRequested));
        userRequestsListView.setCellFactory(new Callback<ListView<UserCard>, ListCell<UserCard>>() {
            public ListCell<UserCard> call(ListView<UserCard> userListView) {
                return new ListCell<UserCard>() {
                    @Override
                    protected void updateItem(UserCard userCard, boolean flag) {
                        super.updateItem(userCard, flag);
                        if (userCard != null)
                            setText(userCard.name);
                    }
                };
            }
        });
    }

    public Document selectDocument(int id) {
        openDocumentID = id;
        return databaseManager.getDocuments(databaseManager.getDocumentsID()[openDocumentID]);
    }

    @FXML
    public void outstbtn(){
        outstandingRequest(selectDocument(documentListView.getSelectionModel().getSelectedIndex()));
    }

    @FXML
    public void clickOnAcceptBtn(){
        Document chosenDoc = selectDocument(documentListView.getSelectionModel().getSelectedIndex());
        ArrayList<UserCard> userCardsBookedCopy = new ArrayList<>();

        for (int i = 0; i < chosenDoc.bookedCopies.size(); i++) {
            userCardsBookedCopy.add(chosenDoc.bookedCopies.get(i).getCheckoutByUser());
        }

        UserCard user = databaseManager.getUserCard(userCardsBookedCopy.get(userBookingListView.getSelectionModel().getSelectedIndex()).getId());
        for(int i = 0; i < chosenDoc.bookedCopies.size(); i++){
            if(chosenDoc.bookedCopies.get(i).getCheckoutByUser().getId() == user.getId()){
                chosenDoc.takenCopies.add(chosenDoc.bookedCopies.get(i));
                user.checkedOutCopies.add(chosenDoc.bookedCopies.get(i));
                chosenDoc.bookedCopies.remove(chosenDoc.bookedCopies.get(i));
                user.notifications.add(new Notification(Notification.ACCEPT_NOTIFICATION, chosenDoc.getID()));
                databaseManager.saveUserCard(user);
                databaseManager.saveDocuments(chosenDoc);
                actionManager.actionNotes.add(new ActionNote(session.userCard, session.day, session.month, ActionNote.ACCEPT_REQUEST_ACTION_ID, user, chosenDoc));
                actionManager.actionNotes.add(new ActionNote(user, session.day, session.month, ActionNote.CHECK_OUT_DOCUMENT_ACTION_ID, chosenDoc));
                databaseManager.update();
            }
        }
    }

    @FXML
    public void clickOnRejectBtn(){
        Document chosenDoc = selectDocument(documentListView.getSelectionModel().getSelectedIndex());
        ArrayList<UserCard> userCardsBookedCopy = new ArrayList<>();
        for (int i = 0; i < chosenDoc.bookedCopies.size(); i++) {
            userCardsBookedCopy.add(chosenDoc.bookedCopies.get(i).getCheckoutByUser());
        }

        UserCard user = databaseManager.getUserCard(userCardsBookedCopy.get(userBookingListView.getSelectionModel().getSelectedIndex()).getId());
        for(int i = 0; i < chosenDoc.bookedCopies.size(); i++){
            if(chosenDoc.bookedCopies.get(i).getCheckoutByUser().getId() == user.getId()){
                chosenDoc.availableCopies.add(chosenDoc.bookedCopies.get(i));
                chosenDoc.bookedCopies.get(i).checkoutBy(null);
                chosenDoc.bookedCopies.remove(chosenDoc.bookedCopies.get(i));
                user.notifications.add(new Notification(Notification.REQECT_NOTIFICATION, chosenDoc.getID()));
                databaseManager.saveUserCard(user);
                databaseManager.saveDocuments(chosenDoc);
                actionManager.actionNotes.add(new ActionNote(session.userCard, session.day, session.month, ActionNote.REJECT_REQUEST_ACTION_ID, user, chosenDoc));
                autobooking(chosenDoc);
                databaseManager.update();
            }
        }
    }

    private void autobooking(Document document){
        UserCard[] userCards = document.requestedBy.toArray(new UserCard[0]);
        Arrays.sort(userCards);
        int ind =userCards.length-1;
        if(userCards.length > 0) {
            userCards[ind].notifications.add(new Notification(Notification.GET_COPY_NOTIFICATION, document.getID()));
            if (!document.isReference() && document.getNumberOfAvailableCopies() > 0) {
                document.bookedCopies.add(document.availableCopies.get(0));
                document.availableCopies.get(0).checkoutBy(userCards[ind]);
                document.availableCopies.remove(0);
                document.requestedBy.remove(userCards[ind]);
                databaseManager.saveDocuments(document);
                databaseManager.saveUserCard(userCards[ind]);
                databaseManager.load();
                actionManager.actionNotes.add(new ActionNote(userCards[ind], session.day, session.month, ActionNote.BOOK_DOCUMENT_ACTION_ID, document));
                databaseManager.update();
            }
        }
    }

    public void outstandingRequest(Document doc){

        UserCard[] users = new UserCard[0];
        users = doc.requestedBy.toArray(users);
        for(int i = 0; i < users.length; i++){
            users[i].notifications.add(new Notification(Notification.OUTDATNDING_REQUEST_NOTIFICATION_FOR_PQ, doc.getID()));
            databaseManager.saveUserCard(users[i]);
            actionManager.actionNotes.add(new ActionNote(session.userCard, session.day, session.month, ActionNote.NOTIFY_REMOVED_FROM_WAITING_LIST_ACTION_ID, doc));
            databaseManager.update();
        }
        ArrayList<UserCard> userCO = new ArrayList<>();
        for (int i = 0; i <doc.takenCopies.size(); i++) {
            userCO.add(doc.takenCopies.get(i).getCheckoutByUser());
        }

        for (int i = 0; i <userCO.size() ; i++) {
            userCO.get(i).notifications.add(new Notification(Notification.OUTDATNDING_REQUEST_NOTIFICATION_FOR_CHECKED_OUT_US, doc.getID()));
            databaseManager.saveUserCard(userCO.get(i));
            actionManager.actionNotes.add(new ActionNote(session.userCard, session.day, session.month, ActionNote.NOTIFY_TO_RETURN_ACTION_ID, doc));
            databaseManager.update();
        }
        for(int i = 0; i < doc.takenCopies.size(); i++){
            doc.takenCopies.get(i).hasRenewed = true;
        }
        doc.deletePQ();
        databaseManager.saveDocuments(doc);
    }

    @FXML
    public void back() throws Exception {
        MainForm mainForm = new MainForm();
        mainForm.startForm(stage, session, databaseManager, actionManager);
    }

    public void setDatabaseManager(DatabaseManager databaseManager){
        this.databaseManager = databaseManager;
    }

}
