package forms;

import core.ActionManager;
import documents.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import storage.DatabaseManager;
import storage.Filter;
import users.userTypes.Guest;
import users.Session;

import javax.print.Doc;
import java.util.ArrayList;
import java.util.Objects;

public class MainForm {

    private Stage stage;
    private Scene scene;
    private Session session;
    private DatabaseManager databaseManager;
    private ActionManager actionManager;

    private int openDocumentID = -1;

    @FXML
    private GridPane documentInfoPane;

    @FXML
    private ListView<Document> documentListView;

    @FXML
    private ComboBox<String> documentSearchTypeBox;
    @FXML
    private TextField documentSearchKeywordsTxt;
    @FXML
    private TextField documentSearchTitleTxt;
    @FXML
    private TextField documentSearchAuthorsTxt;
    @FXML
    private TextField documentSearchMinPriceTxt;
    @FXML
    private TextField documentSearchMaxPriceTxt;
    @FXML
    private TextField documentSearchPublicationMonthTxt;
    @FXML
    private TextField documentSearchPublicationYearTxt;
    @FXML
    private TextField documentSearchPublisherTxt;
    @FXML
    private TextField documentSearchEditionTxt;
    @FXML
    private TextField documentSearchJournalNameTxt;
    @FXML
    private TextField documentSearchEditorTxt;
    @FXML
    private CheckBox documentSearchIsBestsellerCheck;
    @FXML
    private CheckBox documentSearchIsAvailableCheck;

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
    private Label copiesLbl;
    @FXML
    private Label labelCopies;

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
    private Button returnBtn;
    @FXML
    private Button editBtn;
    @FXML
    private Button infoBtn;
    @FXML
    private Button manageBtn;

    @FXML
    private static Button checkOutBtn;
    @FXML
    private static Button requestBtn;
    @FXML
    private static Button bookBtn;

    @FXML
    private Button logOutBtn;

    /**
     * Initialization and run new scene on the primary stage
     *
     * @param primaryStage   != null;
     * @param currentSession != null
     */
    public void startForm(Stage primaryStage, Session currentSession, DatabaseManager databaseManager, ActionManager actionManager) throws Exception {
        this.session = currentSession;
        this.stage = primaryStage;
        this.databaseManager = databaseManager;
        this.actionManager = actionManager;
        updateSession();
        sceneInitialization();
        stage.setScene(scene);
        stage.show();
        if (session.userCard.notifications.size() > 0) showNotification();
    }


    //TODO move to action manager

    /**
     * Check out the document
     *
     * @return true if the document has checked out
     */
    public boolean checkOut(Document document) {
        boolean flag = isAvailableForUser(document);
        if (!document.isReference() && document.getNumberOfAvailableCopies() > 0 && flag) {
            document.takeCopy(session.userCard, session);
            databaseManager.saveUserCard(session.userCard);
            databaseManager.saveDocuments(document);
            //databaseManager.load();
            updateSession();
            return true;
        }
        return false;
    }

