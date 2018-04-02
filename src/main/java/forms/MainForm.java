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
import storage.DatabaseManager;
import users.Guest;
import users.Session;

public class MainForm {

    private Stage stage;
    private Scene scene;
    private Session session;
    private DatabaseManager databaseManager;

    private int openDocumentID = -1;

    @FXML private GridPane documentInfoPane;

    @FXML private ListView<Document> documentListView;

    @FXML private Label titleLbl;
    @FXML private Label authorsLbl;
    @FXML private Label documentTypeLbl;
    @FXML private Label priceLbl;
    @FXML private Label keywordsLbl;
    @FXML private Label copiesLbl;
    @FXML private Label labelCopies;

    @FXML private Label notificationLabel;

    @FXML private Label labelAddition1;
    @FXML private Label labelAddition2;
    @FXML private Label labelAddition3;

    @FXML private Label additionLbl1;
    @FXML private Label additionLbl2;
    @FXML private Label additionLbl3;

    @FXML private Button returnBtn;
    @FXML private Button editBtn;
    @FXML private Button infoBtn;
    @FXML private Button manageBtn;

    @FXML private static Button checkOutBtn;
    @FXML private static Button requestBtn;
    @FXML private static Button bookBtn;

    /**
     * Initialization and run new scene on the primary stage
     * @param primaryStage != null;
     * @param currentSession != null
     */
    public void startForm(Stage primaryStage, Session currentSession, DatabaseManager databaseManager) throws Exception{
        this.session = currentSession;
        this.stage = primaryStage;
        this.databaseManager = databaseManager;
        updateSession();
        sceneInitialization();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Check out the document
     * @return true if the document has checked out
     */
    public boolean checkOut(Document document){
        boolean flag = isAvailableForUser(document);
        if(!document.isReference() && document.getNumberOfAvailableCopies() > 0 && flag) {
            document.takeCopy( session.userCard, session);
            databaseManager.saveDocuments(document);
            databaseManager.saveUserCard(session.userCard);
            databaseManager.load();
            updateSession();
            return true;
        }
        return false;
    }

    public boolean book(Document document){
        boolean flag = isAvailableForUser(document);

        if(!document.isReference() && document.getNumberOfAvailableCopies() > 0 && flag) {
            document.bookedCopies.add(document.availableCopies.get(0));
            document.availableCopies.get(0).checkoutBy(session.userCard);
            document.availableCopies.remove(0);
            databaseManager.saveDocuments(document);
            databaseManager.saveUserCard(session.userCard);
            databaseManager.load();
            updateSession();
            return true;
        }
        return false;
    }

    /**
     * Request the document
     * @return true if the document has requested
     */
    public boolean request(Document document){
        boolean flag = isAvailableForUser(document);

        if(!document.isReference() && document.getNumberOfAvailableCopies() == 0 && flag) {
            document.putInPQ(session.userCard);
            databaseManager.saveDocuments(document);
            databaseManager.saveUserCard(session.userCard);
            updateSession();
            return true;
        }
        return false;
    }

    public Document selectDocument(int id){
        openDocumentID = id;
        return databaseManager.getDocuments(openDocumentID);
    }

    private boolean isAvailableForUser(Document document){
        for(int i = 0; i < session.userCard.checkedOutCopies.size(); i++){
            if(session.userCard.checkedOutCopies.get(i).getDocumentID() == openDocumentID)
                return false;
        }

        for(int i = 0; i < document.bookedCopies.size(); i++){
            if(document.bookedCopies.get(i).getCheckoutByUser().getId() == session.userCard.getId())
                return false;
        }

        for(int i = 0; i < document.requestedBy.size(); i++){
            if(document.requestedBy.contains(databaseManager.getUserCard(session.userCard.getId())))
                return false;
        }

        return true;
    }
    /**
     * Set new database manager to the form
     */
    public void setDatabaseManager(DatabaseManager databaseManager){
        this.databaseManager = databaseManager;
    }

    /**
     * Set new session to the form
     */
    public void setSession(Session session){
        this.session = session;
    }

    /**
     * Initialization scene
     * All elements will be initialized
     */
    private void sceneInitialization() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLFiles/MainForm.fxml"));
        loader.setController(this);
        GridPane root = loader.load();
        this.scene = new Scene(root,1000,700);
        elementsInitialization();
        loadHighPermissionInterface();
        updateDocumentListView();
    }

    /**
     * Initialization scenes' elements
     * All elements will be initialized
     */
    private void elementsInitialization(){
        documentListView = (ListView<Document>) scene.lookup("#documentListView");
        documentInfoPane = (GridPane) scene.lookup("#documentInfoPane");
        titleLbl = (Label) scene.lookup("#titleLbl");
        authorsLbl = (Label) scene.lookup("#authorsLbl");
        documentTypeLbl = (Label) scene.lookup("#documentTypeLbl");
        priceLbl = (Label) scene.lookup("#priceLbl");
        keywordsLbl = (Label) scene.lookup("#keywordsLbl");
        copiesLbl = (Label) scene.lookup("#copiesLbl");
        labelCopies = (Label) scene.lookup("#labelCopies");

        additionLbl1 = (Label) scene.lookup("#additionLbl1");
        additionLbl2 = (Label) scene.lookup("#additionLbl2");
        additionLbl3 = (Label) scene.lookup("#additionLbl3");

        labelAddition1 = (Label) scene.lookup("#labelAddition1");
        labelAddition2 = (Label) scene.lookup("#labelAddition2");
        labelAddition3 = (Label) scene.lookup("#labelAddition3");

        returnBtn = (Button) scene.lookup("#returnButton");
        editBtn = (Button) scene.lookup("#editButton");
        infoBtn = (Button) scene.lookup("#userInfoButton");
        manageBtn = (Button) scene.lookup("#manageButton");

        checkOutBtn = (Button) scene.lookup("#checkOutButton");
        requestBtn = (Button) scene.lookup("#requestButton");
        bookBtn = (Button) scene.lookup("#bookButton");

        notificationLabel = (Label) scene.lookup("#notificationLabel");
    }

