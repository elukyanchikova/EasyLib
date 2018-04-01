package forms;

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
import users.Session;
import users.UserCard;
import users.UserType;

import java.util.ArrayList;

/**
 * Return form allows librarian to see the list of documents and users that have checked out it
 * also allow to return the book for any users
 */
public class ManagementForm {

    private Stage stage;
    private Scene scene;
    private Session session;
    private DatabaseManager databaseManager;

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
    public void startForm(Stage primaryStage, Session currentSession, DatabaseManager databaseManager) throws Exception {
        this.session = currentSession;
        this.stage = primaryStage;
        this.databaseManager = databaseManager;
        sceneInitialization();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Scene initialization method
     * Sets information about chosen book on a labels on a grid pane
     *
     * @throws Exception
     */
    private void sceneInitialization() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLFiles/ManagementForm.fxml"));
        loader.setController(this);
        AnchorPane root = loader.load();
        this.scene = new Scene(root, 1000, 700);

        documentListView = (ListView<Document>) scene.lookup("#documentListView");
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

        /*userListView.setItems(FXCollections.observableArrayList(databaseManager.getAllUsers()));
        userListView.setCellFactory(new Callback<ListView<UserCard>, ListCell<UserCard>>() {
            public ListCell<UserCard> call(ListView<UserCard> userListView) {
                return new ListCell<UserCard>() {
                  @Override
                  protected void updateItem(UserCard userCard, boolean flag){
                      super.updateItem(userCard,flag);
                      if (userCard != null && userCard.checkedOutCopies != null){
                          setText(userCard.name);
                      }
                  }
                };
            }
        });*/

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



        ArrayList<UserCard> userCardsBooked = new ArrayList<>();

        for (int i = 0; i < chosenDocument.bookedCopies.size(); i++) {
            UserCard temp = chosenDocument.bookedCopies.get(i).getCheckoutByUser();
            if (temp != null){
                userCardsBooked.add(temp);
            }
        }

        userListView.setItems(FXCollections.observableArrayList(userCardsBooked));

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
        mainForm.startForm(stage, session, databaseManager);
    }

    /**
     * Method for Return Button
     * Allows librarian return patron's book
     */

    @FXML
    public void acceptBtn() {
        ArrayList<UserCard> userCardsWithCopy = new ArrayList<>();
        Document chosenDocument = selectDocument(documentListView.getSelectionModel().getSelectedIndex());
        ArrayList<UserCard> userCardsBooked = new ArrayList<>();

        for (int i = 0; i < chosenDocument.bookedCopies.size(); i++) {
            UserCard temp = chosenDocument.bookedCopies.get(i).getCheckoutByUser();
            if (temp != null){
                userCardsBooked.add(temp);
            }
        }

        for (int i = 0; i < chosenDocument.getNumberOfAllCopies(); i++) {
            UserCard temp = chosenDocument.bookedCopies.get(i).getCheckoutByUser();
            if (temp == userListView.getSelectionModel().getSelectedItem()){

                boolean flag = true;
                for (Copy copy : userCardsWithCopy.get(userListView.getSelectionModel().getSelectedIndex()).checkedOutCopies) {
                    if (copy.getDocumentID() == databaseManager.getDocuments(databaseManager.getDocumentsID()[openDocumentID]).getID()) {
                        flag = false;
                        break;
                    }
                }

                if (!databaseManager.getDocuments(databaseManager.getDocumentsID()[openDocumentID]).isReference() && databaseManager.getDocuments(databaseManager.getDocumentsID()[openDocumentID]).getNumberOfAvailableCopies() > 0 && flag) {
                    databaseManager.getDocuments(databaseManager.getDocumentsID()[openDocumentID]).takeCopy(userCardsWithCopy.get(userListView.getSelectionModel().getSelectedIndex()), session);
                    databaseManager.saveDocuments(databaseManager.getDocuments(databaseManager.getDocumentsID()[openDocumentID]));
                    databaseManager.saveUserCard(userCardsWithCopy.get(userListView.getSelectionModel().getSelectedIndex()));
                }

                chosenDocument.availableCopies.add(chosenDocument.bookedCopies.get(i));
                chosenDocument.bookedCopies.remove(chosenDocument.bookedCopies.get(i));
            }
        }

    }

    @FXML
    public void rejectBtn() {

        ArrayList<UserCard> userCardsWithCopy = new ArrayList<>();
        Document chosenDocument = selectDocument(documentListView.getSelectionModel().getSelectedIndex());
        ArrayList<Integer> userCardsBooked = new ArrayList<>();

        for (int i = 0; i < chosenDocument.getNumberOfAllCopies(); i++) {
            int temp = chosenDocument.bookedCopies.get(i).getCheckoutByUser().getId();
            userCardsBooked.add(temp);

        }

        for (int i = 0; i < chosenDocument.getNumberOfAllCopies(); i++) {
            int temp = chosenDocument.bookedCopies.get(i).getCheckoutByUser().getId();
            if (temp == userListView.getSelectionModel().getSelectedItem().getId()){
                chosenDocument.availableCopies.add(chosenDocument.bookedCopies.get(i));
                chosenDocument.bookedCopies.remove(chosenDocument.bookedCopies.get(i));
            }
        }


    }



    // calling a doc back
    public void outstandingRequest(Document doc){
        doc.deletePQ();
        databaseManager.saveDocuments(doc);
    }}