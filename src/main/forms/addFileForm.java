package forms;

import documents.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.junit.Test;
import storage.Database;
import users.Session;

import javax.print.attribute.standard.Copies;
import java.util.ArrayList;
import java.util.Arrays;

public class addFileForm {
    private Stage stage;
    private Scene scene;
    private Session session;
    private Database database;

    @FXML private TextField titleTextField;
    @FXML private TextField authorsTextField;
    @FXML private TextField keywordsTextField;
    @FXML private TextField docTypeTextField;
    @FXML private TextField priceTextField;
    @FXML private Button saveFileBtn;
    @FXML private Button backBtn;
    @FXML private CheckBox isBestsellerCheckBox;
    @FXML private TextField publisherTextField;
    @FXML private TextField yearTextField;
    @FXML private TextField journalNameTextField;
    @FXML private TextField editorNameTextField;
    @FXML private TextField numberOfCopiesTextField;


    /**
     * Initialization and run new scene on the primary stage
     */
    void startForm(Stage primaryStage, Session currentSession, Database database) throws Exception{
        this.stage = primaryStage;
        this.session = currentSession;
        this.database = database;
        sceneInitialization();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Initialization scene and scene's elements
     */
    private void sceneInitialization() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("addFileForm.fxml"));
        loader.setController(this);
        GridPane root = loader.load();
        this.scene = new Scene(root,700,700);

        titleTextField = (TextField) scene.lookup("#titleField");
        authorsTextField = (TextField) scene.lookup("#authorsField");
        docTypeTextField = (TextField) scene.lookup("#docTypeField");
        keywordsTextField = (TextField) scene.lookup("#keywordsField");
        priceTextField = (TextField) scene.lookup("#priceField");
        publisherTextField = (TextField) scene.lookup("#publisherField");
        yearTextField = (TextField) scene.lookup("#yearField");
        journalNameTextField = (TextField) scene.lookup("#journalNameField");
        editorNameTextField = (TextField) scene.lookup("#editorNameField");
        numberOfCopiesTextField = (TextField) scene.lookup("#numberOfCopiesField") ;
        saveFileBtn = (Button) scene.lookup("#saveFileBtn");
        backBtn = (Button) scene.lookup("#backBtn");
        isBestsellerCheckBox = (CheckBox) scene.lookup("#isBestsellerCheckBox");

    }
    @FXML public void save(){
        ArrayList<String> keywords = new ArrayList<String>(Arrays.asList(keywordsTextField.getText().toLowerCase().replace(',' , ';').split(";")));
        ArrayList<String> authors = new ArrayList<String>(Arrays.asList(authorsTextField.getText().toLowerCase().replace(',' , ';').split(";")));
        int price = Integer.parseInt(priceTextField.getText());
        int year = Integer.parseInt(yearTextField.getText());
        boolean isBestseller = isBestsellerCheckBox.isSelected();
        int numberOfCopies = Integer.parseInt(numberOfCopiesTextField.getText());

        if (docTypeTextField.getText().equals("Book"))
        {
            Book file = new Book( titleTextField.getText(), authors, keywords, price, publisherTextField.getText(),
                    year, isBestseller);
            database.saveDocuments(file);

            int room = 415;
            for (int i = 0; i <numberOfCopies ; i++) {
                Copy copy = new Copy(file, 4, room);
                room++;
                database.saveDocuments(file);
            }
        }
        else if(docTypeTextField.getText().equals("AVMaterial")) {
              AVMaterial file = new AVMaterial( titleTextField.getText(), authors, keywords, price);
             database.saveDocuments(file);
            int room = 416;
            for (int i = 0; i <numberOfCopies ; i++) {
                Copy copy = new Copy(file, 4, room);
                room++;
                database.saveDocuments(file);
            }}
         else if(docTypeTextField.getText().equals("JournalArticle"))
            {
                JournalArticle file = new JournalArticle( titleTextField.getText(), authors, keywords, price,
                        journalNameTextField.getText(), editorNameTextField.getText(), yearTextField.getText());
                database.saveDocuments(file);

                int room = 417;
                for (int i = 0; i <numberOfCopies ; i++) {
                    Copy copy = new Copy(file, 4, room);
                    room++;
                    database.saveDocuments(file);
                }
            }
        }


    @FXML public void back() throws Exception {
        EditForm mainForm = new EditForm();
        mainForm.startForm(stage, session,database);
    }

    }


