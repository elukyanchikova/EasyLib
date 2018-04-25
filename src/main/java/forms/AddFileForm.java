package forms;

import core.ActionManager;
import core.ActionNote;
import documents.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import storage.DatabaseManager;
import users.Session;

import java.util.ArrayList;
import java.util.Arrays;

public class AddFileForm {
    private Stage stage;
    private Scene scene;
    private Session session;
    private DatabaseManager databaseManager;
    private ActionManager actionManager;

    @FXML
    private TextField titleTextField;
    @FXML
    private TextField authorsTextField;
    @FXML
    private TextField keywordsTextField;
    @FXML
    private TextField docTypeTextField;
    @FXML
    private TextField priceTextField;
    @FXML
    private Button saveFileBtn;
    @FXML
    private Button backBtn;
    @FXML
    private CheckBox isBestsellerCheckBox;
    @FXML
    private TextField publisherTextField;
    @FXML
    private TextField yearTextField;
    @FXML
    private TextField journalNameTextField;
    @FXML
    private TextField editorNameTextField;
    @FXML
    private TextField numberOfCopiesTextField;
    @FXML
    private TextField editionTextField;


    /**
     * Initialization and run new scene on the primary stage
     */
    void startForm(Stage primaryStage, Session currentSession, DatabaseManager databaseManager, ActionManager actionManager) throws Exception {
        this.stage = primaryStage;
        this.session = currentSession;
        this.databaseManager = databaseManager;
        this.actionManager = actionManager;
        sceneInitialization();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Initialization scene and scene's elements
     */
    private void sceneInitialization() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLFiles/addFileForm.fxml"));
        loader.setController(this);
        GridPane root = loader.load();
        this.scene = new Scene(root, 700, 700);

        titleTextField = (TextField) scene.lookup("#titleField");
        authorsTextField = (TextField) scene.lookup("#authorsField");
        docTypeTextField = (TextField) scene.lookup("#docTypeField");
        keywordsTextField = (TextField) scene.lookup("#keywordsField");
        priceTextField = (TextField) scene.lookup("#priceField");
        publisherTextField = (TextField) scene.lookup("#publisherField");
        yearTextField = (TextField) scene.lookup("#yearField");
        editionTextField = (TextField) scene.lookup("#editionField");
        journalNameTextField = (TextField) scene.lookup("#journalNameField");
        editorNameTextField = (TextField) scene.lookup("#editorNameField");
        numberOfCopiesTextField = (TextField) scene.lookup("#numberOfCopiesField");
        saveFileBtn = (Button) scene.lookup("#saveFileBtn");
        backBtn = (Button) scene.lookup("#backBtn");
        isBestsellerCheckBox = (CheckBox) scene.lookup("#isBestsellerCheckBox");

    }

