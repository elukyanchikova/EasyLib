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
        String surname = "None";
        String name = "None";
        String phoneNumber = "none";
        String address = "none";
        if (!surnameTextField.getText().isEmpty()) {
            surname = surnameTextField.getText();
        }
        if (!nameTextField.getText().isEmpty()) {
            name = nameTextField.getText();
        }
        if (!phoneNumberTextField.getText().isEmpty()) {
            phoneNumber = phoneNumberTextField.getText();
        }
        if (!addressTextField.getText().isEmpty()) {
            address = phoneNumberTextField.getText();
        }


        if (userTypeTextField.getText().equals("librarian") || userTypeTextField.getText().equals("Librarian")) {
            newUserCard = new UserCard(name,
                    surname, new Librarian(),
                    phoneNumber,
                    address,1);
        } else if (userTypeTextField.getText().equals("faculty") || userTypeTextField.getText().equals("Faculty")) {
            newUserCard = new UserCard(name,
                    surname,
                    new Faculty(),
                    phoneNumber,
                    address,1);
        } else if (userTypeTextField.getText().equals("student") || userTypeTextField.getText().equals("Student")) {
            newUserCard = new UserCard(nameTextField.getText(),
                    surname,
                    new Student(),
                    phoneNumber,
                    address,1);

        } else {
            newUserCard = new UserCard(name, surname, new Guest(), phoneNumber, address,1);
        }
        database.saveUserCard(newUserCard);
    }

    @FXML
    public void back() throws Exception {
        EditForm mainForm = new EditForm();
        mainForm.startForm(stage, session, database);
    }

}
