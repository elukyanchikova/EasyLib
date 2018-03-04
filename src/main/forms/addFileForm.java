package forms;

import documents.AVMaterial;
import documents.Book;
import documents.Document;
import documents.JournalArticle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import users.Session;

import java.util.ArrayList;
import java.util.Arrays;

public class addFileForm {
    private Stage stage;
    private Scene scene;
    private Session session;

    @FXML private TextField titleTextField;
    @FXML private TextField authorsTextField;
    @FXML private TextField keywordsTextField;
    @FXML private TextField docTypeTextField;
    @FXML private TextField priceTextField;
    @FXML private Button saveFileBtn;
    @FXML private CheckBox isBestsellerCheckBox;
    @FXML private TextField publisherTextField;
    @FXML private TextField yearTextField;
    @FXML private TextField journalNameTextField;
    @FXML private TextField issueTextField;
    @FXML private TextField editorNameTextField;


    /**
     * Initialization and run new scene on the primary stage
     */
    void startForm(Stage primaryStage, Session currentSession) throws Exception{
        this.stage = primaryStage;
        this.session = currentSession;
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
        issueTextField = (TextField) scene.lookup("#issueField");
        journalNameTextField = (TextField) scene.lookup("#journalNameField");
        editorNameTextField = (TextField) scene.lookup("#editorNameField");
        saveFileBtn = (Button) scene.lookup("#saveFileBtn");
        isBestsellerCheckBox = (CheckBox) scene.lookup("#isBestsellerCheckBox");

    }
    @FXML public void save(){
        ArrayList<String> keywords = new ArrayList<String>(Arrays.asList(keywordsTextField.getText().toLowerCase().replace(',' , ';').split(";")));
        ArrayList<String> authors = new ArrayList<String>(Arrays.asList(authorsTextField.getText().toLowerCase().replace(',' , ';').split(";")));


        int price = Integer.parseInt(priceTextField.getText());
        int year = Integer.parseInt(yearTextField.getText());


        boolean isBestseller = isBestsellerCheckBox.isSelected();
        if (docTypeTextField.getText() == "Book")
        {
            Book file = new Book( titleTextField.getText(), authors, keywords, price, publisherTextField.getText(),
                    year, isBestseller);

        }
        else if(docTypeTextField.getText() == "AVMaterial") {
              AVMaterial file = new AVMaterial( titleTextField.getText(), authors, keywords, price)
            ;}
         else if(docTypeTextField.getText() == "JournalArticle")
            {
                JournalArticle file = new JournalArticle( titleTextField.getText(), authors, keywords, price,
                        journalNameTextField.getText(), editorNameTextField.getText(), issueTextField.getText());

            }
        }

        //TODO add this particular doc to the database


    }