    /**
     * Click on button "Save" event
     * button for collecting information from the textFields and create a new doc of a particular docType in the database
     */
    @FXML
    public void save() throws Exception {

        ArrayList<String> keywords;
        ArrayList<String> authors;
        int price = 0;
        int year = 2018;
        int numberOfCopies = 0;
        String title = "None";
        boolean isBestseller = isBestsellerCheckBox.isSelected();
        String publisher = "None";
        String editor = "None";
        String journalName = "None";
        String edition = "None";


        if (!keywordsTextField.getText().isEmpty()) {
            keywords = new ArrayList<String>(Arrays.asList(keywordsTextField.getText().toLowerCase().replace(',', ';').split(";")));
        } else {
            keywords = new ArrayList<String>(Arrays.asList("none"));
        }

        if (!authorsTextField.getText().isEmpty()) {
            authors = new ArrayList<String>(Arrays.asList(authorsTextField.getText().toLowerCase().replace(',', ';').split(";")));
        } else {
            authors = new ArrayList<String>(Arrays.asList("none"));
        }
        if (!priceTextField.getText().isEmpty()) {
            price = Integer.parseInt(priceTextField.getText());
        }
        if (!yearTextField.getText().isEmpty()) {
            year = Integer.parseInt(yearTextField.getText());
        }
        if (!numberOfCopiesTextField.getText().isEmpty()) {
            numberOfCopies = Integer.parseInt(numberOfCopiesTextField.getText());
        }
        if (!titleTextField.getText().isEmpty()) {
            title = titleTextField.getText();
        }
        if (!publisherTextField.getText().isEmpty()) {
            publisher = publisherTextField.getText();
        }
        if (!editorNameTextField.getText().isEmpty()) {
            editor = editorNameTextField.getText();
        }
        if (!editionTextField.getText().isEmpty()) {
            edition = editionTextField.getText() + "edition";
        }
        if (!journalNameTextField.getText().isEmpty()) {
            journalName = journalNameTextField.getText();
        }


        if (docTypeTextField.getText().equals("Book")) {
            Book file = new Book(title, authors, keywords, price, publisher,
                    year, edition, isBestseller);
            databaseManager.saveDocuments(file);

            int room = 417;
            for (int i = 0; i < numberOfCopies; i++) {
                Copy copy = new Copy(file, 4, room);
                room++;
                databaseManager.saveDocuments(file);
            }

            if ((!(numberOfCopiesTextField.getText().replace(" ", "").isEmpty()))
                    && (Integer.parseInt(numberOfCopiesTextField.getText().replace(" ", "")) <= 1)) {
                file.isReference = true;
                databaseManager.saveDocuments(file);
            }
        } else if (docTypeTextField.getText().equals("AVMaterial")) {
            AVMaterial file = new AVMaterial(title, authors, keywords, price);
            databaseManager.saveDocuments(file);
            int room = 416;
            for (int i = 0; i < numberOfCopies; i++) {
                Copy copy = new Copy(file, 4, room);
                room++;
                databaseManager.saveDocuments(file);
            }

            if ((!(numberOfCopiesTextField.getText().replace(" ", "").isEmpty()))
                    && (Integer.parseInt(numberOfCopiesTextField.getText().replace(" ", "")) <= 0)) {
                file.isReference = true;
                databaseManager.saveDocuments(file);
            }
        }

            else if (docTypeTextField.getText().equals("JournalArticle")) {
            JournalArticle file = new JournalArticle(title, authors, keywords, price,
                    journalName, editor, Integer.toString(year));
            databaseManager.saveDocuments(file);

            int room = 417;
            for (int i = 0; i < numberOfCopies; i++) {
                Copy copy = new Copy(file, 4, room);
                room++;
                databaseManager.saveDocuments(file);
            }

            if ((!(numberOfCopiesTextField.getText().replace(" ", "").isEmpty()))
                    && (Integer.parseInt(numberOfCopiesTextField.getText().replace(" ", "")) <= 1)) {
                file.isReference = true;
                databaseManager.saveDocuments(file);
            }
        } else {
            Book file = new Book(title, authors, keywords, price, publisher,
                    year, edition, isBestseller);
            databaseManager.saveDocuments(file);

            int room = 415;
            for (int i = 0; i < numberOfCopies; i++) {
                Copy copy = new Copy(file, 4, room);
                room++;
                databaseManager.saveDocuments(file);
            }
            if ((!(numberOfCopiesTextField.getText().replace(" ", "").isEmpty()))
                    && (Integer.parseInt(numberOfCopiesTextField.getText().replace(" ", "")) <= 1)) {
                file.isReference = true;
                databaseManager.saveDocuments(file);
            }
            actionManager.actionNotes.add(new ActionNote(session.userCard, session.day, session.month,
                    ActionNote.ADD_DOCUMENT_ACTION_ID, file));
            databaseManager.update();
        }
        this.back();
    }

    /**
     * Click on button "Back"
     * button for coming back to the EditForm
     *
     * @throws Exception
     */
    @FXML
    public void back() throws Exception {
        EditForm mainForm = new EditForm();
        mainForm.startForm(stage, session, databaseManager, actionManager);
    }

}