    /**
     * Load special permission buttons' state
     */
    private void loadHighPermissionInterface(){
        editBtn.setVisible(session.getUser().isHasEditPerm());
        returnBtn.setVisible(session.getUser().isHasReturnPerm());
        infoBtn.setVisible(session.getUser().isHasCheckUserInfoPerm());
        manageBtn.setVisible(session.getUser().isHasCheckUserInfoPerm());
    }

    /**
     * Load buttons' state of document info panel
     */
    private void loadAvailableAction(){
        checkOutBtn.setVisible(false);
        bookBtn.setVisible(false);
        requestBtn.setVisible(false);

        if(session.getUser().isHasUserPerm()) {
            boolean flag = isAvailableForUser(databaseManager.getDocuments(openDocumentID));

            if(flag) {
                if (databaseManager.getDocuments(openDocumentID).availableCopies.size() > 0) {
                    checkOutBtn.setVisible(session.getUser().isHasCheckOutPerm());
                    bookBtn.setVisible(!session.getUser().isHasCheckOutPerm());
                } else requestBtn.setVisible(true);
            }
        }
    }

    private void updateDocumentListView(){
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

    /**
     * Update the user card for current session
     */
    public void updateSession(){
        if(session.getUser().getClass() != Guest.class)
            session.userCard = databaseManager.getUserCard(session.userCard.getId());
    }


    /**
     * Select element of Document List View Event
     */
    @FXML
    public void clickOnDocumentListView() {

        //get selected element
        if (documentListView.getSelectionModel().getSelectedIndex() > -1) {
            //If no document was opened
            if (openDocumentID == -1) {
                documentInfoPane.setVisible(true);
            }

            Document chosenDocument = selectDocument(databaseManager.getDocumentsID()[documentListView.getSelectionModel().getSelectedIndex()]);
            loadInfoOnPanel(chosenDocument);
            loadAvailableAction();
        }
    }

    private void loadInfoOnPanel(Document chosenDocument){
        titleLbl.setText(chosenDocument.title);

        StringBuilder stringBuilder = new StringBuilder();
        for (String p : chosenDocument.authors) {
            stringBuilder.append(p);
            stringBuilder.append(", ");
        }
        authorsLbl.setText(stringBuilder.toString());

        documentTypeLbl.setText(chosenDocument.getDocType());
        priceLbl.setText(String.valueOf(chosenDocument.price));
        StringBuilder stringBuilderKeywords = new StringBuilder();
        for (String s : chosenDocument.keywords) {
            stringBuilderKeywords.append(s);
            stringBuilderKeywords.append(", ");
        }
        keywordsLbl.setText(stringBuilderKeywords.toString());

        if (databaseManager.getDocuments(openDocumentID).isReference()) {

            copiesLbl.setText("");
            labelCopies.setText("Reference book");
        }else copiesLbl.setText(String.valueOf(chosenDocument.getNumberOfAvailableCopies()));


        if (chosenDocument.getClass().equals(Book.class)) {
            labelAddition1.setText("Publisher: ");
            additionLbl1.setText(((Book) chosenDocument).publisher);
            labelAddition2.setText("Publication Year: ");
            additionLbl2.setText(String.valueOf(((Book) chosenDocument).year));
            if (((Book) chosenDocument).isBestseller) labelAddition3.setText("Bestseller");
            else labelAddition3.setText("");
            additionLbl3.setText("");
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
    }

    /**
     * Click on check out button event
     * Check out or request(temp) document
     */
    @FXML
    public void clickOnRequestBtn(){
       request(databaseManager.getDocuments(openDocumentID));
       loadAvailableAction();
       copiesLbl.setText(String.valueOf(databaseManager.getDocuments(openDocumentID).getNumberOfAvailableCopies()));
    }

    @FXML
    public void clickOnCheckOutBtn(){
        checkOut(databaseManager.getDocuments(openDocumentID));
        loadAvailableAction();
        copiesLbl.setText(String.valueOf(databaseManager.getDocuments(openDocumentID).getNumberOfAvailableCopies()));
    }

    @FXML
    public void clickOnBookBtn(){
        book(databaseManager.getDocuments(openDocumentID));
        loadAvailableAction();
        copiesLbl.setText(String.valueOf(databaseManager.getDocuments(openDocumentID).getNumberOfAvailableCopies()));

    }

    /**
     * Click on return button event.
     * Open Return Form
     */
    @FXML
    public void clickOnReturnBtn() throws Exception{
        ReturnForm returnForm = new ReturnForm();
        returnForm.startForm(stage, session, databaseManager);
    }

    /**
     * Click on edit button event.
     * Open Edit Form
     */
    @FXML
    public void clickOnEditBtn() throws Exception{
        EditForm editForm = new EditForm();
        editForm.startForm(stage,session, databaseManager);
    }

    /**
     * Click on user info button event.
     * Open UserInfo Form
     */
    @FXML
    public void clickOnUserInfoBtn() throws Exception{
        UserInfoForm userInfoForm = new UserInfoForm();
        userInfoForm.startForm(stage,session, databaseManager);
    }

    /**
     * Click on requests button event.
     * Open Booking Requests Form
     */
    @FXML
    public void clickOnManageBtn() throws Exception{
        ManageForm manageForm = new ManageForm();
        manageForm.startForm(stage,session, databaseManager);
    }
}
