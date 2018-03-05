package forms;

import documents.AVMaterial;
import documents.Book;
import documents.JournalArticle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import storage.Database;
import users.*;

import java.util.ArrayList;
import java.util.Arrays;

public class addUserForm {
    private Stage stage;
    private Scene scene;
    private Session session;
    private Database database;

    @FXML
    private TextField nameTextField;
    @FXML
    private TextField surnameTextField;
    @FXML
    private TextField userTypeTextField;
    @FXML
    private TextField phoneNumberTextField;
    @FXML
    private TextField addressTextField;


    /**
     * Initialization and run new scene on the primary stage
     */
    void startForm(Stage primaryStage, Session currentSession, Database database) throws Exception {
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("addUserForm.fxml"));
        loader.setController(this);
        GridPane root = loader.load();
        this.scene = new Scene(root, 700, 700);

        nameTextField = (TextField) scene.lookup("#nameField");
        surnameTextField = (TextField) scene.lookup("#surnameField");
        userTypeTextField = (TextField) scene.lookup("#userTypeField");
        phoneNumberTextField = (TextField) scene.lookup("#phoneNumberField");
        addressTextField = (TextField) scene.lookup("#addressField");


    }

    @FXML
    public void save() {
        UserCard newUserCard = null;
        if (userTypeTextField.getText().equals("librarian") || userTypeTextField.getText().equals("Librarian")) {
            newUserCard = new UserCard(nameTextField.getText(),
                    surnameTextField.getText(), new Librarian(),
                    phoneNumberTextField.getText(),
                    addressTextField.getText());
        } else if (userTypeTextField.getText().equals("faculty") || userTypeTextField.getText().equals("Faculty")) {
            newUserCard = new UserCard(nameTextField.getText(),
                    surnameTextField.getText(),
                    new Faculty(),
                    phoneNumberTextField.getText(),
                    addressTextField.getText());
        } else if (userTypeTextField.getText().equals("student") || userTypeTextField.getText().equals("Student")) {
            newUserCard = new UserCard(nameTextField.getText(),
                    surnameTextField.getText(),
                    new Student(),
                    phoneNumberTextField.getText(),
                    addressTextField.getText());

        }
        database.saveUserCard(newUserCard);
    }
    @FXML public void back() throws Exception {
        EditForm mainForm = new EditForm();
        mainForm.startForm(stage, session,database);
    }

}
