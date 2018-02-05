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
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import users.Session;

import java.util.ArrayList;

public class MainForm {

    private Stage stage;
    private Scene scene;
    private Session session;

    ArrayList<Document> documents = new ArrayList<>();
    private int openDocumentID = -1;

    @FXML private GridPane documentInfoPane;

    @FXML private ListView<Document> documentListView;

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

    @FXML private static Button checkoutButton;

    /**
     * Initialization and run new scene on the primary stage
     */
    public void startForm(Stage primaryStage, Session currentSession) throws Exception{
        this.session = currentSession;
        this.stage = primaryStage;
        documents = Storage.getDocuments();
        sceneInitialization();
        stage.setScene(scene);
        stage.show();
    }

    //TODO refactor after adding storage
    /**
     * Initialization scene and scene's elements
     */
    private void sceneInitialization() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainForm.fxml"));
        loader.setController(this);
        GridPane root = loader.load();
        this.scene = new Scene(root,1000,700);

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

        checkoutButton = (Button) scene.lookup("#checkoutButton");

        if(!session.getUser().isHasCheckOutPerm()) checkoutButton.setVisible(false);

        documentListView.setItems(FXCollections.observableArrayList(documents));
        documentListView.setCellFactory(new Callback<ListView<Document>, ListCell<Document>>(){
            public ListCell<Document> call(ListView<Document> documentListView) {
                return new ListCell<Document>(){
                    @Override
                    protected void updateItem(Document document, boolean flag) {
                        super.updateItem(document, flag);
                        if (document != null) {
                            setText(document.getTitle());
                        }
                    }
                };
            }
        });
    }

    //TODO change process of output

    /**
     * Select element of Document List View Event
     */
    @FXML
    public void selectDocument(){
        //get selected element
        if(documentListView.getSelectionModel().getSelectedIndex() > -1) {
            if(openDocumentID == -1){
                documentInfoPane.setVisible(true);//If no document was opened
            }
            //Set document info
            openDocumentID = documentListView.getSelectionModel().getSelectedIndex();
            Document chosenDocument = documents.get(openDocumentID);
            titleLbl.setText(chosenDocument.getTitle());
            StringBuilder stringBuilder = new StringBuilder();
            for(Person p:chosenDocument.getAuthors()){
                stringBuilder.append(p.fullName);
                stringBuilder.append(", ");
            }
            authorsLbl.setText(stringBuilder.toString());

            documentTypeLbl.setText(chosenDocument.getDocType());
            priceLbl.setText(String.valueOf(chosenDocument.getPrice()));
            StringBuilder stringBuilderKeywords = new StringBuilder();
            for(String s:chosenDocument.getKeywords()){
                stringBuilderKeywords.append(s);
                stringBuilderKeywords.append(", ");
            }
            keywordsLbl.setText(stringBuilderKeywords.toString());
            requestLbl.setText(String.valueOf(chosenDocument.getNumberOfRequests()));

            if(chosenDocument.getClass().equals(Book.class)){
                labelAddition1.setText("Publisher: ");
                additionLbl1.setText(((Book)chosenDocument).getPublisher());
                labelAddition2.setText("Publication Year: ");
                additionLbl2.setText(String.valueOf(((Book)chosenDocument).getYear()));
                if(((Book) chosenDocument).isBestseller()) labelAddition3.setText("Bestseller");
                additionLbl3.setText("");
            }else if(chosenDocument.getClass().equals(JournalArticle.class)){
                labelAddition1.setText("Journal: ");
                additionLbl1.setText(((JournalArticle)chosenDocument).getJournal());
                labelAddition2.setText("Editor: ");
                additionLbl2.setText(String.valueOf(((JournalArticle)chosenDocument).getIssue().editor));
                labelAddition3.setText("Publication Date: ");
                additionLbl3.setText(String.valueOf(((JournalArticle)chosenDocument).getIssue().publicationDate));
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
                if (documents.get(openDocumentID).getNumberOfCopies() == -1) {
                    checkoutButton.setVisible(false);
                    labelRequests.setText("");
                    requestLbl.setText("");
                } else if (documents.get(openDocumentID).getNumberOfCopies() == 0) {
                    requestLbl.setText(String.valueOf(documents.get(openDocumentID).getNumberOfRequests()));
                    checkoutButton.setVisible(true);
                    if (session.userCard.requestedDocs.contains(documents.get(openDocumentID))) {
                        checkoutButton.setText("Cancel request");
                    } else checkoutButton.setText("Request");
                } else {
                    checkoutButton.setVisible(true);
                    labelRequests.setText("Copies: ");
                    requestLbl.setText(String.valueOf(documents.get(openDocumentID).getNumberOfCopies()));
                    checkoutButton.setText("Check out");
                }
            }
        }
    }

    /**
     * Click on check out button event.
     * Check out or request(temp) document
     */
    @FXML
    public void checkOut(){
        Document currentDoc = documents.get(openDocumentID);
        if(currentDoc.getNumberOfCopies() == 0) {
            boolean requested = session.userCard.requestedDocs.contains(currentDoc);
            if (requested) {
                currentDoc.setRequest(currentDoc.getNumberOfRequests() - 1);
                checkoutButton.setText("Request");
                session.userCard.requestedDocs.remove(currentDoc);
            } else {
                currentDoc.setRequest(currentDoc.getNumberOfRequests() + 1);
                checkoutButton.setText("Cancel request");
                session.userCard.requestedDocs.add(currentDoc);
            }
            requestLbl.setText(String.valueOf(currentDoc.getNumberOfRequests()));
        }else{
            currentDoc.setNumberOfCopies(currentDoc.getNumberOfCopies()-1);
            session.userCard.checkedOutDocs.add(new Copy(currentDoc, -1,10));
            requestLbl.setText(String.valueOf(currentDoc.getNumberOfCopies()));
            if(currentDoc.getNumberOfCopies() == 0) {
                labelRequests.setText("Requests: ");
                requestLbl.setText(String.valueOf(currentDoc.getNumberOfRequests()));
                checkoutButton.setText("Request");
            }
        }
    }
}
