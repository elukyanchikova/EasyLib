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

import java.util.ArrayList;

/**
 * Return form allows librarian to see the list of documents and users that have checked out it
 * also allow to return the book for any users
 */
public class ManageForm {

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
    public void startForm(Stage primaryStage, Session currentSession, DatabaseManager databaseManager) throws Exception {
        this.session = currentSession;
        this.stage = primaryStage;
        this.databaseManager = databaseManager;
        sceneInitialization();
        stage.setScene(scene);
        stage.show();
    }

    private void sceneInitialization() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLFiles/ManagementForm.fxml"));
        loader.setController(this);
        AnchorPane root = loader.load();
        this.scene = new Scene(root, 1000, 700);

        documentListView = (ListView<Document>) scene.lookup("#documentListView");
        userListView = (ListView<UserCard>) scene.lookup("#userListView");

        // requestedByListView = (ListView<UserCard>) scene.lookup("#requestedByListView");

        documentInfoPane = (GridPane) scene.lookup("#documentInfoPane");
        titleLbl = (Label) scene.lookup("#titleLbl");
        authorsLbl = (Label) scene.lookup("#authorsLbl");
        documentTypeLbl = (Label) scene.lookup("#documentTypeLbl");
        priceLbl = (Label) scene.lookup("#priceLbl");
        keywordsLbl = (Label) scene.lookup("#keywordsLbl");

        outstandingRequestBtn = (Button) scene.lookup("#outstandingRequestBtn");

        additionLbl1 = (Label) scene.lookup("#additionLbl1");
        additionLbl2 = (Label) scene.lookup("#additionLbl2");
        additionLbl3 = (Label) scene.lookup("#additionLbl3");

        labelAddition1 = (Label) scene.lookup("#labelAddition1");
        labelAddition2 = (Label) scene.lookup("#labelAddition2");
        labelAddition3 = (Label) scene.lookup("#labelAddition3");

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


        userListView.setItems(FXCollections.observableArrayList(databaseManager.getAllUsers()));
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
        });

    }

}
