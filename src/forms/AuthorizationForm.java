package forms;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import users.Guest;
import users.Session;
import users.Student;

public class AuthorizationForm {


    private Stage stage;
    private Scene scene;

    @FXML private TextField emailTextField;
    @FXML private Button loginAsStudentBtn;
    @FXML private Button loginAsGuestBtn;
    public void startForm(Stage primaryStage) throws Exception{
        this.stage = primaryStage;
        sceneInitialization();
        stage.setScene(scene);
        stage.show();
    }

    private void sceneInitialization() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AuthorizationForm.fxml"));
        loader.setController(this);
        GridPane root = loader.load();
        this.scene = new Scene(root,700,700);
    }

    @FXML
    public void loginAsStudent() throws Exception{

        MainForm mainForm = new MainForm();
        mainForm.startForm(stage,new Session(new Student()));
    }

    @FXML
    public void loginAsGuest()throws Exception{
        MainForm mainForm = new MainForm();
        mainForm.startForm(stage,new Session(new Guest()));

        /*currentSession = new Session(new Guest());
        primaryStage.setScene(scene);
        checkoutButton.setVisible(false);
        primaryStage.show();*/
    }
}
