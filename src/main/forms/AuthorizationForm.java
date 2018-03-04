package forms;

import documents.Storage;
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
    private Session session;

    @FXML private TextField emailTextField;
    @FXML private Button loginAsStudentBtn;
    @FXML private Button loginAsGuestBtn;

    /**
     * Initialization and run new scene on the primary stage
     */
    void startForm(Stage primaryStage) throws Exception{
        this.stage = primaryStage;
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

        emailTextField = (TextField) scene.lookup("#emailTextField");
        loginAsStudentBtn = (Button) scene.lookup("#loginAsStudentBtn");
        loginAsGuestBtn = (Button) scene.lookup("#loginAsGuestBtn");
    }


    /**
     * Click on button "loginAsStudent" event
     * If textField has right format(will be stronger filter soon) then the student log in(temp: from one account)
   /*  */
    @FXML
    public void loginAsStudent() throws Exception{

        //TODO: get UserCard from storage
        if(emailTextField.getText().toLowerCase().contains("@innopolis.ru") &&
                emailTextField.getText().toLowerCase().replace("@innopolis.ru", "").replace(" ", "").length() != 0)
        {
            MainForm mainForm = new MainForm();
            Session session = new Session(new Student());
            session.userCard = Storage.getUsers().get(0);
            mainForm.startForm(stage, session);
        }
    }


     /**
     /* Click on button "loginAsGuest" event
     */
    /*@FXML
    public void loginAsGuest()throws Exception{
        MainForm mainForm = new MainForm();
        mainForm.startForm(stage,new Session(new Guest()));
    }
*/
     @FXML
     public void loginAsGuest()throws Exception{
         EditForm mainForm = new EditForm();
         mainForm.startForm(stage,new Session(new Guest()));
     }

    @FXML
    public void templar()throws Exception{
       ReturnForm mainForm2 = new ReturnForm();
        mainForm2.startForm(stage, new Session(new Guest()));
    }


}
