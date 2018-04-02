package forms;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import storage.DatabaseManager;
import users.*;

public class addUserForm {
    private Stage stage;
    private Scene scene;
    private Session session;
    private DatabaseManager databaseManager;

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
    void startForm(Stage primaryStage, Session currentSession, DatabaseManager databaseManager) throws Exception {
        this.stage = primaryStage;
        this.session = currentSession;
        this.databaseManager = databaseManager;
        sceneInitialization();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Initialization scene and scene's elements
     */
    private void sceneInitialization() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLFiles/addUserForm.fxml"));
        loader.setController(this);
        GridPane root = loader.load();
        this.scene = new Scene(root, 700, 700);

        nameTextField = (TextField) scene.lookup("#nameField");
        surnameTextField = (TextField) scene.lookup("#surnameField");
        userTypeTextField = (TextField) scene.lookup("#userTypeField");
        phoneNumberTextField = (TextField) scene.lookup("#phoneNumberField");
        addressTextField = (TextField) scene.lookup("#addressField");
    }

    /**
     * Click on button "Save" event
     * button for collecting information from the textFields and create a new UserCard
     */
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
                    address);
        } else if (userTypeTextField.getText().equals("faculty") || userTypeTextField.getText().equals("Faculty")) {
            newUserCard = new UserCard(name,
                    surname,
                    new Faculty(),
                    phoneNumber,
                    address);
        } else if (userTypeTextField.getText().equals("student") || userTypeTextField.getText().equals("Student")) {
            newUserCard = new UserCard(nameTextField.getText(),
                    surname,
                    new Student(),
                    phoneNumber,
                    address);

        } else if (userTypeTextField.getText().toLowerCase().equals("ta")) {
            newUserCard = new UserCard(nameTextField.getText(),
                    surname,
                    new TA(),
                    phoneNumber,
                    address);

        } else if (userTypeTextField.getText().toLowerCase().equals(("visitingprofessor"))) {
            newUserCard = new UserCard(nameTextField.getText(),
                    surname,
                    new VisitingProfessor(),
                    phoneNumber,
                    address);

        } else if (userTypeTextField.getText().toLowerCase().equals("professor")) {
            newUserCard = new UserCard(nameTextField.getText(),
                    surname,
                    new Professor(),
                    phoneNumber,
                    address);

        } else {
            newUserCard = new UserCard(name, surname, new Guest(), phoneNumber, address);
        }
        databaseManager.saveUserCard(newUserCard);
    }

    /**
     * Click ob button "back" event
     * button for coming back to the EditForm
     * @throws Exception
     */

    @FXML
    public void back() throws Exception {
        EditForm mainForm = new EditForm();
        mainForm.startForm(stage, session, databaseManager);
    }

}
