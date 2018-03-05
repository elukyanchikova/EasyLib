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
import storage.Database;
import users.Session;
import users.UserCard;

import java.util.ArrayList;

public class ReturnForm {

    private Stage stage;
    private Scene scene;
    private Session session;
    private Database database;

    //ArrayList<Document> documents = new ArrayList<>();
    //ArrayList<UserCard> users = new ArrayList<>();
    private int openDocumentID = -1;

    @FXML
    private GridPane documentInfoPane;

    @FXML private ListView<Document> documentListView;
    @FXML private ListView<UserCard> userListView;

    @FXML private Label titleLbl;
    @FXML private Label authorsLbl;
    @FXML private Label documentTypeLbl;
    @FXML private Label priceLbl;
    @FXML private Label keywordsLbl;
    @FXML private Label requestLbl;
    @FXML private Label labelRequests;

    @FXML private Label labelAddition1;
    @FXML private Label labelAddition2;
    @FXML private Label labelAddition3;

    @FXML private Label additionLbl1;
    @FXML private Label additionLbl2;
    @FXML private Label additionLbl3;

    @FXML private static Button returnButton;

    public void startForm(Stage primaryStage, Session currentSession, Database database) throws Exception{
        this.session = currentSession;
        this.stage = primaryStage;
        this.database = database;
        sceneInitialization();
        stage.setScene(scene);
        stage.show();
    }

    private void sceneInitialization() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ReturnForm.fxml"));
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
        requestLbl = (Label) scene.lookup("#requestLbl");
        labelRequests = (Label) scene.lookup("#labelRequests");

        additionLbl1 = (Label) scene.lookup("#additionLbl1");
        additionLbl2 = (Label) scene.lookup("#additionLbl2");
        additionLbl3 = (Label) scene.lookup("#additionLbl3");

        labelAddition1 = (Label) scene.lookup("#labelAddition1");
        labelAddition2 = (Label) scene.lookup("#labelAddition2");
        labelAddition3 = (Label) scene.lookup("#labelAddition3");

        returnButton = (Button) scene.lookup("#returnButton");

        documentListView.setItems(FXCollections.observableArrayList(database.getAllDocuments()));
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

        /*userListView.setItems(FXCollections.observableArrayList(database.getAllUsers()));
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
    public void selectDocument(){
        //get selected element
        if(documentListView.getSelectionModel().getSelectedIndex() > -1) {
            if (openDocumentID == -1) {
                documentInfoPane.setVisible(true);//If no document was opened
            }
        }

            userListView.setItems(FXCollections.observableArrayList(database.getAllUsers()));
            userListView.setCellFactory(new Callback<ListView<UserCard>, ListCell<UserCard>>() {
                public ListCell<UserCard> call(ListView<UserCard> userListView) {
                    return new ListCell<UserCard>() {
                        @Override
                        protected void updateItem(UserCard userCard, boolean flag){
                            super.updateItem(userCard,flag);
                            if (userCard != null && userCard.checkedOutCopies != null){
                                boolean f = false;
                                for(int i = 0; i < userCard.checkedOutCopies.size(); i++){
                                    if(userCard.checkedOutCopies.get(i).getDocumentID() == database.getDocumentsID()[openDocumentID]);
                                }
                                if(f) setText(userCard.name);
                            }
                        }
                    };
                }
            });
            //Set document info
            Document chosenDocument = selectDocument(documentListView.getSelectionModel().getSelectedIndex());
            titleLbl.setText(chosenDocument.title);
            StringBuilder stringBuilder = new StringBuilder();
            for(String p:chosenDocument.authors){
                stringBuilder.append(p);
                stringBuilder.append(", ");
            }
            authorsLbl.setText(stringBuilder.toString());

            documentTypeLbl.setText(chosenDocument.getDocType());
            priceLbl.setText(String.valueOf(chosenDocument.price));
            StringBuilder stringBuilderKeywords = new StringBuilder();
            for(String s:chosenDocument.keywords){
                stringBuilderKeywords.append(s);
                stringBuilderKeywords.append(", ");
            }
            keywordsLbl.setText(stringBuilderKeywords.toString());

            if(chosenDocument.getClass().equals(Book.class)){
                labelAddition1.setText("Publisher: ");
                additionLbl1.setText(((Book)chosenDocument).publisher);
                labelAddition2.setText("Publication Year: ");
                additionLbl2.setText(String.valueOf(((Book)chosenDocument).year));
                if(((Book) chosenDocument).isBestseller) labelAddition3.setText("Bestseller");
            }else if(chosenDocument.getClass().equals(JournalArticle.class)){
                labelAddition1.setText("Journal: ");
                additionLbl1.setText(((JournalArticle)chosenDocument).journalName);
                labelAddition2.setText("Editor: ");
                additionLbl2.setText(String.valueOf(((JournalArticle)chosenDocument).editor));
                labelAddition3.setText("Publication Date: ");
                additionLbl3.setText(String.valueOf(((JournalArticle)chosenDocument).publicationDate));
            }else{
                labelAddition1.setText("");
                labelAddition2.setText("");
                labelAddition3.setText("");
                additionLbl1.setText("");
                additionLbl2.setText("");
                additionLbl3.setText("");
            }
            if(session.getUser().isHasCheckOutPerm()) {
                //Check number of copies and output it or number of requests
                boolean flag = true;
                for (Copy copy : session.userCard.checkedOutCopies) {
                    if (copy.getDocumentID() == database.getDocuments(database.getDocumentsID()[openDocumentID]).getID()) {
                        flag = false;
                        break;
                    }
                }
                if (flag){
                    if (database.getDocuments(database.getDocumentsID()[openDocumentID]).getNumberOfAvailableCopies() == 0) {
                        requestLbl.setText(String.valueOf(database.getDocuments(database.getDocumentsID()[openDocumentID]).getNumberOfAvailableCopies()));
                        returnButton.setVisible(true);
                        if (session.userCard.requestedDocs.contains(database.getDocuments(database.getDocumentsID()[openDocumentID]))) {
                            returnButton.setText("Cancel request");
                        } else returnButton.setText("Request");
                    } else {
                        returnButton.setVisible(true);
                        requestLbl.setText(String.valueOf(database.getDocuments(database.getDocumentsID()[openDocumentID]).getNumberOfAvailableCopies()));
                        returnButton.setText("Check out");
                    }
                }else returnButton.setVisible(false);
            }else returnButton.setVisible(false);

    }

    public Document selectDocument(int id){
        openDocumentID = id;
        return database.getDocuments(database.getDocumentsID()[openDocumentID]);
    }




}
