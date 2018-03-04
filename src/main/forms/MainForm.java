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
     * @param primaryStage != null;
     * @param currentSession != null
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
     * All elements will be initialized
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
                            setText(document.title);
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
            Document chosenDocument = selectDocument(documentListView.getSelectionModel().getSelectedIndex());
            titleLbl.setText(chosenDocument.title);
            StringBuilder stringBuilder = new StringBuilder();
            for(String p:chosenDocument.authors){
                stringBuilder.append(p);
                stringBuilder.append(", ");
            }
            authorsLbl.setText(stringBuilder.toString());

            documentTypeLbl.setText(chosenDocument.docType);
            priceLbl.setText(String.valueOf(chosenDocument.docType));
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
                else labelAddition3.setText("");
                additionLbl3.setText("");
            }else if(chosenDocument.getClass().equals(JournalArticle.class)){
                labelAddition1.setText("Journal: ");
                additionLbl1.setText(((JournalArticle)chosenDocument).journal);
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
            if(session.getUser().isHasCheckOutPerm()
                    && !(session.userCard.checkedOutDocs.contains(documents.get(openDocumentID)))) {
                //Check number of copies and output it or number of requests
                 if (documents.get(openDocumentID).getNumberOfAvailableCopies() == 0) {
                     requestLbl.setText(String.valueOf(documents.get(openDocumentID).getNumberOfAvailableCopies()));
                    checkoutButton.setVisible(true);
                    if (session.userCard.requestedDocs.contains(documents.get(openDocumentID))) {
                        checkoutButton.setText("Cancel request");
                    } else checkoutButton.setText("Request");
                } else {
                    checkoutButton.setVisible(true);
                    requestLbl.setText(String.valueOf(documents.get(openDocumentID).getNumberOfAvailableCopies()));
                    checkoutButton.setText("Check out");
                }
            }else checkoutButton.setVisible(false);
        }
    }

    /**
     * Click on check out button event.
     * Check out or request(temp) document
     */
    @FXML
    public void checkOut(){
        Document currentDoc = documents.get(openDocumentID);
        if(currentDoc.getNumberOfAvailableCopies() == 0) {
            if (request(currentDoc)) {
                checkoutButton.setText("Request");
            } else {
                checkoutButton.setText("Cancel request");
            }
        }else{
            checkOut(currentDoc);
            requestLbl.setText(String.valueOf(currentDoc.getNumberOfAvailableCopies()));
            if(currentDoc.getNumberOfAvailableCopies() == 0) {
                checkoutButton.setText("Request");
            }
            checkoutButton.setVisible(false);
        }
    }

    public void setSession(Session session){
        this.session = session;
    }

    public Document selectDocument(int id){
        openDocumentID = id;
        return documents.get(openDocumentID);
    }

    public boolean checkOut(Document document){

        if(document.getNumberOfAvailableCopies() > 0 && !session.userCard.checkedOutDocs.contains(document)) {
            document.takeCopy( session.userCard);
            return true;
        }
        return false;

    }

    public boolean request(Document document){

        boolean requested = session.userCard.requestedDocs.contains(document);
        if (requested) {
            document.increaseNumberOfRequest();
            session.userCard.requestedDocs.remove(document);
            return true;
        } else {
            document.increaseNumberOfRequest();
            session.userCard.requestedDocs.add(document);
            return false;
        }
    }
}
