package forms;

import core.ActionManager;
import core.ActionNote;
import documents.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import storage.DatabaseManager;
import users.Notification;
import users.Session;
import users.UserCard;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Return form allows librarian to see the list of documents and users that have checked out it
 * also allow to return the book for any users
 */
public class ReturnForm {

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
    private ListView<UserCard> userListView;

    @FXML
    private Label titleLbl;
    @FXML
    private Label authorsLbl;
    @FXML
    private Label documentTypeLbl;
    @FXML
    private Label priceLbl;
    @FXML
    private Label keywordsLbl;


    @FXML
    private Label labelAddition1;
    @FXML
    private Label labelAddition2;
    @FXML
    private Label labelAddition3;

    @FXML
    private Label additionLbl1;
    @FXML
    private Label additionLbl2;
    @FXML
    private Label additionLbl3;

    @FXML
    private static Button backButt;
    @FXML
    private static Button returnButton;

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

    public void setActionManager(ActionManager actionManager) {
        this.actionManager = actionManager;
    }

    /**
     * Scene initialization method
     * Sets information about chosen book on a labels on a grid pane
     *
     * @throws Exception
     */
    private void sceneInitialization() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLFiles/ReturnForm.fxml"));
        loader.setController(this);
        AnchorPane root = loader.load();
        this.scene = new Scene(root, 1000, 700);

        documentListView = (ListView<Document>) scene.lookup("#documentListView");
        userListView = (ListView<UserCard>) scene.lookup("#userListView");
        documentInfoPane = (GridPane) scene.lookup("#documentInfoPane");
        titleLbl = (Label) scene.lookup("#titleLbl");
        authorsLbl = (Label) scene.lookup("#authorsLbl");
        documentTypeLbl = (Label) scene.lookup("#documentTypeLbl");
        priceLbl = (Label) scene.lookup("#priceLbl");
        keywordsLbl = (Label) scene.lookup("#keywordsLbl");

        additionLbl1 = (Label) scene.lookup("#additionLbl1");
        additionLbl2 = (Label) scene.lookup("#additionLbl2");
        additionLbl3 = (Label) scene.lookup("#additionLbl3");

        labelAddition1 = (Label) scene.lookup("#labelAddition1");
        labelAddition2 = (Label) scene.lookup("#labelAddition2");
        labelAddition3 = (Label) scene.lookup("#labelAddition3");