    //TODO move to action manager
    //TODO add doc
    public boolean book(Document document) {
        boolean flag = isAvailableForUser(document);

        if (!document.isReference() && document.getNumberOfAvailableCopies() > 0 && flag) {
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


    //TODO move to action manager

    /**
     * Request the document
     *
     * @return true if the document has requested
     */
    public boolean request(Document document) {
        boolean flag = isAvailableForUser(document);

        if (!document.isReference() && document.getNumberOfAvailableCopies() == 0 && flag) {
            document.putInPQ(session.userCard);
            databaseManager.saveDocuments(document);
            databaseManager.saveUserCard(session.userCard);
            updateSession();
            return true;
        }
        return false;
    }

    //TODO move to action manager
    //TODO add doc
    public Document selectDocument(int id) {
        openDocumentID = id;
        return databaseManager.getDocuments(openDocumentID);
    }

    //TODO move to action manager
    //TODO add doc
    private boolean isAvailableForUser(Document document) {
        for (int i = 0; i < session.userCard.checkedOutCopies.size(); i++) {
            if (session.userCard.checkedOutCopies.get(i).getDocumentID() == openDocumentID)
                return false;
        }

        for (int i = 0; i < document.bookedCopies.size(); i++) {
            if (document.bookedCopies.get(i).getCheckoutByUser().getId() == session.userCard.getId())
                return false;
        }

        for (int i = 0; i < document.requestedBy.size(); i++) {
            if (document.requestedBy.contains(databaseManager.getUserCard(session.userCard.getId())))
                return false;
        }

        return true;
    }

    //TODO move to action manager

    /**
     * Set new database manager to the form
     */
    public void setDatabaseManager(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    //TODO move to action manager

    /**
     * Set new session to the form
     */
    public void setSession(Session session) {
        this.session = session;
    }

    //TODO: add doc
    public void setActionManager(ActionManager actionManager) {
        this.actionManager = actionManager;
    }

    /**
     * Initialization scene
     * All elements will be initialized
     */
    private void sceneInitialization() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLFiles/MainForm.fxml"));
        loader.setController(this);
        GridPane root = loader.load();
        this.scene = new Scene(root, 1000, 700);

        elementsInitialization();
        loadSearchPane();
        loadHighPermissionInterface();
        updateDocumentListView();
    }

    /**
     * Initialization scenes' elements
     * All elements will be initialized
     */
    private void elementsInitialization() {
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

        logOutBtn = (Button) scene.lookup("#logOutButton");

        documentSearchTypeBox = (ComboBox<String>) scene.lookup("#documentSearchTypeComboBox");
        documentSearchKeywordsTxt = (TextField) scene.lookup("#documentSearchKeywordsTextField");
        documentSearchTitleTxt = (TextField) scene.lookup("#documentSearchTitleTextField");
        documentSearchAuthorsTxt = (TextField) scene.lookup("#documentSearchAuthorsTextField");
        documentSearchMinPriceTxt = (TextField) scene.lookup("#documentSearchMinPriceTextField");
        documentSearchMinPriceTxt.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,7}"))
                    documentSearchMinPriceTxt.setText(oldValue);
            }
        });
        documentSearchMaxPriceTxt = (TextField) scene.lookup("#documentSearchMaxPriceTextField");
        documentSearchMaxPriceTxt.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,7}"))
                    documentSearchMaxPriceTxt.setText(oldValue);
            }
        });
        documentSearchPublicationMonthTxt = (TextField) scene.lookup("#documentSearchPublicationMonthTextField");
        documentSearchPublicationYearTxt = (TextField) scene.lookup("#documentSearchPublicationYearTextField");
        documentSearchPublicationYearTxt.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,4}"))
                    documentSearchPublicationYearTxt.setText(oldValue);
            }
        });
        documentSearchPublisherTxt = (TextField) scene.lookup("#documentSearchPublisherTextField");
        documentSearchEditionTxt = (TextField) scene.lookup("#documentSearchEditionTextField");
        documentSearchEditionTxt.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,2}"))
                    documentSearchEditionTxt.setText(oldValue);
            }
        });
        documentSearchJournalNameTxt = (TextField) scene.lookup("#documentSearchJournalNameTextField");
        documentSearchEditorTxt = (TextField) scene.lookup("#documentSearchEditorTextField");
        documentSearchIsBestsellerCheck = (CheckBox) scene.lookup("#documentSearchIsBestsellerCheckBox");
        documentSearchIsAvailableCheck = (CheckBox) scene.lookup("#documentSearchIsAvailableCheckBox");

    }

    //TODO: update because of adding new permissions

    /**
     * Load special permission buttons' state
     */
    private void loadHighPermissionInterface() {
        boolean b = session.getUser().isHasModifyPerm();
        boolean b1 = session.getUser().isHasDeletePerm();
        boolean b2 = session.getUser().isHasAddPerm();
        boolean b3 = session.getUser().isHasEditingLibrarianPerm();

        editBtn.setVisible(session.getUser().isHasEditPerm() || session.getUser().isHasModifyPerm() || session.getUser().isHasDeletePerm() || session.getUser().isHasAddPerm() ||
                session.getUser().isHasEditingLibrarianPerm());
        returnBtn.setVisible(session.getUser().isHasReturnPerm());
        infoBtn.setVisible(session.getUser().isHasCheckUserInfoPerm());
        manageBtn.setVisible(session.getUser().isHasCheckUserInfoPerm());
    }

    //TODO: add doc
    //TODO: move list of types to document
    private void loadSearchPane() {
        ArrayList<String> documentTypes = new ArrayList<>();
        documentTypes.add("");
        documentTypes.add(Book.class.getName().replace("documents.", ""));
        documentTypes.add(AVMaterial.class.getName().replace("documents.", ""));
        documentTypes.add(JournalArticle.class.getName().replace("documents.", ""));
        documentSearchTypeBox.setItems(FXCollections.observableArrayList(documentTypes));
        documentSearchTypeBox.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> param) {
                return new ListCell<String>() {
                    @Override
                    protected void updateItem(String type, boolean flag) {
                        super.updateItem(type, flag);
                        if (type != null)
                            setText(type);
                    }
                };
            }
        });
        documentSearchTypeBox.getSelectionModel().selectFirst();
    }

    /**
     * Load buttons' state of document info panel when state of document is updated
     */
    private void loadAvailableAction() {
        checkOutBtn.setVisible(false);
        bookBtn.setVisible(false);
        requestBtn.setVisible(false);

        if (session.getUser().isHasUserPerm()) {
            boolean flag = isAvailableForUser(databaseManager.getDocuments(openDocumentID));

            if (flag) {
                if (databaseManager.getDocuments(openDocumentID).availableCopies.size() > 0) {
                    checkOutBtn.setVisible(session.getUser().isHasCheckOutPerm());
                    bookBtn.setVisible(!session.getUser().isHasCheckOutPerm());
                } else requestBtn.setVisible(true);
            }
        }
    }

    //TODO: add java doc
    private void updateDocumentListView() {
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

    //TODO: move to Action Manager

    /**
     * Update the user card for current session
     */
    public void updateSession() {
        if (!Objects.equals(session.getUser().getClass(), Guest.class)) {
            session.userCard = databaseManager.getUserCard(session.userCard.getId());
        }
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

    //TODO: add java doc
    private void loadInfoOnPanel(Document chosenDocument) {
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
        } else copiesLbl.setText(String.valueOf(chosenDocument.getNumberOfAvailableCopies()));


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

    //TODO: reduce java doc

    /**
     * Click on check out button event
     * Check out or request(temp) document
     */
    @FXML
    public void clickOnRequestBtn() {
        request(databaseManager.getDocuments(openDocumentID));
        loadAvailableAction();
        copiesLbl.setText(String.valueOf(databaseManager.getDocuments(openDocumentID).getNumberOfAvailableCopies()));
    }

    @FXML
    public void clickOnCheckOutBtn() {
        checkOut(databaseManager.getDocuments(openDocumentID));
        loadAvailableAction();
        copiesLbl.setText(String.valueOf(databaseManager.getDocuments(openDocumentID).getNumberOfAvailableCopies()));
    }

    @FXML
    public void clickOnBookBtn() {
        book(databaseManager.getDocuments(openDocumentID));
        loadAvailableAction();
        copiesLbl.setText(String.valueOf(databaseManager.getDocuments(openDocumentID).getNumberOfAvailableCopies()));

    }

    /**
     * Click on return button event.
     * Open Return Form
     */
    @FXML
    public void clickOnReturnBtn() throws Exception {
        ReturnForm returnForm = new ReturnForm();
        returnForm.startForm(stage, session, databaseManager, actionManager);
    }

    /**
     * Click on edit button event.
     * Open Edit Form
     */
    @FXML
    public void clickOnEditBtn() throws Exception {
        EditForm editForm = new EditForm();
        editForm.startForm(stage, session, databaseManager, actionManager);
    }

    /**
     * Click on user info button event.
     * Open UserInfo Form
     */
    @FXML
    public void clickOnUserInfoBtn() throws Exception {
        UserInfoForm userInfoForm = new UserInfoForm();
        userInfoForm.startForm(stage, session, databaseManager, actionManager);
    }

    /**
     * Click on log button event.
     * Open Log Form
     */
    @FXML
    public void clickOnLogBtn() throws Exception {
        LogForm logForm = new LogForm();
        logForm.startForm(stage, session, databaseManager, actionManager);
    }

    /**
     * Click on requests button event.
     * Open Booking Requests Form
     */
    @FXML
    public void clickOnManageBtn() throws Exception {
        ManageForm manageForm = new ManageForm();
        manageForm.startForm(stage, session, databaseManager, actionManager);
    }

    @FXML
    public void clickOnFilterBtn() {
        Filter filter = new Filter();
        if (documentSearchTitleTxt.getText().replace(" ", "").length() > 0) {
            filter.title = documentSearchTitleTxt.getText().replace(" ", "");
        }
        if (documentSearchKeywordsTxt.getText().replace(" ", "").length() > 0) {
            String[] keywords = documentSearchKeywordsTxt.getText().split("[, ]+");
            for (String keyword : keywords) {
                filter.keywords.add(keyword.toLowerCase());
            }
        }

        if (documentSearchAuthorsTxt.getText().replace(" ", "").length() > 0) {
            String[] authors = documentSearchKeywordsTxt.getText().split("[, ]+");
            for (String author : authors) {
                filter.authors.add(author.toLowerCase());
            }
        }

        if (documentSearchMinPriceTxt.getText().replace(" ", "").length() > 0) {
            filter.minPrice = Integer.parseInt(documentSearchMinPriceTxt.getText());
        }
        if (documentSearchMaxPriceTxt.getText().replace(" ", "").length() > 0) {
            filter.maxPrice = Integer.parseInt(documentSearchMaxPriceTxt.getText());
        }
        if (documentSearchIsAvailableCheck.isSelected()) {
            filter.isAvailable = true;
        }
        int i = documentSearchTypeBox.getSelectionModel().getSelectedIndex();
        if (i == 0 || i == 1) {
            if (i == 1) filter.documentType = "book";
            if (documentSearchIsBestsellerCheck.isSelected()) {
                filter.isBestseller = true;
            }
            if (documentSearchPublicationYearTxt.getText().replace(" ", "").length() > 0) {
                filter.publicationYear = Integer.parseInt(documentSearchPublicationYearTxt.getText());
            }
            if (documentSearchEditionTxt.getText().replace(" ", "").length() > 0) {
                filter.edition = documentSearchEditionTxt.getText();
            }
            if (documentSearchPublisherTxt.getText().replace(" ", "").length() > 0) {
                filter.publisher = documentSearchPublisherTxt.getText().toLowerCase();
            }
        }
        if (i == 2) filter.documentType = "avmaterial";
        if (i == 0 || i == 3) {
            if (i == 3) filter.documentType = "journalarticle";
            if (documentSearchPublicationYearTxt.getText().replace(" ", "").length() > 0) {
                filter.publicationYear = Integer.parseInt(documentSearchPublicationYearTxt.getText());
            }

            if (documentSearchPublicationMonthTxt.getText().replace(" ", "").length() > 0) {
                filter.publicationMonth = documentSearchPublicationMonthTxt.getText().toLowerCase();
            }

            if (documentSearchEditorTxt.getText().replace(" ", "").length() > 0) {
                filter.edition = documentSearchEditorTxt.getText().toLowerCase();

                if (documentSearchEditorTxt.getText().replace(" ", "").length() > 0) {
                    filter.editor = documentSearchEditorTxt.getText().toLowerCase();

                }
                if (documentSearchPublisherTxt.getText().replace(" ", "").length() > 0) {
                    filter.publisher = documentSearchPublisherTxt.getText().toLowerCase();
                }
                if (documentSearchJournalNameTxt.getText().replace(" ", "").length() > 0) {
                    filter.journalName = documentSearchJournalNameTxt.getText().toLowerCase();
                }
            }

            ArrayList<Document> documents = actionManager.filter(filter);
            documentListView.setItems(FXCollections.observableArrayList(documents));
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
    }

    //TODO: add java doc
    //TODO: make method in action manager that return the first notification and remove it after
    Label notificationLbl;
    Button notificationBtn;

    public void showNotification() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLFiles/Notification.fxml"));
            loader.setController(this);
            GridPane layout = loader.load();
            Scene secondScene = new Scene(layout, 300, 100);
            Stage notificationStage = new Stage();
            notificationLbl = (Label) secondScene.lookup("#notificationLabel");
            notificationBtn = (Button) secondScene.lookup("#nextButton");

            int lastInd = session.userCard.notifications.size() - 1;
            if (lastInd <= 0) notificationBtn.setVisible(false);
            notificationLbl.setText(session.userCard.notifications.get(lastInd).getMessage(databaseManager));
            session.userCard.notifications.remove(lastInd);

            databaseManager.saveUserCard(session.userCard);

            notificationStage.setTitle("Notification");
            notificationStage.setScene(secondScene);
            notificationStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clickOnNextNotificationBtn() {
        databaseManager.saveUserCard(session.userCard);
        updateSession();
        if (session.userCard.notifications.size() > 0) {
            int lastInd = session.userCard.notifications.size() - 1;
            notificationLbl.setText(session.userCard.notifications.get(lastInd).getMessage(databaseManager));
            session.userCard.notifications.remove(session.userCard.notifications.size() - 1);
        }
        if (session.userCard.notifications.size() <= 0) notificationBtn.setVisible(false);
    }

    //TODO: add doc
    //TODO: move list of types in document
    @FXML
    public void selectSearchTypeComboBox() {

        switch (documentSearchTypeBox.getSelectionModel().getSelectedIndex()) {
            case 0:
                documentSearchPublicationYearTxt.setVisible(true);
                documentSearchPublicationMonthTxt.setVisible(true);
                documentSearchPublisherTxt.setVisible(true);
                documentSearchEditionTxt.setVisible(true);
                documentSearchJournalNameTxt.setVisible(true);
                documentSearchEditorTxt.setVisible(true);
                documentSearchIsBestsellerCheck.setVisible(true);
                break;
            case 1:
                documentSearchPublicationYearTxt.setVisible(true);
                documentSearchPublicationMonthTxt.setVisible(false);
                documentSearchPublisherTxt.setVisible(true);
                documentSearchEditionTxt.setVisible(true);
                documentSearchJournalNameTxt.setVisible(false);
                documentSearchEditorTxt.setVisible(false);
                documentSearchIsBestsellerCheck.setVisible(true);
                break;
            case 2:
                documentSearchPublicationYearTxt.setVisible(false);
                documentSearchPublicationMonthTxt.setVisible(false);
                documentSearchPublisherTxt.setVisible(false);
                documentSearchEditionTxt.setVisible(false);
                documentSearchJournalNameTxt.setVisible(false);
                documentSearchEditorTxt.setVisible(false);
                documentSearchIsBestsellerCheck.setVisible(false);
                break;
            case 3:
                documentSearchPublicationYearTxt.setVisible(true);
                documentSearchPublicationMonthTxt.setVisible(true);
                documentSearchPublisherTxt.setVisible(false);
                documentSearchEditionTxt.setVisible(false);
                documentSearchJournalNameTxt.setVisible(true);
                documentSearchEditorTxt.setVisible(true);
                documentSearchIsBestsellerCheck.setVisible(false);
                break;
        }
    }

    /**
     * Click on button "logOut" event
     * button for coming back to the AuthorizationForm
     *
     * @throws Exception
     */
    @FXML
    public void logOut() throws Exception {
        AuthorizationForm mainForm = new AuthorizationForm();
        mainForm.startForm(stage);
    }
 }