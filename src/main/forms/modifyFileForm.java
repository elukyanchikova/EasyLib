package forms;

import documents.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import users.Session;

import java.util.ArrayList;
import java.util.Arrays;

public class modifyFileForm {
    private Stage stage;
    private Scene scene;
    private Session session;

    ArrayList<Document> documents = new ArrayList<>();
    private int openDocumentID = -1;

    @FXML
    private GridPane documentInfoPane;

    @FXML
    private ListView<Document> documentListView;

    @FXML
    private Button saveBtn;
    @FXML
    private Button deleteFileBtn;
    @FXML
    private  Button backBtn;

    @FXML
    private Label labelTitle;
    @FXML
    private Label labelAuthors;
    @FXML
    private Label labelPrice;
    @FXML
    private Label labelPublisher;
    @FXML
    private Label labelYear;
    @FXML
    private Label labelJournal;
    @FXML
    private Label labelEditor;

    @FXML
    private TextField titleTextField;
    @FXML
    private TextField authorsTextField;
    @FXML
    private TextField keywordsTextField;
    @FXML
    private TextField priceTextField;
    @FXML
    private TextField publisherTextField;
    @FXML
    private TextField yearTextField;
    @FXML
    private TextField journalNameTextField;
    @FXML
    private TextField editorNameTextField;


    /**
     * Initialization and run new scene on the primary stage
     */
    void startForm(Stage primaryStage, Session currentSession) throws Exception {
        documents = Storage.getDocuments();
        this.session = currentSession;
        this.stage = primaryStage;
        sceneInitialization();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Initialization scene and scene's elements
     */
    private void sceneInitialization() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("modifyFileForm.fxml"));
        loader.setController(this);
        GridPane root = loader.load();
        this.scene = new Scene(root, 700, 700);

        deleteFileBtn = (Button) scene.lookup("#deleteFileBtn");
        saveBtn = (Button) scene.lookup("#saveBtn");
        backBtn = (Button) scene.lookup("#backBtn");

        labelTitle = (Label) scene.lookup("#labelTitle");
        labelAuthors = (Label) scene.lookup("#labelAuthors");
        labelPrice = (Label) scene.lookup("#labelPrice");
        labelPublisher = (Label) scene.lookup("#labelPublisher");
        labelYear = (Label) scene.lookup("#labelYear");
        labelJournal = (Label) scene.lookup("#labelJournal");
        labelEditor = (Label) scene.lookup("#labelEditor");

        titleTextField = (TextField) scene.lookup("#titleField");
        authorsTextField = (TextField) scene.lookup("#authorsField");
        keywordsTextField = (TextField) scene.lookup("#keywordsField");
        priceTextField = (TextField) scene.lookup("#priceField");
        publisherTextField = (TextField) scene.lookup("#publisherField");
        yearTextField = (TextField) scene.lookup("#yearField");
        journalNameTextField = (TextField) scene.lookup("#journalNameField");
        editorNameTextField = (TextField) scene.lookup("#editorNameField");

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

    /**
     * Select element of Document List View Event
     */
    @FXML
    public void selectFile() {
        if(documentListView.getSelectionModel().getSelectedIndex() > -1) {
            if(openDocumentID == -1){
                documentInfoPane.setVisible(true);//If no document was opened
            }
            //Set document info
            Document chosenDocument = selectFile(documentListView.getSelectionModel().getSelectedIndex());


    }}
    public Document selectFile(int id){
        openDocumentID = id;
        return documents.get(openDocumentID);
    }


    @FXML
    public void save() {
        //TODO add connection with database

        if (!titleTextField.getText().isEmpty()) {
            Document currentDoc = documents.get(openDocumentID);
            currentDoc.title = titleTextField.getText();
        }

        if (!authorsTextField.getText().isEmpty()) {
            Document currentDoc = documents.get(openDocumentID);
            ArrayList<String> authors = new ArrayList<String>(Arrays.asList(authorsTextField.getText().toLowerCase().replace(',', ';').split(";")));
            currentDoc.authors = authors;
        }
        if (!keywordsTextField.getText().isEmpty()) {
            Document currentDoc = documents.get(openDocumentID);
            ArrayList<String> keywords = new ArrayList<String>(Arrays.asList(keywordsTextField.getText().toLowerCase().replace(',', ';').split(";")));
            currentDoc.keywords = keywords;
        }
        if (!priceTextField.getText().isEmpty()) {
            Document currentDoc = documents.get(openDocumentID);
            currentDoc.price = Integer.parseInt(priceTextField.getText());
        }

        if (!publisherTextField.getText().isEmpty()) {
            Document currentDoc = documents.get(openDocumentID);
            if (currentDoc.getClass().equals(Book.class)) {
                ((Book) currentDoc).publisher = publisherTextField.getText();
            }
        }

        if (!yearTextField.getText().isEmpty()) {
            Document currentDoc = documents.get(openDocumentID);
            if (currentDoc.getClass().equals(Book.class)) {
                ((Book) currentDoc).year = Integer.parseInt(yearTextField.getText());
            }
            if (currentDoc.getClass().equals(JournalArticle.class)) {
                ((JournalArticle) currentDoc).publicationDate = yearTextField.getText();
            }
        }
        if (!journalNameTextField.getText().isEmpty()) {
            Document currentDoc = documents.get(openDocumentID);
            if (currentDoc.getClass().equals(JournalArticle.class)) {
                ((JournalArticle) currentDoc).journalName = journalNameTextField.getText();
            }
        }
        if (!editorNameTextField.getText().isEmpty()) {
            Document currentDoc = documents.get(openDocumentID);
            if (currentDoc.getClass().equals(JournalArticle.class)) {
                ((JournalArticle) currentDoc).editor = editorNameTextField.getText();
            }
        }
    }

    @FXML
    public void deleteFile() {
        //TODO delete file
        Document currentDoc = documents.get(openDocumentID);
        //removeDocuments(currentDoc);
    }
    @FXML
    public void back(){}
}