        returnButton = (Button) scene.lookup("#returnButton");
        backButt = (Button) scene.lookup("backButt");

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
    /**
     * Method allows to choose the document from the list
     * passes the chosen document info into scene
     */
    public void selectDocument() {
        //get selected element
        if (documentListView.getSelectionModel().getSelectedIndex() > -1) {
            if (openDocumentID == -1) {
                documentInfoPane.setVisible(true);//If no document was opened
            }
        }

        Document chosenDocument = selectDocument(documentListView.getSelectionModel().getSelectedIndex()); //chosen document

        ArrayList<UserCard> userCardsWithCopy = new ArrayList<>();

        for (int i = 0; i < chosenDocument.takenCopies.size(); i++) {
            userCardsWithCopy.add(chosenDocument.takenCopies.get(i).getCheckoutByUser());
        }

        userListView.setItems(FXCollections.observableArrayList(userCardsWithCopy));
        userListView.setCellFactory(new Callback<ListView<UserCard>, ListCell<UserCard>>() {
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
        //Set document info
        titleLbl.setText(chosenDocument.title);
        StringBuilder stringBuilder = new StringBuilder();
        for (String p : chosenDocument.authors) {
            stringBuilder.append(p);
            stringBuilder.append(", ");
        }
        authorsLbl.setText(stringBuilder.toString());

        //setting for keywords label
        documentTypeLbl.setText(chosenDocument.getDocType());
        priceLbl.setText(String.valueOf(chosenDocument.price));
        StringBuilder stringBuilderKeywords = new StringBuilder();
        for (String s : chosenDocument.keywords) {
            stringBuilderKeywords.append(s);
            stringBuilderKeywords.append(", ");
        }
        keywordsLbl.setText(stringBuilderKeywords.toString());

        //setting for additional document info
        if (chosenDocument.getClass().equals(Book.class)) {
            labelAddition1.setText("Publisher: ");
            additionLbl1.setText(((Book) chosenDocument).publisher);
            labelAddition2.setText("Publication Year: ");
            additionLbl2.setText(String.valueOf(((Book) chosenDocument).year));
            if (((Book) chosenDocument).isBestseller) labelAddition3.setText("Bestseller");
        } else if (chosenDocument.getClass().equals(JournalArticle.class)) {
            labelAddition1.setText("Journal: ");
            additionLbl1.setText(((JournalArticle) chosenDocument).journalName);
            labelAddition2.setText("Editor: ");
            additionLbl2.setText(String.valueOf(((JournalArticle) chosenDocument).editor));
            labelAddition3.setText("Publication Date: ");
            additionLbl3.setText(String.valueOf(((JournalArticle) chosenDocument).publicationDate));
        } else {
            labelAddition1.setText("");
            labelAddition2.setText("");
            labelAddition3.setText("");
            additionLbl1.setText("");
            additionLbl2.setText("");
            additionLbl3.setText("");
        }

        //return permission button
        if (session.getUser().isHasCheckOutPerm()) {
            //Check number of copies and output it or number of requests
            boolean flag = true;
            for (Copy copy : session.userCard.checkedOutCopies) {
                if (copy.getDocumentID() == databaseManager.getDocuments(databaseManager.getDocumentsID()[openDocumentID]).getID()) {
                    flag = false;
                    break;
                }
            }
        } else returnButton.setVisible(false);

    }

    /**
     * overload for the selectDocument method
     *
     * @param id - id of the chosen document
     * @return
     */
    public Document selectDocument(int id) {
        openDocumentID = id;
        return databaseManager.getDocuments(databaseManager.getDocumentsID()[openDocumentID]);
    }

    /**
     * Method for Back Button
     * allow to go to the previous form
     *
     * @throws Exception
     */
    @FXML
    public void back() throws Exception {
        MainForm mainForm = new MainForm();
        mainForm.startForm(stage, session, databaseManager, actionManager);
    }

    /**
     * Method for Return Button
     * Allows librarian return patron's book
     * removes a copy from checked out copies array list
     *
     * void
     */
    @FXML
    public void returnBtn() {
        if (openDocumentID > -1) {
            ArrayList<UserCard> userCardsWithCopy = new ArrayList<>();
            Document document = databaseManager.getDocuments(databaseManager.getDocumentsID()[openDocumentID]);
            for (int i = 0; i < document.takenCopies.size(); i++) {
                userCardsWithCopy.add(document.takenCopies.get(i).getCheckoutByUser());
            }
            UserCard userCard = userCardsWithCopy.get(userListView.getSelectionModel().getSelectedIndex());
            for (int i = 0; i < document.takenCopies.size(); i++) {
                if (document.takenCopies.get(i).getCheckoutByUser().getId() == userCard.getId()) {
                    returnCopy(document.takenCopies.get(i), databaseManager.getUserCard(userCard.getId()));
                }
            }
        }

    }

    public void returnCopy(Copy copy, UserCard userCard){
        actionManager.actionNotes.add(new ActionNote(userCard, session.day, session.month, ActionNote.RETURN_DOCUMENT_ACTION_ID,
                databaseManager.getDocuments(copy.getDocumentID())));
        databaseManager.update();
        databaseManager.getUserCard(userCard.getId()).checkedOutCopies.remove(copy);
        databaseManager.getDocuments(databaseManager.getDocuments(copy.getDocumentID()).getID()).returnCopy(copy);
        databaseManager.rebalanceForReferenceType(databaseManager.getDocuments(copy.getID()));
        databaseManager.saveDocuments( databaseManager.getDocuments(copy.getID()));
        databaseManager.saveUserCard(databaseManager.getUserCard(userCard.getId()));
        this.autobooking(databaseManager.getDocuments(copy.getDocumentID()));
    }

    /**
     * Set new session to the form
     */
    public void setSession(Session session) {
        this.session = session;
    }

    public void setDatabaseManager(DatabaseManager databaseManager){
        this.databaseManager = databaseManager;
    }

    public void renew(UserCard userCard, Copy copy){
        if(!copy.hasRenewed || userCard.userType.isHasMultiRenewPerm()){
            copy.checkOutDay = session.day;
            copy.checkOutMonth = session.month;
            copy.hasRenewed = true;
            actionManager.actionNotes.add(new ActionNote(session.userCard, session.day, session.month, ActionNote.RENEW_DOCUMENT_ACTION_ID,
                    databaseManager.getDocuments(copy.getDocumentID())));
            databaseManager.update();
            databaseManager.saveDocuments(databaseManager.getDocuments(copy.getDocumentID()));
            databaseManager.saveUserCard(userCard);
        }
    }

    /**
     * renews the document for chosen user
     * Work order:
     * 1) returns the book
     * 2) checks out book for new date
     * Allows to prolong due date
     */
    @FXML
    public void renewBtn() {
        if (openDocumentID > -1) {
            ArrayList<UserCard> userCardsWithCopy = new ArrayList<>();
            Document document = databaseManager.getDocuments(databaseManager.getDocumentsID()[openDocumentID]);
            for (int i = 0; i < document.takenCopies.size(); i++) {
                userCardsWithCopy.add(document.takenCopies.get(i).getCheckoutByUser());
            }
            UserCard userCard = userCardsWithCopy.get(userListView.getSelectionModel().getSelectedIndex());
            for (int i = 0; i < document.takenCopies.size(); i++) {
                if (document.takenCopies.get(i).getCheckoutByUser().getId() == userCard.getId()) {
                    renew(userCard, document.takenCopies.get(i));

                }
            }
        }
    }

    /**
     * calling a doc back
     */

    public void outstandingRequest(Document doc){

        UserCard[] users = new UserCard[0];
        users = doc.requestedBy.toArray(users);
        for(int i = 0; i < users.length; i++){
            users[i].notifications.add(new Notification(Notification.OUTDATNDING_REQUEST_NOTIFICATION_FOR_PQ, doc.getID()));
            databaseManager.saveUserCard(users[i]);
            actionManager.actionNotes.add(new ActionNote(session.userCard, session.day, session.month, ActionNote.NOTIFY_REMOVED_FROM_WAITING_LIST_ACTION_ID, doc));
            databaseManager.update();
        }
        UserCard[] userCO = new UserCard[0];
        for (int i = 0; i <doc.takenCopies.size(); i++) {
            userCO[i]=doc.takenCopies.get(i).getCheckoutByUser();
        }
        for (int i = 0; i <userCO.length ; i++) {
            userCO[i].notifications.add(new Notification(Notification.OUTDATNDING_REQUEST_NOTIFICATION_FOR_CHECKED_OUT_US, doc.getID()));
            databaseManager.saveUserCard(userCO[i]);
            actionManager.actionNotes.add(new ActionNote(session.userCard, session.day, session.month, ActionNote.NOTIFY_TO_RETURN_ACTION_ID, doc));
            databaseManager.update();
        }
        for(int i = 0; i < doc.takenCopies.size(); i++){
            doc.takenCopies.get(i).hasRenewed = true;
        }
        doc.deletePQ();
        databaseManager.saveDocuments(doc);
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
                databaseManager.saveDocuments(document);
                databaseManager.saveUserCard(userCards[ind]);
                databaseManager.load();
            }
        }
        document.requestedBy.remove(userCards[ind]);
    }
}