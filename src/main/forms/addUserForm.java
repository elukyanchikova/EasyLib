package forms;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import users.Session;

public class addUserForm {
    private Stage stage;
    private Scene scene;
    private Session session;

    @FXML private TextField nameTextField;
    @FXML private TextField surnameTextField;
    @FXML private TextField idTextField;
    @FXML private TextField addressTextField;
    @FXML private TextField phoneNumberTextField;

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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AuthorizationForm.fxml"));
        loader.setController(this);
        GridPane root = loader.load();
        this.scene = new Scene(root,700,700);

        nameTextField = (TextField) scene.lookup("#nameField");
        surnameTextField = (TextField) scene.lookup("#surnameTextField");
        idTextField = (TextField) scene.lookup("#idTextField");
        phoneNumberTextField = (TextField) scene.lookup("#phoneNumberTextField");
        addressTextField = (TextField) scene.lookup("#addressTextField");



    }

}
