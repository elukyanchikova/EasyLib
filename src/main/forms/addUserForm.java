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
import users.*;

import java.util.ArrayList;
import java.util.Arrays;

public class addUserForm {
    private Stage stage;
    private Scene scene;
    private Session session;

    @FXML private TextField nameTextField;
    @FXML private TextField surnameTextField;
    @FXML private TextField userTypeTextField;
    @FXML private TextField phoneNumberTextField;
    @FXML private TextField addressTextField;


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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("addUserForm.fxml"));
        loader.setController(this);
        GridPane root = loader.load();
        this.scene = new Scene(root,700,700);

        nameTextField = (TextField) scene.lookup("#nameField");
        surnameTextField = (TextField) scene.lookup("#surnameField");
        userTypeTextField = (TextField) scene.lookup("#userTypeField");
        phoneNumberTextField = (TextField) scene.lookup("#phoneNumberField");
        addressTextField = (TextField) scene.lookup("#addressField");



    }
    @FXML public void save(){
       if(userTypeTextField.getText() == "librarian" || userTypeTextField.getText() == "Librarian")
       {
           UserCard newUserCard = new UserCard(nameTextField.getText(),
                   surnameTextField.getText(),  new Librarian(),
                   phoneNumberTextField.getText(),
                   addressTextField.getText());
       }
       else if (userTypeTextField.getText() == "faculty" || userTypeTextField.getText() == "Faculty")
        {
            UserCard newUserCard = new UserCard(nameTextField.getText(),
                    surnameTextField.getText(),
                    new Faculty(),
                    phoneNumberTextField.getText(),
                    addressTextField.getText());
        }
       else if (userTypeTextField.getText() == "student" || userTypeTextField.getText() == "Student")
       {
           UserCard newUserCard = new UserCard(nameTextField.getText(),
                   surnameTextField.getText(),
                   new Student(),
                   phoneNumberTextField.getText(),
                   addressTextField.getText());
       }
    }
   //TODO connect with database

}
